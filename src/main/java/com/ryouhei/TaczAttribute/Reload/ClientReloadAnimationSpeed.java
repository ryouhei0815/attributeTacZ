package com.ryouhei.TaczAttribute.Reload;

import com.tacz.guns.api.client.animation.ObjectAnimation;
import net.minecraft.client.Minecraft;

import java.util.Locale;

public final class ClientReloadAnimationSpeed {
    private ClientReloadAnimationSpeed() {
    }

    public static double getSpeed(ObjectAnimation animation) {
        if (animation == null || animation.name == null) {
            return 1.0D;
        }
        if (!animation.name.toLowerCase(Locale.ROOT).contains("reload")) {
            return 1.0D;
        }
        return ReloadSpeed.get(Minecraft.getInstance().player);
    }
}
