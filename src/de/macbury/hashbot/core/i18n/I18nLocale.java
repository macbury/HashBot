package de.macbury.hashbot.core.i18n;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.yaml.snakeyaml.Yaml;
import org.yecht.YAML;

import java.util.HashMap;

/**
 * Created by macbury on 24.04.14.
 */
public class I18nLocale {

  private String name;
  public String key;
  private HashMap<String, String> keys;
  private Yaml yaml;

  public I18nLocale(FileHandle handle) {
    this.yaml        = new Yaml();
    this.keys        = new HashMap<String, String>();
    HashMap tempKeys = yaml.loadAs(handle.readString(), HashMap.class);
    for (Object k : tempKeys.keySet()) {
      this.key = (String)k;
      break;
    }

    this.name = handle.nameWithoutExtension();

    buildKeyset(tempKeys);
  }

  private void buildKeyset(HashMap tempKeys) {
    for(Object key : tempKeys.keySet()) {
      findKey(tempKeys, (String)key, (String)key);
    }
  }

  public String getKey(String key) {
    if (keys.containsKey(key)) {
      return keys.get(key);
    } else {
      return null;
    }
  }

  private void findKey(HashMap map, String finalKey, String currentKey) {
    Object value = map.get(currentKey);
    if (String.class.isInstance(value)) {
      keys.put(finalKey, (String)value);
    } else {
      HashMap<String, Object> subHash = (HashMap)value;
      for(String k : subHash.keySet()) {
        findKey(subHash, finalKey+"."+k, k);
      }
    }
  }

  public String getName() {
    return name;
  }
}
