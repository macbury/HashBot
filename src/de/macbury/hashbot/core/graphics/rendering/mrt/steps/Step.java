package de.macbury.hashbot.core.graphics.rendering.mrt.steps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Matrix4;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBuffer;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.blur.BlurStep;
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
  public static final String UNIFORM_MODEL_TRANSFORM = "u_modelTrans";
  public static final String UNIFORM_SCREEN_SIZE = "u_screenSize";
  public static final String UNIFORM_TEXTURE_GLOW = "u_glowTexture";
  private static final String UNIFORM_BLUR = "u_blurTexture";
  protected GBuffer gBuffer;
  protected RenderContext renderContext;
  protected MRTRenderingEngine engine;
  protected String shaderName;
  protected Shaders sm;
  protected String fboId;
  private static String lastFBO;

  public Step(MRTRenderingEngine engine, String fboId, String shaderName) {
    this.fboId         = fboId;
    this.engine        = engine;
    this.sm            = engine.sm;
    this.renderContext = engine.renderContext;
    this.gBuffer       = engine.gBuffer;
    this.shaderName    = shaderName;
    createFBO();
  }

  public void clear() {
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
  }

  public abstract void createFBO();

  public void begin() {
    sm.getFB(fboId).begin();
    renderContext.begin();
    sm.begin(shaderName);
    setupUniforms();
  }

  public void renderFBO() {
    begin(); {
      sm.render();
    } end();
  }

  public void end() {
    sm.end();
    renderContext.end();
    sm.getFB(fboId).end();
    lastFBO = this.fboId;
  }

  public abstract void setupUniforms();

  protected void uniformLastResult() {
    sm.setUniformi(UNIFORM_LAST_TEXTURE_COLOR, renderContext.textureBinder.bind(sm.getFBTexture(lastFBO)));
  }

  protected void uniformBlur() {
    sm.setUniformi(UNIFORM_BLUR, renderContext.textureBinder.bind(sm.getFBTexture(BlurStep.BLUR_SUB_STEP_FRAME_BUFFER)));
  }

  protected void uniformColorTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_COLOR, renderContext.textureBinder.bind(gBuffer.colorAttachment));
  }

  protected void uniformNormalTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_NORMAL,   renderContext.textureBinder.bind(gBuffer.normalsAttachment));
  }

  protected void uniformGlowTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_GLOW,   renderContext.textureBinder.bind(gBuffer.glowAttachment));
  }

  protected void uniformPositionTexture() {
    sm.setUniformi(UNIFORM_TEXTURE_POSITION, renderContext.textureBinder.bind(gBuffer.positionAttachment));
  }

  protected void uniformCameraPosition() {
    sm.setUniform(UNIFORM_CAMERA_POSITION, engine.mainCamera);
  }

  protected void uniformModelTransform(Matrix4 transform) {
    sm.setUniformMatrix(UNIFORM_MODEL_TRANSFORM, transform);
  }

  protected void uniformProjectionView() {
    sm.setUniformMatrix(UNIFORM_PROJECT_VIEW, engine.mainCamera.combined);
  }

  protected void uniformScreenSize() {
    sm.setUniformf(UNIFORM_SCREEN_SIZE, sm.getFB(fboId).getWidth(), sm.getFB(fboId).getHeight());
  }

  public float getWidth() {
    return sm.getFB(fboId).getWidth();
  }

  public float getHeight() {
    return sm.getFB(fboId).getHeight();
  }

  // Render on screen!
  public void display() {
    sm.begin(Shaders.SHADER_DEFAULT); {
      sm.renderFB(fboId);
    } sm.end();
  }
}
