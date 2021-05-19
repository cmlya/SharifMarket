package controller;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String userID;
    private Date date;
    private int ID;
    private int number;

    // constructor
    public Order(String userID, Date date, int ID, int number) {
        this.userID = userID;
        this.date = date;
        this.ID = ID;
        this.number = number;
    }

    // GETTERS & SETTERS
    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
}
