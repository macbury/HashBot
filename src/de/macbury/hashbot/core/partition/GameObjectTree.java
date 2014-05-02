package de.macbury.hashbot.core.partition;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import de.macbury.hashbot.core.level.map.Chunk;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.time.IntervalTimer;
import de.macbury.hashbot.core.time.IntervalTimerListener;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by macbury on 02.05.14.
 */
public class GameObjectTree extends QuadTree implements RenderableProvider, IntervalTimerListener {
  private IntervalTimer updateTimer;
  private Stack<QuadTreeObject> tempTreeObjects;
  private ArrayList<QuadTreeObject> visibleObjects;
  private Array<Renderable> visibleRenderables;
  private Array<RenderableProvider> visibleRenderableProviders;
  private PerspectiveCamera camera;
  public GameObjectTree(Terrain terrain) {
    super(0, terrain.getBoundingBox());
    this.tempTreeObjects = new Stack<QuadTreeObject>();
    this.visibleObjects  = new ArrayList<QuadTreeObject>();
    this.visibleRenderables = new Array<Renderable>();
    this.visibleRenderableProviders = new Array<RenderableProvider>();
    append(terrain);

    this.updateTimer = new IntervalTimer(50);
    updateTimer.setListener(this);
  }

  private void append(Terrain terrain) {
    for(Chunk chunk : terrain.getChunks()) {
      insert(chunk);
    }
  }

  public void cull(Frustum frustum) {
    tempTreeObjects.clear();
    visibleObjects.clear();
    visibleRenderables.clear();
    visibleRenderableProviders.clear();
    retrieve(tempTreeObjects, frustum);

    while(tempTreeObjects.size() > 0) {
      QuadTreeObject object = tempTreeObjects.pop();

      if (frustum.boundsInFrustum(object.getBoundingBox())) {
        visibleObjects.add(object);
        if (Renderable.class.isInstance(object)) {
          visibleRenderables.add((Renderable)object);
        } else if (RenderableProvider.class.isInstance(object)) {
          visibleRenderableProviders.add((RenderableProvider)object);
        }
      }
    }
  }

  public ArrayList<QuadTreeObject> getVisibleObjects() {
    return visibleObjects;
  }

  @Override
  public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
    renderables.addAll(visibleRenderables);
    for (RenderableProvider provider : visibleRenderableProviders) {
      provider.getRenderables(renderables, pool);
    }
  }

  public void update(float delta) {
    updateTimer.update(delta);
  }

  @Override
  public void timerTick(IntervalTimer sender) {
    cull(camera.frustum);
  }

  public void setCamera(PerspectiveCamera camera) {
    this.camera = camera;
  }
}
