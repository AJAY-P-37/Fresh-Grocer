package regressionTests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageClasses.DigitalCouponsPage;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import PageClasses.PrintTabPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class PrintClippedCoupons_CheckShowImg_Test extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	DigitalCouponsPage digitalCouponsPage;
	PrintTabPage printPage;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);

		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		digitalCouponsPage = new DigitalCouponsPage(driver);
		printPage = new PrintTabPage(driver);

	}

	@Test
	@Parameters("environment")
	public void printingClippedCouponsWithShowImagesChecked(String environment) {

		basePage.openApplication(environment);

		landPage.closePopUp();

		boolean present = landPage.checkIfSignInIsPresent();
		if(present){
			landPage.clickSignIn();

			logPage.enterCredentials(environment);
		}else{
			
			present = landPage.checkIfMyAccountIsPresent();
			if(present){
				
				landPage.clickAccountHeaderButton();

				landPage.clickSignOutButton();
				
				present = landPage.checkIfSignInIsPresent();
				if(present){
					landPage.clickSignIn();

					logPage.enterCredentials(environment);
				}
			}
		}
		
		present = landPage.checkIfMyAccountIsPresent();

		landPage.clickDigitalCouponsButton();

		landPage.waitForFrameToLoadOrDoRefresh();

		digitalCouponsPage.clickClippedLink();

		digitalCouponsPage.clickUnClipForAllClippedCoupons();

		digitalCouponsPage.clickAllCoupons();

		digitalCouponsPage.clickShowAll();

		digitalCouponsPage.checkCouponsLoadToCardText();

		digitalCouponsPage.getNumberOfCoupons();

		int randomNumber = digitalCouponsPage.clickLoadToCardOfRandomCoupon();

		digitalCouponsPage.verifyChangesInLoadCardBtn(randomNumber);

		String[] randomCouponClipped = digitalCouponsPage
				.getDetailsOfRandomCoupon(randomNumber);

		digitalCouponsPage.clickPrintClippedCouponsButton();

		int indexOfClippedCoupon = printPage
				.checkClippedCouponIsPresentInPrintTab(randomCouponClipped[0]);

		printPage.checkShowImagesCheckBoxIfNotChecked();

		printPage.checkIfImageIsDisplayed(indexOfClippedCoupon);

		printPage
				.checkIfCouponDetailsIsDisplayedForChecked(randomCouponClipped, indexOfClippedCoupon);

		printPage.clickCloseInPrintTab();

		digitalCouponsPage.clickClippedLink();

		digitalCouponsPage.clickUnClipBtnForClippedCoupon(randomCouponClipped);

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
