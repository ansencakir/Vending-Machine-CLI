package com.techelevator;

public class Item {
    private String productID;
    private String productName;
    private double price;
    private String type;
    private int stock;
    private boolean inStock = true;
    private int numberSold = 0;

    public Item (String productID, String productName, double price, String type) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.type = type;
    }
    public Item (String productID, String productName, double price, String type, int stock) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.type = type;
        this.stock = stock;
    }

    public String dispenseItem () {
        if (type.equals("Chip")) {
            return "Crunch Crunch, Yum!";
        } else if (type.equals("Candy")) {
            return "Munch Munch, Yum!";
        } else if (type.equals("Drink")) {
            return "Glug Glug, Yum!";
        } else if (type.equals("Gum")) {
            return "Chew Chew, Yum!";
        }
        return "";
    }

    public void decreaseStock () {
        stock-=1;
        if (stock<=0) {
            inStock = false;
        }
    }

    public int getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(int numberSold) {
        this.numberSold = numberSold;
    }

    public boolean getInStock() {
        return inStock;
    }
    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
