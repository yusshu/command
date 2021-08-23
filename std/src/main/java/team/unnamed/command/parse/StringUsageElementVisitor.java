package team.unnamed.command.parse;

import java.util.StringJoiner;

public class StringUsageElementVisitor
    implements StandardCommandElementVisitor<String> {

    @Override
    public String visitSequential(SequentialCommandElement element) {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(element.getLabel());
        for (CommandElement child : element.getElements()) {
            joiner.add(child.acceptVisitor(this));
        }
        return joiner.toString().trim();
    }

    @Override
    public String visitParent(ParentCommandElement element) {
        return element.getLabel() + " <"
            + String.join("|", element.getSubCommands().keySet())
            + "|<" + element.getArgumentsElement().acceptVisitor(this) + '>';
    }

    @Override
    public String visitArgument(CommandArgument element) {
        return '<' + element.getLabel() + '>';
    }

    @Override
    public String visit(CommandElement element) {
        return element.getLabel();
    }

}
