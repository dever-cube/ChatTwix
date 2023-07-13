package net.devcube.chattwix.gui;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import net.devcube.chattwix.ModInfo;
import net.devcube.chattwix.config.Configs;

import java.util.Collections;
import java.util.List;

public class ConfigsGui  extends GuiConfigsBase {

    private static ConfigGuiTab tab = ConfigGuiTab.GENERIC;

    public ConfigsGui() {
        super(10, 50, ModInfo.MOD_ID, null, "chattwix.gui.title.configs");

    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab)
    {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(ConfigsGui.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }

    private record ButtonListener(ConfigGuiTab tab,
                                  ConfigsGui parent) implements IButtonActionListener {

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            ConfigsGui.tab = this.tab;

            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values())
        {
            x += this.createButton(x, y, -1, tab);
        }
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = ConfigsGui.tab;

        if (tab == ConfigGuiTab.GENERIC)
        {
            configs = Configs.Generic.OPTIONS;
        }
        else if (tab == ConfigGuiTab.STYLE)
        {
            configs = Configs.Style.OPTIONS;
        }
        else if (tab == ConfigGuiTab.HOT_KEYS)
        {
            configs = Configs.HotKeys.HOTKEYS_LIST;
        }
        else
        {
            return Collections.emptyList();
        }
        return ConfigOptionWrapper.createFor(configs);
    }

    public enum ConfigGuiTab
    {
        GENERIC ("chattwix.gui.button.config_gui.generic"),
        STYLE ("chattwix.gui.button.config_gui.style"),
        HOT_KEYS ("chattwix.gui.button.config_gui.hotkeys");
        private final String translationKey;

        public static final ImmutableList<ConfigGuiTab> VALUES = ImmutableList.copyOf(values());

        ConfigGuiTab(String translationKey)
        {
            this.translationKey = translationKey;
        }

        public String getDisplayName()
        {
            return StringUtils.translate(this.translationKey);
        }
    }
}
