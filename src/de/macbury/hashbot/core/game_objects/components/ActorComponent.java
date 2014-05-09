package de.macbury.hashbot.core.game_objects.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import de.macbury.hashbot.core.graphics.utils.CallBackVector3;
import de.macbury.hashbot.core.graphics.utils.Vector3Listener;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.partition.GameObjectTree;
import de.macbury.hashbot.core.partition.QuadTree;
import de.macbury.hashbot.core.partition.QuadTreeObject;

/**
 * Created by macbury on 03.05.14.
 */
public class ActorComponent extends Component implements QuadTreeObject, Vector3Listener {
  protected BoundingBox boundingBox;

  public CallBackVector3 position;
  public CallBackVector3 size;
  protected QuadTree parent;
  protected Vector3 temp;
  protected boolean visible = true;
  protected boolean skipCulling = false;
  protected boolean visibleInRenderTree = false;
  protected boolean modified = true;
  public Quaternion rotation;

  public ActorComponent() {
    this.position    = new CallBackVector3();
    this.size        = new CallBackVector3(Block.BLOCK_SIZE,Block.BLOCK_SIZE,Block.BLOCK_SIZE);
    this.position.setListener(this);
    this.size.setListener(this);
    this.boundingBox = new BoundingBox();
    this.rotation    = new Quaternion();
    this.temp        = new Vector3();
  }

  @Override
  public BoundingBox getBoundingBox() {
    return boundingBox.set(position, temp.set(position).add(size));
  }

  @Override
  public void setQuadTreeParent(QuadTree parent) {
    this.parent = parent;
  }

  public boolean shouldRender() {
    if (isVisible()) {
      return isSkipCulling() || isVisibleInRenderTree();
    } else {
      return false;
    }
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean isSkipCulling() {
    return skipCulling;
  }

  public void setSkipCulling(boolean skipCulling) {
    this.skipCulling = skipCulling;
  }

  public boolean isVisibleInRenderTree() {
    return visibleInRenderTree;
  }

  public void setVisibleInRenderTree(boolean visibleInRenderTree) {
    this.visibleInRenderTree = visibleInRenderTree;
  }

  @Override
  public void onVector3Change(Vector3 vector) {
    modified = true;
  }

  public boolean isModified() {
    return modified;
  }

  public void setModified(boolean modified) {
    this.modified = modified;
  }

  public QuadTree getParent() {
    return parent;
  }

  public void setParent(QuadTree parent) {
    this.parent = parent;
  }

  public void removeFromParentTree() {
    if (this.parent != null) {
      this.parent.remove(this);
    }
    this.parent = null;
  }
}
