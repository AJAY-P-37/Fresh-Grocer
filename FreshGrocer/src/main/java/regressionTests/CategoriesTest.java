package regressionTests;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageClasses.CategoriesElements;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class CategoriesTest extends BaseTestClass {

	PageBaseClass basePage;
	LoginPage logPage;
	LandingPage landPage;
	CategoriesElements categoriesPage;

	@BeforeClass
	@Parameters("browser")
	public void openBrowser(String browser) {

		invokeBrowser(browser);

		logPage = new LoginPage(driver);
		basePage = new PageBaseClass(driver);
		landPage = new LandingPage(driver);
		categoriesPage = new CategoriesElements(driver);
	}

	@Test
	@Parameters("environment")
	public void categoriesExtraction(String environment) {

		basePage.openApplication(environment);

		landPage.closePopUp();

		landPage.checkIfSignInIsPresent();

		landPage.clickSignIn();

		logPage.enterCredentials(environment);

		landPage.checkIfMyAccountIsPresent();

		landPage.clickDigitalCouponsButton();

		landPage.waitForFrameToLoadOrDoRefresh();

		String[] categoriesInPage = categoriesPage.getAllCategoriesFromPage();

		categoriesInPage = categoriesPage
				.extractOnlyCategoryName(categoriesInPage);

		String[] categoriesInExcel = categoriesPage
				.readAllCategoriesFromExcel();

		List<String> categoriesNotSorted = categoriesPage
				.getCategoriestNotSorted(categoriesInPage);

		categoriesPage.updateCategoriesNotSorted(categoriesNotSorted);

		List<String> categoriesNotInPage = categoriesPage
				.getCategoriesNotInPage(categoriesInPage, categoriesInExcel);

		categoriesPage.updateCategoriesNotInPage(categoriesNotInPage);

		List<String> categoriesNotInExcel = categoriesPage
				.getCategoriesNotInExcel(categoriesInPage, categoriesInExcel);

		categoriesPage.updateCategoriesNotInExcel(categoriesNotInExcel);

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
