package de.macbury.hashbot.core.game_objects.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.level.FogComponent;
import de.macbury.hashbot.core.game_objects.components.level.LightComponent;
import de.macbury.hashbot.core.game_objects.components.level.ModelComponent;
import de.macbury.hashbot.core.level.map.FogManager;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.level.map.blocks.Block;

/**
 * Created by macbury on 13.05.14.
 */
public class FogSystem extends EntitySystem {
  private Terrain terrain;
  @Mapper
  ComponentMapper<ActorComponent> am;
  @Mapper
  ComponentMapper<FogComponent> fm;
  private final FogManager fog;

  public FogSystem(FogManager fog, Terrain terrain) {
    super(Aspect.getAspectForAll(ActorComponent.class, FogComponent.class));
    this.fog      = fog;
    this.terrain  = terrain;
  }

  @Override
  protected void processEntities(ImmutableBag<Entity> entities) {
    fog.update();
    fog.begin(); {
      for(int i = 0; i < entities.size(); i++) {
        Entity e             = entities.get(i);
        ActorComponent actorComponent = am.get(e);
        FogComponent   fogComponent   = fm.get(e);

        Block block                   = terrain.getBlock(actorComponent.position);

        fog.applyFov(block.getHeight(), fogComponent.fieldOfView, Math.round(actorComponent.position.x), Math.round(actorComponent.position.z));
      }
    } fog.end();
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }
}
