package com.pluralsight.accounting;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FileManager {

    // CSV file path
    static final String csvFileName = "./src/main/resources/transaction.csv";

    // Reads transactions from CSV and returns a list of transactions
    public static ArrayList<Transaction> loadTransactions() throws IOException {
        ArrayList<Transaction> ledger = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFileName))) {
            // Skip the header line
            bufferedReader.readLine();

            String fileInput;
            while ((fileInput = bufferedReader.readLine()) != null) {
                // Split the line using the pipe symbol
                String[] split = fileInput.split("\\|");

                // Create a new Transaction object and add it to the ledger
                Transaction transaction = new Transaction(
                        LocalDate.parse(split[0]),
                        LocalTime.parse(split[1]),
                        split[2],
                        split[3],
                        Double.parseDouble(split[4])
                );
                ledger.add(transaction);
            }
        }
        return ledger;
    }

    // Saves a transaction to the CSV file
    public static void saveTransactionToFile(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        try (FileWriter fw = new FileWriter(csvFileName, true)) {
            // Create a string for the transaction details to save in the CSV file
            sb.append(transaction.getDate().toString())
                    .append("|")
                    .append(transaction.getTime().toString())
                    .append("|")
                    .append(transaction.getDescription())
                    .append("|")
                    .append(transaction.getVendor())
                    .append("|")
                    .append(transaction.getAmount());

            String newEntry = sb.toString();
            fw.write(newEntry);
            fw.write("\n");
        } catch (Exception ioExp) {
            ioExp.printStackTrace();
            System.out.println("Error saving transaction");
        }
    }
}
