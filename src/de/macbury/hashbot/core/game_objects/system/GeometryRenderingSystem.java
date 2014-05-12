package de.macbury.hashbot.core.game_objects.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LevelEditorComponent;
import de.macbury.hashbot.core.game_objects.components.level.ModelComponent;

/**
 * Created by macbury on 09.05.14.
 */
public class GeometryRenderingSystem extends EntitySystem {
  private final ModelBatch batch;
  @Mapper ComponentMapper<ActorComponent> am;
  @Mapper ComponentMapper<ModelComponent> mm;

  public GeometryRenderingSystem(ModelBatch batch) {
    super(Aspect.getAspectForAll(ActorComponent.class, ModelComponent.class));
    this.batch = batch;
  }

  @Override
  protected void processEntities(ImmutableBag<Entity> entities) {
    for(int i = 0; i < entities.size(); i++) {
      Entity e             = entities.get(i);
      ActorComponent actor = am.get(e);

      if (actor.shouldRender()) {
        ModelComponent model    = mm.get(e);
        ModelInstance instance  = model.instance;

        instance.transform.idt();
        instance.transform.translate(actor.position);
        instance.transform.scl(actor.size);
        instance.transform.rotate(actor.rotation);
        batch.render(instance);
      }
    }
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }
}
