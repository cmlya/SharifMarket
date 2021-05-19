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
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();

    static Database database;

    static Database getInstance() {
        if(database == null)
            read();
        return database;
    }

    private Database() {
    }

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

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // GETTERS & SETTERS
    public ArrayList<Item> getItems() { return items; }
    public ArrayList<Order> getOrders() { return orders; }
    public ArrayList<Customer> getCustomers() { return customers; }
    public ArrayList<Admin> getAdmins() { return admins; }
}
