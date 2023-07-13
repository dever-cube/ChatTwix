package net.devcube.chattwix.client;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.devcube.chattwix.init.InitHandler;
import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ChatTwixClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
    }
}
