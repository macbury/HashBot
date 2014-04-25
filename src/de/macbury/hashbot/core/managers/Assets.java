package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 18.04.14.
 */
public class Assets extends AssetManager {
  private static final String FONT_PREFIX   = "fonts/";
  private static final String MUSIC_PREFIX  = "audio/music/";
  private static final String SOUND_PREFIX  = "audio/effects/";
  private static final String UI_PREFIX     = "ui/";
  private static final String MODELS_PREFIX     = "models/";

  public static final String FONT_UI        = FONT_PREFIX + "ui.fnt";
  public static final String FONT_UI_SMALL  = FONT_PREFIX + "ui_small.fnt";
  public static final String FONT_CODE      = FONT_PREFIX + "code.fnt";
  public static final String MUSIC_MENU     = MUSIC_PREFIX + "menu.mp3";

  public static final String ATLAS_UI                 = UI_PREFIX + "gui.atlas";
  public static final String CURSOR_PLACEHOLDER       = UI_PREFIX + "cursor_placeholder.png";

  public static final String MODEL_MENU_LOGO      = MODELS_PREFIX + "logo.g3db";

  public static final String SOUND_CLICK          = SOUND_PREFIX + "click.wav";
  public static final String SOUND_HOVER          = SOUND_PREFIX + "hover.wav";

  private HashBot game;
  private boolean didLoad = false;

  private AssetsListener listener;

  public Assets(HashBot hashBot) {
    super();
    this.game = hashBot;
    this.loadBaseAssets();
  }

  public void loadBaseAssets() {
    load(FONT_UI, BitmapFont.class);
    finishLoading();
    pushPendingAssets();
  }

  private void pushPendingAssets() {
    didLoad = false;
    load(ATLAS_UI, TextureAtlas.class);
    load(FONT_UI_SMALL, BitmapFont.class);
    load(CURSOR_PLACEHOLDER, Pixmap.class);
    load(MUSIC_MENU, Music.class);
    load(FONT_CODE, BitmapFont.class);

    load(MODEL_MENU_LOGO, Model.class);

    load(SOUND_CLICK, Sound.class);
    load(SOUND_HOVER, Sound.class);
  }

  @Override
  public synchronized boolean update() {
    boolean loaded = super.update();
    if (loaded && !didLoad && listener != null) {
      didLoad = true;
      createAssetsManagers();
      listener.assetsDidLoad();
    }
    return loaded;
  }

  private void createAssetsManagers() {
    HashBot.ui.load();
    HashBot.music.load();
  }

  public void setListener(AssetsListener listener) {
    this.listener = listener;
  }

  public interface AssetsListener {
    public void assetsDidLoad();
  }
}
