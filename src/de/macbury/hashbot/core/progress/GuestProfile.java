package de.macbury.hashbot.core.progress;

import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 30.04.14.
 */
public class GuestProfile extends GameProfile {

  public GuestProfile() {
    super();
    this.name = HashBot.i18n.t("profile.guest");
  }

  @Override
  public void login(LoginGameProfileListener loginListener) {
    loginListener.onLoginSuccess(this);
  }
}
