package de.macbury.hashbot.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.ExtLwjgGraphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import de.macbury.hashbot.core.i18n.I18n;
import de.macbury.hashbot.core.i18n.I18nLocale;
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

  private String[] args;
  private boolean debug;

  @Override
  public void create() {

    for(String arg : args) {
      if (arg.contains("debug")) {
        this.debug = true;
        Gdx.app.log(TAG, "Enabled debug");
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
      }
    }

    Gdx.app.debug(TAG, "Initializing");
    Gdx.app.debug(TAG, "Version: " + Gdx.app.getVersion());
    HashBot.game       = this;
    HashBot.assets     = new Assets(this);
    HashBot.screens    = new ScreenManager(this);
    HashBot.ui         = new UIManager();
    HashBot.music      = new MusicManager();
    HashBot.models     = new ModelsManager();
    HashBot.i18n       = new I18n(Gdx.files.internal("i18n/pl.yml"));
    screens.openLoadingScreen();
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    assets.dispose();
    screens.dispose();
    super.dispose();
  }

  public void setArgs(String[] args) {
    this.args = args;
  }

  public LwjglApplication start(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = GAME_NAME;
    config.width = 1024;
    config.height = 768;
    config.fullscreen = false;
    config.forceExit = true;
    config.resizable = false;
    setArgs(args);
    return new LwjglApplication(this, config, new ExtLwjgGraphics(config));
  }

  public boolean isDebug() {
    return debug;
  }
}
