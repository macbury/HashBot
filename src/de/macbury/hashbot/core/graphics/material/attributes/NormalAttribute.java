package de.macbury.hashbot.core.graphics.material.attributes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;

/**
 * Created by macbury on 12.05.14.
 */
public class NormalAttribute extends Attribute {
  public final static String NormalAlias = "normalTexture";
  public final static long Normal = register(NormalAlias);
  public final TextureDescriptor<Texture> textureDescription;


  public final static boolean is (final long mask) {
    return (mask & Normal) != 0;
  }

  public NormalAttribute(TextureDescriptor<Texture> textureDescription) {
    this(textureDescription.texture);
  }

  public NormalAttribute (Texture texture) {
    super(Normal);
    textureDescription = new TextureDescriptor<Texture>();
    textureDescription.texture = texture;
  }

  public NormalAttribute (final NormalAttribute copyFrom) {
    this(copyFrom.textureDescription);
  }

  @Override
  public Attribute copy () {
    return new NormalAttribute(this);
  }

  @Override
  protected boolean equals (Attribute other) {
    return ((NormalAttribute)other).textureDescription.equals(textureDescription);
  }


}