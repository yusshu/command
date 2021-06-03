package team.unnamed.command.parse;

/**
 * Visitor for {@link CommandElement} types, visitor
 * interface for using the visitors pattern, extending
 * the {@link CommandElement} structures without directly
 * modifying them.
 * @param <R> The return type
 */
public interface CommandElementVisitor<R> {

  /** Visits a sequential command element */
  default R visitSequential(SequentialCommandElement element) {
    return visit(element);
  }

  /** Visits a parent command element */
  default R visitParent(ParentCommandElement element) {
    return visit(element);
  }

  /** Visits an argument command element */
  default R visitArgument(CommandArgument element) {
    return visit(element);
  }

  /**
   * Visits a command element, you should use
   * the specific methods instead of this method.
   */
  default R visit(CommandElement element) {
    throw new UnsupportedOperationException("Not implemented");
  }

}
