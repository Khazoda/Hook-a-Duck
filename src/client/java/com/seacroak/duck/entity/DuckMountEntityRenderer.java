package com.seacroak.duck.entity;

import com.mojang.datafixers.util.Pair;
import com.seacroak.duck.HookADuckClient;
import com.seacroak.duck.util.GenericUtils;
import io.netty.util.Attribute;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ModelWithWaterPatch;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Direction;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Quaternionf;


public class DuckMountEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<DuckMountEntity, DuckMountEntityModel<DuckMountEntity>> {

    private final DuckMountEntityModel<DuckMountEntity> duckModel;
    private final ModelPart waterPatch;

    public DuckMountEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckMountEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_MOUNT_LAYER)),  0.5f);
        this.duckModel = new DuckMountEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_MOUNT_LAYER));
        this.waterPatch = duckModel.getWaterPatch();
    }

    @Override
    public Identifier getTexture(DuckMountEntity entity) {
        return GenericUtils.ID("textures/entity/duck_mount_entity_texture.png");

    }

    @Override
    public void render(DuckMountEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        // Render the main entity model without interpolation
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);

        // Check if the entity is not submerged in water
        if (!entity.isSubmergedInWater()) {
            matrixStack.push();

            // Calculate the interpolated yaw for the waterPatch only
            float interpolatedYaw = MathHelper.lerp(g, entity.prevYaw, entity.getYaw());

            // Scale down the interpolated yaw to adjust the rotation speed of the waterPatch
            float scaledInterpolatedYaw = interpolatedYaw * 0.5F; // Adjust this factor as needed

            // Apply the scaled and interpolated rotation to the waterPatch
            float yawRadians = (float) Math.toRadians(-scaledInterpolatedYaw);
            float sinYaw = MathHelper.sin(yawRadians);
            float cosYaw = MathHelper.cos(yawRadians);
            matrixStack.multiply(new Quaternionf(0.0F, sinYaw, 0.0F, cosYaw));

            // Render the waterPatch
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
            waterPatch.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

            matrixStack.pop();
        }

        matrixStack.pop();
    }
    
}
