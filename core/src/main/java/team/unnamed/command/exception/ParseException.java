package team.unnamed.command.exception;

/**
 * It's a {@link CommandException} that can
 * be thrown when parsing a command
 */
public class ParseException
    extends CommandException {

    public ParseException(
        String messageId,
        String message
    ) {
        super(messageId, message);
    }

    public ParseException() {
    }

}
