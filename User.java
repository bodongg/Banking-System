package BankSystemPack;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Account> accounts;  // List of accounts (checking, savings, etc.)
    private List<Transaction> transactions;  // List of transactions

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

// Method to change the password
    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;  // Password changed successfully
        } else {
            return false;  // Old password is incorrect
        }
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    // Method to create an account of a given type
    public void createAccount(AccountType type) {
        Account newAccount = new Account(type);
        this.accounts.add(newAccount);
    }

    // Find account by type
    public Account getAccount(AccountType type) {
        for (Account account : accounts) {
            if (account.getType() == type) {
                return account;
            }
        }
        return null;
    }

 // Method to perform deposit on the selected account
    public void deposit(AccountType type, double amount) {
        Account account = getAccount(type);
        if (account != null) {
            account.deposit(amount);
            transactions.add(new Transaction("Deposit", amount));  // Add transaction record
        }
    }

 // Method to perform withdrawal on the selected account
    public void withdraw(AccountType type, double amount) {
        Account account = getAccount(type);
        if (account != null && amount <= account.getBalance()) {
            account.withdraw(amount);
            transactions.add(new Transaction("Withdrawal", amount));  // Add transaction record
        }
    }

    // List transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return String.format("User: %s", username);
    }
}
