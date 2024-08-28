package com.globant.view;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private final Scanner scanner = new Scanner(System.in);
    private static final int INVALID_CHOICE = -1;

    public int getUserChoice() {
        System.out.println("Choose one option to continue:");
        System.out.println("1. Create User");
        System.out.println("2. Login");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return INVALID_CHOICE;
        }
    }

    public String getNameInput() {
        System.out.print("Enter name: ");
        return scanner.next();
    }

    public String getEmailInput() {
        System.out.print("Enter email: ");
        return scanner.next();
    }

    public String getPasswordInput() {
        System.out.print("Enter password: ");
        return scanner.next();
    }

    public void showError(String errorMessage) {
        System.out.println(ANSI_RED + errorMessage + ANSI_RESET);
    }

    public void showInfo(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public void showSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public void close() {
        scanner.close();
    }


    public BigDecimal getAmountInput() {
        System.out.print("Enter amount: ");
        try{
            return scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println(ANSI_RED + "Invalid amount, try again." + ANSI_RESET);
            return getAmountInput();
        }
    }

    public int getLoggedInChoice() {
        System.out.println("Choose one option to continue:");
        System.out.println("1. Deposit Fiat");
        System.out.println("2. Buy Crypto");
        System.out.println("3. Log Out");
        System.out.print("Enter your choice: ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return INVALID_CHOICE;
        }
    }

    public String getCryptoName() {
        System.out.print("Enter crypto name (BTC or ETH): ");
        return scanner.nextLine().toUpperCase();
    }
}
