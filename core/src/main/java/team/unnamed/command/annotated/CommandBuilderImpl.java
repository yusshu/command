package team.unnamed.command.annotated;

import team.unnamed.command.CommandExecutor;
import team.unnamed.command.CommandSpec;
import team.unnamed.command.annotated.annotation.Command;
import team.unnamed.command.annotated.provider.ElementProvider;
import team.unnamed.command.parse.CommandElement;
import team.unnamed.command.parse.ParentCommandElement;
import team.unnamed.command.parse.SequentialCommandElement;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandBuilderImpl implements CommandBuilder {

    private final ElementProvider elementProvider;

    public CommandBuilderImpl(ElementProvider elementProvider) {
        this.elementProvider = elementProvider;
    }

    @Override
    public List<CommandSpec> fromClass(CommandClass commandClass) {
        Class<?> clazz = commandClass.getClass();
        Command classAnnotation = clazz.getAnnotation(Command.class);

        Method rootCommandMethod = null;
        List<CommandSpec> result = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            Command annotation = method.getAnnotation(Command.class);
            if (annotation != null) {
                if (annotation.name().isEmpty()) {
                    if (rootCommandMethod != null) {
                        throw new IllegalStateException(
                            "Both '" + rootCommandMethod.getName() + "' and '"
                                + method.getName() + "' methods have an empty name! "
                                + "Cannot use multiple root command methods"
                        );
                    }
                    rootCommandMethod = method;
                } else if (
                    !Modifier.isStatic(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers())
                        && (method.getReturnType() == void.class
                        || method.getReturnType() == boolean.class
                        || method.getReturnType() == Boolean.class)
                ) {

                    List<CommandElement> parameterMembers = new ArrayList<>();

                    for (Parameter parameter : method.getParameters()) {
                        parameterMembers.add(elementProvider.getMember(parameter));
                    }

                    result.add(new CommandSpec(
                        annotation.name(),
                        Arrays.asList(annotation.aliases()),
                        annotation.desc(),
                        annotation.permission(),
                        new SequentialCommandElement(annotation.name(), parameterMembers),
                        new ReflectiveCommandExecutor(
                            method,
                            commandClass,
                            parameterMembers
                        )
                    ));
                }
            }
        }

        if (classAnnotation != null) {

            Map<String, CommandSpec> subCommands = new HashMap<>();

            for (CommandSpec subCommand : result) {
                subCommands.put(subCommand.getName(), subCommand);
                for (String alias : subCommand.getAliases()) {
                    subCommands.put(alias, subCommand);
                }
            }

            List<CommandElement> rootCommandParameterMembers = new ArrayList<>();
            if (rootCommandMethod != null) {
                for (Parameter parameter : rootCommandMethod.getParameters()) {
                    CommandElement member = elementProvider.getMember(parameter);
                    rootCommandParameterMembers.add(member);
                }
            }

            return Collections.singletonList(new CommandSpec(
                classAnnotation.name(),
                Arrays.asList(classAnnotation.aliases()),
                classAnnotation.desc(),
                classAnnotation.permission(),
                new ParentCommandElement(
                    classAnnotation.name(),
                    subCommands,
                    new SequentialCommandElement("", rootCommandParameterMembers)
                ),
                rootCommandMethod == null
                    ? CommandExecutor.NULL
                    : new ReflectiveCommandExecutor(rootCommandMethod, commandClass, rootCommandParameterMembers)
            ));
        } else {
            return result;
        }
    }

    @Override
    public CommandSpec fromMethod(Method method) {
        // TODO: This
        return null;
    }

}
