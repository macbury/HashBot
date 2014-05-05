package de.macbury.hashbot.core.level;

import de.macbury.hashbot.core.level.map.Terrain;
import de.macbury.hashbot.core.level.map.exceptions.LevelInvalidDimensionException;

/**
 * Created by macbury on 30.04.14.
 */
public class LevelFactory {

  public static LevelEditor newLevel(int rows, int cols) throws LevelInvalidDimensionException {
    LevelEditor level = new LevelEditor();
    Terrain terrain = new Terrain(cols, rows);
    terrain.bootstrap();
    level.setTerrain(terrain);
    return level;
  }
}
