package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.screens.BaseScreen;
import de.macbury.hashbot.core.screens.LoadingScreen;
import de.macbury.hashbot.core.screens.editor.MapEditorScreen;
import de.macbury.hashbot.core.screens.menu.MenuScreen;

/**
 * Created by macbury on 18.04.14.
 */
public class ScreenManager implements Disposable {
  private HashBot game;
  private LoadingScreen loadingScreen;
  private MenuScreen mainMenuScreen;
  private MapEditorScreen mapEditorScreen;

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
    transitToScreen(mainMenuScreen);
  }

  public void openMapEditor() {
    if (this.mapEditorScreen == null)
      mapEditorScreen = new MapEditorScreen();
    transitToScreen(mapEditorScreen);
  }

  private void transitToScreen(BaseScreen nextScreen) {
    game.setScreen(nextScreen);
    nextScreen.afterFade();
  }

  @Override
  public void dispose() {
    this.loadingScreen.dispose();
  }
}
