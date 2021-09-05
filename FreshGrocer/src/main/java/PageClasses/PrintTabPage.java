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

import baseClasses.PageBaseClass;

public class PrintTabPage extends PageBaseClass{

	Hashtable<String, String> locators;
	
	public By clippedCouponsInPrint;
	public By printLoadingText;
	public By clippedCouponName;
	public By clippedCouponDiscount;
	public By clippedCouponExpiryDate;
	public By clippedCouponImg;
	public By showImgesCheckBox;
	public By closeInPrintClippedCouponBtn;
	
	public PrintTabPage(WebDriver driver) {
		super(driver);
		locators = readLocators("digital_coupons_page");
		assignValuesForLocatorsFromExcel();
	}
	
	public void assignValuesForLocatorsFromExcel(){
		
		clippedCouponsInPrint = getByLocator(locators, "clippedCouponsInPrintTab_xpath");
		printLoadingText = getByLocator(locators, "printLoadingText_xpath");
		clippedCouponName = getByLocator(locators, "clippedCouponName_xpath");
		clippedCouponDiscount = getByLocator(locators,
				"clippedCouponDiscount_xpath");
		clippedCouponExpiryDate = getByLocator(locators,
				"clippedCouponExpiryDate_xpath");
		clippedCouponImg = getByLocator(locators, "clippedCouponImg_xpath");
		showImgesCheckBox = getByLocator(locators, "showImgesCheckBox_id");
		closeInPrintClippedCouponBtn = getByLocator(locators,
				"closeInPrintClippedCouponBtn_xpath");
	}

	/***** Check if the Coupon Which is Clipped in Print Clipped Coupons Tab *****/
	public int checkClippedCouponIsPresentInPrintTab(String brandName) {

		int indexOfClippedCoupon = 0;
		try {
			boolean couponPresent = false;
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(printLoadingText));

			List<WebElement> clippedCouponsInPrintTab = driver
					.findElements(clippedCouponsInPrint);
			for (int index = 0; index < clippedCouponsInPrintTab.size(); index++) {

				WebElement clippedCouponInPrintTab = clippedCouponsInPrintTab
						.get(index);
				String actualBrandName = clippedCouponInPrintTab.findElement(
						clippedCouponName).getText();
				if (actualBrandName.equals(brandName)) {
					System.out.println("Success: The Clipped Coupon "
							+ actualBrandName
							+ "is Present in the Print Clipped Coupons Tab");
					couponPresent = true;
					indexOfClippedCoupon = index;
					break;
				}

			}
			if (!couponPresent) {
				System.out
						.println("The Clipped Coupons is NOT Present in the Print Clipped Coupons Tab");
				reportFail("The Clipped Coupons is NOT Present in the Print Clipped Coupons Tab");

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return indexOfClippedCoupon;
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
				System.out.println("Success: Check Box Checked");
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
				System.out.println("Success: Check Box UnChecked");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/****** Check if the Coupon Image is Displayed *****/
	public void checkIfImageIsDisplayed(int indexOfClippedCoupon) {

		try {

			WebElement clippedCoupon = driver.findElements(clippedCouponsInPrint).get(
					indexOfClippedCoupon);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement couponImage = wait.until(ExpectedConditions
					.visibilityOf(clippedCoupon.findElement(clippedCouponImg)));

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
				System.out.println("Success: Image is Displayed");
				Assert.assertEquals(imageIsDisplayed, true,
						"Image is NOT Displayed");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

	}

	/****** Check if the Coupon Image is Displayed *****/
	public void checkIfImageIsNotDisplayed(int indexOfClippedCoupon) {

		try {
			WebElement clippedCoupon = driver.findElements(clippedCouponsInPrint).get(
					indexOfClippedCoupon);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement couponImage = wait.until(ExpectedConditions
					.visibilityOf(clippedCoupon.findElement(clippedCouponImg)));
			scrollToView(couponImage);
		} catch (TimeoutException e) {
			System.out.println("Success: Image is NOT Displayed");

		} catch (NoSuchElementException e) {
			System.out.println("Success: Image is NOT Displayed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/******
	 * Check the Clipped Coupon Details in Print Tab by Checking the Show Images
	 *****/
	public void checkIfCouponDetailsIsDisplayedForChecked(
			String[] randomCouponClipped, int indexOfClippedCoupon) {

		try {
			String expectedBrandName = randomCouponClipped[0];
			String expectedDiscount = randomCouponClipped[1];
			String expectedExpiryDate = randomCouponClipped[2];
			expectedExpiryDate = expectedExpiryDate.replace(":", "");

			WebElement clippedCoupon = driver.findElements(clippedCouponsInPrint).get(
					indexOfClippedCoupon);
			String actualBrandName = clippedCoupon.findElement(
					clippedCouponName).getText();

			String actualDiscount = clippedCoupon.findElement(
					clippedCouponDiscount).getText();

			String actualExpiryDate = clippedCoupon.findElement(
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
			String[] randomCouponClipped, int indexOfClippedCoupon) {

		try {
			String expectedBrandName = randomCouponClipped[0];
			String expectedExpiryDate = randomCouponClipped[2];
			expectedExpiryDate = expectedExpiryDate.replace(":", "");

			WebElement clippedCoupon = driver.findElements(clippedCouponsInPrint).get(
					indexOfClippedCoupon);
			String actualBrandName = clippedCoupon.findElement(
					clippedCouponName).getText();

			String actualExpiryDate = clippedCoupon.findElement(
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
	public void checkIfDiscountIsNotDisplayed(int indexOfClippedCoupon) {
		try {
			WebElement clippedCoupon = driver.findElements(clippedCouponsInPrint).get(
					indexOfClippedCoupon);
			clippedCoupon.findElement(clippedCouponDiscount);

		} catch (NoSuchElementException e) {
			System.out.println("Success: Discount is NOT Present");

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
			wait.until(ExpectedConditions.elementToBeClickable(close));

			close.click();
			System.out.println("Success: Close button in Print Tab Clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}
}
