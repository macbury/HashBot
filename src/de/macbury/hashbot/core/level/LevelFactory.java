package de.macbury.hashbot.core.level;

import de.macbury.hashbot.core.level.map.Map;
import de.macbury.hashbot.core.level.map.exceptions.LevelInvalidDimensionException;

/**
 * Created by macbury on 30.04.14.
 */
public class LevelFactory {

  public static Level newLevel(int rows, int cols) throws LevelInvalidDimensionException {
    Level level = new Level();
    Map map     = new Map(cols, rows);
    map.bootstrap();
    level.setMap(map);
    return level;
  }
}
