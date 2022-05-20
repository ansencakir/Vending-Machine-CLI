package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Inventory {
    private List<Item> stockedItems = new ArrayList<>();

    public Inventory () {}

    public void addItem (Item item) {
        stockedItems.add(item);
    }

    public List<Item> getStockedItems() {
        return stockedItems;
    }

    public void restockVendingMachine() {
        File newStock = new File("vendingmachine.csv");
        try (Scanner reader = new Scanner(newStock)) {
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String [] itemParameters = line.split("\\|");
                String productID = itemParameters[0];
                String productName = itemParameters[1];
                double price = Double.parseDouble(itemParameters[2]);
                String type = itemParameters[3];
                Item item = new Item(productID, productName, price, type, 5);
                addItem(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public String itemListForPurchaseMenu() {
        String output = "";
        for(Item item : stockedItems) {
            output += item.getProductID() + " " + item.getProductName() + " $" + item.getPrice() + "\n";
        }
        return output;
    }
    public String displayInventoryItems() {
        String output = "";
        for(Item item : stockedItems) {
            output += item.getProductID() + " " + item.getProductName() + " $" + item.getPrice() + "\n";
            output += item.getInStock() ? item.getStock() + " in stock" : "SOLD OUT";
            output += "\n" + "\n";
        }
        return output;
    }
    public String salesReportOutput() {
        String output = "";
        for(Item item : stockedItems) {
            output += item.getProductName() + "\\|" + item.getNumberSold() + "\n";
        }
        return output;
    }
    public Item getItemByID (String productID) {
        for (Item item : stockedItems) {
            if (item.getProductID().equals(productID)) {
                return item;
            }
        }
        return null;
    }
}
