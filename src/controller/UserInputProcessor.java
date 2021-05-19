package controller;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class UserInputProcessor {
    Scanner scanner = new Scanner(System.in);
    String input;

    public void run(){
        boolean exit = false;
        while (!exit) {
            System.out.print("Enter command: ");
            exit = runCommand(scanner.nextLine());
        }
    }

    private boolean runCommand(String input) {
        if (input.equals("exit")) {
            return true;
        }

        switch (Command.findCommand(input)) {
            case LOGIN: login(Command.getMatcher(input, Command.LOGIN));
                break;
            case ALL_ITEMS: allItems(Command.getMatcher(input, Command.ALL_ITEMS));
                break;
            case ITEMS_IN_STOCK: itemsInStock(Command.getMatcher(input, Command.ITEMS_IN_STOCK));
                break;
            case OUT_OF_STOCK: outOfStock(Command.getMatcher(input, Command.OUT_OF_STOCK));
                break;
            case ORDER: order(Command.getMatcher(input, Command.ORDER));
                break;
            case CANCEL_ORDER: cancelOrder(Command.getMatcher(input, Command.CANCEL_ORDER));
                break;
            case LOGOUT: logout(Command.getMatcher(input, Command.LOGOUT));
                break;
            default: System.out.println("Command does not exist.");
        }
        return false;
    }

    private void login(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            if (Customer.findCustomer(ID) == null) {
                new Customer(ID);
                System.out.println("New customer - Welcome.");
            }
            else System.out.println("Welcome back.");
        }
    }

    private void allItems(Matcher matcher) {
    }

    private void itemsInStock(Matcher matcher) {
    }

    private void outOfStock(Matcher matcher) {
    }

    private void order(Matcher matcher) {
    }

    private void cancelOrder(Matcher matcher) {
    }

    private void logout(Matcher matcher) {
    }
}
