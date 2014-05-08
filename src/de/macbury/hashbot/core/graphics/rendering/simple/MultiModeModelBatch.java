package de.macbury.hashbot.core.graphics.rendering.simple;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.macbury.hashbot.core.graphics.models.RenderDebugStats;
import de.macbury.hashbot.core.graphics.rendering.mrt.model_batch.DefferedShaderProvider;

/**
 * Created by macbury on 07.05.14.
 */
public class MultiModeModelBatch extends ModelBatch {
  private ShapeRenderer shapeDebugger;
  private RenderDebugStats stats;

  public MultiModeModelBatch(RenderContext renderContext) {
    super(renderContext);
    this.shapeDebugger = new ShapeRenderer();
  }

  public MultiModeModelBatch(RenderContext renderContext, DefferedShaderProvider defferedShaderProvider) {
    super(renderContext,defferedShaderProvider);
    this.shapeDebugger = new ShapeRenderer();
  }

  @Override
  public void begin(Camera cam) {
    super.begin(cam);
    this.getStats().reset();
  }

  @Override
  public void flush() {
    if (getStats().isEnabled()) {
      for(Renderable renderable : renderables) {
        //renderable.primitiveType = GL30.GL_LINE_STRIP;
        getStats().renderables++;
        getStats().verticies += renderable.mesh.getMaxVertices();
      }
    }
    super.flush();
  }

  public RenderDebugStats getStats() {
    if (stats == null)
      stats = new RenderDebugStats();
    return stats;
  }
}
