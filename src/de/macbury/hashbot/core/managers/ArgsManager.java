package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by macbury on 30.04.14.
 */
public class ArgsManager {
  private static final CharSequence DEBUG_FLAG = "debug";
  private static final CharSequence EDITOR_FLAG = "editor";
  private static final CharSequence FPS_FLAG = "fps";
  public boolean fps;
  public boolean startWithEditor;
  public boolean debug;

  public ArgsManager(String[] args) {
    for(String arg : args) {
      if (arg.contains(DEBUG_FLAG)) {
        this.debug = true;
      }

      if (arg.contains(EDITOR_FLAG)) {
        this.startWithEditor = true;
      }

      if (arg.contains(FPS_FLAG)) {
        this.fps = true;
      }
    }
  }
}
