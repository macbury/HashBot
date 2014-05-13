package de.macbury.hashbot.core.graphics.material;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import de.macbury.hashbot.core.graphics.material.attributes.GlowAttribute;
import de.macbury.hashbot.core.graphics.material.attributes.NormalAttribute;

/**
 * Created by macbury on 12.05.14.
 */
public class TerrainMaterial extends Material {

  public TerrainMaterial(TextureAtlas tileset, TextureAtlas glow, TextureAtlas normalMap) {
    super(new NormalAttribute(normalMap.getTextures().first()), TextureAttribute.createDiffuse(tileset.getTextures().first()), ColorAttribute.createDiffuse(Color.WHITE), new GlowAttribute(glow.getTextures().first()));
  }
}
