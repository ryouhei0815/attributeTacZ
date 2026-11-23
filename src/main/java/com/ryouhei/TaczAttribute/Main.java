package com.ryouhei.TaczAttribute;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod("tacz_attribute")
public class Main {
public static final String MOD_ID = "tacz_attribute";
public static final Logger LOGGER = LogUtils.getLogger();

public Main() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    MinecraftForge.EVENT_BUS.register(this);
    ModAttributes.ATTRIBUTES.register(bus);
}




    //ammo life @
    //ammo speed @
    //rpm @
    //base damage @
    //scale damage @
    //life steel
    //headshot damage @
    //knock back @
    //damage piace @
    //weight @
    //jump scale @
    //ammo base @
    //ammo scale @
    //suihei @
    //suityoku @
    //ads

}