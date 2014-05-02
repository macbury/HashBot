package de.macbury.hashbot.core.managers;

import de.macbury.hashbot.core.progress.GameProfile;

/**
 * Created by macbury on 30.04.14.
 */
public class ProfileManager {
  private GameProfile currentProfile;

  public ProfileManager() {
    //TODO Load profile
  }

  public boolean loggedIn() {
    return currentProfile != null;
  }

  public void setCurrentProfile(GameProfile currentProfile) {
    this.currentProfile = currentProfile;
  }

  public GameProfile getCurrent() {
    return currentProfile;
  }
}
