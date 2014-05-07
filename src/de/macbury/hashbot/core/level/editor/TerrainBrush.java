package de.macbury.hashbot.core.level.editor;

import com.badlogic.gdx.math.collision.BoundingBox;
import de.macbury.hashbot.core.level.map.blocks.Block;

/**
 * Created by macbury on 05.05.14.
 */
public class TerrainBrush extends BrushBase{

  public TerrainBrush(LevelEditor level) {
    super(level);
  }

  @Override
  public void apply(BoundingBox box) {
    for(int x = (int)box.getMin().x; x < box.getMax().x; x++) {
      for(int z = (int)box.getMin().z; z < box.getMax().z; z++) {
        Block block = level.getTerrain().getBlock(x, z);
        block.setHeight(block.getHeight()+1);
        block.getChunk().rebuild();
      }
    }
  }
}
