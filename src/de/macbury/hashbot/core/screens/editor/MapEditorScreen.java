package de.macbury.hashbot.core.screens.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.game_objects.system.LevelEditorSystem;
import de.macbury.hashbot.core.graphics.camera.RTSCameraController;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.time.IntervalTimer;
import de.macbury.hashbot.core.ui.GameUIOverlay;
import de.macbury.hashbot.core.ui.dialogs.BrushDialog;
import de.macbury.hashbot.core.ui.dialogs.ConfirmDialog;
import de.macbury.hashbot.core.ui.dialogs.StatsDialog;
import de.macbury.hashbot.core.ui.editor.EditorTable;
import de.macbury.hashbot.core.ui.editor.EditorTableListener;
import de.macbury.hashbot.core.ui.editor.LevelEditorSystemListener;
import de.macbury.hashbot.core.ui.widgets.UIStage;
import de.macbury.hashbot.core.level.editor.LevelEditor;
import de.macbury.hashbot.core.level.LevelFactory;
import de.macbury.hashbot.core.level.map.exceptions.LevelInvalidDimensionException;
import de.macbury.hashbot.core.screens.BaseScreen;

/**
 * Created by macbury on 25.04.14.
 */
public class MapEditorScreen extends BaseScreen implements EditorTableListener, ConfirmDialog.ConfirmDialogListener, LevelEditorSystemListener {

  private StatsDialog statsDialog;
  private BrushDialog brushDialog;
  private GameUIOverlay overlay;
  private EditorTable editorTable;
  private InputMultiplexer inputMultiplexer;
  private LevelEditor level;
  private ConfirmDialog exitConfirmDialog;
  private UIStage stage;

  public MapEditorScreen() {
    this.stage = new UIStage();

    try {
      this.level = LevelFactory.newLevel(128, 64);
    } catch (LevelInvalidDimensionException e) {
      e.printStackTrace();
    }

    this.statsDialog = HashBot.ui.statsDialog(level);

    this.exitConfirmDialog = HashBot.ui.confirm("map_editor.confirm_exit.title", "map_editor.confirm_exit.message");
    exitConfirmDialog.setListener(this);

    this.brushDialog       = HashBot.ui.brushDialog(level);

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

    level.getLevelEditorSystem().setListener(this);

    //level.getCameraController().setMaxZoom(RTSCameraController.MAX_ZOOM * 3);
    //statsDialog.toggleVisibility(stage);
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
  public void brushButtonClicked() {
    brushDialog.toggleVisibility(stage);
  }

  @Override
  public void statsButtonClicked() {
    statsDialog.toggleVisibility(stage);
  }

  @Override
  public void onConfirmOkButton(ConfirmDialog sender) {
    HashBot.screens.openMainMenu();
  }

  @Override
  public void onConfirmCancelButton(ConfirmDialog sender) {

  }

  @Override
  public void levelEditorSystemStartSelection(LevelEditorSystem les) {

  }

  @Override
  public void levelEditorSystemCursorMove(LevelEditorSystem les) {

  }

  @Override
  public void levelEditorSystemEndSelectionOrClick(LevelEditorSystem les) {
    BoundingBox box = les.getSelectionBoundingBox();
    brushDialog.applySelection(box);

  }

  private Vector3 cursorMinimalDimension = new Vector3();
  @Override
  public Vector3 levelEditorCursorMinimalDimension(LevelEditorSystem levelEditorSystem) {
    return cursorMinimalDimension.set(1,1f,1);
  }
}
