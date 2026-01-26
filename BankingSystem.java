package BankSystemPack;
import java.io.*;
import java.util.*;

public class BankingSystem {

    private static final String USER_FILE = "users.dat";  // file to store user data

    // Load users from file
    @SuppressWarnings("unchecked")
	public static Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (Map<String, User>) in.readObject();
        } catch (FileNotFoundException e) {
            // No existing users file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
    
 // Save users to file
    public static void saveUsers(Map<String, User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Register a new user with an account type
    public static boolean registerUser(String username, String password, AccountType accountType) {
        Map<String, User> users = loadUsers();
        if (users.containsKey(username)) {
            return false;  // User already exists
        }
        User newUser = new User(username, password);
        newUser.createAccount(accountType);  // Create account of chosen type
        users.put(username, newUser);
        saveUsers(users);
        return true;
    }
    
    // Login a user
    public static User loginUser(String username, String password) {
        Map<String, User> users = loadUsers();
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
