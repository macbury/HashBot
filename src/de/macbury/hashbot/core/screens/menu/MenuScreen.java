package de.macbury.hashbot.core.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.models.LogoInstance;
import de.macbury.hashbot.core.graphics.ui.FlatColors;


/**
 * Created by macbury on 18.04.14.
 */
public class MenuScreen implements Screen, MenuOptionsListener {
  public static final int GRID_SIZE = 64;
  private static final String TAG = "MenuScreen";
  private LogoInstance menuLogoModel;
  private float elapsedTime;
  private RenderContext renderContext;
  private ShapeRenderer shapeRenderer;
  private Environment env;
  private PerspectiveCamera camera;
  private ModelBatch modelBatch;
  private MenuStage stage;
  private Color tempColor = new Color();
  private Vector3 tempVec = new Vector3();
  public MenuScreen() {
    this.stage          = new MenuStage(this);
    this.renderContext  = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED));
    this.camera         = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    camera.position.set(0, -5, 0);
    camera.lookAt(Vector3.Zero);


    env             = new Environment();
    env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
    env.add(new DirectionalLight().set(1f, 1f,1f, 2f, 10f,0f));
    this.modelBatch = new ModelBatch(renderContext);

    this.menuLogoModel     = HashBot.models.menuLogo();

    shapeRenderer    = new ShapeRenderer();
    this.elapsedTime = 0.0f;
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    elapsedTime += delta;
    stage.act(delta);
    camera.update();

    renderContext.begin(); {
      shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
      renderContext.setBlending(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
      shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
        int cols = Gdx.graphics.getWidth() / GRID_SIZE + 1;
        int rows = Gdx.graphics.getHeight() / GRID_SIZE + 1;
        tempColor.set(FlatColors.GREEN);
        tempColor.a = 0.3f;
        shapeRenderer.setColor(tempColor);

        for(int x = 0; x < cols; x++) {
          shapeRenderer.line(x * GRID_SIZE, 0, x * GRID_SIZE, Gdx.graphics.getHeight());
        }

        for(int y = 0; y < rows; y++) {
          shapeRenderer.line(0, y * GRID_SIZE, Gdx.graphics.getWidth(), y * GRID_SIZE);
        }
      } shapeRenderer.end();
    } renderContext.end();

    renderContext.begin(); {
      modelBatch.begin(camera);{
        menuLogoModel.update(delta);
        modelBatch.render(menuLogoModel, env);
      } modelBatch.end();
    } renderContext.end();

    renderContext.begin(); {
      stage.draw();

      if (HashBot.game.isDebug())
        Table.drawDebug(stage);
    } renderContext.end();
  }

  @Override
  public void resize(int width, int height) {
    stage.resize(width, height);
  }

  @Override
  public void show() {
    Gdx.app.debug(TAG, "Show");
    Gdx.input.setInputProcessor(stage);
    HashBot.ui.normalCursor();
    stage.goToDefault();
    //HashBot.music.mainMenu().play();
  }

  @Override
  public void hide() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {
    stage.dispose();
    modelBatch.dispose();
  }

  @Override
  public void onExitButtonClick() {
    Gdx.app.exit();
  }

  @Override
  public void onSettingsButtonClick() {
    //stage.goToSettings();
    HashBot.ui.settingsDialog().show(stage);
  }
}
