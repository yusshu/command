package team.unnamed.command.parse;

/**
 * Visitor for {@link CommandElement} types, visitor
 * interface for using the visitors pattern, extending
 * the {@link CommandElement} structures without directly
 * modifying them.
 *
 * @param <R> The return type
 * @author yusshu (Andre Roldan)
 */
public interface CommandElementVisitor<R> {

    /**
     * Visits a command element, you should use
     * the specific methods instead of this method.
     */
    default R visit(CommandElement element) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
