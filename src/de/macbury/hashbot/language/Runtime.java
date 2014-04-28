package de.macbury.hashbot.language;

import de.macbury.hashbot.language.core.TokenList;
import de.macbury.hashbot.language.core.errors.SyntaxError;
import de.macbury.hashbot.language.core.tokenize.Scanner;
import de.macbury.hashbot.language.core.tree.AbstractSyntaxNode;
import de.macbury.hashbot.language.core.tree.MasterNode;
import de.macbury.hashbot.language.core.tree.primitives.BasePrimitive;
import de.macbury.hashbot.language.core.tree.primitives.NumberPrimitive;
import org.yaml.snakeyaml.util.ArrayStack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by macbury on 26.04.14.
 */
public class Runtime {
  private boolean debug;
  private String source;
  private Scanner scanner;
  private TokenList tokens;
  private MasterNode masterNode;
  private AbstractSyntaxNode currentNode;
  private HashMap<String, Class<? extends BasePrimitive>> primitiveMap;

  public Runtime(boolean debug) {
    this.debug  = debug;
    this.tokens = new TokenList();

    primitiveMap = new HashMap<String, Class<? extends BasePrimitive>>();
    primitiveMap.put("Liczba", NumberPrimitive.class);
  }

  public void parse(String text) {
    this.source     = text.trim();
    this.tokens.clear();
    this.masterNode  = new MasterNode(this);
    this.currentNode = masterNode;
    tokenize();

    currentNode.build(tokens);
  }

  public void run() {
    while(tick()) {}
  }

  private boolean tick() {
    return false;
  }

  private void tokenize() {
    this.scanner    = new Scanner(source);

    while (true) {
      Scanner.Token token = scanner.scan();
      if (token.kind == Scanner.Kind.EOF)
        break;
      tokens.add(token);
    }
  }

  private void debug(String message) {
    if (this.debug) {
      System.out.println(message);
    }
  }

  public boolean isPrimitiveDef(Scanner.Token token) {
    return primitiveMap.containsKey(token.content);
  }

  public Class<? extends BasePrimitive> getPrimitiveFor(Scanner.Token token) {
    if (isPrimitiveDef(token)) {
      return primitiveMap.get(token.content);
    } else {
      return null;
    }
  }

  public boolean isNotKeywordOrFunction(Scanner.Token token) {
    return !isPrimitiveDef(token);
  }

  public void throwSyntaxError(Scanner.Token token) {
    throw new SyntaxError(token);
  }

}
