package de.macbury.hashbot.core.screens.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.UIButton;
import de.macbury.hashbot.core.graphics.ui.UITable;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuOptionsTable extends UITable {
  private static final int PADDING = 10;
  private static final int MENU_ITEM_WIDTH = 320;
  private MenuOptionsListener listener;
  private UIButton exitButton;

  public MenuOptionsTable(MenuOptionsListener listener) {
    super();
    this.listener = listener;
    setFillParent(true);
    setTransform(true);

    this.exitButton = HashBot.ui.textButton(HashBot.i18n.t("menu_screen.exit"));
    exitButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        MenuOptionsTable.this.listener.onExitButtonClick();
      }
    });

    row().expand();
    add().colspan(2);
    row();
    add().expandX();
    add(HashBot.ui.textButton(HashBot.i18n.t("menu_screen.continue"))).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(HashBot.ui.textButton(HashBot.i18n.t("menu_screen.new_game"))).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(HashBot.ui.textButton(HashBot.i18n.t("menu_screen.playground"))).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(HashBot.ui.textButton(HashBot.i18n.t("menu_screen.settings"))).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(HashBot.ui.textButton(HashBot.i18n.t("menu_screen.map_editor"))).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();

    add(exitButton).width(MENU_ITEM_WIDTH).padBottom(PADDING).padRight(PADDING);

  }
}
