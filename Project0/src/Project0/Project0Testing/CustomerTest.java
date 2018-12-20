package Project0.Project0Testing;

import org.junit.Assert;
import org.junit.Test;

import Project0.Account;
import Project0.AccountStatus;
import Project0.Customer;

public class CustomerTest {
	@Test
	public void CustomerInstansiate() {
		Customer c = new Customer("user", "pass");
		Assert.assertEquals("user", c.username);
		Assert.assertEquals("pass", c.password);
	}

	@Test
	public void CustomerCreateAccount() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		Assert.assertEquals("me", c.accounts.get(0).accountNames[0]);
	}
	
	@Test
	public void CustomerWithdraw() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Assert.assertEquals(200d, c.withdraw(c.accounts.get(0), 300d), 0.0d);
	}
	
	@Test
	public void CustomerWithdrawNotActive() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).balance = 500d;
		Assert.assertEquals(-1, c.withdraw(c.accounts.get(0), 300d), 0.0d);
	}
	
	@Test
	public void CustomerWithdrawOtherAccount() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Account a = new Account();
		a.status = AccountStatus.Active;
		a.balance = 500d;
		Assert.assertEquals(-1, c.withdraw(a, 300d), 0.0d);
	}
	
	@Test
	public void CustomerWithdrawNegative() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Assert.assertEquals(-1, c.withdraw(c.accounts.get(0), -300d), 0.0d);
	}
	
	@Test
	public void CustomerWithdrawOverdraft() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Assert.assertEquals(-1, c.withdraw(c.accounts.get(0), 600d), 0.0d);
	}
	
	@Test
	public void CustomerDeposit() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		Assert.assertEquals(300d, c.deposit(c.accounts.get(0), 300d), 0.0d);
	}
	
	@Test
	public void CustomerDepositNotActive() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		Assert.assertEquals(-1, c.deposit(c.accounts.get(0), 300d), 0.0d);
	}
	
	@Test
	public void CustomerDepositOtherAccount() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		Account a = new Account();
		a.status = AccountStatus.Active;
		Assert.assertEquals(-1, c.deposit(a, 300d), 0.0d);
	}
	
	@Test
	public void CustomerDepositNegative() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		Assert.assertEquals(-1, c.deposit(c.accounts.get(0), -300d), 0.0d);
	}
	
	@Test
	public void CustomerTransfer() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(new double []{ 200d, 300d }, c.transfer(c.accounts.get(0), to, 300d), 0.0d);
	}
	
	@Test
	public void CustomerTransferSameAccount() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Assert.assertArrayEquals(null, c.transfer(c.accounts.get(0), c.accounts.get(0), 300d), 0.0d);
	}
	
	@Test
	public void CustomerTransferFromNotActive() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, c.transfer(c.accounts.get(0), to, 300d), 0.0d);
	}
	
	@Test
	public void CustomerTransferToNotActive() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Account to = new Account();
		Assert.assertArrayEquals(null, c.transfer(c.accounts.get(0), to, 300d), 0.0d);
	}
	
	@Test
	public void CustomerTransferOtherAccount() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Account from = new Account();
		from.status = AccountStatus.Active;
		from.balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, c.transfer(from, to, 300d), 0.0d);
	}
	
	@Test
	public void CustomerTransferNegative() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, c.transfer(c.accounts.get(0), to, -300d), 0.0d);
	}
	
	@Test
	public void CustomerTransferOverdraft() {
		Customer c = new Customer("user", "pass");
		c.createAccount("me");
		c.accounts.get(0).status = AccountStatus.Active;
		c.accounts.get(0).balance = 500d;
		Account to = new Account();
		to.status = AccountStatus.Active;
		Assert.assertArrayEquals(null, c.transfer(c.accounts.get(0), to, 600d), 0.0d);
	}
}
