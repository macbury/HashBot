package de.macbury.hashbot.language.core;

import de.macbury.hashbot.language.core.tokenize.Scanner;

import java.util.ArrayList;

/**
 * Created by macbury on 27.04.14.
 */
public class TokenList extends ArrayList<Scanner.Token> {
  private Scanner.Token currentToken;

  public Scanner.Token next() {
    if (!isEmpty()){
      return currentToken = remove(0);
    } else {
      return null;
    }
  }

  public boolean isNextComment() {
    return isNextKind(Scanner.Kind.COMMENT);
  }

  public boolean isNextEoFOrNewLine() {
    return isEmpty() || isNextKind(Scanner.Kind.NEWLINE) || isNextKind(Scanner.Kind.EOF);
  }

  public void skip() {
    next();
  }

  public boolean isNextKind(Scanner.Kind kind) {
    return !isEmpty() && peek().kind == kind;
  }

  public Scanner.Token current() {
    return currentToken;
  }

  public Scanner.Token peek() {
    return !isEmpty() ? get(0) : null;
  }

  public boolean isNextNumber() {
    return isNextKind(Scanner.Kind.NUMBER);
  }

  public boolean isNextOperator() {
    return isNextKind(Scanner.Kind.OPERATOR);
  }
}
