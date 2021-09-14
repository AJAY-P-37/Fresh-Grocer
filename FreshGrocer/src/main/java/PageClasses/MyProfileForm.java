package PageClasses;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.RandomUtil;
import baseClasses.PageBaseClass;

public class MyProfileForm extends PageBaseClass {

	Hashtable<String, String> locators;

	public By firstNameTextBox;
	public By firstNameValidationText;
	public By lastNameTextBox;
	public By lastNameValidationText;
	public By primaryPhoneTextBox;
	public By primaryPhoneCheckBox;
	public By alternatePhoneTextBox;
	public By alternatePhoneCheckBox;
	public By preferredContactTypeDropdown;
	public By stateDropDown;
	public By homeStoreDropdown;
	public By updateProfileBtn;

	public MyProfileForm(WebDriver driver) {

		super(driver);
		locators = readLocators("my_profile_form");
		assignValuesForLocatorsFromExcel();
	}

	public void assignValuesForLocatorsFromExcel() {

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
		stateDropDown = getByLocator(locators, "stateDropDown_id");
		homeStoreDropdown = getByLocator(locators, "homeStoreDropdown_id");
		updateProfileBtn = getByLocator(locators, "updateProfileBtn_id");

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
			System.out.println("Success: '" + selectedContactType
					+ "' selected from the Contact Type DropDown");
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/****
	 * Select a State in Profile where Number of Home Stores is at least 1
	 ******/
	public String selectState() {

		String selectedState = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement state = wait.until(ExpectedConditions
					.elementToBeClickable(stateDropDown));
			Select stateDropdown = new Select(state);

			List<WebElement> stateOptions = stateDropdown.getOptions();

			int totalOptions = stateOptions.size();
			int randomNumber = RandomUtil.getRandomNumberBetween(1,
					totalOptions - 1);

			stateDropdown.selectByIndex(randomNumber);

			selectedState = stateDropdown.getFirstSelectedOption().getText();

			System.out.println("Success: State '" + selectedState
					+ "' is selected");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return selectedState;
	}

	/****
	 * Get All the Home Store and Select a Home Store for state
	 ****/
	public String selectHomeStore() {

		String selectedHomeStore = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement homeStore = wait.until(ExpectedConditions
					.elementToBeClickable(homeStoreDropdown));

			homeStore.click();

			Select homeStoreDropDown = new Select(homeStore);

			List<WebElement> homeStoreOptions = homeStoreDropDown.getOptions();

			List<WebElement> availableOptions = new ArrayList<WebElement>();
			for (int index = 0; index < homeStoreOptions.size(); index++) {

				WebElement option = homeStoreOptions.get(index);

				String displayAttribute = option.getCssValue("display");

				if (displayAttribute.equals("block")) {
					availableOptions.add(option);
				}
			}
			availableOptions.remove(0);

			int totalAvailableOptions = availableOptions.size();

			System.out.println(totalAvailableOptions + " Home Stores found");

			if (totalAvailableOptions >= 1) {

				int randomNumber = RandomUtil.getRandomNumberBetween(0,
						totalAvailableOptions - 1);

				String randomOption = availableOptions.get(randomNumber)
						.getText();

				homeStoreDropDown.selectByVisibleText(randomOption);

				selectedHomeStore = homeStoreDropDown.getFirstSelectedOption()
						.getText();

				System.out.println("Success: Home Store '" + selectedHomeStore
						+ "' is selected");

			} else {
				selectedHomeStore = null;
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

		return selectedHomeStore;
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

			System.out.println("Success: Update profile Clicked");
		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}
	

	/****
	 * Click My Profile Update Button for valid Scenario(checking Page Load
	 * Condition)
	 ****/
	public void clickUpdateProfileBtnForValidScenario() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				WebElement primaryPhone = driver
						.findElement(primaryPhoneTextBox);
				String validation = primaryPhone.getAttribute("class");

				if (validation.contains("input-validation-error")) {
					System.out
							.println("Primary Phone field consists of Invalid Phone Number. Entering it again");
					takeScreenShotOnFailure();
					enterRandomPrimaryPhone();
				}
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

}
