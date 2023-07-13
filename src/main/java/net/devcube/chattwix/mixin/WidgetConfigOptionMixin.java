package net.devcube.chattwix.mixin;

import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import net.devcube.chattwix.config.ConfigColorColorized;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WidgetConfigOption.class)
public class WidgetConfigOptionMixin {

    @Shadow(remap = false) @Final protected GuiConfigsBase.ConfigOptionWrapper wrapper;

    @ModifyArg(method = "addConfigOption", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/widgets/WidgetConfigOption;addLabel(IIIII[Ljava/lang/String;)V", remap = false),index = 4, remap = false)
    private int setColor(int color){
        if(this.wrapper.getConfig() instanceof ConfigColorColorized colorLabelConfig){
            color = colorLabelConfig.getColor().intValue;
        }
        return color;
    }
}
