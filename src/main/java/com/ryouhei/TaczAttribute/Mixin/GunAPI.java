package com.ryouhei.TaczAttribute.Mixin;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.InaccuracyType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = ModernKineticGunScriptAPI.class,remap = false)
public abstract class GunAPI {

    @Shadow
    private LivingEntity shooter;
    @Shadow
    private ShooterDataHolder dataHolder;

    @Shadow
    public abstract int getMaxAmmoCount();


    /**
     * @author
     * @reason
     */
    @Inject(method = "getMaxAmmoCount", at = @At("RETURN"), cancellable = true)
    public void getMaxAmmoCount(CallbackInfoReturnable<Integer> cir) {
        if (shooter.getAttribute(ModAttributes.ADD_MAX_AMMO.get()) != null && shooter.getAttribute(ModAttributes.SCALE_MAX_AMMO.get()) != null) {
            int ammo = cir.getReturnValue();
            int add = (int) shooter.getAttributeValue(ModAttributes.ADD_MAX_AMMO.get());
            float scale = (float) shooter.getAttributeValue(ModAttributes.SCALE_MAX_AMMO.get());

            int maxAmmo = (int) ((ammo + add) * scale);

            cir.setReturnValue(Math.max(maxAmmo, 0));
        }

    }

    /**
     * @author
     * @reason
     */
    @Redirect(method = {"getNeededAmmoAmount", "putAmmoInMagazine"},
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/util/AttachmentDataUtils;getAmmoCountWithAttachment(Lnet/minecraft/world/item/ItemStack;Lcom/tacz/guns/resource/pojo/data/gun/GunData;)I"))
    private int maxAmmoWithAttribute(ItemStack gunItem, GunData gunData) {
        return getMaxAmmoCount();
    }

    @Redirect(method = "shootOnce",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/resource/pojo/data/gun/InaccuracyType;getInaccuracyType(Lnet/minecraft/world/entity/LivingEntity;)Lcom/tacz/guns/resource/pojo/data/gun/InaccuracyType;"))
    private InaccuracyType airborneInaccuracy(LivingEntity living) {
        if (!living.onGround()) return InaccuracyType.MOVE;   // 空中は常に移動時の拡散（従来どおり）
        return InaccuracyType.getInaccuracyType(living);
    }

    @ModifyArg(method = "shootOnce", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 1)
    private float scaleInaccuracy(float inaccuracy) {
        if (shooter.getAttribute(ModAttributes.INACCURACY.get()) != null)
            inaccuracy *= (float) shooter.getAttributeValue(ModAttributes.INACCURACY.get());
        return inaccuracy;
    }

    @ModifyArg(method = "shootOnce", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", remap = true), index = 0)
    private float scaleBulletSpeed(float speed) {
        if (shooter.getAttribute(ModAttributes.BULLET_SPEED.get()) != null)
            speed *= (float) shooter.getAttributeValue(ModAttributes.BULLET_SPEED.get());
        return speed;
    }
}
