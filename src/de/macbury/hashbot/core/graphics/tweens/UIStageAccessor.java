package de.macbury.hashbot.core.graphics.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import de.macbury.hashbot.core.graphics.ui.UIStage;

/**
 * Created by macbury on 24.04.14.
 */
public class UIStageAccessor implements TweenAccessor<UIStage>{
  public static final int FADE = 1;

  @Override
  public int getValues(UIStage target, int tweenType, float[] returnValues) {
    if (tweenType == FADE) {

      return 1;
    }
    return -1;
  }

  @Override
  public void setValues(UIStage target, int tweenType, float[] newValues) {

  }
}
