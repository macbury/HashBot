package de.macbury.hashbot.core;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.ExtLwjgGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.macbury.hashbot.core.graphics.tweens.ScreenManagerAccessor;
import de.macbury.hashbot.core.graphics.tweens.UIStageAccessor;
import de.macbury.hashbot.core.graphics.tweens.UITableAccessor;
import de.macbury.hashbot.core.graphics.ui.Overlay;
import de.macbury.hashbot.core.graphics.ui.widgets.UIStage;
import de.macbury.hashbot.core.graphics.ui.widgets.UITable;
import de.macbury.hashbot.core.i18n.I18n;
import de.macbury.hashbot.core.input.InputManager;
import de.macbury.hashbot.core.managers.*;

/**
 * Created by macbury on 18.04.14.
 */
public class HashBot extends Game {
  private static final String TAG = "#bot";

  private static final String GAME_NAME = "#bot";
  public static HashBot game;
  public static Assets assets;
  public static UIManager ui;
  public static ScreenManager screens;
  public static MusicManager music;
  public static ModelsManager models;
  public static I18n i18n;
  public static ConfigManager config;
  public static ArgsManager args;
  public static InputManager input;
  public static ProfileManager profile;
  public static StorageManager storage;

  private Overlay overlay;

  @Override
  public void create() {
    if (args.debug) {
      Gdx.app.log(TAG, "Enabled debug");
      Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    Gdx.app.debug(TAG, "Initializing");
    Gdx.app.debug(TAG, "Version: " + Gdx.app.getVersion());

    this.overlay       = new Overlay();

    HashBot.game       = this;
    HashBot.input      = new InputManager();
    HashBot.i18n       = new I18n();
    HashBot.config     = new ConfigManager();
    HashBot.assets     = new Assets(this);
    HashBot.screens    = new ScreenManager(this);
    HashBot.ui         = new UIManager();
    HashBot.music      = new MusicManager();
    HashBot.models     = new ModelsManager();
    HashBot.profile    = new ProfileManager();
    HashBot.storage    = new StorageManager();

    Tween.registerAccessor(UIStage.class, new UIStageAccessor());
    Tween.registerAccessor(UITable.class, new UITableAccessor());
    Tween.registerAccessor(ScreenManager.class, new ScreenManagerAccessor());
    screens.openLoadingScreen();
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    overlay.resize(width, height);
    if (screens != null)
      screens.resize(width, height);
  }

  @Override
  public void render() {
    super.render();
    screens.render();
    overlay.draw();
  }

  @Override
  public void dispose() {
    assets.dispose();
    screens.dispose();
    super.dispose();
  }


  public LwjglApplication start(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = GAME_NAME;
    config.width = 1024;
    config.height = 768;
    config.fullscreen = false;
    config.forceExit = true;
    config.resizable = false;
    HashBot.args = new ArgsManager(args);
    return new LwjglApplication(this, config, new ExtLwjgGraphics(config));
  }

  public boolean isDebug() {
    return args.debug;
  }
}
