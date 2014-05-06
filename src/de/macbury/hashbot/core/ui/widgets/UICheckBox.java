package de.macbury.hashbot.core.ui.widgets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by macbury on 25.04.14.
 */
public class UICheckBox extends CheckBox {
  private UICheckBoxStyle style;

  public UICheckBox(UICheckBoxStyle style) {
    super("", style);
    this.style = style;

    addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        if (UICheckBox.this.style.hoverSound != null && fromActor == null) {
          UICheckBox.this.style.hoverSound.play();
        }
      }

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (UICheckBox.this.style.clickSound != null) {
          UICheckBox.this.style.clickSound.play();
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  public static class UICheckBoxStyle extends CheckBoxStyle {
    public Sound hoverSound;
    public Sound clickSound;
  }
}
