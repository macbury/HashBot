package de.macbury.hashbot.core.i18n;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

/**
 * Created by macbury on 24.04.14.
 */
public class I18n {
  private static final String TAG = "I18n";
  public I18nLocale currentLocale;

  public I18n() {
  }

  public void setLocale(String name) {
    currentLocale = null;
    for(I18nLocale locale : all()) {
      if (locale.getName().equals(name)) {
        currentLocale = locale;
        break;
      }
    }
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

  public ArrayList<I18nLocale> all() {
    ArrayList<I18nLocale> list = new ArrayList<I18nLocale>();
    for(FileHandle localeHandle : Gdx.files.internal("i18n/").list()) {
      if (localeHandle.extension().equals("yml")) {
        list.add(new I18nLocale(localeHandle));
      }
    }
    return list;
  }
}