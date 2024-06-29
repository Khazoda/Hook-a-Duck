package com.seacroak.duck.item;

import com.seacroak.duck.Constants;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.Direction;

public class DispensableSpawnEggItem extends SpawnEggItem {
  public DispensableSpawnEggItem(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Settings settings) {
    super(type, primaryColor, secondaryColor, settings);

    DispenserBlock.registerBehavior(
        this,
        (pointer, stack) -> {
          Direction direction = pointer.state().get(DispenserBlock.FACING);
          EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getEntityType(stack);
          try {
            entitytype.spawnFromItemStack(pointer.world(), stack, null, pointer.pos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            stack.decrement(1);
          } catch (Exception e) {
            Constants.DUCK_LOGGER.error("Error while dispensing duck from dispenser at {}", pointer.pos(), e);
            return stack;
          }
          return stack;
        });
  }
}
