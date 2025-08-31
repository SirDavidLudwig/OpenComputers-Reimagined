package tech.dlii.opencomputers.integration;

import org.apache.logging.log4j.Logger;
import tech.dlii.opencomputers.OpenComputers;
import tech.dlii.opencomputers.integration.opencomputers.ModOpenComputers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mods {

    private static Logger LOGGER = tech.dlii.opencomputers.OpenComputers.LOGGER;
    private static boolean preInitialized = false;
    private static boolean initialized = false;
    public static final List<Mod> knownMods = new ArrayList<>();

    public static final Mod OpenComputers = register(new ModOpenComputers());

    public static Mod register(Mod mod) {
        knownMods.add(mod);
        return mod;
    }

    public static void preInitialize() {
        if (preInitialized) {
            return;
        }
        for (Mod mod : knownMods) {
            tryPreInitialize(mod);
        }
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        for (Mod mod : knownMods) {
            tryInitialize(mod);
        }
    }

    protected static void tryPreInitialize(Mod mod) {
        try {
            LOGGER.debug("Pre-initializing mod integration for '" + mod.id() + "'");
            mod.initialize();
        } catch (Throwable exception) {
            LOGGER.warn("Error pre-initializing integration for '" + mod.id() + "'");
        }
    }

    protected static void tryInitialize(Mod mod) {
        try {
            LOGGER.debug("Initializing mod integration for '" + mod.id() + "'");
            mod.initialize();
        } catch (Throwable exception) {
            LOGGER.warn("Error initializing integration for '" + mod.id() + "'");
            LOGGER.warn(exception);
        }
    }
}
