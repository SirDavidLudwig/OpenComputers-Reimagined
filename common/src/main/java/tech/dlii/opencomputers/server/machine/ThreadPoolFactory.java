package tech.dlii.opencomputers.server.machine;

import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.server.MinecraftServer;
import tech.dlii.opencomputers.OpenComputers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Pretty much copied directly from old implementation.
 */
public class ThreadPoolFactory {

    private static ArrayList<SafeThreadPool> safePools = new ArrayList<>();

    public static ScheduledExecutorService create(String name, int threads) {
        return Executors.newScheduledThreadPool(threads, new ThreadFactory() {

            private String BASE_NAME = "OpenComputers-" + name + "-";
            private AtomicInteger threadId = new AtomicInteger(1);
            private ThreadGroup group = Thread.currentThread().getThreadGroup();

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(group, runnable, BASE_NAME + threadId.getAndIncrement());
                if (!thread.isDaemon()) {
                    thread.setDaemon(true);
                }
                // @TODO thread priority
                thread.setPriority(Thread.NORM_PRIORITY);
                return thread;
            }
        });
    }

    public static SafeThreadPool createSafeThreadPool(String name, int threads) {
        SafeThreadPool handler = new SafeThreadPool(name, threads);
        safePools.add(handler);
        return handler;
    }

    // Event Handling --------------------------------------------------------------------------------------------------

    public static void onServerBeforeStart(MinecraftServer minecraftServer) {
        safePools.forEach(SafeThreadPool::newThreadPool);
    }

    public static void onServerStopped(MinecraftServer minecraftServer) {
        safePools.forEach(SafeThreadPool::waitForCompletion);
    }

    public static class SafeThreadPool {

        private String name;
        private int threads;
        private ScheduledExecutorService threadPool = null;

        public SafeThreadPool(String name, int threads) {
            this.name = name;
            this.threads = threads;
        }

        public Optional<Future<?>> withPool(Function<ScheduledExecutorService, Future<?>> f, boolean requiresPool) {
            if (threadPool == null) {
                OpenComputers.LOGGER.warn("Error handling file saving: Did the server never start?");
                if (!requiresPool) {
                    return Optional.empty();
                }
                OpenComputers.LOGGER.warn("Creating new thread pool.");
                newThreadPool();
            } else if (threadPool.isShutdown() || threadPool.isTerminated()) {
                OpenComputers.LOGGER.warn("Error handling file saving: Thread pool shut down!");
                if (!requiresPool) {
                    return Optional.empty();
                }
                OpenComputers.LOGGER.warn("Creating new thread pool.");
                newThreadPool();
            }
            return Optional.ofNullable(f.apply(threadPool));
        }

        public void newThreadPool() {
            if (threadPool != null && !threadPool.isTerminated()) {
                threadPool.shutdownNow();
            }
            threadPool = ThreadPoolFactory.create(name, threads);
        }

        public void waitForCompletion() {
            withPool(tp -> {
                try {
                    tp.shutdown();
                    boolean terminated = tp.awaitTermination(15, TimeUnit.SECONDS);
                    if (!terminated) {
                        OpenComputers.LOGGER.warn("Warning: Completing all tasks has already taken 15 seconds!");
                        terminated = tp.awaitTermination(105, TimeUnit.SECONDS);
                        if (!terminated) {
                            OpenComputers.LOGGER.error("Warning: Completing all tasks has already taken two minutes! Aborting");
                            tp.shutdownNow();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                return null; // mirrors the Scala code's `null` in the lambda
            }, false);
        }
    }
}
