package test.banking;

import org.junit.Test;
import atm.ATM;
class AccountTest {
	@Test
	void testGetAccount() {
		ATM.getInstance().turnon();
		System.out.println(ATM.getInstance().getResponse());
		//fail("Not yet implemented");
	}

}
