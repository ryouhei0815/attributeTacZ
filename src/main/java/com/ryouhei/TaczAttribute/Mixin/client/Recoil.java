package com.ryouhei.TaczAttribute.Mixin.client;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.client.event.CameraSetupEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(value = CameraSetupEvent.class, remap = false)
public abstract class Recoil {
    @ModifyArg(method = "initialCameraRecoil",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunRecoil;genPitchSplineFunction(F)Lorg/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction;"))
    private static float scalePitch(float modifier) { return modifier * tacz_attribute$value(true); }

    @ModifyArg(method = "initialCameraRecoil",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/GunRecoil;genYawSplineFunction(F)Lorg/apache/commons/math3/analysis/polynomials/PolynomialSplineFunction;"))
    private static float scaleYaw(float modifier) { return modifier * tacz_attribute$value(false); }

    @Unique
    private static float tacz_attribute$value(boolean pitch) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return 1;
        float scale = 1;
        if (player.getAttribute(ModAttributes.RECOIL_SCALE.get()) != null)
            scale = (float) player.getAttributeValue(ModAttributes.RECOIL_SCALE.get());
        var axis = pitch ? ModAttributes.PITCH_SCALE.get() : ModAttributes.YAW_SCALE.get();
        if (player.getAttribute(axis) != null)
            scale *= (float) player.getAttributeValue(axis);
        return scale;
    }
}
