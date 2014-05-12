package de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.blur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.Step;

/**
 * Created by macbury on 12.05.14.
 */
public abstract class BlurStep extends Step {
  public static final int RENDER_SIZE = 512;
  private static final String  UNIFORM_BLUR_SIZE = "blurSize";
  public static String BLUR_SUB_STEP_FRAME_BUFFER = "BLUR_SUB_STEP_FRAME_BUFFER";

  public BlurStep(MRTRenderingEngine engine, String shaderName) {
    super(engine, BLUR_SUB_STEP_FRAME_BUFFER, shaderName);
  }

  @Override
  public void createFBO() {
    sm.createFB(fboId, Pixmap.Format.RGBA8888, RENDER_SIZE, RENDER_SIZE, false);
  }

  public void uniformBlurSize() {
    sm.setUniformf(UNIFORM_BLUR_SIZE, 1.0f/ getBlurSize());
  }

  protected abstract float getBlurSize();
}
