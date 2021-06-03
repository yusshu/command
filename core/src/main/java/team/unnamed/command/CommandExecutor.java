package team.unnamed.command;

import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.parse.CommandElement;

/**
 * Responsible of executing commands,
 * this is the command logic, this
 * interface is always attached to a
 * command.
 */
@FunctionalInterface
public interface CommandExecutor {

  CommandExecutor NULL = parseInfo -> {};

  /**
   * Executes the command
   * @param parseInfo The parse info, it's supposed
   *                  to be already parsed by all the
   *                  {@link CommandElement} of this
   *                  command
   */
  void execute(CommandParseInfo parseInfo);

}
