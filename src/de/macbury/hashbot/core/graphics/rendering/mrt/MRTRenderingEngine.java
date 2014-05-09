package de.macbury.hashbot.core.graphics.rendering.mrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBuffer;
import de.macbury.hashbot.core.graphics.rendering.mrt.model_batch.DefferedModelBatch;
import de.macbury.hashbot.core.graphics.rendering.BaseRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal.ApplyFogStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal.ApplyLightStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 07.05.14.
 */
public class MRTRenderingEngine extends BaseRenderingEngine {
  private static final String TAG = "MRTRenderingEngine";
  private final ApplyLightStep applyLightStep;
  private final ApplyFogStep applyFogStep;
  public GBuffer gBuffer;
  public Shaders sm;

  public MRTRenderingEngine(PerspectiveCamera camera) {
    super(camera);
    this.gBuffer = new GBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), renderContext);

    this.sm     = HashBot.shaders;
    Gdx.app.log(TAG, "Created");

    this.applyLightStep = new ApplyLightStep(this);
    this.applyFogStep   = new ApplyFogStep(this);
  }

  @Override
  protected DefferedModelBatch buildModelBatch() {
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

    applyLightStep.renderFBO();
    applyFogStep.renderFBO();
    applyFogStep.display();
  }


  @Override
  public void dispose() {
    super.dispose();
    gBuffer.dispose();
  }
}
