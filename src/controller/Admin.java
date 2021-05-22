package controller;

public class Admin {
    private int ID;

    // constructor
    public Admin(int ID) {
        this.ID = ID;
        Database.getInstance().addAdmin(this);
    }

    public static Admin findAdmin(int ID) {
        for (Admin admin : Database.getInstance().getAdmins()){
            if (admin.getID() == ID) {
                return admin;
            }
        }
        return null;
    }

    // GETTERS & SETTERS
    public int getID() { return ID; }
}
