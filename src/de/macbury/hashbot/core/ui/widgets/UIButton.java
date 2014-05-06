package de.macbury.hashbot.core.ui.widgets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by macbury on 23.04.14.
 */
public class UIButton extends TextButton {
  private UITextButtonStyle style;

  public UIButton(String text, UITextButtonStyle style) {
    super(text, style);
    this.style = style;
    addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        if (UIButton.this.style.hoverSound != null && fromActor == null) {
          UIButton.this.style.hoverSound.play();
        }
      }

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (UIButton.this.style.clickSound != null) {
          UIButton.this.style.clickSound.play();
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  public static class UITextButtonStyle extends TextButtonStyle {
    public Sound hoverSound;
    public Sound clickSound;
  }
}
