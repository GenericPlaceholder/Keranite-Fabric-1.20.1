package net.generic404_.keranite.enchantment;

import net.generic404_.keranite.effect.ModEffects;
import net.generic404_.keranite.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;

import java.util.Objects;

public class Conductor extends Enchantment {
    public Conductor() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }

    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(user.getMainHandStack().getItem()==ModItems.KERANITE_BROADSWORD&& Objects.requireNonNull(user.getMainHandStack().getNbt()).getBoolean("charged")){
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(ModEffects.CHARGED, 100, 0));
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(ModEffects.SHOCKED, 30, 0));
                user.getMainHandStack().getOrCreateNbt().putBoolean("charged",false);
                user.getMainHandStack().getOrCreateNbt().putInt("meter", 0);
                user.getMainHandStack().getOrCreateNbt().putInt("CustomModelData", 0);
            }
        }
        int maxmeter = 20;
        int meter = Objects.requireNonNull(user.getMainHandStack().getNbt()).getInt("meter");
        if(Objects.requireNonNull(user.getMainHandStack().getNbt()).getInt("meter")<maxmeter){
            user.getMainHandStack().getOrCreateNbt().putInt("meter",user.getMainHandStack().getNbt().getInt("meter")+1);
        }else if(meter>=maxmeter&&meter<(maxmeter+2)){
            user.sendMessage(Text.of("[ Charge ready! ]"));
            user.getMainHandStack().getOrCreateNbt().putInt("meter",maxmeter+2);
            user.getMainHandStack().getOrCreateNbt().putInt("CustomModelData", 1);
        }
        super.onTargetDamaged(user, target, level);
    }
}
