package me.srrapero720.embeddiumplus;

import com.mojang.blaze3d.platform.Window;
import me.srrapero720.embeddiumplus.mixins.impl.borderless.MainWindowAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public class EmbPlusAPI {
    public static void setFullScreenMode(Options opts, EmbPlusConfig.FullScreenMode value) {
        EmbPlusConfig.fullScreenMode.set(value);
        opts.fullscreen = value != EmbPlusConfig.FullScreenMode.WINDOWED;

        Minecraft client = Minecraft.getInstance();
        Window window = client.getWindow();
        if (window.isFullscreen() != opts.fullscreen) {
            window.toggleFullScreen();
            opts.fullscreen = window.isFullscreen();
        }

        if (opts.fullscreen) {
            ((MainWindowAccessor) (Object) window).setDirty(true);
            window.changeFullscreenVideoMode();
        }
    }
}
