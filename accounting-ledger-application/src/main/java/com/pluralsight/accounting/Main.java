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

        UserInterface uI = new UserInterface();
        uI.ShowHomeScreen();

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

