package team.unnamed.command.annotated;

import team.unnamed.command.CommandExecutor;
import team.unnamed.command.exception.CommandException;
import team.unnamed.command.parse.CommandElement;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.CommandSpec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Implementation of {@link CommandExecutor} that
 * uses reflection and the data returned by the
 * reflective command builder to build {@link CommandSpec}
 * from {@link CommandClass} and methods.
 */
public class ReflectiveCommandExecutor
  implements CommandExecutor {

  private final Method method;
  private final CommandClass commandClass;
  private final List<CommandElement> argumentElements;

  public ReflectiveCommandExecutor(
    Method method,
    CommandClass commandClass,
    List<CommandElement> argumentElements
  ) {
    this.method = method;
    this.commandClass = commandClass;
    this.argumentElements = argumentElements;
  }

  @Override
  public void execute(CommandParseInfo parseInfo) {

    Object[] values = new Object[argumentElements.size()];
    for (int i = 0; i < argumentElements.size(); i++) {
      values[i] = parseInfo.getValue(argumentElements.get(i));
    }

    boolean accessible = method.isAccessible();
    method.setAccessible(true);

    try {
      method.invoke(commandClass, values);
    } catch (IllegalAccessException | InvocationTargetException e) {
      Throwable error = e.getCause();
      if (e.getCause() != null) {
        error = e.getCause();
      }

      if (error instanceof CommandException) {
        throw (CommandException) error;
      }
    } finally {
      method.setAccessible(accessible);
    }
  }

}
