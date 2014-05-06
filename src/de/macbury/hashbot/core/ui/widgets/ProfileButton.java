package de.macbury.hashbot.core.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.progress.GameProfile;

/**
 * Created by macbury on 01.05.14.
 */
public class ProfileButton extends Button {
  private static final float AVATAR_WIDTH = 96;
  private static final float AVATAR_PADDING = 5;
  private static final float PADDING = 10;
  private static final float PROFILE_NAME_PADDING = 8;
  private Label levelLabel;
  private UIButton.UITextButtonStyle style;
  private Image avatarImage;
  private Label expPointsLabel;
  private UITable profileDetailsTable;
  private Label profileName;
  private GameProfile gameProfile;
  private UITable contentTable;

  public ProfileButton(GameProfile profile, UIButton.UITextButtonStyle normalButtonStyle) {
    super(normalButtonStyle);
    style = normalButtonStyle;
    this.gameProfile  = profile;
    this.contentTable = new UITable();
    add(contentTable).fill().expand();

    this.profileName = HashBot.ui.label("...");
    this.expPointsLabel = HashBot.ui.label("Exp: 0pkt");
    this.levelLabel = HashBot.ui.label("Poziom: 1");
    this.profileDetailsTable = new UITable();
    this.avatarImage = new Image();

    profileDetailsTable.row().padBottom(PROFILE_NAME_PADDING); {
      profileDetailsTable.add(profileName).expandX().fill();
    }
    profileDetailsTable.row(); {
      profileDetailsTable.add(levelLabel).expandX().fill();
    }
    profileDetailsTable.row(); {
      profileDetailsTable.add(expPointsLabel).expandX().fill();
    }

    profileDetailsTable.row(); {
      profileDetailsTable.add().expand().fill();
    }

    contentTable.row().pad(PADDING, AVATAR_PADDING, PADDING, AVATAR_PADDING); {
      contentTable.add(avatarImage).width(AVATAR_WIDTH).padRight(AVATAR_PADDING).height(AVATAR_WIDTH).left();
      contentTable.add(profileDetailsTable).expand().fill();
    }

    addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        if (ProfileButton.this.style.hoverSound != null && fromActor == null) {
          ProfileButton.this.style.hoverSound.play();
        }
      }

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (ProfileButton.this.style.clickSound != null) {
          ProfileButton.this.style.clickSound.play();
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  @Override
  public void act(float delta) {
    super.act(delta);

  }

  public void setGameProfile(GameProfile gameProfile) {
    this.gameProfile = gameProfile;
    if (gameProfile != null) {
      profileName.setText(gameProfile.getName());
      levelLabel.setText(HashBot.i18n.t("profile.level", gameProfile.getLevel()));
      expPointsLabel.setText(HashBot.i18n.t("profile.exp", gameProfile.getExperience(), gameProfile.getRequiredExperience()));
      avatarImage.setDrawable(HashBot.ui.skin.getDrawable("avatar_default"));
      avatarImage.setWidth(AVATAR_WIDTH);
      avatarImage.setHeight(AVATAR_WIDTH);

    }
  }
}
