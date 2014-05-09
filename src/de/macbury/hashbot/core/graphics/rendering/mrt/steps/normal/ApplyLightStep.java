package de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.NormalStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public class ApplyLightStep extends NormalStep {
  public static final String UNIFORM_AMBIENT_LIGHT_COLOR = "u_ambientLight";

  public ApplyLightStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_APPLY_LIGHT);
  }

  @Override
  public void setupUniforms() {
    ColorAttribute ambientLight = (ColorAttribute) engine.environment.get(ColorAttribute.AmbientLight);
    sm.setUniformf(UNIFORM_AMBIENT_LIGHT_COLOR, ambientLight.color);

    uniformColorTexture();
    uniformPositionTexture();
    uniformNormalTexture();
    uniformCameraPosition();
    uniformProjectionView();
  }
}
