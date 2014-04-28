package de.macbury.hashbot.language.core.tree.variables;

import de.macbury.hashbot.language.Runtime;
import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.tokenize.Scanner;
import de.macbury.hashbot.language.core.tree.AbstractSyntaxNode;

/**
 * Created by macbury on 28.04.14.
 */
public class AssignNode extends AbstractSyntaxNode {
  public AssignNode(Runtime runtime, Scanner.Token token) {
    super(runtime, token);
  }

  @Override
  public void build(TokenList tokens) {
    Scanner.Token nextToken = tokens.next();
    if (nextToken.isEqualSymbol()) {
      while(tokens.isNextNumber() || tokens.isNextOperator()) {
        nextToken = tokens.next();
      }
    } else {
      runtime.throwSyntaxError(nextToken);
    }
  }
}
