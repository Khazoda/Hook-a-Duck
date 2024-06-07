// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.seacroak.duck.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class DuckEntityModel<T extends DuckEntity> extends SinglePartEntityModel<DuckEntity> {
  private final ModelPart bb_main;

  public DuckEntityModel(ModelPart root) {
    this.bb_main = root.getChild("bb_main");
  }

  public static TexturedModelData getTexturedModelData() {
    ModelData modelData = new ModelData();
    ModelPartData modelPartData = modelData.getRoot();
    ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

    ModelPartData Beak_r1 = bb_main.addChild("Beak_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-4.2882F, 0.1894F, -1.5F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5847F, -6.25F, 0.0F, -1.5708F, -1.4399F, 1.5708F));

    ModelPartData Wing_r1 = bb_main.addChild("Wing_r1", ModelPartBuilder.create().uv(12, 10).cuboid(-2.5847F, 2.75F, 3.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5847F, -6.25F, 0.0F, 0.0F, 1.5708F, 0.0F));

    ModelPartData Eye_r1 = bb_main.addChild("Eye_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-0.25F, 0.9153F, 1.25F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5847F, -6.25F, 0.0F, -1.5708F, 0.0F, -1.5708F));

    ModelPartData Hook2_r1 = bb_main.addChild("Hook2_r1", ModelPartBuilder.create().uv(0, 10).cuboid(-0.4153F, -4.25F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
        .uv(0, 3).cuboid(-1.4153F, -4.25F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
        .uv(0, 0).cuboid(-2.9153F, -1.75F, 1.25F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
        .uv(12, 10).cuboid(-1.4153F, 2.75F, 3.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
        .uv(0, 10).cuboid(-3.4153F, -2.25F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
        .uv(0, 0).cuboid(-2.4153F, 1.75F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5847F, -6.25F, 0.0F, 0.0F, -1.5708F, 0.0F));

    ModelPartData Tail_r1 = bb_main.addChild("Tail_r1", ModelPartBuilder.create().uv(11, 13).cuboid(3.3014F, -0.3697F, -2.5F, 2.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-0.5847F, -6.25F, 0.0F, -1.5708F, -1.1781F, 1.5708F));
    return TexturedModelData.of(modelData, 32, 32);
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
    bb_main.render(matrices, vertexConsumer, light, overlay, color);
  }

  @Override
  public ModelPart getPart() {
    return bb_main;
  }

  @Override
  public void setAngles(DuckEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

  }
}