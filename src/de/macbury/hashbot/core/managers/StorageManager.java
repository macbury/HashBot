package de.macbury.hashbot.core.managers;

import com.esotericsoftware.kryo.Kryo;
import de.macbury.hashbot.core.level.Level;
import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.serializers.LevelSerializer;
import de.macbury.hashbot.core.serializers.MapSerializer;

/**
 * Created by macbury on 30.04.14.
 */
public class StorageManager {
  private Kryo kryo;

  public StorageManager() {
    this.kryo = new Kryo();
    kryo.register(Level.class, new LevelSerializer());
    kryo.register(Terrain.class, new MapSerializer());
  }
}
