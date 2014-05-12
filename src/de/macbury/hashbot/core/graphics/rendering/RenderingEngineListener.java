package de.macbury.hashbot.core.graphics.rendering;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.AccumulateLightsStep;

/**
 * Created by macbury on 07.05.14.
 */
public interface RenderingEngineListener {
  public void geometryPass(ModelBatch modelBatch);
  public void renderShapes(ShapeRenderer shapeRenderer);

  public void lightPass(AccumulateLightsStep accumulateLightsStep);
}
