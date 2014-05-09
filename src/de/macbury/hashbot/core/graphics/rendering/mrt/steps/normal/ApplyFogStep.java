package de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal;

import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.NormalStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public class ApplyFogStep extends NormalStep {
  public ApplyFogStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_APPLY_FOG);
  }

  @Override
  public void setupUniforms() {
    uniformLastResult();
    uniformCameraPosition();
    uniformPositionTexture();
  }
}
