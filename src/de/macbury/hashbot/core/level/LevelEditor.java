package de.macbury.hashbot.core.level;

import com.artemis.Entity;
import de.macbury.hashbot.core.game_objects.system.LevelEditorSystem;
import de.macbury.hashbot.core.screens.editor.EditorTable;

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
}
