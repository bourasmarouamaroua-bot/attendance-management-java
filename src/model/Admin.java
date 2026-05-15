package model;

public class Admin extends User {

    public Admin(int id, String name, String username, String password, String role) {
        super(id, name, username, password, role);
    }

    @Override
    public String toString() {
        return "Admin: " + super.toString();
    }
}