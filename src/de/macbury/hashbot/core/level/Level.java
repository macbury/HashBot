package de.macbury.hashbot.core.level;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.debug.DebugQuadTree;
import de.macbury.hashbot.core.debug.FrustrumRenderer;
import de.macbury.hashbot.core.game_objects.system.ActorSystem;
import de.macbury.hashbot.core.game_objects.system.GeometryRenderingSystem;
import de.macbury.hashbot.core.game_objects.system.LightRenderingSystem;
import de.macbury.hashbot.core.game_objects.system.ShapeRenderingSystem;
import de.macbury.hashbot.core.graphics.camera.RTSCameraController;
import de.macbury.hashbot.core.graphics.camera.RTSCameraListener;
import de.macbury.hashbot.core.graphics.models.RenderDebugStats;
import de.macbury.hashbot.core.graphics.rendering.BaseRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.graphics.rendering.RenderingEngineListener;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.AccumulateLightsStep;
import de.macbury.hashbot.core.graphics.rendering.simple.SimpleRenderingEngine;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.partition.GameObjectTree;


/**
 * Created by macbury on 30.04.14.
 */
public abstract class Level implements Disposable, RTSCameraListener, RenderingEngineListener {
  public BaseRenderingEngine renderingEngine;
  protected ActorSystem actorSystem;
  protected ShapeRenderingSystem shapeRenderingSystem;
  protected World world;
  protected FrustrumRenderer frustrumDebugger;
  protected GameObjectTree tree;
  protected PerspectiveCamera camera;
  protected RTSCameraController cameraController;
  protected Terrain terrain;
  protected EntityFactory entities;
  protected GeometryRenderingSystem geometryRenderingSystem;
  protected LightRenderingSystem lightRenderingSystem;

  public Level() {
    this.camera           = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(10f, 10f, 10f);
    camera.near = 0.01f;
    camera.far = 40f;
    camera.update();

    this.cameraController = new RTSCameraController();
    cameraController.setCenter(0, 0);
    cameraController.setCamera(camera);
    cameraController.setListener(this);

    frustrumDebugger  = new FrustrumRenderer(camera);
    world                = new World();

    if (HashBot.config.isGoodQuality()) {
      this.renderingEngine = new MRTRenderingEngine(camera);
    } else {
      this.renderingEngine = new SimpleRenderingEngine(camera);
    }

    renderingEngine.setListener(this);
  }

  // run after setup map and entities
  public void init() {
    this.tree        = new GameObjectTree(terrain);
    this.entities    = new EntityFactory(this);

    this.tree.setFrustum(camera.frustum);
    cameraController.setCenter(terrain.getCenter());

    shapeRenderingSystem    = new ShapeRenderingSystem(renderingEngine.shapes);
    actorSystem = new ActorSystem(tree);
    geometryRenderingSystem = new GeometryRenderingSystem(renderingEngine.models);
    lightRenderingSystem    = new LightRenderingSystem();
    world.setSystem(actorSystem, true);
    world.setSystem(geometryRenderingSystem, true);
    world.setSystem(shapeRenderingSystem, true);
    world.setSystem(lightRenderingSystem, true);
    world.initialize();

  }

  @Override
  public void dispose() {
    renderingEngine.dispose();
  }

  public void draw() {
    renderingEngine.render();

  }

  @Override
  public void geometryPass(ModelBatch modelBatch) {
    modelBatch.render(tree);
    geometryRenderingSystem.process();
  }

  @Override
  public void lightPass(AccumulateLightsStep accumulateLightsStep) {
    lightRenderingSystem.setProcessor(accumulateLightsStep);
    lightRenderingSystem.process();
  }

  @Override
  public void renderShapes(ShapeRenderer shapeRenderer) {
    if (frustrumDebugger.isEnabled()) {
      DebugQuadTree.debug(shapeRenderer, tree);
      frustrumDebugger.render(camera);
    }

    shapeRenderingSystem.process();
  }

  public void act(float delta) {
    cameraController.update(delta);
    terrain.update();
    if (frustrumDebugger.isEnabled()) {
      tree.setFrustum(frustrumDebugger.getFrustrum());
    } else {
      tree.setFrustum(camera.frustum);
    }
    actorSystem.process();
    this.world.setDelta(delta);
    this.world.process();
  }

  public void setTerrain(Terrain terrain) {
    this.terrain = terrain;
  }

  public Terrain getTerrain() {
    return terrain;
  }

  public RTSCameraController getCameraController() {
    return cameraController;
  }

  @Override
  public BoundingBox getCameraBounds() {
    return terrain.getBoundingBox();
  }

  public World getWorld() {
    return world;
  }

  public FrustrumRenderer getFrustrumDebugger() {
    return frustrumDebugger;
  }

  public GameObjectTree getTree() {
    return tree;
  }

  public RenderDebugStats getStats() { return renderingEngine.models.getStats(); }

}
