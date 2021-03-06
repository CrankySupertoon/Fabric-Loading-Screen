package com.crankysupertoon.fabricloadingscreen.mixin.client.gui.screen;

import com.crankysupertoon.fabricloadingscreen.client.FabricLoadingScreenClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.resource.ResourceReloadMonitor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SplashScreen.class)
public abstract class SplashScreenMixin extends Overlay {
    @Mutable @Shadow @Final private boolean reloading;
    @Shadow @Final private MinecraftClient client;

    @Inject(
        at = @At("RETURN"),
        method = "<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/resource/ResourceReloadMonitor;Ljava/util/function/Consumer;Z)V"
    )
    private void constructor(MinecraftClient client, ResourceReloadMonitor resourceReloadMonitor, Consumer<Optional<Throwable>> consumer, boolean reloading, CallbackInfo ci) {
        // TODO: DO we need this?
        if (FabricLoadingScreenClient.splashFadeTime <= 0) {
            // this.reloading = true;
        }
    }

    @ModifyConstant(
        constant = @Constant(
            floatValue = 1000.F
        ),
        method = "render(IIF)V"
    )
    private float modifyFadeTime1(float originalValue) {
        return FabricLoadingScreenClient.splashFadeTime / 2.0F;
    }

    @ModifyConstant(
        constant = @Constant(
            floatValue = 500.F
        ),
        method = "render(IIF)V"
    )
    private float modifyFadeTime2(float originalValue) {
        return FabricLoadingScreenClient.splashFadeTime / 4.0F;
    }

    @ModifyArg(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/SplashScreen;fill(IIIII)V"
        ),
        method = "render(IIF)V",
        index = 4
    )
    private int backgroundColor(int x, int y, int width, int height, int color) {
        // In case someone else injects drawRect calls in this method
        // We'll assume it's drawing a background if drawRect covers the whole screen exactly
        if (x == 0 && y == 0 && width == client.getWindow().getScaledWidth() && height == client.getWindow().getScaledHeight()) {
            return FabricLoadingScreenClient.theme.getBackgroundColor(color);
        } else {
            return color;
        }
    }

    @ModifyArg(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/SplashScreen;fill(IIIII)V",
            ordinal = 0
        ),
        method = "renderProgressBar(IIIIF)V",
        index = 4
    )
    private int progressBarOutlineColor(int i) {
        return FabricLoadingScreenClient.theme.getProgressBarOutlineColor(i);
    }

    @ModifyArg(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/SplashScreen;fill(IIIII)V",
            ordinal = 1
        ),
        method = "renderProgressBar(IIIIF)V",
        index = 4
    )
    private int progressBarBackgroundColor(int i) {
        return FabricLoadingScreenClient.theme.getProgressBarBackgroundColor(i);
    }

    @ModifyArg(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screen/SplashScreen;fill(IIIII)V",
            ordinal = 2
        ),
        method = "renderProgressBar(IIIIF)V",
        index = 4
    )
    private int progressBarFillColor(int i) {
        return FabricLoadingScreenClient.theme.getProgressBarFillColor(i);
    }
}
