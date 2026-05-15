package service;

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

    public User login(String username, String password) {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null;
    }
}