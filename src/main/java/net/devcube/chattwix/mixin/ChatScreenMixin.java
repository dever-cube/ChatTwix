package net.devcube.chattwix.mixin;

import net.devcube.chattwix.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen{

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Shadow protected abstract void init();

    @Shadow protected TextFieldWidget chatField;

    @Redirect(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
    private void keepOpen(MinecraftClient instance, Screen screen){
        if(!Configs.Generic.CLOSE_AFTER_SEND.getBooleanValue()) {
            init();
            this.chatField.setText("");
        }
        else {
            if(this.client != null) {
                this.client.setScreen(null);
            }
        }
    }

}
