package de.macbury.hashbot.language.core.errors;

import de.macbury.hashbot.language.core.tokenize.Scanner;

/**
 * Created by macbury on 28.04.14.
 */
public class ScriptError extends RuntimeException {
  protected final Scanner.Token token;

  public ScriptError(Scanner.Token token) {
    this.token = token;
  }
}
