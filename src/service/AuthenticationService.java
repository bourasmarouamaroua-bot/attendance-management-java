package service;

import exceptions.InvalidLoginException;
import model.User;

import java.util.ArrayList;

public class AuthenticationService {

    private ArrayList<User> users;

    public AuthenticationService() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User login(String username, String password) throws InvalidLoginException {

        username = username.trim();
        password = password.trim();

        for (User user : users) {

            System.out.println("Checking: " + user.getUsername() + " / " + user.getPassword());

            if (user.getUsername().equalsIgnoreCase(username)
                    && user.getPassword().equals(password)) {

                System.out.println("Login found: " + user.getName());
                return user;
            }
        }

        throw new InvalidLoginException("Invalid username or password");
    }
}