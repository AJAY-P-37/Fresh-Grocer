package PageClasses;

import java.time.Duration;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

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
	public By cancelBtn;

	Wait<WebDriver> visibilityWait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(30)).pollingEvery(
					Duration.ofMillis(500)).ignoring(Exception.class);

	public EmailSubscriptionForm(WebDriver driver) {

		super(driver);
		locators = readLocators("add_edit_address_page");
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
		cancelBtn = getByLocator(locators, "cancelBtn_id");

	}

	/*** Check if FreshGrocer BrandLogo is Present At Top ***/
	public void checkIfFresheGrocerBrandLogoIsPresent() {

		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}
}
