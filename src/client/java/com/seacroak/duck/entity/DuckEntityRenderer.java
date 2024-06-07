package com.seacroak.duck.entity;

import com.seacroak.duck.util.GenericUtils;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, DuckEntityModel> {
  public DuckEntityRenderer(EntityRendererFactory.Context context, DuckEntityModel entityModel, float f) {
    super(context, new DuckEntityModel(context.getPart(EntityTest.MODEL_CUBE_LAYER)), 0.5f);
  }

  @Override
  public Identifier getTexture(DuckEntity entity) {
    return GenericUtils.ID( "textures/entity/duck/duck_texture.png");  }
}
