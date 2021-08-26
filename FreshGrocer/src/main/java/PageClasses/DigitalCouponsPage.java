package PageClasses;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.RandomUtil;
import utilities.ReadExcelDataFile;
import baseClasses.PageBaseClass;

public class DigitalCouponsPage extends PageBaseClass {

	Hashtable<String, String> locators;

	public By loadingCouponsText;
	public By showAllBtn;
	public By resetBtn;
	public By loadToCardBtnClass;
	public By loadToCardBtnXpath;
	public By categoriesList;
	public By brandsList;
	public By couponsList;
	public By couponsNameList;
	public By couponsDiscountList;
	public By couponsExpiryDateList;
	public By couponName;
	public By couponDiscount;
	public By couponExpiryDate;
	public By couponDescription;
	public By loadToCardBtn;
	public By modalContent;
	public By modalContentCloseBtn;
	public By loadedText;
	public By unClipBtn;
	public By clippedLink;
	public By printClippedCouponBtn;
	public By clippedCouponName;
	public By clippedCouponDiscount;
	public By clippedCouponExpiryDate;
	public By clippedCouponImg;
	public By showImgesCheckBox;
	public By closeInPrintClippedCouponBtn;
	public By allCouponsLink;

	public DigitalCouponsPage(WebDriver driver) {
		super(driver);
		locators = readLocators("digital_coupons_page");
		assignValuesForLocatorsFromExcel();
	}

	/****** assigning the values for locators *********/
	public void assignValuesForLocatorsFromExcel() {

		loadingCouponsText = getByLocator(locators,
				("loadingCouponsText_xpath"));
		showAllBtn = getByLocator(locators, ("showAllBtn_xpath"));
		resetBtn = getByLocator(locators, ("resetBtn_xpath"));
		loadToCardBtnClass = getByLocator(locators, ("loadToCardBtn_classname"));
		loadToCardBtnXpath = getByLocator(locators, ("loadToCardBtn_xpath"));
		categoriesList = getByLocator(locators, "categoriesList_xpath");
		brandsList = getByLocator(locators, "brandsList_xpath");
		couponsList = getByLocator(locators, "couponsList_classname");
		couponsNameList = getByLocator(locators, ("couponsNameList_classname"));
		couponsDiscountList = getByLocator(locators,
				("couponsDiscountList_xpath"));
		couponsExpiryDateList = getByLocator(locators,
				("couponsExpiryDateList_classname"));
		couponName = getByLocator(locators, "couponName_xpath");
		couponDiscount = getByLocator(locators, "couponDiscount_xpath");
		couponExpiryDate = getByLocator(locators, "couponExpiryDate_xpath");
		couponDescription = getByLocator(locators, "couponDescription_xpath");
		loadToCardBtn = getByLocator(locators, "loadToCardBtn_xpath");
		modalContent = getByLocator(locators, "modalContent_xpath");
		modalContentCloseBtn = getByLocator(locators,
				"modalContentCloseBtn_xpath");
		loadedText = getByLocator(locators, "loadedText_xpath");
		unClipBtn = getByLocator(locators, "unClipBtn_xpath");
		clippedLink = getByLocator(locators, "clippedLink_xpath");
		printClippedCouponBtn = getByLocator(locators,
				"printClippedCouponBtn_xpath");
		clippedCouponName = getByLocator(locators, "clippedCouponName_xpath");
		clippedCouponDiscount = getByLocator(locators,
				"clippedCouponDiscount_xpath");
		clippedCouponExpiryDate = getByLocator(locators,
				"clippedCouponExpiryDate_xpath");
		clippedCouponImg = getByLocator(locators, "clippedCouponImg_xpath");
		showImgesCheckBox = getByLocator(locators, "showImgesCheckBox_id");
		closeInPrintClippedCouponBtn = getByLocator(locators,
				"closeInPrintClippedCouponBtn_xpath");
		allCouponsLink = getByLocator(locators, "allCouponsLink_xpath");

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

	/********* Get Number of Coupons Present in the Page ************/
	public int getNumberOfCoupons() {

		List<WebElement> couponContainer = driver.findElements(couponsList);

		return couponContainer.size();
	}

	/****** Getting Name, Discount and Expiry Date of the Coupon with random number ********/
	public String[] getDetailsOfRandomCoupon(int randomNumber) {

		String[] randomCouponDetails = new String[3];
		try {
			List<WebElement> couponsContainer = driver
					.findElements(couponsList);
			WebElement randomCoupon = couponsContainer.get(randomNumber);
			scrollToElement(randomCoupon);

			String brandName = randomCoupon.findElement(couponName).getText();
			String discount = randomCoupon.findElement(couponDiscount)
					.getText();
			String expiryDate = randomCoupon.findElement(couponExpiryDate)
					.getText();
			System.out.println("Randomly selected coupons: " + brandName + ", "
					+ discount + ", " + expiryDate);

			randomCouponDetails[0] = brandName;
			randomCouponDetails[1] = discount;
			randomCouponDetails[2] = expiryDate;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomCouponDetails;
	}

	/*********** Click Load to Card Button ********/
	public int clickLoadToCardOfRandomCoupon() {

		int numberOfCoupons = getNumberOfCoupons();
		int randomNumber = RandomUtil.getRandomNumberBetween(0,
				numberOfCoupons - 1);

		try {

			List<WebElement> couponsContainer = driver
					.findElements(couponsList);
			WebElement randomCoupon = couponsContainer.get(randomNumber);
			scrollToElement(randomCoupon);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(loadToCardBtn));

			WebElement loadToCard = randomCoupon.findElement(loadToCardBtn);
			loadToCard.click();
			System.out.println("Load to Card Clicked");

			randomNumber = checkIfCouponClickingHasErrors(randomNumber);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomNumber;

	}

	/****** Wait for Modal Content (Error) in Page ****/
	public int checkIfCouponClickingHasErrors(int randomNumber) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(modalContent));

			System.out
					.println("Error Occured While Clicking the Load To Card of the Coupon");

			scrollToElement(driver.findElement(printClippedCouponBtn));

			wait = new WebDriverWait(driver, 10);
			WebElement closeModalBtn = wait.until(ExpectedConditions
					.elementToBeClickable(modalContentCloseBtn));
			closeModalBtn.click();
			System.out
					.println("Close Button Clicked. Clicking another random Coupon");

			randomNumber = clickLoadToCardOfRandomCoupon();
		} catch (TimeoutException e) {
			System.out
					.println("No Error Occured While Clicking the Load To Card of Coupon. Waited");

		} catch (NoSuchElementException e) {
			System.out
					.println("No Error Occured While Clicking the Load To Card of Coupon");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomNumber;

	}

	/******** Check if the Load To Card button changes to UnClip ************/
	public void verifyChangesInLoadCardBtn(int randomNumber) {

		try {

			List<WebElement> couponsContainer = driver
					.findElements(couponsList);
			WebElement randomCoupon = couponsContainer.get(randomNumber);
			scrollToElement(randomCoupon);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.textToBePresentInElement(
					randomCoupon.findElement(unClipBtn), "Unclip"));

			String expectedText = "Loaded -Unclip";

			String actualText = randomCoupon.findElement(loadedText).getText()
					+ randomCoupon.findElement(unClipBtn).getText();

			System.out.println("Text changed to " + actualText);

			Assert.assertEquals(actualText, expectedText,
					"The expected Text is not present in the Load To Card Button");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*** Clicking the Clipped Link *****/
	public void clickClippedLink() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement clipped = wait.until(ExpectedConditions.visibilityOfElementLocated(clippedLink));
			
			scrollToView(clipped);

			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(clipped));

			clipped.click();
			System.out.println("Clipped Link clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/********** Verifying if the random coupon clipped is present in the Clipped Link *****/
	public void verifyCouponExistsInClipped(String[] randomCouponClipped) {

		try {
			String expectedBrandName = randomCouponClipped[0];
			String expectedDiscount = randomCouponClipped[1];
			String expectedExpiryDate = randomCouponClipped[2];

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.numberOfElementsToBe(couponsList, 1));

			WebElement clippedCoupon = driver.findElement(couponsList);
			scrollToElement(clippedCoupon);

			String actualBrandName = clippedCoupon.findElement(couponName)
					.getText();
			String actualDiscount = clippedCoupon.findElement(couponDiscount)
					.getText();
			String actualExpiryDate = clippedCoupon.findElement(
					couponExpiryDate).getText();

			System.out.println("Clipped coupons details: " + actualBrandName
					+ ", " + actualDiscount + ", " + actualExpiryDate);

			Assert.assertEquals(actualBrandName, expectedBrandName,
					"Brand Name clipped is NOT Same");
			Assert.assertEquals(actualDiscount, expectedDiscount,
					"Discount clipped is NOT Same");
			Assert.assertEquals(actualExpiryDate, expectedExpiryDate,
					"Expiry Date clipped is NOT Same");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Click the UnClip Button ******/
	public void clickUnClipBtn() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.numberOfElementsToBe(couponsList, 1));

			WebElement clippedCoupon = driver.findElement(couponsList);
			scrollToElement(clippedCoupon);
			clippedCoupon.findElement(unClipBtn).click();
			System.out.println("Coupon UnClip clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Clicking the Print Clipped Coupons Button *****/
	public void clickPrintClippedCouponsButton() {

		try {
			WebElement printClippedCoupon = driver
					.findElement(printClippedCouponBtn);

			scrollToView(printClippedCoupon);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.elementToBeClickable(printClippedCoupon));

			printClippedCoupon.click();
			System.out.println("Print Clipped Coupon Link Clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/***** Check if the Coupon Which is Clipped in Print Clipped Coupons Tab *****/
	public void checkClippedCouponIsPresentInPrintTab(String brandName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			boolean elementPresent = wait.until(ExpectedConditions
					.textToBePresentInElementLocated(clippedCouponName,
							brandName));
			if (elementPresent) {
				System.out
						.println("The Clipped Coupons is Present in the Print Clipped Coupons Tab");
			} else {
				System.out
						.println("The Clipped Coupons is NOT Present in the Print Clipped Coupons Tab");
				reportFail("The Clipped Coupons is NOT Present in the Print Clipped Coupons Tab");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/********* Click the Show Images Check Box if it is NOT Checked *******/
	public void checkShowImagesCheckBoxIfNotChecked() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.elementToBeClickable(showImgesCheckBox));
			WebElement showImages = driver.findElement(showImgesCheckBox);

			scrollToView(showImages);

			if (!showImages.isSelected()) {
				showImages.click();
				System.out.println("Check Box Checked");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/********* Click the Show Images Check Box if it is NOT Checked *******/
	public void unCheckShowImagesCheckBoxIfChecked() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.elementToBeClickable(showImgesCheckBox));
			WebElement showImages = driver.findElement(showImgesCheckBox);

			scrollToView(showImages);
			if (showImages.isSelected()) {
				showImages.click();
				System.out.println("Check Box UnChecked");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/****** Check if the Coupon Image is Displayed *****/
	public void checkIfImageIsDisplayed() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(clippedCouponImg));
			WebElement couponImage = driver.findElement(clippedCouponImg);

			scrollToView(couponImage);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			Object result = jse
					.executeScript(
							"return arguments[0].complete && "
									+ "typeof arguments[0].naturalWidth != \"undefined\" && "
									+ "arguments[0].naturalWidth > 0",
							couponImage);

			boolean imageIsDisplayed = false;
			if (result instanceof Boolean) {
				imageIsDisplayed = (Boolean) result;
				System.out.println("Image Displayed is " + imageIsDisplayed);
				Assert.assertEquals(true, imageIsDisplayed,
						"Image is NOT Displayed");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/****** Check if the Coupon Image is Displayed *****/
	public void checkIfImageIsNotDisplayed() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(clippedCouponImg));
			WebElement couponImage = driver.findElement(clippedCouponImg);
			scrollToView(couponImage);
		} catch (TimeoutException e) {
			System.out.println("Image is NOT Displayed");

		} catch (NoSuchElementException e) {
			System.out.println("Image is NOT Displayed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/******
	 * Check the Clipped Coupon Details in Print Tab by Checking the Show Images
	 *****/
	public void checkIfCouponDetailsIsDisplayedForChecked(
			String[] randomCouponClipped) {

		try {
			String expectedBrandName = randomCouponClipped[0];
			String expectedDiscount = randomCouponClipped[1];
			String expectedExpiryDate = randomCouponClipped[2];
			expectedExpiryDate = expectedExpiryDate.replace(":", "");

			String actualBrandName = driver.findElement(clippedCouponName)
					.getText();

			String actualDiscount = driver.findElement(clippedCouponDiscount)
					.getText();

			String actualExpiryDate = driver.findElement(
					clippedCouponExpiryDate).getText();

			System.out.println("Clipped Coupons details: " + actualBrandName
					+ ", " + actualDiscount + ", " + actualExpiryDate);

			Assert.assertEquals(actualBrandName, expectedBrandName,
					"Brand Name clipped is NOT Same");
			Assert.assertEquals(actualDiscount, expectedDiscount,
					"Discount clipped is NOT Same");
			Assert.assertEquals(actualExpiryDate, expectedExpiryDate,
					"Expiry Date clipped is NOT Same");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/******
	 * Check the Clipped Coupon Details in Print Tab by UnChecking the Show
	 * Images
	 *****/
	public void checkIfCouponDetailsIsDisplayedForUnchecked(
			String[] randomCouponClipped) {

		try {
			String expectedBrandName = randomCouponClipped[0];
			String expectedExpiryDate = randomCouponClipped[2];
			expectedExpiryDate = expectedExpiryDate.replace(":", "");

			String actualBrandName = driver.findElement(clippedCouponName)
					.getText();

			String actualExpiryDate = driver.findElement(
					clippedCouponExpiryDate).getText();

			System.out.println("Clipped Coupons details: " + actualBrandName
					+ ", " + actualExpiryDate);

			Assert.assertEquals(actualBrandName, expectedBrandName,
					"Brand Name clipped is NOT Same");

			Assert.assertEquals(actualExpiryDate, expectedExpiryDate,
					"Expiry Date clipped is NOT Same");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/***
	 * Check if Coupon Discount is NOT present in Print Tab by Unchecking the
	 * Show Image
	 ***/
	public void checkIfDiscountIsNotDisplayed() {
		try {
			driver.findElement(clippedCouponDiscount);

		} catch (NoSuchElementException e) {
			System.out.println("Discount is NOT Present");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/****** Clicking the Close Button **********/
	public void clickCloseInPrintTab() {

		try {
			WebElement close = driver.findElement(closeInPrintClippedCouponBtn);
			scrollToView(close);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.elementToBeClickable(close));
			

			scrollToView(close);

			close.click();
			System.out.println("Close button in Print Tab Clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/****** Check if it is in All Coupons Page *********/
	public void checkIfAllCouponsIsClicked() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(allCouponsLink));
			WebElement allCoupons = driver.findElement(allCouponsLink);

			// scrollToElement(allCoupons);
			allCoupons.click();

			System.out.println("All coupons is Clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/******* clicking the randomly selected Category ****/
	public String clickRandomCategoryAndGetCategoyName(int randomCategoryNumber) {

		String randomCategoryName = "";
		try {
			List<WebElement> categoriesElements = driver
					.findElements(categoriesList);

			WebElement randomCategory = categoriesElements
					.get(randomCategoryNumber);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(randomCategory));

			scrollToView(randomCategory);
			randomCategory.click();

			randomCategoryName = randomCategory.getText();
			System.out.println("Random category Clicked is "
					+ randomCategory.getText());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomCategoryName;
	}

	/***** Extract the Number of Coupons in the Random Category *****/
	public int extractNumberOfCouponsInRandomCategory(String randomCategoryName) {

		int numberOfCouponsInRandomCategory = 0;
		try {
			String[] category = randomCategoryName.split(" ");
			String temp = category[category.length - 1].trim();
			temp = temp.substring(1, temp.length() - 1);
			System.out.println(temp
					+ " <= is the extracted category number from =>"
					+ randomCategoryName);

			numberOfCouponsInRandomCategory = Integer.valueOf(temp);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return numberOfCouponsInRandomCategory;
	}

	/******** Check if the number of Coupons are Showing Up correctly *****/
	public void checkIfNumberCouponsPresentAreCorrect(
			int expectedNumberOfCouponsInRandomCategory) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.numberOfElementsToBe(couponsList,
					expectedNumberOfCouponsInRandomCategory));

			List<WebElement> coupons = driver.findElements(couponsList);
			scrollToView(coupons.get(0));

			int actualNumberOfCouponsInRandomCategory = coupons.size();

			System.out.println("The Expected Number of Coupons is "
					+ expectedNumberOfCouponsInRandomCategory);
			System.out.println("The Actual Number of Coupons is "
					+ actualNumberOfCouponsInRandomCategory);
			Assert.assertEquals(actualNumberOfCouponsInRandomCategory,
					expectedNumberOfCouponsInRandomCategory,
					"No of Coupons in the Category and No of coupons in the Page are NOT same");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/**** Get All Coupons and Store in an Array ******/
	public String[][] getCouponsInCheckedCategoryFromPage() {

		System.out
				.println("******Getting all the coupon details in the checked Category*****");
		List<WebElement> couponContainer = driver.findElements(couponsList);

		String[][] couponsArray = new String[couponContainer.size()][5];
		try {

			for (int index = 0; index < couponContainer.size(); index++) {

				scrollToElement(couponContainer.get(index));

				WebElement coupon = couponContainer.get(index);
				String brandName = coupon.findElement(couponName).getText();
				String discountPrice = coupon.findElement(couponDiscount)
						.getText();
				String expiryDate = coupon.findElement(couponExpiryDate)
						.getText();
				String description = coupon.findElement(couponDescription)
						.getText();

				couponsArray[index][0] = "" + (index + 1);
				couponsArray[index][1] = brandName;
				couponsArray[index][2] = discountPrice;
				couponsArray[index][3] = expiryDate;
				couponsArray[index][4] = description;

				System.out.println(brandName + ", " + discountPrice + ", "
						+ expiryDate + ", " + description);
				System.out.println("Coupon number " + (index + 1)
						+ " read successfully");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return couponsArray;

	}

	/****** Set All data into Excel ********/
	public void setAllCouponDataInExcel(String[][] couponsArray) {

		try {

			String dir = System.getProperty("user.dir")
					+ "/src/main/resources/TestData";
			String fileNamePrefix = "couponsInCategories";
			String path = readFileWithPrefix(dir, fileNamePrefix);
			ReadExcelDataFile readData = new ReadExcelDataFile(path);

			readData.clearExistingDataInSheet("coupon_details", 2);
			System.out.println("All data cleared in the sheet");

			for (int index = 0; index < couponsArray.length; index++) {

				String number = couponsArray[index][0];
				String brandName = couponsArray[index][1];
				String discountPrice = couponsArray[index][2];
				String expiryDate = couponsArray[index][3];
				String description = couponsArray[index][4];

				boolean flag = readData.setCellData("coupon_details",
						"Coupon Number", index + 2, number);
				Assert.assertEquals(flag, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag1 = readData.setCellData("coupon_details",
						"Coupon Name", index + 2, brandName);
				Assert.assertEquals(flag1, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag2 = readData.setCellData("coupon_details",
						"Coupon Discount Price", index + 2, discountPrice);
				Assert.assertEquals(flag2, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag3 = readData.setCellData("coupon_details",
						"Coupon Expiry Date", index + 2, expiryDate);
				Assert.assertEquals(flag3, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				boolean flag4 = readData.setCellData("coupon_details",
						"Coupon Description", index + 2, description);
				Assert.assertEquals(flag4, true, "Coupon Data number"
						+ (index + 1) + "did not set=>");

				System.out.println("Details of Coupon " + (index + 1)
						+ " updated: " + brandName + ", " + discountPrice
						+ ", " + expiryDate + ", " + description);
			}
			renameFileWithDateTime(path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

}
