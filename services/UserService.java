package services;

import entity.*;
import repository.*;
import java.util.*;
import java.util.logging.Logger;

public class UserService {
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public boolean registerUser(User user) {
        if (!isValidEmail(user.getEmail())) {
            System.out.println("Registration Failed: Invalid email format");
            return false;
        }
        if (isEmailTaken(user.getEmail())) {
            System.out.println("Registration Failed: Email already registered");
            return false;
        }
        userRepository.addUser(user);
        System.out.println("Registration Successful: " + user.getName());
        return true;
    }

    private boolean isEmailTaken(String email) {
        return userRepository.getAllUsers().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public User login(String email, String password) {
        User user = userRepository.authenticate(email, password);
        if (user == null) {
            System.out.println("Login Failed: Invalid credentials");
        }
        return user;
    }

    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }
}