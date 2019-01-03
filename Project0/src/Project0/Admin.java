package Project0;

public class Admin extends Employee {
	public void cancelAccount(Account _a) {
		_a.status = AccountStatus.Deactivated;
	}
	
	public double withdraw(Account _a, double _amount) {
		_amount = (double)Math.round(_amount * 100d) / 100d;
		if (_a.status == AccountStatus.Active && _amount > 0.0d && _a.balance > _amount) {
			_a.balance -= _amount;
			return _a.balance;
		}
		else
			return -1;
	}
	
	public double deposit(Account _a, double _amount) {
		_amount = (double)Math.round(_amount * 100d) / 100d;
		if (_a.status == AccountStatus.Active && _amount > 0.0d) {
			_a.balance += _amount;
			return _a.balance;
		}
		else
			return -1;
	}
	
	public double[] transfer(Account _from, Account _to, double _amount) {
		_amount = (double)Math.round(_amount * 100d) / 100d;
		if (_from != _to && _from.status == AccountStatus.Active && _to.status == AccountStatus.Active && _amount > 0.0d && _from.balance > _amount)
		{
			_from.balance -= _amount;
			_to.balance += _amount;
			return new double[] { _from.balance, _to.balance};
		}
		else
			return null;
	}
}
