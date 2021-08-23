package team.unnamed.command;

import team.unnamed.command.exception.CommandException;
import team.unnamed.command.exception.ParseException;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.stack.ArgumentStack;

import java.util.HashMap;
import java.util.Map;

public class CommandManagerImpl
    extends AbstractCommandManager
    implements CommandManager {

    private final Map<String, CommandSpec> commands = new HashMap<>();

    @Override
    public void register(CommandSpec command) {
        if (commands.putIfAbsent(command.getName(), command) != null) {
            throw new IllegalStateException("Command '" + command.getName()
                + "' already registered!");
        }
    }

    @Override
    public CommandParseInfo parse(ArgumentStack stack) throws ParseException {

        String label = stack.next();
        CommandSpec command = commands.get(label.toLowerCase());

        if (command == null) {
            // TODO: label should be handleable by the exception handler
            throw new ParseException(
                "command.unknown",
                "Unknown command: " + label
            );
        }

        CommandParseInfo parseInfo = new CommandParseInfo();
        parseInfo.setCommand(command);
        command.getElement().parse(parseInfo, stack);
        return parseInfo;
    }

    @Override
    public void execute(CommandParseInfo parseInfo) throws CommandException {
        parseInfo.getCommand().getExecutor().execute(parseInfo);
    }

}
