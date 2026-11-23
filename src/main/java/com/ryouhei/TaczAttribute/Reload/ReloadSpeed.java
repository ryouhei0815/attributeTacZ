package com.ryouhei.TaczAttribute.Reload;

import com.ryouhei.TaczAttribute.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public final class ReloadSpeed {
    public static final double MIN_SPEED = 0.05D;
    public static final double MAX_SPEED = 20.0D;
    private ReloadSpeed() {}

    public static double get(LivingEntity entity) {
        if (entity == null) return 1.0D;
        AttributeInstance instance = entity.getAttribute(ModAttributes.RELOAD_TIME.get());
        if (instance == null) return 1.0D;
        double value = instance.getValue();
        if (!Double.isFinite(value)) return 1.0D;
        return Math.max(MIN_SPEED, Math.min(MAX_SPEED, value));
    }
}
