package com.ryouhei.TaczAttribute.Mixin;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.entity.shooter.LivingEntitySpeedModifier;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.WeightModifier;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = LivingEntitySpeedModifier.class, remap = false)
public abstract class SpeedWeight {

    @Shadow
    @Final
    private LivingEntity shooter;

    @Unique
    private double tacz_attribute$weight() {
        if (this.shooter.getAttribute(ModAttributes.GUN_WEIGHT.get()) != null) {
            return this.shooter.getAttributeValue(ModAttributes.GUN_WEIGHT.get());
        }
        return 1;
    }

    // 重量による減速（weight_modifier）に gun_weight を掛ける
    @Redirect(method = "updateSpeedModifier",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/modifier/AttachmentCacheProperty;getCache(Ljava/lang/String;)Ljava/lang/Object;"))
    private Object scaleWeight(AttachmentCacheProperty cacheProperty, String id) {
        Object value = cacheProperty.getCache(id);
        if (WeightModifier.ID.equals(id) && value instanceof Float weight) {
            return (float) (weight * tacz_attribute$weight());
        }
        return value;
    }

    // 銃ごとの移動速度補正（movement_speed）にも gun_weight を掛ける
    @Inject(method = "getTargetSpeed", at = @At("RETURN"), cancellable = true)
    private void scaleExtraSpeed(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(cir.getReturnValue() * tacz_attribute$weight());
    }
}