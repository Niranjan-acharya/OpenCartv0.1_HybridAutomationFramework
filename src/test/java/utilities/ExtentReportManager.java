package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
  public ExtentSparkReporter sparkReporter; // look and feel
  public ExtentReports extent;
  public ExtentTest test; // result

  String repName;

  @Override
  public void onStart(ITestContext testContext) {
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
    repName = "Test-Report-" + timeStamp + ".html";

    sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report
    sparkReporter.config().setDocumentTitle("OpenCart Automation Project"); // Title of report
    sparkReporter.config().setReportName("OpenCart Functional testing"); // name of the report
    sparkReporter.config().setTheme(Theme.DARK);

    extent = new ExtentReports();
    System.out.println("EXTENT :: " + extent);
    extent.attachReporter(sparkReporter);
    extent.setSystemInfo("Application", "OpenCart");
    extent.setSystemInfo("Module", "Admin");
    extent.setSystemInfo("SubModule", "Customers");
    extent.setSystemInfo("Operating System", System.getProperty("os.name"));
    extent.setSystemInfo("User name", System.getProperty("user.name"));
    extent.setSystemInfo("Environement", "QA");
    extent.setSystemInfo("user", "niranjan");

    String os = testContext.getCurrentXmlTest().getParameter("os"); // get os from testng parameter
    extent.setSystemInfo("Operating System", "os");

    String browser = testContext.getCurrentXmlTest().getParameter("browser"); // get browser from testng parameter
    extent.setSystemInfo("Browser", "browser");

    // get <include> from testng parameter
    List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
    if (!includedGroups.isEmpty()) {
      extent.setSystemInfo("Groups", includedGroups.toString());
    }
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    test = extent.createTest(result.getTestClass().getName());
    test.assignCategory(result.getMethod().getGroups()); // to display groups in report
    test.createNode(result.getName());
    test.log(Status.PASS, result.getName() + " got successfully executed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    test = extent.createTest(result.getTestClass().getName());
    test.assignCategory(result.getMethod().getGroups()); // to display groups in report

    test.log(Status.FAIL, result.getName() + " got failed");
    test.log(Status.INFO, result.getThrowable().getMessage());

    try {
      String imgPath = new BaseClass().captureScreen(result.getName());
      test.addScreenCaptureFromPath(imgPath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    test = extent.createTest(result.getTestClass().getName());
    test.assignCategory(result.getMethod().getGroups());

    test.log(Status.SKIP, result.getName() + " got Skipped");
    test.log(Status.INFO, result.getThrowable().getMessage());

    try {
      String imgPath = new BaseClass().captureScreen(result.getName());
      test.addScreenCaptureFromPath(imgPath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onFinish(ITestContext context) {
    extent.flush(); // to write the result in the report

    // to open the generated report automatically
    String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
    File extentReport = new File(pathOfExtentReport);

    try {
      Desktop.getDesktop().browse(extentReport.toURI());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // to send report to email automatically
    /*
     * try { URL url = new URL("file:///" + System.getProperty("user.dir") +
     * "\\reports" + repName);
     *
     * // Create email message ImageHtmlEmail email = new ImageHtmlEmail();
     * email.setDataSourceResolver(new DataSourceUrlResolver(url));
     * email.setHostName("smtp.googleemail.com"); email.setSmtpPort(465);
     * email.setAuthenticator(new
     * DefaultAuthenticator("niranjanacharya09@gmail.com", "FerrariDeadpool@09"));
     * email.setSSLOnConnect(true); email.setFrom("niranjanacharya09@gmail.com"); //
     * sender email.setSubject("Test Results");
     * email.setMsg("Please find Attached Report.");
     * email.addTo("niranjanacharya171096@gmail.com"); // receiver email.attach(url,
     * "extent report", "please check report."); email.send(); //send the email }
     * catch (Exception e) { e.printStackTrace(); }
     */
  }
}
