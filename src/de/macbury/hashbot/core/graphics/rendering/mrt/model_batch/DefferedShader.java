package de.macbury.hashbot.core.graphics.rendering.mrt.model_batch;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;

/**
 * Created by macbury on 08.05.14.
 */
public class DefferedShader extends DefaultShader {

  public DefferedShader(Renderable renderable, Config config) {
    super(renderable, config);
  }
}
