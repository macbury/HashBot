package de.macbury.hashbot.core.graphics.rendering.mrt.steps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBuffer;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public abstract class Step {
  public static final String UNIFORM_LAST_TEXTURE_COLOR = "u_texture";
  public static final String UNIFORM_TEXTURE_COLOR = "u_colorTexture";
  public static final String UNIFORM_TEXTURE_NORMAL = "u_normalTexture";
  public static final String UNIFORM_TEXTURE_POSITION = "u_positionTexture";

  public static final String UNIFORM_CAMERA_POSITION = "u_cameraPosition";
  public static final String UNIFORM_PROJECT_VIEW    = "u_projViewTrans";
  protected GBuffer gBuffer;
  protected RenderContext renderContext;
  protected MRTRenderingEngine engine;
  protected String shaderName;
  protected Shaders sm;
  protected String fboId;

  public Step(MRTRenderingEngine engine, String fboId, String shaderName) {
    this.fboId         = fboId;
    this.engine        = engine;
    this.sm            = engine.sm;
    this.renderContext = engine.renderContext;
    this.gBuffer       = engine.gBuffer;
    this.shaderName    = shaderName;
    createFBO();
  }

  public abstract void createFBO();

  public void renderFBO() {
    sm.getFB(fboId).begin(); {
      sm.begin(shaderName); {
        setupUniforms();
        sm.render();
      }sm.end();
    } sm.getFB(fboId).end();
  }

  public abstract void setupUniforms();

  protected void uniformLastResult() {
    sm.setUniformi(UNIFORM_LAST_TEXTURE_COLOR, renderContext.textureBinder.bind(sm.getFBTexture(fboId)));
  }

  protected void uniformColorTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_COLOR, renderContext.textureBinder.bind(gBuffer.colorAttachment));
  }

  protected void uniformNormalTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_NORMAL,   renderContext.textureBinder.bind(gBuffer.normalsAttachment));
  }

  protected void uniformPositionTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_POSITION, renderContext.textureBinder.bind(gBuffer.positionAttachment));
  }

  protected void uniformCameraPosition() {
    sm.setUniform(UNIFORM_CAMERA_POSITION, engine.mainCamera);
  }

  protected void uniformProjectionView() {
    sm.setUniformMatrix(UNIFORM_PROJECT_VIEW, engine.mainCamera.combined);
  }


  // Render on screen!
  public void display() {
    sm.begin(Shaders.SHADER_DEFAULT); {
      sm.renderFB(fboId);
    } sm.end();
  }
}
