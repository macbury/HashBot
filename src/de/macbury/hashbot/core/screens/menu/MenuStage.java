package de.macbury.hashbot.core.screens.menu;

import de.macbury.hashbot.core.graphics.ui.widgets.UIStage;
import de.macbury.hashbot.core.screens.menu.table.MenuDefaultTable;
import de.macbury.hashbot.core.screens.menu.table.MenuSettingsTable;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuStage extends UIStage {

  private final MenuSettingsTable menuSettingsTable;
  private MenuScreen screen;
  public MenuDefaultTable menuTable;

  public MenuStage(MenuScreen screen) {
    super();
    this.screen = screen;
    menuTable = new MenuDefaultTable(screen);
    menuSettingsTable = new MenuSettingsTable();
  }


  public void goToDefault() {
    setCurrentTable(menuTable);
  }

  public void goToSettings() {
    setCurrentTable(menuSettingsTable);
  }
}
