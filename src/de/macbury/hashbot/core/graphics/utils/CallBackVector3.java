package de.macbury.hashbot.core.graphics.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by macbury on 05.05.14.
 */
public class CallBackVector3 extends Vector3 {
  private Vector3Listener listener;

  public CallBackVector3() {
    super();
  }

  public CallBackVector3(float x, float y, float z) {
    super(x, y, z);
  }

  public CallBackVector3(Vector3 vector) {
    super(vector);
  }

  public CallBackVector3(float[] values) {
    super(values);
  }

  public CallBackVector3(Vector2 vector, float z) {
    super(vector, z);
  }

  @Override
  public Vector3 set(float x, float y, float z) {
    boolean modified = (this.x != x || this.y != y || this.z != z);
    super.set(x, y, z);
    if (listener != null && modified)
      listener.onVector3Change(this);
    return this;
  }

  public Vector3Listener getListener() {
    return listener;
  }

  public void setListener(Vector3Listener listener) {
    this.listener = listener;
  }
}
