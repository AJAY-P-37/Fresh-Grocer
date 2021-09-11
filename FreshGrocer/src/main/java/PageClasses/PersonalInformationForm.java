package PageClasses;

import java.time.LocalDate;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.RandomUtil;
import baseClasses.PageBaseClass;

public class PersonalInformationForm extends PageBaseClass {

	Hashtable<String, String> locators;

	public By membersDropDown;
	public By birthDateTextBox;
	public By maleRadioBtn;
	public By femaleRadioBtn;
	public By textYesRadioBtn;
	public By textNoRadioBtn;
	public By promotionYesRadioBtn;
	public By promotionNoRadioBtn;
	public By receiptYesRadioBtn;
	public By receiptNoRadioBtn;
	public By emailYesRadioBtn;
	public By emailNoRadioBtn;
	public By updatePersonalInfoBtn;

	public PersonalInformationForm(WebDriver driver) {

		super(driver);
		locators = readLocators("personal_information_form");
		assignValuesForLocatorsFromExcel();
	}

	public void assignValuesForLocatorsFromExcel() {

		membersDropDown = getByLocator(locators, "membersDropDown_id");
		birthDateTextBox = getByLocator(locators, "birthDateTextBox_xpath");
		maleRadioBtn = getByLocator(locators, "maleRadioBtn_id");
		femaleRadioBtn = getByLocator(locators, "femaleRadioBtn_id");
		textYesRadioBtn = getByLocator(locators, "textYesRadioBtn_id");
		textNoRadioBtn = getByLocator(locators, "textNoRadioBtn_id");
		promotionYesRadioBtn = getByLocator(locators, "promotionYesRadioBtn_id");
		promotionNoRadioBtn = getByLocator(locators, "promotionNoRadioBtn_id");
		receiptYesRadioBtn = getByLocator(locators, "receiptYesRadioBtn_id");
		receiptNoRadioBtn = getByLocator(locators, "receiptNoRadioBtn_id");
		emailYesRadioBtn = getByLocator(locators, "emailYesRadioBtn_id");
		emailNoRadioBtn = getByLocator(locators, "emailNoRadioBtn_id");
		updatePersonalInfoBtn = getByLocator(locators,
				"updatePersonalInfoBtn_id");

	}

	/**** Check if Page is loaded and Add/Edit Address Title is Present ****/
	public boolean checkIfUpdatePersonalInfoBtnIsPresent() {

		boolean isPresent = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement update = wait.until(ExpectedConditions
					.visibilityOfElementLocated(updatePersonalInfoBtn));

			isPresent = true;

		} catch (Exception e) {

			isPresent = false;
		}
		return isPresent;

	}

	/**** Select Members in Household DropDown *****/
	public void selectMembersInHousehold() {

		try {

			WebElement members = waitUntil("visibilityOfElementLocated",
					membersDropDown, 10);

			scrollToElement(members);
			Select dropDown = new Select(members);

			int optionsCount = dropDown.getOptions().size();

			int randomOption = RandomUtil.getRandomNumberBetween(0,
					optionsCount - 1);

			dropDown.selectByIndex(randomOption);

			String selectedMembers = dropDown.getFirstSelectedOption()
					.getText();

			System.out.println("Success: " + selectedMembers
					+ " selected from the Members in Household DropDown");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Enter a Birth Date *****/
	public void enterBirthDate() {

		try {

			WebElement birthDate = waitUntil("elementToBeClickable",
					birthDateTextBox, 10);

			scrollToElement(birthDate);

			int minAge = 18, maxAge = 100;
			LocalDate randomDate = RandomUtil.getRandomDate(minAge, maxAge);

			String date = String.format("%02d", randomDate.getDayOfMonth());
			String month = String.format("%02d", randomDate.getMonthValue());
			String year = String.format("%04d", randomDate.getYear());

			String dob = date + "/" + month + "/" + year;

			birthDate.clear();
			birthDate.sendKeys(dob);

			System.out.println("Sucess: Random Date " + dob + " is selected");

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Select Gender from Radio button *****/
	public void selectGenderRadioBtn() {

		try {

			int randomNumber = RandomUtil.getRandomNumberBetween(1, 2);

			if (randomNumber == 1) {
				WebElement male = driver.findElement(maleRadioBtn);
				scrollToElement(male);

				clickWithJSExecutor(male);

				System.out.println("Success: Male Clicked for Gender");

			} else if (randomNumber == 2) {
				WebElement female = driver.findElement(femaleRadioBtn);
				scrollToElement(female);

				clickWithJSExecutor(female);

				System.out.println("Success: Female Clicked for Gender");

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Select Receive Text Messages Radio Button *****/
	public void selectReceiveTextMessagesRadioBtn() {

		try {

			int randomNumber = RandomUtil.getRandomNumberBetween(1, 2);

			if (randomNumber == 1) {
				WebElement yes = driver.findElement(textYesRadioBtn);
				scrollToElement(yes);

				clickWithJSExecutor(yes);

				System.out
						.println("Success: Yes Clicked for Receive Text Messages Radio Button");

			} else if (randomNumber == 2) {
				WebElement no = driver.findElement(textNoRadioBtn);
				scrollToElement(no);

				clickWithJSExecutor(no);

				System.out
						.println("Success: No Clicked for Receive Text Messages Radio Button");

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Select Receive Promotional Mailings Radio Button *****/
	public void selectReceivePromotionalMailingsRadioBtn() {

		try {

			int randomNumber = RandomUtil.getRandomNumberBetween(1, 2);

			if (randomNumber == 1) {
				WebElement yes = driver.findElement(promotionYesRadioBtn);
				scrollToElement(yes);

				clickWithJSExecutor(yes);

				System.out
						.println("Success: Yes Clicked for Receive Promotional Mailings Radio Button");

			} else if (randomNumber == 2) {
				WebElement no = driver.findElement(promotionNoRadioBtn);
				scrollToElement(no);

				clickWithJSExecutor(no);

				System.out
						.println("Success: No Clicked for Receive Promotional Mailings Radio Button");

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Select Receive Digital Receipts Radio Button *****/
	public void selectReceiveDigitalReceiptsRadioBtn() {

		try {

			int randomNumber = RandomUtil.getRandomNumberBetween(1, 2);

			if (randomNumber == 1) {
				WebElement yes = driver.findElement(receiptYesRadioBtn);
				scrollToElement(yes);

				clickWithJSExecutor(yes);

				System.out
						.println("Success: Yes Clicked for Receive Digital Receipts Radio Button");

			} else if (randomNumber == 2) {
				WebElement no = driver.findElement(receiptNoRadioBtn);
				scrollToElement(no);

				clickWithJSExecutor(no);

				System.out
						.println("Success: No Clicked for Receive Digital Receipts Radio Button");

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*****
	 * Select Receive Email Notification when Digital Receipts available Radio
	 * Button
	 *****/
	public void selectReceiveEmailForDigitalReceiptsRadioBtn() {

		try {

			int randomNumber = RandomUtil.getRandomNumberBetween(1, 2);

			if (randomNumber == 1) {
				WebElement yes = driver.findElement(emailYesRadioBtn);

				scrollToElement(yes);

				clickWithJSExecutor(yes);

				System.out
						.println("Success: Yes Clicked for Receive Email Notification when Digital Receipts available Radio Button");

			} else if (randomNumber == 2) {
				WebElement no = driver.findElement(emailNoRadioBtn);
				scrollToElement(no);

				clickWithJSExecutor(no);

				System.out
						.println("Success: No Clicked for Receive Email Notification when Digital Receipts available Radio Button");

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Click Update Button for Personal Information *****/
	public void clickUpdatePersonalInfoBtn() {

		try {

			int count = 0, maxAttempts = 3;
			do {
				WebDriverWait wait = new WebDriverWait(driver, 10);
				WebElement update = wait.until(ExpectedConditions
						.elementToBeClickable(updatePersonalInfoBtn));

				scrollToElement(update);
				update.click();

				System.out
						.println("Sucess: Update Personal Info Button clicked");

				wait = new WebDriverWait(driver, 10);
				boolean pageLoaded = wait.until(ExpectedConditions
						.invisibilityOf(update));

				if (pageLoaded) {

					System.out.println("Sucess: Page Loaded in attempt no. "
							+ (count + 1) + ". Update button is invisible");
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
