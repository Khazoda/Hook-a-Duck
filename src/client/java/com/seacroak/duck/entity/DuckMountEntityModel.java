// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.seacroak.duck.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ModelWithWaterPatch;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class DuckMountEntityModel<T extends DuckMountEntity> extends CompositeEntityModel<DuckMountEntity> implements ModelWithWaterPatch {
    private final ModelPart Base;
    private final ModelPart Additions;
    private final ModelPart RWing;
    private final ModelPart LWing;
    private final ModelPart LeverTop;
    private final ModelPart Metals;
    private final ModelPart waterblock;


    public DuckMountEntityModel(ModelPart root) {
        this.Base = root.getChild("Base");
        this.Additions = root.getChild("Additions");
        this.RWing = this.Additions.getChild("RWing");
        this.LWing = this.Additions.getChild("LWing");
        this.LeverTop = root.getChild("LeverTop");
        this.Metals = root.getChild("Metals");
        this.waterblock = root.getChild("waterblock");


    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData waterblock = modelPartData.addChild("waterblock", ModelPartBuilder.create().uv(7, 104).cuboid(-7.0F, -2.0F, -12.0F, 16.0F, 6.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.95F, 16.5F, -2.0F));

        ModelPartData Base = modelPartData.addChild("Base", ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, -1.0F, 16.0F, 3.0F, 28.0F, new Dilation(0.0F))
                .uv(64, 31).cuboid(0.0F, -6.0F, 27.0F, 18.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(60, 20).cuboid(0.0F, -6.0F, -3.0F, 18.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(32, 37).cuboid(17.0F, -6.0F, -1.0F, 2.0F, 6.0F, 28.0F, new Dilation(0.0F))
                .uv(0, 31).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 28.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 21.0F, -13.0F));

        ModelPartData Additions = modelPartData.addChild("Additions", ModelPartBuilder.create().uv(60, 0).cuboid(-8.05F, 6.5F, 29.5F, 16.0F, 6.0F, 6.0F, new Dilation(0.0F))
                .uv(60, 12).cuboid(-8.05F, 10.5F, 23.5F, 16.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(32, 31).cuboid(-3.55F, -4.5F, 0.5F, 7.0F, 7.0F, 7.0F, new Dilation(0.0F))
                .uv(19, 6).cuboid(2.7F, -3.5F, 1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(19, 0).cuboid(-3.8F, -3.5F, 1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.05F, 8.5F, -21.5F));

        ModelPartData LowerBeak_r1 = Additions.addChild("LowerBeak_r1", ModelPartBuilder.create().uv(14, 19).cuboid(-2.5F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        ModelPartData UpperBeak_r1 = Additions.addChild("UpperBeak_r1", ModelPartBuilder.create().uv(14, 22).cuboid(-2.5F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        ModelPartData Neck_r1 = Additions.addChild("Neck_r1", ModelPartBuilder.create().uv(32, 45).cuboid(-3.0F, -5.0F, -2.0F, 6.0F, 11.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.05F, 6.2F, 6.5F, 0.1745F, 0.0F, 0.0F));

        ModelPartData RWing = Additions.addChild("RWing", ModelPartBuilder.create().uv(8, 8).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(8, 11).cuboid(-1.0F, -7.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(6, 11).cuboid(-1.0F, -7.0F, 4.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(4, 11).cuboid(-1.0F, -7.0F, 6.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(2, 10).cuboid(-1.0F, -7.0F, 8.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-11.05F, 12.5F, 18.5F));

        ModelPartData LWing = Additions.addChild("LWing", ModelPartBuilder.create().uv(8, 8).mirrored().cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 6.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(8, 11).mirrored().cuboid(-1.0F, -7.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(6, 11).mirrored().cuboid(-1.0F, -7.0F, 4.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(4, 11).mirrored().cuboid(-1.0F, -7.0F, 6.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(2, 10).cuboid(-1.0F, -7.0F, 8.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(10.95F, 12.5F, 18.5F));

        ModelPartData LeverTop = modelPartData.addChild("LeverTop", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 22.0F, -9.25F));

        ModelPartData LeverTop_r1 = LeverTop.addChild("LeverTop_r1", ModelPartBuilder.create().uv(64, 47).cuboid(-2.0F, -1.0F, -3.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.0F, 3.0F, 0.9163F, 0.0F, 0.0F));

        ModelPartData Metals = modelPartData.addChild("Metals", ModelPartBuilder.create().uv(0, 19).cuboid(-3.0F, 1.0F, -5.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 19.0F, -6.25F));

        ModelPartData LeftFootRest_r1 = Metals.addChild("LeftFootRest_r1", ModelPartBuilder.create().uv(0, 50).cuboid(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(64, 39).cuboid(-12.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 1.0F, -6.0F, -0.6545F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        Base.render(matrices, vertices, light, overlay, color);
        Additions.render(matrices, vertices, light, overlay, color);
        LeverTop.render(matrices, vertices, light, overlay, color);
        Metals.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return null;
    }

    @Override
    public void setAngles(DuckMountEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public ModelPart getWaterPatch() {
        return this.waterblock;
    }
}

