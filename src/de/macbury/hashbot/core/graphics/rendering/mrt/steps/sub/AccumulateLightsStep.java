package de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub;

import com.badlogic.gdx.graphics.GL30;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LightComponent;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.NormalSamplingStep;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.SubSamplingStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public class AccumulateLightsStep extends SubSamplingStep {
  public static final String UNIFORM_LIGHT_COLOR    = "u_light.color";
  public static final String UNIFORM_LIGHT_POSITION = "u_light.position";
  public static final String UNIFORM_LIGHT_RADIUS   = "u_light.radius";
  public AccumulateLightsStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_ACCUMULATE_LIGHT);
  }

  @Override
  public void setupUniforms() {
    uniformScreenSize();
    uniformColorTexture();
    uniformNormalTexture();
    uniformPositionTexture();
    uniformProjectionView();
    uniformCameraPosition();
  }

  public void renderLight(LightComponent light, ActorComponent actor) {
    sm.setUniformf(UNIFORM_LIGHT_COLOR,    light.color);
    actor.position.y = 1;
    sm.setUniformf(UNIFORM_LIGHT_POSITION, actor.position);
    sm.setUniformf(UNIFORM_LIGHT_RADIUS, light.radius);
    uniformModelTransform(light.worldTransform);
    light.mesh.render(HashBot.shaders.getCurrent(), GL30.GL_TRIANGLE_FAN);
  }
}
