package me.srrapero720.embeddiumplus.mixins.impl.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import me.srrapero720.embeddiumplus.EmbPlusConfig;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.common.gui.overlay.IngredientListOverlay;
import mezz.jei.common.gui.elements.GuiIconToggleButton;
import mezz.jei.common.input.GuiTextFieldFilter;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = IngredientListOverlay.class, remap = false)
@Pseudo
public class JeiOverlayMixin {
    @Shadow @Final private GuiTextFieldFilter textFieldFilter;
    @Shadow @Final private GuiIconToggleButton configButton;
    @Shadow private @Nullable IGuiProperties guiProperties;

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lmezz/jei/common/gui/overlay/IngredientGridWithNavigation;draw(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"), cancellable = true)
    public void render(Minecraft minecraft, PoseStack guiGraphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (!EmbPlusConfig.hideJEI.get()) return;

        String value = textFieldFilter.getValue();
        if (value.isEmpty()) {
            if (guiProperties != null) {
                configButton.draw(guiGraphics, mouseX, mouseY, partialTicks);
            }
            ci.cancel();
        }
    }

    @Inject(method = "drawTooltips", at = @At(value = "HEAD"), cancellable = true)
    public void render(Minecraft minecraft, PoseStack guiGraphics, int mouseX, int mouseY, CallbackInfo ci) {
        if (!EmbPlusConfig.hideJEI.get()) return;

        String value = textFieldFilter.getValue();
        if (value.isEmpty()) {
            ci.cancel();
        }
    }
}