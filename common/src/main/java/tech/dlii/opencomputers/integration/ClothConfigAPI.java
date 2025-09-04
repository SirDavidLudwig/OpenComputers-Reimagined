package tech.dlii.opencomputers.integration;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.Level;
import tech.dlii.opencomputers.OpenComputers;

import static tech.dlii.opencomputers.api.API.MOD_ID;
import static tech.dlii.opencomputers.common.config.Configuration.SCREEN_RENDER_DISTANCE;
import static tech.dlii.opencomputers.common.config.Configuration.USE_POWER;

public class ClothConfigAPI
{

    public static Screen createConfigScreen(Screen parent)
    {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config." + MOD_ID));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Server specific settings.
        ConfigCategory server = builder.getOrCreateCategory(Component.translatable("config." + MOD_ID + ".server"));

        server.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config." + MOD_ID + ".server.use_power"), USE_POWER)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config." + MOD_ID + ".server.use_power.desc"))
                .setSaveConsumer(newValue -> USE_POWER = newValue)
                .build());

        // Client specific settings.
        ConfigCategory client = builder.getOrCreateCategory(Component.translatable("config." + MOD_ID + ".client"));

        client.addEntry(entryBuilder.startIntSlider(Component.translatable("config." + MOD_ID + ".client.screen_render_distance"), SCREEN_RENDER_DISTANCE, 0, 32)
                .setDefaultValue(16)
                .setTooltip(Component.translatable("config." + MOD_ID + ".client.screen_render_distance.desc"))
                .setSaveConsumer(newValue -> SCREEN_RENDER_DISTANCE = newValue)
                .build());

        // Save the config.
        builder.setSavingRunnable(() -> {
            OpenComputers.LOGGER.log(Level.INFO, "Saving config ..");

            //File file = new File()

            OpenComputers.LOGGER.log(Level.INFO, "Done");
        });

        return builder.build();
    }
}
