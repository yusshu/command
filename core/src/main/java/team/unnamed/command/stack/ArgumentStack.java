package team.unnamed.command.stack;

import team.unnamed.command.exception.NoMoreArgumentsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class ArgumentStack {

    private final List<String> arguments;
    private int cursor;

    public ArgumentStack(List<String> arguments) {
        this.arguments = arguments;
    }

    public static ArgumentStack splitBySpace(String line) {
        String[] arguments = line.trim().split(" +");
        return new ArgumentStack(new ArrayList<>(Arrays.asList(arguments)));
    }

    public boolean hasNext() {
        return cursor < arguments.size();
    }

    public String next() {
        if (cursor >= arguments.size()) {
            throw new NoMoreArgumentsException();
        }
        return arguments.get(cursor++);
    }

    public String nextQuoted() {
        if (cursor >= arguments.size()) {
            throw new NoMoreArgumentsException();
        }

        String argument = arguments.get(cursor);
        char quote = argument.charAt(0);

        // normal argument, just return it
        if (quote != '"' && quote != '\'') {
            cursor++;
            return argument;
        }

        // remove the initial quote
        argument = argument.substring(1);
        StringJoiner joiner = new StringJoiner(" ");

        while (true) {
            boolean escaped = false;
            StringBuilder builder = new StringBuilder(argument.length());

            for (int i = 0; i < argument.length(); i++) {
                char current = argument.charAt(i);
                if (current == '\\') {
                    escaped = true;
                    continue;
                }
                if (current == quote) {
                    if (escaped) {
                        builder.append(quote);
                        escaped = false;
                        continue;
                    }

                    cursor++;
                    String extra = argument.substring(i + 1);
                    if (!extra.isEmpty()) {
                        arguments.add(cursor, extra);
                    }
                    joiner.add(builder.toString());
                    return joiner.toString();
                }

                if (escaped) {
                    builder.append('\\');
                    escaped = false;
                }

                builder.append(current);
            }

            if (builder.length() > 0) {
                joiner.add(builder.toString());
            }

            // pass to the next argument
            cursor++;
            if (cursor >= arguments.size()) {
                break;
            } else {
                argument = arguments.get(cursor);
            }
        }

        return joiner.toString();
    }

    public String remove() {
        checkNonZeroCursor();
        return arguments.get(--cursor);
    }

    public String peek() {
        if (cursor >= arguments.size()) {
            throw new NoMoreArgumentsException();
        }
        return arguments.get(cursor);
    }

    public String current() {
        checkNonZeroCursor();
        return arguments.get(cursor - 1);
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        if (cursor < 0 || cursor > arguments.size()) {
            throw new IndexOutOfBoundsException(
                "Cursor cannot be less than zero or" +
                    " greater than the argument count"
            );
        }
        this.cursor = cursor;
    }

    private void checkNonZeroCursor() {
        if (cursor == 0) {
            throw new IllegalStateException("You must advance the" +
                " stack at least one time before calling this method!");
        }
    }

}
