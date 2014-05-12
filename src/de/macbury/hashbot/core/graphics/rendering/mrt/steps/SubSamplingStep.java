package de.macbury.hashbot.core.graphics.rendering.mrt.steps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;

/**
 * Created by macbury on 11.05.14.
 */
public abstract class SubSamplingStep extends Step {
  public static final float SAMPLE_FACTOR = 0.35f;
  public static String SUB_STEP_FRAME_BUFFER = "SUB_STEP_FRAME_BUFFER";
  public SubSamplingStep(MRTRenderingEngine engine, String shaderName) {
    super(engine, SUB_STEP_FRAME_BUFFER, shaderName);
  }

  @Override
  public void createFBO() {
    sm.createFB(fboId, Pixmap.Format.RGBA8888, Math.round(Gdx.graphics.getWidth() * SAMPLE_FACTOR), Math.round(Gdx.graphics.getHeight() * SAMPLE_FACTOR), false);
  }

}
