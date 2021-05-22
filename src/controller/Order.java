package controller;

public class Order {
    private int userID;
    private String date;
    private int ID;
    private int number;

    // constructor
    public Order(int userID, String date, int ID, int number) {
        this.userID = userID;
        this.date = date;
        this.ID = ID;
        this.number = number;
        Database.getInstance().addOrder(this);
    }

    public static Order findOrder(int ID) {
        for (Order order : Database.getInstance().getOrders())
            if (order.ID == ID)
                return order;
        return null;
    }

    // GETTERS & SETTERS
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }
}
