package Project0.Project0Testing;

import org.junit.Assert;
import org.junit.Test;

import Project0.Account;

public class AccountTest {
	@Test
	public void AccountInstantiate() {
		Account acc = new Account("me");
		Assert.assertEquals("me", acc.accountNames[0]);
	}
	
	@Test
	public void JointAccountInstantiate() {
		String[] names = new String[] { "me", "other" };
		Account acc = new Account(names);
		Assert.assertArrayEquals(names, acc.accountNames);
	}
}
