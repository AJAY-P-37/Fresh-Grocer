package myAccountPageTests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageClasses.AddEditAddressPage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.MyAccountPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class ValidateErronicStatesInDropDown extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	MyAccountPage myAccountPage;
	AddEditAddressPage addressPage;

	int addressIndex = 0;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);

		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		myAccountPage = new MyAccountPage(driver);
		addressPage = new AddEditAddressPage(driver);
	}

	@Test
	@Parameters("environment")
	public void verifyAllStatesWithErrorsInAddressBook(String environment) {

		basePage.openApplication(environment);

		landPage.closePopUp();

		boolean present = landPage.checkIfSignInIsPresent();
		if (present) {
			landPage.clickSignIn();

			logPage.enterCredentials(environment);
		} else {

			present = landPage.checkIfMyAccountIsPresent();
			if (present) {

				landPage.clickAccountHeaderButton();

				landPage.clickSignOutButton();

				present = landPage.checkIfSignInIsPresent();
				if (present) {
					landPage.clickSignIn();

					logPage.enterCredentials(environment);
				}
			} else {
				refreshPage();
			}
		}

		present = landPage.checkIfMyAccountIsPresent();

		landPage.clickAccountHeaderButton();

		landPage.clickMyAccountSettingLink();

		myAccountPage.checkIfMyAccountTitleIsPresent();

		myAccountPage.clickAddressBookEditBtn();

		myAccountPage.clickAddNewAddressBtn();

		List<WebElement> statesList = addressPage.getAllStates();

		List<String> states = addressPage
				.extractingTextFromStatesDropDown(statesList);

		addressPage.clickCancelAddressBtn();

		myAccountPage.clickHomeLink();

		landPage.checkIfMyAccountIsPresent();

		landPage.clickAccountHeaderButton();

		landPage.clickSignOutButton();

		List<String> duplicateStates = addressPage
				.findDuplicatesInStatesDropDown(states);

		if (duplicateStates.size() > 0) {
			System.out.println("States which are duplicated : "
					+ duplicateStates);

			addressPage.updateDuplicateStatesInExcel(duplicateStates);

			reportFail("States which are duplicated : " + duplicateStates);
		} else {
			
			System.out.println("No States are Duplicated");
		}

	}

	@AfterClass
	/********** Close the Browser ***********/
	public void closeAllTheBrowser() {

		flushReports();
	}

}
