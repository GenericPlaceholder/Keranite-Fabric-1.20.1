package net.generic404_.keranite.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class Charged extends StatusEffect {
    protected Charged(StatusEffectCategory category, int color) {
        super(category, color);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        assert entity.isAlive();
        // eventually, this effect should disable sprinting.
        // see: mixin/NoSprint.java
        entity.disablesShield();

        if(entity.isFallFlying()){
            if(entity.getVelocity().y<=0) {
                entity.setVelocity(0, entity.getVelocity().y-0.1d, 0);
                entity.fallDistance = -1f;
            }else{
                entity.setVelocity(0,0,0);
            }
        }

        if(entity.isSprinting()){
//            entity.setSprinting(false);
//            entity.setSneaking(true);
            if(entity instanceof PlayerEntity plr){
                plr.addExhaustion(0.1f);
            }
        }
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,2,0,false,false,false));
    }
}