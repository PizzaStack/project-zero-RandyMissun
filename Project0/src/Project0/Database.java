package Project0;

import java.io.FileInputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
	public Connection connection;

	public Database() {
		try {
			Class.forName("org.postgresql.Driver");
			Properties properties = new Properties();
			properties.load(new FileInputStream("connection.properties"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("pass"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertAccount(Account _a) {
		try {
			Statement s = connection.createStatement();
			ResultSet count = s.executeQuery("SELECT COUNT(id)+1 AS count FROM accounts");
			count.next();
			_a.id = count.getInt("count");

			PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?)");
			Array names = connection.createArrayOf("VARCHAR", _a.accountNames);
			statement.setInt(1, _a.id);
			statement.setArray(2, names);
			statement.setString(3, _a.status.toString());
			statement.setDouble(4, _a.balance);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateAccount(Account _a) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE accounts SET customers=?, status=?, balance=? where id=?");
			Array names = connection.createArrayOf("VARCHAR", _a.accountNames);
			statement.setArray(1, names);
			statement.setString(2, _a.status.toString());
			statement.setDouble(3, _a.balance);
			statement.setInt(4, _a.id);
			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Account retrieveAccount(int _id) {
		try {
			Statement statement = connection.createStatement();
			ResultSet accounts = statement.executeQuery("SELECT * FROM accounts WHERE id=" + _id);
			if (!accounts.isBeforeFirst()) {
				return null;
			}
			Account a = new Account();
			while (accounts.next()) {
				a.accountNames = ((String[]) accounts.getArray("customers").getArray());
				a.id = accounts.getInt("id");
				a.status = AccountStatus.valueOf(accounts.getString("status"));
				a.balance = accounts.getDouble("balance");
			}
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Account> retrieveAllAccounts() {
		try {
			Statement statement = connection.createStatement();
			ResultSet accounts = statement.executeQuery("SELECT * FROM accounts");
			if (!accounts.isBeforeFirst()) {
				return null;
			}
			List<Account> result = new ArrayList<Account>();
			while (accounts.next()) {
				Account a = new Account((String[]) accounts.getArray("customers").getArray());
				a.id = accounts.getInt("id");
				a.status = AccountStatus.valueOf(accounts.getString("status"));
				a.balance = accounts.getDouble("balance");
				result.add(a);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insertCustomer(Customer _c) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO customers VALUES (?, ?, ?)");
			Array accounts = connection.createArrayOf("INTEGER", _c.accounts.toArray());
			statement.setString(1, _c.username);
			statement.setString(2, _c.password);
			statement.setArray(3, accounts);
			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Customer retrieveCustomer(String _name) {
		try {
			Statement statement = connection.createStatement();
			ResultSet customers = statement.executeQuery(
					"SELECT * FROM customers LEFT JOIN accounts ON accounts.id = ANY (customers.accounts) WHERE username = '" + _name + "'");
			if (!customers.isBeforeFirst()) {
				return null;
			}
			List<Customer> result = new ArrayList<Customer>();
			while (customers.next()) {
				Customer c = new Customer(customers.getString("username"), customers.getString("password"));
				Integer[] ids = (Integer[]) customers.getArray("accounts").getArray();
				for (Integer id : ids) {
					Account a = new Account((String[]) customers.getArray("customers").getArray());
					a.id = id;
					a.status = AccountStatus.valueOf(customers.getString("status"));
					a.balance = customers.getDouble("balance");
					c.accounts.add(a);
				}
				boolean isNotInList = true;
				for (Customer inList : result) {
					if (c.username.equals(inList.username)) {
						isNotInList = false;
					}
				}
				if (isNotInList) {
					result.add(c);
				}
			}
			return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateCustomer(Customer _c) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE customers SET accounts=?, password=? where username=?");
			Integer[] accountIds = new Integer[_c.accounts.size()];
			for (int i = 0; i < accountIds.length; i++) {
				accountIds[i] = _c.accounts.get(i).id;
			}
			Array accounts = connection.createArrayOf("INTEGER", accountIds);
			statement.setArray(1, accounts);
			statement.setString(2, _c.password);
			statement.setString(3, _c.username);
			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Customer> retrieveAllCustomers() {
		try {
			Statement statement = connection.createStatement();
			ResultSet customers = statement.executeQuery(
					"SELECT * FROM customers LEFT JOIN accounts ON accounts.id = ANY (customers.accounts)");
			if (!customers.isBeforeFirst()) {
				return null;
			}
			List<Customer> result = new ArrayList<Customer>();
			while (customers.next()) {
				Customer c = new Customer(customers.getString("username"), customers.getString("password"));
				Integer[] ids = (Integer[]) customers.getArray("accounts").getArray();
				for (Integer id : ids) {
					Account a = new Account((String[]) customers.getArray("customers").getArray());
					a.id = id;
					a.status = AccountStatus.valueOf(customers.getString("status"));
					a.balance = customers.getDouble("balance");
					c.accounts.add(a);
				}
				boolean isNotInList = true;
				for (Customer inList : result) {
					if (c.username.equals(inList.username)) {
						isNotInList = false;
					}
				}
				if (isNotInList) {
					result.add(c);
				}
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
