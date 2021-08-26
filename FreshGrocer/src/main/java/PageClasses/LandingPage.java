package PageClasses;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import baseClasses.PageBaseClass;

public class LandingPage extends PageBaseClass {

	Hashtable<String, String> locators;

	public By signInBtn;
	public By popUpBtn;
	public By loginToLoadBtn;
	public By accountHeaderBtn;
	public By signInOrRegisterText;
	public By myAccountText;
	public By digitalCouponsLink;
	public By couponsFrame;
	public By loadingCouponsText;

	public By signOutBtn;

	public LandingPage(WebDriver driver) {
		super(driver);
		locators = readLocators("landing_page");
		assignValuesForLocatorsFromExcel();
	}

	/****** Assign values to the By locators **********/
	public void assignValuesForLocatorsFromExcel() {
		signInBtn = getByLocator(locators, ("signInBtn_xpath"));
		popUpBtn = getByLocator(locators, ("popUpBtn_xpath"));
		loginToLoadBtn = getByLocator(locators, ("loginToLoadBtn_classname"));
		accountHeaderBtn = getByLocator(locators, ("accountHeaderBtn_xpath"));
		signInOrRegisterText = getByLocator(locators,
				("signInOrRegisterText_xpath"));
		myAccountText = getByLocator(locators, ("myAccountText_xpath"));
		digitalCouponsLink = getByLocator(locators,
				("digitalCouponsLink_xpath"));
		couponsFrame = getByLocator(locators, ("couponsFrame_id"));
		loadingCouponsText = getByLocator(locators,
				("loadingCouponsText_xpath"));

		signOutBtn = getByLocator(locators, "signOutBtn_id");
	}

	/********* Read All the Object Locators from Excel ********/
	public void loadAllLocators() {
		locators = readLocators("landing_page");
	}

	/************** Get Login To Load Text ****************/
	public void getLoginToLoadText(String expectedText) {

		try {
			String actualText = driver.findElement(loginToLoadBtn).getText();
			Assert.assertEquals(actualText, expectedText);
			reportPass("Actual Text : " + actualText
					+ " - equals to Expected Text : " + expectedText);
			System.out.println("Coupon Button Text is " + expectedText);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/********* Close the PopUp id exists **************/
	public void closePopUp() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(popUpBtn));
			driver.findElement(popUpBtn).click();
			System.out.println("PopUp Closed");
		} catch (TimeoutException e) {
			System.out.println("Pop did not exists");
		} catch (NoSuchElementException e) {
			System.out.println("Pop did not exists");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Check if Sign In is present before Signing In ********/
	public void checkIfSignInIsPresent() {
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(accountHeaderBtn));

			String expectedText = "Sign In or Register";
			String actualText = driver.findElement(signInOrRegisterText)
					.getText();
			Assert.assertEquals(actualText, expectedText);
			System.out.println("Sign In button is Present");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Click in the SignIn Button ************/
	public void clickSignIn() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
			driver.findElement(signInBtn).click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/********* Check if My Account is present after Signing In ********/
	public void checkIfMyAccountIsPresent() {
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(myAccountText));
			String myAccounText = driver.findElement(myAccountText).getText();
			System.out.println(myAccounText + " text is present");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*********** Click Digital Coupons Link **********/
	public void clickDigitalCouponsButton() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(digitalCouponsLink));
			driver.findElement(digitalCouponsLink).click();
			System.out.println("Digital Coupons Clicked");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****
	 * Wait for Switching to coupons frame and for Loading Coupons... to
	 * disappear
	 *****/
	public void waitForFrameToLoadOrDoRefresh() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(couponsFrame));

			System.out.println("Switched to the Frame");

			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(loadingCouponsText));

			System.out
					.println("Loading Coupons... is invisible and the coupons are being visible");

		} catch (NoSuchElementException e) {
			try {
				driver.navigate().refresh();
				System.out
						.println("Page refreshes because the Coupons frame was loading more than 90 seconds");
				driver.findElement(digitalCouponsLink).click();

				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(couponsFrame));
				System.out.println("Switched to the Frame");

				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(loadingCouponsText));
			} catch (Exception e1) {
				reportFail(e1.getMessage());
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Clicking the Account Header (My Account) Button *******/
	public void clickAccountHeaderButton() {

		driver.findElement(accountHeaderBtn).click();
		System.out.println("Acoount Header Button Clicked");
	}

	/****** Click Sign Out Button *******/
	public void clickSignOutButton() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(signOutBtn));

		driver.findElement(signOutBtn).click();
		System.out.println("Successfully Signed Out");

	}
}
