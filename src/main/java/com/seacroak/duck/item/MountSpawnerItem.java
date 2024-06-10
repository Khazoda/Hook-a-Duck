package com.seacroak.duck.item;

import com.seacroak.duck.entity.DuckMountEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

public class MountSpawnerItem<T extends Entity> extends Item {
  private final EntityType<T> entityType;

  public MountSpawnerItem(EntityType<T> entityType, Settings settings) {
    super(settings);
    this.entityType = entityType;
  }


  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    Vec3d hitpos = context.getHitPos();
    DuckMountEntity duck = (DuckMountEntity) entityType.create(context.getWorld());
    if (duck instanceof DuckMountEntity) {
      duck.setPosition(hitpos.getX(), hitpos.getY(), hitpos.getZ());
      if (!context.getWorld().isClient()) {
        context.getWorld().spawnEntity(duck);
        context.getStack().decrement(1);
        return ActionResult.SUCCESS;
      }
    }
    return super.useOnBlock(context);
  }
}
