package regressionTests;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageClasses.BrandsElements;
import PageClasses.DigitalCouponsPage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class BrandsTest extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	DigitalCouponsPage digitalCouponsPage;
	BrandsElements brandsPage;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);

		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		digitalCouponsPage = new DigitalCouponsPage(driver);
		brandsPage = new BrandsElements(driver);
	}

	@Test
	@Parameters("environment")
	public void brandsExtraction(String environment) {

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

		landPage.clickDigitalCouponsButton();

		landPage.waitForFrameToLoadOrDoRefresh();

		digitalCouponsPage.clickClippedLink();

		digitalCouponsPage.clickUnClipForAllClippedCoupons();

		digitalCouponsPage.clickAllCoupons();

		String[] brandsInPage = brandsPage.getAllBrandsFromPage();

		brandsInPage = brandsPage.extractOnlyBrandName(brandsInPage);

		String[] brandsInExcel = brandsPage.readAllBrandsFromExcel();

		List<String> brandsNotSorted = brandsPage
				.getBrandsNotSorted(brandsInPage);

		brandsPage.updateBrandsNotSorted(brandsNotSorted);

		List<String> brandsNotInPage = brandsPage.getBrandsNotInPage(
				brandsInPage, brandsInExcel);

		brandsPage.updateBrandsNotInPage(brandsNotInPage);

		List<String> brandsNotInExcel = brandsPage.getBrandsNotInExcel(
				brandsInPage, brandsInExcel);

		brandsPage.updateBrandsNotInExcel(brandsNotInExcel);

		switchToDefaultFrame();

		landPage.clickAccountHeaderButton();

		landPage.clickSignOutButton();

	}

	@AfterClass
	/********** Close the Browser ***********/
	public void closeAllTheBrowser() {

		flushReports();
	}
}
