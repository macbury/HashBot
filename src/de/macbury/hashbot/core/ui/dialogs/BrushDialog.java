package de.macbury.hashbot.core.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.level.editor.BrushBase;
import de.macbury.hashbot.core.level.editor.LevelEditor;
import de.macbury.hashbot.core.level.editor.TerrainBrush;
import de.macbury.hashbot.core.managers.UIManager;

/**
 * Created by macbury on 05.05.14.
 */
public class BrushDialog extends UIDialog {
  private LevelEditor level;
  private SelectBox typeSelectBox;
  private BrushBase currentBrush;

  public BrushDialog(WindowStyle style, LevelEditor level) {
    super("Brush", style);
    this.level = level;
    setModal(false);
    setMovable(false);
    setPosition(10, 10);

    this.typeSelectBox = HashBot.ui.stringSelectBox();
    typeSelectBox.setItems(HashBot.i18n.t("map_editor.brush.types.terrain"),
            HashBot.i18n.t("map_editor.brush.types.texture"),
            HashBot.i18n.t("map_editor.brush.types.triggers"),
            HashBot.i18n.t("map_editor.brush.types.objects")
    );

    currentBrush = new TerrainBrush(level);

    Table table = getContentTable();

    table.row().padTop(10); {
      table.add(typeSelectBox).fill().expandX();
    }

    table.row(); {
      table.add(currentBrush).expand().fill();
    }
  }

  @Override
  public float getPrefHeight() {
    return Gdx.graphics.getHeight() - 2 * UIManager.BUTTON_HEIGHT;
  }

  @Override
  public float getPrefWidth() {
    return 320;
  }

  @Override
  public Dialog show(Stage stage) {
    super.show(stage);
    setPosition(0, UIManager.BUTTON_HEIGHT);

    return this;
  }

  @Override
  public void hide() {
    super.hide();
  }

  public void applySelection(BoundingBox selectionBoundingBox) {
    if (currentBrush != null) {
      currentBrush.apply(selectionBoundingBox);
    }
  }
}
