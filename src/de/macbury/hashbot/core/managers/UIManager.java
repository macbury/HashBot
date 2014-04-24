package de.macbury.hashbot.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.graphics.CursorDefiniton;
import de.macbury.hashbot.core.graphics.ui.UIButton;

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
  public void load() {
    this.atlas = (TextureAtlas)HashBot.assets.get(Assets.ATLAS_UI);
    this.skin  = new Skin(atlas);

    arrowCursor = new CursorDefiniton(atlas.findRegion("arrow"));

    Gdx.input.setCursorImage((Pixmap)HashBot.assets.get(Assets.CURSOR_PLACEHOLDER), 0,0);
    normalCursor();

    this.uiFont = (BitmapFont)HashBot.assets.get(Assets.FONT_UI);

    this.normalButtonStyle      = new UIButton.UITextButtonStyle();
    normalButtonStyle.font      = uiFont;
    normalButtonStyle.fontColor = Color.WHITE;
    normalButtonStyle.up        = skin.getDrawable("button_normal");
    normalButtonStyle.over      = skin.getDrawable("button_hover");
    normalButtonStyle.down      = skin.getDrawable("button_pressed");
    normalButtonStyle.clickSound = HashBot.assets.get(Assets.SOUND_CLICK);
    normalButtonStyle.hoverSound = HashBot.assets.get(Assets.SOUND_HOVER);
  }

  public void normalCursor() {
    currentCursor = arrowCursor;
  }

  public UIButton textButton(String text) {
    UIButton button = new UIButton(text, normalButtonStyle);
    return button;
  }
}
