package de.macbury.hashbot.core.graphics.rendering.mrt.steps.normal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.NormalSamplingStep;
import de.macbury.hashbot.core.managers.Shaders;

/**
 * Created by macbury on 09.05.14.
 */
public class ApplyFogStep extends NormalSamplingStep {
  private static final String UNIFORM_MAP_SIZE = "u_mapSize";
  private static final String UNIFORM_TEXTURE_FOG = "u_fogTexture";

  public ApplyFogStep(MRTRenderingEngine engine) {
    super(engine, Shaders.SHADER_APPLY_FOG);
  }

  @Override
  public void setupUniforms() {
    uniformLastResult();
    uniformCameraPosition();
    uniformPositionTexture();

    sm.setUniformi(UNIFORM_TEXTURE_FOG, engine.level.fogOfWar.bind(renderContext.textureBinder));
    sm.setUniformf(UNIFORM_MAP_SIZE, engine.level.getTerrain().getWidth(), engine.level.getTerrain().getHeight());
  }
}
