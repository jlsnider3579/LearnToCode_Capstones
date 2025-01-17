package com.pluralsight.accounting;

import java.util.Scanner;

public class UserInterface {
    public void ShowHomeScreen() {
        Scanner myScanner = new Scanner(System.in);
        boolean input = true;

        // create a while loop that continuously runs through the prompts until user exits
        while (input) {
            System.out.println("\n======= Home Screen ======= ");
            System.out.println();
            System.out.println("""
                    (D) Add deposit
                    (p) Make payment
                    (L) Ledger
                    (X) Exit
                    """);
            String choice = myScanner.nextLine().toUpperCase();

            //Create a switch statement to cover user input
            switch (choice) {
                case "D":
                    System.out.println("Enter a deposit ");
                    // method for a deposit
                    Utilities.makeDeposit();

                    break;
                case "P":
                    System.out.println("Make payment ");
                    //method for a payment
                    Utilities.makePayment();
                    break;
                case "L":
                    System.out.println("\n======= Ledger =======");
                    System.out.println();
                    //method for ledger
                    Ledger();
                    break;
                case "X":
                    System.out.println("Exiting");
                    input = false;
                    break;
                default:
                    System.out.println("Invalid option choose on of the following D, P, L,X");
                    break;
            }
        }
    }
    private static void Ledger() {
        System.out.println("Choose an entry to display ");

        Scanner myLedScanner = new Scanner(System.in);


        boolean input = true;

        while (input) {
            System.out.println("""
                    (A) Display all entries
                    (D) deposits
                    (P) payments
                    (R) reports
                    (H) Home
                    
                    """);

            //Create a switch statement for home screen prompts to user
            String option = myLedScanner.nextLine().toUpperCase();
            switch (option) {
                case "A":
                    System.out.println("show all entries");
                    Utilities.viewAllTransactions();
                    //method for all entries
                    break;
                case "D":
                    System.out.println("Showing all deposits");
                    //method for deposits
                    Utilities.showDeposits();
                    break;
                case "P":
                    System.out.println("Showing all payments");
                    //method for payments
                    Utilities.showPayments();
                    break;
                case "R":
                    System.out.println("\n======= Reports =======");
                    System.out.println();
                    Utilities.reports();
                    break;
                case "H":
                    System.out.println("Returning home");
                    input = false;
                    break;
                default:
                    System.out.println("Invalid option choose on of the following (L),(D),(P),(R)");
            }
        }
    }
}

