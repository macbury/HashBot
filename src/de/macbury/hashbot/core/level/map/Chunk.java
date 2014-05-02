package de.macbury.hashbot.core.level.map;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.partition.QuadTreeObject;

/**
 * Created by macbury on 01.05.14.
 */
public class Chunk extends Renderable implements Disposable, QuadTreeObject {
  public static final int SIZE = 10;
  public final static int CHUNK_WIDTH = SIZE * Block.BLOCK_SIZE; //size in meters
  private boolean dirty = true;
  private Vector3 start;
  private BoundingBox boundingBox;

  public Chunk(int bx, int by, Map map) {
    Vector3 tempVec   = new Vector3(bx * CHUNK_WIDTH, 0, by * CHUNK_WIDTH);
    this.start        = new Vector3(bx * SIZE, 0, by * SIZE);
    this.boundingBox  = new BoundingBox(tempVec, tempVec.cpy().add(CHUNK_WIDTH, Block.BLOCK_HEIGHT, CHUNK_WIDTH));
  }

  @Override
  public void dispose() {
    mesh.dispose();
  }

  public void build() {

  }

  public void update() {
    if (dirty) {
      build();
    }
    dirty = false;
  }

  @Override
  public String toString() {
    return "Chunk: "+start.x + "x"+start.z + " box " + boundingBox.toString();
  }

  @Override
  public BoundingBox getBoundingBox() {
    return boundingBox;
  }
}
