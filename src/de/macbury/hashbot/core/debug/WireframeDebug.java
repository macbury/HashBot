package de.macbury.hashbot.core.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import de.macbury.hashbot.core.level.map.Chunk;
import de.macbury.hashbot.core.partition.GameObjectTree;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by macbury on 07.05.14.
 */
public class WireframeDebug {
  private Vector3 v2;
  private Vector3 v3;
  private Vector3 v1;
  private Pool<Renderable> pool;
  private Array<Renderable> renderables;

  public WireframeDebug() {
    this.renderables = new Array<Renderable>();
    this.pool        = new Pool<Renderable>() {
      @Override
      protected Renderable newObject() {
        return new Renderable();
      }
    };

    this.v1 = new Vector3();
    this.v2 = new Vector3();
    this.v3 = new Vector3();
  }

  public void render(ShapeRenderer renderer, GameObjectTree tree) {
    renderables.clear();
    pool.clear();
    tree.getRenderables(renderables, pool);

    for(int i = 0; i < renderables.size; i++) {
      Renderable renderable = renderables.get(i);

      drawTriangleWireframe(renderer, renderable);
    }
  }

  private void drawTriangleWireframe(ShapeRenderer renderer, Renderable renderable) {
    Mesh mesh           = renderable.mesh;
    ShortBuffer indices = mesh.getIndicesBuffer();
    FloatBuffer verts   = mesh.getVerticesBuffer();
    indices.position(0);
    verts.position(0);

    int repeat    = indices.limit() / 3;
    int i         = 0;
    int vertSize  = mesh.getVertexSize() / 4;
    renderer.setTransformMatrix(renderable.worldTransform);

    while(i <= repeat) {
      int i1 = indices.get(i) * vertSize;
      int i2 = indices.get(i+1) * vertSize;
      int i3 = indices.get(i+2) * vertSize;

      v1.set(verts.get(i1), verts.get(i1+1), verts.get(i1+2));
      v2.set(verts.get(i2), verts.get(i2+1), verts.get(i2+2));
      v3.set(verts.get(i3), verts.get(i3+1), verts.get(i3+2));

      renderer.begin(ShapeRenderer.ShapeType.Line); {
        renderer.setColor(Color.WHITE);
        renderer.line(v1,v2);
        //renderer.line(v2,v3);
        //renderer.line(v3,v1);
      } renderer.end();
      i+=3;
    }

  }
}
