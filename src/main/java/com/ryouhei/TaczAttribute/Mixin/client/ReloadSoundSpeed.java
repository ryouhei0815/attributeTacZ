package com.ryouhei.TaczAttribute.Mixin.client;

import com.ryouhei.TaczAttribute.Reload.ReloadSpeed;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.sound.SoundPlayManager;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SoundPlayManager.class, remap = false)
public abstract class ReloadSoundSpeed {

    @Unique
    private static float tacz_attribute$reloadPitch = 1.0F;

    @Inject(method = "playReloadSound", at = @At("HEAD"))
    private static void beginReloadSound(LivingEntity entity, GunDisplayInstance display, boolean noAmmo, CallbackInfo ci) {
        tacz_attribute$reloadPitch = (float) ReloadSpeed.get(entity);
    }

    @Inject(method = "playReloadSound", at = @At("RETURN"))
    private static void endReloadSound(LivingEntity entity, GunDisplayInstance display, boolean noAmmo, CallbackInfo ci) {
        tacz_attribute$reloadPitch = 1.0F;
    }

    // 銃パックの pitch 指定やランダム幅を反映した後の値に倍率を掛ける
    @ModifyArg(method = "playClientSound(Lnet/minecraft/world/entity/Entity;Lcom/tacz/guns/client/resource/pojo/display/SoundData;FFIZIZZ)Lcom/tacz/guns/client/sound/GunSoundInstance;",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/client/sound/SoundPlayManager;playClientSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/resources/ResourceLocation;FFIZIZZ)Lcom/tacz/guns/client/sound/GunSoundInstance;"),
            index = 3)
    private static float scaleReloadPitch(float pitch) {
        return pitch * tacz_attribute$reloadPitch;
    }
}
