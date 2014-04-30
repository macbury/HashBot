package de.macbury.hashbot.core.screens;

import com.badlogic.gdx.Screen;

/**
 * Created by macbury on 25.04.14.
 */
public abstract class BaseScreen implements Screen {
  public abstract void afterFadeIn();
  public abstract void afterFadeOut();
}
