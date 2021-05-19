package controller;

public class Customer {
    private int ID;

    // constructor
    public Customer(int ID) {
        this.ID = ID;
        Database.getInstance().addCustomer(this);
    }

    public static Customer findCustomer(int ID) {
        for (Customer customer : Database.getInstance().getCustomers()){
            if (customer.getID() == ID) {
                return customer;
            }
        }
        return null;
    }

    // GETTERS & SETTERS
    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
}
