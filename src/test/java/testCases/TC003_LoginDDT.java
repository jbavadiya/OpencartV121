package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


/*
 * Data is valid - login sucess - exp. result test pass - logout
 * Data is valid - login fail - exp. result test fail
 * Data is invalid - login sucess - exp. result test fail - logout
 * Data is invalid - login fail - exp. result test pass
 * 
 * */
public class TC003_LoginDDT extends BaseClass {

	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="Datadriven")
	
	public void verify_loggedinDDT(String email, String pwd, String exp) throws IOException, InterruptedException {
		
		
		
		logger.info("************Starting test - TC003_LoginDDT**********");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			logger.info("Clicked on Login link*********");

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPw(pwd);
			lp.clickLoginBtn();
			
			MyAccountPage macc = new MyAccountPage(driver);
		 	//Assert.assertEquals(true, macc.isMyAccPageExist(),"Login failed");
		 	boolean tagetPage = macc.isMyAccPageExist();
		 	
		 	/*
		 	 * Data is valid - login sucess - exp. result test pass - logout
		 	 * Data is valid - login fail - exp. result test fail
		 	 * Data is invalid - login sucess - exp. result test fail - logout
		 	 * Data is invalid - login fail - exp. result test pass
		 	 * 
		 	 * */
		 	if (exp.equalsIgnoreCase("Valid")) {
		 		if (tagetPage==true) {
		 			macc.clickLogout();
		 			Assert.assertTrue(true);
				}else
				{
					Assert.assertTrue(false);
				}
				
			}
		 	
		 	if (exp.equalsIgnoreCase("Invalid")) {
		 		if (tagetPage==true) {
		 			macc.clickLogout();
		 			Assert.assertTrue(false);
				}else
				{
					Assert.assertTrue(true);
				}
				
			}
			
		}
		catch(Exception e){
			Assert.fail();
		}
		Thread.sleep(3000);

		logger.info("*******Finished TC003_LoginDDT***************");
	}
}
