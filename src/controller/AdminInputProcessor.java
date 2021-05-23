package controller;

import model.ConsoleColors;
import java.util.Scanner;
import java.util.regex.Matcher;
import static controller.Utils.randomCode;

public class AdminInputProcessor {
    Scanner scanner = new Scanner(System.in);
    String input;

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Enter command: " + ConsoleColors.RESET);
            exit = runCommand(scanner.nextLine());
        }
    }

    private boolean runCommand(String input) {
        if (input.equals("exit")) {
            Database.getInstance().setCurrentAdmin(null);
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
            case LOGOUT: logout(Command.getMatcher(input, Command.LOGOUT));
                break;
            case NEW_ORDERS: newOrders(Command.getMatcher(input, Command.NEW_ORDERS));
                break;
            case CHECKOUT: checkout(Command.getMatcher(input, Command.CHECKOUT));
                break;
            case HISTORY: history(Command.getMatcher(input, Command.HISTORY));
                break;
            case NEW_ITEM: newItem(Command.getMatcher(input, Command.NEW_ITEM));
                break;
            case REMOVE: remove(Command.getMatcher(input, Command.REMOVE));
                break;
            case EDIT_NAME_COUNT: editNameCount(Command.getMatcher(input, Command.EDIT_NAME_COUNT));
                break;
            case EDIT_NAME: editName(Command.getMatcher(input, Command.EDIT_NAME));
                break;
            case EDIT_SP_BP_COUNT: editSPBPCount(Command.getMatcher(input, Command.EDIT_SP_BP_COUNT));
                break;
            case CALCULATE_PROFIT: calculateProfit(Command.getMatcher(input, Command.CALCULATE_PROFIT));
                break;
            case CALCULATE_ITEM_PROFIT: calculateItemProfit(Command.getMatcher(input, Command.CALCULATE_ITEM_PROFIT));
                break;
            case SALES: sales(Command.getMatcher(input, Command.SALES));
                break;
            case ITEM_SALES: itemSales(Command.getMatcher(input, Command.ITEM_SALES));
                break;
            default: System.out.println("Command does not exist.");
        }
        Database.write();
        return false;
    }

    private void login(Matcher matcher) {
        if (matcher.find()) {
            int ID = Integer.parseInt(matcher.group(1));
            if (Admin.findAdmin(ID) == null) {
                new Admin(ID);
                System.out.println("New admin - Welcome.");
            }
            else System.out.println("Welcome back.");
            Database.getInstance().setCurrentAdmin(Admin.findAdmin(ID));
        }
    } // DONE

    private void logout(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("No admin is currently logged in.");
                return;
            }
            System.out.println("Admin " + ConsoleColors.CYAN_BRIGHT + Database.getInstance().getCurrentAdmin().getID() +
                    ConsoleColors.RESET + " logged out successfully.");
            Database.getInstance().setCurrentAdmin(null);
        }
    } //DONE

    private void newItem(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("Admin must be logged in to add items.");
                return;
            }
            String name = matcher.group(1);
            int inStock = Integer.parseInt(matcher.group(2));
            int sellingPrice = Integer.parseInt(matcher.group(3));
            int buyingPrice = Integer.parseInt(matcher.group(4));
            if (Item.findItemByName(name) == null) {
                int ID = randomCode();
                new Item(name, ID, buyingPrice, sellingPrice, inStock);
                System.out.println("Item added successfully. Item ID: " + ID);
            } else { System.out.println("Something went wrong. Item was not added."); }
        }
    } // DONE

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

    private void remove(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("Admin must be logged in to remove items.");
                return;
            }
            int ID = Integer.parseInt(matcher.group(1));
            if (Item.findItem(ID) == null)
                System.out.println(ConsoleColors.RED_BRIGHT + "Item does not exit. Nothing was removed." + ConsoleColors.RESET);
            else {
                Item item = Item.findItem(ID);
                System.out.println("Item " + ConsoleColors.CYAN_BRIGHT + item.getName() +
                        ConsoleColors.RESET + " removed successfully");
                Database.getInstance().removeItem(item);
            }
        }
    } // DONE

    private void editName(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("Admin must be logged in to edit items.");
                return;
            }
            int ID = Integer.parseInt(matcher.group(1));
            String newName = matcher.group(2);
            if (Item.findItem(ID) == null)
                System.out.println(ConsoleColors.BLUE + "Item does not exist. Nothing was changed." + ConsoleColors.RESET);
            else if (newName.equals(Item.findItem(ID).getName()))
                System.out.println(ConsoleColors. BLUE +
                        "This item already has this name. Nothing was changed." + ConsoleColors.RESET);
            else {
                System.out.println("Item " + ConsoleColors.CYAN_BRIGHT + Item.findItem(ID).getName() + ConsoleColors.RESET
                + " renamed \"" + ConsoleColors.CYAN_BRIGHT + newName + ConsoleColors.RESET + "\"." );
                Database.getInstance().setName(Item.findItem(ID), newName);
            }
        }
    } // DONE

    private void editNameCount(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("Admin must be logged in to edit items.");
                return;
            }
            int ID = Integer.parseInt(matcher.group(1));
            String newName = matcher.group(2);
            int newCount = Integer.parseInt(matcher.group(3));
            boolean changeName = true;
            boolean changeCount = true;
            if (Item.findItem(ID) == null) {
                System.out.println(ConsoleColors.BLUE + "Item does not exist. Nothing was changed." + ConsoleColors.RESET);
                return;
            }
            Item item = Item.findItem(ID);
            if (newName.equals(item.getName())) {
                changeName = false;
                System.out.println(ConsoleColors.BLUE + "This item already has this name." + ConsoleColors.RESET);
            }
            if (newCount == item.getInStock()) {
                changeCount = false;
                System.out.println(ConsoleColors.BLUE + newCount + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " in stock already." + ConsoleColors.RESET);
            }
            else if (newCount < 0) {
                changeCount = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new count." + ConsoleColors.RESET);
            }
            if (changeName) {
                System.out.println("Item " + ConsoleColors.CYAN_BRIGHT + item.getName() + ConsoleColors.RESET
                        + " renamed \"" + ConsoleColors.CYAN_BRIGHT + newName + ConsoleColors.RESET + "\"." );
                Database.getInstance().setName(item, newName);
            }
            if (changeCount) {
                Database.getInstance().setCount(item, newCount);
                System.out.println(ConsoleColors.CYAN_BRIGHT + newCount + " " + item.getName() + ConsoleColors.RESET +
                        " currently in stock.");
            }
        }
    } // DONE

    private void editSPBPCount(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("Admin must be logged in to edit items.");
                return;
            }
            int ID = Integer.parseInt(matcher.group(1));
            int newSellPrice = Integer.parseInt(matcher.group(2));
            int newBuyPrice = Integer.parseInt(matcher.group(3));
            int newCount = Integer.parseInt(matcher.group(4));
            boolean changeSellPrice = true;
            boolean changeBuyPrice = true;
            boolean changeCount = true;
            if (Item.findItem(ID) == null) {
                System.out.println(ConsoleColors.BLUE + "Item does not exist. Nothing was changed." + ConsoleColors.RESET);
                return;
            }
            Item item = Item.findItem(ID);
            if (newCount == item.getInStock()) {
                changeCount = false;
                System.out.println(ConsoleColors.BLUE + newCount + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " in stock already." + ConsoleColors.RESET);
            }
            else if (newCount < 0) {
                changeCount = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new count." + ConsoleColors.RESET);
            }
            if (newSellPrice == item.getSellingPrice()) {
                changeSellPrice = false;
                System.out.println(ConsoleColors.BLUE + "Sell Price of" + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " is already " + item.getSellingPrice() + "." + ConsoleColors.RESET);
            }
            else if (newSellPrice < 0) {
                changeSellPrice = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new sell price." + ConsoleColors.RESET);
            }
            if (newBuyPrice == item.getBuyingPrice()) {
                changeBuyPrice = false;
                System.out.println(ConsoleColors.BLUE + "Buy Price of" + ConsoleColors.CYAN_BRIGHT + " " + item.getName() +
                        ConsoleColors. BLUE + " is already " + item.getBuyingPrice() + "." + ConsoleColors.RESET);
            }
            else if (newSellPrice < 0) {
                changeBuyPrice = false;
                System.out.println(ConsoleColors. BLUE + "Invalid new buy price." + ConsoleColors.RESET);
            }
            if (newSellPrice < newBuyPrice) {
                changeSellPrice = false;
                changeBuyPrice = false;
                System.out.println(ConsoleColors.RED +
                        "Invalid values. (New selling price cannot be less than new buying price.)" + ConsoleColors.RESET);
            }
            if (changeCount) {
                Database.getInstance().setCount(item, newCount);
                System.out.println(ConsoleColors.CYAN_BRIGHT + newCount + " " + item.getName() + ConsoleColors.RESET +
                        " currently in stock.");
            }
            if (changeSellPrice) {
                Database.getInstance().setSellingPrice(item, newSellPrice);
                System.out.println(item.getName() + " is now being sold for " + ConsoleColors.CYAN_BRIGHT + newSellPrice
                + ConsoleColors.RESET + ".");
            }
            if (changeBuyPrice) {
                Database.getInstance().setBuyingPrice(item, newBuyPrice);
                System.out.println(item.getName() + " is now being bought for " + ConsoleColors.CYAN_BRIGHT + newBuyPrice
                + ConsoleColors.RESET + ".");
            }
        }
    } // DONE

    private void checkout(Matcher matcher) {
        if (matcher.find()) {
            if (Database.getInstance().getCurrentAdmin() == null) {
                System.out.println("Admin must be logged in to checkout orders.");
                return;
            }
            int orderID = Integer.parseInt(matcher.group(1));
            if (Order.findOrder(orderID) == null) {
                System.out.println("Order with this ID does not exit.");
                return;
            }
            Order order = Order.findOrder(orderID);
            Database.getInstance().addOrderHistory(order);
            Database.getInstance().removeOrder(order);
            System.out.println("Order checked out.");
        }
    } // DONE

    private void newOrders(Matcher matcher) {
        if (matcher.find())
            Order.printOrders();
    } // DONE

    private void history(Matcher matcher) {
        if (matcher.find())
            Order.printHistory();
    } // DONE

    private void calculateProfit(Matcher matcher) {
    }

    private void calculateItemProfit(Matcher matcher) {
    }

    private void sales(Matcher matcher) {
    }

    private void itemSales(Matcher matcher) {
    }
}