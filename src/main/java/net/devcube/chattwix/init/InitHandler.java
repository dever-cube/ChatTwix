package net.devcube.chattwix.init;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.devcube.chattwix.ModInfo;
import net.devcube.chattwix.config.Configs;
import net.devcube.chattwix.event.InputHandler;
import net.devcube.chattwix.event.KeybindCallbacks;

public class InitHandler  implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(ModInfo.MOD_ID, new Configs());

        InputHandler handler = new InputHandler();
        InputEventHandler.getKeybindManager().registerKeybindProvider(handler);

        KeybindCallbacks.getInstance().setCallbacks();}
}