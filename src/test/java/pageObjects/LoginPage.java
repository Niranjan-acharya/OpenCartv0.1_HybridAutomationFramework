package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

  public LoginPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(xpath="//input[@id='input-email']")
  WebElement txtEmail;

  @FindBy(xpath="//input[@id='input-password']")
  WebElement txtPassword;

  @FindBy(xpath="//input[@value='Login']")
  WebElement btnLogin;

  @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
  WebElement msgConfirmation;

  public void setEmail(String email) {
    txtEmail.sendKeys(email);
  }

  public void setPassword(String password) {
    txtPassword.sendKeys(password);
  }

  public void clickLogin() {
    btnLogin.click(); //solution 1

    //  btnLogin.sendKeys(Keys.RETURN);

    //    btnLogin.submit();

    //    Actions action = new Actions(driver);
    //    action.moveToElement(btnLogin).click().perform();

    //    JavascriptExecutor jse = (JavascriptExecutor) driver;
    //    jse.executeScript("arguments[0].click();", btnLogin);

    //    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(10));
    //    wdw.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
  }
}
