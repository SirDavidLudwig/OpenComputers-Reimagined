package tech.dlii.opencomputers.fabric.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import tech.dlii.opencomputers.OpenComputers;

@Environment(EnvType.CLIENT)
public class ModMenu implements ModMenuApi
{

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {

        if (isClothConfigLoaded())
            return ClothConfigAPI::createConfigScreen;
        return parent -> null;
    }

    private static boolean isClothConfigLoaded() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            try {
                Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
                OpenComputers.LOGGER.warn("Using Cloth Config GUI");
                return true;
            } catch (Exception e) {
                OpenComputers.LOGGER.error("Failed to load Cloth Config: {}", e.getMessage());
                OpenComputers.LOGGER.error(e.getStackTrace());
            }
        }
        return false;
    }
}
