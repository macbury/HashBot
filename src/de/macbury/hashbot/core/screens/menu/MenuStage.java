package de.macbury.hashbot.core.screens.menu;

import de.macbury.hashbot.core.ui.widgets.UIStage;
import de.macbury.hashbot.core.screens.menu.table.MenuDefaultTable;
import de.macbury.hashbot.core.screens.menu.table.MenuProfileTable;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuStage extends UIStage {

  private MenuProfileTable menuProfileTable;
  private MenuScreen screen;
  public MenuDefaultTable menuTable;

  public MenuStage(MenuScreen screen) {
    super();
    this.screen       = screen;
    menuTable         = new MenuDefaultTable(screen);
    menuProfileTable  = new MenuProfileTable(this);
  }

  public void goToDefault() {
    menuTable.update();
    setCurrentTable(menuTable);
  }

  public void goToProfileSelect() {
    setCurrentTable(menuProfileTable);
  }
}
