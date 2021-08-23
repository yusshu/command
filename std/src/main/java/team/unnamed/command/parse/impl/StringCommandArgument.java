package team.unnamed.command.parse.impl;

import team.unnamed.command.parse.CommandArgument;
import team.unnamed.command.parse.CommandParseInfo;
import team.unnamed.command.stack.ArgumentStack;

import java.util.StringJoiner;

public class StringCommandArgument
    implements CommandArgument {

    private final String name;
    private final boolean quoted;
    private final boolean infinite;
    private final String join;

    public StringCommandArgument(
        String name,
        boolean quoted,
        boolean infinite,
        String join
    ) {
        this.name = name;
        this.quoted = quoted;
        this.infinite = infinite;
        this.join = join;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public void parse(CommandParseInfo parseInfo, ArgumentStack stack) {
        String value;
        if (quoted) {
            if (infinite) {
                StringJoiner joiner = new StringJoiner(join);
                while (stack.hasNext()) {
                    joiner.add(stack.nextQuoted());
                }
                value = joiner.toString();
            } else {
                value = stack.nextQuoted();
            }
        } else if (infinite) {
            StringJoiner joiner = new StringJoiner(join);
            while (stack.hasNext()) {
                joiner.add(stack.next());
            }
            value = joiner.toString();
        } else {
            value = stack.next();
        }
        parseInfo.setValue(this, value);
    }

}
