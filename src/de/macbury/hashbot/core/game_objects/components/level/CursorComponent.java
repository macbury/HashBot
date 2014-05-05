package de.macbury.hashbot.core.game_objects.components.level;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by macbury on 03.05.14.
 */
public class CursorComponent extends Component {
  public Vector3 endPositon    = new Vector3();
  public Vector3 startPosition = new Vector3();
  private Vector3 tempPosition = new Vector3();
  public boolean selection = false;

  public void startSelection() {
    if (!selection) {
      this.startPosition.set(endPositon);
    }

    selection = true;
  }

  public void endSelection() {
    selection = false;
  }

  public void snapPosition() {
    endPositon.y = (float) Math.floor(endPositon.y);
    endPositon.x = (float) Math.floor(endPositon.x);
    endPositon.z = (float) Math.floor(endPositon.z);

    startPosition.y = (float) Math.floor(startPosition.y);
    startPosition.x = (float) Math.floor(startPosition.x);
    startPosition.z = (float) Math.floor(startPosition.z);
  }

  public Vector3 getBottomRightPosition() {
    int ex = (int) Math.min(this.startPosition.x, this.endPositon.x);
    int ez = (int) Math.min(this.startPosition.z, this.endPositon.z);

    return tempPosition.set(ex, endPositon.y, ez);
  }

  public float getWidth() {
    return Math.max(Math.abs(startPosition.x - endPositon.x), 1);
  }

  public float getHeight() {
    return Math.max(Math.abs(startPosition.z - endPositon.z), 1);
  }
}
