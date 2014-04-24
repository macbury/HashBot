package de.macbury.hashbot.core.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by macbury on 23.04.14.
 */
public class CursorDefiniton {
  public Vector2 offset = new Vector2(0,0);
  public TextureRegion texture;

  public CursorDefiniton(TextureRegion region) {
    texture = region;
  }

  public CursorDefiniton(TextureRegion region, Vector2 offset) {
    this(region);
    this.offset = offset;
  }

  float tempAlpha = 1;

  public void draw(Batch batch) {
    batch.begin();
      tempAlpha = batch.getColor().a;
      batch.getColor().a = 1f;
      batch.draw(texture, Gdx.input.getX() + offset.x, Gdx.graphics.getHeight() - Gdx.input.getY() - texture.getRegionHeight() + offset.y);
      batch.getColor().a = tempAlpha;
    batch.end();
  }
}
