package BankSystemPack;
import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private Date date;
    private String type;  // “Deposit” or “Withdrawal”
    private double amount;

    // Constructor
    public Transaction(String type, double amount) {
        this.date = new Date();  // current date and time
        this.type = type;
        this.amount = amount;
    }

    // Getters
    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s: $%.2f", date.toString(), type, amount);
    }
}
