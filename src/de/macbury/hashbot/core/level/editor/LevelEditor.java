package de.macbury.hashbot.core.level.editor;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import de.macbury.hashbot.core.game_objects.system.LevelEditorSystem;
import de.macbury.hashbot.core.level.Level;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.ui.editor.EditorTable;

/**
 * Created by macbury on 03.05.14.
 */
public class LevelEditor extends Level {

  private LevelEditorSystem levelEditorSystem;
  private EditorTable uiLayout;
  private Entity cursor;

  public LevelEditor() {
    super();
    this.levelEditorSystem = new LevelEditorSystem(this);
    world.setSystem(levelEditorSystem, false);
  }

  @Override
  public void init() {
    super.init();
    this.cursor = entities.cursor();
    this.cursor.addToWorld();

    Color[] colors = { Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.RED };

    /*for(int i = 0; i < 1; i++) {
      float x = (float)Math.random() * terrain.getWidth();
      float z = (float)Math.random() * terrain.getHeight();
      float y = 0;
      Vector3 pos = new Vector3(x,y,z);

      int index = (int)Math.round(Math.random() * (colors.length - 1));

      entities.light(pos, colors[index]).addToWorld();
    }*/
    /*
    float x = terrain.getWidth() / 2;
    float z = terrain.getHeight() / 2;
    float y = 1;
    Vector3 pos = new Vector3(x,y,z);
    entities.light(pos, Color.WHITE).addToWorld();
    //entities.unit().addToWorld();*/
    //fogOfWar.setEnabled(false);
  }

  public void setUILayout(EditorTable UILayout) {
    this.uiLayout = UILayout;
  }

  public EditorTable getUiLayout() {
    return uiLayout;
  }

  public void setUiLayout(EditorTable uiLayout) {
    this.uiLayout = uiLayout;
  }

  public LevelEditorSystem getLevelEditorSystem() {
    return levelEditorSystem;
  }

  public Entity getCursor() {
    return cursor;
  }

  public int getMaxHeightForSelection(BoundingBox box) {
    int max = 0;
    for(int x = (int)box.getMin().x; x < box.getMax().x; x++) {
      for(int z = (int)box.getMin().z; z < box.getMax().z; z++) {
        Block block = getTerrain().getBlock(x, z);
        max = Math.max(max, block.getHeight());
      }
    }

    return max;
  }

  public int getMinHeightForSelection(BoundingBox box) {
    int min = 0;
    for(int x = (int)box.getMin().x; x < box.getMax().x; x++) {
      for(int z = (int)box.getMin().z; z < box.getMax().z; z++) {
        Block block = getTerrain().getBlock(x, z);
        min = Math.min(min, block.getHeight());
      }
    }

    return min;
  }
}
