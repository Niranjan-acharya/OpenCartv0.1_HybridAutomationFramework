package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest  extends BaseClass {
  @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="datadriven")
  public void verify_loginDDT(String email, String password, String res) {
    logger.info("****** Starting TC003_LoginDataDrivenTest *******");
    try {
      // HomePage
      HomePage hp = new HomePage(driver);
      hp.clickMyAccount();
      hp.clickLogin();

      // LoginPage
      LoginPage lp = new LoginPage(driver);
      lp.setEmail(email);
      lp.setPassword(password);
      lp.clickLogin();

      // MyAccountPage
      MyAccountPage map = new MyAccountPage(driver);
      boolean targetPage = map.isMyAccountPageExists();

      /*Data is valid --> logged in success - test pass - logout
       *              --> logged in failed - test fail
       */
      if(res.equalsIgnoreCase("Valid")) {
        if(targetPage == true) {
          map.clickLogout();
          Assert.assertTrue(true);
        } else {
          Assert.assertTrue(false);
        }
      }

      /*Data is Invalid --> logged in success - test fail - logout
       *              --> logged in failed - test pass
       */
      if(res.equalsIgnoreCase("Invalid")) {
        if(targetPage == true) {
          map.clickLogout();
          Assert.assertTrue(false);
        } else {
          Assert.assertTrue(true);
        }
      }
    } catch (Exception e) {
      Assert.fail();
    }
    logger.info("****** Finished TC003_LoginDataDrivenTest *******");
  }
}
