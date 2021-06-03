package team.unnamed.command.parse;

import org.jetbrains.annotations.Nullable;
import team.unnamed.command.stack.ArgumentStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@link CommandElement} compound
 * by other elements, it is commonly used to represent
 * a list of command arguments (a command method)
 */
public class SequentialCommandElement
  implements CommandElement {

  private final String label;
  private final List<CommandElement> elements;

  public SequentialCommandElement(
     String label,
    List<CommandElement> elements
  ) {
    this.label = label;
    this.elements = elements;
  }

  @Override
  public String getLabel() {
    return label;
  }

  public List<CommandElement> getElements() {
    return elements;
  }

  /**
   * {@inheritDoc}
   *
   * This implementation delegates all the
   * functionality to its {@code elements} by
   * calling them sequentially
   */
  @Override
  public void parse(
    CommandParseInfo parseInfo,
    ArgumentStack stack
  ) {
    for (CommandElement member : elements) {
      member.parse(parseInfo, stack);
    }
  }

  /**
   * {@inheritDoc}
   *
   * This implementation calls all the
   * {@code elements} to get its suggestions
   * and returns when the {@code stack}
   * has no more elements
   */
  @Override
  public void suggest(
    CommandParseInfo parseInfo,
    ArgumentStack stack,
    List<String> suggestions
  ) {
    List<String> lastSuggestions = new ArrayList<>();
    for (CommandElement member : elements) {
      member.suggest(parseInfo, stack, lastSuggestions);
      if (!lastSuggestions.isEmpty() && !stack.hasNext()) {
        suggestions.addAll(lastSuggestions);
        break;
      }
      lastSuggestions.clear();
    }
  }

  @Override
  public <T> T acceptVisitor(CommandElementVisitor<T> visitor) {
    return visitor.visitSequential(this);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Sequential {");
    for (int i = 0; i < elements.size(); i++) {
      if (i != 0) {
        builder.append(", ");
      }
      builder.append(elements.get(i));
    }
    return builder.append('}').toString();
  }

}
