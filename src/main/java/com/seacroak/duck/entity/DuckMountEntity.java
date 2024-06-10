package com.seacroak.duck.entity;

import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.registry.SoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class DuckMountEntity extends PigEntity {

    public DuckMountEntity(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    /*Attributes*/
    public static DefaultAttributeContainer.Builder createDuckMountAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    /*on Interact*/
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        } else if (this.hasPassengers()) {
            return ActionResult.PASS;
        } else if (!this.getWorld().isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            return ActionResult.SUCCESS;
        }
    }

    /*Causes Entity to Swim while !vehicle*/
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
    }


    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        Vec2f vec2f = this.getControlledRotation(controllingPlayer);
        this.setRotation(vec2f.y, vec2f.x);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
        if (this.isLogicalSideForUpdatingMovement()) {
            if (movementInput.z <= 0.0) {
                int dummy = 0;
            }
        }

    }

    protected Vec2f getControlledRotation(LivingEntity controllingPassenger) {
        return new Vec2f(controllingPassenger.getPitch() * 0.5F, controllingPassenger.getYaw());
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        if (this.isSwimming() && !this.jumping) {
            return Vec3d.ZERO;
        } else {
            float f = controllingPlayer.sidewaysSpeed * 0.5F;
            float g = controllingPlayer.forwardSpeed;
            if (g <= 0.0F) {
                if (this.isOnGround()) {
                    g *= 0.25F;
                } else if(this.isTouchingWater()) {
                    g *= 0.55F;
                }
            }

            return new Vec3d((double) f, 0.0, (double) g);
        }
    }

    public LivingEntity getControllingPassenger() {
        if (this.hasPassengers()) {
            Entity var2 = this.getFirstPassenger();
            if (var2 instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) var2;
                return playerEntity;
            }
        }

        return super.getControllingPassenger();
    }

    @Override
    public boolean hurtByWater() {
        return false;
    }

    @Override
    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.WATER);
    }

    private void updateFloating() {
        if (this.isTouchingWater()) {
            ShapeContext shapeContext = ShapeContext.of(this);
            if (shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) && !this.getWorld().getFluidState(this.getBlockPos().up()).isIn(FluidTags.WATER)) {
                this.setOnGround(true);
            } else {
                this.setVelocity(this.getVelocity().multiply(0.5).add(0.0, 0.05, 0.0));
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.SQUEAK;
    }

    protected SoundEvent getDeathSound() {
        return SoundRegistry.SQUEAK;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.0F, 0.0F);
    }

    protected void dropInventory() {
        super.dropInventory();
        {
            this.dropItem(MainRegistry.DUCK_SWORD);
        }
    }
}

