package com.seacroak.duck.block;

import com.mojang.serialization.MapCodec;
import com.seacroak.duck.util.VoxelShapeUtils;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class DuckDispenser extends HorizontalFacingBlock implements Waterloggable {
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

  public DuckDispenser(Settings settings) {
    super(settings);
    setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));

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
