package de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.blur;

import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 12.05.14.
 */
public class GlowHorizontalBlurStep extends BlurStep {
  public GlowHorizontalBlurStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_BLUR_HORIZONTAL);
  }

  @Override
  public void setupUniforms() {
    uniformGlowTexture();
    uniformBlurSize();
  }

  @Override
  protected float getBlurSize() {
    return getWidth();
  }
}
