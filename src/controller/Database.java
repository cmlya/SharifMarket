package controller;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> deletedItems = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private Customer currentCustomer;
    private Admin currentAdmin;

    static Database database;

    static Database getInstance() {
        if(database == null) {
            read();
            //database = new Database();
        }
        return database;
    }

    private Database() { }

    private static void read(){
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader("database.json");
            database = gson.fromJson(fileReader, Database.class);
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    public static void write(){
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("database.json")){
            gson.toJson(database, writer);
            writer.flush();
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void addCustomer(Customer customer) { customers.add(customer); }
    public void addAdmin(Admin admin) { admins.add(admin); }
    public void addItem(Item item) { items.add(item); }
    public void addDeletedItem(Item item) { deletedItems.add(item); }
    public void addOrder(Order order) { orders.add(order); }
    public void removeOrder(Order order) { orders.remove(order); }
    public void addOrderHistory(Order order) { orderHistory.add(order); }
    public void removeItem(Item item) { items.remove(item); }
    public void setName(Item item, String newName) { item.setName(newName); }
    public void setCount(Item item, int newCount) { item.setInStock(newCount); }
    public void setSellingPrice(Item item, int newSellingPrice) { item.setSellingPrice(newSellingPrice); }
    public void setBuyingPrice(Item item, int newBuyingPrice) { item.setBuyingPrice(newBuyingPrice); }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public void setCurrentCustomer(Customer currentCustomer) { this.currentCustomer = currentCustomer; }
    public Admin getCurrentAdmin() { return currentAdmin; }
    public void setCurrentAdmin(Admin currentAdmin) { this.currentAdmin = currentAdmin; }
    public ArrayList<Item> getItems() { return items; }
    public ArrayList<Item> getDeletedItems() { return deletedItems; }
    public ArrayList<Order> getOrders() { return orders; }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<Admin> getAdmins() { return admins; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }
}
