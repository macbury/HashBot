package de.macbury.hashbot.language.core.tree;

import de.macbury.hashbot.language.*;
import de.macbury.hashbot.language.Runtime;
import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.tokenize.Scanner;
import sun.net.www.content.audio.wav;

import java.util.ArrayList;

/**
 * Created by macbury on 27.04.14.
 */
public abstract class AbstractSyntaxNode {
  protected Scanner.Token token;
  protected Runtime runtime;
  protected ArrayList<AbstractSyntaxNode> childs;
  protected AbstractSyntaxNode parent;

  public AbstractSyntaxNode(Runtime runtime, Scanner.Token token) {
    this.runtime = runtime;
    this.token   = token;
    this.childs  = new ArrayList<AbstractSyntaxNode>();
  }

  public ArrayList<AbstractSyntaxNode> getChilds() {
    return childs;
  }

  public void add(AbstractSyntaxNode node) {
    node.setParent(this);
    childs.add(node);
  }

  public AbstractSyntaxNode getParent() {
    return parent;
  }

  public void setParent(AbstractSyntaxNode parent) {
    this.parent = parent;
  }

  public abstract void build(TokenList tokens);
}
