package controller;

import model.ConsoleColors;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

import static controller.Utils.date;
import static controller.Utils.randomCode;

public class UserInputProcessor {
    Scanner scanner = new Scanner(System.in);

    public void run() {
        Database.getInstance().setCurrentCustomer(null);
        Database.write();
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
        Database.read();
        switch (Command.findCommand(input)) {
            case LOGIN -> login(Command.getMatcher(input, Command.LOGIN));
            case ALL_ITEMS -> allItems(Command.getMatcher(input, Command.ALL_ITEMS));
            case ITEMS_IN_STOCK -> itemsInStock(Command.getMatcher(input, Command.ITEMS_IN_STOCK));
            case OUT_OF_STOCK -> outOfStock(Command.getMatcher(input, Command.OUT_OF_STOCK));
            case ORDER -> order(Command.getMatcher(input, Command.ORDER));
            case CANCEL_ORDER -> cancelOrder(Command.getMatcher(input, Command.CANCEL_ORDER));
            case LOGOUT -> logout(Command.getMatcher(input, Command.LOGOUT));
            default -> {
                System.out.println("Command does not exist.");
                return false;
            }
        }
        Database.write();
        return false;
    }

    private void login(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            if (Customer.findCustomer(ID) == null) {
                System.out.println("New customer - Welcome. Choose a password: ");
                String password = scanner.nextLine();
                System.out.println("Confirm password: ");
                String testString = scanner.nextLine();
                while (!password.equals(testString)) {
                    System.out.println("Passwords do not match. Choose a password: ");
                    password = scanner.nextLine();
                    System.out.println("Confirm password: ");
                    testString = scanner.nextLine();
                }
                new Customer(ID, password);
                System.out.println("Account created successfully!");
            } else {
                String password = Objects.requireNonNull(Customer.findCustomer(ID)).getPassword();
                System.out.println("Enter password: ");
                String attempt = scanner.nextLine();
                int limit = 3;
                while (limit > 0 && !attempt.equals(password)) {
                    System.out.println(ConsoleColors.RED_BACKGROUND + "Incorrect password. Try again." +
                            ConsoleColors.RESET);
                    attempt = scanner.nextLine();
                    limit--;
                    if (limit == 1) {
                        System.out.println("Out of attempts. You have been restricted.");
                        return;
                    }
                }
                System.out.println("Welcome back.");
            }
            Database.getInstance().setCurrentCustomer(Customer.findCustomer(ID));
        }
    }

    private void allItems(Matcher matcher) {
        if (matcher.find())
            Item.printAll();
    }

    private void itemsInStock(Matcher matcher) {
        if (matcher.find())
            Item.printInStock();
    }

    private void outOfStock(Matcher matcher) {
        if (matcher.find())
            Item.printOutOfStock();
    }

    private void order(Matcher matcher) {
        if (matcher.find()) {
            int itemID = Integer.parseInt(matcher.group(1));
            int count = Integer.parseInt(matcher.group(2));
            boolean setOrder = true;
            if (Database.getInstance().getCurrentCustomer() == null) {
                System.out.println(ConsoleColors.WHITE_BACKGROUND + "You must be logged in to place an order."
                        + ConsoleColors.RESET);
                return;
            }
            if (Item.findItem(itemID) == null) {
                System.out.println(ConsoleColors.WHITE_BACKGROUND +
                        "Item does not exist. Order not placed." + ConsoleColors.RESET);
                return;
            }
            if (count <= 0) {
                System.out.println(ConsoleColors.CYAN_BACKGROUND + "Invalid count." + ConsoleColors.RESET);
                setOrder = false;
            } else if (count > Objects.requireNonNull(Item.findItem(itemID)).getInStock()) {
                System.out.println("Not enough " + ConsoleColors.CYAN_BRIGHT +
                        Objects.requireNonNull(Item.findItem(itemID)).getName() + ConsoleColors.RESET + " in stock.");
                setOrder = false;
            }
            if (setOrder) {
                int orderID = randomCode();
                String itemName = Objects.requireNonNull(Item.findItem(itemID)).getName();
                new Order(Database.getInstance().getCurrentCustomer().getID(), date(), itemID, count, orderID, itemName);
                Database.getInstance().setCount(Objects.requireNonNull(Item.findItem(itemID)),
                        Objects.requireNonNull(Item.findItem(itemID)).getInStock() - count);
                System.out.println("You have ordered " + ConsoleColors.GREEN_BACKGROUND + count +
                        ConsoleColors.RESET + " " + ConsoleColors.GREEN_BACKGROUND + itemName +
                        ConsoleColors.RESET + ". Your order ID is: " + ConsoleColors.GREEN_BACKGROUND +
                        orderID + ConsoleColors.RESET);
            } else System.out.println(ConsoleColors.RED_BACKGROUND + "Order was not placed." + ConsoleColors.RESET);
        }
    }

    private void cancelOrder(Matcher matcher) {
        if (matcher.find()) {
            int orderID = Integer.parseInt(matcher.group(1));
            if (Database.getInstance().getCurrentCustomer() == null) {
                System.out.println("You must be signed in to cancel orders.");
                return;
            }
            if (Order.findOrder(orderID) == null) {
                System.out.println(ConsoleColors.RED_BACKGROUND + "No order with such order ID exists." + ConsoleColors.RESET);
                return;
            }
            if (Database.getInstance().getCurrentCustomer().getID() != Objects.requireNonNull(Order.findOrder(orderID)).getUserID()) {
                System.out.println("You can only cancel orders you have made. Order was not cancelled.");
                return;
            }
            if (Item.findItem(Objects.requireNonNull(Order.findOrder(orderID)).getItemID()) != null) {
                Item item = Item.findItem(Objects.requireNonNull(Order.findOrder(orderID)).getItemID());
                Objects.requireNonNull(item).setInStock(item.getInStock() + Objects.requireNonNull(Order.findOrder(orderID)).getNumber());
            }
            Database.getInstance().removeOrder(Order.findOrder(orderID));
            System.out.println(ConsoleColors.GREEN + "Order No. " + ConsoleColors.CYAN_BRIGHT + orderID + ConsoleColors.GREEN +
                    " cancelled successfully." + ConsoleColors.RESET);
        }
    }

    private void logout(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentCustomer() == null) {
                System.out.println("No user is currently logged in.");
                return;
            }
            System.out.println("User " + ConsoleColors.CYAN_BRIGHT + Database.getInstance().getCurrentCustomer().getID() +
                    ConsoleColors.RESET + " logged out successfully.");
            Database.getInstance().setCurrentCustomer(null);
        }
    }
}
