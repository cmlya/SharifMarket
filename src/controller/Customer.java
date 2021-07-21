package controller;

public class Customer {
    private final int ID;
    private final String password;

    public Customer(int ID, String password) {
        this.ID = ID;
        this.password = password;
        Database.getInstance().addCustomer(this);
    }

    public static Customer findCustomer(int ID) {
        return Database.getInstance().getCustomers().stream().filter(customer -> customer.getID() == ID).findFirst().orElse(null);
    }

    public int getID() { return ID; }
    public String getPassword() { return password; }
}
