package PageClasses;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
	public By addressBookEditBtn;
	public By addNewAddressBtn;
	public By addressValidationText;
	public By addressDeleteBtn;
	public By personalInformationEditBtn;
	public By personalInformationValidationText;
	public By ajaxLoadingSpinner;

	AddEditAddressPage addressPage;
	PersonalInformationForm personalInfoPage;

	public MyAccountPage(WebDriver driver) {

		super(driver);
		locators = readLocators("my_account_page");

		addressPage = new AddEditAddressPage(driver);
		personalInfoPage = new PersonalInformationForm(driver);

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
		stateDropdown = getByLocator(locators, "stateDropDown_id");
		homeStoreDropdown = getByLocator(locators, "homeStoreDropdown_id");
		updateProfileBtn = getByLocator(locators, "updateProfileBtn_id");
		profileValidationText = getByLocator(locators,
				"profileValidationText_id");
		addressBookEditBtn = getByLocator(locators, "addressBookEditBtn_xpath");
		addNewAddressBtn = getByLocator(locators, "addNewAddressBtn_id");
		addressValidationText = getByLocator(locators,
				"addressValidationText_id");
		addressDeleteBtn = getByLocator(locators, "addressDeleteBtn_xpath");
		personalInformationEditBtn = getByLocator(locators,
				"personalInformationEditBtn_id");
		personalInformationValidationText = getByLocator(locators,
				"personalInformationValidationText_id");
		ajaxLoadingSpinner = getByLocator(locators, "ajaxLoadingSpinner_xpath");
	}

	/***** Check id My Account Page is Loaded ********/
	public void checkIfMyAccountTitleIsPresent() {

		try {
			int count = 0, maxAttempts = 3;
			do {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 20);
					WebElement myAccount = wait.until(ExpectedConditions
							.visibilityOfElementLocated(myAccountTitle));

					scrollToElement(myAccount);
					String actualText = "My Account";
					String expectedText = myAccount.getText();

					if (actualText.equals(expectedText)) {
						System.out
								.println("My Account Title is present. My Account Page is Loaded in attempt no. "
										+ (count + 1));
						break;

					} else {
						System.out
								.println("My Account Title is NOT present. Title is Incorrect");
						try {

							Assert.assertEquals(actualText, expectedText,
									"My Account Title is NOT present. Title is Incorrect");
						} catch (Exception e) {
							reportFail(e.getMessage());
						}
					}

				} catch (Exception e) {
					System.out
							.println("My Account Title is NOT present. My Account Page is NOT Loaded in attempt no."
									+ (count + 1) + ". Refreshing the Page");
					refreshPage();
				}
				count++;
				if (count == maxAttempts) {
					System.out
							.println("My Account Title is NOT present. My Account Page is NOT Loaded even after "
									+ count + " attempts");
					reportFail("My Account Title is NOT present. My Account Page is NOT Loaded even after "
							+ count + " attempts");
				}

			} while (count <= maxAttempts);

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

			String phoneNumber = "732732"
					+ RandomUtil.getRandomNumberOfLength(4);

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement primaryPhone = wait.until(ExpectedConditions
					.visibilityOfElementLocated(primaryPhoneTextBox));

			scrollToElement(primaryPhone);

			primaryPhone.clear();
			primaryPhone.sendKeys(phoneNumber);

			System.out.println("Success: The Phone Number '" + phoneNumber
					+ "' has been Entered");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Clicking the Check Box if it is Unchecked ****/
	public void checkMobileIfUnChecked() {

		try {

			WebElement primaryMobileCheckBox = driver
					.findElement(primaryPhoneCheckBox);

			scrollToElement(primaryMobileCheckBox);

			if (!primaryMobileCheckBox.isSelected()) {

				// primaryMobileCheckBox.click();
				// Actions act = new Actions(driver);
				// act.moveToElement(primaryMobileCheckBox).click().build()
				// .perform();
				clickWithJSExecutor(primaryMobileCheckBox);

				System.out
						.println("Success: Mobile Check Box is Checked for Primary Phone");
			} else {

				System.out
						.println("Success: Mobile Check Box is Already Checked for Primary Phone");
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
				// primaryMobileCheckBox.click();
				// Actions act = new Actions(driver);
				// act.moveToElement(primaryMobileCheckBox).click().build()
				// .perform();
				clickWithJSExecutor(primaryMobileCheckBox);

				System.out
						.println("Success: Mobile Check Box is Unchecked for Primary Phone");
			} else {
				System.out
						.println("Success: Mobile Check Box is Already Unchecked for Primary Phone");
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

			String selectedContactType = contactTypeDropDown
					.getFirstSelectedOption().getText();
			System.out.println("Success: " + selectedContactType
					+ " selected from the Contact Type DropDown");
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/****
	 * Click Update Button for Invalid Scenario (Not checking Page Load
	 * Condition)
	 ****/
	public void clickUpdateProfileBtnForInvalidScenario() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement updateProfile = wait.until(ExpectedConditions
					.elementToBeClickable(updateProfileBtn));

			scrollToElement(updateProfile);
			updateProfile.click();

			System.out.println("Update profile Clicked");
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Validating Preferred Contact Method Error Message ****/
	public boolean validateContactMethodErrorMessage() {

		boolean validated = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement contactMethodError = wait.until(ExpectedConditions
					.visibilityOfElementLocated(profileValidationText));

			scrollToElement(contactMethodError);

			String expectedText = "Please enter a Mobile Phone Number for Text Preferred Contact Method";
			String actualText = contactMethodError.getText();

			if (actualText.equals(expectedText)) {
				System.out
						.println("Success: Error msg for Preferred Contact Type '"
								+ actualText + "' is correct");
				validated = true;
			} else {

				System.out.println("Error msg for Preferred Contact Type '"
						+ actualText + "' is NOT correct");
				reportFail("Error msg for Preferred Contact Type '"
						+ actualText + "' is NOT correct");
			}

		} catch (Exception e) {

			System.out
					.println("Error msg for Preferred Contact Type is NOT found");
			validated = false;
		}
		return validated;
	}

	/****
	 * Click My Profile Update Button for valid Scenario(checking Page Load
	 * Condition)
	 ****/
	public void clickUpdateProfileBtnForValidScenario() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				WebElement updateProfile = wait.until(ExpectedConditions
						.elementToBeClickable(updateProfileBtn));

				scrollToElement(updateProfile);
				updateProfile.click();

				System.out.println("Update profile Clicked");

				try {
					wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.invisibilityOf(updateProfile));

					System.out.println("Success: Page Loaded in attempt no. "
							+ (count + 1) + ". Update is invisible.");
					break;
				} catch (Exception e) {

					System.out
							.println("Page Loaded did NOT load in attempt no. "
									+ (count + 1)
									+ ". Clicking Update button again");
				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Page did NOT load even after " + count
							+ " attempts, for 10 seconds in each attempt");
					reportFail("Page did NOT load even after " + count
							+ " attempts, for 10 seconds in each attempt");
				}
			} while (count <= maxAttempts);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Validating Success Message after Entering valid Details for Profile ****/
	public boolean validateProfileUpdatedSuccessMessage() {

		boolean validated = false;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement profileUpdatedSuccess = wait.until(ExpectedConditions
					.visibilityOfElementLocated(profileValidationText));

			scrollToElement(profileUpdatedSuccess);

			String expectedText = "Your profile has been successfully updated.";
			String actualText = profileUpdatedSuccess.getText();

			if (expectedText.equals(actualText)) {
				System.out.println("Success: Success msg for profile updated '"
						+ actualText + "' is correct");
				validated = true;

			} else {

				System.out.println("Success msg for profile updated '"
						+ actualText + "' is NOT correct");
				reportFail("Success msg for profile updated '" + actualText
						+ "' is NOT correct");

			}

		} catch (Exception e) {

			System.out.println("Success msg for profile updated is NOT found");
			validated = false;
		}
		return validated;
	}

	/**** Click Address Book Edit Button ****/
	public void clickAddressBookEditBtn() {

		try {

			int count = 0, maxAttempts = 3;
			do {

				WebElement addressBookEdit = driver
						.findElement(addressBookEditBtn);

				scrollToElement(addressBookEdit);
				clickWithJSExecutor(addressBookEdit);

				System.out.println("Success: Clicked Address Book Edit Button");

				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					WebElement addNewAddress = wait.until(ExpectedConditions
							.visibilityOfElementLocated(addNewAddressBtn));
					System.out
							.println("Success: Add New Address Button is Present in attempt no. "
									+ (count + 1));
					break;
				} catch (Exception e) {

					System.out
							.println("Add New Address Button is NOT Present in attempt no. "
									+ (count + 1)
									+ ". Clicking Edit button again");
				}
				count++;
				if (count == maxAttempts) {
					System.out
							.println("Add New Address Button is NOT Present even after "
									+ count + " attempts");
					reportFail("Add New Address Button is NOT Present even after "
							+ count + " attempts");
				}
			} while (count <= maxAttempts);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Click Add New Address Button ****/
	public void clickAddNewAddressBtn() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				WebElement addNewAddress = driver.findElement(addNewAddressBtn);

				scrollToElement(addNewAddress);
				clickWithJSExecutor(addNewAddress);

				System.out.println("Success: Clicked Add New Address Button");

				boolean isPresent = addressPage
						.checkIfAddEditAddressTitleIsPresent();

				System.out.println("in attempt no. " + (count + 1));
				if (isPresent) {
					break;
				}
				count++;
				if (count == maxAttempts) {
					System.out
							.println("Add/Edit Address Page NOT loaded even after "
									+ count + " attempts");
				}

			} while (count <= maxAttempts);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Validating Success Message after Entering valid Details for Address *****/
	public void validateAdrressUpdatedSuccessMessage() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement addressUpdated = wait.until(ExpectedConditions
					.visibilityOfElementLocated(addressValidationText));

			scrollToView(addressUpdated);
			String expectedText = "Your address book has been successfully updated.";
			String actualText = addressUpdated.getText();

			if (actualText.equals(expectedText)) {
				System.out.println("Success: Success msg for address updated '"
						+ actualText + "' is correct");
			} else {

				System.out.println("Success msg for address updated '"
						+ actualText + "' is NOT correct");
				reportFail("Success msg for address updated '" + actualText
						+ "' is NOT correct");
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Click Delete Button for the Latest Updated Address ****/
	public void clickAddressDeleteBtn() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			List<WebElement> allDeleteButtons = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(addressDeleteBtn));

			WebElement latestUpdatedAddressDeleteBtn = allDeleteButtons
					.get(allDeleteButtons.size() - 1);

			int count = 0, maxAttempts = 3;
			do {
				wait.until(ExpectedConditions
						.elementToBeClickable(latestUpdatedAddressDeleteBtn));

				scrollToElement(latestUpdatedAddressDeleteBtn);
				latestUpdatedAddressDeleteBtn.click();

				System.out
						.println("Success: Clicked Delete Button for the Latest Updated Address");
				try {
					wait.until(ExpectedConditions
							.invisibilityOf(latestUpdatedAddressDeleteBtn));
					System.out
							.println("Sucess: Latest Updated Address Deleted in attempt no."
									+ (count + 1));
					break;

				} catch (Exception e) {
					System.out
							.println("Latest Updated Address NOT Deleted in attempt no."
									+ (count + 1));
				}

				count++;
				if (count == maxAttempts) {

					System.out
							.println("Latest Updated Address NOT Deleted even after"
									+ count + " attempts");
					reportFail("Latest Updated Address NOT Deleted even after"
							+ count + " attempts");
				}
			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Click Personal Information Edit Button ****/
	public void clickPersonalInformationEditBtn() {

		try {

			int count = 0, maxAttempts = 3;
			do {

				WebElement personalInformationEdit = driver
						.findElement(personalInformationEditBtn);

				scrollToElement(personalInformationEdit);
				clickWithJSExecutor(personalInformationEdit);

				System.out
						.println("Success: Clicked Personal Info Edit Button");

				boolean isPresent = personalInfoPage
						.checkIfUpdatePersonalInfoBtnIsPresent();

				if (isPresent) {
					System.out
							.println("Success: Update Personal Info Button is Present in attempt no. "
									+ (count + 1));
					break;
				} else {
					System.out
							.println("Update Personal Info Button is NOT Present in attempt no. "
									+ (count + 1) + ". Clicking again");
				}
				count++;
				if (count == maxAttempts) {
					System.out
							.println("Update Personal Info Button is NOT Present even after "
									+ count + " attempts");
					reportFail("Update Personal Info Button is NOT Present even after "
							+ count + " attempts");
				}
			} while (count <= maxAttempts);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/****
	 * Validating Success Message after Entering valid Details for Personal
	 * Information
	 *****/
	public void validatePersonalInfoUpdatedSuccessMessage() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement personalInformationUpdated = wait
					.until(ExpectedConditions
							.visibilityOfElementLocated(personalInformationValidationText));

			scrollToView(personalInformationUpdated);
			String expectedText = "Your Personal Info has been successfully updated.";
			String actualText = personalInformationUpdated.getText();

			if (actualText.equals(expectedText)) {
				System.out
						.println("Success: Success msg for perosnal info updated '"
								+ actualText + "' is correct");
			} else {

				System.out.println("Success msg for personal info updated '"
						+ actualText + "' is NOT correct");
				reportFail("Success msg for personal info updated '"
						+ actualText + "' is NOT correct");
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/*** Waiting for AJAX Loading Spinner ****/
	public void waitForAjaxLoadingSpinner() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.attributeToBe(ajaxLoadingSpinner,
					"display", "none"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());

		}
	}

	/**** Clicking Home Link to navigate to Home Page ****/
	public void clickHomeLink() {

		try {

			int count = 0, maxAttempts = 3;
			do {

				WebDriverWait wait = new WebDriverWait(driver, 10);
				WebElement home = wait.until(ExpectedConditions
						.visibilityOfElementLocated(homeLink));

				wait.until(ExpectedConditions.elementToBeClickable(home));

				scrollToElement(home);
				home.click();

				System.out.println("Success: Home Link clicked");

				try {
					wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.invisibilityOf(home));

					System.out.println("Sucess: Page Loaded in attempt no. "
							+ (count + 1));
					break;
				} catch (Exception e) {

					System.out.println("Page Loaded did NOT in attempt no. "
							+ (count + 1));

				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Page NOT loaded even after " + count
							+ " attempts for 10 seconds in each attempt");

					reportFail("Page NOT loaded even after " + count
							+ " attempts for 10 seconds in each attempt");
				}

			} while (count <= maxAttempts);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

}
