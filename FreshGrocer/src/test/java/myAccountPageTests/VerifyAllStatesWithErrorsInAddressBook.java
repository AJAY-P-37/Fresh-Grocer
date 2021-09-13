package myAccountPageTests;

import java.util.ArrayList;
import java.util.List;

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

public class VerifyAllStatesWithErrorsInAddressBook extends BaseTestClass {

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

	public void fillAddressBook() {

		myAccountPage.clickAddressBookEditBtn();

		myAccountPage.clickAddNewAddressBtn();

		addressPage.enterFirstName();

		addressPage.enterLastName();

		addressPage.enterAddress();

		addressPage.enterCity();

		addressPage.enterZipcode();

		addressPage.enterPrimaryPhone();
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

		String firstName = null, lastName = null, address = null, city = null, state = null, zipcode = null, primaryPhone = null;
		boolean successValidated = false;
		List<WebElement> statesList = null;

		List<String> states = new ArrayList<String>(), statesWithError = new ArrayList<String>();
		int count = 0;
		do {

			System.out.println("Checking for State no. : " + (count + 1));
			if (count == 0) {

				fillAddressBook();

				statesList = addressPage.getAllStates();

				states = addressPage
						.extractingTextFromStatesDropDown(statesList);

			}

			state = addressPage.selectStateDropDown(states.get(count));

			String expectedErrorMessageForState, actualErrorMessageForState;

			do {
				addressPage.clickSaveAddressBtn();

				successValidated = myAccountPage
						.validateAdrressUpdatedSuccessMessage();

				if (successValidated) {

					System.out.println("Success Message Validated for state "
							+ state);
					if (count == states.size() - 1) {
						break;
					}
					myAccountPage.clickLastAddressEditBtn();
					break;
				} else {
					System.out
							.println("Success Message is NOT Validated for state "
									+ state);

					expectedErrorMessageForState = "The following fields are in error: State";
					actualErrorMessageForState = addressPage
							.getValidationTextForAddress();

					if (expectedErrorMessageForState
							.equals(actualErrorMessageForState)) {

						System.out
								.println("Error Occured for selecting the State "
										+ state + ". Selecting another state");
						statesWithError.add(state);
						count++;
						if (count == states.size()) {
							break;
						}
						state = addressPage.selectStateDropDown(states
								.get(count));
					} else {

						boolean addressEditBtnIsPresent = myAccountPage
								.checkIfAddressBookEditBtnIsPresent();

						if (addressEditBtnIsPresent) {

							fillAddressBook();

						} else {
							System.out
									.println("Some other Error occured for the state "
											+ state);
							reportFail("Some other Error occured for the state "
									+ state);
						}

					}
				}
			} while (expectedErrorMessageForState
					.equals(actualErrorMessageForState));

			System.out.println("states with error:" + statesWithError);

		} while (++count < states.size());

		myAccountPage.clickLastAddressDeleteBtn();

		myAccountPage.clickHomeLink();

		landPage.checkIfMyAccountIsPresent();

		landPage.clickAccountHeaderButton();

		landPage.clickSignOutButton();

		if (statesWithError.size() > 0) {

			System.out.println("States with error:" + statesWithError);

			addressPage.updateStatesWithErrorInExcel(statesWithError);

			reportFail("states with error:" + statesWithError);
		} else {
			System.out.println("No states are with error");
		}

	}

	@AfterClass
	/********** Close the Browser ***********/
	public void closeAllTheBrowser() {

		flushReports();
	}
}
