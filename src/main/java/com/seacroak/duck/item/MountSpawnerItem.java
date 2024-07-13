package com.seacroak.duck.item;

import com.seacroak.duck.entity.DuckMountEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MountSpawnerItem<T extends Entity> extends Item {
  private final EntityType<T> entityType;

  public MountSpawnerItem(EntityType<T> entityType, Settings settings) {
    super(settings);
    this.entityType = entityType;
  }

  @Override
  public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    if (!user.canModifyBlocks()) {
      if (world instanceof ServerWorld serverWorld) {
        entityType.spawn(serverWorld, user.getBlockPos().up(), SpawnReason.SPAWN_EGG);
        user.getStackInHand(hand).decrement(1);
      }
    }
    return super.use(world, user, hand);
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    if (context.getPlayer() == null) return ActionResult.PASS;
    if (context.getPlayer().canModifyBlocks()) {
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
    }
    return super.useOnBlock(context);
  }
}
