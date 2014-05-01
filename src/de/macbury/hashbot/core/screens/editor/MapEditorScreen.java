package de.macbury.hashbot.core.screens.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.dialogs.ConfirmDialog;
import de.macbury.hashbot.core.graphics.ui.widgets.UIStage;
import de.macbury.hashbot.core.level.Level;
import de.macbury.hashbot.core.level.LevelFactory;
import de.macbury.hashbot.core.screens.BaseScreen;

/**
 * Created by macbury on 25.04.14.
 */
public class MapEditorScreen extends BaseScreen implements EditorTableListener, ConfirmDialog.ConfirmDialogListener {

  private Level level;
  private ConfirmDialog exitConfirmDialog;
  private UIStage stage;

  public MapEditorScreen() {
    this.stage = new UIStage();
    this.stage.setCurrentTable(new EditorTable(this));

    this.level = LevelFactory.newLevel(25, 25);

    this.exitConfirmDialog = HashBot.ui.confirm("map_editor.confirm_exit.title", "map_editor.confirm_exit.message");
    exitConfirmDialog.setListener(this);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    level.act(delta);
    stage.act(delta);

    level.draw();
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
    exitConfirmDialog.hide();
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
  public void afterFadeIn() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void afterFadeOut() {
    dispose();
  }

  @Override
  public void newMapButtonClicked() {
    HashBot.screens.openMapEditor();
  }

  @Override
  public void openMapButtonClicked() {
    HashBot.ui.filePickerDialog(Gdx.files.internal("./maps")).show(stage);
  }

  @Override
  public void saveMapButtonClicked() {

  }

  @Override
  public void codeButtonClicked() {
    HashBot.ui.codeEditorDialog().show(stage);
  }

  @Override
  public void exitButtonClicked() {
    exitConfirmDialog.show(stage);
  }

  @Override
  public void onConfirmOkButton(ConfirmDialog sender) {
    HashBot.screens.openMainMenu();
  }

  @Override
  public void onConfirmCancelButton(ConfirmDialog sender) {

  }
}
