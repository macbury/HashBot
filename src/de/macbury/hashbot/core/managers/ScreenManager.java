package de.macbury.hashbot.core.managers;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.tweens.ScreenManagerAccessor;
import de.macbury.hashbot.core.screens.BaseScreen;
import de.macbury.hashbot.core.screens.LoadingScreen;
import de.macbury.hashbot.core.screens.editor.MapEditorScreen;
import de.macbury.hashbot.core.screens.menu.MenuScreen;

/**
 * Created by macbury on 18.04.14.
 */
public class ScreenManager implements Disposable {
  private static final float FADE_SPEED = 0.5f;
  private final TweenManager tweenManager;
  private HashBot game;
  private LoadingScreen loadingScreen;
  private MenuScreen mainMenuScreen;
  private MapEditorScreen mapEditorScreen;

  private OrthographicCamera camera;
  private SpriteBatch spriteBatch;
  private float fadeAlpha = 0.0f;

  public ScreenManager(HashBot game) {
    this.game         = game;
    this.tweenManager = new TweenManager();
    this.spriteBatch  = new SpriteBatch();
    this.camera       = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void openLoadingScreen() {
    this.loadingScreen = new LoadingScreen();

    game.setScreen(loadingScreen);
  }

  public void openMainMenu() {
    if (this.mainMenuScreen == null)
      mainMenuScreen = new MenuScreen();
    transitToScreen(mainMenuScreen);
  }

  public void render() {
    tweenManager.update(Gdx.graphics.getDeltaTime());
    camera.update();
    spriteBatch.setProjectionMatrix(camera.combined);
    spriteBatch.begin();
      if (fadeAlpha > 0.0f) {
        spriteBatch.setColor(0,0,0,fadeAlpha);
        spriteBatch.draw(HashBot.ui.fadeBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      }
    spriteBatch.end();
  }

  public void resize(int width, int height) {
    camera.setToOrtho(false, width, height);
  }

  public void openMapEditor() {
    if (this.mapEditorScreen == null)
      mapEditorScreen = new MapEditorScreen();
    transitToScreen(mapEditorScreen);
  }

  private void transitToScreen(final BaseScreen nextScreen) {
    Gdx.input.setInputProcessor(null);
    Tween.to(this, ScreenManagerAccessor.FADE, FADE_SPEED).target(1).setCallback(new TweenCallback() {
      @Override
      public void onEvent(int type, BaseTween<?> source) {
        game.setScreen(nextScreen);

        Tween.to(ScreenManager.this, ScreenManagerAccessor.FADE, FADE_SPEED).target(0).setCallback(new TweenCallback() {
          @Override
          public void onEvent(int type, BaseTween<?> source) {
            nextScreen.afterFade();
          }
        }).start(tweenManager);
      }
    }).start(tweenManager);
  }

  @Override
  public void dispose() {
    this.loadingScreen.dispose();
    tweenManager.killAll();
  }

  public float getFadeAlpha() {
    return fadeAlpha;
  }

  public void setFadeAlpha(float fadeAlpha) {
    this.fadeAlpha = fadeAlpha;
  }
}
