package com.pluralsight.accounting;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    //initializing of an array list of transaction objects
    static ArrayList<Transaction> ledger;
    //csv path


    public static void main(String[] args) throws IOException {

        try {
           ledger = FileManager.loadTransactions();
        } catch (Exception exo) {
            System.out.println(exo.getLocalizedMessage());
        }

        System.out.println(ledger);

        //create Scanner to read user input
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
                    makeDeposit();
                    break;
                case "P":
                    System.out.println("Make payment ");
                    //method for a payment
                    makePayment();
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
    private static void makeDeposit() {
        // Get amount
        System.out.println(" How much would you like to deposit?");
        Scanner depoScanner = new Scanner(System.in);
        double amount = depoScanner.nextDouble();
        depoScanner.nextLine();

        // Get payee
        System.out.println("Who's the payee");
        String payee = depoScanner.nextLine();

        //Get description
        System.out.println("Transaction description");
        String desc = depoScanner.nextLine();

        //Get date and time
        LocalDateTime dateTime = LocalDateTime.now();

        Transaction deposit = new Transaction(dateTime.toLocalDate(), dateTime.toLocalTime(), desc, payee, amount);


        System.out.println("Awesome your deposit was " + deposit + ".");

        FileManager.saveTransactionToFile(deposit);



    }
    private static void makePayment() {
        System.out.println("How much would you like to pay? ");

        Scanner payScanner = new Scanner(System.in);
        double payment = -Math.abs(payScanner.nextDouble()); // Negative value for payments
        payScanner.nextLine();

        System.out.println("Who's the payee?");
        String payee = payScanner.nextLine();

        // Get description
        System.out.println("Transaction description");
        String desc = payScanner.nextLine();

        // Get current date and time
        LocalDateTime dateTime = LocalDateTime.now();

        // Create payment transaction
        Transaction paymentTransaction = new Transaction(dateTime.toLocalDate(), dateTime.toLocalTime(), desc, payee, payment);

        System.out.println("Your payment was successful. ");

        // Save payment to the file
        FileManager.saveTransactionToFile(paymentTransaction);


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
                    viewAllTransactions();
                    //method for all entries
                    break;
                case "D":
                    System.out.println("Showing all deposits");
                    //method for deposits
                    showDeposits();
                    break;
                case "P":
                    System.out.println("Showing all payments");
                    //method for payments
                    showPayments();
                    break;
                case "R":
                    System.out.println("\n======= Reports =======");
                    System.out.println();
                    reports();
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
    //viewAll
    public static void viewAllTransactions() {

        System.out.println("======= All Transactions =======");
        for (Transaction transaction : ledger) {
            System.out.println(transaction);
        }
    }
    public static void showDeposits() {
        for (Transaction transaction : ledger) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }
    public static void showPayments() {
        for (Transaction transaction : ledger) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }
    private static void reports() {
        System.out.println("Choose a report to display");

        Scanner myReports = new Scanner(System.in);

        Boolean input = true;

        while (input) {
            System.out.println("""
                    1) Month To Date
                    2) Previous Month
                    3) Year to date
                    4) Previous year
                    5) Search by Vendor
                    6) Return to Ledger
                    """);
            String option = myReports.nextLine();

            switch (option) {
                case "1":
                    System.out.println("Displaying month to date");
                    // method for month to date
                    monthToDate();
                    break;
                case "2":
                    System.out.println("Displaying previous month");
                    // method for previous month
                    prevMonth();
                    break;
                case "3":
                    System.out.println("Displaying year to date");
                    // method for year to date
                    yearToDate();
                    break;
                case "4":
                    System.out.println("Previous year");
                    // method for previous year
                    prevYear();
                    break;
                case "5":
                    System.out.println("Search by vendor");
                    // method for searching by vendor
                    vendorSearch();
                    break;
                case "6":
                    System.out.println("Back to Ledger Menu");
                    input = false;
                    break;
                default:
                    System.out.println("Invalid response please choose again");

                    break;
            }
        }
    }
    public static void monthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofmonth = today.withDayOfMonth(1);
        System.out.println("======= Month To Date =======");

        for (Transaction transaction : ledger) {
            if (!transaction.getDate().isBefore(firstDayofmonth) && !transaction.getDate().isAfter(today)) {
                System.out.println(transaction);
            }
        }
    }
    public static void prevMonth() {
        LocalDate today = LocalDate.now();
        YearMonth previousMonth = YearMonth.from(today).minusMonths(1);
        LocalDate startOfPrevMonth = previousMonth.atDay(1);
        LocalDate endOfPrevMonth = previousMonth.atEndOfMonth();


        System.out.println("previous month" + previousMonth);

        for (Transaction transaction : ledger) {
            if (!transaction.getDate().isBefore(startOfPrevMonth) && !transaction.getDate().isAfter(endOfPrevMonth)) ;
            System.out.println(transaction);
        }
    }
    public static void yearToDate() {
        LocalDate today = LocalDate.now();

        LocalDate startOfYear = LocalDate.of(today.getYear(), 1, 1);
        System.out.println("Year To Date " + startOfYear + " to " + today);

        for (Transaction transaction : ledger) {
            if (!transaction.getDate().isBefore(startOfYear) && !transaction.getDate().isAfter(today)) {
                System.out.println(transaction);
            }
        }

    }
    public static void prevYear() {

        LocalDate today = LocalDate.now();

        int prevYear = today.getYear() - 1;
        LocalDate startOfPrevYear = LocalDate.of(prevYear, 1, 1);
        LocalDate endOfPrevOfYear = LocalDate.of(prevYear, 12, 31);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String formattedStartOfPrevYear = startOfPrevYear.format(formatter);
        String formattedEndOfYear = endOfPrevOfYear.format(formatter);

        System.out.println(" previous year " + formattedStartOfPrevYear + " to " + formattedEndOfYear);
        for (Transaction transaction : ledger) {
            if (transaction.getDate().getYear() - 1 == prevYear) ;
            System.out.println(transaction);
        }
    }
    public static void vendorSearch() {
        Scanner searchVendorScanner = new Scanner(System.in);

        System.out.println("Please enter the name of the vendor you're searching for ");
        String search = searchVendorScanner.nextLine().trim().toLowerCase();

        boolean vendorFound = false;

        for (Transaction transaction : ledger) {
            if (transaction.getVendor().toLowerCase().contains(search)) {
                System.out.println("Found vendor" + transaction.getVendor());
                System.out.println(transaction);
                vendorFound = true;
            }
        }

        if (!vendorFound) {
            System.out.println(" No vendor found " + search);
        }
    }
}

