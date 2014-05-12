package de.macbury.hashbot.core.graphics.rendering.mrt.model_batch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.material.attributes.GlowAttribute;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 08.05.14.
 * Gather information about scene in attachments
 */
public class DefferedShader extends BaseShader {
  private int u_glowTexture;
  private Renderable renderable;
  private int u_projViewTrans;
  private int u_cameraPosition;
  private int u_normalMatrix;
  private int u_worldTrans;
  private int u_diffuseColor;
  private int u_diffuseTexture;

  public DefferedShader(Renderable renderable) {
    this.program    = HashBot.shaders.get(Shaders.SHADER_DEFFERED_GBUFFER);
    this.renderable = renderable;

    u_projViewTrans  = register(DefaultShader.Inputs.projViewTrans, DefaultShader.Setters.projViewTrans);
    u_cameraPosition = register(DefaultShader.Inputs.cameraPosition, DefaultShader.Setters.cameraPosition);
    u_normalMatrix   = register(DefaultShader.Inputs.normalMatrix, DefaultShader.Setters.normalMatrix);
    u_worldTrans     = register(DefaultShader.Inputs.worldTrans, DefaultShader.Setters.worldTrans);

    u_diffuseColor   = register(DefaultShader.Inputs.diffuseColor, DefaultShader.Setters.diffuseColor);

    //TODO inteligent setters and getters!
    u_diffuseTexture = register(DefaultShader.Inputs.diffuseTexture, DefaultShader.Setters.diffuseTexture);
    u_glowTexture    = register(glowTexture, glowSetterTexture);
  }

  public final static Uniform glowTexture = new Uniform("u_glowTexture", GlowAttribute.Glow);
  public final static Setter glowSetterTexture = new Setter() {
    @Override
    public boolean isGlobal (BaseShader shader, int inputID) {
      return false;
    }

    @Override
    public void set (BaseShader shader, int inputID, Renderable renderable, Attributes combinedAttributes) {
      final int unit = shader.context.textureBinder.bind(((GlowAttribute)(combinedAttributes
              .get(GlowAttribute.Glow))).textureDescription);
      shader.set(inputID, unit);
    }
  };

  @Override
  public void init() {
    init(program, renderable);
  }

  @Override
  public void render(Renderable renderable) {
    context.setCullFace(GL20.GL_BACK);
    context.setDepthMask(true);
    context.setDepthTest(GL20.GL_LEQUAL);
    super.render(renderable);
  }

  @Override
  public int compareTo(Shader other) {
    if (other == null) return -1;
    if (other == this) return 0;
    return 0; // FIXME compare shaders on their impact on performance
  }

  @Override
  public boolean canRender(Renderable instance) {
    return true;
  }
}
