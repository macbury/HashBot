package de.macbury.hashbot.language.core.errors;

import de.macbury.hashbot.language.core.tokenize.Scanner;

/**
 * Created by macbury on 28.04.14.
 */
public class SyntaxError extends ScriptError {
  public SyntaxError(Scanner.Token token) {
    super(token);
  }

  @Override
  public String getMessage() {
    if (token != null) {
      return "Syntax Error on line " + token.line + " and column " + token.column;
    } else {
      return super.getMessage();
    }
  }
}
