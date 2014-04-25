package de.macbury.hashbot.core.screens.menu.table;

import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.widgets.UIButton;
import de.macbury.hashbot.core.graphics.ui.widgets.UITable;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuSettingsTable extends UITable {
  private static final int PADDING = 10;
  private static final int MENU_ITEM_WIDTH = 320;
  private UIButton backButton;

  public MenuSettingsTable() {
    super();

    this.backButton = HashBot.ui.menuBackButton();
    setFillParent(true);
    setTransform(true);
    row().padTop(PADDING).padLeft(PADDING);
    add(HashBot.ui.textButton("TEST")).fillX();
    add().colspan(2);
    row().expand();
    add().colspan(3);
    row();
    add().expandX().colspan(3);
    add(backButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
  }
}
