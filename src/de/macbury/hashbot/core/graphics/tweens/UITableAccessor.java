package de.macbury.hashbot.core.graphics.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import de.macbury.hashbot.core.ui.widgets.UITable;

/**
 * Created by macbury on 24.04.14.
 */
public class UITableAccessor implements TweenAccessor<UITable> {
  public static final int FADE = 1;
  @Override
  public int getValues(UITable target, int tweenType, float[] returnValues) {
    if (tweenType == FADE) {
      returnValues[0] = target.getAlpha();
      return 1;
    }
    return -1;
  }

  @Override
  public void setValues(UITable target, int tweenType, float[] newValues) {
    if (tweenType == FADE) {
      target.setAlpha(newValues[0]);
    }
  }
}
