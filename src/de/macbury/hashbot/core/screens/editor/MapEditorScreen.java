package de.macbury.hashbot.core.screens.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.camera.RTSCameraController;
import de.macbury.hashbot.core.graphics.ui.GameUIOverlay;
import de.macbury.hashbot.core.graphics.ui.dialogs.ConfirmDialog;
import de.macbury.hashbot.core.graphics.ui.widgets.UIStage;
import de.macbury.hashbot.core.level.Level;
import de.macbury.hashbot.core.level.LevelEditor;
import de.macbury.hashbot.core.level.LevelFactory;
import de.macbury.hashbot.core.level.map.exceptions.LevelInvalidDimensionException;
import de.macbury.hashbot.core.screens.BaseScreen;

/**
 * Created by macbury on 25.04.14.
 */
public class MapEditorScreen extends BaseScreen implements EditorTableListener, ConfirmDialog.ConfirmDialogListener {

  private GameUIOverlay overlay;
  private EditorTable editorTable;
  private InputMultiplexer inputMultiplexer;
  private LevelEditor level;
  private ConfirmDialog exitConfirmDialog;
  private UIStage stage;

  public MapEditorScreen() {
    this.stage = new UIStage();

    try {
      this.level = LevelFactory.newLevel(50, 50);
    } catch (LevelInvalidDimensionException e) {
      e.printStackTrace();
    }

    this.exitConfirmDialog = HashBot.ui.confirm("map_editor.confirm_exit.title", "map_editor.confirm_exit.message");
    exitConfirmDialog.setListener(this);

    this.inputMultiplexer = new InputMultiplexer();
    inputMultiplexer.addProcessor(stage);
    inputMultiplexer.addProcessor(level.getFrustrumDebugger());

    this.overlay = new GameUIOverlay();
    level.getCameraController().setOverlay(this.overlay);
    level.getLevelEditorSystem().setOverlay(this.overlay);
    stage.addActor(overlay);

    this.editorTable = new EditorTable(this);
    this.stage.setCurrentTable(editorTable);
    level.setUILayout(editorTable);
    level.init();

    level.getCameraController().setMaxZoom(RTSCameraController.MAX_ZOOM * 3);


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
    Gdx.input.setInputProcessor(inputMultiplexer);
    overlay.focus();
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
