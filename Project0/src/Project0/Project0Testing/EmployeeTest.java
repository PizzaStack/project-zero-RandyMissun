package Project0.Project0Testing;

import org.junit.Assert;
import org.junit.Test;

import Project0.Account;
import Project0.AccountStatus;
import Project0.Customer;
import Project0.Employee;

public class EmployeeTest {
	@Test
	public void EmployeeViewCustomerUsername() {
		Employee e = new Employee();
		Customer c = new Customer("user", "pass");
		Assert.assertEquals("user", e.viewCustomerUsername(c));
	}
	
	@Test
	public void EmployeeViewCustomerPassword() {
		Employee e = new Employee();
		Customer c = new Customer("user", "pass");
		Assert.assertEquals("pass", e.viewCustomerPassword(c));
	}
	
	@Test
	public void EmployeeViewAccountStatus() {
		Employee e = new Employee();
		Account a = new Account();
		Assert.assertEquals(AccountStatus.Pending, e.viewAccountStatus(a));
	}
	
	@Test
	public void EmployeeViewAccountBalance() {
		Employee e = new Employee();
		Account a = new Account();
		Assert.assertEquals(0.0d, e.viewAccountBalance(a), 0.0d);
	}
	
	@Test
	public void EmployeeViewAccountUsernames() {
		Employee e = new Employee();
		Account a = new Account();
		String[] result = {};
		Assert.assertArrayEquals(result, e.viewAccountUsernames(a));
	}
	
	@Test
	public void EmployeeApproveAccount() {
		Employee e = new Employee();
		Account a = new Account();
		e.approveAccount(a);
		Assert.assertEquals(AccountStatus.Active, a.status);
	}

	@Test
	public void EmployeeDenyAccount() {
		Employee e = new Employee();
		Account a = new Account();
		e.denyAccount(a);
		Assert.assertEquals(AccountStatus.Denied, a.status);
	}
}
