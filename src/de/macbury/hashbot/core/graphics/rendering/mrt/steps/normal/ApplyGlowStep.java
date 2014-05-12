package de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal;

import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.NormalSamplingStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 12.05.14.
 */
public class ApplyGlowStep extends NormalSamplingStep {

  public ApplyGlowStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_APPLY_GLOW);
  }

  @Override
  public void setupUniforms() {
    uniformLastResult();
    uniformGlowTexture();
    uniformBlur();
  }
}
