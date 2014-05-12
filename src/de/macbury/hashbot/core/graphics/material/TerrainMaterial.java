package de.macbury.hashbot.core.graphics.material;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import de.macbury.hashbot.core.graphics.material.attributes.GlowAttribute;

/**
 * Created by macbury on 12.05.14.
 */
public class TerrainMaterial extends Material {

  public TerrainMaterial(TextureAtlas tileset, TextureAtlas glow) {
    super(TextureAttribute.createDiffuse(tileset.getTextures().first()), ColorAttribute.createDiffuse(Color.WHITE), new GlowAttribute(glow.getTextures().first()));
  }
}
