package com.seacroak.duck.entity;

import com.google.common.collect.Maps;
import com.seacroak.duck.HookADuckClient;
import com.seacroak.duck.util.DuckRarity;
import com.seacroak.duck.util.GenericUtils;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ModelWithWaterPatch;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class DuckMountEntityRenderer extends MobEntityRenderer<DuckMountEntity, DuckMountEntityModel<DuckMountEntity>> {


private final DuckMountEntityModel<DuckMountEntity> duckModel;

    public DuckMountEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckMountEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_MOUNT_LAYER)),  0.5f);
        this.duckModel = new DuckMountEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_MOUNT_LAYER));

    }

    @Override
    public Identifier getTexture(DuckMountEntity entity) {
        return GenericUtils.ID("textures/entity/duck_mount_entity_texture.png");

    }

    @Override
    public void render(DuckMountEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
        if (!livingEntity.isSubmergedInWater()) {
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getWaterMask());
            if (duckModel != null) {
                duckModel.getWaterPatch().render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV);
            }
        }

    }
}
