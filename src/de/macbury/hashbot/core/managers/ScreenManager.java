package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.screens.LoadingScreen;
import de.macbury.hashbot.core.screens.MenuScreen;

/**
 * Created by macbury on 18.04.14.
 */
public class ScreenManager implements Disposable {
  private HashBot game;
  private LoadingScreen loadingScreen;
  private MenuScreen mainMenuScreen;

  public ScreenManager(HashBot game) {
    this.game = game;
  }

  public void openLoadingScreen() {
    this.loadingScreen = new LoadingScreen();
    game.setScreen(loadingScreen);
  }

  public void openMainMenu() {
    if (this.mainMenuScreen == null)
      mainMenuScreen = new MenuScreen();
    game.setScreen(mainMenuScreen);
  }

  @Override
  public void dispose() {
    this.loadingScreen.dispose();
  }
}
