package de.macbury.hashbot.core.time;

/**
 * Created by macbury on 30.04.14.
 */
public class IntervalTimer {
  private float acumulated = 0.0f;
  private float maxTime    = 1.0f;
  private IntervalTimerListener listener;

  public IntervalTimer(float max) {
    this.maxTime = max;
  }

  public void update(float dt) {
    acumulated += dt;
    if (acumulated > maxTime) {
      acumulated = 0;
      if (listener != null)
        listener.timerTick(this);
    }

  }

  public void setListener(IntervalTimerListener listener) {
    this.listener = listener;
  }
}
