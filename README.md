# Banking System

A small Java desktop banking application built with Swing. Users can register, choose a checking or savings account, sign in, view balances and transaction history, deposit or withdraw funds, and change their password. Registration creates a local `users.dat` file.

This is an educational demonstration. It does not connect to a real bank and is not suitable for real financial information or production use.

## Features

- Register and log in with a username and password
- Create a checking or savings account during registration
- View account balances and transaction history
- Deposit and withdraw money with basic amount checks
- Change the account password
- Serialize registered user and initial account data locally in `users.dat`

## Requirements

- JDK 8 or later
- No third-party libraries are required; the interface uses Java Swing

Check that Java is installed:

```bash
java -version
javac -version
```

## Compile and run

Open a terminal in the repository folder, where the `.java` files are located, then run:

```bash
mkdir out
javac -d out *.java
java -cp out BankSystemPack.BankingGUI
```

The compiled classes are written to `out/`. The first launch opens the login and registration window. Register a user, select an account type, and then log in to try the account features.

On Windows PowerShell, if `mkdir out` reports that the folder already exists, keep the existing folder and run the `javac` and `java` commands.

## Project files

| File | Purpose |
| --- | --- |
| `BankingGUI.java` | Swing screens and button actions |
| `BankingSystem.java` | Registration, login, and local user-file persistence |
| `User.java` | User accounts, deposits, withdrawals, and transaction history |
| `Account.java` | Account type and balance operations |
| `AccountType.java` | Checking and savings account types |
| `Transaction.java` | Deposit and withdrawal transaction records |

All source files declare the `BankSystemPack` package. The compile command uses `-d out` to place the generated class files in the matching package directory.

## Data and security

The program creates `users.dat` in the working directory when a user registers. Keep this file with the project if you want to retain registered demo users; remove it to start over. It contains Java-serialized application objects, including passwords stored as plain text by this project. Use only fictional test credentials, and do not store real passwords or banking data in it. The current GUI does not save deposits, withdrawals, transaction history, or changed passwords back to the file, so those changes are lost when the program closes.

The balances and transactions are simulated locally. They are not sent to a server, connected to a bank, or protected with production-grade authentication or storage.
