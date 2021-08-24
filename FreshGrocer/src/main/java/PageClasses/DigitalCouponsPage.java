package PageClasses;

import java.util.ArrayList;
import java.util.Arrays;
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

	/******* Get All the Categories from the Page *********/
	public String[] getAllCategoriesFromPage() {

		System.out
				.println("*******Get All the Categories from the Page*********");
		List<WebElement> categoriesElements = driver
				.findElements(categoriesList);

		String[] categoriesText = new String[categoriesElements.size()];

		try {
			for (int index = 0; index < categoriesElements.size(); index++) {

				WebElement category = categoriesElements.get(index);

				scrollToView(category);

				String categoryText = category.getText().trim();
				categoriesText[index] = categoryText;
				System.out.println(categoriesText[index] + " <= is in page");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

		return categoriesText;
	}

	/********
	 * Extracting only the Category Name by omitting the No of items in the
	 * category()
	 *******/
	public String[] extractOnlyCategoryName(String[] categoriesInPage) {

		System.out.println("*****Extracting only the Category Name*******");
		String[] extractedCategoryNames = new String[categoriesInPage.length];
		try {
			for (int index = 0; index < categoriesInPage.length; index++) {
				String[] category = categoriesInPage[index].trim().split(" ");
				String temp = "";
				for (int word = 0; word < category.length - 1; word++) {
					temp = temp + category[word] + " ";
				}
				extractedCategoryNames[index] = temp.trim();
				System.out.println(extractedCategoryNames[index]
						+ " <= is the extracted category name from =>"
						+ categoriesInPage[index]);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return extractedCategoryNames;
	}

	/***** Reading all the Category from Excel ********/
	public String[] readAllCategoriesFromExcel() {

		System.out.println("*****Reading all the Category from Excel*******");
		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir")
						+ "/src/main/resources/TestData/" + "categories.xlsx");
		int rowCount = readData.getRowCount("categories") - 1;
		if (rowCount == -1) {
			System.out.println("Sheet Categories NOT found");
			reportFail("Sheet Categories NOT found");
		}
		String[] categoriesText = new String[rowCount];
		try {
			for (int index = 0; index < rowCount; index++) {
				String temp = readData.getCellData("categories",
						"Category Name", index + 2);

				// Trimming the leading and Trailing Spaces in the Excel Sheet
				categoriesText[index] = temp.replaceAll("^\\p{Z}+", "")
						.replaceAll("\\p{Z}+$", "");

				System.out.println(categoriesText[index] + " <= is in Excel");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return categoriesText;

	}

	/******* Check if All the Category elements are sorted Alphabetically ************/
	public List<String> getCategoriestNotSorted(String[] categoriesInPage) {

		System.out
				.println("*******Check if All the Category elements are sorted Alphabetically************");

		List<String> notSortedCategories = new ArrayList<String>();
		try {
			String[] sortedCategories = categoriesInPage.clone();
			Arrays.sort(sortedCategories);

			List<WebElement> categoriesElements = driver
					.findElements(categoriesList);

			for (int index = 0; index < categoriesInPage.length; index++) {

				scrollToView(categoriesElements.get(index));

				if (!categoriesInPage[index].equals(sortedCategories[index])) {
					System.out.println(categoriesInPage[index]
							+ " in page is NOT sorted. The element should be "
							+ sortedCategories[index]);
					notSortedCategories.add(categoriesInPage[index]);
				} else {
					System.out
							.println(categoriesInPage[index]
									+ " in page is sorted. The corresponding element is "
									+ sortedCategories[index]);
				}
			}
			if (notSortedCategories.size() == 0) {
				System.out.println("===All the categories are Sorted===");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return notSortedCategories;

	}

	/*****
	 * Check if All the Category elements in the Page are present in Excel
	 *******/
	public List<String> getCategoriesNotInExcel(String[] categoriesInPage,
			String[] categoriesInExcel) {

		System.out
				.println("*****Check if All the Category elements are present in Excel******");

		List<WebElement> categoriesElements = driver
				.findElements(categoriesList);
		List<String> categoriesNotPresentInExcel = new ArrayList<String>();
		try {
			for (int index = 0; index < categoriesInPage.length; index++) {

				scrollToView(categoriesElements.get(index));

				boolean isPresent = Arrays.asList(categoriesInExcel).contains(
						categoriesInPage[index]);

				if (isPresent) {
					// System.out.println(categoriesInPage[categoryInPage] +
					// " is present in Excel");
				} else {
					System.out.println(categoriesInPage[index]
							+ " is NOT present in Excel");
					categoriesNotPresentInExcel.add(categoriesInPage[index]);
				}
			}
			if (categoriesNotPresentInExcel.size() == 0) {
				System.out
						.println("All the elements in the Page are Present in the Excel");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return categoriesNotPresentInExcel;

	}

	/**
	 * Check if all the Excel elements are present in the Page
	 **/
	public List<String> getCategoriesNotInPage(String[] categoriesInPage,
			String[] categoriesInExcel) {

		System.out
				.println("*****Check if All the Category elements are present in Page******");

		List<String> categoriesNotPresentInPage = new ArrayList<String>();
		try {
			for (int index = 0; index < categoriesInExcel.length; index++) {

				boolean isPresent = Arrays.asList(categoriesInPage).contains(
						categoriesInExcel[index]);

				if (isPresent) {
					// System.out.println(categoriesInExcel[categoryInExel] +
					// " is present in the Page");
				} else {
					System.out.println(categoriesInExcel[index]
							+ " is NOT present in the Page");
					categoriesNotPresentInPage.add(categoriesInExcel[index]);

				}
			}

			if (categoriesNotPresentInPage.size() == 0) {
				System.out
						.println("All the elements in the Excel are present in Page");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return categoriesNotPresentInPage;

	}

	public void updatingInExcel(String fileName, String sheetName,
			String colName, int fromRowNum, List<String> data) {

		System.out.println("*****Updating the " + sheetName + "******");

		try {
			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/" + fileName);

			readData.clearExistingDataInSheet(sheetName, 2);

			for (int index = 0; index < data.size(); index++) {

				readData.setCellData(sheetName, "S.No", index + 2, ""
						+ (index + 1));

				boolean flag = readData.setCellData(sheetName, colName,
						index + 2, data.get(index));
				Assert.assertEquals(true, flag);
				System.out.println(data.get(index) + " updated in Excel");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	public void updateCategoriesNotSorted(List<String> categoriesNotSorted) {

		updatingInExcel("categories.xlsx", "categories_not_sorted",
				"Not Sorted Category Name", 2, categoriesNotSorted);

	}

	public void updateCategoriesNotInPage(List<String> categoriesNotInPage) {

		updatingInExcel("categories.xlsx", "categories_not_in_page",
				"Not in Page Category Name", 2, categoriesNotInPage);

	}

	public void updateCategoriesNotInExcel(List<String> categoriesNotInExcel) {

		updatingInExcel("categories.xlsx", "categories_not_in_excel",
				"Not in Excel Category Name", 2, categoriesNotInExcel);

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
	public void clickLoadToCardOfRandomCoupon(int randomNumber) {
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

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
			WebElement clipped = driver.findElement(clippedLink);
			scrollToView(clipped);

			WebDriverWait wait = new WebDriverWait(driver, 10);
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
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.elementToBeClickable(closeInPrintClippedCouponBtn));
			WebElement close = driver.findElement(closeInPrintClippedCouponBtn);

			scrollToView(close);

			close.click();
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

			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "couponsInCategories.xlsx");

			readData.clearExistingDataInSheet("name_discount_expiry", 2);
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

}
