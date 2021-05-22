package controller;

import model.ConsoleColors;

import java.util.Scanner;
import java.util.regex.Matcher;
import static controller.Utils.date;
import static controller.Utils.randomCode;

public class UserInputProcessor {
    Scanner scanner = new Scanner(System.in);
    String input;

    public void run(){
        boolean exit = false;
        while (!exit) {
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Enter command: " + ConsoleColors.RESET);
            exit = runCommand(scanner.nextLine());
        }
    }

    private boolean runCommand(String input) {
        if (input.equals("exit")) {
            Database.getInstance().setCurrentCustomer(null);
            Database.write();
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
        Database.write();
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
            Database.getInstance().setCurrentCustomer(Customer.findCustomer(ID));
        }
    }

    private void allItems(Matcher matcher) {
        if (matcher.find())
            Item.printAll();
    } // DONE

    private void itemsInStock(Matcher matcher) {
        if (matcher.find())
           Item.printInStock();
    } // DONE

    private void outOfStock(Matcher matcher) {
        if (matcher.find())
            Item.printOutOfStock();
    } // DONE

    private void order(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            int count = Integer.parseInt(matcher.group(2));
            boolean setOrder = true;
            if (Item.findItem(ID) == null) {
                System.out.println(ConsoleColors.WHITE_BACKGROUND +
                        "Item does not exist. Order not placed." + ConsoleColors.RESET);
                return;
            }
            if (Database.getInstance().getCurrentCustomer() == null) {
                System.out.println(ConsoleColors.WHITE_BACKGROUND + "You must be logged in to place an order."
                + ConsoleColors.RESET);
                return;
            }
            if (count <= 0) {
                System.out.println(ConsoleColors.CYAN_BACKGROUND + "Invalid count." + ConsoleColors.RESET);
                setOrder = false;
            }
            else if (count > Item.findItem(ID).getInStock()) {
                System.out.println("Not enough " + ConsoleColors.CYAN_BRIGHT +
                        Item.findItem(ID).getName() + ConsoleColors.RESET + " in stock.");
                setOrder = false;
            }
            if (setOrder) {
                int orderID = randomCode();
                new Order(Database.getInstance().getCurrentCustomer().getID(), date(), orderID, count);
                Database.getInstance().setCount(Item.findItem(ID), Item.findItem(ID).getInStock() - count);
                System.out.println("You have ordered " + ConsoleColors.GREEN_BACKGROUND + count +
                        ConsoleColors.RESET + " " + ConsoleColors.GREEN_BACKGROUND + Item.findItem(ID).getName() +
                        ConsoleColors.RESET + ". Your order ID is: " + ConsoleColors.GREEN_BACKGROUND +
                        orderID + ConsoleColors.RESET);
            }
            else System.out.println(ConsoleColors.RED_BACKGROUND +"Order was not placed." + ConsoleColors.RESET);
        }
    } // DONE

    private void cancelOrder(Matcher matcher) {
    }

    private void logout(Matcher matcher) {
    }
}
