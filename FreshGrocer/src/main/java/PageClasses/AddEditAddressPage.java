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

public class AddEditAddressPage extends PageBaseClass {

	Hashtable<String, String> locators;

	public By addEditAddressTitle;
	public By firstNameTextBox;
	public By lastNameTextBox;
	public By addressTextBox;
	public By cityTextBox;
	public By stateDropDown;
	public By zipcodeTextBox;
	public By primaryPhoneTextBox;
	public By primaryPhoneCheckBox;
	public By alternatePhoneTextBox;
	public By alternatePhoneCheckBox;
	public By saveAddressBtn;

	public AddEditAddressPage(WebDriver driver) {

		super(driver);
		locators = readLocators("add_edit_address_page");
		assignValuesForLocatorsFromExcel();

	}

	public void assignValuesForLocatorsFromExcel() {

		addEditAddressTitle = getByLocator(locators,
				"addEditAddressTitle_xpath");
		firstNameTextBox = getByLocator(locators, "firstNameTextBox_id");
		lastNameTextBox = getByLocator(locators, "lastNameTextBox_id");
		addressTextBox = getByLocator(locators, "addressTextBox_id");
		cityTextBox = getByLocator(locators, "cityTextBox_id");
		stateDropDown = getByLocator(locators, "stateDropDown_id");
		zipcodeTextBox = getByLocator(locators, "zipcodeTextBox_id");
		primaryPhoneTextBox = getByLocator(locators, "primaryPhoneTextBox_id");
		saveAddressBtn = getByLocator(locators, "saveAddressBtn_id");

	}

	/**** Check if Page is loaded and Add/Edit Address Title is Present ****/
	public boolean checkIfAddEditAddressTitleIsPresent() {

		boolean isPresent = false;
		try {
			WebElement addEditAddress = waitUntil("visibilityOfElementLocated",
					addEditAddressTitle, 30);

			scrollToElement(addEditAddress);
			String expectedText = "Add/Edit Address";
			String actualText = addEditAddress.getText();

			if (actualText.equals(expectedText)) {

				System.out.print("Page is Loaded. " + actualText
						+ " is present ");
				isPresent = true;
			} else {

				System.out.print("Page is NOT Loaded. " + actualText
						+ " is present ");
				isPresent = false;
			}
		} catch (Exception e) {

			isPresent = false;
		}
		return isPresent;

	}

	/**** Enter a random First Name *****/
	public void enterFirstName() {

		try {

			WebElement firstName = waitUntil("visibilityOfElementLocated",
					firstNameTextBox, 10);

			scrollToElement(firstName);
			String randomFirstName = RandomUtil.getRandomStringOfLength(10);

			firstName.clear();
			firstName.sendKeys(randomFirstName);
			System.out.println("Success: Random First Name '" + randomFirstName
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Enter a random Last Name *****/
	public void enterLastName() {

		try {

			WebElement lastName = waitUntil("visibilityOfElementLocated",
					lastNameTextBox, 10);

			scrollToElement(lastName);

			String randomLastName = RandomUtil.getRandomStringOfLength(10);

			lastName.clear();
			lastName.sendKeys(randomLastName);
			System.out.println("Success: Random Last Name '" + randomLastName
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Enter a random Address *****/
	public void enterAddress() {

		try {

			WebElement address = waitUntil("visibilityOfElementLocated",
					addressTextBox, 10);

			scrollToElement(address);

			String randomAddress = RandomUtil.getRandomStringOfLength(10);

			address.clear();
			address.sendKeys(randomAddress);
			System.out.println("Success: Random Address '" + randomAddress
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Enter a random City *****/
	public void enterCity() {

		try {

			WebElement city = waitUntil("visibilityOfElementLocated",
					cityTextBox, 10);

			scrollToElement(city);

			String randomCity = RandomUtil.getRandomStringOfLength(10);

			city.clear();
			city.sendKeys(randomCity);
			System.out.println("Success: Random City '" + randomCity
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*** Select A State from the DropDown ***/
	public void selectStateDropDown() {

		try {
			WebElement state = driver.findElement(stateDropDown);
			Select dropDown = new Select(state);

			int numberOfOptions = dropDown.getOptions().size();

			int randomNumber = RandomUtil.getRandomNumberBetween(1,
					numberOfOptions - 1);

			dropDown.selectByIndex(randomNumber);

			String selectedState = dropDown.getFirstSelectedOption().getText();
			System.out.println("Success: Random State '" + selectedState
					+ "' is selected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Enter a random ZipCode *****/
	public void enterZipcode() {

		try {

			WebElement zipcode = waitUntil("visibilityOfElementLocated",
					zipcodeTextBox, 10);

			scrollToElement(zipcode);

			String randomZipcode = RandomUtil.getRandomNumberOfLength(5);

			zipcode.clear();
			zipcode.sendKeys(randomZipcode);
			System.out.println("Success: Random Zipcode '" + randomZipcode
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Enter Primary Phone Number ****/
	public void enterPrimaryPhone() {

		try {

			String phoneNumber = "732732"
					+ RandomUtil.getRandomNumberOfLength(4);

			WebElement primaryPhone = waitUntil("visibilityOfElementLocated",
					primaryPhoneTextBox, 10);

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

	/**** Click Save Address Button ****/
	public void clickSaveAddressBtn() {

		try {
			
			int count = 0, maxAttempts = 3;
			do {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				WebElement save = wait.until(ExpectedConditions
						.elementToBeClickable(saveAddressBtn));

				scrollToElement(save);
				save.click();

				System.out.println("Success: Save Address Button Clicked");

				wait = new WebDriverWait(driver, 10);
				boolean homePageLoaded = wait.until(ExpectedConditions
						.invisibilityOf(save));

				if (homePageLoaded) {

					System.out.println("Sucess: Page Loaded in attempt no. "
							+ (count + 1));
					break;
				} else {
					
					System.out
							.println("Sucess: Page Loaded did NOT in attempt no. "
									+ (count + 1));

				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Success: Page NOT loaded even after "
							+ count
							+ " attempts for 10 seconds in each attempt");

					reportFail("Success: Page NOT loaded even after " + count
							+ " attempts for 10 seconds in each attempt");
				}

			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}
}
