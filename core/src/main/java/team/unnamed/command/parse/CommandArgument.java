package team.unnamed.command.parse;

import team.unnamed.command.stack.ArgumentStack;

import java.util.List;

/**
 * Represents a command argument element,
 * these types of arguments are supposed to
 * consume the {@link ArgumentStack} in order
 * to provide the argument values.
 */
public interface CommandArgument
  extends CommandElement {

  @Override
  default void suggest(
    CommandParseInfo parseInfo,
    ArgumentStack stack,
    List<String> suggestions
  ) {
    // just consume
    if (stack.hasNext()) {
      stack.next();
    }
  }

  @Override
  default <T> T acceptVisitor(CommandElementVisitor<T> visitor) {
    return visitor.visitArgument(this);
  }

}
