package de.macbury.hashbot.core.graphics.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 24.04.14.
 */
public class UITable extends Table {

  public UITable() {
    super();
    if (HashBot.game.isDebug())
      debug();
  }

  public float getAlpha() {
    return getColor().a;
  }

  public void setAlpha(float a) {
    getColor().a = a;
  }
}
