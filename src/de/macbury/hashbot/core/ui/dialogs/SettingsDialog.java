package de.macbury.hashbot.core.ui.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import de.macbury.hashbot.core.HashBot;
import de.macbury.hashbot.core.i18n.I18nLocale;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by macbury on 24.04.14.
 */
public class SettingsDialog extends UIDialog {
  private static final String TAG = "SettingsDialog";
  private static final float PADDING_VERITICAL = 10;
  private static final float PADDING_HORIZONTAL = 20;
  private static final float OPTION_WIDTH = 200;
  private static final float LABEL_WIDTH = 300;
  private static final float RESOLUTION_SCROLL_PANE_HEIGHT = 120;
  private static final float PADDING_BUTTONS_BOTTOM = 25;
  private SelectBox languageSelectBox;
  private CheckBox vsyncCheckbox;
  private CheckBox fullscreenCheckbox;
  private Slider musicVolumeSlider;
  private SelectBox qualitySelectBox;
  private ArrayList<TempDisplayMode> resModes;
  private SelectBox resolutionSelectBox;

  public SettingsDialog(WindowStyle style) {
    super(HashBot.i18n.t("settings.title"), style);

    setSize(800, 600);

    Table contentTable = getContentTable();
    this.fullscreenCheckbox = HashBot.ui.checkBox();
    fullscreenCheckbox.setChecked(Gdx.graphics.isFullscreen());

    this.vsyncCheckbox      = HashBot.ui.checkBox();
    vsyncCheckbox.setChecked(HashBot.config.isVSync());

    this.musicVolumeSlider  = HashBot.ui.slider(0,1,0.1f);
    musicVolumeSlider.setValue(HashBot.config.getMusicVolume());

    this.qualitySelectBox   = HashBot.ui.stringSelectBox();
    this.languageSelectBox  = HashBot.ui.stringSelectBox();

    Array<String> languageArray = new Array<String>();

    for(I18nLocale locale : HashBot.i18n.all()) {
      languageArray.add(locale.getName());
    }

    languageSelectBox.setItems(languageArray);
    languageSelectBox.setSelectedIndex(languageArray.indexOf(HashBot.config.getLanguage(), false));

    Array<String> qualityArray = new Array<String>();
    qualityArray.add(HashBot.i18n.t("settings.quality.good"));
    qualityArray.add(HashBot.i18n.t("settings.quality.none"));

    qualitySelectBox.setItems(qualityArray);

    if (HashBot.config.isGoodQuality()) {
      qualitySelectBox.setSelectedIndex(0);
    } else {
      qualitySelectBox.setSelectedIndex(1);
    }

    this.resolutionSelectBox = HashBot.ui.stringSelectBox();
    Array<String> resArrays = new Array<String>();
    this.resModes = new ArrayList<TempDisplayMode>();
    TempDisplayMode currentMode = new TempDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    try {
      for(DisplayMode mode : Display.getAvailableDisplayModes()) {
        TempDisplayMode newMode = new TempDisplayMode(mode);

        if(resModes.indexOf(newMode) == -1 && mode.isFullscreenCapable()) {
          resModes.add(newMode);
        }

        TempDisplayMode panoramicMode = newMode.toPanoramic();

        if(resModes.indexOf(panoramicMode) == -1) {
          resModes.add(panoramicMode);
        }
      }
    } catch (LWJGLException e) {
      e.printStackTrace();
    }


    Collections.sort(resModes);

    for(TempDisplayMode mode : resModes) {
      resArrays.add(mode.toString());
    }

    resolutionSelectBox.setItems(resArrays);
    int selectedResIndex = Math.max(0, resModes.indexOf(currentMode));
    resolutionSelectBox.setSelectedIndex(selectedResIndex);

    contentTable.row().padTop(PADDING_VERITICAL);
      contentTable.add(HashBot.ui.labelI18n("settings.resolution.label")).width(LABEL_WIDTH).padLeft(PADDING_HORIZONTAL).right().top();
      contentTable.add(resolutionSelectBox).colspan(2).fillX().padRight(PADDING_HORIZONTAL).width(OPTION_WIDTH);
    contentTable.row().padTop(PADDING_VERITICAL);
      contentTable.add(HashBot.ui.labelI18n("settings.resolution.fullscreen")).width(LABEL_WIDTH).padLeft(PADDING_HORIZONTAL).right().top();
      contentTable.add(fullscreenCheckbox).padRight(PADDING_HORIZONTAL).left();
    contentTable.row().padTop(PADDING_VERITICAL);
      contentTable.add(HashBot.ui.labelI18n("settings.resolution.vsync")).width(LABEL_WIDTH).padLeft(PADDING_HORIZONTAL).right().top();
      contentTable.add(vsyncCheckbox).padRight(PADDING_HORIZONTAL).left();
    contentTable.row().padTop(PADDING_VERITICAL);
      contentTable.add(HashBot.ui.labelI18n("settings.quality.label")).width(LABEL_WIDTH).padLeft(PADDING_HORIZONTAL).right().top();
      contentTable.add(qualitySelectBox).colspan(2).fillX().padRight(PADDING_HORIZONTAL).width(OPTION_WIDTH);
    contentTable.row().padTop(PADDING_VERITICAL);
      contentTable.add(HashBot.ui.labelI18n("settings.language.label")).width(LABEL_WIDTH).padLeft(PADDING_HORIZONTAL).right().top();
      contentTable.add(languageSelectBox).colspan(2).fillX().padRight(PADDING_HORIZONTAL).width(OPTION_WIDTH);
    contentTable.row().padTop(PADDING_VERITICAL);
      contentTable.add(HashBot.ui.labelI18n("settings.music.label")).width(LABEL_WIDTH).padLeft(PADDING_HORIZONTAL).right().top();
      contentTable.add(musicVolumeSlider).colspan(2).fillX().padRight(PADDING_HORIZONTAL).width(OPTION_WIDTH);
    contentTable.row();
      contentTable.add().colspan(3).padBottom(25);

    button(HashBot.ui.textI18nButton("settings.save"), true);
    button(HashBot.ui.textI18nButton("settings.cancel"), null);
  }

  @Override
  protected void result(Object object) {
    if (object != null) {
      TempDisplayMode mode = resModes.get(resolutionSelectBox.getSelectedIndex());
      HashBot.config.setMusicVolume(musicVolumeSlider.getValue());
      HashBot.config.putResolution(mode.width, mode.height, fullscreenCheckbox.isChecked(), vsyncCheckbox.isChecked());
      HashBot.config.setQuality(qualitySelectBox.getSelectedIndex() == 0);
      HashBot.config.setLanguage((String)languageSelectBox.getSelected());
      HashBot.config.load();
    }
  }

  class TempDisplayMode implements Comparable<TempDisplayMode> {
    public final int width;
    public final int height;

    public TempDisplayMode(int width, int height) {
      this.width = width;
      this.height = height;
    }

    public TempDisplayMode(DisplayMode mode) {
      this.width = mode.getWidth();
      this.height = mode.getHeight();
    }

    @Override
    public boolean equals(Object obj) {
      TempDisplayMode mode = (TempDisplayMode)obj;
      return mode.width == this.width && mode.height == this.height;
    }

    public int sum() {
      return width + height;
    }

    @Override
    public String toString() {
      return width+"x"+height;
    }

    @Override
    public int compareTo(TempDisplayMode o) {
      if (o.sum() > this.sum()){
        return 1;
      } else if (o.sum() < this.sum()) {
        return -1;
      } else {
        return 0;
      }
    }
    private final static float PANORAMA = 0.5625F;
    public TempDisplayMode toPanoramic() {
      return new TempDisplayMode(width, Math.round(width * PANORAMA));
    }
  }
}
