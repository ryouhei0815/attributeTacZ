package com.ryouhei.TaczAttribute.Mixin;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.item.ModernKineticGunItem;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.GunReloadData;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(value = ModernKineticGunItem.class,remap = false)
public abstract class Gun {

    /**
     * @author
     * @reason
     */

    @Overwrite
    private ReloadState defaultTickReload(ModernKineticGunScriptAPI api) {
        CommonGunIndex gunIndex = api.getGunIndex();
        GunData gunData = gunIndex.getGunData();
        GunReloadData reloadData = gunData.getReloadData();
        ReloadState.StateType oldStateType = ReloadState.StateType.values()[api.getReloadStateType()];
        long progressTime = api.getReloadTime();
        if(api.getShooter().getAttribute(ModAttributes.RELOAD_TIME.get())!=null){
            progressTime = (long) (api.getReloadTime() * api.getShooter().getAttributeValue(ModAttributes.RELOAD_TIME.get()));
        }
        long countDown;
        ReloadState.StateType stateType;
        long feedTime;
        long finishingTime;
        if (oldStateType.isReloadingEmpty()) {
            feedTime = (long)(reloadData.getFeed().getEmptyTime() * 1000.0F);
            finishingTime = (long)(reloadData.getCooldown().getEmptyTime() * 1000.0F);
            if (progressTime < feedTime) {
                stateType = ReloadState.StateType.EMPTY_RELOAD_FEEDING;
                countDown = feedTime - progressTime;
            } else if (progressTime < finishingTime) {
                stateType = ReloadState.StateType.EMPTY_RELOAD_FINISHING;
                countDown = finishingTime - progressTime;
            } else {
                stateType = ReloadState.StateType.NOT_RELOADING;
                countDown = -1L;
            }
        } else if (oldStateType.isReloadingTactical()) {
            feedTime = (long)(reloadData.getFeed().getTacticalTime() * 1000.0F);
            finishingTime = (long)(reloadData.getCooldown().getTacticalTime() * 1000.0F);
            if (progressTime < feedTime) {
                stateType = ReloadState.StateType.TACTICAL_RELOAD_FEEDING;
                countDown = feedTime - progressTime;
            } else if (progressTime < finishingTime) {
                stateType = ReloadState.StateType.TACTICAL_RELOAD_FINISHING;
                countDown = finishingTime - progressTime;
            } else {
                stateType = ReloadState.StateType.NOT_RELOADING;
                countDown = -1L;
            }
        } else {
            stateType = ReloadState.StateType.NOT_RELOADING;
            countDown = -1L;
        }

        if (oldStateType == ReloadState.StateType.EMPTY_RELOAD_FEEDING && oldStateType != stateType) {
            this.defaultReloadFinishing(api, false);
        }

        if (oldStateType == ReloadState.StateType.TACTICAL_RELOAD_FEEDING && oldStateType != stateType) {
            this.defaultReloadFinishing(api, true);
        }

        ReloadState reloadState = new ReloadState();
        reloadState.setStateType(stateType);
        reloadState.setCountDown(countDown);
        return reloadState;
    }

    @Shadow
    private void defaultReloadFinishing(ModernKineticGunScriptAPI api, boolean isTactical) {}





    @Shadow
    protected abstract LuaFunction checkFunction(LuaValue luaValue) ;
}
