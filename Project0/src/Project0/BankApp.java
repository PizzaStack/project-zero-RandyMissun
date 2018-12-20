package Project0;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class BankApp {

	public static void main(String[] args) {
		Customer customer = null;
		Employee employee = null;
		Admin admin = null;
		Scanner scanner = new Scanner(System.in);

		try {
			Class.forName("org.postgresql.Driver");
			Properties properties = new Properties();
			properties.load(new FileInputStream("connection.properties"));
			Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("pass"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (mainMenu(scanner)) {
		case "1":
			customerSignIn(customer);
			break;
		case "2":
			customerRegister(customer);
			break;
		case "3":
			employee = new Employee();
			employeeOptions(employee);
			break;
		case "4":
			admin = new Admin();
			adminOptions(admin);
			break;
		}

		System.out.println("System end");
		scanner.close();
	}

	public static String mainMenu(Scanner _sc) {
		System.out.println("Welcome to Bank App. What would you like to do?");
		System.out.println(
				"1) Sign in as an existing customer\t2) Register as a new customer\t3) Sign in as an employee\t4)Sign in as an admin");
		String input = _sc.nextLine();
		boolean notValidOption = !input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4");
		while (notValidOption) {
			System.out.println("Please select a valid option.");
			input = _sc.nextLine();
		}
		return input;
	}

	public static void customerSignIn(Customer _c) {

	}

	public static void customerRegister(Customer _c) {

	}

	public static void employeeOptions(Employee _e) {
		System.out.println("Signed in as Employee. What would you like to do?");
	}

	public static void adminOptions(Admin _a) {
		System.out.println("Signed in as Admin. What would you like to do?");
	}
}
