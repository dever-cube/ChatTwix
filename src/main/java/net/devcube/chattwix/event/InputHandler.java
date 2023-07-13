package net.devcube.chattwix.event;

import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import net.devcube.chattwix.ModInfo;
import net.devcube.chattwix.config.Configs;

public class InputHandler implements IKeybindProvider
{
    private final KeybindCallbacks callbacks;

    public InputHandler()
    {
        this.callbacks = KeybindCallbacks.getInstance();
    }

    @Override
    public void addKeysToMap(IKeybindManager manager)
    {
        for (IHotkey hotkey : Configs.HotKeys.HOTKEYS_LIST)
        {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager)
    {
        manager.addHotkeysForCategory(ModInfo.MOD_NAME, "chattwix.hotkeys.category.hotkeys", Configs.HotKeys.HOTKEYS_LIST);
    }
}