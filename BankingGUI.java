package BankSystemPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BankingGUI {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField amountField;
    private JTextArea infoArea;
    private JComboBox<AccountType> accountTypeComboBox;
    private JComboBox<String> accountSelectionComboBox;

    private User currentUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingGUI().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new CardLayout());

        // Panel for Login and Registration
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2));

        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        loginPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegistration());
        loginPanel.add(registerButton);

        // Account Type selection during registration
        loginPanel.add(new JLabel("Select Account Type:"));
        accountTypeComboBox = new JComboBox<>(AccountType.values());
        loginPanel.add(accountTypeComboBox);

        frame.add(loginPanel, "login");

        // Panel for User Dashboard (after login)
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        // Info area to display user account information
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        userPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // Panel for Deposit and Withdraw
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(2, 2));

        actionPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        actionPanel.add(amountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> handleDeposit());
        actionPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> handleWithdraw());
        actionPanel.add(withdrawButton);

        userPanel.add(actionPanel, BorderLayout.SOUTH);

        // Panel for selecting account type and logout button
        JPanel accountPanel = new JPanel();
        accountSelectionComboBox = new JComboBox<>();
        accountPanel.add(accountSelectionComboBox);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());
        accountPanel.add(logoutButton);

        // Add a button to change password
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> handleChangePassword());
        accountPanel.add(changePasswordButton);

        userPanel.add(accountPanel, BorderLayout.NORTH);

        frame.add(userPanel, "user");

        frame.setVisible(true);
    }

    // Handle the login process
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        currentUser = BankingSystem.loginUser(username, password);
        if (currentUser != null) {
            // Switch to user dashboard screen
            ((CardLayout) frame.getLayout()).show(frame.getContentPane(), "user");
            updateUserInfo();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password!");
        }
    }

    // Handle the registration process
    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        AccountType selectedAccountType = (AccountType) accountTypeComboBox.getSelectedItem();

        boolean success = BankingSystem.registerUser(username, password, selectedAccountType);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Registration successful!");
        } else {
            JOptionPane.showMessageDialog(frame, "Username already exists!");
        }
    }

// Handle deposit action
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(frame, "Amount must be greater than zero.");
                return;
            }

            AccountType selectedAccountType = getSelectedAccountType();
            if (selectedAccountType != null) {
                currentUser.deposit(selectedAccountType, amount);
                updateUserInfo();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid amount.");
        }
    }

// Handle withdrawal action
    private void handleWithdraw() {
        try {
            Double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(frame, "Amount must be greater than zero.");
                return;
            }

            AccountType selectedAccountType = getSelectedAccountType();
            if (selectedAccountType != null) {
                currentUser.withdraw(selectedAccountType, amount);
                updateUserInfo();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid amount.");
        }
    }

// Handle logout action
    private void handleLogout() {
        currentUser = null;
        ((CardLayout) frame.getLayout()).show(frame.getContentPane(), "login");
    }

    // Handle changing password
    private void handleChangePassword() {
        String oldPassword = JOptionPane.showInputDialog(frame, "Enter your old password:");
        if (oldPassword == null || oldPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Old password is required.");
            return;
        }

        String newPassword = JOptionPane.showInputDialog(frame, "Enter your new password:");
        if (newPassword == null || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "New password is required.");
            return;
        }

String confirmPassword = JOptionPane.showInputDialog(frame, "Confirm your new password:");
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match.");
            return;
        }

        boolean success = currentUser.changePassword(oldPassword, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Password changed successfully.");
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect old password.");
        }
    }

// Get the selected account type from the account combo box
    private AccountType getSelectedAccountType() {
        String selectedAccount = (String) accountSelectionComboBox.getSelectedItem();
        for (Account account : currentUser.getAccounts()) {
            if (account.toString().equals(selectedAccount)) {
                return account.getType();
            }
        }
        return null;
    }

// Update the user info and available accounts on the user dashboard
    private void updateUserInfo() {
        infoArea.setText("User: " + currentUser.getUsername() + "\n");
        infoArea.append("Accounts:\n");

        // Update account selection combo box with account types
        accountSelectionComboBox.removeAllItems();
        List<Account> accounts = currentUser.getAccounts();
        for (Account account : accounts) {
            accountSelectionComboBox.addItem(account.toString());
        }

// Display user’s transaction history
        infoArea.append("\nTransaction History:\n");
        for (Transaction transaction : currentUser.getTransactions()) {
            infoArea.append(transaction.toString() + "\n");
        }
    }
}