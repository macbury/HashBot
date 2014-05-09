package de.macbury.hashbot.core.graphics.rendering.mrt.model_batch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.managers.Assets;

/**
 * Created by macbury on 08.05.14.
 */
public class DefferedShaderProvider extends BaseShaderProvider {

  private ShaderProgram modelShader;

  public DefferedShaderProvider() {
    this.modelShader = new ShaderProgram(Gdx.files.internal(Assets.SHADER_VERTEX_DEFFERED).readString(), Gdx.files.internal(Assets.SHADER_FRAGMENT_DEFFERED).readString());
  }

  @Override
  protected Shader createShader(Renderable renderable) {
    return new DefferedShader(renderable, modelShader);
  }

  @Override
  public void dispose() {
    super.dispose();
    modelShader.dispose();
  }
}
