package de.macbury.hashbot.core.graphics.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

/**
 * Created by macbury on 05.05.14.
 */
public class GameUIOverlay extends Widget {
  public GameUIOverlay() {
    super();
    setFillParent(true);
  }

  private void blur() {
    Stage s = getStage();
    if (s != null) {
      s.unfocus(this);
    }
  }


  public boolean focused() {
    return (getStage() != null) && (getStage().getKeyboardFocus() == this);
  }

  public void focus() {
    if (getStage() != null) {
      getStage().setKeyboardFocus(this);
      getStage().setScrollFocus(this);
    }
  }
}
