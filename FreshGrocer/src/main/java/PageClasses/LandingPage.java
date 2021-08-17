package PageClasses;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.ReadExcelDataFile;
import baseClasses.PageBaseClass;

public class LandingPage extends PageBaseClass {

	Hashtable<String, String> locators;

	public By signInBtn;
	public By popUpBtn;
	public By loginToLoadBtn;
	public By accountHeaderBtn;
	public By signInOrRegisterText;
	public By myAccountText;
	public By digitalCouponsLink;
	public By couponsFrame;
	public By loadingCouponsText;
	public By showAllBtn;
	public By resetBtn;
	public By couponsList;
	public By couponsNameList;
	public By couponsDiscountList;
	public By couponsExpiryDateList;
	public By couponName;
	public By loadToCardBtnClass;
	public By loadToCardBtnXpath;
	public By loadedText;
	public By unClipBtn;

	public LandingPage(WebDriver driver) {
		super(driver);
		locators = readLocators("landing_page");
		assignValuesForLocatorsFromExcel();
	}

	// @FindBy(className = "login-to-load")
	// public WebElement loginToLoadBtn;
	//
	// @FindBy(xpath = "//*[@id='AccountHeaderButton']")
	// public WebElement signInBtn;
	//
	// @FindBy(css = ".available-to-clip.ng-star-inserted")
	// public WebElement LoadToCardBtn;
	//
	// @FindBy(xpath = "//button[contains(text(),'Show All')]")
	// public WebElement showAllBtn;
	//
	// @FindBy(xpath = "//button[contains(text(),'Reset')]")
	// public WebElement resetBtn;
	//
	// @FindBy(className = "coupon-item-container")
	// public List<WebElement> couponsList;
	//
	// @FindBy(className = "coupon-brand-name")
	// public List<WebElement> couponsNameList;
	//
	// @FindBy(xpath = "//div[@class='coupon-value']/p")
	// public List<WebElement> couponsDiscountList;
	//
	// @FindBy(className = "coupon-expiration-text")
	// public List<WebElement> couponsExpiryDateList;

	@FindBy(className = "available-to-clip")
	public List<WebElement> loadToCardBtn;

	/****** Assign values to the By locators **********/
	public void assignValuesForLocatorsFromExcel() {
		signInBtn = getByLocator(locators, ("signInBtn_xpath"));
		popUpBtn = getByLocator(locators, ("popUpBtn_xpath"));
		loginToLoadBtn = getByLocator(locators, ("loginToLoadBtn_classname"));
		accountHeaderBtn = getByLocator(locators, ("accountHeaderBtn_xpath"));
		signInOrRegisterText = getByLocator(locators,
				("signInOrRegisterText_xpath"));
		myAccountText = getByLocator(locators, ("myAccountText_xpath"));
		digitalCouponsLink = getByLocator(locators,
				("digitalCouponsLink_xpath"));
		couponsFrame = getByLocator(locators, ("couponsFrame_id"));
		loadingCouponsText = getByLocator(locators,
				("loadingCouponsText_xpath"));
		showAllBtn = getByLocator(locators, ("showAllBtn_xpath"));
		resetBtn = getByLocator(locators, ("resetBtn_xpath"));
		couponsList = getByLocator(locators, ("couponsList_classname"));
		couponsNameList = getByLocator(locators, ("couponsNameList_classname"));
		couponsDiscountList = getByLocator(locators,
				("couponsDiscountList_xpath"));
		couponsExpiryDateList = getByLocator(locators,
				("couponsExpiryDateList_classname"));
		couponName = getByLocator(locators, ("couponName_xpath"));
		loadToCardBtnClass = getByLocator(locators, ("loadToCardBtn_classname"));
		loadToCardBtnXpath = getByLocator(locators, ("loadToCardBtn_xpath"));
		loadedText = getByLocator(locators, ("loadedText_xpath"));
		unClipBtn = getByLocator(locators, ("unClipBtn_xpath"));
	}

	/********* Read All the Object Locators from Excel ********/
	public void loadAllLocators() {
		locators = readLocators("landing_page");
	}

	/************** Get Login To Load Text ****************/
	public void getLoginToLoadText(String expectedText) {

		try {
			String actualText = driver.findElement(loginToLoadBtn).getText();
			Assert.assertEquals(actualText, expectedText);
			reportPass("Actual Text : " + actualText
					+ " - equals to Expected Text : " + expectedText);
			System.out.println("Coupon Button Text is " + expectedText);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/********* Close the Popup id exists **************/
	public void closePopUp() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(popUpBtn));
			driver.findElement(popUpBtn).click();
			System.out.println("PopUp Closed");
		} catch (NoSuchElementException e) {
			System.out.println("Pop did not exists");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Check if Sign In is present before Signing In ********/
	public void checkIfSignInIsPresent() {
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(accountHeaderBtn));

			String expectedText = "Sign In or Register";
			String actualText = driver.findElement(signInOrRegisterText)
					.getText();
			Assert.assertEquals(actualText, expectedText);
			System.out.println("Sign In button is Present");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********* Click in the SignIn Button ************/
	public LoginPage clickSignIn() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(signInBtn));
			driver.findElement(signInBtn).click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		LoginPage logPage = new LoginPage(driver);
		PageFactory.initElements(driver, logPage);
		return logPage;

	}

	/********* Check if My Account is present after Signing In ********/
	public void checkIfMyAccountIsPresent() {
		try {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(myAccountText));
			String myAccounText = driver.findElement(myAccountText).getText();
			System.out.println(myAccounText + " text is present");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*********** Click Digital Coupons Link **********/
	public void clickDigitalCouponsButton() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(digitalCouponsLink));
			driver.findElement(digitalCouponsLink).click();
			System.out.println("Digital Coupons Clicked");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/**********
	 * Wait for Switching to coupons frame and for Loading Coupons... to
	 * disappear
	 *****/
	public void waitForFrameToLoadOrDoRefresh() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(couponsFrame));

			System.out.println("Switched to the Frame");

			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(loadingCouponsText));

			System.out
					.println("Loading Coupons... is invisible and the coupons are being visible");

		} catch (NoSuchElementException e) {
			try {
				driver.navigate().refresh();
				System.out
						.println("Page refreshes because the Coupons frame was loading more than 90 seconds");
				driver.findElement(digitalCouponsLink).click();

				WebDriverWait wait = new WebDriverWait(driver, 90);
				wait.until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(couponsFrame));
				System.out.println("Switched to the Frame");

				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(loadingCouponsText));
			} catch (Exception e1) {
				reportFail(e1.getMessage());
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/************** Get Load To Card Text ****************/
	public void getLoadToCardButtonText(String expectedText) {

		try {
			String actualText = driver.findElement(loadToCardBtnClass)
					.getText();
			Assert.assertEquals(actualText, expectedText);
			reportPass("Actual Text : " + actualText
					+ " - equals to Expected Text : " + expectedText);
			System.out.println("Coupon Button Text is " + expectedText);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/******** Click Show All Button *************/
	public void clickShowAll() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(showAllBtn));
			driver.findElement(showAllBtn).click();
			System.out.println("Show All Button Clicked");

			wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
					resetBtn, "Reset"));
			String expectedText = "Reset";
			String actualText = driver.findElement(resetBtn).getText();
			Assert.assertEquals(actualText, expectedText);
			System.out.println("Text Changed to Reset");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/**** Get All Coupons and Store in an Array ******/
	public String[][] getAllCouponsFromPage() {

		List<WebElement> couponContainer = driver.findElements(couponsList);
		String[][] couponsArray = new String[couponContainer.size()][4];
		try {

			for (int index = 0; index < couponContainer.size(); index++) {

				scrollToElement(couponContainer.get(index));

				String brandName = driver.findElements(couponsNameList)
						.get(index).getText();
				String discountPrice = driver.findElements(couponsDiscountList)
						.get(index).getText();
				String expiryDate = driver.findElements(couponsExpiryDateList)
						.get(index).getText();

				couponsArray[index][0] = "" + (index + 1);
				couponsArray[index][1] = brandName;
				couponsArray[index][2] = discountPrice;
				couponsArray[index][3] = expiryDate;

				System.out.println(brandName + " " + discountPrice + " "
						+ expiryDate);
				System.out.println("Coupon number " + (index + 1)
						+ " read successfully");
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return couponsArray;

	}

	/****** Check if all the coupons have "Load To Card" button *********/
	public void checkCouponsLoadToCardText() {
		try {
			List<WebElement> couponContainer = driver.findElements(couponsList);

			for (int index = 0; index < couponContainer.size(); index++) {
				scrollToElement(couponContainer.get(index));

				String loadTocardString = couponContainer.get(index)
						.findElement(By.tagName("a")).getText().trim();
				System.out.println("Load to card is present at coupon number "
						+ (index + 1));

				Assert.assertEquals(loadTocardString, "Load To Card");
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/********
	 * Clearing all the existing row in the Data except First row (column names)
	 **********/
	public void clearDataInExcel() {
		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir")
						+ "/src/main/resources/TestData/"
						+ "couponDetails.xlsx");
		try {
			readData.clearExistingDataInSheet("name_discount_expiry", 2);
			System.out.println("All data cleared in the sheet");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****** Set All data into Excel ********/
	public void setAllCouponDataInExcel(String[][] couponsArray) {

		try {

			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "couponDetails.xlsx");

			for (int index = 0; index < couponsArray.length; index++) {

				String number = couponsArray[index][0];
				String brandName = couponsArray[index][1];
				String discountPrice = couponsArray[index][2];
				String expiryDate = couponsArray[index][3];

				boolean flag = readData.setCellData("name_discount_expiry",
						"Coupon Number", index + 2, number);
				Assert.assertEquals(flag, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag1 = readData.setCellData("name_discount_expiry",
						"Coupon Name", index + 2, brandName);
				Assert.assertEquals(flag1, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag2 = readData.setCellData("name_discount_expiry",
						"Coupon Discount Price", index + 2, discountPrice);
				Assert.assertEquals(flag2, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag3 = readData.setCellData("name_discount_expiry",
						"Coupon Expiry Date", index + 2, expiryDate);
				Assert.assertEquals(flag3, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				System.out.println("Details of Coupon " + (index + 1)
						+ " updated: " + brandName + ", " + discountPrice
						+ ", " + expiryDate);
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/******** Set wrong values in Excel **********/
	public void setWrongCouponDataInExcel(String brandName,
			String discountPrice, String expiryDate) {

		try {

			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "couponDetails.xlsx");

			List<WebElement> couponContainer = driver.findElements(couponsList);

			String couponNumberString = String
					.valueOf(couponContainer.size() + 1);
			boolean flag = readData.setCellData("name_discount_expiry",
					"Coupon Number", couponContainer.size() + 2,
					couponNumberString);
			Assert.assertEquals(true, flag);

			boolean flag1 = readData.setCellData("name_discount_expiry",
					"Coupon Name", couponContainer.size() + 2, brandName);
			Assert.assertEquals(true, flag1);

			boolean flag2 = readData.setCellData("name_discount_expiry",
					"Coupon Discount Price", couponContainer.size() + 2,
					discountPrice);
			Assert.assertEquals(true, flag2);

			boolean flag3 = readData.setCellData("name_discount_expiry",
					"Coupon Expiry Date", couponContainer.size() + 2,
					expiryDate);
			Assert.assertEquals(true, flag3);

			System.out.println("Details of Coupon "
					+ (couponContainer.size() + 1) + " updated: " + brandName
					+ ", " + discountPrice + ", " + expiryDate);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/******** Get Coupon Data *******/
	public void getCouponDataInExcel(String expectedCouponNumber,
			String expectedBrandName, String expectedDiscountPrice,
			String expectedExpiryDate) {

		try {
			System.out.println("Checking the details of coupon number "
					+ expectedCouponNumber);
			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "couponDetails.xlsx");

			List<WebElement> couponContainer = driver.findElements(couponsList);

			for (int index = 0; index < couponContainer.size(); index++) {

				scrollToElement(couponContainer.get(index));

				String actualBrandName = driver.findElements(couponsNameList)
						.get(index).getText();
				String actualDiscountPrice = driver
						.findElements(couponsDiscountList).get(index).getText();
				String actualExpiryDate = driver
						.findElements(couponsExpiryDateList).get(index)
						.getText();

				boolean flag = (expectedBrandName
						.equalsIgnoreCase(actualBrandName)
						&& expectedDiscountPrice
								.equalsIgnoreCase(actualDiscountPrice) && expectedExpiryDate
						.equalsIgnoreCase(actualExpiryDate));

				if (flag) {

					readData.setCellData("name_discount_expiry", "Status",
							index + 2, "Pass");
					System.out.println(expectedBrandName + " exists");

					return;
				}

			}

			readData.setCellData("name_discount_expiry", "Status",
					Integer.valueOf(expectedCouponNumber) + 1, "Fail");
			System.out.println(expectedBrandName + " not found");

		} catch (Exception e) {
			reportFail(e.getMessage());

		}

	}

	/******** Check if the Load To Card button changes to Unclip ************/
	public void clickLoadToCardAndVerifyChanges(String randomCouponName) {

		try {

			List<WebElement> couponsContainer = driver
					.findElements(couponsList);

			for (int index = 0; index < couponsContainer.size(); index++) {
				WebElement couponContainer = couponsContainer.get(index);

				scrollToElement(couponContainer);

				String brandName = couponContainer.findElement(couponName)
						.getText();
				if (brandName.equals(randomCouponName)) {

					WebDriverWait wait = new WebDriverWait(driver, 10);
					WebElement loadToCard = couponContainer
							.findElement(loadToCardBtnXpath);

					wait.until(ExpectedConditions
							.elementToBeClickable(loadToCard));
					loadToCard.click();

					wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.textToBePresentInElement(
							couponContainer.findElement(unClipBtn), "Unclip"));

					String expectedText = "Loaded -Unclip";

					String actualText = couponContainer.findElement(loadedText)
							.getText()
							+ couponContainer.findElement(unClipBtn).getText();

					wait.until(ExpectedConditions
							.elementToBeClickable(couponContainer
									.findElement(unClipBtn)));
					couponContainer.findElement(unClipBtn).click();
					Assert.assertEquals(actualText, expectedText);
					return;

				}
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
}
