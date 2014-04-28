package de.macbury.hashbot.language.core.tree.variables;

import de.macbury.hashbot.language.Runtime;
import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.tokenize.Scanner;
import de.macbury.hashbot.language.core.tree.AbstractSyntaxNode;
import de.macbury.hashbot.language.core.tree.primitives.BasePrimitive;

/**
 * Created by macbury on 28.04.14.
 */
public class DefineVarNode extends AbstractSyntaxNode {
  private Class<? extends BasePrimitive> primitiveType;

  public DefineVarNode(Runtime runtime, Scanner.Token token) {
    super(runtime, token);
    this.primitiveType = runtime.getPrimitiveFor(token);
  }


  @Override
  public void build(TokenList tokens) {
    Scanner.Token nextToken = tokens.next();
    if (nextToken != null && nextToken.isNormal() && runtime.isNotKeywordOrFunction(nextToken)) {
      VarNameNode varNameNode = new VarNameNode(runtime, nextToken);
      add(varNameNode);
      if (!tokens.isNextEoFOrNewLine()) {
        AssignNode assignNode = new AssignNode(runtime, tokens.peek());
        add(assignNode);
        assignNode.build(tokens);
      }
    } else {
      runtime.throwSyntaxError(nextToken);
    }
  }
}
