package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

  @Test(groups={"regression","master"})
  public void verify_account_registration() {
    logger.info("******Starting TC001_AccountRegistrationTest******");

    try {
      //HomePage
      HomePage hp = new HomePage(driver);
      hp.clickMyAccount();
      logger.info("Clicked on MyAccount Link");
      hp.clickRegister();
      logger.info("Clicked on Register Link");

      //AccountRegistrationPage
      AccountRegistrationPage arp = new AccountRegistrationPage(driver);
      logger.info("Providing Customer Details");

      arp.setFirstName(randomString().toUpperCase());
      arp.setLastName(randomString().toUpperCase());
      arp.setEmail(randomString()+"@gmail.com");
      arp.setTelephone(randomNumber());

      String password = randomAlphaNumber();
      arp.setPassword(password);
      arp.setConfirmPassword(password);

      arp.checkPrivacyPolicy();
      arp.clickContinue();

      logger.info("Validating Expected Message");
      String msg = arp.getConfirmationMsg();

      if(msg.equals("Your Account Has Been Created!")) {
        Assert.assertTrue(true, "Passed");
      } else {
        logger.error("Test failed......");
        logger.debug("Debug logs.....");
        Assert.assertTrue(false, "Failed");
      }
      //      Assert.assertEquals(msg, "Your Account Has Been Created!!");
    } catch(Exception e) {
      System.out.println("Failed");
      Assert.fail();
    }
    logger.info("******Finished TC001_AccountRegistrationTest******");
  }
}