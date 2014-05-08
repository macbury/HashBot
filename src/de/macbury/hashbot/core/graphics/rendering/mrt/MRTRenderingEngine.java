package de.macbury.hashbot.core.graphics.rendering.mrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBuffer;
import de.macbury.hashbot.core.graphics.rendering.mrt.model_batch.DefferedModelBatch;
import de.macbury.hashbot.core.graphics.rendering.BaseRenderingEngine;

/**
 * Created by macbury on 07.05.14.
 */
public class MRTRenderingEngine extends BaseRenderingEngine {
  private static final String TAG = "MRTRenderingEngine";
  public final GBuffer gBuffer;

  public MRTRenderingEngine(PerspectiveCamera camera) {
    super(camera);
    this.gBuffer = new GBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), renderContext);
    Gdx.app.log(TAG, "Created");
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
  }

  @Override
  public void dispose() {
    super.dispose();
    gBuffer.dispose();
  }
}
