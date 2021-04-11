import java.util.Scanner;

public class Bank {
	static BankAccount[] accounts = new BankAccount[1]; // This array will point to all the BankAccount
	// objects
	static int noOfAccs; // Keeps track of the total number of accounts
	static int currentIndex = -1; // This variable tells us whether we are pointing to a valid account
	static Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {
		bankMenu();
	}

	// bankMenu runs the main part of the program, until user selects 'Q'
	static void bankMenu() {
		boolean quit = false;
		char userSelection;
		String temp;
		do {
			System.out.println();
			printMenu();
			temp = scnr.nextLine();
			System.out.println();
			if (temp.length() > 0) {
				userSelection = temp.charAt(0);
			} else {
				userSelection = '0';
			}
			switch (userSelection) {
			case 'O': // Open account
				// make sure you have enough space or double size OF accounts array
				// make sure the account number is not a duplicate. Assign array index to
				// account
				// set the current index;
				// increment the number of accounts
				openAcc();
				break;
			case 'D': // Deposit
				// deposit only if currentIndex is not -1. you are depositing into a particular
				// account
				deposit();
				break;
			case 'S': // Select account
				// look for account and if it exists, set currentIndex to it
				selectAcc();
				break;
			case 'C': // Close account
				// if currentindex is not -1 close the account and reset currentIndex
				closeAcc();
				break;
			case 'W': // Withdraw
				// if current index is not -1, withdraw
				withdraw();
				break;
			case 'L': // List accounts
				// traverse through all the accounts and display their information
				listAccounts();
				break;
			case 'Q': // Quit
				quit = true;
				break;
			default:
				System.out.println("Invalid selection, please select a valid option");
				break;
			}
		} while (!quit);
		scnr.close();
	}

	// Print the menu, takes index of currently selected account
	static void printMenu() {
		// display menu
		// if index is not -1 display the account information
		System.out.println(
				"O: Open account\nD: Deposit\nS: Select account\nC: Close account\nW: Withdraw\nL: List all accounts\nQ: Quit\n");
		if (currentIndex >= 0) {
			System.out.print("Current account selected: ");
			accounts[currentIndex].printInfo();
		}
		System.out.print("\nEnter a selection: ");
	}

	static void openAcc() {
		// Grab account number
		// validate for duplicate account number
		// Grab balance
		// create new account and return the object so that the array index can point to
		// the
		// newly created object
		// These are reference types because I want to use null as a sentinel value
		Integer userAccNo = null;
		Double userBal = null;
		// Get the account number, checks for dupes
		while (userAccNo == null) {
			try {
				System.out.print("Please enter a valid account number: ");
				userAccNo = scnr.nextInt();
				for (int i = 0; i < noOfAccs; i++) {
					if (userAccNo == accounts[i].getAcc()) {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				scnr.nextLine(); // Eat the newline if user enters a non-int
				userAccNo = null; // Have to reset userAccNo if its a duplicate
				System.out.println("Error: input was not a number or was a duplicate account ID");
			}
		}
		// Get balance
		while (userBal == null) {
			try {
				System.out.print("\nPlease enter a balance: ");
				userBal = scnr.nextDouble();

			} catch (Exception e) {
				System.out.println("Error: input was not a number");
				scnr.nextLine(); // Eat the newline if user enters a non-double
			}
		}
		scnr.nextLine(); // This is to get rid of the newline for the printmenu
		if (noOfAccs == 0) {
			accounts[0] = new BankAccount(userAccNo, userBal);
			currentIndex = 0;
		} else {
			if (accounts.length == noOfAccs) {
				resize();
			}
			for (int i = 0; i < accounts.length; i++) {
				if (accounts[i] == null) {
					accounts[i] = new BankAccount(userAccNo, userBal);
					currentIndex = i;
				}
			}
		}
		noOfAccs += 1;
	}

	static void deposit() {
		if (currentIndex == -1) {
			System.out.println("Please select an account");
		} else {
			Double userBal = null;
			while (userBal == null) {
				try {
					System.out.print("\nPlease enter an amount to deposit: ");
					userBal = scnr.nextDouble();

				} catch (Exception e) {
					System.out.println("Error: input was not a number");
					scnr.nextLine(); // Eat the newline if user enters a non-double
				}
			}
			if (userBal <= 0) {
				System.out.println("\nPlease use the withdraw function instead");
			}
			accounts[currentIndex].deposit(userBal);
			scnr.nextLine(); // Eat the newline
		}
	}

	static void selectAcc() {
		// ask for the account number, check to see if it exists and return index
		if (noOfAccs == 0) {
			System.out.println("No accounts detected, Input an account first");
		} else {
			Integer userAccNo = null;
			while (userAccNo == null) {
				try {
					System.out.print("Please enter a valid account number: ");
					userAccNo = scnr.nextInt();
				} catch (Exception e) {
					scnr.nextLine(); // Eat the newline if user enters a non-int
					System.out.println("Error: input was not a number");
				}
			}
			scnr.nextLine(); // Eat the newline
			boolean foundAcc = false;
			for (int i = 0; i < noOfAccs; i++) {
				if (accounts[i] != null && userAccNo == accounts[i].getAcc()) {
					currentIndex = i;
					foundAcc = true;
				}
			}
			if (!foundAcc) {
				System.out.println("Didn't find an account with that account number");
			}
		}

	}

	static void closeAcc() {
		// move last account to the index that needs to be deleted
		// set last account to null
		// decrement noOfAccts variable
		Integer userAccNo = null;
		while (userAccNo == null) {
			try {
				System.out.print("Please enter a valid account number: ");
				userAccNo = scnr.nextInt();
			} catch (Exception e) {
				scnr.nextLine(); // Eat the newline if user enters a non-int
				System.out.println("Error: input was not a number");
			}
		}
		boolean foundAcc = false;
		for (int i = 0; i < noOfAccs; i++) {
			if (accounts[i] != null && accounts[i].getAcc() == userAccNo) {
				foundAcc = true;
				accounts[i] = accounts[noOfAccs - 1];
				accounts[noOfAccs - 1] = null;
				noOfAccs--;
				currentIndex = -1;
				System.out.println("Successfully deleted account ID " + userAccNo);
			}
		}
		if (!foundAcc) {
			System.out.println("Didn't find an account with that account number");
		}
		scnr.nextLine();	// Eat the newline
	}

	static void withdraw() {
		if (currentIndex == -1) {
			System.out.println("Please select an account");
		} else {
			Double userBal = null;
			while (userBal == null) {
				try {
					System.out.print("\nPlease enter an amount to deposit: ");
					userBal = scnr.nextDouble();

				} catch (Exception e) {
					System.out.println("Error: input was not a number");
					scnr.nextLine(); // Eat the newline if user enters a non-double
				}
			}
			if (userBal >= 0) {
				System.out.println("\nPlease use the withdraw function instead");
			}
			accounts[currentIndex].withdraw(userBal);
			scnr.nextLine(); // Eat the newline
		}
	}

	static void listAccounts() {
		// Go through all the accounts using a for loop and display their content
		if (noOfAccs == 0) {
			System.out.println("No accounts entered");
		}
		for (int i = 0; i < noOfAccs; i++) {
			if (accounts[i] == null) {
				break;
			}
			System.out.print(1 + i + ")\t");
			accounts[i].printInfo();
		}
	}

	static void resize() {
		// resize array. Double the size
		BankAccount[] temp = new BankAccount[noOfAccs * 2];
		System.out.println(temp.length);
		for (int i = 0; i < noOfAccs; i++) {
			temp[i] = accounts[i];
		}
		accounts = new BankAccount[noOfAccs * 2];
		for (int i = 0; i < noOfAccs; i++) {
			accounts[i] = temp[i];
		}
	}
}

class BankAccount {
	private int accNbr = -1;
	private double balance = -1;

	BankAccount(int accNbr, double balance) {
		// set instance variables
		this.accNbr = accNbr;
		this.balance = balance;
	}

	int getAcc() {
		// return accountNumber
		return accNbr;
	}

	double getBalance() {
		// return balance
		return balance;
	}

	void deposit(double deposit) {
		// add to deposit
		this.balance += deposit;
	}

	void withdraw(double withdraw) {
		// withdraw as long as there is still $1 in the account
		if (Math.abs(balance - withdraw) >= 1.001) {
			balance -= withdraw;
		} else {
			System.out.println("You are withdrawing too much: try again");
		}
	}

	void printInfo() {
		System.out.format("Account ID: %d    Balance: $%.2f\n", this.accNbr, this.balance);
	}
}
