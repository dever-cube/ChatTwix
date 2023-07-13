package net.devcube.chattwix.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import net.devcube.chattwix.ModInfo;

import java.io.File;

public class Configs implements IConfigHandler {

    private static final String CONFIG_FILE_NAME = ModInfo.MOD_ID + ".json";

    public static class Generic{
        public static final ConfigBoolean CLOSE_AFTER_SEND = new ConfigBoolean("chattwix.option.close_after_send", true, "chattwix.option.close_after_send.desc");
        public static final ConfigBoolean PLAY_MSG_SOUND = new ConfigBoolean("chattwix.option.play_msg_sound", false, "chattwix.option.play_msg_sound.desc");
        public static final ConfigDouble MSG_SOUND_VOLUME = new ConfigDouble("chattwix.option.msg_sound_volume", 0.5, 0.0, 1.0, "chattwix.option.msg_sound_volume.desc");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                CLOSE_AFTER_SEND,
                PLAY_MSG_SOUND,
                MSG_SOUND_VOLUME);
    }

    public static class Style{
        public static final ConfigBoolean BOLD = new ConfigBoolean("chattwix.option.msg_bold", false, "chattwix.option.msg_bold.desc");
        public static final ConfigBoolean ITALIC = new ConfigBoolean("chattwix.option.msg_italic", true, "chattwix.option.msg_italic.desc");
        public static final ConfigColor MSG_COLOR = new ConfigColorColorized("chattwix.option.msg_color", "#AAAAAA", "chattwix.option.msg_color.desc");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                BOLD,
                ITALIC,
                MSG_COLOR);
    }



    @Override
    public void load() {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "Generic", Configs.Generic.OPTIONS);
                ConfigUtils.readConfigBase(root, "Style", Configs.Style.OPTIONS);

            }
        }
    }

    @Override
    public void save() {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();
            ConfigUtils.writeConfigBase(root, "Generic", Configs.Generic.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Style", Configs.Style.OPTIONS);
            root.add("config_version", new JsonPrimitive(ModInfo.MOD_VERSION));
            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }
}
