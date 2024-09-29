package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

  public static WebDriver driver; // as we create BaseClass object, creates new driver hence static
  public Logger logger; // org.apache.logging.log4j.Logger;
  public Properties prop;

  @BeforeClass(groups = { "sanity", "regression", "datadriven", "master" })
  @Parameters({ "os", "browser" })
  public void setup(String os, String browser) throws IOException {
    // loading config properties
    FileReader file = new FileReader("./src//test//resources//config.properties");
    prop = new Properties();
    prop.load(file);

    logger = LogManager.getLogger(this.getClass()); // org.apache.logging.log4j.LogManager;

    System.out.println(os + " :: " + browser);

    // for Grid
    if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
      System.out.println("REMOTE");

      DesiredCapabilities capabilities = new DesiredCapabilities();
      System.out.println("DesiredCapabilities");

      if (os.equalsIgnoreCase("windows"))
        capabilities.setPlatform(Platform.WIN10);
      else if (os.equalsIgnoreCase("linux"))
        capabilities.setPlatform(Platform.LINUX);
      else if (os.equalsIgnoreCase("mac"))
        capabilities.setPlatform(Platform.MAC);
      else {
        System.out.println("No matching OS");
        return;
      }

      System.out.println("OS");

      switch (browser.toLowerCase()) {
      case "chrome":
        capabilities.setBrowserName("chrome");
        break;
      case "firefox":
        capabilities.setBrowserName("firefox");
        break;
      case "edge":
        capabilities.setBrowserName("MicrosoftEdge"); // MicrosoftEdge should be same
        break;
      default:
        System.out.println("Invalid Browser Name..");
        return;
      }

      System.out.println("Browser");

      driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

      System.out.println("WebDriver :: "+driver);
    }

    // for local
    if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
      System.out.println("LOCAL");
      switch (browser.toLowerCase()) {
      case "chrome":
        driver = new ChromeDriver();
        logger.info("=====CHROME=====");
        break;
      case "edge":
        driver = new EdgeDriver();
        logger.info("=====EDGE=====");
        break;
      case "firefox":
        driver = new FirefoxDriver();
        logger.info("=====FIREFOX=====");
        break;
      default:
        logger.info("=====Invalid Browser Name..=====");
        System.out.println("Invalid Browser Name..");
        return;
      }
    }
    driver.manage().deleteAllCookies();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    driver.get(prop.getProperty("appUrl"));
    driver.manage().window().maximize();
  }

  @AfterClass(groups = { "sanity", "regression", "datadriven", "master" })
  public void tearDown() {
    driver.quit();
  }

  public String randomString() {
    String generatedString = RandomStringUtils.randomAlphabetic(5);
    return generatedString;
  }

  public String randomNumber() {
    String generatedNumber = RandomStringUtils.randomNumeric(10);
    return generatedNumber;
  }

  public String randomAlphaNumber() {
    String generatedAlphaNumber = RandomStringUtils.randomAlphanumeric(5);
    return generatedAlphaNumber;
  }

  public String captureScreen(String tname) {
    String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

    String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
    File targetFile = new File(targetFilePath);

    sourceFile.renameTo(targetFile);

    return targetFilePath;
  }
}
