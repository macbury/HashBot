package de.macbury.hashbot.core.ui.widgets;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.macbury.hashbot.core.graphics.tweens.UITableAccessor;

/**
 * Created by macbury on 23.04.14.
 */
public class UIStage extends Stage {

  private static final float FADE_SPEED = 0.25F;
  public TweenManager tweenEngine;
  protected UITable currentTable;
  private UITable tempTable;

  public UIStage() {
    super(new ScreenViewport());
    this.tweenEngine =  new TweenManager();
  }

  public UITable getCurrentTable() {
    return currentTable;
  }

  public void setCurrentTable(UITable nextTable) {
    if (nextTable == currentTable)
      return;
    Timeline sequence = Timeline.createSequence();
    final InputProcessor tempProcessor = Gdx.input.getInputProcessor();
    Gdx.input.setInputProcessor(null);
    sequence.beginParallel();
      if (currentTable != null) {
        this.tempTable = currentTable;

        sequence.push(Tween.to(currentTable, UITableAccessor.FADE, FADE_SPEED).target(0.0f));
      }
      this.currentTable = nextTable;
      if (currentTable != null) {
        addActor(currentTable);
        currentTable.setAlpha(0);
        sequence.push(Tween.to(currentTable, UITableAccessor.FADE, FADE_SPEED).target(1.0f));
      }
    sequence.end();

    sequence.setCallback(new TweenCallback() {
      @Override
      public void onEvent(int type, BaseTween<?> source) {
        if (tempTable != null)
          UIStage.this.tempTable.remove();
        tempTable = null;
        Gdx.input.setInputProcessor(tempProcessor);
      }
    });
    sequence.start(tweenEngine);
  }

  public void resize(int width, int height) {
    getViewport().update(width, height, true);
    if (getCurrentTable() != null)
      getCurrentTable().invalidate();
  }

  @Override
  public void draw() {
    super.draw();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    tweenEngine.update(delta);
  }

  @Override
  public void dispose() {
    super.dispose();
    tweenEngine.killAll();
  }
}
