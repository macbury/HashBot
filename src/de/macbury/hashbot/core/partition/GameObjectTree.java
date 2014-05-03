package de.macbury.hashbot.core.partition;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import de.macbury.hashbot.core.level.map.Chunk;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.time.BaseTimer;
import de.macbury.hashbot.core.time.FrameTickTimer;
import de.macbury.hashbot.core.time.IntervalTimer;
import de.macbury.hashbot.core.time.TimerListener;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by macbury on 02.05.14.
 */
public class GameObjectTree extends QuadTree implements RenderableProvider, TimerListener {
  private FrameTickTimer updateTimer;
  private Stack<QuadTreeObject> tempTreeObjects;
  private Array<QuadTreeObject> visibleObjects;
  private Array<Renderable> visibleRenderables;
  private Array<RenderableProvider> visibleRenderableProviders;
  private PerspectiveCamera camera;

  public GameObjectTree(Terrain terrain) {
    super(0, terrain.getBoundingBox());
    this.tempTreeObjects = new Stack<QuadTreeObject>();
    this.visibleObjects  = new Array<QuadTreeObject>();
    this.visibleRenderables = new Array<Renderable>();
    this.visibleRenderableProviders = new Array<RenderableProvider>();
    append(terrain);

    this.updateTimer = new FrameTickTimer(2);
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

  public Array<QuadTreeObject> getVisibleObjects() {
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

  public boolean isVisible(QuadTreeObject object) {
    return visibleObjects.contains(object, true);
  }

  @Override
  public void timerTick(BaseTimer sender) {
    cull(camera.frustum);
  }

  public void setCamera(PerspectiveCamera camera) {
    this.camera = camera;
  }
}
