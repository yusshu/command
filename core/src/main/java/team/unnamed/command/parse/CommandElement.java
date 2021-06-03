package team.unnamed.command.parse;

import team.unnamed.command.stack.ArgumentStack;

import java.util.List;

/**
 * Represents a command parsing element, most
 * times, it's supposed to fill the command
 * parsing information object ({@link CommandParseInfo}).
 *
 * Most implementations consume arguments in order
 * to give the command argument values
 */
public interface CommandElement {

  /** Returns the name of this element */
  String getLabel();

  /**
   * This method is supposed to interact
   * with the {@code parseInfo} object, giving
   * parsed values; in order to do this,
   * it can consume the argument {@code stack}
   */
  void parse(
    CommandParseInfo parseInfo,
    ArgumentStack stack
  );

  /**
   * Adds valid argument suggestions for the
   * {@link CommandElement#parse} method (if it
   * consumes the argument stack) to the given
   * {@code suggestions} list
   */
  default void suggest(
    CommandParseInfo parseInfo,
    ArgumentStack stack,
    List<String> suggestions
  ) {
    // no-op by default
  }

  /**
   * Accepts an element visitor by calling the visitor
   * method specific to this element type
   */
  <T> T acceptVisitor(CommandElementVisitor<T> visitor);

}
