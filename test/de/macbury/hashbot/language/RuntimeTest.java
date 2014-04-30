package de.macbury.hashbot.language;

import de.macbury.hashbot.language.core.errors.SyntaxError;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by macbury on 28.04.14.
 */
public class RuntimeTest {
  private Runtime runtime;

  public RuntimeTest() {
    this.runtime = new Runtime(true);
  }

  public void parseAndRaiseSyntaxError(String source) {
    try {
      runtime.parse(source);
      Assert.fail("Should raise syntax error for:"+source);
    } catch (SyntaxError error) {

    }
  }

  @Test
  public void testVariableDef() throws Exception {
    Runtime runtime = new Runtime(true);
    runtime.parse("Liczba a");
    runtime.parse("Liczba b = 33");
    parseAndRaiseSyntaxError("Liczba b = b33");
    parseAndRaiseSyntaxError("Liczba b = 33+");
    parseAndRaiseSyntaxError("Liczba b = 33b");
  }
}
