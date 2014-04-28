package de.macbury.hashbot.language.core.tree;

import de.macbury.hashbot.language.Runtime;
import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.tokenize.Scanner;
import de.macbury.hashbot.language.core.tree.variables.DefineVarNode;

/**
 * Created by macbury on 27.04.14.
 */
public class MasterNode extends AbstractSyntaxNode {

  public MasterNode(Runtime runtime) {
    super(runtime, null);
  }

  @Override
  public void build(TokenList tokens) {
    while(true) {
      Scanner.Token token = tokens.next();
      if (token == null)
        break;

      if (token.isNormal()) {
        processNormal(token, tokens);
      } else {
        runtime.throwSyntaxError(token);
      }

    }
  }

  private void processNormal(Scanner.Token token, TokenList tokens) {
    if (runtime.isPrimitiveDef(token)) {
      DefineVarNode node = new DefineVarNode(runtime, token);
      add(node);
      node.build(tokens);
    } else {
      runtime.throwSyntaxError(token);
    }
  }

}
