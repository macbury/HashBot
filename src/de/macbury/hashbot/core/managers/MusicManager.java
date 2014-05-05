package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.audio.Music;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 22.04.14.
 */
public class MusicManager {
  public void load() {

  }

  public Music mainMenu() {
    return applyConfig((Music)HashBot.assets.get(Assets.MUSIC_MENU));
  }

  private Music applyConfig(Music music) {
    music.setVolume(HashBot.config.getMusicVolume());
    return music;
  }
}
