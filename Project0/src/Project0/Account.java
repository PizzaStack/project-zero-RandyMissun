package Project0;

public class Account {
	public AccountStatus status = AccountStatus.Pending;
	public String[] accountNames = null;
	public double balance = 0.0d;
	
	public Account(String ..._accountNames)
	{
		accountNames = _accountNames;
	}
}
