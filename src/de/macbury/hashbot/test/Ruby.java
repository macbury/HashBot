package de.macbury.hashbot.test;

import org.jruby.*;
import org.jruby.embed.EmbedEvalUnit;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.exceptions.RaiseException;
import org.jruby.ext.timeout.Timeout;
import org.jruby.ir.runtime.IRRuntimeHelpers;
import org.jruby.javasupport.util.RuntimeHelpers;
import org.jruby.parser.StaticScope;
import org.jruby.runtime.*;
import org.jruby.runtime.builtin.IRubyObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ruby {
  public static ScriptingContainer container;
  public static EmbedEvalUnit parsed = null;
  public static class DummyTest {
    public void test() {
      return;
    }
  }
  public static void main(String[] args) {
    container = new ScriptingContainer(LocalContextScope.THREADSAFE);
    container.setCompatVersion(CompatVersion.RUBY2_0);

    container.setRunRubyInProcess(false);

    org.jruby.Ruby runtime = container.getProvider().getRuntime();

    try {
      parsed = container.parse("return Thread.new {"+readFile("test.rb", StandardCharsets.UTF_8)+"\n}");
    } catch (IOException e) {
      e.printStackTrace();
    }

    JavaInternalBlockBody body = new JavaInternalBlockBody(runtime, Arity.NO_ARGUMENTS) {
      @Override
      public IRubyObject yield(ThreadContext context, IRubyObject value) {
        return null;
      }
    };
    RubyThread mainThread = (RubyThread)parsed.run();
    //RubyThread mainThread = (RubyThread)RubyThread.newInstance(threadClass, null, new Block(body));
    mainThread.abort_on_exception_set(RubyBoolean.createTrueClass(runtime));
    //parsed.run();
    System.out.println("wait for it...");
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      System.out.println("killing");
      //container.runScriptlet("Thread.exit");
      //thread.interrupt();
      ///container.getProvider().getRuntime().tearDown(true);
     // RubyClass anonException = (RubyClass)runtime.getClassFromPath("RuntimeError");
      //IRubyObject anonExceptionObj = anonException.newInstance(runtime.getCurrentContext(), runtime.newString("Killed!"), Block.NULL_BLOCK);
      //runtime.getThreadService().getMainThread().internalRaise(new IRubyObject[]{ anonExceptionObj });
      //runtime.getThreadService().getMainThread().exceptionRaised(new Exception("ss"));
      //for (RubyThread thread : runtime.getThreadService().getActiveRubyThreads()) {
      //  thread.kill();
      //}
      //runtime.getThreadService().getCritical()
      //RubyKernel.raise(runtime.getCurrentContext(), runtime.getKernel(),  new IRubyObject[]{anonExceptionObj}, Block.NULL_BLOCK);
     // System.out.println("killed");*/

      mainThread.kill();
    } catch (Exception e) {
      e.printStackTrace();
    }



    container.terminate();

    System.out.println("Exoited");

    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }



  }

  static String readFile(String path, Charset encoding)
          throws IOException
  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }
}
