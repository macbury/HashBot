package de.macbury.hashbot.core.level;

import com.artemis.Entity;
import com.artemis.World;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.debug.LineBoxComponent;
import de.macbury.hashbot.core.game_objects.components.level.CursorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LevelEditorComponent;
import de.macbury.hashbot.core.partition.GameObjectTree;

/**
 * Created by macbury on 05.05.14.
 */
public class EntityFactory {
  private GameObjectTree tree;
  private World world;

  public EntityFactory(Level level) {
    this.world = level.getWorld();
    this.tree  = level.getTree();
  }

  public Entity cursor() {
    Entity e              = world.createEntity();
    ActorComponent actor  = new ActorComponent();
    actor.position.y      = 1;
    actor.setSkipCulling(true);
    e.addComponent(actor);

    e.addComponent(new LineBoxComponent());
    e.addComponent(new LevelEditorComponent());
    e.addComponent(new CursorComponent());
    return e;
  }
}
