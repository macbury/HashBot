package de.macbury.hashbot.core.graphics.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 24.04.14.
 */
public class CursorOverlay {

  private OrthographicCamera camera;
  private SpriteBatch spriteBatch;

  public CursorOverlay() {
    this.spriteBatch = new SpriteBatch();
    this.camera      = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void draw() {
    camera.update();
    spriteBatch.setProjectionMatrix(camera.combined);
    if (HashBot.ui.currentCursor != null)
      HashBot.ui.currentCursor.draw(spriteBatch);
  }

  public void resize(int width, int height) {
    camera.setToOrtho(false, width, height);
  }
}
