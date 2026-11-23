package com.ryouhei.TaczAttribute.Mixin;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = GunData.class,remap = false)
public class GunRpm {

    @Inject(method = "getShootInterval", at = @At("RETURN"), cancellable = true)
    private void onRpm(LivingEntity shooter, FireMode fireMode, ItemStack gunStack, CallbackInfoReturnable<Long> cir) {

        if(shooter.getAttribute(ModAttributes.AUTO_SHOOT_RPM.get())!=null && fireMode == FireMode.AUTO){
           long rpm = 6000 / cir.getReturnValue();
           float attributeValue = (float) shooter.getAttributeValue(ModAttributes.AUTO_SHOOT_RPM.get());
            rpm = (long) (rpm * attributeValue);
            rpm = (long) Mth.clamp(rpm, 1, 1200);
            cir.setReturnValue(6000/rpm);
        }else

        if(shooter.getAttribute(ModAttributes.SEMI_SHOOT_RPM.get())!=null && fireMode == FireMode.SEMI){
            long rpm = 6000 / cir.getReturnValue();
            float attributeValue = (float) shooter.getAttributeValue(ModAttributes.SEMI_SHOOT_RPM.get());
            rpm = (long) (rpm * attributeValue);
            rpm = (long) Mth.clamp(rpm, 1, 1200);
            cir.setReturnValue(6000/rpm);
        }
    }



}
