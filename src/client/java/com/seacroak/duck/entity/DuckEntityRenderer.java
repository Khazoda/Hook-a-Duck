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

public class DuckEntityRenderer extends MobEntityRenderer<DuckEntity, DuckEntityModel<DuckEntity>> {

  private static final Map<DuckRarity, Identifier> TEXTURES = Util.make(Maps.newEnumMap(DuckRarity.class), (map) -> {
    map.put(DuckRarity.DEFAULT, GenericUtils.ID("textures/entity/popcorn_texture.png"));
    map.put(DuckRarity.GREEN, GenericUtils.ID("textures/entity/duck_green_texture.png"));
    map.put(DuckRarity.BLUE, GenericUtils.ID("textures/entity/duck_blue_texture.png"));
    map.put(DuckRarity.PURPLE, GenericUtils.ID("textures/entity/duck_purple_texture.png"));
    map.put(DuckRarity.RED, GenericUtils.ID("textures/entity/duck_red_texture.png"));
    map.put(DuckRarity.GOLD, GenericUtils.ID("textures/entity/duck_gold_texture.png"));
  });


  public DuckEntityRenderer(EntityRendererFactory.Context context) {
    super(context, new DuckEntityModel<>(context.getPart(HookADuckClient.MODEL_DUCK_LAYER)), 0.5f);
  }

  @Override
  public Identifier getTexture(DuckEntity entity) {
    return TEXTURES.get(entity.getVariant());
  }
}
