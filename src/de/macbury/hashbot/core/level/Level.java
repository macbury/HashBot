package de.macbury.hashbot.core.level;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.debug.DebugQuadTree;
import de.macbury.hashbot.core.debug.DebugShape;
import de.macbury.hashbot.core.debug.FrustrumRenderer;
import de.macbury.hashbot.core.graphics.camera.RTSCameraController;
import de.macbury.hashbot.core.graphics.camera.RTSCameraListener;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.partition.GameObjectTree;


/**
 * Created by macbury on 30.04.14.
 */
public class Level implements Disposable, RTSCameraListener {
  private World world;
  private Environment environment;
  private FrustrumRenderer frustrumDebugger;
  private GameObjectTree tree;
  private PerspectiveCamera camera;
  private ShapeRenderer shapeRender;
  private RenderContext renderContext;
  private ModelBatch modelBatch;
  private RTSCameraController cameraController;
  private Terrain terrain;

  public Level() {
    this.camera           = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(10f, 10f, 10f);
    camera.near = 0.1f;
    camera.far = 150f;
    camera.update();
    this.cameraController = new RTSCameraController();
    cameraController.setCenter(0, 0);
    cameraController.setCamera(camera);
    cameraController.setListener(this);
    renderContext     = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    modelBatch        = new ModelBatch(renderContext);
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
    this.tree = new GameObjectTree(terrain);
    this.tree.setCamera(camera);
    cameraController.setCenter(terrain.getCenter());

    world.initialize();
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
      shapeRender.setProjectionMatrix(camera.combined);
      //DebugQuadTree.debug(shapeRender, tree);
      frustrumDebugger.render(camera);
    } renderContext.end();
  }

  public void act(float delta) {
    terrain.update();
    tree.update(delta);
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
}
