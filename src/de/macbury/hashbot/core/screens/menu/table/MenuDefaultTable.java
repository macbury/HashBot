package de.macbury.hashbot.core.screens.menu.table;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.widgets.UIButton;
import de.macbury.hashbot.core.graphics.ui.widgets.UITable;
import de.macbury.hashbot.core.screens.menu.MenuOptionsListener;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuDefaultTable extends UITable {
  private static final int PADDING = 10;
  private static final int MENU_ITEM_WIDTH = 320;
  public UIButton aboutButton;
  public UIButton settingsButton;
  public UIButton mapEditorButton;
  public UIButton playgroundButton;
  public UIButton newGameButton;
  public UIButton exitButton;
  public UIButton continueButton;

  private MenuOptionsListener listener;


  public MenuDefaultTable(MenuOptionsListener listener) {
    super();
    this.listener = listener;
    setFillParent(true);
    setTransform(true);

    this.exitButton = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.exit"));
    exitButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        MenuDefaultTable.this.listener.onExitButtonClick();
      }
    });

    this.continueButton   = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.continue"));
    this.newGameButton    = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.new_game"));
    this.playgroundButton = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.playground"));
    this.aboutButton      = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.about"));
    this.settingsButton   = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.settings"));
    this.settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        MenuDefaultTable.this.listener.onSettingsButtonClick();
      }
    });
    this.mapEditorButton  = HashBot.ui.menuButton(HashBot.i18n.t("menu_screen.map_editor"));
    mapEditorButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        MenuDefaultTable.this.listener.onMapEditButtonClick();
      }
    });
    row().expand();
    add().colspan(2);
    row();
    add().expandX();
    add(continueButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(newGameButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(playgroundButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(mapEditorButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(settingsButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();
    add(aboutButton).padBottom(PADDING).padRight(PADDING).width(MENU_ITEM_WIDTH);
    row();
    add().expandX();

    add(exitButton).width(MENU_ITEM_WIDTH).padBottom(PADDING).padRight(PADDING);

  }
}
