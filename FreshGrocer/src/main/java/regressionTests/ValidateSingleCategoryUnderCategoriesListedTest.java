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

public class ValidateSingleCategoryUnderCategoriesListedTest extends
		BaseTestClass {

	
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


		digitalCouponsPage.checkIfAllCouponsIsClicked();

		digitalCouponsPage.clickShowAll();

		digitalCouponsPage.checkCouponsLoadToCardText();

		String[] categoriesInPage = digitalCouponsPage
				.getAllCategoriesFromPage();

		int randomCategoryNumber = RandomUtil.getRandomNumberBetween(0,
				categoriesInPage.length - 1);

		String randomCategoryName = digitalCouponsPage.clickRandomCategoryAndGetCategoyName(randomCategoryNumber);

		int expectedNumberOfCouponsInRandomCategory = digitalCouponsPage
				.extractNumberOfCouponsInRandomCategory(randomCategoryName);

		digitalCouponsPage
				.checkIfNumberCouponsPresentAreCorrect(expectedNumberOfCouponsInRandomCategory);

		String[][] couponsArray = digitalCouponsPage
				.getCouponsInCheckedCategoryFromPage();

		digitalCouponsPage.setAllCouponDataInExcel(couponsArray);

		switchToParentFrame();

		landPage.clickAccountHeaderButton();

		landPage.clickSignOutButton();

	}

	@AfterClass
	public void closeAllTheBrowser() {

		flushReports();
	}

}
