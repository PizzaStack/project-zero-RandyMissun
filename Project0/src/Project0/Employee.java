package Project0;

public class Employee {
	public String viewCustomerUsername(Customer _c) {
		return _c.username;
	}
	
	public String viewCustomerPassword(Customer _c) {
		return _c.password;
	}
	
	public AccountStatus viewAccountStatus(Account _a) {
		return _a.status;
	}
	
	public double viewAccountBalance(Account _a) {
		return _a.balance;
	}
	
	public String[] viewAccountUsernames(Account _a) {
		return _a.accountNames;
	}
	
	public void approveAccount(Account _a) {
		_a.status = AccountStatus.Active;
	}
	
	public void denyAccount(Account _a) {
		_a.status = AccountStatus.Denied;
	}
}