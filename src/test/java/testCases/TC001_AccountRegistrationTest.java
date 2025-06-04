package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = {"Regression","Master"})
	void verify_account_registration() {
		logger.info("************Starting test - TC001_AccountRegistrationTest**********");

		try {
			HomePage hp = new HomePage(driver);
			AccountRegistrationPage arp = new AccountRegistrationPage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on My Account link*********");

			hp.clickRegister();
			logger.info("Clicked on Register link*********");

			logger.info("************Providing custormer details**********");

			arp.setFirstName(randomeString());
			arp.setLastName(randomeString());
			arp.setEmail(randomeString() + "@gmail.com");
			arp.setTelephone(randomeNum());
			String password = randomeAlphaNumberic();
			arp.setPassword(password);
			arp.setConfirmPassword(password);
			arp.setPrivacyPolicy();
			arp.clickContinue();

			logger.info("*******Validating expected message********");

			String confirmMsg = arp.getConfirmationMsg();
			if (confirmMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}
			else {
				logger.error("Test failed..");
				logger.debug("Debug logs...");
				Assert.assertTrue(false);
				
			}

			//Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");
		} 
		catch (Exception e) 
		{
			
			Assert.fail();
		}
	}
}
