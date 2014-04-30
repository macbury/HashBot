package de.macbury.hashbot.core.graphics.ui.dialogs;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.code_editor.CodeEditorView;

/**
 * Created by macbury on 30.04.14.
 */
public class CodeEditorDialog extends UIDialog {
  private CodeEditorView codeEditorScrollPane;
  private static final float H_PADDING = 10;
  private static final float V_PADDING = 10;
  public CodeEditorDialog(WindowStyle windowStyle) {
    super("Code Editor", windowStyle);

    this.codeEditorScrollPane = HashBot.ui.codeEditorTextArea();

    Table contentTable = getContentTable();
    contentTable.row(); {
      contentTable.add(HashBot.ui.textButton("Load")).padTop(V_PADDING).padLeft(H_PADDING);
      contentTable.add(HashBot.ui.textButton("Save")).padTop(V_PADDING);
      contentTable.add(HashBot.ui.textButton("Run")).padTop(V_PADDING);
      contentTable.add().expandX();
    }
    contentTable.row(); {
      contentTable.add(codeEditorScrollPane).fill().pad(0, H_PADDING, V_PADDING, H_PADDING).expand().colspan(4);
    }

    setModal(false);
    setResizable(true);
  }

  @Override
  public Dialog show(Stage stage) {
    super.show(stage);
    codeEditorScrollPane.focus();
    return this;
  }

  @Override
  public float getPrefWidth() {
    return 800;
  }

  @Override
  public float getPrefHeight() {
    return 600;
  }

  @Override
  public void hide() {
    codeEditorScrollPane.unfocus();
    super.hide();
  }
}
