package de.macbury.hashbot.core.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.macbury.hashbot.core.level.Level;

/**
 * Created by macbury on 01.05.14.
 */
public class LevelSerializer extends Serializer<Level> {

  @Override
  public void write(Kryo kryo, Output output, Level object) {

  }

  @Override
  public Level read(Kryo kryo, Input input, Class<Level> type) {
    return null;
  }
}
