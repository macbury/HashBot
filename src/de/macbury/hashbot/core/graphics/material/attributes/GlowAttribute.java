package de.macbury.hashbot.core.graphics.material.attributes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by macbury on 12.05.14.
 */
public class GlowAttribute extends Attribute {
  public final static String GlowAlias = "glowTexture";
  public final static long Glow = register(GlowAlias);
  public final TextureDescriptor<Texture> textureDescription;


  public final static boolean is (final long mask) {
    return (mask & Glow) != 0;
  }

  public GlowAttribute(TextureDescriptor<Texture> textureDescription) {
    this(textureDescription.texture);
  }

  public GlowAttribute (Texture texture) {
    super(Glow);
    textureDescription = new TextureDescriptor<Texture>();
    textureDescription.texture = texture;
  }

  public GlowAttribute (final GlowAttribute copyFrom) {
    this(copyFrom.textureDescription);
  }

  @Override
  public Attribute copy () {
    return new GlowAttribute(this);
  }

  @Override
  protected boolean equals (Attribute other) {
    return ((GlowAttribute)other).textureDescription.equals(textureDescription);
  }


}
