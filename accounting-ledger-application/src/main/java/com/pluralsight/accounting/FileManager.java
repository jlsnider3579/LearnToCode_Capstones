package com.pluralsight.accounting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.pluralsight.accounting.Main.ledger;

public class FileManager {
    static final String csvFileName = "./src/main/resources/transaction.csv";

    // Reads from transaction csv and stores it inside ledger
    private static void loadTransactions() throws IOException {
        //File reader is reading the transaction.csv file
        FileReader fileReader = new FileReader(csvFileName);

        //Buffer reader is reading and storing data from the file reader
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //Reading the first line of the transaction.csv but not saving it
        String fileInput = bufferedReader.readLine();


        // reads and goes through the transaction csv until there is nothing left
        while ((fileInput = bufferedReader.readLine()) != null) {

            // Split the line using the pipe
            String[] split = fileInput.split("\\|");

            System.out.println(split.length);


            // Create a new Transaction object by parsing the relevant values from the 'split' array
            Transaction transaction = new Transaction(LocalDate.parse(split[0]), LocalTime.parse(split[1]), split[2], split[3], Double.parseDouble(split[4]));
            ledger.add(transaction);
        }
    }
    //helper method to help us write t0 the csv file
    //aka to save a transaction
    static void saveTransactionToFile(Transaction transaction) {
        StringBuilder sb = new StringBuilder();

        try (FileWriter fw = new FileWriter(csvFileName, true)) {
            // Creating a string for the transaction details to save in the CSV file
            sb.append(transaction.getDate().toString());
            sb.append("|");
            sb.append(transaction.getTime().toString());
            sb.append("|");
            sb.append(transaction.getDescription());
            sb.append("|");
            sb.append(transaction.getVendor());
            sb.append("|");
            sb.append(transaction.getAmount());

            String newEntry = sb.toString();
            fw.write(newEntry);
            fw.write("\n");
            // fw.close();

            ledger.add(transaction);
        } catch (Exception ioExp) {
            ioExp.getLocalizedMessage();
            System.out.println("Error");
        }
    }
}