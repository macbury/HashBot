package de.macbury.hashbot.core.game_objects.components.debug;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by macbury on 03.05.14.
 */
public class LineBoxComponent extends Component {
  private Color color;

  public LineBoxComponent(Color newColor) {
    setColor(newColor);
  }

  public LineBoxComponent() {
    this(new Color(Color.WHITE));
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
