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
    Music music = HashBot.assets.get(Assets.MUSIC_MENU);
    return music;
  }
}
