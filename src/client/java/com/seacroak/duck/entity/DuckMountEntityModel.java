// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.seacroak.duck.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class DuckMountEntityModel<T extends DuckMountEntity> extends SinglePartEntityModel<DuckMountEntity> {
	private final ModelPart Base;
	private final ModelPart bb_main;

	public DuckMountEntityModel(ModelPart root) {
		super();
		this.Base = root.getChild("Base");
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Base = modelPartData.addChild("Base", ModelPartBuilder.create().uv(-40, -26).cuboid(1.0F, 0.0F, -1.0F, 16.0F, 3.0F, 28.0F, new Dilation(0.0F))
				.uv(-16, 0).cuboid(0.0F, -6.0F, 27.0F, 18.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(-16, 0).cuboid(0.0F, -6.0F, -3.0F, 18.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(-26, -26).cuboid(17.0F, -6.0F, -1.0F, 2.0F, 6.0F, 28.0F, new Dilation(0.0F))
				.uv(-26, -26).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 28.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 21.0F, -13.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(-10, -5).cuboid(-3.5F, -20.0F, -21.0F, 7.0F, 7.0F, 7.0F, new Dilation(0.0F))
				.uv(-6, -1).cuboid(2.75F, -19.0F, -20.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(-6, -1).cuboid(-3.75F, -19.0F, -20.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(-10, -10).cuboid(10.0F, -10.0F, -5.0F, 2.0F, 7.0F, 12.0F, new Dilation(0.0F))
				.uv(-10, -10).cuboid(-12.0F, -10.0F, -5.0F, 2.0F, 7.0F, 12.0F, new Dilation(0.0F))
				.uv(-19, -4).cuboid(-8.0F, -9.0F, 8.0F, 16.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(-19, -4).cuboid(-8.0F, -5.0F, 2.0F, 16.0F, 2.0F, 6.0F, new Dilation(0.0F))
				.uv(-19, -4).cuboid(-2.0F, -4.0F, -11.25F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData LeverTop_r1 = bb_main.addChild("LeverTop_r1", ModelPartBuilder.create().uv(-20, -5).cuboid(-2.0F, -1.0F, -3.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -5.0F, -6.25F, 0.9163F, 0.0F, 0.0F));

		ModelPartData LeftFootRest_r1 = bb_main.addChild("LeftFootRest_r1", ModelPartBuilder.create().uv(-19, -4).cuboid(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F))
				.uv(-19, -4).cuboid(-12.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -4.0F, -12.25F, -0.6545F, 0.0F, 0.0F));

		ModelPartData LowerBeak_r1 = bb_main.addChild("LowerBeak_r1", ModelPartBuilder.create().uv(-5, 0).cuboid(-2.5F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.05F, -15.5F, -21.5F, 0.2182F, 0.0F, 0.0F));

		ModelPartData UpperBeak_r1 = bb_main.addChild("UpperBeak_r1", ModelPartBuilder.create().uv(-5, 0).cuboid(-2.5F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.05F, -16.5F, -21.5F, -0.2182F, 0.0F, 0.0F));

		ModelPartData Neck_r1 = bb_main.addChild("Neck_r1", ModelPartBuilder.create().uv(-3, -1).cuboid(-3.0F, -5.0F, -2.0F, 6.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.3F, -15.0F, 0.1745F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		Base.render(matrices, vertices, light, overlay);
		bb_main.render(matrices, vertices, light, overlay);
	}


	@Override
	public ModelPart getPart() {
		return null;
	}

	@Override
	public void setAngles(DuckMountEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}