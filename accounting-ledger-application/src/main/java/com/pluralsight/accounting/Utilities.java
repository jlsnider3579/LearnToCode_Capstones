package com.pluralsight.accounting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

    public class Utilities {

        // Method for making a deposit
        public static void makeDeposit() {
            Scanner depoScanner = new Scanner(System.in);

            // Get amount
            System.out.println("How much would you like to deposit?");
            double amount = depoScanner.nextDouble();
            depoScanner.nextLine(); // Consume newline character

            // Get payee
            System.out.println("Who's the payee?");
            String payee = depoScanner.nextLine();

            // Get description
            System.out.println("Transaction description:");
            String desc = depoScanner.nextLine();

            // Create a deposit transaction and save it
            Transaction deposit = new Transaction(LocalDate.now(), LocalTime.now(), desc, payee, amount);
            System.out.println("Your deposit was successful: " + deposit);

            // Save the deposit to the file (method in Utilities class)
            FileManager.saveTransactionToFile(deposit);
        }

        // Method for making a payment
        public static void makePayment() {
            Scanner payScanner = new Scanner(System.in);

            // Get payment amount (negative for payment)
            System.out.println("How much would you like to pay?");
            double payment = -Math.abs(payScanner.nextDouble());  // Negative value for payments
            payScanner.nextLine();  // Consume newline character

            // Get payee
            System.out.println("Who's the payee?");
            String payee = payScanner.nextLine();

            // Get description
            System.out.println("Transaction description:");
            String desc = payScanner.nextLine();

            // Create a payment transaction and save it
            Transaction paymentTransaction = new Transaction(LocalDate.now(), LocalTime.now(), desc, payee, payment);
            System.out.println("Your payment was successful: " + paymentTransaction);

            // Save the payment to the file (method in Utilities class)
            FileManager.saveTransactionToFile(paymentTransaction);
        }

        // Method for displaying all transactions
        public static void viewAllTransactions() {
            // Assuming `ledger` is an ArrayList of Transaction objects
            for (Transaction transaction : Main.ledger) {
                System.out.println(transaction);
            }
        }

        // Method for showing all deposits
        public static void showDeposits() {
            for (Transaction transaction : Main.ledger) {
                if (transaction.getAmount() > 0) {
                    System.out.println(transaction);
                }
            }
        }

        // Method for showing all payments
        public static void showPayments() {
            for (Transaction transaction : Main.ledger) {
                if (transaction.getAmount() < 0) {
                    System.out.println(transaction);
                }
            }
        }

        // Placeholder for the reports method
        public static void reports() {
            System.out.println("Generating reports...");
            // Add report generation logic here
        }

    }

