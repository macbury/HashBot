package de.macbury.hashbot.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.managers.Assets;

/**
 * Created by macbury on 18.04.14.
 */
public class LoadingScreen implements Screen, Assets.AssetsListener {
  private static final String TAG = "LoadingScreen";
  private SpriteBatch spriteBatch;
  private BitmapFont cyberFont;

  public LoadingScreen() {
    this.cyberFont   = HashBot.assets.get(Assets.FONT_UI);
    this.spriteBatch = new SpriteBatch();
    HashBot.assets.setListener(this);
    Gdx.app.debug(TAG, "Initialized");
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    spriteBatch.begin();{
      HashBot.assets.update();

      float progress = HashBot.assets.getProgress();
      Gdx.app.debug(TAG, "Loading: " + progress);

      cyberFont.draw(spriteBatch, HashBot.i18n.t("loading_screen.loading", Math.round(progress * 100)), 10, 30);
    } spriteBatch.end();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void show() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }

  @Override
  public void assetsDidLoad() {
    HashBot.screens.openMainMenu();
  }
}
