package de.macbury.hashbot.core.screens.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.macbury.hashbot.core.graphics.ui.widgets.UIButton;

/**
 * Created by macbury on 24.04.14.
 */
public class MenuBackButton extends UIButton {
  public MenuBackButton(String text, UITextButtonStyle style) {
    super(text, style);

    addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        MenuStage stage = (MenuStage)MenuBackButton.this.getStage();
        stage.goToDefault();
      }
    });
  }
}
