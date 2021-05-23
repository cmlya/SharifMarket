package controller;

import model.ConsoleColors;
import static controller.Utils.digitCount;
import static controller.Utils.spaces;

public class Order {
    private int userID;
    private String date;
    private int itemID;
    private int number;
    private int orderID;
    private String itemName;

    // constructor
    public Order(int userID, String date, int itemID, int number, int orderID, String itemName) {
        this.userID = userID;
        this.date = date;
        this.itemID = itemID;
        this.number = number;
        this.orderID = orderID;
        this.itemName = itemName;
        Database.getInstance().addOrder(this);
    }

    public static Order findOrder(int orderID) {
        for (Order order : Database.getInstance().getOrders())
            if (order.orderID == orderID)
                return order;
        return null;
    }

    public static void printOrders() {
        int orderIDLength = 8;
        int maxUserID = 9999999;
        int dateLength = 19;
        int maxLengthName = ("ITEM".length());
        int maxCount = 99999;
        for (Order order : Database.getInstance().getOrders()) {
            if (order.userID > maxUserID)
                maxUserID = order.userID;
            if (order.itemName.length() > maxLengthName)
                maxLengthName = order.itemName.length();
            if (order.number > maxCount)
                maxCount = order.number;
        }
        System.out.println(ConsoleColors.PURPLE + "NEW ORDERS:" + ConsoleColors.RESET);
        String header = "ORDER ID" + spaces("ORDER ID", orderIDLength) +
                "USER ID" + spaces("USER ID", digitCount(maxUserID)) +
                "DATE" + spaces("DATE", dateLength) + "ITEM" + spaces("ITEM", maxLengthName) +
                "COUNT" + spaces("COUNT", digitCount(maxCount));
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.BLUE_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Order order : Database.getInstance().getOrders()) {
            System.out.println(order.orderID + spaces(String.valueOf(order.orderID), 8) +
                    order.userID + spaces(String.valueOf(order.userID), digitCount(maxUserID)) +
                    order.date + spaces(order.date, dateLength) +
                    order.itemName + spaces(order.itemName, maxLengthName) +
                    order.number);
        }
    }

    public static void printHistory() {
        int orderIDLength = 8;
        int maxUserID = 9999999;
        int dateLength = 19;
        int maxLengthName = ("ITEM".length());
        int maxCount = 99999;
        for (Order order : Database.getInstance().getOrderHistory()) {
            if (order.userID > maxUserID)
                maxUserID = order.userID;
            if (order.itemName.length() > maxLengthName)
                maxLengthName = order.itemName.length();
            if (order.number > maxCount)
                maxCount = order.number;
        }
        System.out.println(ConsoleColors.BLUE + "ORDER HISTORY:" + ConsoleColors.RESET);
        String header = "ORDER ID" + spaces("ORDER ID", orderIDLength) +
                "USER ID" + spaces("USER ID", digitCount(maxUserID)) +
                "DATE" + spaces("DATE", dateLength) + "ITEM" + spaces("ITEM", maxLengthName) +
                "COUNT" + spaces("COUNT", digitCount(maxCount));
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Order order : Database.getInstance().getOrderHistory()) {
            System.out.println(order.orderID + spaces(String.valueOf(order.orderID), 8) +
                    order.userID + spaces(String.valueOf(order.userID), digitCount(maxUserID)) +
                    order.date + spaces(order.date, dateLength) +
                    order.itemName + spaces(order.itemName, maxLengthName) +
                    order.number);
        }
    }

    public int getUserID() { return userID; }
    public int getItemID() { return itemID; }
    public int getNumber() { return number; }
}
