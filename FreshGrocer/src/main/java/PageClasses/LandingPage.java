package PageClasses;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
			WebElement popUp = wait.until(ExpectedConditions
					.visibilityOfElementLocated(popUpBtn));
			popUp.click();
			System.out.println("Success: PopUp Closed");
		} catch (TimeoutException e) {
			System.out.println("Pop did not exists");
		} catch (NoSuchElementException e) {
			System.out.println("Pop did not exists");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Check if Sign In is present before Signing In ********/
	public boolean checkIfSignInIsPresent() {

		boolean present = false;
		try {

			int count = 1;
			do {
				String expectedText = "Sign In or Register";
				String actualText = null;

				try {
					WebDriverWait wait = new WebDriverWait(driver, 30);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(accountHeaderBtn));

					actualText = driver.findElement(signInOrRegisterText)
							.getText();

					if (actualText.equals(expectedText)) {

						Assert.assertEquals(actualText, expectedText);
						present = true;
						System.out
								.println("Success: Sign In button is Present in attempt no. "
										+ count);
						break;
					}
				} catch (Exception e) {
					refreshPage();

				}
				count++;
				if (count == 3) {
					System.out
							.println("Sign In or Register Text did NOT load even after "
									+ count
									+ " attempts, for 30 seconds each attempt");
					reportFail("Sign In or Register Text did NOT load even after "
							+ count + " attempts, for 30 seconds each attempt");
				}
			} while (count <= 3);

		} catch (Exception e) {
			present = false;
		}
		return present;
	}

	/********* Click in the SignIn Button ************/
	public void clickSignIn() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
			driver.findElement(signInBtn).click();
			System.out.println("Success: Sign in Button is clicked");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/********* Check if My Account is present after Signing In ********/
	public boolean checkIfMyAccountIsPresent() {

		boolean isPresent = false;
		try {
			int count = 1;
			do {
				String expectedText = "My Account";
				String actualText = null;

				try {

					WebDriverWait wait = new WebDriverWait(driver, 30);
					WebElement myAccountElement = wait.until(ExpectedConditions
							.visibilityOfElementLocated(myAccountText));
					actualText = myAccountElement.getText();

					if (actualText.equals(expectedText)) {
						Assert.assertEquals(actualText, expectedText);
						isPresent = true;
						System.out.println("Success: " + actualText
								+ " text is present in attempt no. " + count);
						break;
					}

				} catch (Exception e) {
					refreshPage();
				}
				count++;
				if (count == 3) {
					System.out
							.println("My Account Text did NOT load even after "
									+ count
									+ " attempts, for 30 seconds each attempt");
					reportFail("My Account Text did NOT load even after "
							+ count + " attempts, for 30 seconds each attempt");
				}
			} while (count <= 3);

		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}

	/*********** Click Digital Coupons Link **********/
	public void clickDigitalCouponsButton() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(digitalCouponsLink));
			driver.findElement(digitalCouponsLink).click();
			System.out.println("Success: Digital Coupons Clicked");

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
			int count = 1;
			do {

				try {
					WebDriverWait wait = new WebDriverWait(driver, 30);
					wait.until(ExpectedConditions
							.frameToBeAvailableAndSwitchToIt(couponsFrame));

					System.out
							.println("Success: Switched to the Frame in attempt no. "
									+ count);

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(loadingCouponsText));
					System.out
							.println("Success: Loading Coupons... is invisible and the coupons are being visiblein attempt no. "
									+ count);
					break;

				} catch (Exception e) {
					refreshPage();
				}
				count++;
				if (count == 3) {
					System.out
							.println("Digital Coupons Frame did NOT load even after "
									+ count
									+ " attempts, for 30 seconds each attempt");
					reportFail("Digital Coupons Frame did NOT load even after "
							+ count + " attempts, for 30 seconds each attempt");
				}
			} while (count <= 3);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Clicking the Account Header (My Account) Button *******/
	public void clickAccountHeaderButton() {

		driver.findElement(accountHeaderBtn).click();
		System.out.println("Success: Account Header Button Clicked");
	}

	/****** Click Sign Out Button *******/
	public void clickSignOutButton() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(signOutBtn));

		driver.findElement(signOutBtn).click();
		System.out.println("Success: Successfully Signed Out");

	}
}
