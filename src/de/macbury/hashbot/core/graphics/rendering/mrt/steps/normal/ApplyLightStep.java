package de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal;

import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.NormalSamplingStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public class ApplyLightStep extends NormalSamplingStep {
  public static final String UNIFORM_AMBIENT_LIGHT_COLOR = "u_ambientLight";
  public static final String UNIFORM_DIR_LIGHT_COLOR = "u_dirLight.color";
  public static final String UNIFORM_DIR_LIGHT_DIRECTION = "u_dirLight.direction";
  public ApplyLightStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_APPLY_LIGHT);
  }

  @Override
  public void setupUniforms() {
    sm.setUniformf(UNIFORM_AMBIENT_LIGHT_COLOR, engine.ambientLight.color);

    sm.setUniformf(UNIFORM_DIR_LIGHT_COLOR, engine.sunLight.color);
    sm.setUniformf(UNIFORM_DIR_LIGHT_DIRECTION, engine.sunLight.direction);

    uniformLastResult();
    uniformColorTexture();
    //uniformPositionTexture();
    uniformNormalTexture();
    uniformCameraPosition();
    uniformProjectionView();
  }
}
