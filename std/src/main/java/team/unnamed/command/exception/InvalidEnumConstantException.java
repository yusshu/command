package team.unnamed.command.exception;

import java.util.Collection;

/**
 * Exception thrown by the Enum command
 * argument element when a given argument
 * isn't a valid enum constant name
 */
public class InvalidEnumConstantException
    extends ParseException {

    private final Class<? extends Enum<?>> enumType;
    private final Collection<String> validNames;
    private final String argument;

    public InvalidEnumConstantException(
        Class<? extends Enum<?>> enumType,
        Collection<String> validNames,
        String argument
    ) {
        super(
            "argument.invalid-enum",
            "Invalid enum constant, '" + argument
                + "' isn't part of " + enumType
        );
        this.enumType = enumType;
        this.validNames = validNames;
        this.argument = argument;
    }

    public Class<? extends Enum<?>> getEnumType() {
        return enumType;
    }

    public Collection<String> getValidNames() {
        return validNames;
    }

    public String getArgument() {
        return argument;
    }

}
