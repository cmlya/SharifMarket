package controller;

public class Admin {
    private final int ID;
    private final String password;

    public Admin(int ID, String password) {
        this.ID = ID;
        this.password = password;
        Database.getInstance().addAdmin(this);
    }

    public static Admin findAdmin(int ID) {
        return Database.getInstance().getAdmins().stream().filter(admin -> admin.getID() == ID).findFirst().orElse(null);
    }

    public int getID() { return ID; }
    public String getPassword() { return password; }
}
