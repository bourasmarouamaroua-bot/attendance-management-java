package model;

public class Teacher extends User {

    private String speciality;

    public Teacher(int id,
                   String name,
                   String username,
                   String password,
                   String role,
                   String speciality) {

        super(id, name, username, password, role);

        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return super.toString() + " - Speciality: " + speciality;
    }
}