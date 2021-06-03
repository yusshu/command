package team.unnamed.command.exception;

import org.jetbrains.annotations.Nullable;

/**
 * The super-exception for all the exceptions
 * in the library, can be thrown when parsing
 * or executing a command
 */
public class CommandException extends RuntimeException {

  private final String messageId;

  public CommandException(
    String messageId,
    String message
  ) {
    super(message);
    this.messageId = messageId;
  }

  public CommandException() {
    this.messageId = null;
  }

  @Nullable
  public String getMessageId() {
    return messageId;
  }

}
