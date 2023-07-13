package net.devcube.chattwix.mixin;

import net.devcube.chattwix.config.Configs;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

@Mixin(MessageHandler.class)
public class MessageHandlerMixin {

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
