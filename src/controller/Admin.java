package controller;

public class Admin {
    private int ID;
    private String password;

    // constructor
    public Admin(int ID, String password) {
        this.ID = ID;
        this.password = password;
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

    public int getID() { return ID; }
    public String getPassword() { return password; }
}
