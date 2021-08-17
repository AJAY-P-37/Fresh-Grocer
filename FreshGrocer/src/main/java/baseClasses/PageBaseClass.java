package baseClasses;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import utilities.DateUtil;
import utilities.ReadExcelDataFile;
import PageClasses.LandingPage;

public class PageBaseClass extends BaseTestClass {

	public PageBaseClass(WebDriver driver) {
		super.driver = driver;
		// super.logger = logger;
	}

	/****************** OpenApplication ***********************/
	public LandingPage openApplication(String environment) {
		try {
			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "url_username_pwd.xlsx");

			String websiteURL = readData.getCellData(environment, "url", 2);
			driver.get(websiteURL);

			System.out.println("Application Opened");

		} catch (TimeoutException e) {

			System.out.println("Page refereshed due to Time Out Exception");
			driver.navigate().refresh();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		LandingPage landingPage = new LandingPage(driver);
		PageFactory.initElements(driver, landingPage);
		return landingPage;
	}

	/****************** Get Page Title ***********************/
	public void getTitle(String expectedTitle) {
		try {
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			reportPass("Actual Title : " + driver.getTitle()
					+ " - equals to Expected Title : " + expectedTitle);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/******* Read the Locators from Excel *********/
	public Hashtable<String, String> readLocators(String sheetName) {
		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir")
						+ "/src/main/resources/TestData/" + "locators.xlsx");
		int locatorsCount = readData.getRowCount(sheetName);
		Hashtable<String, String> locators = new Hashtable<String, String>();
		for (int index = 1; index <= locatorsCount; index++) {
			String locatorsName = readData.getCellData(sheetName,
					"locators name", index + 1);
			String locatorsValue = readData.getCellData(sheetName,
					"locators value", index + 1);
			String locatorsType = readData.getCellData(sheetName,
					"locators type", index + 1);
			
			String locatorsKey = locatorsName + "_" + locatorsType;
			locators.put(locatorsKey, locatorsValue);

		}
		return locators;
	}
	
	/******** Get the By class from the locator name ******/
	public By getByLocator(Hashtable<String, String> locator,String locatorKey) {
		By by = null;
		String temp = locatorKey.toLowerCase();
		String locatorValue = locator.get(locatorKey);
		if (temp.endsWith("_id")) {
			by = By.id(locatorValue);
		} else if (temp.endsWith("_name")) {
			by = By.name(locatorValue);
		} else if (temp.endsWith("_classname")) {
			by = By.className(locatorValue);
		} else if (temp.endsWith("_linktext")) {
			by = By.linkText(locatorValue);
		} else if (temp.endsWith("_partiallinktext")) {
			by = By.partialLinkText(locatorValue);
		} else if (temp.endsWith("xpath")) {
			by = By.xpath(locatorValue);
		} else if (temp.endsWith("_css")) {
			by = By.cssSelector(locatorValue);
		} else {
			reportFail("Invalid Locator from excel " + locatorKey);
		}
		return by;
	}

//	/****************** Identify Element ***********************/
//	public WebElement getElement(String locatorKey) {
//		WebElement element = null;
//
//		try {
//			if (locatorKey.endsWith("_Id")) {
//				element = driver
//						.findElement(By.id(prop.getProperty(locatorKey)));
//				// logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else if (locatorKey.endsWith("_Xpath")) {
//				element = driver.findElement(By.xpath(prop
//						.getProperty(locatorKey)));
//				// /logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else if (locatorKey.endsWith("_ClassName")) {
//				element = driver.findElement(By.className(prop
//						.getProperty(locatorKey)));
//				// logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else if (locatorKey.endsWith("_CSS")) {
//				element = driver.findElement(By.cssSelector(prop
//						.getProperty(locatorKey)));
//				// logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else if (locatorKey.endsWith("_LinkText")) {
//				element = driver.findElement(By.linkText(prop
//						.getProperty(locatorKey)));
//				// logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else if (locatorKey.endsWith("_PartialLinkText")) {
//				element = driver.findElement(By.partialLinkText(prop
//						.getProperty(locatorKey)));
//				// logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else if (locatorKey.endsWith("_Name")) {
//				element = driver.findElement(By.name(prop
//						.getProperty(locatorKey)));
//				// logger.log(Status.INFO, "Locator Identidied : " +
//				// locatorKey);
//			} else {
//				// reportFail("Failing the Testcase, Invalid Locator " +
//				// locatorKey);
//			}
//		} catch (Exception e) {
//
//			// Fail the TestCase and Report the error
//			// reportFail(e.getMessage());
//			e.printStackTrace();
//		}
//
//		return element;
//	}

	

	/****** Scroll the the Web page *********/
	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
	}

	/****************** Reporting Functions ***********************/
	public void reportFail(String reportString) {
		// logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		Assert.assertTrue(true);
		// logger.log(Status.PASS, reportString);
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
			// logger.addScreenCaptureFromPath(
			// System.getProperty("user.dir") + "/test-output/ScreenShots/" +
			// DateUtil.getTimeStamp() + ".png");

		} catch (IOException e) {
			reportFail(e.getMessage());
		}

	}

}
