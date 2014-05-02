package de.macbury.hashbot.core.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.debug.DebugShape;
import de.macbury.hashbot.core.graphics.camera.RTSCameraController;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.partition.GameObjectTree;


/**
 * Created by macbury on 30.04.14.
 */
public class Level implements Disposable {

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

    renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    modelBatch    = new ModelBatch(renderContext);
    shapeRender   = new ShapeRenderer();


  }
  // run after setup map ant entities
  public void init() {
    this.tree = new GameObjectTree(terrain);
    this.tree.setCamera(camera);
  }

  @Override
  public void dispose() {
    modelBatch.dispose();
    shapeRender.dispose();
  }

  public void draw() {
    renderContext.begin(); {
      modelBatch.begin(camera);{
        modelBatch.render(tree);
      } modelBatch.end();
    } renderContext.end();

    renderContext.begin(); {
      shapeRender.setProjectionMatrix(camera.combined);
      DebugShape.drawMap(shapeRender, terrain);
    } renderContext.end();
  }

  public void act(float delta) {
    tree.update(delta);
    terrain.update();
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
}
