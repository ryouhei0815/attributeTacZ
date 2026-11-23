package com.ryouhei.TaczAttribute.Mixin;

import com.ryouhei.TaczAttribute.Reload.ReloadSpeedHolder;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ShooterDataHolder.class, remap = false)
public abstract class ShooterDataHolderMixin implements ReloadSpeedHolder {
    @Unique private double tacz_attribute$reloadSpeed = 1.0D;
    @Unique private long tacz_attribute$reloadKey = Long.MIN_VALUE;

    @Override public double tacz_attribute$getReloadSpeed() { return this.tacz_attribute$reloadSpeed; }
    @Override public void tacz_attribute$setReloadSpeed(double speed) { this.tacz_attribute$reloadSpeed = speed; }
    @Override public long tacz_attribute$getReloadKey() { return this.tacz_attribute$reloadKey; }
    @Override public void tacz_attribute$setReloadKey(long key) { this.tacz_attribute$reloadKey = key; }
}
