package regressionTests;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.RandomUtil;
import utilities.TestDataProvider;
import PageClasses.LandingPage;
import PageClasses.LoginPage;
import baseClasses.BaseTestClass;
import baseClasses.PageBaseClass;

public class CouponDetailsTest extends BaseTestClass {

	int count = 1;
	
	@BeforeClass
	@Parameters("browser")
	public void  openBrowser(String browser){
		
		invokeBrowser(browser);
		
	}
	
	
	@Test
	@Parameters("environment")
	public void couponDetailsExtraction(String environment) {

		PageBaseClass basePage = new PageBaseClass(driver);
		PageFactory.initElements(driver, basePage);
		LandingPage landPage = basePage.openApplication(environment);
		landPage.closePopUp();
		
		landPage.checkIfSignInIsPresent();
		
		LoginPage logPage = landPage.clickSignIn();

		landPage = logPage.enterCredentials(environment);

		landPage.checkIfMyAccountIsPresent();

		landPage.clickDigitalCouponsButton();
		
		landPage.waitForFrameToLoadOrDoRefresh();
		
		landPage.clickShowAll();

		String[][] coupons = landPage.getAllCouponsFromPage();
		
		landPage.checkCouponsLoadToCardText();
		
		landPage.clearDataInExcel();
		
		landPage.setAllCouponDataInExcel(coupons);
		
		String randomName = RandomUtil.getRandomStringOfLength(10);
		String randomDiscount = RandomUtil.getRandomStringOfLength(10);
		String randomExpiry = RandomUtil.getRandomStringOfLength(10);
		System.out.println("random Data: " + randomName + " " + randomDiscount + " " + randomExpiry);
		landPage.setWrongCouponDataInExcel(randomName, randomDiscount, randomExpiry);
		
		int randomNumber = RandomUtil.getRandomNumberBetween(0, coupons.length-1);
		System.out.println(randomNumber);
		String couponName = coupons[randomNumber][1];
		System.out.println(couponName);
		landPage.clickLoadToCardAndVerifyChanges(couponName);

	}
	
	@Test(dependsOnMethods={"couponDetailsExtraction"}, dataProvider = "getCouponDetailsData")
	public void verifyCouponDetails(Hashtable<String, String> testData) {

		LandingPage landPage = new LandingPage(driver);
		PageFactory.initElements(driver, landPage);
		landPage.getCouponDataInExcel(testData.get("Coupon Number"),
				testData.get("Coupon Name"),
				testData.get("Coupon Discount Price"),
				testData.get("Coupon Expiry Date"));
	
	}

	@DataProvider
	public Object[][] getCouponDetailsData() {
		Object[][] obj = new Object[6][1];
		Object[][] excelObj = TestDataProvider.getTestData("couponDetails.xlsx",
				"name_discount_expiry", "pageLogin");
		
		for(int index = 0;index<5;index++){
			obj[index][0] = excelObj[index][0];
		}
		obj[5][0] = excelObj[excelObj.length-1][0];
		return obj;
	}

	@AfterClass
	/********** Close the Browser ***********/
	public void closeAllTheBrowser() {

		driver.quit();
	}
}
