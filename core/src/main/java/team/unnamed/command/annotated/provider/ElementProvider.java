package team.unnamed.command.annotated.provider;

import org.jetbrains.annotations.Nullable;
import team.unnamed.command.annotated.annotation.Name;
import team.unnamed.command.parse.CommandElement;

import java.lang.reflect.Parameter;

/**
 * In order to parse {@link CommandElement} from
 * Java methods, we need to create command members
 * from Java parameter information, this class
 * is responsible of doing that.
 */
public interface ElementProvider {

  /**
   * Returns the {@link CommandElement} for the
   * given {@code parameter} or null if not found
   */
  @Nullable
  CommandElement getMember(Parameter parameter);

  /**
   * Checks if the given {@code parameter} is annotated
   * with {@link Name}. If not, returns the name returned
   * by {@link Parameter#getName()}, if annotation is present,
   * it returns {@link Name#value()}
   */
  static String getName(Parameter parameter) {
    Name name = parameter.getAnnotation(Name.class);
    return name == null ? parameter.getName() : name.value();
  }

  /**
   * Creates a new {@link ElementProvider} instance
   * that works as a registry. Fluent-api
   */
  static DefaultElementProvider createRegistry() {
    return new DefaultElementProvider();
  }

}
