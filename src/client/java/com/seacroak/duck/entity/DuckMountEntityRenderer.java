package com.seacroak.duck.entity;

import com.google.common.collect.Maps;
import com.seacroak.duck.HookADuckClient;
import com.seacroak.duck.util.DuckRarity;
import com.seacroak.duck.util.GenericUtils;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class DuckMountEntityRenderer extends MobEntityRenderer<DuckMountEntity, DuckMountEntityModel<DuckMountEntity>> {




    public DuckMountEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DuckMountEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_MOUNT_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(DuckMountEntity entity) {
        return GenericUtils.ID("textures/entity/duck_texture.png");

    }
}
