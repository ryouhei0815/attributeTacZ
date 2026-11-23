package com.ryouhei.TaczAttribute.Mixin.client;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.gameplay.LocalPlayerDataHolder;
import com.tacz.guns.client.gameplay.LocalPlayerShoot;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.time.GunTime;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = LocalPlayerShoot.class, remap = false)
public abstract class LocalShoot {

    @Shadow @Final private LocalPlayerDataHolder data;
    @Shadow @Final private LocalPlayer player;

    @Inject(method = "getCoolDown", at = @At("HEAD"), cancellable = true)
    private void burstCoolDown(IGun iGun, ItemStack mainHandItem, GunData gunData, CallbackInfoReturnable<Long> cir) {
        if (iGun.getFireMode(mainHandItem) != FireMode.BURST) return;
        if (this.player.getAttribute(ModAttributes.SEMI_SHOOT_RPM.get()) == null) return;
        float f = (float) this.player.getAttributeValue(ModAttributes.SEMI_SHOOT_RPM.get());
        if (f <= 0) return;
        long coolDown = (long) (gunData.getBurstData().getMinInterval() * 1000f / f) - (GunTime.nowMillis() - data.clientShootTimestamp);
        cir.setReturnValue(Math.max(coolDown, 0));
    }
}

