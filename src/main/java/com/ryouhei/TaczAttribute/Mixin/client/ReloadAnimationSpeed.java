package com.ryouhei.TaczAttribute.Mixin.client;

import com.ryouhei.TaczAttribute.Reload.ClientReloadAnimationSpeed;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.client.animation.ObjectAnimationRunner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ObjectAnimationRunner.class, remap = false)
public abstract class ReloadAnimationSpeed {
    @Shadow
    @Final
    private ObjectAnimation animation;

    @Inject(method = "elapsedSinceLastUpdate", at = @At("RETURN"), cancellable = true)
    private void tacz_attribute$scaleElapsed(CallbackInfoReturnable<Long> cir) {
        long elapsed = cir.getReturnValueJ();
        if (elapsed <= 0L) return;
        double speed = ClientReloadAnimationSpeed.getSpeed(this.animation);
        if (speed != 1.0D) cir.setReturnValue((long) (elapsed * speed));
    }
}
