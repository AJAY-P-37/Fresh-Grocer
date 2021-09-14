package myAccountPageTests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.MyAccountPage;
import PageClasses.MyProfileForm;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class VerifyStateWithHomeTown extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	MyAccountPage myAccountPage;
	MyProfileForm profilePage;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);

		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		myAccountPage = new MyAccountPage(driver);
		profilePage = new MyProfileForm(driver);
	}

	@Test
	@Parameters("environment")
	public void validateMessageForInvalidPreferredContactType(String environment) {

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

		myAccountPage.clickEditProfileBtn();

		String state = null, homeStore = null;
		int count = 0, maxAttempts = 100;
		do {

			state = profilePage.selectState();

			homeStore = profilePage.selectHomeStore();

			if (homeStore != null) {
				System.out.println("Success: Home Store " + homeStore
						+ " is selected. for State " + state
						+ " in attempt no. " + (count + 1));

				profilePage.clickUpdateProfileBtnForValidScenario();

				boolean successValidated = myAccountPage
						.validateProfileUpdatedSuccessMessage();

				if (successValidated) {

					System.out
							.println("Success Message Validated in attempt no. "
									+ (count + 1));
					break;
				} else {
					System.out
							.println("Success Message is NOT Validated in attempt no. "
									+ (count + 1));
					refreshPage();
					
					myAccountPage.clickEditProfileBtn();
				}

			} else {
				System.out.println("Home Store is NOT found for State " + state
						+ " in attempt no. " + (count + 1) + ". Trying again");
			}
			count++;
			if (count == maxAttempts) {

				System.out
						.println("Home Store is NOT found for State even after "
								+ count + " attempts");
				reportFail("Home Store is NOT found for State even after "
						+ count + " attempts");
			}

		} while (count <= maxAttempts);

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
