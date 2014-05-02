package de.macbury.hashbot.core.progress;

/**
 * Created by macbury on 30.04.14.
 */
public abstract class GameProfile {
  protected String name;
  protected String email;
  protected String password;
  protected int experience = 0;
  protected int level = 1;

  public boolean valid() {
    return name != null && name.length() > 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public abstract void login(LoginGameProfileListener loginListener);

  public int getLevel() {
    return level;
  }

  public int getExperience() {
    return experience;
  }
  public int getRequiredExperience() {
    return level * 10; // TODO: Better calculation
  }
}
