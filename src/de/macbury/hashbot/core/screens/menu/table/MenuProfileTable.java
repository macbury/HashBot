package de.macbury.hashbot.core.screens.menu.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.ui.widgets.UIButton;
import de.macbury.hashbot.core.ui.widgets.UITable;
import de.macbury.hashbot.core.progress.GameProfile;
import de.macbury.hashbot.core.progress.GuestProfile;
import de.macbury.hashbot.core.progress.LoginGameProfileListener;
import de.macbury.hashbot.core.screens.menu.MenuStage;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuProfileTable extends UITable implements LoginGameProfileListener {
  private static final int PADDING = 10;
  private static final int MENU_ITEM_WIDTH = 320;
  private MenuStage menuStage;
  private UIButton guestProfileButton;
  private UIButton loginProfileButton;
  private UIButton createProfileButton;

  public MenuProfileTable(MenuStage menuStage) {
    super();
    this.menuStage = menuStage;
    this.createProfileButton = HashBot.ui.menuI18nButton("profile_screen.create");
    this.loginProfileButton  = HashBot.ui.menuI18nButton("profile_screen.login");
    this.guestProfileButton  = HashBot.ui.menuI18nButton("profile_screen.guest");

    this.guestProfileButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        MenuProfileTable.this.authProfile(new GuestProfile());
      }
    });

    setFillParent(true);
    setTransform(true);

    row().padTop(PADDING).padRight(PADDING).padBottom(PADDING); {
      add().width(Gdx.graphics.getWidth() * 0.3f);
      add(HashBot.ui.labelI18n("profile_screen.title")).left().fill().expandX();
    }

    row().padRight(PADDING).padBottom(PADDING); {
      add();
      add(loginProfileButton).fill();
    }

    row().padRight(PADDING).padBottom(PADDING); {
      add();
      add(createProfileButton).fill();
    }

    row().padRight(PADDING).padBottom(PADDING); {
      add();
      add(guestProfileButton).fill();
    }
  }

  private void authProfile(GameProfile profile) {
    profile.login(this);
  }

  @Override
  public void onLoginSuccess(GameProfile profile) {
    HashBot.profile.setCurrentProfile(profile);
    menuStage.goToDefault();
  }

  @Override
  public void onLoginError(GameProfile profile, Error error) {

  }
}
