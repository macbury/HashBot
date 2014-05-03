package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.CursorDefiniton;
import de.macbury.hashbot.core.graphics.ui.SolarizedDarkColors;
import de.macbury.hashbot.core.graphics.ui.code_editor.CodeEditorView;
import de.macbury.hashbot.core.graphics.ui.code_editor.widget.CodeEditorTextArea;
import de.macbury.hashbot.core.graphics.ui.dialogs.CodeEditorDialog;
import de.macbury.hashbot.core.graphics.ui.dialogs.ConfirmDialog;
import de.macbury.hashbot.core.graphics.ui.dialogs.FilePickerDialog;
import de.macbury.hashbot.core.graphics.ui.widgets.ProfileButton;
import de.macbury.hashbot.core.graphics.ui.widgets.UIButton;
import de.macbury.hashbot.core.graphics.ui.dialogs.SettingsDialog;
import de.macbury.hashbot.core.graphics.ui.widgets.UICheckBox;
import de.macbury.hashbot.core.progress.GameProfile;
import de.macbury.hashbot.core.screens.menu.MenuBackButton;

/**
 * Created by macbury on 22.04.14.
 */
public class UIManager {
  public CursorDefiniton currentCursor;
  public CursorDefiniton arrowCursor;
  public TextureAtlas atlas;
  public BitmapFont uiFont;
  public UIButton.UITextButtonStyle normalButtonStyle;
  public Skin skin;
  public Window.WindowStyle dialogStyle;
  public BitmapFont uiFontSmall;
  public UIButton.UITextButtonStyle menuButtonStyle;
  public Label.LabelStyle normalLabel;
  public List.ListStyle listStyle;
  public ScrollPane.ScrollPaneStyle darkScrollPaneStyle;
  public SelectBox.SelectBoxStyle selectBoxStyle;
  public Slider.SliderStyle sliderStyle;
  public UICheckBox.UICheckBoxStyle checkBoxStyle;
  public TextureRegion fadeBackground;
  public ScrollPane.ScrollPaneStyle lightScrollPaneStyle;
  public CodeEditorTextArea.CodeEditorTextAreaStyle codeEditorStyle;
  public BitmapFont codeFont;
  public CursorDefiniton textCursor;
  public Window.WindowStyle dialogNpnModalStyle;
  public CursorDefiniton grabCursor;

  public void load() {
    this.atlas = (TextureAtlas)HashBot.assets.get(Assets.ATLAS_UI);
    this.skin  = new Skin(atlas);

    arrowCursor = new CursorDefiniton(atlas.findRegion("arrow"));
    textCursor  = new CursorDefiniton(atlas.findRegion("ibeam"));
    grabCursor  = new CursorDefiniton(atlas.findRegion("closedhand"));
    Gdx.input.setCursorImage((Pixmap)HashBot.assets.get(Assets.CURSOR_PLACEHOLDER), 0,0);
    normalCursor();

    this.uiFont = (BitmapFont)HashBot.assets.get(Assets.FONT_UI);
    this.uiFontSmall = (BitmapFont)HashBot.assets.get(Assets.FONT_UI_SMALL);
    this.codeFont = (BitmapFont)HashBot.assets.get(Assets.FONT_CODE);
    this.normalLabel            = new Label.LabelStyle();
    normalLabel.font            = uiFontSmall;
    normalLabel.fontColor       = Color.WHITE;

    this.normalButtonStyle      = new UIButton.UITextButtonStyle();
    normalButtonStyle.font      = uiFontSmall;
    normalButtonStyle.fontColor = Color.WHITE;
    normalButtonStyle.up        = skin.getDrawable("button_normal");
    normalButtonStyle.over      = skin.getDrawable("button_hover");
    normalButtonStyle.down      = skin.getDrawable("button_pressed");
    normalButtonStyle.clickSound = HashBot.assets.get(Assets.SOUND_CLICK);
    normalButtonStyle.hoverSound = HashBot.assets.get(Assets.SOUND_HOVER);

    this.menuButtonStyle        = new UIButton.UITextButtonStyle();
    menuButtonStyle.font      = uiFont;
    menuButtonStyle.fontColor = Color.WHITE;
    menuButtonStyle.up        = skin.getDrawable("button_normal");
    menuButtonStyle.over      = skin.getDrawable("button_hover");
    menuButtonStyle.down      = skin.getDrawable("button_pressed");
    menuButtonStyle.clickSound = HashBot.assets.get(Assets.SOUND_CLICK);
    menuButtonStyle.hoverSound = HashBot.assets.get(Assets.SOUND_HOVER);

    this.dialogStyle            = new Dialog.WindowStyle();
    dialogStyle.stageBackground = skin.getDrawable("window_foreground");
    dialogStyle.background      = skin.getDrawable("window_body");
    dialogStyle.titleFont       = uiFontSmall;
    dialogStyle.titleFontColor  = Color.WHITE;

    this.dialogNpnModalStyle            = new Dialog.WindowStyle();
    dialogNpnModalStyle.background      = skin.getDrawable("window_body");
    dialogNpnModalStyle.titleFont       = uiFontSmall;
    dialogNpnModalStyle.titleFontColor  = Color.WHITE;

    this.listStyle                = new List.ListStyle();
    //listStyle.background          = skin.getDrawable("list_view_background");
    listStyle.font                = uiFontSmall;
    listStyle.fontColorSelected   = Color.WHITE;
    listStyle.fontColorUnselected = Color.WHITE;
    listStyle.selection           = skin.getDrawable("list_view_selected_background");

    this.lightScrollPaneStyle          = new ScrollPane.ScrollPaneStyle();
    lightScrollPaneStyle.vScroll       = skin.getDrawable("scroll_pane_knob_background");
    lightScrollPaneStyle.hScroll       = skin.getDrawable("scroll_pane_knob_background");
    lightScrollPaneStyle.hScrollKnob   = skin.getDrawable("scroll_pane_knob");
    lightScrollPaneStyle.vScrollKnob   = skin.getDrawable("scroll_pane_knob");
    lightScrollPaneStyle.background    = skin.getDrawable("scroll_pane_light_background");

    this.darkScrollPaneStyle          = new ScrollPane.ScrollPaneStyle();
    darkScrollPaneStyle.vScroll       = skin.getDrawable("scroll_pane_knob_background");
    darkScrollPaneStyle.hScroll       = skin.getDrawable("scroll_pane_knob_background");
    darkScrollPaneStyle.hScrollKnob   = skin.getDrawable("scroll_pane_knob");
    darkScrollPaneStyle.vScrollKnob   = skin.getDrawable("scroll_pane_knob");
    darkScrollPaneStyle.background    = skin.getDrawable("scroll_pane_background");

    this.selectBoxStyle           = new SelectBox.SelectBoxStyle();
    selectBoxStyle.scrollStyle    = darkScrollPaneStyle;
    selectBoxStyle.listStyle      = listStyle;
    selectBoxStyle.fontColor      = Color.WHITE;
    selectBoxStyle.background     = skin.getDrawable("select_normal");
    selectBoxStyle.backgroundOpen = skin.getDrawable("select_pressed");
    selectBoxStyle.backgroundOver = skin.getDrawable("select_hover");
    selectBoxStyle.font           = uiFontSmall;

    this.sliderStyle        = new Slider.SliderStyle();
    sliderStyle.background  = skin.getDrawable("scroll_pane_knob_background");
    sliderStyle.knob        = skin.getDrawable("scroll_pane_knob");

    this.checkBoxStyle              = new UICheckBox.UICheckBoxStyle();
    this.checkBoxStyle.checkboxOn   = skin.getDrawable("checkbox_on");
    this.checkBoxStyle.checkboxOff  = skin.getDrawable("checkbox_off");
    checkBoxStyle.clickSound        = HashBot.assets.get(Assets.SOUND_CLICK);
    checkBoxStyle.hoverSound        = HashBot.assets.get(Assets.SOUND_HOVER);
    checkBoxStyle.font              = uiFontSmall;

    this.fadeBackground             = skin.getRegion("fade_background");

    this.codeEditorStyle                            = new CodeEditorTextArea.CodeEditorTextAreaStyle();
    this.codeEditorStyle.background                 = skin.getDrawable("text_area_background");
    this.codeEditorStyle.focusedBackground          = skin.getDrawable("text_area_background");
    this.codeEditorStyle.focusedFontColor           = codeEditorStyle.fontColor = SolarizedDarkColors.TEXT;
    this.codeEditorStyle.font                       = codeFont;
    this.codeEditorStyle.selection                  = skin.getDrawable("text_area_selection");
    this.codeEditorStyle.cursor                     = skin.getDrawable("text_area_cursor");
    this.codeEditorStyle.lineNumberBackround        = skin.getDrawable("text_area_line_number_background");
    this.codeEditorStyle.focusedLineBackround       = skin.getDrawable("text_area_current_line_background");
    this.codeEditorStyle.lineNumberColor            = SolarizedDarkColors.LINE_NUMBER;
    this.codeEditorStyle.textColor                  = SolarizedDarkColors.TEXT;
    this.codeEditorStyle.syntaxCommentColor         = SolarizedDarkColors.COMMENT;
    this.codeEditorStyle.syntaxNumberColor          = SolarizedDarkColors.NUMBER;
    this.codeEditorStyle.syntaxStringColor          = SolarizedDarkColors.STRING;
    this.codeEditorStyle.syntaxKeywordColor         = SolarizedDarkColors.KEYWORD;
    this.codeEditorStyle.syntaxSpecialKeywordColor  = SolarizedDarkColors.SPECIAL_KEYWORD;
    this.codeEditorStyle.syntaxErrorLineBackground  = skin.getDrawable("textarea_syntax_error_line_number");
    this.codeEditorStyle.syntaxErrorTextColor       = Color.WHITE;
    //this.codeEditorStyle.exceptionGutterIcon        = skin.getDrawable("exception");
  }

  public void normalCursor() {
    currentCursor = arrowCursor;
  }

  public void textCursor() {
    currentCursor = textCursor;
  }

  public void grabCursor() {
    currentCursor = grabCursor;
  }

  public UICheckBox checkBox() {
    return new UICheckBox (this.checkBoxStyle);
  }

  public Slider slider(float min, float max, float stepSize) {
    return new Slider(min, max, stepSize, false, sliderStyle);
  }

  public ScrollPane scrollPane(Actor content) {
    return new ScrollPane(content, lightScrollPaneStyle);
  }

  public ScrollPane scrollPaneDark(Actor content) {
    return new ScrollPane(content, darkScrollPaneStyle);
  }

  public List list() {
    return new List(listStyle);
  }

  public SelectBox stringSelectBox() {
    return new SelectBox<String>(this.selectBoxStyle);
  }

  public SettingsDialog settingsDialog() {
    SettingsDialog dialog = new SettingsDialog(dialogStyle);
    return dialog;
  }

  public FilePickerDialog filePickerDialog(FileHandle handle) {
    return new FilePickerDialog(handle, dialogStyle);
  }

  public CodeEditorDialog codeEditorDialog() {
    return new CodeEditorDialog(dialogNpnModalStyle);
  }

  public Dialog dialog() {
    Dialog dialog = new Dialog("TEST", dialogStyle);
    dialog.setWidth(320);
    dialog.setHeight(240);
    dialog.setTitleAlignment(Align.left);
    dialog.setSize(800, 600);
    dialog.setResizable(true);

    dialog.getContentTable().row();
    dialog.getContentTable().add(labelI18n("test"));

    dialog.button(textButton("OK"));
    dialog.button(textButton("CANCEL"));
    return dialog;
  }

  public UIButton menuI18nButton(String key) {
    UIButton button = new UIButton(HashBot.i18n.t(key), menuButtonStyle);
    return button;
  }

  public UIButton menuButton(String text) {
    UIButton button = new UIButton(text, menuButtonStyle);
    return button;
  }

  public ProfileButton profileButton(GameProfile profile) {
    return new ProfileButton(profile, normalButtonStyle);
  }

  public Label labelI18n(String i18nKey) {
    return label(HashBot.i18n.t(i18nKey));
  }

  public Label label(String text) {
    return new Label(text, normalLabel);
  }

  public UIButton textI18nButton(String i18nKey) {
    UIButton button = new UIButton(HashBot.i18n.t(i18nKey), normalButtonStyle);
    return button;
  }

  public UIButton textButton(String text) {
    UIButton button = new UIButton(text, normalButtonStyle);
    return button;
  }

  public UIButton menuBackButton() {
    MenuBackButton button = new MenuBackButton(HashBot.i18n.t("menu_screen.back"), normalButtonStyle);
    return button;
  }

  public CodeEditorView codeEditorTextArea() {
    return new CodeEditorView();
  }

  public ConfirmDialog confirm(String titleKey, String messageKey) {
    return new ConfirmDialog(titleKey, messageKey, dialogStyle);
  }
}
