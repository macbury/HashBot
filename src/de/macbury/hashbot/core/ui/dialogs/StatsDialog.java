package de.macbury.hashbot.core.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.models.RenderDebugStats;
import de.macbury.hashbot.core.time.BaseTimer;
import de.macbury.hashbot.core.time.IntervalTimer;
import de.macbury.hashbot.core.time.TimerListener;

/**
 * Created by macbury on 07.05.14.
 */
public class StatsDialog extends UIDialog implements TimerListener {

  private IntervalTimer updateTimer;
  private Label fpsLabel;
  private Label renderablesLabel;
  private RenderDebugStats stats;
  private Label verticiesLabel;

  public StatsDialog(RenderDebugStats stats, WindowStyle style) {
    super("Stats", style);

    this.stats = stats;
    this.verticiesLabel   = HashBot.ui.label("Verticies: ");
    this.renderablesLabel = HashBot.ui.label("Renderables: ");
    this.fpsLabel         = HashBot.ui.label("FPS: ");
    Table table = getContentTable();
    table.row(); {
      table.add(verticiesLabel).expandX().left();
    }

    table.row(); {
      table.add(renderablesLabel).expandX().left();
    }

    table.row(); {
      table.add(fpsLabel).expandX().left();
    }

    table.row(); {
      table.add().expand();
    }

    setModal(false);

    this.updateTimer = new IntervalTimer(1);
    this.updateTimer.setListener(this);
    timerTick(updateTimer);
  }

  @Override
  public float getPrefWidth() {
    return 320;
  }

  @Override
  public float getPrefHeight() {
    return 160;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    updateTimer.update(delta);

  }

  @Override
  public Dialog show(Stage stage) {
    stats.setEnabled(true);
    return super.show(stage);
  }

  @Override
  public void hide() {
    super.hide();
    stats.setEnabled(false);
  }


  @Override
  public void timerTick(BaseTimer sender) {
    if (stats.isEnabled()) {
      verticiesLabel.setText("Verticies: " + stats.verticies);
      renderablesLabel.setText("Renderables: " + stats.renderables);
      fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
    }
  }
}
