package PageClasses;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.RandomUtil;
import baseClasses.PageBaseClass;

public class EmailSubscriptionForm extends PageBaseClass {

	Hashtable<String, String> locators;

	public By brandLogoTop;
	public By emailSubscriptionframe;
	public By checkBoxes;
	public By submitBtn;
	public By loadingText;
	public By successMessage;
	public By emailSubscriptionOptions;
	public By emailOptions;
	public By cancelBtn;

	Wait<WebDriver> visibilityWait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(30))
			.pollingEvery(Duration.ofMillis(500)).ignoring(Exception.class);

	public EmailSubscriptionForm(WebDriver driver) {

		super(driver);
		locators = readLocators("email_subscription_form");
		assignValuesForLocatorsFromExcel();

	}

	public void assignValuesForLocatorsFromExcel() {

		brandLogoTop = getByLocator(locators, "brandLogoTop_id");
		emailSubscriptionframe = getByLocator(locators,
				"emailSubscriptionframe_id");
		checkBoxes = getByLocator(locators, "checkBoxes_xpath");
		submitBtn = getByLocator(locators, "submitBtn_xpath");
		loadingText = getByLocator(locators, "loadingText_xpath");
		successMessage = getByLocator(locators, "successMessage_xpath");
		emailSubscriptionOptions = getByLocator(locators,
				"emailSubscriptionOptions_id");
		emailOptions = getByLocator(locators, "emailOptions_xpath");
		cancelBtn = getByLocator(locators, "cancelBtn_id");

	}

	/*** Check if FreshGrocer BrandLogo is Present At Top ***/
	public boolean checkIfFresheGrocerBrandLogoIsPresent() {

		boolean isPresent = false;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement brandLogo = wait.until(ExpectedConditions
					.visibilityOfElementLocated(brandLogoTop));

			String srcAttribute = brandLogo.getAttribute("src");
			if (srcAttribute.contains("FG")) {

				System.out
						.println("Success: Fresh Grocer Brand Logo is Present At the Top");
				isPresent = true;

			} else {
				System.out
						.println("Fresh Grocer Brand Logo is NOT Present At the Top");
				isPresent = false;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			isPresent = false;
		}
		return isPresent;
	}

	/*** Choose A Random SubScription Option and Select ***/
	public String selectRandomEmailSubscriptionOption() {

		String selected = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement brandLogo = wait.until(ExpectedConditions
					.visibilityOfElementLocated(emailSubscriptionOptions));

			List<WebElement> emailSubscriptionOptions = brandLogo
					.findElements(emailOptions);
			List<WebElement> availableOptions = new ArrayList<WebElement>();

			System.out
					.println("Choosing a random option from the available Option for email subscription "
							+ "from the below options");
			for (int index = 0; index < emailSubscriptionOptions.size(); index++) {

				WebElement emailSubscriptionOption = emailSubscriptionOptions
						.get(index);

				String option = emailSubscriptionOption.getAttribute("for");

				String available = emailSubscriptionOption
						.getCssValue("display");

				System.out.print("Email Subscription for " + option + " is ");

				if (!available.equals("none")) {

					System.out.println(" Available");
					availableOptions.add(emailSubscriptionOption);

				} else {

					System.out.println(" NOT Available");
				}
			}

			int totalOptions = availableOptions.size();
			int randomNumber = RandomUtil.getRandomNumberBetween(0,
					totalOptions - 1);

			WebElement selectedOption = availableOptions.get(randomNumber);

			selected = selectedOption.getAttribute("for");
			System.out.println("Success: Choosed Option is " + selected);
			int count = 0, maxAttempts = 3;
			do {
				selectedOption.click();
				System.out.println("Success: " + selected + " is Clicked");

				String available = selectedOption.getCssValue("display");

				if (available.equals("none")) {

					System.out.println("Selected option" + selected
							+ " is clicked in attempt no." + (count + 1));
					break;
				} else {

					System.out.println("Selected option" + selected
							+ " is NOT clicked in attempt no. " + (count + 1)
							+ ". Clicking again");
				}
				count++;
				if (count == maxAttempts) {
					System.out.println("Selected option" + selected
							+ " is NOT clicked even after " + count
							+ " attempts");
					reportFail("Selected option" + selected
							+ " is NOT clicked even after " + count
							+ " attempts");
				}
			} while (count <= maxAttempts);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return selected;
	}

	/*** Check if the Selected Option for subscription is Present at the TOP ***/
	public void checkIfSelectedOptionIsPresentAtTop(String selectedOption) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean selectedLogoPresent = wait.until(ExpectedConditions
					.attributeContains(brandLogoTop, "src", selectedOption));

			if (selectedLogoPresent) {
				System.out.println("Success: Selected Email Subscription '"
						+ selectedOption + "' Option is Present at the Top");
			} else {
				System.out
						.println("Fail: Selected Email Subscription Option is NOT Present at the Top");
				reportFail("Fail: Selected Email Subscription Option is NOT Present at the Top");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/**** Switch to the Email Subscription Frame ***/
	public void switchToEmailSubscriptionFrame() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(emailSubscriptionframe));

			System.out.println("Success: Switched to Email Subscription Frame");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*** Click all the Check Boxes in the Frame ****/
	public void checkAllCheckBoxesInFrame() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);

			List<WebElement> emailCheckBoxes = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(checkBoxes));

			for (WebElement checkBox : emailCheckBoxes) {

				if (!checkBox.isSelected()) {
					checkBox.click();
				}
			}
			System.out.println("Success: All Check Boxes clicked");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*** Click Submit button in the Frame ***/
	public void clickSubmitBtn() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);

			WebElement submit = wait.until(ExpectedConditions
					.visibilityOfElementLocated(submitBtn));

			submit.click();
			System.out.println("Success: Submit Button Clicked");

			wait.until(ExpectedConditions.invisibilityOf(submit));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*** Check if the Success Message is Displayed in the Frame ***/
	public boolean checkIfSuccessMessageIsDisplayed() {

		boolean isDisplayed = false;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(loadingText));

			WebElement success = wait.until(ExpectedConditions
					.visibilityOfElementLocated(successMessage));

			String actualText = success.getText();
			String expectedText = "Thank you for updating your email preferences.";

			if (actualText.equals(expectedText)) {
				System.out.println("Success: Success Message '" + actualText
						+ "' is displayed");
				isDisplayed = true;
			} else {
				System.out.println("Success: Sucess Message '" + actualText
						+ "' displayed is NOT correct");
				isDisplayed = false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			isDisplayed = false;
		}
		return isDisplayed;
	}

	/*** Click Cancel Button for Email Subscription ***/
	public void clickCancelForEmailSubscription() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement cancel = wait.until(ExpectedConditions
					.elementToBeClickable(cancelBtn));

			cancel.click();
			System.out
					.println("Success: Cancel Button Email Subscription clicked");

			wait.until(ExpectedConditions.invisibilityOf(cancel));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}
}
