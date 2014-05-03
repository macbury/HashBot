package de.macbury.hashbot.core.partition;

import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import de.macbury.hashbot.core.level.map.blocks.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbury on 09.03.14.
 */
public class QuadTree {
  public static int TOP_LEFT     = 0;
  public static int TOP_RIGHT    = 1;
  public static int BOTTOM_LEFT  = 2;
  public static int BOTTOM_RIGHT = 3;
  public static int MAX_OBJECTS = 20;
  public static int MAX_LEVELS  = 8;

  private int         level;
  private ArrayList<QuadTreeObject> objects;
  private BoundingBox bounds;
  private QuadTree[]  nodes;

  private BoundingBox topRightQuadrant;
  private BoundingBox bottomLeftQuadrant;
  private BoundingBox topLeftQuadrant;
  private BoundingBox bottomRightQuadrant;
  private Vector3 aTempVec;
  private Vector3 bTempVec;
  private QuadTree parent;

  public QuadTree(int pLevel, BoundingBox nBounds) {
    level       = pLevel;
    objects     = new ArrayList<QuadTreeObject>();
    bounds      = nBounds;
    Vector3 a   = nBounds.getMin();
    a.y         = 0;
    Vector3 b   = nBounds.getMax();
    b.y         = Block.BLOCK_SIZE * 2;
    bounds.set(a, b);
    clear();
  }

  public void clear() {
    objects.clear();

    if (nodes != null) {
      for (int i = 0; i < nodes.length; i++) {
        if (nodes[i] != null) {
          nodes[i].clear();
          nodes[i] = null;
        }
      }
    }

    nodes = null;
  }

  public BoundingBox getBounds() {
    return bounds;
  }

  public int getLevel() {
    return level;
  }

  public void split() {
    Vector3 center      = bounds.getCenter().cpy();
    Vector3 min         = bounds.getMin().cpy();
    Vector3 max         = bounds.getMax().cpy();
    aTempVec            = center.cpy();
    aTempVec.y          = Block.BLOCK_SIZE * 2;
    bTempVec            = min.cpy();
    bottomLeftQuadrant  = new BoundingBox(bTempVec, aTempVec);

    aTempVec            = center.cpy();
    aTempVec.y          = 0;
    bTempVec            = max.cpy();
    bTempVec.y          = Block.BLOCK_SIZE * 2;
    topRightQuadrant    = new BoundingBox(aTempVec, bTempVec);

    aTempVec           = center.cpy();
    aTempVec.y         = 0;
    aTempVec.x         = min.x;

    bTempVec           = center.cpy();
    bTempVec.y         = Block.BLOCK_SIZE * 2;
    bTempVec.z         = max.z;
    topLeftQuadrant    = new BoundingBox(aTempVec,bTempVec);

    aTempVec           = center.cpy();
    aTempVec.y         = 0;

    bTempVec           = max.cpy();
    bTempVec.y         = Block.BLOCK_SIZE * 2;
    bTempVec.z         = min.z;
    bottomRightQuadrant = new BoundingBox(aTempVec, bTempVec);

    nodes               = new QuadTree[4];
    nodes[TOP_LEFT]     = new QuadTree(level+1, topLeftQuadrant);
    nodes[TOP_RIGHT]    = new QuadTree(level+1, topRightQuadrant);
    nodes[BOTTOM_LEFT]  = new QuadTree(level+1, bottomLeftQuadrant);
    nodes[BOTTOM_RIGHT] = new QuadTree(level+1, bottomRightQuadrant);

    nodes[TOP_LEFT].setParent(this);
    nodes[TOP_RIGHT].setParent(this);
    nodes[BOTTOM_LEFT].setParent(this);
    nodes[BOTTOM_RIGHT].setParent(this);
  }

  public int getIndex(QuadTreeObject object) {
    return getIndex(object.getBoundingBox());
  }

  public int getIndex(BoundingBox pRect) {
    int index = -1;

    if (haveNodes()) {
      for(byte i = 0; i < 4; i++){
        if (nodes[i].getBounds().contains(pRect)) {
          index = i;
          break;
        }
      }
    }

    return index;
  }

  public boolean haveNodes() {
    return nodes != null;
  }

  public void insert(QuadTreeObject object) {
    if (haveNodes()) {
      int index = getIndex(object);
      if (index != -1) {
        nodes[index].insert(object);
        return;
      }
    }

    objects.add(object);
    object.setQuadTreeParent(this);
    if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
      if (!haveNodes()) {
        split();
      }

      int i = 0;
      while (i < objects.size()) {
        int index = getIndex(objects.get(i));
        if (index != -1) {
          nodes[index].insert(objects.remove(i));
        } else {
          i++;
        }
      }
    }
  }

  public boolean remove(QuadTreeObject object) {
    int index = getIndex(object);
    if (index == -1) {
      return objects.remove(object);
    } else {
      return nodes[index].remove(object);
    }
  }

  public void retrieve(List<QuadTreeObject> returnObjects, Frustum frustum) {
    if (haveNodes()) {
      for(QuadTree node : nodes) {
        if (frustum.boundsInFrustum(node.getBounds())) {
          node.retrieve(returnObjects, frustum);
        }
      }
    }

    returnObjects.addAll(objects);
  }

  public void retrieve(List<QuadTreeObject> returnObjects, BoundingBox object) {
    int index = getIndex(object);
    if (index != -1 && haveNodes()) {
      nodes[index].retrieve(returnObjects, object);
    }

    returnObjects.addAll(objects);
  }

  public void retrieve(List<QuadTreeObject> returnObjects, QuadTreeObject object) {
    retrieve(returnObjects, object.getBoundingBox());
  }

  public QuadTree getNode(int index) {
    return nodes[index];
  }

  public float getWidth() {
    return bounds.getDimensions().x;
  }

  public float getHeight() {
    return bounds.getDimensions().z;
  }

  public QuadTree getParent() {
    return parent;
  }

  public void setParent(QuadTree parent) {
    this.parent = parent;
  }
}
