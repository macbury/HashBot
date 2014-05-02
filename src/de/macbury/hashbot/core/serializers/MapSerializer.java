package de.macbury.hashbot.core.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.macbury.hashbot.core.level.map.Terrain;

/**
 * Created by macbury on 01.05.14.
 */
public class MapSerializer extends Serializer<Terrain> {
  @Override
  public void write(Kryo kryo, Output output, Terrain object) {

  }

  @Override
  public Terrain read(Kryo kryo, Input input, Class<Terrain> type) {
    return null;
  }
}
