package de.macbury.hashbot.core.graphics.ui.code_editor;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.ui.code_editor.widget.CodeEditorTextArea;

/**
 * Created by macbury on 06.04.14.
 */
public class CodeEditorView extends ScrollPane {

  private CodeEditorTextArea textArea;
  private String text;

  public CodeEditorView() {
    super(null, HashBot.ui.lightScrollPaneStyle);

    this.setFadeScrollBars(false);
    this.setFlickScroll(false);

    this.textArea = new CodeEditorTextArea(HashBot.ui.codeEditorStyle, this);
    this.setWidget(textArea);
  }

  public CodeEditorTextArea getTextArea() {
    return textArea;
  }

  public void setText(String text) {
    this.textArea.setText(text);
  }

  public void focus() {
    getStage().setScrollFocus(this);
    getStage().setKeyboardFocus(textArea);
    textArea.resetBlink();
  }

  public void unfocus() {
    getStage().unfocus(this);
    getStage().unfocus(textArea);
    HashBot.ui.normalCursor();
  }

  public String getText() {
    return textArea.getAllText();
  }
}
