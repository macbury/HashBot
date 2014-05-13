package de.macbury.hashbot.core.level.editor;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.ui.widgets.UITable;

/**
 * Created by macbury on 05.05.14.
 */
public class TerrainBrush extends BrushBase {
  public static final int TERRAIN_UP   = 0;
  public static final int TERRAIN_DOWN = 1;
  public static final int TERRAIN_SET  = 2;
  private TextField heightTextField;
  private SelectBox terrainModeSelectBox;

  public TerrainBrush(LevelEditor level) {
    super(level);

    this.terrainModeSelectBox = HashBot.ui.stringSelectBox();
    terrainModeSelectBox.setItems(HashBot.i18n.t("map_editor.brush.terrain.up"), HashBot.i18n.t("map_editor.brush.terrain.down"), HashBot.i18n.t("map_editor.brush.terrain.set"));

    this.heightTextField = HashBot.ui.textField();

    this.row(); {
      this.add(terrainModeSelectBox).expandX().fill().colspan(2);
    }

    this.row(); {
      this.add(heightTextField).expandX().fill().colspan(2);
    }

    this.row(); {
      this.add().colspan(2).expand();
    }

  }

  @Override
  public void apply(BoundingBox box) {
    for(int x = (int)box.getMin().x; x < box.getMax().x; x++) {
      for(int z = (int)box.getMin().z; z < box.getMax().z; z++) {
        Block block = level.getTerrain().getBlock(x, z);
        if (block != null) {
          modifyBlockByMode(block);
          block.updateChunk();
        }
      }
    }
  }

  private void modifyBlockByMode(Block block) {
    switch (terrainModeSelectBox.getSelectedIndex()) {
      case TERRAIN_UP:
        block.setHeight(block.getHeight()+1);
      break;

      case TERRAIN_DOWN:
        block.setHeight(block.getHeight()-1);
      break;

      case TERRAIN_SET:
        block.setHeight(block.getHeight()-1);
      break;
    }

  }
}
