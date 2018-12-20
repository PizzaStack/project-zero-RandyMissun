package Project0.Project0Testing;

import org.junit.Assert;
import org.junit.Test;

import Project0.Account;
import Project0.AccountStatus;
import Project0.Admin;

public class AdminTest {
	@Test
	public void AdminCancelAccount() {
		Admin a = new Admin();
		Account acc = new Account();
		a.cancelAccount(acc);
		Assert.assertEquals(AccountStatus.Deactivated, acc.status);
	}
	
	@Test
	public void AdminWithdraw() {
		Admin a = new Admin();
		Account acc = new Account();
		acc.status = AccountStatus.Active;
		acc.balance = 500d;
		Assert.assertEquals(200d, a.withdraw(acc, 300d), 0.0d);
	}
	
	@Test
	public void AdminWithdrawNotActive() {
		Admin a = new Admin();
		Account acc = new Account();
		acc.balance = 500d;
		Assert.assertEquals(-1, a.withdraw(acc, 300d), 0.0d);
	}
	
	@Test
	public void AdminWithdrawNegative() {
		Admin a = new Admin();
		Account acc = new Account();
		acc.status = AccountStatus.Active;
		acc.balance = 500d;
		Assert.assertEquals(-1, a.withdraw(acc, -300d), 0.0d);
	}
	
	@Test
	public void AdminWithdrawOverdraft() {
		Admin a = new Admin();
		Account acc = new Account();
		acc.status = AccountStatus.Active;
		acc.balance = 500d;
		Assert.assertEquals(-1, a.withdraw(acc, 600d), 0.0d);
	}
	
	@Test
	public void AdminDeposit() {
		Admin a = new Admin();
		Account acc = new Account();
		acc.status = AccountStatus.Active;
		Assert.assertEquals(300d, a.deposit(acc, 300d), 0.0d);
	}
	
	@Test
	public void AdminDepositNotActive() {
		Admin a = new Admin();
		Account acc = new Account();
		Assert.assertEquals(-1, a.deposit(acc, 300d), 0.0d);
	}
	
	@Test
	public void AdminDepositNegative() {
		Admin a = new Admin();
		Account acc = new Account();
		acc.status = AccountStatus.Active;
		Assert.assertEquals(-1, a.deposit(acc, -300d), 0.0d);
	}

	@Test
	public void AdminTransfer() {
		Admin a = new Admin();
		Account from = new Account();
		from.status = AccountStatus.Active;
		from.balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(new double []{ 200d, 300d }, a.transfer(from, to, 300d), 0.0d);
	}
	
	@Test
	public void AdminTransferSameAccount() {
		Admin a = new Admin();
		Account from = new Account();
		from.status = AccountStatus.Active;
		from.balance = 500d;
		Assert.assertArrayEquals(null, a.transfer(from, from, 300d), 0.0d);
	}
	
	@Test
	public void AdminTransferFromNotActive() {
		Admin a = new Admin();
		Account from = new Account();
		from.balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, a.transfer(from, to, 300d), 0.0d);
	}
	
	@Test
	public void AdminTransferToNotActive() {
		Admin a = new Admin();
		Account from = new Account();
		from.status = AccountStatus.Active;
		from.balance = 500d;
		Account to = new Account();
		Assert.assertArrayEquals(null, a.transfer(from, to, 300d), 0.0d);
	}
	
	@Test
	public void AdminTransferNegative() {
		Admin a = new Admin();
		Account from = new Account();
		from.status = AccountStatus.Active;
		from.balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, a.transfer(from, to, -300d), 0.0d);
	}
	
	@Test
	public void AdminTransferOverdraft() {
		Admin a = new Admin();
		Account from = new Account();
		from.status = AccountStatus.Active;
		from.balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, a.transfer(from, to, 600d), 0.0d);
	}
}
