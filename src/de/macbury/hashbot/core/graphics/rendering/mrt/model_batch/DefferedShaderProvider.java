package de.macbury.hashbot.core.graphics.rendering.mrt.model_batch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.managers.Assets;

/**
 * Created by macbury on 08.05.14.
 */
public class DefferedShaderProvider extends DefaultShaderProvider {

  public DefferedShaderProvider() {
    super(new DefaultShader.Config(Gdx.files.internal(Assets.SHADER_VERTEX_DEFFERED).readString(), Gdx.files.internal(Assets.SHADER_FRAGMENT_DEFFERED).readString()));
  }

  @Override
  protected Shader createShader(Renderable renderable) {
    return new DefferedShader(renderable, config);
  }
}
