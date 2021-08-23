package team.unnamed.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.unnamed.command.annotated.CommandBuilder;
import team.unnamed.command.annotated.CommandBuilderImpl;
import team.unnamed.command.annotated.CommandClass;
import team.unnamed.command.annotated.annotation.Command;
import team.unnamed.command.annotated.provider.ElementProvider;
import team.unnamed.command.annotated.provider.factory.StringArgumentFactory;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.parse.ParentCommandElement;
import team.unnamed.command.parse.SequentialCommandElement;
import team.unnamed.command.stack.ArgumentStack;

public class CommandParseTest {

    @Test
    public void test() {
        CommandBuilder builder = new CommandBuilderImpl(
            ElementProvider.createRegistry()
                .register(String.class, new StringArgumentFactory())
        );

        CommandSpec command = builder.fromClass(new TestCommand()).get(0);
        ParentCommandElement element = (ParentCommandElement) command.getElement();
        SequentialCommandElement subCommand = (SequentialCommandElement) element.getSubCommands()
            .get("subcommand").getElement();
        SequentialCommandElement subCommand2 = (SequentialCommandElement) element.getSubCommands()
            .get("sub2").getElement();

        CommandParseInfo parseInfo = new CommandParseInfo();
        parseInfo.setCommand(command);
        element.parse(parseInfo, ArgumentStack.splitBySpace("subcommand arg0"));

        Assertions.assertEquals("subcommand", parseInfo.getCommand().getName());
        Assertions.assertEquals("arg0", parseInfo.getValue(subCommand.getElements().get(0)));

        parseInfo = new CommandParseInfo();
        parseInfo.setCommand(command);
        element.parse(parseInfo, ArgumentStack.splitBySpace("sub2 arg1"));

        Assertions.assertEquals("sub2", parseInfo.getCommand().getName());
        Assertions.assertEquals("arg1", parseInfo.getValue(subCommand2.getElements().get(0)));

        parseInfo = new CommandParseInfo();
        parseInfo.setCommand(command);
        element.parse(parseInfo, ArgumentStack.splitBySpace("testttt"));
        Assertions.assertEquals("test", parseInfo.getCommand().getName());
        Assertions.assertEquals("testttt", parseInfo.getValue(element.getArgumentsElement().getElements().get(0)));
    }

    @Command(name = "test")
    public static class TestCommand
        implements CommandClass {

        @Command(name = "")
        public void runTest(String fallback) {
        }

        @Command(name = "subcommand")
        public void runSubCommand(String arg1) {
        }

        @Command(name = "sub2")
        public void runSubCommand2(String arg2) {
        }

    }

}
