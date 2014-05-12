package de.macbury.hashbot.core.graphics.rendering.mrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBuffer;
import de.macbury.hashbot.core.graphics.rendering.mrt.model_batch.DefferedModelBatch;
import de.macbury.hashbot.core.graphics.rendering.BaseRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal.ApplyGlowStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.AccumulateLightsStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal.ApplyFogStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal.ApplyLightStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.blur.GlowHorizontalBlurStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.blur.GlowVerticalBlurStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 07.05.14.
 */
public class MRTRenderingEngine extends BaseRenderingEngine {
  private static final String TAG = "MRTRenderingEngine";
  private final ApplyLightStep applyLightStep;
  private final ApplyFogStep applyFogStep;
  private final ApplyGlowStep applyGlowStep;
  private final GlowHorizontalBlurStep applyHorizontalBlur;
  private final GlowVerticalBlurStep applyVerticalBlur;
  public AccumulateLightsStep accumulateLightsStep;
  public GBuffer gBuffer;
  public Shaders sm;

  public MRTRenderingEngine(PerspectiveCamera camera) {
    super(camera);
    this.gBuffer = new GBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), renderContext);

    this.sm     = HashBot.shaders;
    Gdx.app.log(TAG, "Created");
    this.accumulateLightsStep = new AccumulateLightsStep(this);
    this.applyLightStep       = new ApplyLightStep(this);
    this.applyFogStep         = new ApplyFogStep(this);
    this.applyGlowStep        = new ApplyGlowStep(this);
    this.applyHorizontalBlur  = new GlowHorizontalBlurStep(this);
    this.applyVerticalBlur    = new GlowVerticalBlurStep(this);
  }

  @Override
  protected DefferedModelBatch buildGeometryBatch() {
    return new DefferedModelBatch(renderContext);
  }

  @Override
  protected void beforeRender() {
    gBuffer.begin();
    gBuffer.clear();
  }

  @Override
  protected void afterRender() {
    gBuffer.end();

    /*accumulateLightsStep.begin(); {
      accumulateLightsStep.clear();
      renderContext.setCullFace(GL30.GL_FRONT);
      renderContext.setBlending(true, GL30.GL_ONE, GL30.GL_ONE);
      renderContext.setDepthMask(false);
      renderContext.setDepthTest(GL30.GL_GREATER);
      Gdx.gl.glBlendEquation(GL30.GL_FUNC_ADD);

      getListener().lightPass(accumulateLightsStep);
    } accumulateLightsStep.end();*/

    applyHorizontalBlur.renderFBO();
    applyVerticalBlur.renderFBO();
    applyLightStep.renderFBO();
    applyGlowStep.renderFBO();
    applyFogStep.renderFBO();

    applyFogStep.display();
  }


  @Override
  public void dispose() {
    super.dispose();
    gBuffer.dispose();
  }
}
