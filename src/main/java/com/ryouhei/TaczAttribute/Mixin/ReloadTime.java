package com.ryouhei.TaczAttribute.Mixin;

import com.ryouhei.TaczAttribute.Reload.ReloadSpeed;
import com.ryouhei.TaczAttribute.Reload.ReloadSpeedHolder;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.util.time.GunTime;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModernKineticGunScriptAPI.class, remap = false)
public abstract class ReloadTime {
    @Shadow
    private ShooterDataHolder dataHolder;
    @Shadow private LivingEntity shooter;

    // リロード開始時点の速度で固定（途中で属性が変わっても進行度が飛ばない）
    @Unique
    private double tacz_attribute$speed() {
        long timestamp = this.dataHolder.reloadTimestamp;
        ReloadSpeedHolder holder = (ReloadSpeedHolder) this.dataHolder;
        if (holder.tacz_attribute$getReloadKey() != timestamp) {
            holder.tacz_attribute$setReloadSpeed(ReloadSpeed.get(this.shooter));
            holder.tacz_attribute$setReloadKey(timestamp);
        }
        return holder.tacz_attribute$getReloadSpeed();
    }

    @Inject(method = "getReloadTime", at = @At("HEAD"), cancellable = true)
    private void tacz_attribute$getReloadTime(CallbackInfoReturnable<Long> cir) {
        if (this.dataHolder == null || this.dataHolder.reloadTimestamp == -1) return;
        double speed = tacz_attribute$speed();
        if (speed == 1.0D) return;
        long realElapsed = GunTime.nowMillis() - this.dataHolder.reloadTimestamp;
        cir.setReturnValue((long) (realElapsed * speed));
    }

    @Inject(method = "adjustReloadTime", at = @At("HEAD"), cancellable = true)
    private void tacz_attribute$adjustReloadTime(long alpha, CallbackInfo ci) {
        if (this.dataHolder == null || this.dataHolder.reloadTimestamp == -1) return;
        double speed = tacz_attribute$speed();
        if (speed == 1.0D) return;
        this.dataHolder.reloadTimestamp -= Math.round(alpha / speed);
        ((ReloadSpeedHolder) this.dataHolder).tacz_attribute$setReloadKey(this.dataHolder.reloadTimestamp);
        ci.cancel();
    }
}
