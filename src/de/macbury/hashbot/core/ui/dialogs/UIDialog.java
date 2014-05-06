package de.macbury.hashbot.core.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by macbury on 24.04.14.
 */
public class UIDialog extends Dialog {
  public UIDialog(String title, WindowStyle style) {
    super(title, style);
    setTitleAlignment(Align.left);
  }
}
