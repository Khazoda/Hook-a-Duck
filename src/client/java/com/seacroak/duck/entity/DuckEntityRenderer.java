package com.seacroak.duck.entity;

import com.seacroak.duck.HookADuckClient;
import com.seacroak.duck.util.GenericUtils;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, DuckEntityModel<DuckEntity>> {


  public DuckEntityRenderer(EntityRendererFactory.Context context) {
    super(context, new DuckEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_LAYER)), 0.5f);
  }

  @Override
  public Identifier getTexture(DuckEntity entity) {
    return GenericUtils.ID( "textures/entity/duck_texture.png");  }
}
