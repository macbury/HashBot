package de.macbury.hashbot.core.game_objects.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.partition.GameObjectTree;

/**
 * Created by macbury on 05.05.14.
 */
public class ActorSystem extends EntitySystem {
  @Mapper
  ComponentMapper<ActorComponent> am;
  private GameObjectTree tree;

  public ActorSystem(GameObjectTree tree) {
    super(Aspect.getAspectForAll(ActorComponent.class));
    this.tree = tree;
  }

  // Check if moved, update visibility TODO: Implement all
  @Override
  protected void processEntities(ImmutableBag<Entity> entities) {
    this.tree.update(Gdx.graphics.getDeltaTime());

    for(int i = 0; i < entities.size(); i++) {
      Entity e             = entities.get(i);
      ActorComponent actor = am.get(e);
      if (actor.isModified()) {
        actor.computeMatrix();
        actor.removeFromParentTree();
        tree.insert(actor);
        actor.setModified(false);
      }
      actor.setVisibleInRenderTree(tree.getVisibleObjects().contains(actor, true));
    }
  }

  @Override
  protected boolean checkProcessing() {
    return true;
  }
}
