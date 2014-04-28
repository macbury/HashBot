package de.macbury.hashbot.language.core.tokenize;

/**
 * Created by macbury on 27.04.14.
 */
public class KeywordList {
  private static final String TAG = "KeywordList";
  private final String[] keywords;
  private final int maxLength;

  public KeywordList(String ... keywords) {
    int len = 0;
    for(String kw : keywords) {
      len = Math.max(len, kw.length());
    }

    this.keywords = keywords;
    this.maxLength = len;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public boolean isKeyword(char[] buf, int start, int len) {
    if(len > maxLength) {
      return false;
    }

    int kwidx = 0;
    String text = "";
    for(int chpos=0 ; chpos < len ; chpos++) {
      text += buf[start + chpos];
    }

    for (String k : keywords) {
      if (k.equalsIgnoreCase(text)) {
        return true;
      }
    }

    return false;
  }
}
