package de.macbury.hashbot.core.level;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.debug.DebugShape;
import de.macbury.hashbot.core.debug.FrustrumRenderer;
import de.macbury.hashbot.core.debug.WireframeDebug;
import de.macbury.hashbot.core.game_objects.system.CullingSystem;
import de.macbury.hashbot.core.game_objects.system.ShapeRenderingSystem;
import de.macbury.hashbot.core.graphics.camera.RTSCameraController;
import de.macbury.hashbot.core.graphics.camera.RTSCameraListener;
import de.macbury.hashbot.core.graphics.models.MultiModeModelBatch;
import de.macbury.hashbot.core.graphics.models.RenderDebugStats;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.partition.GameObjectTree;


/**
 * Created by macbury on 30.04.14.
 */
public abstract class Level implements Disposable, RTSCameraListener {
  private CullingSystem cullingSystem;
  protected ShapeRenderingSystem shapeRenderingSystem;
  protected World world;
  protected Environment environment;
  protected FrustrumRenderer frustrumDebugger;
  protected GameObjectTree tree;
  protected PerspectiveCamera camera;
  protected ShapeRenderer shapeRender;
  protected RenderContext renderContext;
  protected MultiModeModelBatch modelBatch;
  protected RTSCameraController cameraController;
  protected Terrain terrain;
  protected EntityFactory entities;
  protected WireframeDebug wireframeDebug;
  public Level() {
    this.camera           = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(10f, 10f, 10f);
    camera.near = 0.1f;
    camera.far = 40f;
    camera.update();


    this.cameraController = new RTSCameraController();
    cameraController.setCenter(0, 0);
    cameraController.setCamera(camera);
    cameraController.setListener(this);

    renderContext     = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    modelBatch        = new MultiModeModelBatch(renderContext);
    shapeRender       = new ShapeRenderer();
    frustrumDebugger  = new FrustrumRenderer(camera);

    environment       = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
    environment.set(new ColorAttribute(ColorAttribute.Fog, 0f, 0f, 0f, 1f));
    environment.add(new DirectionalLight().set(new Color(.4f,.4f, .4f, 0.1f), new Vector3(1,-1,0)));

    world                = new World();
  }


  // run after setup map and entities
  public void init() {
    this.tree       = new GameObjectTree(terrain);
    this.entities    = new EntityFactory(this);

    this.tree.setFrustum(camera.frustum);
    cameraController.setCenter(terrain.getCenter());

    shapeRenderingSystem = new ShapeRenderingSystem(shapeRender);
    cullingSystem        = new CullingSystem(tree);

    world.setSystem(cullingSystem, true);
    world.setSystem(shapeRenderingSystem, true);

    world.initialize();

    wireframeDebug = new WireframeDebug();
  }

  @Override
  public void dispose() {
    modelBatch.dispose();
    shapeRender.dispose();
  }

  public void draw() {
    renderContext.begin(); {
      modelBatch.begin(camera);{
        modelBatch.render(tree, environment);
      } modelBatch.end();
    } renderContext.end();

    renderContext.begin(); {
      renderContext.setDepthMask(true);
      renderContext.setDepthTest(GL30.GL_LEQUAL);
      shapeRender.setProjectionMatrix(camera.combined);

      shapeRenderingSystem.process();

      //DebugQuadTree.debug(shapeRender, tree);
      frustrumDebugger.render(camera);
      //renderContext.setDepthTest(GL30.GL_ALWAYS);
      //wireframeDebug.render(shapeRender,tree);
    } renderContext.end();
  }

  public void act(float delta) {
    cameraController.update(delta);
    terrain.update();
    if (frustrumDebugger.isEnabled()) {
      tree.setFrustum(frustrumDebugger.getFrustrum());
    } else {
      tree.setFrustum(camera.frustum);
    }
    cullingSystem.process();
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

  public RenderDebugStats getStats() { return modelBatch.getStats(); }
}
