package de.macbury.hashbot.language.core.tokenize;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by macbury on 28.04.14.
 */
public class TokenTest {
  @Test
  public void testIsEqualSymbol() throws Exception {
    Scanner.Token token = new Scanner.Token("=", Scanner.Kind.OPERATOR, 0,0);
    Assert.assertEquals(token.isEqualSymbol(), true);
    token = new Scanner.Token("+", Scanner.Kind.OPERATOR, 0,0);
    Assert.assertEquals(token.isEqualSymbol(), false);
  }
}
