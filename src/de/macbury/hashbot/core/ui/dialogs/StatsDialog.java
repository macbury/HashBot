package de.macbury.hashbot.core.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.models.RenderDebugStats;
import de.macbury.hashbot.core.graphics.rendering.mrt.MRTRenderingEngine;
import de.macbury.hashbot.core.level.Level;
import de.macbury.hashbot.core.managers.UIManager;
import de.macbury.hashbot.core.time.BaseTimer;
import de.macbury.hashbot.core.time.IntervalTimer;
import de.macbury.hashbot.core.time.TimerListener;
import de.macbury.hashbot.core.ui.debug.RenderTargetPreview;

/**
 * Created by macbury on 07.05.14.
 */
public class StatsDialog extends UIDialog implements TimerListener {

  private ScrollPane scrollPane;
  private IntervalTimer updateTimer;
  private Label fpsLabel;
  private Label renderablesLabel;
  private RenderDebugStats stats;
  private Label verticiesLabel;

  public StatsDialog(Level level, WindowStyle style) {
    super("Stats", style);

    this.stats = level.getStats();
    this.verticiesLabel   = HashBot.ui.label("Verticies: ");
    this.renderablesLabel = HashBot.ui.label("Renderables: ");
    this.fpsLabel         = HashBot.ui.label("FPS: ");

    Table mrtTable        = new Table();

    if (MRTRenderingEngine.class.isInstance(level.renderingEngine)) {
      MRTRenderingEngine engine = (MRTRenderingEngine)level.renderingEngine;
      mrtTable.row(); {
        mrtTable.add(new RenderTargetPreview(engine.gBuffer.colorAttachment)).expandX().left();
      }

      mrtTable.row(); {
        mrtTable.add(new RenderTargetPreview(engine.gBuffer.normalsAttachment)).expandX().left();
      }

      mrtTable.row(); {
        mrtTable.add(new RenderTargetPreview(engine.gBuffer.positionAttachment)).expandX().left();
      }
    }

    this.scrollPane       = HashBot.ui.scrollPane(mrtTable);

    Table table = getContentTable();
    table.row(); {
      table.add(verticiesLabel).expandX().left().padTop(10);
    }

    table.row(); {
      table.add(renderablesLabel).expandX().left();
    }

    table.row(); {
      table.add(fpsLabel).expandX().left();
    }

    table.row(); {
      table.add(scrollPane).expand().fill();
    }

    setModal(false);

    this.updateTimer = new IntervalTimer(1);
    this.updateTimer.setListener(this);
    timerTick(updateTimer);
  }

  @Override
  public float getPrefWidth() {
    return 340;
  }

  @Override
  public float getPrefHeight() {
    return 800;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    updateTimer.update(delta);

  }

  @Override
  public Dialog show(Stage stage) {
    stats.setEnabled(true);
    super.show(stage);
    setPosition(stage.getWidth() - getPrefWidth(), 0);
    return this;
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
