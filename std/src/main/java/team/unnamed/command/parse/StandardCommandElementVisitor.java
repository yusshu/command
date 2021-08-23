package team.unnamed.command.parse;

/**
 * Standard base interface adding methods to visit
 * standard command element types
 * @param <R> The return type
 * @author yusshu (Andre Roldan)
 */
public interface StandardCommandElementVisitor<R>
    extends CommandElementVisitor<R> {

    /**
     * Visits a sequential command element
     */
    default R visitSequential(SequentialCommandElement element) {
        return visit(element);
    }

    /**
     * Visits a parent command element
     */
    default R visitParent(ParentCommandElement element) {
        return visit(element);
    }

    /**
     * Visits an argument command element
     */
    default R visitArgument(CommandArgument element) {
        return visit(element);
    }

}
