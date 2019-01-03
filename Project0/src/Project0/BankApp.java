package Project0;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankApp {

	public static void main(String[] args) {
		mainMenu();
	}

	public static void mainMenu() {
		Scanner sc = new Scanner(System.in);
		Database db = new Database();

		System.out.println("Welcome to Bank App. What would you like to do?");
		System.out.println(
				"1) Sign in as an existing customer\n2) Register as a new customer\n3) Sign in as an employee\n4) Sign in as an admin\n5) Exit");
		String input = sc.nextLine();
		boolean notValidOption = !input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
				&& !input.equals("5");
		while (notValidOption) {
			System.out.println("Please select a valid option.");
			input = sc.nextLine();
		}

		switch (input) {
		case "1":
			customerSignIn(sc, db);
			break;
		case "2":
			customerRegister(sc, db);
			break;
		case "3":
			employeeOptions(sc, db);
			break;
		case "4":
			adminOptions(sc, db);
			break;
		case "5":
			break;
		}

		db.close();
		sc.close();
	}

	public static void customerSignIn(Scanner _sc, Database _db) {
		System.out.println("Enter your username");
		String user = _sc.nextLine();
		System.out.println("Enter your password");
		String pass = _sc.nextLine();
		Customer c = _db.retrieveCustomer(user);
		if (c.equals(null)) {
			System.out.println("No username found. Press enter to return to main menu");
			_sc.nextLine();
			mainMenu();
		}
		while (pass != c.password) {
			System.out.println("Wrong password. Try again.");
			pass = _sc.nextLine();
		}
		customerOptions(_sc, _db, c);
	}

	public static void customerRegister(Scanner _sc, Database _db) {
		System.out.println("Enter a username");
		String user = _sc.nextLine();
		System.out.println("Enter a password");
		String pass = _sc.nextLine();
		Customer c = _db.retrieveCustomer(user);
		while (!c.equals(null)) {
			System.out.println("Username already taken. Enter a new username.");
			user = _sc.nextLine();
			c = _db.retrieveCustomer(user);
		}
		c = new Customer(user, pass);
		_db.insertCustomer(c);
		System.out.println("Would you like to apply for an account? y/n");
		String input = _sc.nextLine();
		while (!input.equals("y") || !input.equals("n")) {
			System.out.println("Please select a valid option.");
			input = _sc.nextLine();
		}
		if (input.equals("y")) {
			accountApplication(_sc, _db, c);
		} else {
			customerOptions(_sc, _db, c);
		}
	}

	public static void accountApplication(Scanner _sc, Database _db, Customer _c) {
		System.out.println(
				"Enter more names and press enter to add multiple names to the account. Leave input blank and press enter to finalize application.");
		String input = _sc.nextLine();
		List<String> names = new ArrayList<String>();
		names.add(_c.username);
		while (!input.equals("")) {
			if (_db.retrieveCustomer(input) == null) {
				System.out.println("Username not found. Only registered customers allowed.");
			} else {
				names.add(input);
			}
			input = _sc.nextLine();
		}
		Account a = new Account((String[]) names.toArray());
		int id = _db.insertAccount(a);
		a.id = id;
		_c.accounts.add(a);
		_db.updateCustomer(_c);
		System.out.println("");
		customerOptions(_sc, _db, _c);
	}

	public static void customerOptions(Scanner _sc, Database _db, Customer _c) {
		System.out.println("Signed in as " + _c.username + ". What would you like to do?");
		System.out.println(
				"1) Apply for an account\n2) Withdraw from an account\n3) Deposit into an account\n4) Transfer between accounts\n5) Return to main menu");
		String input = _sc.nextLine();
		boolean notValidOption = !input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
				&& !input.equals("5");
		while (notValidOption) {
			System.out.println("Please select a valid option.");
			input = _sc.nextLine();
		}

		boolean notValidInput = true;
		Account a = new Account();
		double balance;
		switch (input) {
		case "1":
			accountApplication(_sc, _db, _c);
			break;
		case "2":
			displayActiveAccounts(_sc, _c);
			System.out.println("Enter the ID of the account you want to withdraw from.");
			input = _sc.nextLine();
			do {
				for (int i = 0; i < _c.accounts.size(); i++) {
					if (input.equals(_c.accounts.get(i).id + "")) {
						notValidInput = false;
						a = _c.accounts.get(i);
						break;
					}
				}
				if (notValidInput) {
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}
			} while (notValidInput);
			System.out.println("How much would you like to withdraw?");
			input = _sc.nextLine();
			balance = _c.withdraw(a, Double.parseDouble(input));
			if (balance == -1) {
				System.out.println("Withdrawl failed.");
			} else {
				_db.updateAccount(a);
				_db.updateCustomer(_c);
				System.out.println("Withdrawl successful. Remaining balance: " + balance);
			}
			customerOptions(_sc, _db, _c);
			break;
		case "3":
			displayActiveAccounts(_sc, _c);
			System.out.println("Enter the ID of the account you want to deposit into.");
			input = _sc.nextLine();
			notValidInput = true;
			a = new Account();
			do {
				for (int i = 0; i < _c.accounts.size(); i++) {
					if (input.equals(_c.accounts.get(i).id + "")) {
						notValidInput = false;
						a = _c.accounts.get(i);
						break;
					}
				}
				if (notValidInput) {
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}
			} while (notValidInput);
			System.out.println("How much would you like to deposit?");
			input = _sc.nextLine();
			balance = _c.deposit(a, Double.parseDouble(input));
			if (balance == -1) {
				System.out.println("Deposit failed.");
			} else {
				_db.updateAccount(a);
				_db.updateCustomer(_c);
				System.out.println("Deposit successful. New balance: " + balance);
			}
			customerOptions(_sc, _db, _c);
			break;
		case "4":
			displayActiveAccounts(_sc, _c);
			System.out.println("Enter the ID of the account you want to transfer from.");
			input = _sc.nextLine();
			notValidInput = true;
			a = new Account();
			do {
				for (int i = 0; i < _c.accounts.size(); i++) {
					if (input.equals(_c.accounts.get(i).id + "")) {
						notValidInput = false;
						a = _c.accounts.get(i);
						break;
					}
				}
				if (notValidInput) {
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}
			} while (notValidInput);
			System.out.println("Enter the ID of the account you want to transfer to.");
			Account to = _db.retrieveAccount(Integer.parseInt(input));
			while (to == null) {
				System.out.println("Please enter a valid ID");
				input = _sc.nextLine();
				to = _db.retrieveAccount(Integer.parseInt(input));
			}

			System.out.println("How much would you like to transfer?");
			input = _sc.nextLine();
			double[] b = _c.transfer(a, to, Double.parseDouble(input));
			if (b == null) {
				System.out.println("Transfer failed.");
			} else {
				_db.updateAccount(a);
				_db.updateAccount(to);
				_db.updateCustomer(_c);
				System.out.println("Transfer successful. New balances: " + b[0] + ", " + b[1]);
			}
			customerOptions(_sc, _db, _c);
			break;
		case "5":
			mainMenu();
			break;
		}
	}

	public static void displayActiveAccounts(Scanner _sc, Customer _c) {
		for (int i = 0; i < _c.accounts.size(); i++) {
			Account a = _c.accounts.get(i);
			if (a.status == AccountStatus.Active) {
				System.out.print("ID: " + a.id + "\tNames on account: ");
				for (int j = 0; j < a.accountNames.length; j++) {
					System.out.print(a.accountNames[j] + ", ");
				}
				System.out.println("\tBalance: " + (double) Math.round(a.balance * 100d) / 100d);
			}
		}
	}

	public static void employeeOptions(Scanner _sc, Database _db) {
		Employee e = new Employee();
		System.out.println("Signed in as Employee. What would you like to do?");
		System.out.println(
				"1) View account information\n2) View customer information\n3) Approve or deny pending account applications\n4) Return to main menu");
		String input = _sc.nextLine();
		boolean notValidOption = !input.equals("1") && !input.equals("2") && !input.equals("3");
		while (notValidOption) {
			System.out.println("Please select a valid option.");
			input = _sc.nextLine();
		}

		List<Account> accounts;
		List<Customer> customers;
		switch (input) {
		case "1":
			accounts = _db.retrieveAllAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				Account a = accounts.get(i);
				System.out.print("ID: " + a.id + "\tNames on account: ");
				for (int j = 0; j < a.accountNames.length; j++) {
					System.out.print(a.accountNames[j] + ", ");
				}
				System.out.println(
						"\tBalance: " + ((double) Math.round(a.balance * 100d) / 100d) + "\tStatus: " + a.status);
			}
			employeeOptions(_sc, _db);
			break;
		case "2":
			customers = _db.retrieveAllCustomers();
			for (int i = 0; i < customers.size(); i++) {
				Customer c = customers.get(i);
				System.out.print("Username: " + c.username + "\tPassword: " + c.password + "\tAccounts by ID");
				for (int j = 0; j < c.accounts.size(); j++) {
					System.out.print(c.accounts.get(j).id + ", ");
				}
				System.out.println();
			}
			employeeOptions(_sc, _db);
			break;
		case "3":
			accounts = _db.retrieveAllAccounts();
			for (int i = 0; i < accounts.size(); i++) {
				if (accounts.get(i).status == AccountStatus.Pending) {
					System.out.print("ID: " + accounts.get(i).id + "\tNames on account: ");
					for (int j = 0; j < accounts.get(i).accountNames.length; j++) {
						System.out.print(accounts.get(i).accountNames[j] + ", ");
					}
					System.out.println();
				} else {
					accounts.remove(i);
				}
			}
			System.out.println(
					"Enter the ID of the account you want to approve or deny. Leave input blank and press enter to go back to options.");
			input = _sc.nextLine();
			while (!input.equals("")) {
				for (int i = 0; i < accounts.size(); i++) {
					if (input.equals(accounts.get(i).id + "")) {
						System.out.println("Approve or deny this account? a/d");
						input = _sc.nextLine();
						while (!input.equals("a") || !input.equals("d")) {
							System.out.println("Please select a valid option.");
							input = _sc.nextLine();
						}
						if (input.equals("a")) {
							e.approveAccount(accounts.get(i));
						} else {
							e.denyAccount(accounts.get(i));
						}
						_db.updateAccount(accounts.get(i));
						System.out.println("Account updated.");
					}
				}
				input = _sc.nextLine();
			}
			employeeOptions(_sc, _db);
			break;
		case "4":
			mainMenu();
			break;
		}
	}

	public static void adminOptions(Scanner _sc, Database _db) {
		Admin a = new Admin();
		System.out.println("Signed in as Admin. What would you like to do?");
		System.out.println(
				"1) View or edit account information\n2) View customer information\n3) Return to main menu");
		String input = _sc.nextLine();
		boolean notValidOption = !input.equals("1") && !input.equals("2") && !input.equals("3");
		while (notValidOption) {
			System.out.println("Please select a valid option.");
			input = _sc.nextLine();
		}

		List<Account> accounts = new ArrayList<Account>();
		List<Customer> customers = new ArrayList<Customer>();
		Account acc = new Account();
		double balance;
		boolean notValidId;
		switch (input) {
		case "1":
			System.out.println("Select an option to manage accounts.");
			System.out.println(
					"1) View account information\n2) Change account status\n3) Withdraw from an account\n4) Deposit into an account\n5) Transfer between accounts\n6) Return to options");
			input = _sc.nextLine();
			notValidOption = !input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4")
					&& !input.equals("5") && !input.equals("6");
			while (notValidOption) {
				System.out.println("Please select a valid option.");
				input = _sc.nextLine();
			}
			switch (input) {
			case "1":
				accounts = _db.retrieveAllAccounts();
				for (int i = 0; i < accounts.size(); i++) {
					acc = accounts.get(i);
					System.out.print("ID: " + acc.id + "\tNames on account: ");
					for (int j = 0; j < acc.accountNames.length; j++) {
						System.out.print(acc.accountNames[j] + ", ");
					}
					System.out.println("\tBalance: " + ((double) Math.round(acc.balance * 100d) / 100d) + "\tStatus: "
							+ acc.status);
				}
				adminOptions(_sc, _db);
				break;
			case "2":
				accounts = _db.retrieveAllAccounts();
				for (int i = 0; i < accounts.size(); i++) {
					System.out.print("ID: " + accounts.get(i).id + "\tNames on account: ");
					for (int j = 0; j < accounts.get(i).accountNames.length; j++) {
						System.out.print(accounts.get(i).accountNames[j] + ", ");
						System.out.println();
					}
				}
				System.out.println(
						"Enter the ID of the account you want to approve, deny, or cancel. Leave input blank and press enter to go back to options.");
				input = _sc.nextLine();
				while (!input.equals("")) {
					for (int i = 0; i < accounts.size(); i++) {
						if (input.equals(accounts.get(i).id + "")) {
							System.out.println("Approve or deny this account? a/d/c");
							input = _sc.nextLine();
							while (!input.equals("a") || !input.equals("d") || !input.equals("c")) {
								System.out.println("Please select a valid option.");
								input = _sc.nextLine();
							}
							if (input.equals("a")) {
								a.approveAccount(accounts.get(i));
							} else if (input.equals("d")) {
								a.denyAccount(accounts.get(i));
							} else {
								a.cancelAccount(accounts.get(i));
							}
							_db.updateAccount(accounts.get(i));
							System.out.println("Account updated.");
						}
					}
					input = _sc.nextLine();
				}
				employeeOptions(_sc, _db);
				break;
			case "3":
				accounts = _db.retrieveAllAccounts();
				System.out.println("Enter the ID of the account you want to withdraw from.");
				input = _sc.nextLine();
				notValidId = true;
				while (notValidId) {
					for (int i = 0; i < accounts.size(); i++) {
						if (input.equals(accounts.get(i).id + "")) {
							acc = accounts.get(i);
							notValidId = false;
						}
					}
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}
				System.out.println("How much would you like to withdraw?");
				input = _sc.nextLine();
				balance = a.withdraw(acc, Double.parseDouble(input));
				if (balance == -1) {
					System.out.println("Withdrawl failed.");
				} else {
					_db.updateAccount(acc);
					System.out.println("Withdrawl successful. Remaining balance: " + balance);
				}
				adminOptions(_sc, _db);
				break;
			case "4":
				accounts = _db.retrieveAllAccounts();
				System.out.println("Enter the ID of the account you want to deposit into.");
				input = _sc.nextLine();
				notValidId = true;
				while (notValidId) {
					for (int i = 0; i < accounts.size(); i++) {
						if (input.equals(accounts.get(i).id + "")) {
							acc = accounts.get(i);
							notValidId = false;
						}
					}
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}
				System.out.println("How much would you like to deposit?");
				input = _sc.nextLine();
				balance = a.deposit(acc, Double.parseDouble(input));
				if (balance == -1) {
					System.out.println("Deposit failed.");
				} else {
					_db.updateAccount(acc);
					System.out.println("Deposit successful. New balance: " + balance);
				}
				adminOptions(_sc, _db);
				break;
			case "5":
				accounts = _db.retrieveAllAccounts();
				System.out.println("Enter the ID of the account you want to transfer from.");
				input = _sc.nextLine();
				notValidId = true;
				while (notValidId) {
					for (int i = 0; i < accounts.size(); i++) {
						if (input.equals(accounts.get(i).id + "")) {
							acc = accounts.get(i);
							notValidId = false;
						}
					}
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}
				System.out.println("Enter the ID of the account you want to transfer to.");
				Account to = new Account();
				input = _sc.nextLine();
				notValidId = true;
				while (notValidId) {
					for (int i = 0; i < accounts.size(); i++) {
						if (input.equals(accounts.get(i).id + "")) {
							to = accounts.get(i);
							notValidId = false;
						}
					}
					System.out.println("Please select a valid option.");
					input = _sc.nextLine();
				}

				System.out.println("How much would you like to transfer?");
				input = _sc.nextLine();
				double[] b = a.transfer(acc, to, Double.parseDouble(input));
				if (b == null) {
					System.out.println("Transfer failed.");
				} else {
					_db.updateAccount(acc);
					_db.updateAccount(to);
					System.out.println("Transfer successful. New balances: " + b[0] + ", " + b[1]);
				}
				adminOptions(_sc, _db);
				break;
			case "6":
				adminOptions(_sc, _db);
				break;
			}
			break;
		case "2":
			customers = _db.retrieveAllCustomers();
			for (int i = 0; i < customers.size(); i++) {
				Customer c = customers.get(i);
				System.out.print("Username: " + c.username + "\tPassword: " + c.password + "\tAccounts by ID");
				for (int j = 0; j < c.accounts.size(); j++) {
					System.out.print(c.accounts.get(j).id + ", ");
				}
				System.out.println();
			}
			adminOptions(_sc, _db);
			break;
		case "3":
			mainMenu();
			break;
		}
	}
}
