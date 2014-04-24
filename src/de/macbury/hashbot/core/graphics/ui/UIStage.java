package de.macbury.hashbot.core.graphics.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.macbury.hashbot.core.HashBot;

/**
 * Created by macbury on 23.04.14.
 */
public class UIStage extends Stage {

  public Table table;

  public UIStage() {
    super(new ScreenViewport());
    this.table = new Table();

    if (HashBot.game.isDebug())
      table.debug();

    table.row();
    table.add();

    table.setTransform(true);
    addActor(table);
    table.setFillParent(true);
  }

  public void resize(int width, int height) {
    getViewport().update(width, height, true);
  }

  @Override
  public void draw() {
    super.draw();
    HashBot.ui.currentCursor.draw(getSpriteBatch());
  }
}
