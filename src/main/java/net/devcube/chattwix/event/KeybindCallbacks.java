package net.devcube.chattwix.event;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.devcube.chattwix.config.Configs;
import net.devcube.chattwix.gui.ConfigsGui;
import net.minecraft.client.MinecraftClient;


public class KeybindCallbacks implements IHotkeyCallback
{
    private static final KeybindCallbacks INSTANCE = new KeybindCallbacks();

    protected int massCraftTicker;

    public static KeybindCallbacks getInstance()
    {
        return INSTANCE;
    }

    private KeybindCallbacks()
    {
    }

    public void setCallbacks()
    {
        for (ConfigHotkey hotkey : Configs.HotKeys.HOTKEYS_LIST)
        {
            hotkey.getKeybind().setCallback(this);
        }
    }



    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key)
    {

        return this.onKeyActionImpl(action, key);
    }

    private boolean onKeyActionImpl(KeyAction action, IKeybind key)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.player == null || mc.world == null)
        {
            return false;
        }


        else if (key == Configs.HotKeys.OPEN_CONFIG_GUI.getKeybind())
        {
            GuiBase.openGui(new ConfigsGui());
            return true;
        }

        return false;
    }


}