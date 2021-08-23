package team.unnamed.command;

/**
 * Responsible of checking if the environment
 * where the command has been executed is authorized
 * to use it.
 */
public interface CommandAuthorizer {

    /**
     * No-op implementation
     */
    CommandAuthorizer NULL = (namespace, permission) -> true;

    /**
     * Returns true if the given {@code namespace}
     * contains information that can authorize the
     * command execution. Permission is represented
     * by a string
     */
    boolean isAuthorized(Namespace namespace, String permission);

}
