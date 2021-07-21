package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    LOGIN("login (\\d+)"),
    ALL_ITEMS("ls -r"),
    ITEMS_IN_STOCK("ls -i"),
    OUT_OF_STOCK("ls -n"),
    ORDER("order (\\d+) -c (\\d+)"),
    CANCEL_ORDER("order -d (\\d+)"),
    LOGOUT("logout"),
    NEW_ORDERS("ls -o"),
    CHECKOUT("checkout (\\d+)"),
    HISTORY("ls -ho"),
    NEW_ITEM("add -n (\\w+) -c (\\d+) -sp (\\d+) -bp (\\d+)"),
    REMOVE("remove -c (\\d+)"),
    EDIT_NAME_COUNT("edit (\\d+) -n (\\w+) -c (\\d+)"),
    EDIT_NAME("edit (\\d+) -n (\\w+)"),
    EDIT_SP_BP_COUNT("edit (\\d+) -sp (\\d+) -bp (\\d+) -c (\\d+)"),
    CALCULATE_PROFIT("calc -p"),
    CALCULATE_ITEM_PROFIT("calc -p -c (\\d+)"),
    SALES("calc -s"),
    ITEM_SALES("calc -s -c (\\d+)"),
    INVALID("");

    private final Pattern pattern;
    private final String regex;

    Command(String regex) {
        pattern = Pattern.compile(regex);
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Command command) {
        return command.pattern.matcher(input);
    }

    public static Command findCommand(String input) {
        for (Command command : Command.values())
            if (Pattern.matches(command.regex, input))
                return command;
        return INVALID;
    }
}
