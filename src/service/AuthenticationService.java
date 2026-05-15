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

    public User login(String username,
                      String password)
            throws InvalidLoginException {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        throw new InvalidLoginException(
                "Invalid username or password."
        );
    }
}