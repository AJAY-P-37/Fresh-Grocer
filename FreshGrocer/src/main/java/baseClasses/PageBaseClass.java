package baseClasses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.DateUtil;
import utilities.ReadExcelDataFile;

public class PageBaseClass extends BaseTestClass {

	public PageBaseClass(WebDriver driver) {
		super.driver = driver;
	}

	/****************** OpenApplication ***********************/
	public void openApplication(String environment) {
		try {
			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "url_username_pwd.xlsx");

			String websiteURL = readData.getCellData(environment, "url", 2);

			int count = 0;
			String expectedTitle = "The Fresh Grocer";
			do {

				try {
					driver.get(websiteURL);

					if (expectedTitle.equals(getTitle())) {
						System.out.println("Page Loaded in the attempt no. "
								+ (count + 1));
						break;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				count++;
				if (count == 3) {
					System.out.println("Page did NOT load even after "
							+ (count)
							+ " attempts, for 30 seconds each attempt");
					reportFail("Page did NOT load even after " + (count)
							+ " attempts, for 30 seconds each attempt");
				}
			} while (count <= 3);

		} catch (Exception e) {

			reportFail(e.getMessage());

		}
	}

	/****************** Get Page Title ***********************/
	public String getTitle() {

		String title = null;
		try {
			title = driver.getTitle();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return title;

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
	public By getByLocator(Hashtable<String, String> locator, String locatorKey) {

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

	/****** Scroll the the Web page *********/
	public void scrollToElement(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	/**** Scroll to the Elements in Frame *****/
	public void scrollToView(WebElement element) {

		Coordinates coord = ((Locatable) element).getCoordinates();
		coord.onPage();
		coord.inViewPort();
	}

	/******* Performing JavaScript Executor Click *******/
	public void clickWithJSExecutor(WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", element);
	}

	/****
	 * Wait for CSS Transition
	 *****/
	public void waitForCssTransition(WebElement element, String cssValue) {

		String duration = element.getCssValue(cssValue);
		duration = duration.substring(0, duration.length() - 1);
		System.out.println("Waiting for " + duration
				+ " seconds for CSS transition to complete");
		try {
			Thread.sleep((long) ((1 + Double.valueOf(duration)) * 1000));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**** Wait for Element (Explicit Wait) ****/
	public WebElement waitUntil(String condition, By locator,
			int timeOutInSeconds) {

		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);

			if (condition.equals("visibilityOfElementLocated")) {

				element = wait.until(ExpectedConditions
						.visibilityOfElementLocated(locator));

			} else if (condition.equals("elementToBeClickable")) {

				element = wait.until(ExpectedConditions
						.elementToBeClickable(locator));

			} else if (condition.equals("invisibilityOfElementLocated")) {

				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(locator));

			} else if (condition.equals("presenceOfElementLocated")) {

				element = wait.until(ExpectedConditions
						.presenceOfElementLocated(locator));

			} else if (condition.equals("")) {

			} else if (condition.equals("")) {

			} else if (condition.equals("")) {

			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return element;
	}

}
