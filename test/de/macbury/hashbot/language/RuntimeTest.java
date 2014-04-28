package de.macbury.hashbot.language;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by macbury on 28.04.14.
 */
public class RuntimeTest {
  @Test
  public void testVariableDef() throws Exception {
    Runtime runtime = new Runtime(true);
    runtime.parse("Liczba a");
    runtime.parse("Liczba b = 33");
  }
}
