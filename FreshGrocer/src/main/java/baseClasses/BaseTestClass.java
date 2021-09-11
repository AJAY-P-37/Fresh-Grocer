package baseClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import utilities.DateUtil;

public class BaseTestClass {

	public WebDriver driver;

	/****************** Invoke Browser ***********************/
	public void invokeBrowser(String browserName) {

		try {

			if (browserName.equalsIgnoreCase("chrome")) {

				System.setProperty(
						"webdriver.chrome.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				// options.setExperimentalOption("excludeSwitches",
				// Collections.singletonList("enable-automation"));
				// options.setExperimentalOption("useAutomationExtension",
				// false);

				// options.addArguments("headless");

				driver = new ChromeDriver(options);
			} else if (browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/geckodriver.exe");

				System.setProperty(
						FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,
						"true");
				System.setProperty(
						FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
						"/dev/null");
				// FirefoxOptions options = new FirefoxOptions();
				// options.setHeadless(true);
				driver = new FirefoxDriver();

			} else if (browserName.equalsIgnoreCase("Opera")) {
				System.setProperty("webdriver.opera.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/operadriver");
				driver = new OperaDriver();

			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty(
						"webdriver.ie.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			} else if (browserName.equals("edge")) {
				System.setProperty(
						"webdriver.edge.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/msedgedriver85.exe");

				// EdgeOptions edgeOptions = new EdgeOptions();
				// edgeOptions.addArguments("headless");

				driver = new EdgeDriver();

			} else if (browserName.equals("safari")) {
				driver = new SafariDriver();

			}
			System.out.println("Application Opened");

			// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			// reportFail(e.getMessage());
			System.out.println(e.getMessage());
		}

	}

	/******* Closing the Web driver *********/
	// @AfterClass
	public void flushReports() {
		// report.flush();
		driver.close();
		System.out.println("Application Closed");
	}

	/************** Switch to New Tab *************/
	public void switchToNewTab() {
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		String mainTab = windowHandles.next();
		String newTab = windowHandles.next();

		driver.switchTo().window(newTab);
		System.out.println("Success: Switched to New Tab");
	}

	/************** Switch to Main Tab *************/
	public void switchToMainTab() {
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		String mainTab = windowHandles.next();

		driver.switchTo().window(mainTab);
		System.out.println("Success: Switched to Main Tab");
	}

	/******* Switch to Parent Frame ********/
	public void switchToDefaultFrame() {

		driver.switchTo().defaultContent();

		System.out.println("Success: Switched to Default frame");
	}

	/****** Do a refresh *****/
	public void refreshPage() {

		driver.navigate().refresh();
	}
	
	/****************** Reporting Functions ***********************/
	public void reportFail(String reportString) {

		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		Assert.assertTrue(true);
	}

	/****************** Capture Screen Shot ***********************/
	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFolder = new File(System.getProperty("user.dir")
				+ "/ScreenShots");
		destFolder.mkdir();
		File destFile = new File(System.getProperty("user.dir")
				+ "/ScreenShots/" + DateUtil.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);

		} catch (IOException e) {
			reportFail(e.getMessage());
		}
	}

	/********* Reading files with Prefix ************/
	public String readFileWithPrefix(String dirPath, String fileNamePrefix) {

		File dir = new File(dirPath);

		File[] foundFiles = dir.listFiles();
		List<String> matchedFiles = new ArrayList<String>();
		String path = "";
		for (File file : foundFiles) {
			if (file.getName().startsWith(fileNamePrefix + " ")) {
				matchedFiles.add(file.getAbsolutePath());
			}
		}

		if (matchedFiles.size() > 1) {
			System.out.println("More than one file found for the prefix "
					+ fileNamePrefix + " in directory " + dirPath);
			reportFail("More than one file found for the prefix "
					+ fileNamePrefix + " in directory " + dirPath);

		} else if (matchedFiles.size() == 0) {
			System.out.println("No matched file found for Prefix "
					+ fileNamePrefix + " in directory " + dirPath);
			reportFail("No matched file found for Prefix " + fileNamePrefix
					+ " in directory " + dirPath);

		} else if (matchedFiles.size() == 1) {
			path = matchedFiles.get(0);
			System.out.println("Success: File prefix matched with " + path);
		}

		return path;
	}

	/*********** Updating Name of the Files *************/
	public void renameFileWithDateTime(String path) {

		File oldFile = new File(path);

		String[] fileName = path.split(" ");

		String newPath = fileName[0] + " " + DateUtil.getTimeStamp() + ".xslx";
		File newFile = new File(newPath);

		if (oldFile.renameTo(newFile)) {
			System.out.println("Success: File renamed to " + newPath);
		} else {
			System.out.println("Sorry! the" + path
					+ "can't be renamed. Error oocured");
			reportFail("Sorry! the" + path + "can't be renamed. Error oocured");
		}
	}

	/***************** Wait Functions in Framework *****************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js
					.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(1);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js
					.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(long i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
