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
	public By myAccountSettingsLink;
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

		myAccountSettingsLink = getByLocator(locators,
				"myAccountSettingsLink_xpath");
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

			System.out.println(e.getMessage());
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

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/********* Check if Sign In is present before Signing In ********/
	public boolean checkIfSignInIsPresent() {

		boolean present = false;
		try {

			int count = 0;
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
										+ (count + 1));
						break;
					}
				} catch (Exception e) {
					System.out
							.println("Page did NOT load properly. Refreshing");

					refreshPage();

				}
				count++;
				if (count == 3) {
					System.out
							.println("Sign In or Register Text did NOT load even after "
									+ (count)
									+ " attempts, for 30 seconds each attempt");
					reportFail("Sign In or Register Text did NOT load even after "
							+ (count)
							+ " attempts, for 30 seconds each attempt");
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
			int count = 0, maxAttempts = 3;
			do {
				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement signIn = wait.until(ExpectedConditions
						.elementToBeClickable(signInBtn));

				signIn.click();
				System.out.println("Success: Sign in Button is clicked");

				try {
					wait.until(ExpectedConditions.invisibilityOf(signIn));
					System.out.println("Page Loaded in  attempt no."
							+ (count + 1) + ". SignIn Button is Invisible");
					break;
				} catch (Exception e) {
					System.out.println("Page NOT Loaded in  attempt no. "
							+ (count + 1) + ". Clicking again");
				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Page NOT Loaded even after " + count
							+ " attempts for 20 seconds in each attempt");
					reportFail("Page NOT Loaded even after " + count
							+ " attempts for 20 seconds in each attempt");
				}
			} while (count <= maxAttempts);
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/********* Check if My Account is present after Signing In ********/
	public boolean checkIfMyAccountIsPresent() {

		boolean isPresent = false;
		try {
			int count = 0;
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
								+ " text is present in attempt no. "
								+ (count + 1));
						break;
					}

				} catch (Exception e) {
					System.out
							.println("Page did NOT load properly. Refreshing");
					refreshPage();
				}
				count++;
				if (count == 3) {
					System.out
							.println("My Account Text did NOT load even after "
									+ (count)
									+ " attempts, for 30 seconds each attempt");
					reportFail("My Account Text did NOT load even after "
							+ (count)
							+ " attempts, for 30 seconds each attempt");
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

			int count = 0, maxAttempts = 3;
			do {
				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement digitalCoupons = wait.until(ExpectedConditions
						.visibilityOfElementLocated(digitalCouponsLink));

				digitalCoupons.click();
				System.out.println("Success: digitalCoupons Button is clicked");

				try {
					wait.until(ExpectedConditions
							.presenceOfElementLocated(couponsFrame));

					System.out.println("Page Loaded in  attempt no."
							+ (count + 1) + ". Coupons Frame available");
					break;
				} catch (Exception e) {
					System.out.println("Page NOT Loaded in  attempt no. "
							+ (count + 1) + ". Clicking again");
				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Page NOT Loaded even after " + count
							+ " attempts for 20 seconds in each attempt");
					reportFail("Page NOT Loaded even after " + count
							+ " attempts for 20 seconds in each attempt");
				}
			} while (count <= maxAttempts);

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
			int count = 0;
			do {

				try {
					WebDriverWait wait = new WebDriverWait(driver, 30);
					wait.until(ExpectedConditions
							.frameToBeAvailableAndSwitchToIt(couponsFrame));

					System.out
							.println("Success: Switched to the Frame in attempt no. "
									+ (count + 1));

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(loadingCouponsText));
					System.out
							.println("Success: Loading Coupons... is invisible and the coupons are being visible in attempt no. "
									+ (count + 1));
					break;

				} catch (Exception e) {
					System.out
							.println("Frame did NOT load properly. Refreshing");
					refreshPage();
				}
				count++;
				if (count == 3) {
					System.out
							.println("Digital Coupons Frame did NOT load even after "
									+ (count)
									+ " attempts, for 30 seconds each attempt");
					reportFail("Digital Coupons Frame did NOT load even after "
							+ (count)
							+ " attempts, for 30 seconds each attempt");
				}
			} while (count <= 3);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/********* Clicking the Account Header (My Account) Button *******/
	public void clickAccountHeaderButton() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement accountHeader = wait.until(ExpectedConditions
					.elementToBeClickable(accountHeaderBtn));

			int maxAttempts = 3;
			int count = 0;
			do {

				try {

					accountHeader.click();
					System.out
							.println("Success: Account Header Button Clicked");

					wait = new WebDriverWait(driver, 10);
					WebElement myAccountHeaderContect = wait
							.until(ExpectedConditions
									.visibilityOfElementLocated(myAccountSettingsLink));
					System.out
							.println("Account Header content is Visible in attempt no."
									+ (count + 1));
					break;

				} catch (Exception e) {
					System.out
							.println("Account Header content is NOT Visible. Clicking again");
				}
				count++;
				if (count == maxAttempts) {

					System.out
							.println("Account Header content is NOT Visible even after "
									+ count + "attempts. Clicking again");

				}
			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/******* Clicking the My Account Setting *******/
	public void clickMyAccountSettingLink() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement myAccountSettings = wait.until(ExpectedConditions
						.elementToBeClickable(myAccountSettingsLink));

				myAccountSettings.click();
				System.out
						.println("Success: My Account Settings link clicked Successfully");

				try {
					wait.until(ExpectedConditions
							.invisibilityOf(myAccountSettings));
					System.out.println("Page Loaded in  attempt no."
							+ (count + 1)
							+ ". myAccountSettings Button is Invisible");
					break;
					
				} catch (Exception e) {
					System.out.println("Page NOT Loaded in  attempt no. "
							+ (count + 1) + ". Clicking again");
				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Page NOT Loaded even after " + count
							+ " attempts for 20 seconds in each attempt");
					reportFail("Page NOT Loaded even after " + count
							+ " attempts for 20 seconds in each attempt");
				}
			} while (count <= maxAttempts);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/****** Click Sign Out Button *******/
	public void clickSignOutButton() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement signOut = wait.until(ExpectedConditions
					.elementToBeClickable(signOutBtn));

			signOut.click();
			System.out.println("Success: Successfully Signed Out");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}
}
