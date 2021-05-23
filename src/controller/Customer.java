package controller;

public class Customer {
    private int ID;
    private String password;

    // constructor
    public Customer(int ID, String password) {
        this.ID = ID;
        this.password = password;
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

    public int getID() { return ID; }
    public String getPassword() { return password; }
}
