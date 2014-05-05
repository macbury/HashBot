package de.macbury.hashbot.core.level.map;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.graphics.mesh.MeshAssembler;
import de.macbury.hashbot.core.graphics.mesh.MeshVertexData;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.partition.QuadTree;
import de.macbury.hashbot.core.partition.QuadTreeObject;

/**
 * Created by macbury on 01.05.14.
 */
public class Chunk extends Renderable implements Disposable, QuadTreeObject {
  public static final int SIZE = 5;
  public final static int CHUNK_WIDTH = SIZE * Block.BLOCK_SIZE; //size in meters
  private static final float CHUNK_HEIGHT = Block.BLOCK_HEIGHT;
  private Terrain terrain;
  private boolean dirty = true;
  private Vector3 start;
  private BoundingBox boundingBox;
  private QuadTree parent;

  public Chunk(int bx, int by, Terrain terrain) {
    Vector3 tempVec   = new Vector3(bx * CHUNK_WIDTH, 0, by * CHUNK_WIDTH);
    this.start        = new Vector3(bx * SIZE, 0, by * SIZE);
    this.boundingBox  = new BoundingBox(tempVec, tempVec.cpy().add(CHUNK_WIDTH, CHUNK_HEIGHT, CHUNK_WIDTH));

    this.primitiveType  = GL30.GL_TRIANGLES;
    this.meshPartOffset = 0;
    this.meshPartSize   = 0;
    this.terrain        = terrain;
    this.material       = terrain.getMaterial();
  }

  @Override
  public void dispose() {
    mesh.dispose();
  }

  public void build() {
    MeshAssembler builder = terrain.getBuilder();
    TextureRegion uv      = terrain.getTileset().findRegion("floor");
    builder.begin(); {
      builder.using(MeshVertexData.AttributeType.Position);
      builder.using(MeshVertexData.AttributeType.Normal);
      builder.using(MeshVertexData.AttributeType.TextureCord);

      for (int x = fromX(); x < toX(); x++) {
        for (int y = fromY(); y < toY(); y++) {
          Block block       = terrain.getBlock(x,y);
          Block topBlock    = terrain.getBlock(x,y+1);
          Block bottomBlock = terrain.getBlock(x,y-1);
          Block leftBlock   = terrain.getBlock(x-1,y);
          Block rightBlock  = terrain.getBlock(x+1,y);

          builder.topFace(x * Block.BLOCK_SIZE, Block.BLOCK_SIZE, y * Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, uv.getU(),uv.getV(),uv.getU2(),uv.getU2());

          if (topBlock == null) {
            builder.frontFace(x * Block.BLOCK_SIZE, 0f, y * Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE,uv.getU(),uv.getV(),uv.getU2(),uv.getU2());
          }

          if (bottomBlock == null) {
            builder.backFace(x * Block.BLOCK_SIZE, 0f,y  * Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE,uv.getU(),uv.getV(),uv.getU2(),uv.getU2());
          }

          if (leftBlock == null) {
            builder.leftFace(x * Block.BLOCK_SIZE, 0f, y * Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE,uv.getU(),uv.getV(),uv.getU2(),uv.getU2());
          }

          if (rightBlock == null) {
            builder.rightFace(x * Block.BLOCK_SIZE, 0f, y * Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, Block.BLOCK_SIZE, uv.getU(),uv.getV(),uv.getU2(),uv.getU2());
          }
        }
      }
    } builder.end();

    if (mesh == null) {
      mesh = new Mesh(true, builder.getVerties().length, builder.getIndices().length, builder.getVertexAttributes());
    }

    mesh.setVertices(builder.getVerties());
    mesh.setIndices(builder.getIndices());
    mesh.setAutoBind(true);
    meshPartSize   = mesh.getNumIndices();
    meshPartOffset = 0;
    dirty          = false;
  }

  private int fromX() {
    return (int) this.start.x;
  }

  private int fromY() {
    return (int) this.start.z;
  }

  private int toX() {
    return fromX() + SIZE;
  }

  private int toY() {
    return fromY() + SIZE;
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

  @Override
  public void setQuadTreeParent(QuadTree parent) {
    this.parent = parent;
  }

  public Vector3 getStart() {
    return start;
  }
}
