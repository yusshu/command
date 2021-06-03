package team.unnamed.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.unnamed.command.annotated.CommandBuilder;
import team.unnamed.command.annotated.CommandBuilderImpl;
import team.unnamed.command.annotated.CommandClass;
import team.unnamed.command.annotated.annotation.Command;
import team.unnamed.command.annotated.annotation.Infinite;
import team.unnamed.command.annotated.annotation.Name;
import team.unnamed.command.annotated.annotation.Quoted;
import team.unnamed.command.annotated.provider.ElementProvider;
import team.unnamed.command.annotated.provider.factory.StringArgumentFactory;
import team.unnamed.command.parse.CommandElement;
import team.unnamed.command.parse.StringUsageElementVisitor;

public class CommandExecuteTest {

  @Test
  public void test() {
    CommandBuilder builder = new CommandBuilderImpl(
      ElementProvider.createRegistry()
        .register(String.class, new StringArgumentFactory())
    );
    CommandManager manager = new CommandManagerImpl();

    TestCommand command = new TestCommand();
    manager.register(builder.fromClass(command));

    manager.parseAndExecute("test 'arg0 arg0\\' arg0' arg1");
    Assertions.assertTrue(command.main);
    manager.parseAndExecute("test test arg0 arg1 arg1 arg1 arg1 arg1");
    Assertions.assertTrue(command.test);
    manager.parseAndExecute("test foo arg0 arg1 'arg1 arg1 \\'arg1' arg1 'arg1 arg1'");
    Assertions.assertTrue(command.foo);

    Test2Command command2 = new Test2Command();
    CommandElement element = builder.fromClass(command2).get(0).getElement();
    String usage = element.acceptVisitor(new StringUsageElementVisitor());

    Assertions.assertEquals("test2 <a0> <a1>", usage);
  }

  public static class Test2Command
    implements CommandClass {

    @Command(name = "test2")
    public void run(@Name("a0") String arg0, @Name("a1") String arg1) {
    }

  }

  @Command(name = "test")
  public static class TestCommand
    implements CommandClass {

    private boolean main;
    private boolean test;
    private boolean foo;

    @Command(name = "")
    public void runMain(
      @Quoted String parameterA,
      String parameterB
    ) {
      Assertions.assertEquals("arg0 arg0' arg0", parameterA);
      Assertions.assertEquals("arg1", parameterB);
      main = true;
    }

    @Command(name = "test")
    public void runTest(
      String parameterA,
      @Infinite String parameterB
    ) {
      Assertions.assertEquals("arg0", parameterA);
      Assertions.assertEquals("arg1 arg1 arg1 arg1 arg1", parameterB);
      test = true;
    }

    @Command(name = "foo")
    public void runFoo(
      String parameterA,
      @Quoted @Infinite String parameterB
    ) {
      Assertions.assertEquals("arg0", parameterA);
      Assertions.assertEquals("arg1 arg1 arg1 'arg1 arg1 arg1 arg1", parameterB);
      foo = true;
    }

  }

}
