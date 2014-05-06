package de.macbury.hashbot.core.ui.dialogs;

import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 30.04.14.
 */
public class ConfirmDialog extends UIDialog {
  private static final float PADDING = 10;
  private ConfirmDialogListener listener;
  public ConfirmDialog(String i18nTitleKey, String i18nMessageKey, WindowStyle windowStyle) {
    super(HashBot.i18n.t(i18nTitleKey), windowStyle);

    getContentTable().row(); {
      getContentTable().add(HashBot.ui.labelI18n(i18nMessageKey)).fill().pad(PADDING).expand();
    }

    button(HashBot.ui.textI18nButton("confirm.ok"), true);
    button(HashBot.ui.textI18nButton("confirm.cancel"), null);
  }

  @Override
  protected void result(Object object) {
    super.result(object);
    if (listener != null) {
      if (object == null) {
        listener.onConfirmCancelButton(this);
      } else {
        listener.onConfirmOkButton(this);
      }
    }
  }

  public void setListener(ConfirmDialogListener listener) {
    this.listener = listener;
  }

  public interface ConfirmDialogListener {
    public void onConfirmOkButton(ConfirmDialog sender);
    public void onConfirmCancelButton(ConfirmDialog sender);
  }
}
