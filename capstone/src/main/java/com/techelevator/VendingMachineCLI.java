package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Print Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private static Inventory inventory = new Inventory();
	private Menu menu;
	private Logger logger;

	private double balance = 0.00;
	private String currentMoneyProvided = "Current Money Provided $" + balance;


	public static String getMainMenuOptionSalesReport() {
		return MAIN_MENU_OPTION_SALES_REPORT;
	}

	public VendingMachineCLI(Menu menu, Logger logger) {
		this.menu = menu;
		this.logger = logger;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				System.out.println(inventory.displayInventoryItems());
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchaseMenuRun();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				// break out of loop
				System.out.println("Thank you. Have a good day.");
				break;
			} else if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				// write sales report
				logger.generateSalesReport(inventory.salesReportOutput());
				System.out.println("Sales report generated.");
			}
		}
	}

	public void purchaseMenuRun() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			double previousBalance = (balance);

			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				// allow customer to feed money in
				System.out.println("Enter whole dollar amount: ");
				String userInput = menu.getIn().nextLine();
				if (isNumeric(userInput)) {
					double moneyIn = Double.parseDouble(userInput);
					balance += moneyIn;
					logger.log("FEED MONEY:", moneyIn, balance);
				}
			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				// list products and allow customer to select products
				System.out.println("Enter product code to select product");
				System.out.println(inventory.itemListForPurchaseMenu());
				String userInput = menu.getIn().nextLine();
				Item requestedItem = inventory.getItemByID(userInput);
					if (requestedItem == null) {
						System.out.println("Product ID does not exist");
					} else if (requestedItem.getInStock()) {
						requestedItem.decreaseStock();
						requestedItem.setNumberSold(requestedItem.getNumberSold() + 1);
						balance -= requestedItem.getPrice();
						System.out.println(requestedItem.getProductName() + " " + requestedItem.getPrice() + " " + "Remaining Balance: $" + balance);
						System.out.println(requestedItem.dispenseItem());
						String action = requestedItem.getProductName() + " " + requestedItem.getProductID();
						logger.log(action, previousBalance, balance);
					} else {
						System.out.println("Sorry, this item is out of stock");
					}
			} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				// increment coins until balance is zero. Dispense coins to user
				System.out.println(finishTransaction());
				logger.log("GIVE CHANGE:", previousBalance, balance);
				break;
				}
			System.out.println("Current Money Provided: $" + balance);
			}
		}

	private String finishTransaction () {
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		while (balance > 0) {
			quarters = (int) ((balance*100) / 25);
			balance -= quarters * .25;
			dimes = (int) ((balance*100) / 10);
			balance -= dimes * .10;
			nickels = (int) ((balance*100) / 5);
			balance -= nickels * .05;
		}
		int [] changeDue = {quarters, dimes, nickels};
		String [] coins = {"quarter(s)", "dime(s)", "nickel(s)"};
		String changeToCustomer = "Here is your change:";
		for(int i =0; i< changeDue.length; i++) {
			if (changeDue[i] != 0) {
				changeToCustomer += " " + changeDue[i] + " " + coins[i];
			}
		}
		return changeToCustomer;
	}
	private static boolean isNumeric(String str){
		return str != null && str.matches("[0-9.]+");
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		Logger logger = new Logger();
		VendingMachineCLI cli = new VendingMachineCLI(menu, logger);
		//Restock Vending Machine
		inventory.restockVendingMachine();


		cli.run();
	}


}
