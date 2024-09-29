package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{

  public AccountRegistrationPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(xpath="//input[@id='input-firstname']")
  WebElement txtFirstname;

  @FindBy(xpath="//input[@id='input-lastname']")
  WebElement txtLastname;

  @FindBy(xpath="//input[@id='input-email']")
  WebElement txtEmail;

  @FindBy(xpath="//input[@id='input-telephone']")
  WebElement txtTelephone;

  @FindBy(xpath="//input[@id='input-password']")
  WebElement txtPassword;

  @FindBy(xpath="//input[@id='input-confirm']")
  WebElement txtConfirmPassword;

  @FindBy(xpath="//input[@name='agree']")
  WebElement ckdPolicy;

  @FindBy(xpath="//input[@value='Continue']")
  WebElement btnContinue;

  @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
  WebElement msgConfirmation;

  public void setFirstName(String fname) {
    txtFirstname.sendKeys(fname);
  }

  public void setLastName(String lname) {
    txtLastname.sendKeys(lname);
  }

  public void setEmail(String email) {
    txtEmail.sendKeys(email);
  }

  public void setTelephone(String telephone) {
    txtTelephone.sendKeys(telephone);
  }

  public void setPassword(String password) {
    txtPassword.sendKeys(password);
  }

  public void setConfirmPassword(String confirm) {
    txtConfirmPassword.sendKeys(confirm);
  }

  public void checkPrivacyPolicy() {
    ckdPolicy.click();
  }

  public void clickContinue() {
    btnContinue.click(); //solution 1

    //  btnContinue.sendKeys(Keys.RETURN);

    //    btnContinue.submit();

    //    Actions action = new Actions(driver);
    //    action.moveToElement(btnContinue).click().perform();

    //    JavascriptExecutor jse = (JavascriptExecutor) driver;
    //    jse.executeScript("arguments[0].click();", btnContinue);

    //    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(10));
    //    wdw.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
  }

  public String getConfirmationMsg() {
    try {
      return (msgConfirmation.getText());
    } catch (Exception e) {
      return e.getMessage();
    }
  }
}
