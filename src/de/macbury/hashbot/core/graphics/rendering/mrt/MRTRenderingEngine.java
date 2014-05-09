package de.macbury.hashbot.core.graphics.rendering.mrt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBuffer;
import de.macbury.hashbot.core.graphics.rendering.mrt.model_batch.DefferedModelBatch;
import de.macbury.hashbot.core.graphics.rendering.BaseRenderingEngine;
import de.macbury.hashbot.core.managers.Shaders;
import de.macbury.hashbot.core.shader.ShaderManager;

/**
 * Created by macbury on 07.05.14.
 */
public class MRTRenderingEngine extends BaseRenderingEngine {
  private static final String TAG = "MRTRenderingEngine";
  private static final String FRAMEBUFFER_DEFFERED_RENDERING = "FRAMEBUFFER_DEFFERED_RENDERING";
  private static final String UNIFORM_TEXTURE_COLOR = "u_colorTexture";
  private static final String UNIFORM_TEXTURE_NORMAL = "u_normalTexture";
  private static final String UNIFORM_TEXTURE_POSITION = "u_positionTexture";
  private static final String UNIFORM_AMBIENT_LIGHT_COLOR = "u_ambientLight";
  public final GBuffer gBuffer;
  private final ShaderManager sm;

  public MRTRenderingEngine(PerspectiveCamera camera) {
    super(camera);
    this.gBuffer = new GBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), renderContext);

    this.sm = HashBot.shaders;
    sm.createFB(FRAMEBUFFER_DEFFERED_RENDERING);
    Gdx.app.log(TAG, "Created");
  }

  @Override
  protected DefferedModelBatch buildModelBatch() {
    return new DefferedModelBatch(renderContext);
  }

  @Override
  protected void beforeRender() {
    gBuffer.begin();
    gBuffer.clear();
  }

  @Override
  protected void afterRender() {
    gBuffer.end();

    compositLights();

    sm.begin(Shaders.SHADER_DEFAULT); {
      sm.renderFB(FRAMEBUFFER_DEFFERED_RENDERING);
    } sm.end();
  }

  private void compositLights() {
    ColorAttribute ambientLight = (ColorAttribute) environment.get(ColorAttribute.AmbientLight);

    sm.beginFB(FRAMEBUFFER_DEFFERED_RENDERING); {
      sm.begin(Shaders.SHADER_DEFFERED_COMPOSIT); {
        sm.setUniformi(UNIFORM_TEXTURE_COLOR,    renderContext.textureBinder.bind(gBuffer.colorAttachment));
        sm.setUniformi(UNIFORM_TEXTURE_NORMAL,   renderContext.textureBinder.bind(gBuffer.normalsAttachment));
        sm.setUniformi(UNIFORM_TEXTURE_POSITION, renderContext.textureBinder.bind(gBuffer.positionAttachment));
        sm.setUniformf(UNIFORM_AMBIENT_LIGHT_COLOR, ambientLight.color);
        sm.render();
      }sm.end();
    } sm.endFB();
  }

  @Override
  public void dispose() {
    super.dispose();
    gBuffer.dispose();
  }
}
