package controller;

public class Item {
    private String name;
    private int ID;
    private int buyingPrice;
    private int sellingPrice;
    private int inStock;

    // constructor
    public Item(String name, int ID, int buyingPrice, int sellingPrice, int inStock) {
        this.name = name;
        this.ID = ID;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.inStock = inStock;
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