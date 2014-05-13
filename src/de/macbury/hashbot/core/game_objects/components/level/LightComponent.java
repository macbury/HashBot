package de.macbury.hashbot.core.game_objects.components.level;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import de.macbury.hashbot.core.graphics.mesh.MeshAssembler;

/**
 * Created by macbury on 09.05.14.
 */
public class LightComponent extends Component {
  public static Mesh pointLightMeshCache;
  public Mesh mesh;
  public Matrix4 worldTransform;
  public Color color;
  public float intensity;
  public float radius;

  public LightComponent(Color color, int intensity) {
    if (pointLightMeshCache == null) {
      MeshBuilder builder = new MeshBuilder(); //TODO: STATic mesh here

      builder.begin(VertexAttributes.Usage.Position); {
        //builder.part("light", GL30.GL_TRIANGLES);
        builder.part("light", GL30.GL_TRIANGLES);
        builder.box(0, 0, 0, 1, 1, 1);
        //builder
        //builder.box();
        //builder.sphere(1,1,1, 8,8);
      }

      pointLightMeshCache = builder.end();
    }


    mesh = pointLightMeshCache;

    worldTransform = new Matrix4();
    worldTransform.translate(1,1,1);
    this.color     = color;
    this.intensity = intensity;
  }
}
