// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.seacroak.duck.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class DuckEntityModel extends EntityModel<Entity> {
  private final ModelPart bb_main;

  public DuckEntityModel(ModelPart root) {
    this.bb_main = root.getChild("bb_main");
  }

  public static TexturedModelData getTexturedModelData() {
    ModelData modelData = new ModelData();
    ModelPartData modelPartData = modelData.getRoot();
    ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F))
        .uv(0, 10).cuboid(-4.0F, -8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
        .uv(12, 10).cuboid(-2.0F, -3.0F, 3.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
        .uv(0, 0).cuboid(-3.5F, -7.5F, 1.25F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
        .uv(0, 3).cuboid(-2.0F, -10.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
        .uv(0, 10).cuboid(-1.0F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

    ModelPartData Beak_r1 = bb_main.addChild("Beak_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -5.0F, -0.5F, 0.0F, 0.0F, 0.1309F));

    ModelPartData Wing_r1 = bb_main.addChild("Wing_r1", ModelPartBuilder.create().uv(12, 10).cuboid(-2.0F, -1.0F, -0.5F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -3.5F, 0.0F, 3.1416F, 0.0F));

    ModelPartData Eye_r1 = bb_main.addChild("Eye_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -6.5F, -1.75F, 0.0F, 3.1416F, 1.5708F));

    ModelPartData Tail_r1 = bb_main.addChild("Tail_r1", ModelPartBuilder.create().uv(11, 13).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -0.75F, -1.5F, 0.0F, 0.0F, 0.3927F));
    return TexturedModelData.of(modelData, 32, 32);
  }

  @Override
  public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
  }


  @Override
  public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
    bb_main.render(matrices, vertexConsumer, light, overlay, color);
  }
}