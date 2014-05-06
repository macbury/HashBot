package de.macbury.hashbot.core.screens.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.ui.widgets.UIButton;
import de.macbury.hashbot.core.ui.widgets.UITable;

/**
 * Created by macbury on 25.04.14.
 */
public class EditorTable extends UITable {

  private static final float TITLE_PADDING = 24;
  private final EditorTableListener listener;
  private UIButton brushButton;
  private UIButton testMapButton;
  private UIButton codeMapButton;
  private UIButton saveMapButton;
  private UIButton openMapButton;
  private UIButton newMapButton;
  private UIButton exitButton;

  public EditorTable(EditorTableListener listener) {
    super();
    this.listener = listener;
    setFillParent(true);
    setTransform(true);

    newMapButton  = HashBot.ui.textI18nButton("map_editor.new");
    newMapButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTable.this.listener.newMapButtonClicked();
      }
    });

    openMapButton = HashBot.ui.textI18nButton("map_editor.open");
    openMapButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTable.this.listener.openMapButtonClicked();
      }
    });
    saveMapButton = HashBot.ui.textI18nButton("map_editor.save");
    saveMapButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTable.this.listener.saveMapButtonClicked();
      }
    });

    brushButton   = HashBot.ui.textI18nButton("map_editor.brush.toggle");
    brushButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTable.this.listener.brushButtonClicked();
      }
    });
    codeMapButton = HashBot.ui.textI18nButton("map_editor.code");
    codeMapButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTable.this.listener.codeButtonClicked();
      }
    });

    testMapButton      = HashBot.ui.textI18nButton("map_editor.test");
    exitButton         = HashBot.ui.textButton("X");
    exitButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTable.this.listener.exitButtonClicked();
      }
    });

    row();
      add(openMapButton).fill();
      add(saveMapButton).fill();
      add(newMapButton).fill();
      add(HashBot.ui.label("New map")).padRight(TITLE_PADDING).padLeft(TITLE_PADDING);
      add().expandX();
      add(exitButton);
    row();
      add().colspan(5).expand();
    row();
      add(brushButton).fill();
      add(codeMapButton).fill();
      add(testMapButton).left();
  }
}
