package de.macbury.hashbot.core.level.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.material.TerrainMaterial;
import de.macbury.hashbot.core.graphics.mesh.MeshAssembler;
import de.macbury.hashbot.core.graphics.utils.CallBackVector3;
import de.macbury.hashbot.core.level.map.blocks.BaseBlock;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.level.map.exceptions.LevelInvalidDimensionException;
import de.macbury.hashbot.core.managers.Assets;
import de.macbury.hashbot.core.partition.GameObjectTree;
import de.macbury.hashbot.core.partition.QuadTreeObject;

import javax.swing.text.AttributeSet;
import java.util.ArrayList;

/**
 * Created by macbury on 01.05.14.
 */
public class Terrain implements Disposable {
  private static final int OUTLINE_HEIGHT = 10;
  private TextureAtlas normalMap;
  private TextureAtlas glowMap;
  private Material material;
  private Block[][] tiles;
  private int width;
  private int height;
  private MeshAssembler builder;
  private TextureAtlas tileset;
  private Array<Chunk> chunks;
  private BoundingBox boundingBox;
  private Vector2 tempVec = new Vector2();

  public Terrain(int width, int height) throws LevelInvalidDimensionException {
    builder = new MeshAssembler();
    tileset = HashBot.assets.get(Assets.TERRAIN_TILESET);
    glowMap = HashBot.assets.get(Assets.GLOW_TILESET);
    normalMap = HashBot.assets.get(Assets.NORMAL_TILESET);
    this.width = width;
    this.height = height;

    tiles   = new Block[width][height];
    chunks  = new Array<Chunk>();

    if (width % Chunk.SIZE != 0 && height % Chunk.SIZE != 0) {
      throw new LevelInvalidDimensionException();
    }

    this.boundingBox = new BoundingBox(new Vector3(0,0,0), new Vector3(width,Block.BLOCK_HEIGHT,height));
    this.material    = new TerrainMaterial(tileset, glowMap, normalMap);
  }

  public void bootstrap() {
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        tiles[x][y] = new BaseBlock(x,y);
      }
    }
    build();
  }

  public void build() {
    int chunkCols = Math.round(width / Chunk.SIZE);
    int chunkRows = Math.round(height / Chunk.SIZE);

    for (int row = 0; row < chunkRows; row++) {
      for(int col = 0; col < chunkCols; col++) {
        chunks.add(new Chunk(col, row, this));
      }
    }
  }

  public void rebuild() {
    for (Chunk chunk : chunks) {
      chunk.build();
    }
  }

  @Override
  public void dispose() {
    tiles = null;
    builder.dispose();
    for (Chunk chunk : chunks) {
      chunk.dispose();
    }
  }

  public MeshAssembler getBuilder() {
    return builder;
  }

  public TextureAtlas getTileset() {
    return tileset;
  }

  public Block getBlock(int x, int y) {
    if (x < 0 || y < 0 || x >= width || y >= height) {
      return null;
    } else {
      return tiles[x][y];
    }
  }

  public Array<Chunk> getChunks() {
    return chunks;
  }

  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  public void update() {
    for(Chunk chunk : chunks) {
      chunk.update();
    }
  }

  public Vector2 getCenter() {
    return tempVec.set(width/2, height/2);
  }

  public boolean intersect(GameObjectTree tree, ArrayList<Chunk> returnObjects, Ray ray, Vector3 intersect) {
    returnObjects.clear();
    intersect.set(Vector3.Zero);

    for (Chunk chunk : chunks) {
      if (tree.isVisible(chunk) && Intersector.intersectRayTriangles(ray, chunk.getVerticies(), chunk.getIndicies(), chunk.getVertexSize(), intersect)) {
        returnObjects.add(chunk);
      }
    }

    return returnObjects.size() != 0;
  }

  public Material getMaterial() {
    return material;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void outline() {
    for(int x = 0; x < width; x++) {
      getBlock(x, 0).setHeight(OUTLINE_HEIGHT);
      getBlock(x, height-1).setHeight(OUTLINE_HEIGHT);
    }

    for(int y = 0; y < height;y++) {
      getBlock(0, y).setHeight(OUTLINE_HEIGHT);
      getBlock(width-1, y).setHeight(OUTLINE_HEIGHT);
    }
  }

  public Block getBlock(Vector3 position) {
    return getBlock(position.x, position.z);
  }

  private Block getBlock(float x, float z) {
    return getBlock((int)x, (int)z);
  }
}
