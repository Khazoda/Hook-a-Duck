package com.seacroak.duck.block;

import com.mojang.serialization.MapCodec;
import com.seacroak.duck.entity.DuckEntity;
import com.seacroak.duck.networking.DuckNetworking;
import com.seacroak.duck.networking.SoundPayload;
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
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  public DuckDispenser(Settings settings) {
    super(settings);
    setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

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
      if (!world.isClient()) {
        Direction facing = state.get(Properties.HORIZONTAL_FACING);
        dispenseDuck(facing, pos, world);

        if (stack.isOf(TerrificTickets.TOKEN)) stack.decrement(1);
        if (stack.isOf(TerrificTickets.PASSCARD)) TerrificTicketsApi.removeTokens(stack, 1);
        SoundPayload.sendPlayerPacketToClients((ServerWorld) world, new SoundPayload(player, pos, SoundRegistry.SQUEAK, 1f));
        return ItemActionResult.SUCCESS;

      } else {
        DuckNetworking.playSoundOnClient(SoundRegistry.SQUEAK, world, pos, 1f, 1f);
      }
    }
    return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
  }

  @Override
  protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
    return null;
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
    builder.add(FACING, WATERLOGGED);
  }
}
