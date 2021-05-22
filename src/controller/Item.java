package controller;
import model.ConsoleColors;

import static controller.Utils.digitCount;
import static controller.Utils.spaces;

import java.util.Random;

public class Item {
    private String name;
    private int inStock;
    private int buyingPrice;
    private int sellingPrice;
    private int ID;


    // constructor
    public Item(String name, int ID, int buyingPrice, int sellingPrice, int inStock) {
        this.name = name;
        this.ID = ID;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.inStock = inStock;
        Database.getInstance().addItem(this);
    }

    public static Item findItemByName(String name) {
        for (Item item : Database.getInstance().getItems())
            if (item.name.equals(name))
                return item;
        return null;
    }

    public static Item findItem(int ID) {
        for (Item item : Database.getInstance().getItems())
            if (item.ID == ID)
                return item;
        return null;
    }

//    public static int randomCode() {
//        while (true) {
//            Random rnd = new Random();
//            if (findItem(Integer.parseInt(String.format("%06d", rnd.nextInt(999999)))) == null)
//                return Integer.parseInt(String.format("%06d", rnd.nextInt(999999)));
//        }
//    }

    public static void printAll() {
        int maxLengthName = ("ITEM".length());
        int maxInStock = 99999999;
        int maxSellingPrice = 99999;
        for (Item item : Database.getInstance().getItems()) {
            if (item.name.length() > maxLengthName)
                maxLengthName = item.name.length();
            if (item.inStock > maxInStock)
                maxInStock = item.inStock;
            if (item.sellingPrice > maxSellingPrice)
                maxSellingPrice = item.sellingPrice;
        }

        System.out.println(ConsoleColors.CYAN_BOLD + "ALL ITEMS" + ConsoleColors.RESET);
        String header = "ITEM" + spaces("ITEM", maxLengthName) +
                "IN STOCK" + spaces("IN STOCK", digitCount(maxInStock)) +
                "PRICE";
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.PURPLE_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Item item : Database.getInstance().getItems()) {
            System.out.println(item.name + spaces(item.name, maxLengthName) +
                    item.inStock + spaces(String.valueOf(item.inStock), digitCount(maxInStock)) +
                    item.sellingPrice + spaces(String.valueOf(item.sellingPrice), digitCount(maxSellingPrice)));
        }
    }

    public static void printInStock() {
        int maxLengthName = ("ITEM".length());
        int maxInStock = 99999999;
        int maxSellingPrice = 99999;
        for (Item item : Database.getInstance().getItems()) {
            if (item.inStock != 0) {
                if (item.name.length() > maxLengthName)
                    maxLengthName = item.name.length();
                if (item.inStock > maxInStock)
                    maxInStock = item.inStock;
                if (item.sellingPrice > maxSellingPrice)
                    maxSellingPrice = item.sellingPrice;
            }
        }

        System.out.println(ConsoleColors.GREEN_BOLD + "ITEMS IN STOCK" + ConsoleColors.RESET);
        String header = "ITEM" + spaces("ITEM", maxLengthName) +
                "IN STOCK" + spaces("IN STOCK", digitCount(maxInStock)) +
                "PRICE";
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.PURPLE_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Item item : Database.getInstance().getItems()) {
            if (item.inStock != 0) {
                System.out.println(item.name + spaces(item.name, maxLengthName) +
                        item.inStock + spaces(String.valueOf(item.inStock), digitCount(maxInStock)) +
                        item.sellingPrice + spaces(String.valueOf(item.sellingPrice), digitCount(maxSellingPrice)));
            }
        }
    }

    public static void printOutOfStock() {
        int maxLengthName = ("ITEM".length());
        int maxSellingPrice = 99999;
        for (Item item : Database.getInstance().getItems()) {
            if (item.inStock == 0) {
                if (item.name.length() > maxLengthName)
                    maxLengthName = item.name.length();
                if (item.sellingPrice > maxSellingPrice)
                    maxSellingPrice = item.sellingPrice;
            }
        }

        System.out.println(ConsoleColors.RED_BOLD + "UNAVAILABLE ITEMS" + ConsoleColors.RESET);
        String header = "ITEM" + spaces("ITEM", maxLengthName) + "PRICE";
        System.out.println(header);
        for (int i = 0; i < header.length(); i++)
            System.out.print(ConsoleColors.PURPLE_BRIGHT + "-" + ConsoleColors.RESET);
        System.out.println();
        for (Item item : Database.getInstance().getItems()) {
            if (item.inStock == 0) {
                System.out.println(item.name + spaces(item.name, maxLengthName) + item.sellingPrice);
            }
        }
    }

    // GETTERS & SETTERS
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public int getBuyingPrice() { return buyingPrice; }
    public void setBuyingPrice(int buyingPrice) { this.buyingPrice = buyingPrice; }
    public int getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(int sellingPrice) { this.sellingPrice = sellingPrice; }
    public int getInStock() { return inStock; }
    public void setInStock(int inStock) { this.inStock = inStock; }
}