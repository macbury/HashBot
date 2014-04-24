package de.macbury.hashbot.core.graphics.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 24.04.14.
 */
public class CursorOverlay {

  private SpriteBatch spriteBatch;

  public CursorOverlay() {
    this.spriteBatch = new SpriteBatch();
  }

  public void draw() {
    if (HashBot.ui.currentCursor != null)
      HashBot.ui.currentCursor.draw(spriteBatch);
  }
}
