package net.devcube.chattwix.mixin;

import com.mojang.authlib.GameProfile;
import net.devcube.chattwix.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;
import java.util.Objects;

@Mixin(MessageHandler.class)
public class MessageHandlerMixin {

    @Inject(method = "processChatMessageInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", shift = At.Shift.AFTER))
    private void playSound(MessageType.Parameters params, SignedMessage message, Text decorated, GameProfile sender, boolean onlyShowSecureChat, Instant receptionTimestamp, CallbackInfoReturnable<Boolean> cir){
        if (Configs.Generic.PLAY_MSG_SOUND.getBooleanValue()) {
            if (decorated.getContent() instanceof TranslatableTextContent content) {
                if (Objects.equals(content.getKey(), "commands.message.display.incoming")) {
                    ClientPlayerEntity player = MinecraftClient.getInstance().player;
                    if (player != null) {

                        player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), (float) Configs.Generic.MSG_SOUND_VOLUME.getDoubleValue(), (float) 1);
                        player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), (float) Configs.Generic.MSG_SOUND_VOLUME.getDoubleValue(), (float) 1);
                        player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL.value(), (float) Configs.Generic.MSG_SOUND_VOLUME.getDoubleValue(), (float) 1);
                    }
                }
            }

        }
    }

    @ModifyArg(method = "processChatMessageInternal", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(" +
                    "Lnet/minecraft/text/Text;" +
                    "Lnet/minecraft/network/message/MessageSignatureData;" +
                    "Lnet/minecraft/client/gui/hud/MessageIndicator;)V"),
            index = 0)
    private Text editStyle(Text message) {

        if (message.getContent() instanceof TranslatableTextContent messageContent) {
            if (Objects.equals(messageContent.getKey(), "commands.message.display.incoming")) {

                Style style = message.getStyle()
                        .withItalic(Configs.Style.ITALIC.getBooleanValue())
                        .withBold(Configs.Style.BOLD.getBooleanValue())
                        .withColor(Configs.Style.MSG_COLOR.getIntegerValue());
                return message.copy().setStyle(style);
            }
        }
        return message;
    }
}
