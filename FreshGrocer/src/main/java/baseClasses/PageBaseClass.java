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

}
