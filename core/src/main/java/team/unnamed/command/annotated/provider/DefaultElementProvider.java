package team.unnamed.command.annotated.provider;

import org.jetbrains.annotations.Nullable;
import team.unnamed.command.parse.CommandElement;
import team.unnamed.command.parse.impl.EnumCommandArgument;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link ElementProvider},
 * works as a registry, delegating the {@link CommandElement}
 * instantiation behaviour to registered {@link CommandElementFactory}
 */
public class DefaultElementProvider
    implements ElementProvider {

    private final Map<Type, CommandElementFactory> factories =
        new HashMap<>();

    public DefaultElementProvider register(Type type, CommandElementFactory factory) {
        if (factories.putIfAbsent(type, factory) != null) {
            throw new IllegalStateException("A factory is already " +
                "registered for type " + type);
        }
        return this;
    }

    @Override
    @Nullable
    public CommandElement getMember(Parameter parameter) {

        Class<?> rawType = parameter.getType();
        Type genericType = parameter.getParameterizedType();
        String name = ElementProvider.getName(parameter);

        CommandElementFactory factory = factories.get(genericType);

        if (factory != null) {
            return factory.create(name, parameter);
        } else if (rawType.isEnum()) {
            @SuppressWarnings("unchecked") // it's safe
            Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) rawType;
            return new EnumCommandArgument(enumType, name);
        }

        return null;
    }

}
