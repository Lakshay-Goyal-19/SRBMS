package controller;

import entity.*;
import services.*;

import java.util.List;

public class UserController {
    private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    public boolean registerUser(User user) {
        return userService.registerUser(user);
    }

    public User login(String email, String password) {
        return userService.login(email, password);
    }

    public void viewAllUsers() {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            System.out.println("No users found in the system");
            return;
        }
        System.out.println("\n=== All Users ===");
        users.forEach(user -> System.out.println("ID: " + user.getId() + 
                                               " | Name: " + user.getName() + 
                                               " | Email: " + user.getEmail() + 
                                               " | Role: " + user.getClass().getSimpleName()));
        System.out.println();
    }
}