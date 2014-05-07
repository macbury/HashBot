package de.macbury.hashbot.core.ui.editor;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.ui.widgets.UITable;

/**
 * Created by macbury on 05.05.14.
 */
public class BrushSettingsWidgetGroup extends WidgetGroup {
  public static enum BrushMode {
    Terrain, Texture, Triggers, Objects
  }
  private UITable terrainBrushSettingsTable;
  private SelectBox terrainModeSelectBox;
  private BrushMode currentMode;

  public BrushSettingsWidgetGroup() {
    super();
    createTerrainTable();

    showTerrain();
  }

  private void createTerrainTable() {
    this.terrainBrushSettingsTable = new UITable();

    this.terrainModeSelectBox = HashBot.ui.stringSelectBox();
    terrainModeSelectBox.setItems(HashBot.i18n.t("map_editor.brush.terrain.up"), HashBot.i18n.t("map_editor.brush.terrain.down"), HashBot.i18n.t("map_editor.brush.terrain.set"));

    this.terrainBrushSettingsTable.row(); {
      this.terrainBrushSettingsTable.add(terrainModeSelectBox).expandX().fill().colspan(2);
    }

    this.terrainBrushSettingsTable.row(); {
      this.terrainBrushSettingsTable.add().colspan(2).expand();
    }

    addActor(terrainBrushSettingsTable);
    terrainBrushSettingsTable.setFillParent(true);
  }

  public void hideAll() {
    this.terrainBrushSettingsTable.setVisible(false);
  }

  public void showTerrain() {
    hideAll();
    terrainBrushSettingsTable.setVisible(true);
    this.currentMode = BrushMode.Terrain;
  }
}
