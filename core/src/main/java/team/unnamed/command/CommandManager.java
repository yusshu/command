package team.unnamed.command;

import team.unnamed.command.exception.CommandException;
import team.unnamed.command.exception.ParseException;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.stack.ArgumentStack;

import java.util.List;

/**
 * Manages command register, command execution and
 * argument suggestions (used on tab-completing)
 */
public interface CommandManager {

  /** Registers the given {@code command} */
  void register(CommandSpec command);

  /** Registers the given {@code commands} */
  void register(List<CommandSpec> commands);

  /**
   * Parses and executes the given {@code commandLine}
   * This method internally calls {@link CommandManager#parseAndExecute(ArgumentStack)}
   * using the default splitting method
   * @throws CommandException If an error occurs while parsing
   * or executing the command
   */
  void parseAndExecute(String commandLine) throws CommandException;

  /**
   * Parses and executes the given {@code stack}
   * @throws CommandException If an error occurs while
   * parsing or executing the command
   */
  void parseAndExecute(ArgumentStack stack) throws CommandException;

  /**
   * Parses the given {@code commandLine}
   * This method internally calls {@link CommandManager#parse(ArgumentStack)}
   * using the default splitting method
   * @throws ParseException If an error occurs while
   * parsing the command line
   */
  CommandParseInfo parse(String commandLine) throws ParseException;

  /**
   * Parses the given {@code commandLine}
   * @throws ParseException If an error occurs while
   * parsing the command line
   */
  CommandParseInfo parse(ArgumentStack stack) throws ParseException;

  /**
   * Given the {@code parseInfo}, executes it
   * @param parseInfo The parse info, returned by
   *                  {@link CommandManager#parse}
   * @throws CommandException If an error occurs while executing
   * the command
   */
  void execute(CommandParseInfo parseInfo) throws CommandException;

  /** Returns the authorizer for this command manager */
  CommandAuthorizer getAuthorizer();

  /** Sets the authorizer for this command manager */
  void setAuthorizer(CommandAuthorizer authorizer);

}
