package de.macbury.hashbot.test;

import de.macbury.hashbot.language.Runtime;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by macbury on 26.04.14.
 */
public class TestLang {
  public static void main(String[] args) {
    Runtime lang = new de.macbury.hashbot.language.Runtime(true);
    try {
      lang.parse(readFile("./test.hb", StandardCharsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    }
    lang.run();
  }

  static String readFile(String path, Charset encoding)  throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }
}
