package de.macbury.hashbot.core.managers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.i18n.I18nLocale;

/**
 * Created by macbury on 25.04.14.
 */
public class ConfigManager {
  private static final String SETTINGS_FILE         = "SETTING";
  private static final String RESOLUTION_WIDTH      = "RESOLUTION_WIDTH";
  private static final String RESOLUTION_HEIGHT     = "RESOLUTION_HEIGHT";
  private static final String RESOLUTION_FULLSCREEN = "RESOLUTION_FULLSCREEN";
  private static final String RESOLUTION_VSYNC      = "RESOLUTION_VSYNC";
  private static final String MUSIC_VOLUME          = "MUSIC_VOLUME";
  private static final String LANGUAGE              = "LANGUAGE";
  private static final String QUALITY               = "QUALITY";
  private Preferences config;

  public ConfigManager() {
    this.config = Gdx.app.getPreferences(SETTINGS_FILE);
    load();
  }

  public void putResolution(int width, int height, boolean fullscreen, boolean vsync) {
    config.putInteger(RESOLUTION_WIDTH, width);
    config.putInteger(RESOLUTION_HEIGHT, height);
    config.putBoolean(RESOLUTION_FULLSCREEN, fullscreen);
    config.putBoolean(RESOLUTION_VSYNC, vsync);
    config.flush();
  }

  public void loadResolution() {
    Gdx.graphics.setVSync(config.getBoolean(RESOLUTION_VSYNC, true));
    Gdx.graphics.setDisplayMode(config.getInteger(RESOLUTION_WIDTH, 1024), config.getInteger(RESOLUTION_HEIGHT, 768), config.getBoolean(RESOLUTION_FULLSCREEN, false));

  }

  public void setQuality(boolean goodQuality) {
    config.putBoolean(QUALITY, goodQuality);
    config.flush();
  }

  public boolean isGoodQuality() {
    return config.getBoolean(QUALITY, false);
  }

  public String getLanguage() {
    return config.getString(LANGUAGE, "Polski");
  }

  public void setLanguage(String name) {
    config.putString(LANGUAGE, name);
    config.flush();
  }

  public void setMusicVolume(float volume) {
    config.putFloat(MUSIC_VOLUME, volume);
    config.flush();
  }

  public void load() {
    loadResolution();
    loadAudio();
    HashBot.i18n.setLocale(getLanguage());
    HashBot.game.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void loadAudio() {
    //BotLogic.audio.mainMenuMusic.setVolume(getMusicVolume());
    //BotLogic.audio.gameMusic.setVolume(getMusicVolume());
  }

  public float getMusicVolume() {
    return config.getFloat(MUSIC_VOLUME, 0.6f);
  }

  public boolean isVSync() {
    return config.getBoolean(RESOLUTION_VSYNC, false);
  }
}
