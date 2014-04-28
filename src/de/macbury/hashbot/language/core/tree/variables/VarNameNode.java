package de.macbury.hashbot.language.core.tree.variables;

import de.macbury.hashbot.language.*;
import de.macbury.hashbot.language.Runtime;
import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.tokenize.Scanner;
import de.macbury.hashbot.language.core.tree.AbstractSyntaxNode;

/**
 * Created by macbury on 28.04.14.
 */
public class VarNameNode extends AbstractSyntaxNode {
  public VarNameNode(Runtime runtime, Scanner.Token token) {
    super(runtime, token);
  }

  @Override
  public void build(TokenList tokens) {

  }
}
