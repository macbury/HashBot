package de.macbury.hashbot.core.game_objects.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LightComponent;
import de.macbury.hashbot.core.graphics.rendering.mrt.steps.sub.AccumulateLightsStep;

/**
 * Created by macbury on 09.05.14.
 */
public class LightRenderingSystem extends EntitySystem {
  @Mapper
  ComponentMapper<ActorComponent> am;
  @Mapper ComponentMapper<LightComponent> lm;
  private AccumulateLightsStep processor;

  public LightRenderingSystem() {
    super(Aspect.getAspectForAll(ActorComponent.class, LightComponent.class));
  }

  @Override
  protected void processEntities(ImmutableBag<Entity> entities) {
    int rendered = 0;
    for(int i = 0; i < entities.size(); i++) {
      Entity e             = entities.get(i);
      ActorComponent actor = am.get(e);

      if (actor.shouldRender()) {
        rendered++;
        LightComponent light = lm.get(e);
        light.worldTransform = actor.worldTransform;
        light.radius = actor.size.x / 2; //TODO: More inteligents
        //light.worldTransform.idt();
        //light.worldTransform.translate(actor.position);
        //TODO: recaulculate matrix only if needed!
        //TODO: maybe it is because is not one mesh instance butt 100 diffrent mesh ...
        //light.worldTransform.rotate(actor.rotation);
        //light.worldTransform.scale(actor.size.x, actor.size.y, actor.size.z);

        processor.renderLight(light, actor);
      }
    }

    //Gdx.app.log("Count", "Light: " + rendered); //Not culling!
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }

  public void setProcessor(AccumulateLightsStep processor) {
    this.processor = processor;
  }

  public AccumulateLightsStep getProcessor() {
    return processor;
  }
}
