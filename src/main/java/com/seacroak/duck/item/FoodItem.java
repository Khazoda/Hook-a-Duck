package com.seacroak.duck.item;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class FoodItem extends Item {
    private final Item itemReturned;
    public FoodItem(Settings settings,Item itemReturned) {
        super(settings);
        this.itemReturned = itemReturned;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (stack.isEmpty()) {
            return new ItemStack(itemReturned);
        } else {
            if (user instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) user;
                if (!playerEntity.getAbilities().creativeMode) {
                    ItemStack itemStack = new ItemStack(itemReturned);
                    if (!playerEntity.getInventory().insertStack(itemStack)) {
                        playerEntity.dropItem(itemStack, false);
                    }
                }
            }

            return stack;
        }

    }
}