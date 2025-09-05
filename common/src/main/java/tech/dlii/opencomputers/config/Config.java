package tech.dlii.opencomputers.config;

import tech.dlii.opencomputers.api.API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static tech.dlii.opencomputers.OpenComputers.LOGGER;
import static tech.dlii.opencomputers.common.config.Configuration.*;

public class Config
{

    private static final Path PATH_COMMON = getCommonConfigPath();

    public static void read()
    {
        if (Files.exists(PATH_COMMON)) {

            try (BufferedReader br = Files.newBufferedReader(PATH_COMMON)) {

                String line;

                // Read until the end.
                while ((line = br.readLine()) != null) {
                    // Ignore comment lines.
                    if (!line.startsWith("#")) {

                        // Get the key and the value.
                        String[] parts = line.split("=");

                        if (2 == parts.length) {

                            String key = parts[0], val = parts[1];

                            try {

                                switch (key) {

                                    case "screen_render_distance":
                                        SCREEN_RENDER_DISTANCE = Integer.parseInt(val);
                                        break;

                                    default:
                                        LOGGER.warn("Unknown option {}, skipping ..", key);
                                }

                            } catch (Exception e) {
                                LOGGER.warn("Bad value for option {}", key);
                            }

                        }
                    }
                }
            } catch (IOException e) {

                // Something went wrong.
                LOGGER.error("Unable to load config: {}", e.getMessage());
                LOGGER.error(e.getStackTrace());
            }
        }
    }

    public static void write()
    {
        try (BufferedWriter bw = Files.newBufferedWriter(PATH_COMMON)) {

            // Save the config.
            bw.write("screen_render_distance=");
            bw.write(Integer.toString(SCREEN_RENDER_DISTANCE));
            bw.newLine();

            LOGGER.info("Finished saving config.");

        } catch (IOException e) {
            // Something went wrong.

            LOGGER.error("Unable to save config: {}", e.getMessage());
            LOGGER.error(e.getStackTrace());

        }

    }


    private static Path getCommonConfigPath()
    {
        return Paths.get(".").resolve("config").resolve(API.MOD_ID + "-common.conf");
    }


}
