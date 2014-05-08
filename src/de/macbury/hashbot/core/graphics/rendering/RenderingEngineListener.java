package de.macbury.hashbot.core.graphics.rendering;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by macbury on 07.05.14.
 */
public interface RenderingEngineListener {
  public void renderModels(ModelBatch modelBatch, Environment env);
  public void renderShapes(ShapeRenderer shapeRenderer);
}
