package de.macbury.hashbot.core.graphics.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import de.macbury.hashbot.core.graphics.ui.widgets.UIStage;
import de.macbury.hashbot.core.managers.ScreenManager;

/**
 * Created by macbury on 25.04.14.
 */
public class ScreenManagerAccessor implements TweenAccessor<ScreenManager> {
  public final static int FADE = 1;
  @Override
  public int getValues(ScreenManager target, int tweenType, float[] returnValues) {
    if (tweenType == FADE) {
      returnValues[0] = target.getFadeAlpha();
      return 1;
    }
    return -1;
  }

  @Override
  public void setValues(ScreenManager target, int tweenType, float[] newValues) {
    if (tweenType == FADE) {
      target.setFadeAlpha(newValues[0]);
    }
  }
}
