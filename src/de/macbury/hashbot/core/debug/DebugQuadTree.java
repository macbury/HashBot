package de.macbury.hashbot.core.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.macbury.hashbot.core.partition.GameObjectTree;
import de.macbury.hashbot.core.partition.QuadTree;
import de.macbury.hashbot.core.partition.QuadTreeObject;

/**
 * Created by macbury on 09.03.14.
 */
public class DebugQuadTree {

  public static void debugFull(ShapeRenderer renderer, QuadTree tree) {
    renderer.setColor(Color.GRAY);
    renderer.begin(ShapeRenderer.ShapeType.Line); {
      debugNode(renderer, tree);
    } renderer.end();
  }

  public static void debugNode(ShapeRenderer renderer, QuadTree tree) {
    renderer.setColor(Color.GRAY);
    if (tree.haveNodes()) {
      debugNode(renderer, tree.getNode(QuadTree.BOTTOM_LEFT));
      debugNode(renderer, tree.getNode(QuadTree.BOTTOM_RIGHT));
      debugNode(renderer, tree.getNode(QuadTree.TOP_RIGHT));
      debugNode(renderer, tree.getNode(QuadTree.TOP_LEFT));
    } else {
      DebugShape.draw(renderer, tree.getBounds());
    }
  }

  public static void debug(ShapeRenderer renderer, GameObjectTree tree) {
    renderer.setColor(Color.GREEN);
    renderer.begin(ShapeRenderer.ShapeType.Line); {
      for(QuadTreeObject visible : tree.getVisibleObjects()) {
        DebugShape.draw(renderer, visible.getBoundingBox());
      }
    } renderer.end();

    debugFull(renderer, tree);
  }
}
