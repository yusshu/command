package team.unnamed.command.parse;

import team.unnamed.command.CommandSpec;
import team.unnamed.command.exception.ParseException;
import team.unnamed.command.stack.ArgumentStack;

import java.util.Map;

/**
 * Represents a command that holds sub-commands
 * and also has parameters
 */
public class ParentCommandElement
    implements CommandElement {

    private final String name;
    private final Map<String, CommandSpec> subCommands;
    private final SequentialCommandElement argumentsElement;

    public ParentCommandElement(
        String name,
        Map<String, CommandSpec> subCommands,
        SequentialCommandElement argumentsElement
    ) {
        this.name = name;
        this.subCommands = subCommands;
        this.argumentsElement = argumentsElement;
    }

    @Override
    public String getLabel() {
        return name;
    }

    public Map<String, CommandSpec> getSubCommands() {
        return subCommands;
    }

    public SequentialCommandElement getArgumentsElement() {
        return argumentsElement;
    }

    @Override
    public void parse(
        CommandParseInfo parseInfo,
        ArgumentStack stack
    ) {
        if (stack.hasNext()) {
            int cursor = stack.getCursor();
            String subCommand = stack.next();
            CommandSpec spec = subCommands.get(subCommand);

            if (spec == null) {
                stack.setCursor(cursor);
                // try as arguments
                argumentsElement.parse(parseInfo, stack);
                return;
            }

            parseInfo.setCommand(spec);
            cursor = stack.getCursor();
            try {
                spec.getElement().parse(parseInfo, stack);
                return;
            } catch (ParseException e) {
                stack.setCursor(cursor);
            }
        }

        argumentsElement.parse(parseInfo, stack);
    }

    @Override
    public <T> T acceptVisitor(CommandElementVisitor<T> visitor) {
        return visitor.visitParent(this);
    }

}
