package de.macbury.hashbot.core.graphics.rendering.mrt.model_batch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by macbury on 08.05.14.
 * Gather information about scene in attachments
 */
public class DefferedShader extends BaseShader {

  private Renderable renderable;
  private int u_projViewTrans;
  private int u_cameraPosition;
  private int u_normalMatrix;
  private int u_worldTrans;
  private int u_diffuseColor;
  private int u_diffuseTexture;

  public DefferedShader(Renderable renderable, ShaderProgram shaderProgram) {
    this.program    = shaderProgram;
    this.renderable = renderable;

    u_projViewTrans  = register(DefaultShader.Inputs.projViewTrans, DefaultShader.Setters.projViewTrans);
    u_cameraPosition = register(DefaultShader.Inputs.cameraPosition, DefaultShader.Setters.cameraPosition);
    u_normalMatrix   = register(DefaultShader.Inputs.normalMatrix, DefaultShader.Setters.normalMatrix);
    u_worldTrans     = register(DefaultShader.Inputs.worldTrans, DefaultShader.Setters.worldTrans);

    u_diffuseColor   = register(DefaultShader.Inputs.diffuseColor, DefaultShader.Setters.diffuseColor);

    //TODO inteligent setters and getters!
    u_diffuseTexture = register(DefaultShader.Inputs.diffuseTexture, DefaultShader.Setters.diffuseTexture);
  }

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
