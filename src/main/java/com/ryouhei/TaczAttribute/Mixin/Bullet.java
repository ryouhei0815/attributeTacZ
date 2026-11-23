package com.ryouhei.TaczAttribute.Mixin;


import com.ryouhei.TaczAttribute.ModAttributes;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.resource.pojo.data.gun.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = EntityKineticBullet.class,remap = false)
public abstract class Bullet extends Projectile{

    @Shadow private boolean explosion;

    protected Bullet(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * @author
     * @reason
     */
    @Inject(method = "getDamage", at = @At("RETURN"), cancellable = true)
    public void getDamages(Vec3 hitVec, CallbackInfoReturnable<Float> cir) {
        float add = 0;
        float scale = 1;
        if (this.getOwner()!=null){
            if(this.getOwner() instanceof LivingEntity entity){
                if(entity.getAttribute(ModAttributes.BASE_DAMAGE.get())!=null){
                    add = (float) entity.getAttributeValue(ModAttributes.BASE_DAMAGE.get());
                }

                if(entity.getAttribute(ModAttributes.SCALE_DAMAGE.get())!=null){
                    scale = (float) entity.getAttributeValue(ModAttributes.SCALE_DAMAGE.get());
                }
            }
        }
        float damage = cir.getReturnValue();
        damage = Math.max(((damage+add)*scale), 0.0F);
       cir.setReturnValue(damage);

    }

    @Shadow
    private float knockback;

    @Shadow
    private float headShot;
    @Shadow private float armorIgnore;

    @Shadow private int life;

    @Shadow private float gravity;

    @Shadow private float friction;
    @Shadow private float explosionDamage;

    @Shadow private int explosionDelayCount;

    /**
     * EntityKineticBullet の protected コンストラクタの最後にコードを挿入
     */
    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ZLcom/tacz/guns/resource/pojo/data/gun/GunData;Lcom/tacz/guns/resource/pojo/data/gun/BulletData;)V", at = @At("RETURN"))
    private void onEntityKineticBulletConstructed(EntityType<? extends Projectile> type, Level worldIn, LivingEntity throwerIn, ItemStack gunItem, ResourceLocation ammoId, ResourceLocation gunId, ResourceLocation gunDisplayId, boolean isTracerAmmo, GunData gunData, BulletData bulletData, CallbackInfo ci) {

        EntityKineticBullet bullet = (EntityKineticBullet) (Object) this;
        if(this.getOwner() instanceof LivingEntity livingEntity){
            if(livingEntity.getAttribute(ModAttributes.ADD_HEADSHOT.get())!=null){
                this.headShot += (float) livingEntity.getAttributeValue(ModAttributes.ADD_HEADSHOT.get());
            }
            if(livingEntity.getAttribute(ModAttributes.ARMOR_PIERCE.get())!=null){
                this.armorIgnore = (float) (this.armorIgnore + livingEntity.getAttributeValue(ModAttributes.ARMOR_PIERCE.get()));
            }
            if(livingEntity.getAttribute(ModAttributes.KNOCKBACK.get())!=null){
                this.knockback += (float) livingEntity.getAttributeValue(ModAttributes.KNOCKBACK.get());
            }
            if(livingEntity.getAttribute(ModAttributes.BULLET_FRICTION.get())!=null){
                float g = (float) (livingEntity.getAttributeValue(ModAttributes.BULLET_FRICTION.get()));
                this.friction *= g;
            }
            if(livingEntity.getAttribute(ModAttributes.BULLET_GRAVITY.get())!=null){
                float g = (float) (livingEntity.getAttributeValue(ModAttributes.BULLET_GRAVITY.get()));
                this.gravity *= g;
            }
            if(livingEntity.getAttribute(ModAttributes.BULLET_LIFE.get())!=null){
                this.life = (int) (this.life * livingEntity.getAttributeValue(ModAttributes.BULLET_LIFE.get()));
                this.life = Mth.clamp(life, 1, Integer.MAX_VALUE);
            }


        }
    }

    /**
     * @author
     * @reason
     */
    @Inject(method = "tacAttackEntity", at = @At("TAIL"))
    private void lifeSteal(EntityKineticBullet.MaybeMultipartEntity parts, float damage, Pair<DamageSource, DamageSource> sources, CallbackInfo ci) {
        if (sources.getLeft().getEntity() instanceof LivingEntity entity && entity.getAttribute(ModAttributes.LIFE_STEEL.get()) != null) {
            entity.heal((float) (damage * entity.getAttributeValue(ModAttributes.LIFE_STEEL.get())));
        }
    }
}

