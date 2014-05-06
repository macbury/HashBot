package de.macbury.hashbot.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.time.BaseTimer;
import de.macbury.hashbot.core.time.IntervalTimer;
import de.macbury.hashbot.core.time.TimerListener;

/**
 * Created by macbury on 24.04.14.
 */
public class Overlay implements TimerListener {

  private IntervalTimer fpsTimer;
  private OrthographicCamera camera;
  private SpriteBatch spriteBatch;
  private CharSequence currentFPSText;

  public Overlay() {
    this.spriteBatch = new SpriteBatch();
    this.camera      = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.fpsTimer    = new IntervalTimer(1);
    fpsTimer.setListener(this);
    updateFPS();
  }

  private void updateFPS() {
    currentFPSText = "FPS: " + Gdx.graphics.getFramesPerSecond();
  }

  public void draw() {
    fpsTimer.update(Gdx.graphics.getDeltaTime());
    camera.update();
    spriteBatch.setProjectionMatrix(camera.combined);
    if (HashBot.ui.currentCursor != null)
      HashBot.ui.currentCursor.draw(spriteBatch);
    if (HashBot.args.fps && HashBot.ui != null && HashBot.ui.uiFontSmall != null) {
      spriteBatch.begin();
        HashBot.ui.uiFontSmall.setColor(Color.WHITE);
        HashBot.ui.uiFontSmall.draw(spriteBatch, currentFPSText, Gdx.graphics.getWidth() - 100,20);
      spriteBatch.end();
    }
  }

  public void resize(int width, int height) {
    camera.setToOrtho(false, width, height);
  }

  @Override
  public void timerTick(BaseTimer sender) {
    updateFPS();
  }
}
