package com.techelevator;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Logger {


    public Logger() {
    }

    private static PrintWriter writer;

    public void log(String action, double priorBalance, double endingBalance) {
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String formattedDate = formatDate.format(new Date()).toString();
        String message = formattedDate + " " + action + "  $" + priorBalance + " $" + endingBalance;
        File logFile = new File("Log.txt");
        try {
            if (writer == null) {
                writer = new PrintWriter(new FileOutputStream(logFile, true));
            }
            writer.println(message);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateSalesReport(String salesReportText) {
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy_hh:mm:ss_a");
        String formattedDate = formatDate.format(new Date()).toString();
        String fileName = "SalesReports/" + formattedDate + "_report.txt";
        File salesReport = new File(fileName);
        try {
            salesReport.createNewFile();
        } catch (IOException e) {
           System.out.println("Sales report could not be generated");
        }
        try {
            if (writer == null) {
                writer = new PrintWriter(new FileOutputStream(salesReport, true));
            }
            writer.println(salesReportText);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
