package de.macbury.hashbot.core.graphics.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.graphics.rendering.simple.MultiModeModelBatch;
import de.macbury.hashbot.core.level.Level;

/**
 * Created by macbury on 07.05.14.
 */
public abstract class BaseRenderingEngine implements Disposable {
  public Level level;
  public DirectionalLight sunLight;
  public ColorAttribute fog;
  public ColorAttribute ambientLight;
  public PerspectiveCamera mainCamera;
  public ShapeRenderer shapes;
  public RenderContext renderContext;
  public MultiModeModelBatch models;
  private RenderingEngineListener listener;

  public BaseRenderingEngine(Level level) {
    renderContext     = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    models            = buildGeometryBatch();
    shapes            = new ShapeRenderer();
    this.mainCamera   = level.camera;
    this.level        = level;
    ambientLight      = new ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.1f, 0.1f, 1f);
    fog               = new ColorAttribute(ColorAttribute.Fog, 0f, 0f, 0f, 1f);
    sunLight          = new DirectionalLight().set(new Color(.8f,.8f, .8f, 0.1f), new Vector3(1,-1,1));
  }

  protected abstract MultiModeModelBatch buildGeometryBatch();

  public void render() {
    beforeRender(); {
      renderContext.begin(); {
        renderContext.setDepthMask(true);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        models.begin(mainCamera); {
          listener.geometryPass(models);
        } models.end();
      } renderContext.end();

    } afterRender();

    renderContext.begin(); {
        renderContext.setDepthMask(true);
        renderContext.setDepthTest(GL30.GL_LEQUAL);
        shapes.setProjectionMatrix(mainCamera.combined);
        listener.renderShapes(shapes);
      } renderContext.end();
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
