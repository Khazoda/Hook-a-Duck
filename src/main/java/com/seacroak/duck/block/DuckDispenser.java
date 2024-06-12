package com.seacroak.duck.block;

import com.mojang.serialization.MapCodec;
import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.networking.DuckNetworking;
import com.seacroak.duck.networking.SoundPayload;
import com.seacroak.duck.networking.SoundPayloadPlayerless;
import com.seacroak.duck.registry.MainRegistry;
import com.seacroak.duck.registry.SoundRegistry;
import com.seacroak.duck.util.VoxelShapeUtils;
import gay.lemmaeof.terrifictickets.TerrificTickets;
import gay.lemmaeof.terrifictickets.api.TerrificTicketsApi;
import net.minecraft.block.*;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DuckDispenser extends HorizontalFacingBlock implements Waterloggable {
  public static final BooleanProperty ON_COOLDOWN = BooleanProperty.of("on_cooldown");
  public static final BooleanProperty TIMER_RUNNING = BooleanProperty.of("timer_running");

  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  public DuckDispenser(Settings settings) {
    super(settings);
    setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false).with(ON_COOLDOWN, false).with(TIMER_RUNNING, false));

  }

  private void dispenseDuck(Direction facing, BlockPos pos, World world) {
    BlockPos posBuilder = BlockPos.ofFloored(
        pos.toCenterPos().getX() + (facing.getOffsetX()),
        pos.toCenterPos().getY(),
        pos.toCenterPos().getZ() + (facing.getOffsetZ()));

    DuckEntity duck = MainRegistry.DUCK_ENTITY.spawn((ServerWorld) world,
        posBuilder,
        SpawnReason.MOB_SUMMONED);

    assert duck != null;
    duck.setVelocity(0.5 * facing.getOffsetX(), 0.2, 0.5 * facing.getOffsetZ());

    ServerWorld serverWorld = (ServerWorld) duck.getWorld();
    for (int i = 0; i < 2; i++) {
      serverWorld.spawnParticles(MainRegistry.DUCKS, duck.getX(), duck.getY() + 0.1, duck.getZ(), (int) (1.0F + duck.getWidth() * 20.0F), duck.getWidth() / 5, 0.0, duck.getWidth() / 5, 0.05F);
    }
  }

  @Override
  protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (stack.isOf(TerrificTickets.TOKEN) || stack.isOf(TerrificTickets.PASSCARD)) {
      if (state.get(ON_COOLDOWN)) return ItemActionResult.CONSUME;
      if (state.get(TIMER_RUNNING)) return ItemActionResult.CONSUME;
      if (stack.isOf(TerrificTickets.PASSCARD)) {
        if (TerrificTicketsApi.getTokens(stack) <= 0) {
          return ItemActionResult.CONSUME;
        }
      }
      this.startCooldown(state, world, pos, 50);
      if (world.isClient()) {
        DuckNetworking.playSoundOnClient(SoundRegistry.PULL, world, pos, 1f, 1f);
        if (stack.isOf(TerrificTickets.TOKEN)) stack.decrement(1);
        if (stack.isOf(TerrificTickets.PASSCARD)) TerrificTicketsApi.removeTokens(stack, 1);
      } else if (world.isClient()) {
        SoundPayload.sendPlayerPacketToClients((ServerWorld) world, new SoundPayload(player, pos, SoundRegistry.PULL, 1f));
      }
      this.startTimer(state, world, pos, 40);
      return ItemActionResult.SUCCESS;
    }
    return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
  }

  @Override
  protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    super.onStateReplaced(state, world, pos, newState, moved);
  }

  public void startCooldown(BlockState state, World world, BlockPos pos, int period) {
    world.setBlockState(pos, state.with(ON_COOLDOWN, true), 3);
    this.updateNeighbors(state, world, pos);
    world.scheduleBlockTick(pos, this, period);
  }

  public void startTimer(BlockState state, World world, BlockPos pos, int period) {
    world.setBlockState(pos, state.with(TIMER_RUNNING, true), 3);
    this.updateNeighbors(state, world, pos);
    world.scheduleBlockTick(pos, this, period);
  }

  public void scheduledTick(BlockState state, ServerWorld world, BlockPos
      pos, net.minecraft.util.math.random.Random random) {

    if (state.get(TIMER_RUNNING)) {
      dispenseDuck(state.get(Properties.HORIZONTAL_FACING), pos, world);
      SoundPayloadPlayerless.sendNoPlayerPacketToClients(world, pos, "duck:squeak", 1f);
      SoundPayloadPlayerless.sendNoPlayerPacketToClients(world, pos, "duck:trumpet", 1f);

      world.setBlockState(pos, state.with(TIMER_RUNNING, false), 3);
    }
    if (state.get(ON_COOLDOWN)) {
      world.setBlockState(pos, state.with(ON_COOLDOWN, false), 3);
    }
    this.updateNeighbors(state, world, pos);
  }

  @Override
  protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
    return null;
  }

  private void updateNeighbors(BlockState state, World world, BlockPos pos) {
    world.updateNeighborsAlways(pos, this);
  }

  public VoxelShape getShape() {
    VoxelShape shape = VoxelShapes.empty();
    shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
    return shape;
  }

  final VoxelShape blockShape = getShape();
  final VoxelShape[] blockShapes = VoxelShapeUtils.calculateBlockShapes(blockShape);

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    Direction direction = state.get(FACING);
    return VoxelShapeUtils.getSidedOutlineShape(direction, blockShape, blockShapes);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  // Initial state upon placing
  @Override
  public BlockState getPlacementState(ItemPlacementContext context) {
    return this.getDefaultState().with(Properties.HORIZONTAL_FACING, context.getHorizontalPlayerFacing().getOpposite())
        .with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).isOf(Fluids.WATER));
  }

  /* Waterlogging */
  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

  // Append initial properties
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(ON_COOLDOWN, TIMER_RUNNING, FACING, WATERLOGGED);
  }
}
