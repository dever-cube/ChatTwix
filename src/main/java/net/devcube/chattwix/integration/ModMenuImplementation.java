package net.devcube.chattwix.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.devcube.chattwix.gui.ConfigsGui;

public class ModMenuImplementation implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return (screen) -> {
            ConfigsGui gui = new ConfigsGui();
            gui.setParent(screen);
            return gui;
        };
    }
}