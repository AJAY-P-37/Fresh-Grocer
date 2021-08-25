package regressionTests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.RandomUtil;
import PageClasses.DigitalCouponsPage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class ClippedCouponExistsTest extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	DigitalCouponsPage digitalCouponsPage;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);
		
		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		digitalCouponsPage = new DigitalCouponsPage(driver);

	}

	@Test
	@Parameters("environment")
	public void clippingCoupons(String environment) {

		basePage.openApplication(environment);

		landPage.closePopUp();

		landPage.checkIfSignInIsPresent();

		landPage.clickSignIn();

		logPage.enterCredentials(environment);

		landPage.checkIfMyAccountIsPresent();

		landPage.clickDigitalCouponsButton();

		landPage.waitForFrameToLoadOrDoRefresh();

		digitalCouponsPage.clickShowAll();

		digitalCouponsPage.checkCouponsLoadToCardText();

		int numberOfCoupons = digitalCouponsPage.getNumberOfCoupons();

		int randomNumber = digitalCouponsPage.clickLoadToCardOfRandomCoupon();

		digitalCouponsPage.verifyChangesInLoadCardBtn(randomNumber);
		
		String[] randomCouponClipped = digitalCouponsPage
				.getDetailsOfRandomCoupon(randomNumber);

		digitalCouponsPage.clickClippedLink();

		digitalCouponsPage.verifyCouponExistsInClipped(randomCouponClipped);

		digitalCouponsPage.clickUnClipBtn();

		switchToParentFrame();

		landPage.clickAccountHeaderButton();

		landPage.clickSignOutButton();

	}

	@AfterClass
	/********** Close the Browser ***********/
	public void closeAllTheBrowser() {

		flushReports();
	}

}
