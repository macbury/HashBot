package de.macbury.hashbot.core.level;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.game_objects.components.ActorComponent;
import de.macbury.hashbot.core.game_objects.components.debug.LineBoxComponent;
import de.macbury.hashbot.core.game_objects.components.level.CursorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LevelEditorComponent;
import de.macbury.hashbot.core.game_objects.components.level.LightComponent;
import de.macbury.hashbot.core.game_objects.components.level.ModelComponent;
import de.macbury.hashbot.core.partition.GameObjectTree;
import org.jruby.ir.operands.Hash;

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

  public Entity unit() {
    Entity e              = world.createEntity();
    ActorComponent actor  = new ActorComponent();
    actor.position.y      = 1;
    e.addComponent(actor);

    e.addComponent(new ModelComponent(HashBot.models.unit()));
    return e;
  }

  public Entity light(Vector3 position, Color color) {
    Entity e              = world.createEntity();
    ActorComponent actor  = new ActorComponent();
    actor.position.set(position);
    float size = (float) Math.random() * 10;
    actor.size.set(size,size,size);
    e.addComponent(actor);

    e.addComponent(new LightComponent(color, 10));
    return e;
  }
}
