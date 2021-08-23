package team.unnamed.command.annotated;

import team.unnamed.command.CommandSpec;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Responsible of converting {@link CommandClass}
 * instances and {@link Method}s to core-understandable
 * {@link CommandSpec} instances
 */
public interface CommandBuilder {

    /**
     * Converts the given {@code commandClass} to
     * a list of {@link CommandSpec}.
     * <p>
     * Depending on command class structure, it
     * can return a singleton list with the root
     * command specification, or a list containing
     * all its methods as commands
     */
    List<CommandSpec> fromClass(CommandClass commandClass);

    /**
     * Converts the given {@code method} to a
     * {@link CommandSpec}
     */
    CommandSpec fromMethod(Method method);

}
