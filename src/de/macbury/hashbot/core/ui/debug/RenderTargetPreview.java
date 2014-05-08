package de.macbury.hashbot.core.ui.debug;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import de.macbury.hashbot.core.graphics.rendering.mrt.buffer.GBufferAttachment;

/**
 * Created by macbury on 08.05.14.
 */
public class RenderTargetPreview extends Widget {
  private GBufferAttachment attachment;

  public RenderTargetPreview(GBufferAttachment attachment) {
    this.attachment = attachment;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    batch.draw(attachment.proxy(), getX(),getY(), getPrefWidth(), getPrefHeight());
  }

  @Override
  public float getPrefWidth() {
    return 320;
  }

  @Override
  public float getPrefHeight() {
    return 240;
  }
}
