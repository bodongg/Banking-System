package BankSystemPack;

import java.io.Serializable;

public class Account implements Serializable {
    private AccountType type;
    private double balance;

    // Constructor
    public Account(AccountType type) {
        this.type = type;
        this.balance = 0.0;  // Initial balance is 0
    }

    // Getters and Setters
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
        }
    }
    
    
    public void withdraw(double amount) {
        if(amount > 0 && amount <= balance) {
            this.balance -= amount;
        }
    }

    @Override
    public String toString() {
        return String.format("%s Account - Balance: $%.2f", type, balance);
    }
}
    