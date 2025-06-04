package testCases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	
	@Test(groups= {"Sanity","Master"})
	void verify_loggedin() throws IOException {
		
		
		
		logger.info("************Starting test - TC002_LoginTest**********");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			logger.info("Clicked on Login link*********");

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPw(p.getProperty("password"));
			lp.clickLoginBtn();
			
			MyAccountPage map = new MyAccountPage(driver);
		 	Assert.assertEquals(true, map.isMyAccPageExist(),"Login failed");
		 	
		 	map.clickLogout();
			
		} 
		catch (Exception e) 
		{
			
			Assert.fail();
		}
	 	logger.info("*******Finished login test***************");

	}
}
