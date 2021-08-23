package team.unnamed.command.parse;

import org.jetbrains.annotations.Nullable;
import team.unnamed.command.CommandSpec;

import java.util.HashMap;
import java.util.Map;

public class CommandParseInfo {

    private final Map<CommandElement, Object> values = new HashMap<>();
    private CommandSpec command;

    public CommandSpec getCommand() {
        return command;
    }

    public void setCommand(CommandSpec command) {
        this.command = command;
    }

    public void setValue(CommandElement member, Object value) {
        values.put(member, value);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getValue(CommandElement member) {
        return (T) values.get(member);
    }

}
