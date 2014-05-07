package de.macbury.hashbot.core.ui.editor;

import com.badlogic.gdx.math.Vector3;
import de.macbury.hashbot.core.game_objects.system.LevelEditorSystem;

/**
 * Created by macbury on 06.05.14.
 */
public interface LevelEditorSystemListener {
  public void levelEditorSystemStartSelection(LevelEditorSystem levelEditorSystem);
  public void levelEditorSystemCursorMove(LevelEditorSystem levelEditorSystem);
  public void levelEditorSystemEndSelectionOrClick(LevelEditorSystem levelEditorSystem);
  public Vector3 levelEditorCursorMinimalDimension(LevelEditorSystem levelEditorSystem);
}
