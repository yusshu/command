package team.unnamed.command.parse.impl;

import team.unnamed.command.exception.InvalidEnumConstantException;
import team.unnamed.command.parse.CommandArgument;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.stack.ArgumentStack;

import java.util.HashMap;
import java.util.Map;

public class EnumCommandArgument
    implements CommandArgument {

    private final Map<String, Object> enumConstants = new HashMap<>();
    private final Class<? extends Enum<?>> enumType;
    private final String name;

    public EnumCommandArgument(Class<? extends Enum<?>> enumType, String name) {
        this.enumType = enumType;
        this.name = name;
        // cache the enum constants by lowercase name
        for (Object constant : enumType.getEnumConstants()) {
            enumConstants.put(
                ((Enum<?>) constant).name().toLowerCase(),
                constant
            );
        }
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public void parse(CommandParseInfo parseInfo, ArgumentStack stack) {
        String arg = stack.next().toLowerCase();
        Object constant = enumConstants.get(arg);
        if (constant == null) {
            throw new InvalidEnumConstantException(
                enumType,
                enumConstants.keySet(),
                arg
            );
        }
        parseInfo.setValue(this, constant);
    }

}
