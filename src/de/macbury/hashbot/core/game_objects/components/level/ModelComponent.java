package de.macbury.hashbot.core.game_objects.components.level;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * Created by macbury on 09.05.14.
 */
public class ModelComponent extends Component {
  public ModelInstance instance;

  public ModelComponent(ModelInstance newInstance) {
    this.instance = newInstance;
  }
}
