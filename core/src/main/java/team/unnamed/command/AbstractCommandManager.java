package team.unnamed.command;

import team.unnamed.command.exception.CommandException;
import team.unnamed.command.exception.ParseException;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.stack.ArgumentStack;

import java.util.List;

/**
 * Abstract class implementing common
 * {@link CommandManager} method behavior
 */
public abstract class AbstractCommandManager
  implements CommandManager {

  private CommandAuthorizer authorizer = CommandAuthorizer.NULL;

  @Override
  public CommandAuthorizer getAuthorizer() {
    return authorizer;
  }

  @Override
  public void setAuthorizer(CommandAuthorizer authorizer) {
    this.authorizer = authorizer;
  }

  @Override
  public void register(List<CommandSpec> commands) {
    for (CommandSpec command : commands) {
      register(command);
    }
  }

  @Override
  public void parseAndExecute(String commandLine) throws CommandException {
    execute(parse(commandLine));
  }

  @Override
  public void parseAndExecute(ArgumentStack stack) throws CommandException {
    execute(parse(stack));
  }

  @Override
  public CommandParseInfo parse(String commandLine) throws ParseException {
    return parse(ArgumentStack.splitBySpace(commandLine));
  }

}
