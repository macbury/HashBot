package de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.blur;

import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 12.05.14.
 */
public class GlowVerticalBlurStep extends BlurStep {
  public GlowVerticalBlurStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_BLUR_VERTICAL);
  }

  @Override
  public void setupUniforms() {
    uniformLastResult();
    uniformBlurSize();
  }

  @Override
  protected float getBlurSize() {
    return getHeight();
  }
}
