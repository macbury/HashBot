package de.macbury.hashbot.core.screens.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.widgets.UIStage;
import de.macbury.hashbot.core.screens.BaseScreen;

/**
 * Created by macbury on 25.04.14.
 */
public class MapEditorScreen extends BaseScreen implements EditorTableListener {

  private UIStage stage;

  public MapEditorScreen() {
    this.stage = new UIStage();
    this.stage.setCurrentTable(new EditorTable(this));
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    this.stage.resize(width, height);
  }

  @Override
  public void show() {

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
  }

  @Override
  public void afterFade() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void newMapButtonClicked() {

  }

  @Override
  public void openMapButtonClicked() {
    HashBot.ui.filePickerDialog(Gdx.files.internal("./maps")).show(stage);
  }

  @Override
  public void saveMapButtonClicked() {

  }
}
