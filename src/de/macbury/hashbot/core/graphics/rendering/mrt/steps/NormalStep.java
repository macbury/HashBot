package de.macbury.hashbot.core.graphics.rendering.mrt.steps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public abstract class NormalStep extends Step {
  public static String NORMAL_STEP_FRAME_BUFFER = "NORMAL_STEP_FRAME_BUFFER";

  public NormalStep(MRTRenderingEngine engine, String shaderName) {
    super(engine, NORMAL_STEP_FRAME_BUFFER, shaderName);
  }

  @Override
  public void createFBO() {
    sm.createFB(fboId, Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
  }
}
