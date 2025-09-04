package tech.dlii.opencomputers.common.config;

public final class Configuration {

    public static int EEPROM_DATA_SIZE = 256;

    public static int EEPROM_SIZE = 4096;

    public static int EXECUTION_DELAY = 12;

    public static boolean USE_POWER = true;

    public static int SCREEN_RENDER_DISTANCE = 16;

    public static int[] RAM_SIZES = new int[] {
            192,
            256,
            384,
            512,
            768,
            1024
    };

    public static double[] CALL_BUDGETS = new double[] {
            0.5,
            1.0,
            1.5
    };

    public static int[] CPU_COMPONENT_COUNT = new int[] {
            8,
            12,
            16,
            1024
    };
}
