package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.CursorDefiniton;
import de.macbury.hashbot.core.graphics.ui.widgets.UIButton;
import de.macbury.hashbot.core.graphics.ui.dialogs.SettingsDialog;
import de.macbury.hashbot.core.graphics.ui.widgets.UICheckBox;
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
  public ScrollPane.ScrollPaneStyle scrollPaneStyle;
  public SelectBox.SelectBoxStyle selectBoxStyle;
  public Slider.SliderStyle sliderStyle;
  public UICheckBox.UICheckBoxStyle checkBoxStyle;

  public void load() {
    this.atlas = (TextureAtlas)HashBot.assets.get(Assets.ATLAS_UI);
    this.skin  = new Skin(atlas);

    arrowCursor = new CursorDefiniton(atlas.findRegion("arrow"));

    Gdx.input.setCursorImage((Pixmap)HashBot.assets.get(Assets.CURSOR_PLACEHOLDER), 0,0);
    normalCursor();

    this.uiFont = (BitmapFont)HashBot.assets.get(Assets.FONT_UI);
    this.uiFontSmall = (BitmapFont)HashBot.assets.get(Assets.FONT_UI_SMALL);

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

    this.listStyle                = new List.ListStyle();
    //listStyle.background          = skin.getDrawable("list_view_background");
    listStyle.font                = uiFontSmall;
    listStyle.fontColorSelected   = Color.WHITE;
    listStyle.fontColorUnselected = Color.WHITE;
    listStyle.selection           = skin.getDrawable("list_view_selected_background");

    this.scrollPaneStyle          = new ScrollPane.ScrollPaneStyle();
    scrollPaneStyle.vScroll       = skin.getDrawable("scroll_pane_knob_background");
    scrollPaneStyle.hScroll       = skin.getDrawable("scroll_pane_knob_background");
    scrollPaneStyle.hScrollKnob   = skin.getDrawable("scroll_pane_knob");
    scrollPaneStyle.vScrollKnob   = skin.getDrawable("scroll_pane_knob");
    scrollPaneStyle.background    = skin.getDrawable("scroll_pane_background");

    this.selectBoxStyle           = new SelectBox.SelectBoxStyle();
    selectBoxStyle.scrollStyle    = scrollPaneStyle;
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

    checkBoxStyle.clickSound = HashBot.assets.get(Assets.SOUND_CLICK);
    checkBoxStyle.hoverSound = HashBot.assets.get(Assets.SOUND_HOVER);

    this.checkBoxStyle.font = uiFontSmall;
  }

  public void normalCursor() {
    currentCursor = arrowCursor;
  }

  public UICheckBox checkBox() {
    return new UICheckBox (this.checkBoxStyle);
  }

  public Slider slider(float min, float max, float stepSize) {
    return new Slider(min, max, stepSize, false, sliderStyle);
  }

  public SelectBox stringSelectBox() {
    return new SelectBox<String>(this.selectBoxStyle);
  }

  public SettingsDialog settingsDialog() {
    SettingsDialog dialog = new SettingsDialog(dialogStyle);
    return dialog;
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

  public UIButton menuButton(String text) {
    UIButton button = new UIButton(text, menuButtonStyle);
    return button;
  }

  public Label labelI18n(String i18nKey) {
    return new Label(HashBot.i18n.t(i18nKey), normalLabel);
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
}
