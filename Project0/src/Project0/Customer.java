package Project0;

import java.util.ArrayList;

public class Customer {
	public String username;
	public String password;
	public ArrayList<Account> accounts;
	
	public Customer(String _username, String _password) {
		username = _username;
		password = _password;
		accounts = new ArrayList<Account>();
	}
	
	public void createAccount(String ..._accountNames)
	{
		accounts.add(new Account(_accountNames));
	}
	
	public double withdraw(Account _a, double _amount) {
		if (accounts.contains(_a) && _a.status == AccountStatus.Active && _amount > 0.0d && _a.balance > _amount) {
			_a.balance -= _amount;
			return _a.balance;
		}
		else
			return -1;
	}
	
	public double deposit(Account _a, double _amount) {
		if (accounts.contains(_a) && _a.status == AccountStatus.Active && _amount > 0.0d) {
			_a.balance += _amount;
			return _a.balance;
		}
		else
			return -1;
	}
	
	public double[] transfer(Account _from, Account _to, double _amount) {
		if (accounts.contains(_from) && _from != _to && _from.status == AccountStatus.Active && _to.status == AccountStatus.Active && _amount > 0.0d && _from.balance > _amount)
		{
			_from.balance -= _amount;
			_to.balance += _amount;
			return new double[] { _from.balance, _to.balance} ;
		}
		else
			return null;
	}
}
