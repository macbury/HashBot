package de.macbury.hashbot.core.graphics.ui;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.tweens.UITableAccessor;

/**
 * Created by macbury on 23.04.14.
 */
public class UIStage extends Stage {

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
    Timeline sequence = Timeline.createSequence();

    sequence.beginParallel();
      if (currentTable != null) {
        this.tempTable = currentTable;

        sequence.push(Tween.to(currentTable, UITableAccessor.FADE, 1.0f).target(0.0f));
        sequence.setCallback(new TweenCallback() {
          @Override
          public void onEvent(int type, BaseTween<?> source) {
            UIStage.this.tempTable.remove();
          }
        });
      }
      this.currentTable = nextTable;
      if (currentTable != null) {
        addActor(currentTable);
        currentTable.setAlpha(0);
        sequence.pushPause(0.5f);
        sequence.push(Tween.to(currentTable, UITableAccessor.FADE, 1.0f).target(1.0f));
      }
    sequence.end();
    sequence.start(tweenEngine);
  }

  public void resize(int width, int height) {
    getViewport().update(width, height, true);
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
