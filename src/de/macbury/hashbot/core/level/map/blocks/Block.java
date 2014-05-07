package de.macbury.hashbot.core.level.map.blocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.macbury.hashbot.core.level.map.Chunk;

/**
 * Created by macbury on 01.05.14.
 */
public abstract class Block {
  public static final int BLOCK_SIZE = 1;
  public static final float BLOCK_HEIGHT = 1;
  private final int x;
  private final int y;
  private int height = 0;
  private Vector2 vec2;
  private Vector3 vec3;
  private Chunk chunk;

  public Block(int x, int y) {
    this.x = x;
    this.y = y;
    this.vec2 = new Vector2();
    this.vec3 = new Vector3();
  }

  public Vector2 vec2() {
    return vec2.set(x,y);
  }

  public Vector3 vec3() {
    return vec3.set(x,getHeight(), y);
  }

  public Chunk getChunk() {
    return chunk;
  }

  public void setChunk(Chunk chunk) {
    this.chunk = chunk;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
