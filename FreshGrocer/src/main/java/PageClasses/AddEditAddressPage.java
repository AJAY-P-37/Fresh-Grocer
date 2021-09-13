package PageClasses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.RandomUtil;
import utilities.ReadExcelDataFile;
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
	public By cancelAddressBtn;
	public By addressValidationText;

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
		cancelAddressBtn = getByLocator(locators, "cancelAddressBtn_id");

		addressValidationText = getByLocator(locators,
				"addressValidationText_id");

	}

	/**** Check if Page is loaded and Add/Edit Address Title is Present ****/
	public boolean checkIfAddEditAddressTitleIsPresent() {

		boolean isPresent = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement addEditAddress = wait.until(ExpectedConditions
					.visibilityOfElementLocated(addEditAddressTitle));

			scrollToElement(addEditAddress);
			String expectedText = "Add/Edit Address";
			String actualText = addEditAddress.getText();

			if (actualText.equals(expectedText)) {

				System.out.print("Address Page is Loaded. " + actualText
						+ " is present ");
				isPresent = true;
			} else {

				System.out.print("Address Page is NOT Loaded. " + actualText
						+ " is present ");
				isPresent = false;
			}
		} catch (Exception e) {
			System.out.print("Address Page is NOT Loaded. ");
			isPresent = false;
		}
		return isPresent;

	}

	/**** Enter a random First Name *****/
	public String enterFirstName() {

		String randomFirstName = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement firstName = wait.until(ExpectedConditions
					.visibilityOfElementLocated(firstNameTextBox));

			scrollToElement(firstName);
			randomFirstName = RandomUtil.getRandomUpperCaseLettersOfLength(1)
					+ RandomUtil.getRandomLowerCaseLettersOfLength(9);

			firstName.clear();
			firstName.sendKeys(randomFirstName);
			System.out.println("Success: Random First Name '" + randomFirstName
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomFirstName;
	}

	/**** Enter a random Last Name *****/
	public String enterLastName() {

		String randomLastName = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement lastName = wait.until(ExpectedConditions
					.visibilityOfElementLocated(lastNameTextBox));

			scrollToElement(lastName);

			randomLastName = RandomUtil.getRandomUpperCaseLettersOfLength(1)
					+ RandomUtil.getRandomLowerCaseLettersOfLength(9);

			lastName.clear();
			lastName.sendKeys(randomLastName);
			System.out.println("Success: Random Last Name '" + randomLastName
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomLastName;
	}

	/**** Enter a random Address *****/
	public String enterAddress() {

		String randomAddress = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement address = wait.until(ExpectedConditions
					.visibilityOfElementLocated(addressTextBox));

			scrollToElement(address);

			randomAddress = RandomUtil.getRandomUpperCaseLettersOfLength(1)
					+ RandomUtil.getRandomLowerCaseLettersOfLength(9);

			address.clear();
			address.sendKeys(randomAddress);
			System.out.println("Success: Random Address '" + randomAddress
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomAddress;
	}

	/**** Enter a random City *****/
	public String enterCity() {

		String randomCity = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement city = wait.until(ExpectedConditions
					.visibilityOfElementLocated(cityTextBox));
			scrollToElement(city);

			randomCity = RandomUtil.getRandomUpperCaseLettersOfLength(1)
					+ RandomUtil.getRandomLowerCaseLettersOfLength(9);

			city.clear();
			city.sendKeys(randomCity);
			System.out.println("Success: Random City '" + randomCity
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomCity;
	}

	/*** Select A State from the DropDown ***/
	public String selectStateDropDown() {

		String selectedState = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement state = wait.until(ExpectedConditions
					.elementToBeClickable(stateDropDown));

			Select dropDown = new Select(state);

			int numberOfOptions = dropDown.getOptions().size();

			int randomNumber = RandomUtil.getRandomNumberBetween(1,
					numberOfOptions - 1);

			dropDown.selectByIndex(randomNumber);

			selectedState = dropDown.getFirstSelectedOption().getText();
			System.out.println("Success: Random State '" + selectedState
					+ "' is selected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return selectedState;
	}

	/*** Select A State from the DropDown ***/
	public String selectStateDropDown(String stateToBeSelected) {

		String selectedState = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement state = wait.until(ExpectedConditions
					.elementToBeClickable(stateDropDown));

			Select dropDown = new Select(state);

			dropDown.selectByVisibleText(stateToBeSelected);

			selectedState = dropDown.getFirstSelectedOption().getText();
			System.out.println("Success: State '" + selectedState
					+ "' is selected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return selectedState;
	}

	/****
	 * Enter a random ZipCode
	 *****/
	public String enterZipcode() {

		String randomZipcode = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement zipcode = wait.until(ExpectedConditions
					.visibilityOfElementLocated(zipcodeTextBox));

			scrollToElement(zipcode);

			randomZipcode = ""
					+ RandomUtil.getRandomNumberBetween(10001, 99999);

			zipcode.clear();
			zipcode.sendKeys(randomZipcode);
			System.out.println("Success: Random Zipcode '" + randomZipcode
					+ "' has been entered");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomZipcode;
	}

	/****
	 * Enter Primary Phone Number
	 ****/
	public String enterPrimaryPhone() {

		String randomPhoneNumber = null;
		try {

			randomPhoneNumber = "732732"
					+ RandomUtil.getRandomNumberOfLength(4);

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement primaryPhone = wait.until(ExpectedConditions
					.visibilityOfElementLocated(primaryPhoneTextBox));

			scrollToElement(primaryPhone);

			primaryPhone.clear();
			primaryPhone.sendKeys(randomPhoneNumber);

			System.out.println("Success: The Phone Number '"
					+ randomPhoneNumber + "' has been Entered");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomPhoneNumber;
	}

	/**** Click Save Address Button ****/
	public void clickSaveAddressBtn() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					WebElement save = wait.until(ExpectedConditions
							.elementToBeClickable(saveAddressBtn));

					scrollToElement(save);
					save.click();

					System.out.println("Success: Save Address Button Clicked");

					// wait = new WebDriverWait(driver, 10);
					// wait.until(ExpectedConditions.invisibilityOf(save));
					break;

				} catch (Exception e) {
					System.out.println("Save NOT clicked in attempt no. "
							+ (count + 1));

				}

				count++;
				if (count == maxAttempts) {
					System.out.println("Save NOT clicked even after " + count
							+ " attempts for 10 seconds in each attempt");

					reportFail("Save NOT clicked even after " + count
							+ " attempts for 10 seconds in each attempt");
				}

			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Click Cancel Address Button ****/
	public void clickCancelAddressBtn() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					WebElement cancel = wait.until(ExpectedConditions
							.elementToBeClickable(cancelAddressBtn));

					scrollToElement(cancel);
					cancel.click();

					System.out
							.println("Success: Cancel Address Button Clicked");

					wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.invisibilityOf(cancel));
					break;

				} catch (Exception e) {
					System.out.println("Cancel NOT clicked in attempt no. "
							+ (count + 1));

				}

				count++;
				if (count == maxAttempts) {
					System.out.println("Cancel NOT clicked even after " + count
							+ " attempts for 10 seconds in each attempt");

					reportFail("Cancel NOT clicked even after " + count
							+ " attempts for 10 seconds in each attempt");
				}

			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Validating field for Address *******/
	public String getValidationTextForAddress() {

		String errorMessage = "";
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement addressErrorMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(addressValidationText));

			errorMessage = addressErrorMessage.getText();
		} catch (Exception e) {
			System.out.println("Error Message NOT Found");
			errorMessage = "";
		}
		return errorMessage;
	}

	/*** Getting All States in the DropDown with Error in Address Book Form *****/
	public List<WebElement> getAllStates() {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement states = wait.until(ExpectedConditions
				.visibilityOfElementLocated(stateDropDown));
		Select dropDown = new Select(states);
		List<WebElement> statesList = dropDown.getOptions();

		return statesList;

	}

	/**** Extracting the Texts from All the States in DropDown ***/
	public List<String> extractingTextFromStatesDropDown(
			List<WebElement> statesList) {

		List<String> states = new ArrayList<String>();

		statesList.remove(0);
		for (int index = 0; index < statesList.size(); index++) {
			states.add(statesList.get(index).getText());
		}
		return states;

	}

	/**** Finding the Duplicates in the States DropDown ***/
	public List<String> findDuplicatesInStatesDropDown(List<String> states) {

		List<String> listWithDuplicates = new ArrayList<String>();

		final Set<String> setWithUniqueStates = new HashSet<String>();

		for (String state : states) {

			if (!setWithUniqueStates.add(state)) {

				listWithDuplicates.add(state);
			}
		}
		return listWithDuplicates;

	}

	public void updatingInExcel(String fileNamePrefix, String sheetName,
			String colName, int fromRowNum, List<String> data) {
		System.out.println("*****Updating the " + sheetName + "******");
		try {
			String dir = System.getProperty("user.dir")
					+ "/src/main/resources/TestData";

			String path = readFileWithPrefix(dir, fileNamePrefix);

			ReadExcelDataFile readData = new ReadExcelDataFile(path);

			readData.clearExistingDataInSheet(sheetName, fromRowNum);

			for (int index = 0; index < data.size(); index++) {

				readData.setCellData(sheetName, "S.No", index + 2, ""
						+ (index + 1));

				boolean flag = readData.setCellData(sheetName, colName,
						index + 2, data.get(index));
				Assert.assertEquals(true, flag);
				System.out.println(data.get(index) + " updated in Excel");
			}

			renameFileWithDateTime(path);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Updating the States with Error in Excel ***/
	public void updateStatesWithErrorInExcel(List<String> statesWithError) {

		updatingInExcel("erroneousStates", "states_with_error", "States", 2,
				statesWithError);

	}

	/**** Updating the Duplicate States in Excel ***/
	public void updateDuplicateStatesInExcel(List<String> duplicateStates) {

		updatingInExcel("erroneousStates", "duplicate_states", "States", 2,
				duplicateStates);

	}
}
