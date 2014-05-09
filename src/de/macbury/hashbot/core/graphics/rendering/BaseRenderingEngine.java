package de.macbury.hashbot.core.graphics.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.graphics.rendering.simple.MultiModeModelBatch;

/**
 * Created by macbury on 07.05.14.
 */
public abstract class BaseRenderingEngine implements Disposable {
  public PerspectiveCamera mainCamera;
  public Environment environment;
  public ShapeRenderer shapes;
  public RenderContext renderContext;
  public MultiModeModelBatch models;
  private RenderingEngineListener listener;

  public BaseRenderingEngine(PerspectiveCamera camera) {
    renderContext     = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    models            = buildModelBatch();
    shapes            = new ShapeRenderer();
    this.mainCamera   = camera;

    environment       = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
    environment.set(new ColorAttribute(ColorAttribute.Fog, 0f, 0f, 0f, 1f));
    environment.add(new DirectionalLight().set(new Color(.4f,.4f, .4f, 0.1f), new Vector3(1,-1,0)));
  }

  protected abstract MultiModeModelBatch buildModelBatch();

  public void render() {
    beforeRender(); {
      renderContext.begin(); {
        renderContext.setDepthMask(true);
        models.begin(mainCamera); {
          listener.renderModels(models, environment);
        } models.end();
      } renderContext.end();

      renderContext.begin(); {
        renderContext.setDepthMask(true);
        renderContext.setDepthTest(GL30.GL_LEQUAL);
        shapes.setProjectionMatrix(mainCamera.combined);
        listener.renderShapes(shapes);
      } renderContext.end();
    } afterRender();
  }

  protected abstract void afterRender();
  protected abstract void beforeRender();

  public void end() {
    renderContext.end();
  }

  @Override
  public void dispose() {
    models.dispose();
    shapes.dispose();
  }

  public RenderingEngineListener getListener() {
    return listener;
  }

  public void setListener(RenderingEngineListener listener) {
    this.listener = listener;
  }
}
