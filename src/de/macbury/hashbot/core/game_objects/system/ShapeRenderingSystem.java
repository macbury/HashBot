package de.macbury.hashbot.core.game_objects.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.macbury.hashbot.core.debug.DebugShape;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.debug.LineBoxComponent;

/**
 * Created by macbury on 03.05.14.
 */
public class ShapeRenderingSystem extends EntitySystem {
  private ShapeRenderer shapeRenderer;
  @Mapper ComponentMapper<LineBoxComponent> lbm;
  @Mapper ComponentMapper<ActorComponent> am;

  public ShapeRenderingSystem(ShapeRenderer shapeRenderer) {
    super(Aspect.getAspectForAll(ActorComponent.class, LineBoxComponent.class));
    this.shapeRenderer = shapeRenderer;
  }

  @Override
  protected void processEntities(ImmutableBag<Entity> entities) {
    for (int i = 0; i < entities.size(); i++) {
      Entity e                          = entities.get(i);

      LineBoxComponent lineBoxComponent = lbm.get(e);
      ActorComponent   actorComponent   = am.get(e);
      if (actorComponent.shouldRender()) {
        if (lineBoxComponent != null) {
          shapeRenderer.begin(ShapeRenderer.ShapeType.Line); {
            shapeRenderer.setColor(lineBoxComponent.getColor());
            DebugShape.draw(shapeRenderer, actorComponent.getBoundingBox());
          } shapeRenderer.end();
        }
      }
    }
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }
}
