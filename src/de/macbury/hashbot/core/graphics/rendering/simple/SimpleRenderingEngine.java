package de.macbury.hashbot.core.graphics.rendering.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import de.macbury.hashbot.core.graphics.rendering.BaseRenderingEngine;
import de.macbury.hashbot.core.level.Level;

/**
 * Created by macbury on 07.05.14.
 */
public class SimpleRenderingEngine extends BaseRenderingEngine {
  private static final String TAG = "SimpleRenderingEngine";

  public SimpleRenderingEngine(Level camera) {
    super(camera);
    Gdx.app.log(TAG, "Created");
  }

  @Override
  protected MultiModeModelBatch buildGeometryBatch() {
    return new MultiModeModelBatch(renderContext);
  }

  @Override
  protected void beforeRender() {

  }

  @Override
  protected void afterRender() {

  }
}
