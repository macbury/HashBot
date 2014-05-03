package de.macbury.hashbot.core.partition;

import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by macbury on 09.03.14.
 */
public interface QuadTreeObject {
  public BoundingBox getBoundingBox();
  public void setQuadTreeParent(QuadTree parent);
}
