package de.macbury.hashbot.core.level.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.TextureBinder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.hashbot.core.level.map.blocks.Block;
import de.macbury.hashbot.core.time.BaseTimer;
import de.macbury.hashbot.core.time.FrameTickTimer;
import de.macbury.hashbot.core.time.TimerListener;

/**
 * Created by macbury on 12.05.14.
 */
public class FogManager implements Disposable, TimerListener {
  private static final int SYNC_TERRAIN_EVERY_FRAME = 4;
  private FrameTickTimer terrainSyncTimer;
  private Terrain terrain;
  private Pixmap visitedPixmap;
  private int height;
  private int width;
  private Texture texture;
  private Pixmap mainPixmap;
  private boolean enabled = true;
  public static final Color ACTIVE_TILE     = new Color(1,0,0,1);
  public static final Color UNIVISITED_TILE = new Color(0,0,0,1);
  public static final Color VISITED_TILE    = new Color(0.1f,0,0,1);
  private Color tempColor = new Color();
  public FogManager(Terrain terrain) {
    this.terrain       = terrain;
    this.width         = terrain.getWidth() ;
    this.height        = terrain.getHeight() ;
    this.mainPixmap    = new Pixmap(width, height, Pixmap.Format.RGB565);
    this.visitedPixmap = new Pixmap(width, height, Pixmap.Format.RGB565);
    this.texture = new Texture(width, height, Pixmap.Format.RGB565);
    texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    visitedPixmap.setColor(UNIVISITED_TILE);
    visitedPixmap.fill();
    clear();
    this.terrainSyncTimer = new FrameTickTimer(SYNC_TERRAIN_EVERY_FRAME);
    terrainSyncTimer.setListener(this);
  }

  public void clear() {
    if (enabled) {
      mainPixmap.setColor(UNIVISITED_TILE);
      mainPixmap.fill();
      mainPixmap.drawPixmap(visitedPixmap,0,0);
    } else {
      mainPixmap.setColor(ACTIVE_TILE);
      mainPixmap.fill();
    }
  }

  public int bind(TextureBinder binder) {
    return binder.bind(texture);
  }

  @Override
  public void dispose() {
    mainPixmap.dispose();
    texture.dispose();
    visitedPixmap.dispose();
  }

  public void begin() {
    clear();
  }

  public void update() {
    if (enabled)
      terrainSyncTimer.update();
  }

  public void applyFov(int fov, int x, int y) {
    mainPixmap.setColor(ACTIVE_TILE);
    mainPixmap.fillCircle(x, y, fov);
    visitedPixmap.setColor(VISITED_TILE);
    visitedPixmap.fillCircle(x, y, fov);
  }

  public void end() {
    mainPixmap.setColor(UNIVISITED_TILE);
    mainPixmap.drawRectangle(0, 0, width, height);
    this.texture.draw(mainPixmap, 0, 0);
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public void timerTick(BaseTimer sender) {
    for(int x = 0; x < width; x++) {
      for(int y = 0; y < width; y++) {
        Block block = terrain.getBlock(x,y);
        if (block != null) {
          Color.rgba8888ToColor(tempColor, visitedPixmap.getPixel(x,y));
          if (tempColor.equals(ACTIVE_TILE)) {
            block.markAsActive();
          } else if (tempColor.equals(VISITED_TILE)) {
            block.markAsVisited();
          }
        }
      }
    }
  }
}
