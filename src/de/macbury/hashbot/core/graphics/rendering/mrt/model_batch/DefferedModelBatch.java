package de.macbury.hashbot.core.graphics.rendering.mrt.model_batch;

import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import de.macbury.hashbot.core.graphics.rendering.simple.MultiModeModelBatch;

/**
 * Created by macbury on 08.05.14.
 */
public class DefferedModelBatch extends MultiModeModelBatch {
  public DefferedModelBatch(RenderContext renderContext) {
    super(renderContext, new DefferedShaderProvider());

  }
}
