package de.macbury.hashbot.language.core.tree;

import de.macbury.hashbot.language.Runtime;
import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.tokenize.Scanner;

/**
 * Created by macbury on 28.04.14.
 */
public class ExpresionNode extends AbstractSyntaxNode {

  public ExpresionNode(Runtime runtime, Scanner.Token token) {
    super(runtime, token);
  }

  @Override
  public void build(TokenList tokens) {
    Scanner.Token nextToken = tokens.next();

  }
}
