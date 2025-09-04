package tech.dlii.opencomputers.neoforge.integration;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import tech.dlii.opencomputers.integration.ClothConfigAPI;

public class NeoForgeModMenu implements IConfigScreenFactory
{
    @Override
    public Screen createScreen(ModContainer modContainer, Screen modScreen)
    {
        return ClothConfigAPI.createConfigScreen(modScreen);
    }
}
