package team.unnamed.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.unnamed.command.annotated.CommandBuilder;
import team.unnamed.command.annotated.CommandBuilderImpl;
import team.unnamed.command.annotated.CommandClass;
import team.unnamed.command.annotated.annotation.Command;
import team.unnamed.command.annotated.annotation.Name;
import team.unnamed.command.annotated.provider.ElementProvider;
import team.unnamed.command.annotated.provider.factory.StringArgumentFactory;
import team.unnamed.command.parse.CommandElement;
import team.unnamed.command.parse.ParentCommandElement;
import team.unnamed.command.parse.SequentialCommandElement;
import team.unnamed.command.parse.impl.StringCommandArgument;

import java.util.List;

public class CommandBuildTest {

    @Test
    public void test() {

        CommandBuilder builder = new CommandBuilderImpl(
            ElementProvider.createRegistry()
                .register(String.class, new StringArgumentFactory())
        );

        CommandSpec command = builder.fromClass(new TestCommand()).get(0);
        Assertions.assertEquals("test", command.getName());

        CommandElement element = command.getElement();
        Assertions.assertTrue(element instanceof ParentCommandElement);

        ParentCommandElement parentElement = (ParentCommandElement) element;
        Assertions.assertEquals(1, parentElement.getSubCommands().size());

        // root command
        SequentialCommandElement argumentsElement = parentElement.getArgumentsElement();
        Assertions.assertNotNull(argumentsElement);
        Assertions.assertEquals(1, argumentsElement.getElements().size());
        CommandElement rootCommandArgument = argumentsElement.getElements().get(0);
        Assertions.assertEquals("parameter", rootCommandArgument.getLabel());
        Assertions.assertTrue(rootCommandArgument instanceof StringCommandArgument);

        // sub command
        CommandSpec subCommand = parentElement.getSubCommands().get("subcommand");
        Assertions.assertNotNull(subCommand);
        CommandElement subCommandElement = subCommand.getElement();
        Assertions.assertTrue(subCommandElement instanceof SequentialCommandElement);
        SequentialCommandElement sequentialSubCommand = (SequentialCommandElement) subCommandElement;
        Assertions.assertEquals(1, sequentialSubCommand.getElements().size());
        Assertions.assertTrue(sequentialSubCommand.getElements().get(0) instanceof StringCommandArgument);
    }

    @Test
    public void testNoRoot() {
        CommandBuilder builder = new CommandBuilderImpl(
            ElementProvider.createRegistry()
        );

        List<CommandSpec> commands = builder.fromClass(new TestCommandNoRoot());
        Assertions.assertEquals(2, commands.size());

        CommandSpec testCommand = commands.get(0);
        Assertions.assertEquals("test", testCommand.getName());
        Assertions.assertTrue(testCommand.getElement() instanceof SequentialCommandElement);

        CommandSpec barCommand = commands.get(1);
        Assertions.assertEquals("bar", barCommand.getName());
        Assertions.assertTrue(barCommand.getElement() instanceof SequentialCommandElement);
    }

    @Command(name = "test")
    public static class TestCommand
        implements CommandClass {

        @Command(name = "")
        public void runMain(@Name("parameter") String parameter) {
        }

        @Command(name = "subcommand")
        public void runSubCommand(String parameter) {
        }

    }

    public static class TestCommandNoRoot
        implements CommandClass {

        @Command(name = "test")
        public void runTest() {
        }

        @Command(name = "bar")
        public void runBar() {
        }

    }

}
