package de.macbury.hashbot.core.level.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.collision.BoundingBox;
import de.macbury.hashbot.core.ui.widgets.UITable;

/**
 * Created by macbury on 05.05.14.
 */
public abstract class BrushBase extends UITable {
  private static final String TAG = "BrushBase";
  protected LevelEditor level;

  public BrushBase(LevelEditor level) {
    this.level = level;
  }

  public void apply(BoundingBox rect) {
    Gdx.app.log(TAG, "Apply brush on " + rect.toString());
  }
}
