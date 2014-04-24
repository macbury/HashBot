package de.macbury.hashbot.core.graphics.models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by macbury on 23.04.14.
 */
public class LogoInstance extends ModelInstance {
  private static final float ROTATION_SPEED = 90;
  public Vector3 position;
  private float rotation;
  public LogoInstance(Model model) {
    super(model);
    position = new Vector3();
    rotation = 0.0f;
  }

  public void update(float delta) {
    transform.idt();
    transform.translate(position);
    rotation += ROTATION_SPEED * delta;
    transform.rotate(Vector3.Z, rotation);
    //transform.rotate(Vector3.Y, -rotation);
  }
}
