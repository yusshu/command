package team.unnamed.command;

import team.unnamed.command.parse.CommandElement;

import java.util.List;

/**
 * Represents a command, it contains
 * all the necessary data for parsing
 * and executing the command
 */
public class CommandSpec {

  private final String name;
  private final List<String> aliases;
  private final Object description;
  private final String permission;
  private final CommandElement element;
  private final CommandExecutor executor;

  public CommandSpec(
    String name,
    List<String> aliases,
    Object description,
    String permission,
    CommandElement element,
    CommandExecutor executor
  ) {
    this.name = name;
    this.aliases = aliases;
    this.description = description;
    this.permission = permission;
    this.element = element;
    this.executor = executor;
  }

  public String getName() {
    return name;
  }

  public List<String> getAliases() {
    return aliases;
  }

  public Object getDescription() {
    return description;
  }

  public String getPermission() {
    return permission;
  }

  public CommandElement getElement() {
    return element;
  }

  public CommandExecutor getExecutor() {
    return executor;
  }

}
