package de.macbury.hashbot.core.progress;

/**
 * Created by macbury on 01.05.14.
 */
public interface LoginGameProfileListener {
  public void onLoginSuccess(GameProfile profile);
  public void onLoginError(GameProfile profile, Error error);
}
