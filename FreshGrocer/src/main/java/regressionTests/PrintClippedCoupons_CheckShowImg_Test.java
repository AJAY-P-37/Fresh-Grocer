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

public class PrintClippedCoupons_CheckShowImg_Test extends BaseTestClass {

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

		int randomNumber = RandomUtil.getRandomNumberBetween(0,
				numberOfCoupons - 1);

		String[] randomCouponClipped = digitalCouponsPage
				.getDetailsOfRandomCoupon(randomNumber);

		digitalCouponsPage.clickLoadToCardOfRandomCoupon(randomNumber);

		digitalCouponsPage.verifyChangesInLoadCardBtn(randomNumber);

		digitalCouponsPage.clickPrintClippedCouponsButton();

		digitalCouponsPage
				.checkClippedCouponIsPresentInPrintTab(randomCouponClipped[0]);

		digitalCouponsPage.checkShowImagesCheckBoxIfNotChecked();

		digitalCouponsPage.checkIfImageIsDisplayed();

		digitalCouponsPage.checkIfCouponDetailsIsDisplayedForChecked(randomCouponClipped);

		digitalCouponsPage.clickCloseInPrintTab();

		digitalCouponsPage.clickClippedLink();

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