package de.macbury.hashbot.language.core.tokenize;

/**
 * Created by macbury on 27.04.14.
 */
public class Scanner {

  public enum Kind {
    EOF,
    WHITESPACE,
    NEWLINE,
    NORMAL,
    KEYWORD,
    STRING,
    COMMENT,
    NUMBER,
    OPEN_CLAMP,
    CLOSE_CLAMP,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPERATOR
  }

  private final CharacterIterator iterator;
  private Token currentToken;
  private int line = 1;
  private int column = 0;
  private final char[] operators;

  public Scanner(CharSequence cs) {
    this.iterator  = new CharacterIterator(cs);
    this.operators = new char[] { '+', '-', '/', '*', '=' };
  }

  public Token scan() {
    iterator.clear();
    int ch = iterator.next();
    moveCursorBy(ch);
    switch(ch) {
      case CharacterIterator.EOF:
        return token(Kind.EOF);
      case '\n':
        return token(Kind.NEWLINE);
      case '\"':
      case '\'':
        return scanString(ch);
      case ' ':
        return scan();
      case '{':
        return token(Kind.OPEN_CLAMP);
      case '}':
        return token(Kind.CLOSE_CLAMP);
      case '(':
        return token(Kind.OPEN_BRACKET);
      case ')':
        return token(Kind.CLOSE_BRACKET);
      case '#':
        //iterator.advanceToEOL();
        //return token(Kind.COMMENT);
        return scan();
      default:
        for (int i = 0; i < operators.length; i++) {
          if (operators[i] == ch) {
            return token(Kind.OPERATOR);
          }
        }
        if (Character.isDigit(ch)) {
          return scanNumber(ch);
        } else {
          return scanNormal(ch);
        }
    }
  }

  private Token scanNumber(int ch) {
    while(true) {
      int nch = iterator.peek();
      if (Character.isDigit(nch) || nch == '.'){
        moveCursorBy(nch);
        iterator.next();
      } else {
        break;
      }
    }
    return token(Kind.NUMBER);
  }

  private void moveCursorBy(int ch) {
    column++;
    if (ch == '\n') {
      column = 0;
      line++;
    }
  }


  private Token scanString(int endMarker) {
    for(;;) {
      int ch = iterator.next();
      moveCursorBy(ch);
      if(ch == '\\') {
        iterator.next();
      } else if(ch == '\n') {
        iterator.pushback();
        break;
      } else if(ch == endMarker || ch == '\n' || ch == '\r' || ch < 0) {
        break;
      }
    }

    return token(Kind.STRING);
  }

  private Token scanNormal(int ch) {
    while(true) {
      int nch = iterator.peek();

      if (Character.isAlphabetic(nch) || nch == '_'){
        iterator.next();
        moveCursorBy(nch);
      } else {
        break;
      }
    }
    return token(Kind.NORMAL);
  }


  private Token token(Kind kind) {
    return new Token(iterator.getString(), kind, line, column);
  }

  public static class Token {
    public String content;
    public Kind kind;
    public int line;
    public int column;

    public Token(String content, Kind kind, int line, int column) {
      this.content = content.trim();
      this.kind    = kind;
      this.line    = line;
      this.column  = column;
    }

    @Override
    public String toString() {
      return "Token("+column+"x"+line+") "+kind.toString() + " = " + content;
    }

    public boolean isComment() {
      return kind == Kind.COMMENT;
    }

    public boolean isNormal() {
      return kind == Kind.NORMAL;
    }

    public boolean isEqualSymbol() {
      return kind == Kind.OPERATOR && content.equals("=");
    }
  }
}
