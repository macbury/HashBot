package de.macbury.hashbot.core.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.macbury.hashbot.core.partition.QuadTree;

/**
 * Created by macbury on 09.03.14.
 */
public class DebugQuadTree {

  public static void debug(ShapeRenderer renderer, QuadTree tree) {
    renderer.setColor(Color.GRAY);
    if (tree.haveNodes()) {
      debug(renderer, tree.getNode(QuadTree.BOTTOM_LEFT));
      debug(renderer, tree.getNode(QuadTree.BOTTOM_RIGHT));
      debug(renderer, tree.getNode(QuadTree.TOP_RIGHT));
      debug(renderer, tree.getNode(QuadTree.TOP_LEFT));
    } else {
      DebugShape.draw(renderer, tree.getBounds());
    }
  }
}
