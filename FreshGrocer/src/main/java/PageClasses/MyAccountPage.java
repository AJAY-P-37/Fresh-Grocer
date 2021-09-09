package PageClasses;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.RandomUtil;
import baseClasses.PageBaseClass;

public class MyAccountPage extends PageBaseClass {

	Hashtable<String, String> locators;

	public By homeLink;
	public By myAccountTitle;
	public By myProfileEditBtn;
	public By firstNameTextBox;
	public By firstNameValidationText;
	public By lastNameTextBox;
	public By lastNameValidationText;
	public By primaryPhoneTextBox;
	public By primaryPhoneCheckBox;
	public By alternatePhoneTextBox;
	public By alternatePhoneCheckBox;
	public By preferredContactTypeDropdown;
	public By stateDropdown;
	public By homeStoreDropdown;
	public By updateProfileBtn;
	public By profileValidationText;

	public MyAccountPage(WebDriver driver) {

		super(driver);
		locators = readLocators("my_account_page");
		assignValuesForLocatorsFromExcel();
	}

	public void assignValuesForLocatorsFromExcel() {

		homeLink = getByLocator(locators, "homeLink_xpath");
		myAccountTitle = getByLocator(locators, "myAccountTitle_xpath");
		myProfileEditBtn = getByLocator(locators, "myProfileEditBtn_id");
		firstNameTextBox = getByLocator(locators, "firstNameTextBox_id");
		firstNameValidationText = getByLocator(locators,
				"firstNameValidationText_xpath");
		lastNameTextBox = getByLocator(locators, "lastNameTextBox_id");
		lastNameValidationText = getByLocator(locators,
				"lastNameValidationText_xpath");
		primaryPhoneTextBox = getByLocator(locators, "primaryPhoneTextBox_id");
		primaryPhoneCheckBox = getByLocator(locators, "primaryPhoneCheckBox_id");
		alternatePhoneTextBox = getByLocator(locators,
				"alternatePhoneTextBox_id");
		alternatePhoneCheckBox = getByLocator(locators,
				"alternatePhoneCheckBox_id");
		preferredContactTypeDropdown = getByLocator(locators,
				"preferredContactTypeDropdown_id");
		stateDropdown = getByLocator(locators, "stateDropdown_id");
		homeStoreDropdown = getByLocator(locators, "homeStoreDropdown_id");
		updateProfileBtn = getByLocator(locators, "updateProfileBtn_id");
		profileValidationText = getByLocator(locators,
				"profileValidationText_id");

	}

	/***** Check id My Account Page is Loaded ********/
	public void checkIfMyAccountTitleIsPresent() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement myAccount = wait.until(ExpectedConditions
					.visibilityOfElementLocated(myAccountTitle));

			scrollToElement(myAccount);
			String actualText = "My Account";
			String expectedText = myAccount.getText();

			if (actualText.equals(expectedText)) {
				System.out
						.println("My Account Title is present. My Account Page is Loaded");
			} else {
				System.out
						.println("My Account Title is NOT present. My Account Page is NOT Loaded");
				Assert.assertEquals(actualText, expectedText,
						"My Account Title is NOT present. My Account Page is NOT Loaded");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/***** Click Edit Button for My Profile *****/
	public void clickEditProfileBtn() {

		try {

			int maxAttempts = 3;
			int count = 0;
			do {

				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement edit = wait.until(ExpectedConditions
						.elementToBeClickable(myProfileEditBtn));

				scrollToElement(edit);
				edit.click();
				System.out.println("Edit Button Clicked");

				try {
					wait = new WebDriverWait(driver, 20);
					WebElement updateProfile = wait.until(ExpectedConditions
							.visibilityOfElementLocated(updateProfileBtn));

					System.out.println("Success: Page Loaded in attempt no."
							+ (count + 1) + ". Update Button is visible");
					break;

				} catch (Exception e) {
					System.out
							.println("Page did NOT Load properly. Clicking Edit button again");
				}
				count++;
				if (count == maxAttempts) {

					System.out.println("Page did NOT load even after " + count
							+ " attempts, for 20 seconds each attempt");
					reportFail("Page did NOT load even after " + count
							+ " attempts, for 20 seconds each attempt");
				}

			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Enter Primary Phone Number ****/
	public void enterRandomPrimaryPhone() {

		try {

			String phoneNumber = "732" + RandomUtil.getRandomNumberOfLength(7);

			System.out.println("The Phone Number is: " + phoneNumber);

			WebElement primaryPhone = driver.findElement(primaryPhoneTextBox);

			scrollToElement(primaryPhone);

			primaryPhone.clear();
			primaryPhone.sendKeys(phoneNumber);

			System.out.println("Success: The Phone Number has been Entered");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Clicking the Check Box if it is Unchecked ****/
	public void checkMobileIfUnChecked() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement primaryMobileCheckBox = wait.until(ExpectedConditions
					.elementToBeClickable(primaryPhoneCheckBox));

			scrollToElement(primaryMobileCheckBox);

			if (!primaryMobileCheckBox.isSelected()) {
				primaryMobileCheckBox.click();

				System.out
						.println("Sucess: Mobile Check Box is Checked for Primary Phone");
			} else {

				System.out
						.println("Sucess: Mobile Check Box is Already Checked for Primary Phone");
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Clicking the Check Box if it Checked ****/
	public void unCheckMobileIfChecked() {

		try {

			WebElement primaryMobileCheckBox = driver
					.findElement(primaryPhoneCheckBox);

			scrollToElement(primaryMobileCheckBox);

			if (primaryMobileCheckBox.isSelected()) {
				primaryMobileCheckBox.click();

				System.out
						.println("Sucess: Mobile Check Box is Unchecked for Primary Phone");
			} else {
				System.out
						.println("Sucess: Mobile Check Box is Already Unchecked for Primary Phone");
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Select Preferred Contact Type ****/
	public void selectPreferredContactType(String preferedContactType) {

		try {
			Select contactTypeDropDown = new Select(
					driver.findElement(preferredContactTypeDropdown));

			contactTypeDropDown.selectByVisibleText(preferedContactType);
			System.out.println("Success: " + preferedContactType
					+ " selected from the Contact Type DropDown");
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Click My Profile Update Button ****/
	public void clickUpdateProfileBtn() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement updateProfile = wait.until(ExpectedConditions
					.elementToBeClickable(updateProfileBtn));

			updateProfile.click();

			System.out.println("Success: Update profile Clicked");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Validating Preferred Contact Method Error Message ****/
	public void validateContactMethodErrorMessage() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement contactMethodError = wait.until(ExpectedConditions
					.visibilityOfElementLocated(profileValidationText));

			String expectedText = "Please enter a Mobile Phone Number for Text Preferred Contact Method";
			String actualText = contactMethodError.getText();

			if (actualText.equals(expectedText)) {
				System.out
						.println("Success: Error msg for Preferred Contact Type '"
								+ actualText + "' is correct");
			} else {

				System.out
						.println("Success: Error msg for Preferred Contact Type '"
								+ actualText + "' is NOT correct");
				Assert.assertEquals(actualText, expectedText,
						"Success: Error msg for Preferred Contact Type '"
								+ actualText + "' is NOT correct");
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Validating Success Message after Entering valid Details ****/
	public boolean validateProfileUpdatedSuccessMessage() {

		boolean validated = false;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement profileUpdatedSuccess = wait.until(ExpectedConditions
					.visibilityOfElementLocated(profileValidationText));

			String expectedText = "Your profile has been successfully updated.";
			String actualText = profileUpdatedSuccess.getText();

			if (expectedText.equals(actualText)) {
				System.out.println("Success: Success msg for profile updated '"
						+ actualText + "' is correct");
				validated = true;
				;
			} else {

				System.out.println("Success: Success msg for profile updated '"
						+ actualText + "' is NOT correct");
				Assert.assertEquals(actualText, expectedText,
						"Success: Success msg for profile updated '"
								+ actualText + "' is NOT correct");
				validated = false;
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return validated;
	}

	/**** Clicking Home Link to navigate to Home Page ****/
	public void clickHomeLink() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement home = wait.until(ExpectedConditions
					.elementToBeClickable(homeLink));

			scrollToElement(home);
			home.click();

			System.out.println("Home Link clicked");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

}
