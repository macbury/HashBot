package de.macbury.hashbot.core.level.map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.mesh.MeshAssembler;
import de.macbury.hashbot.core.level.map.blocks.BaseBlock;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.level.map.exceptions.LevelInvalidDimensionException;
import de.macbury.hashbot.core.managers.Assets;
import de.macbury.hashbot.core.partition.QuadTreeObject;

/**
 * Created by macbury on 01.05.14.
 */
public class Terrain implements Disposable, QuadTreeObject {
  private Block[][] tiles;
  private int width;
  private int height;
  private MeshAssembler builder;
  private TextureAtlas tileset;
  private Array<Chunk> chunks;
  private BoundingBox boundingBox;

  public Terrain(int width, int height) throws LevelInvalidDimensionException {
    builder = new MeshAssembler();
    tileset = HashBot.assets.get(Assets.TERRAIN_TILESET);

    this.width = width;
    this.height = height;

    tiles   = new Block[width][height];
    chunks  = new Array<Chunk>();

    if (width % Chunk.SIZE != 0 && height % Chunk.SIZE != 0) {
      throw new LevelInvalidDimensionException();
    }

    this.boundingBox = new BoundingBox(new Vector3(0,0,0), new Vector3(width,Block.BLOCK_HEIGHT,height));
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

    rebuild();
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
    return tiles[x][y];
  }

  public Array<Chunk> getChunks() {
    return chunks;
  }

  @Override
  public BoundingBox getBoundingBox() {
    return boundingBox;
  }

  public void update() {
    for(Chunk chunk : chunks) {
      chunk.update();
    }
  }
}