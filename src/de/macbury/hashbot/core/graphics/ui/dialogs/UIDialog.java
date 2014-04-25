package de.macbury.hashbot.core.graphics.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.rmi.server.UID;

/**
 * Created by macbury on 24.04.14.
 */
public class UIDialog extends Dialog {
  public UIDialog(String title, WindowStyle style) {
    super(title, style);
    setTitleAlignment(Align.left);
  }
}
