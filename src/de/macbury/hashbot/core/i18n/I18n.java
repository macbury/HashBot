package de.macbury.hashbot.core.i18n;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by macbury on 24.04.14.
 */
public class I18n {
  private static final String TAG = "I18n";
  public I18nLocale currentLocale;

  public I18n(FileHandle localeHandle) {
    currentLocale = new I18nLocale(localeHandle);
  }

  public String t(String key) {
    String localized = currentLocale.getKey(currentLocale.key + "." + key);
    if (localized != null) {
      return localized;
    } else {
      Gdx.app.error(TAG, "translation missing: "+key);
      return "translation missing: "+key;
    }
  }

  public String t(String key, Object... args) {
    String localized = currentLocale.getKey(currentLocale.key + "." + key);
    if (localized != null) {
      return String.format(localized, args);
    } else {
      Gdx.app.error(TAG, "translation missing: "+key);
      return "translation missing: "+key;
    }
  }
}
