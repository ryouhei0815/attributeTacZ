package com.ryouhei.TaczAttribute;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Main.MOD_ID);

    public static final RegistryObject<Attribute> AUTO_SHOOT_RPM = ATTRIBUTES.register(
            "auto_rpm",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.auto_rpm",
                    1.0D, 0.0D, 100.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> SEMI_SHOOT_RPM = ATTRIBUTES.register(
            "semi_rpm",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.semi_rpm",
                    1.0D, 0.0D, 100.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> BASE_DAMAGE = ATTRIBUTES.register(
            "base_damage",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.base_damage",
                    0.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> SCALE_DAMAGE = ATTRIBUTES.register(
            "scale_damage",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.scale_damage",
                    1.0D, 0.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> ADD_MAX_AMMO = ATTRIBUTES.register(
            "add_ammo",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.add_ammo",
                    0.0D, -1024.0D, 9999.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> SCALE_MAX_AMMO = ATTRIBUTES.register(
            "scale_ammo",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.scale_ammo",
                    1.0D, 0.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> KNOCKBACK = ATTRIBUTES.register(
            "base_knockback",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.base_knockback",
                    0.0D, 0.0D, 1024.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> ADD_HEADSHOT = ATTRIBUTES.register(
            "add_headshot",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.add_headshot",
                    0.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> ARMOR_PIERCE = ATTRIBUTES.register(
            "armor_pierce",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.armor_pierce",
                    0.0D, -1.0D, 1.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> RELOAD_TIME = ATTRIBUTES.register(
            "reload",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.reload",
                    1.0D, 0.5D, 2.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> BULLET_SPEED = ATTRIBUTES.register(
            "bullet_speed",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.bullet_speed",
                    1.0D, 0.0D, 100.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> BULLET_GRAVITY = ATTRIBUTES.register(
            "bullet_gravity",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.bullet_gravity",
                    1.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> BULLET_FRICTION = ATTRIBUTES.register(
            "bullet_friction",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.bullet_friction",
                    1.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> GUN_WEIGHT = ATTRIBUTES.register(
            "gun_weight",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.gun_weight",
                    1.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> BULLET_LIFE = ATTRIBUTES.register(
            "bullet_life",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.bullet_life",
                    1.0D, 0.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> INACCURACY = ATTRIBUTES.register(
            "inaccuracy",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.inaccuracy",
                    1.0D, 0.0D, 1024.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> PITCH_SCALE = ATTRIBUTES.register(
            "pitch",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.pitch",
                    1.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> YAW_SCALE = ATTRIBUTES.register(
            "yaw",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.yaw",
                    1.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );
    public static final RegistryObject<Attribute> RECOIL_SCALE = ATTRIBUTES.register(
            "recoil",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.recoil",
                    1.0D, -1024.0D, 1024.0D
            ).setSyncable(true)
    );

    public static final RegistryObject<Attribute> LIFE_STEEL = ATTRIBUTES.register(
            "bullet_life_steel",
            () -> new RangedAttribute(
                    "attribute.tacz_attribute.bullet_life_steel",
                    0.0D, 0.0D, 1024.0D
            ).setSyncable(true)
    );
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {
        @SubscribeEvent
        public static void addAttributes(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, SCALE_DAMAGE.get());
            event.add(EntityType.PLAYER, BASE_DAMAGE.get());
            event.add(EntityType.PLAYER, KNOCKBACK.get());
            event.add(EntityType.PLAYER, AUTO_SHOOT_RPM.get());
            event.add(EntityType.PLAYER, SEMI_SHOOT_RPM.get());
            event.add(EntityType.PLAYER, RELOAD_TIME.get());
            event.add(EntityType.PLAYER, ADD_HEADSHOT.get());
            event.add(EntityType.PLAYER, ARMOR_PIERCE.get());
            event.add(EntityType.PLAYER, BULLET_SPEED.get());
            event.add(EntityType.PLAYER, BULLET_LIFE.get());
            event.add(EntityType.PLAYER, SCALE_MAX_AMMO.get());
            event.add(EntityType.PLAYER, ADD_MAX_AMMO.get());
            event.add(EntityType.PLAYER, BULLET_GRAVITY.get());
            event.add(EntityType.PLAYER, BULLET_FRICTION.get());
            event.add(EntityType.PLAYER, GUN_WEIGHT.get());
            event.add(EntityType.PLAYER, INACCURACY.get());
            event.add(EntityType.PLAYER,LIFE_STEEL.get());

            event.add(EntityType.PLAYER, PITCH_SCALE.get());
            event.add(EntityType.PLAYER, YAW_SCALE.get());
            event.add(EntityType.PLAYER, RECOIL_SCALE.get());
        }
    }
}