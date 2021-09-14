package myAccountPageTests;

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

public class VerifyAddressBookByAddingNewAddress extends BaseTestClass {

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

	@Test(groups = { "addingAddress" })
	@Parameters("environment")
	public void verifyAddressBookByAddingNewAddress(String environment) {

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

		boolean successValidated = false, readyForAddressLineValidation = false;
		int count = 0, maxAttempts = 3;
		do {
			myAccountPage.clickAddressBookEditBtn();

			myAccountPage.clickAddNewAddressBtn();

			firstName = addressPage.enterFirstName();

			lastName = addressPage.enterLastName();

			address = addressPage.enterAddress();

			city = addressPage.enterCity();

			state = addressPage.selectStateDropDown();

			zipcode = addressPage.enterZipcode();

			primaryPhone = addressPage.enterPrimaryPhone();

			String expectedErrorMessageForState, actualErrorMessageForState;

			do {
				addressPage.clickSaveAddressBtn();

				successValidated = myAccountPage
						.validateAdrressUpdatedSuccessMessage();

				if (successValidated) {

					System.out
							.println("Sucess Message Validated in attempt no. "
									+ (count + 1));
					readyForAddressLineValidation = true;
					break;
				} else {
					System.out
							.println("Sucess Message is NOT Validated in attempt no. "
									+ (count + 1));

					expectedErrorMessageForState = "The following fields are in error: State";
					actualErrorMessageForState = addressPage
							.getValidationTextForAddress();

					if (expectedErrorMessageForState
							.equals(actualErrorMessageForState)) {

						System.out
								.println("Error Occured for selecting the State "
										+ state + ". Selecting another state");
						state = addressPage.selectStateDropDown();
					} else {
						System.out
								.println("NO Error Occured for selecting the State "
										+ state);
						break;
					}
				}
			} while (expectedErrorMessageForState
					.equals(actualErrorMessageForState));

			if(readyForAddressLineValidation){
				break;
			}
			count++;
			if (count == maxAttempts) {

				System.out.println("Sucess Message NOT Validated even after "
						+ count + " attempts");
				reportFail("Sucess Message NOT Validated even after " + count
						+ " attempts");
			}

		} while (count <= maxAttempts);

		addressIndex = myAccountPage.validateAddressLines(firstName, lastName,
				address, city, state, zipcode, primaryPhone);

		myAccountPage.clickHomeLink();

		landPage.checkIfMyAccountIsPresent();

		landPage.clickAccountHeaderButton();

		landPage.clickSignOutButton();

	}

	@AfterClass
	/********** Close the Browser ***********/
	public void closeAllTheBrowser() {

		flushReports();
	}

}
