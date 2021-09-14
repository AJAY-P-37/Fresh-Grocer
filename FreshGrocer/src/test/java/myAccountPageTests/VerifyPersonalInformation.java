package myAccountPageTests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.MyAccountPage;
import PageClasses.PersonalInformationForm;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class VerifyPersonalInformation extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	MyAccountPage myAccountPage;
	PersonalInformationForm personalInfoForm;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);

		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		myAccountPage = new MyAccountPage(driver);
		personalInfoForm = new PersonalInformationForm(driver);
	}

	@Test
	@Parameters("environment")
	public void verifyPersonalInformationByEditing(String environment) {

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

		boolean successValidated = false;
		int count = 0, maxAttempts = 3;
		do {
			myAccountPage.clickPersonalInformationEditBtn();

			personalInfoForm.selectMembersInHousehold();

			personalInfoForm.enterBirthDate();

			personalInfoForm.selectGenderRadioBtn();

			personalInfoForm.selectReceiveTextMessagesRadioBtn();

			personalInfoForm.selectReceivePromotionalMailingsRadioBtn();

			personalInfoForm.selectReceiveDigitalReceiptsRadioBtn();

			personalInfoForm.selectReceiveEmailForDigitalReceiptsRadioBtn();

			personalInfoForm.clickUpdatePersonalInfoBtn();

			successValidated = myAccountPage.validatePersonalInfoUpdatedSuccessMessage();
			
			if (successValidated) {

				System.out.println("Sucess Message Validated in attempt no. "
						+ (count + 1));
				break;
			} else {
				System.out
						.println("Sucess Message is NOT Validated in attempt no. "
								+ (count + 1));
				refreshPage();
			}
			count++;
			if (count == maxAttempts) {

				System.out.println("Sucess Message NOT Validated even after "
						+ count + " attempts");
				reportFail("Sucess Message NOT Validated even after " + count
						+ " attempts");
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
