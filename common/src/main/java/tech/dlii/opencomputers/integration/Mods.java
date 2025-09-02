package tech.dlii.opencomputers.integration;

import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class Mods {

    private static Logger LOGGER = tech.dlii.opencomputers.OpenComputers.LOGGER;
    private static boolean preInitialized = false;
    private static boolean initialized = false;
    public static final List<Mod> knownMods = new ArrayList<>();

//    public static final Mod OpenComputers = register(new ModOpenComputers());

    public static Mod register(Mod mod) {
        knownMods.add(mod);
        return mod;
    }

    public static void preInitialize() {
        if (preInitialized) {
            LOGGER.warn("Attempted to re-pre-initialize mod integration.");
            return;
        }
        for (Mod mod : knownMods) {
            tryPreInitialize(mod);
        }
        preInitialized = true;
    }

    public static void initialize() {
        if (initialized) {
            LOGGER.warn("Attempted to re-initialize mod integration.");
            return;
        }
        for (Mod mod : knownMods) {
            tryInitialize(mod);
        }
        initialized = true;
    }

    protected static void tryPreInitialize(Mod mod) {
        try {
            mod.initialize();
        } catch (Throwable exception) {
            LOGGER.warn("Error pre-initializing integration for '" + mod.id() + "'");
        }
    }

    protected static void tryInitialize(Mod mod) {
        try {
            mod.initialize();
        } catch (Throwable exception) {
            LOGGER.warn("Error initializing integration for '" + mod.id() + "'");
            LOGGER.warn(exception);
        }
    }
}
