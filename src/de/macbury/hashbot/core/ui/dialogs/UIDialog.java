package de.macbury.hashbot.core.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import de.macbury.hashbot.core.ui.widgets.UIStage;

/**
 * Created by macbury on 24.04.14.
 */
public class UIDialog extends Dialog {
  private boolean showed = false;

  public UIDialog(String title, WindowStyle style) {
    super(title, style);
    setTitleAlignment(Align.left);
  }

  public void toggleVisibility(UIStage stage) {
    if (showed) {
      hide();
    } else {
      show(stage);
    }
  }

  @Override
  public Dialog show(Stage stage) {
    showed = true;
    return super.show(stage);
  }

  @Override
  public void hide() {
    super.hide();
    showed = false;
  }
}
