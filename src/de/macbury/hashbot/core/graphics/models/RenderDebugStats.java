package de.macbury.hashbot.core.graphics.models;

/**
 * Created by macbury on 07.05.14.
 */
public class RenderDebugStats {
  public int triangles;
  public int verticies;
  public int renderables;
  private boolean enabled;
  public RenderDebugStats() {
    reset();
  }

  public void reset() {
    this.renderables = 0;
    this.verticies   = 0;
    this.triangles   = 0;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
