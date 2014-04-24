package de.macbury.hashbot.core.screens.menu;

import com.badlogic.gdx.graphics.Color;
import de.macbury.hashbot.core.graphics.ui.UIStage;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuStage extends UIStage {

  private MenuScreen screen;
  public MenuOptionsTable menuTable;

  public MenuStage(MenuScreen screen) {
    super();
    this.screen = screen;
    menuTable = new MenuOptionsTable(screen);
  }

}
