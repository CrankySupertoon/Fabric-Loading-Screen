package com.crankysupertoon.fabricloadingscreen.mixin.client;

import com.crankysupertoon.fabricloadingscreen.client.RunArgsExtension;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RunArgs.class)
public abstract class RunArgsMixin implements RunArgsExtension {
    private String developermode_autoWorld;

    @Override
    public String developermode_getAutoWorld() {
        return developermode_autoWorld;
    }

    @Override
    public void developermode_setAutoWorld(String autoWorld) {
        this.developermode_autoWorld = autoWorld;
    }
}
