package repository;

import entity.User;
import java.util.*;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) { users.add(user); }
    public List<User> getAllUsers() { return users; }

    public User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}