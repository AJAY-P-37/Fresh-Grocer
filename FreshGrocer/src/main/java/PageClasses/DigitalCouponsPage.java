package PageClasses;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
	public By modalContent;
	public By modalContentBody;
	public By modalContentOkBtn;
	public By modalContentCloseBtn;
	public By loadedText;
	public By unClipBtn;
	public By availableSoonBtn;
	public By clippedLink;
	public By noClippedCouponsText;
	public By printClippedCouponBtn;
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
		modalContent = getByLocator(locators, "modalContent_xpath");
		modalContentBody = getByLocator(locators, "modalContentBody_xpath");
		modalContentCloseBtn = getByLocator(locators,
				"modalContentCloseBtn_xpath");
		modalContentOkBtn = getByLocator(locators, "modalContentOkBtn_xpath");
		loadedText = getByLocator(locators, "loadedText_xpath");
		unClipBtn = getByLocator(locators, "unClipBtn_xpath");
		availableSoonBtn = getByLocator(locators, "availableSoonBtn_xpath");
		clippedLink = getByLocator(locators, "clippedLink_xpath");
		noClippedCouponsText = getByLocator(locators,
				"noClippedCouponsText_xpath");
		printClippedCouponBtn = getByLocator(locators,
				"printClippedCouponBtn_xpath");
		allCouponsLink = getByLocator(locators, "allCouponsLink_xpath");

	}

	/***** Check if the Clipped Coupon Link Text Changes To "Load To Card" ********/
	public boolean verifyChangesAfterUnClip(WebElement clippedCoupon) {

		boolean textChanged = false;
		WebDriverWait wait = new WebDriverWait(driver, 5);
		try {

			List<WebElement> loadToCard = wait.until(ExpectedConditions
					.visibilityOfNestedElementsLocatedBy(clippedCoupon,
							loadToCardBtnXpath));
			wait.until(ExpectedConditions.textToBePresentInElement(
					loadToCard.get(0), "Load To Card"));

			String expectedText = "Load To Card";

			String actualText = loadToCard.get(0).getText();

			System.out.println("Text Changed to Load To Card for "
					+ clippedCoupon.findElement(couponName).getText());
			Assert.assertEquals(actualText, expectedText);

			textChanged = true;
		} catch (Exception e) {

			System.out.println("Text did NOT Change to Load To Card for "
					+ clippedCoupon.findElement(couponName).getText());
			try {
				scrollToElement(driver.findElement(printClippedCouponBtn));
				WebElement modalDiv = null;
				try {
					wait = new WebDriverWait(driver, 10);
					modalDiv = wait.until(ExpectedConditions
							.visibilityOfElementLocated(modalContent));

				} catch (TimeoutException e1) {
					System.out.println(e1.getMessage());
					System.out
							.println("Modal content PopUp also NOT found. Leaving this Clipped Coupon");
				}
				WebElement modalBody = wait.until(ExpectedConditions
						.visibilityOfElementLocated(modalContentBody));

				System.out
						.println("PopUp occurred while Unclipping the coupon. It shows "
								+ modalBody.getText());

				wait = new WebDriverWait(driver, 5);
				WebElement closeModalBtn = wait.until(ExpectedConditions
						.elementToBeClickable(modalContentCloseBtn));

				scrollToView(closeModalBtn);
				closeModalBtn.click();
				System.out
						.println("Close Button Clicked in modal. Leaving this Clipped Coupon "
								+ clippedCoupon.findElement(couponName)
										.getText());

				wait = new WebDriverWait(driver, 5);
				wait.until(ExpectedConditions.invisibilityOf(modalDiv));

				textChanged = false;
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				reportFail(e1.getMessage());
			}

		}
		return textChanged;
	}

	/******* Clicking UnClip for all the Coupons which are clicked already *******/
	public void clickUnClipForAllClippedCoupons() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);

			WebElement noClippedCoupons = wait.until(ExpectedConditions
					.visibilityOfElementLocated(noClippedCouponsText));

			System.out.println("No clipped coupons found. It shows '"
					+ noClippedCoupons.getText() + "'");

		} catch (TimeoutException e) {

			System.out.println("Clipped coupons found");

			try {

				List<WebElement> clippedCouponsList = driver
						.findElements(couponsList);

				for (int index = 0; index < clippedCouponsList.size(); index++) {

					WebElement clippedCoupon = clippedCouponsList.get(index);

					WebDriverWait wait = new WebDriverWait(driver, 10);
					List<WebElement> unClipButton = wait
							.until(ExpectedConditions
									.visibilityOfNestedElementsLocatedBy(
											clippedCoupon, unClipBtn));

					scrollToView(unClipButton.get(0));
					wait.until(ExpectedConditions
							.elementToBeClickable(unClipButton.get(0)));
					unClipButton.get(0).click();
					System.out.println("Unclip Clicked for "
							+ clippedCoupon.findElement(couponName).getText());

					boolean textChanged = verifyChangesAfterUnClip(clippedCoupon);
					if (!textChanged) {
						continue;
					}

					System.out.println("Coupon UnClip clicked for coupon "
							+ clippedCoupon.findElement(couponName).getText());
				}

				if (clippedCouponsList.size() == 0) {
					System.out.println("There were no already clipped coupons");
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				reportFail(e1.getMessage());
			}
		}

	}

	/******** Click Show All Button *************/
	public void clickShowAll() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement showAll = wait.until(ExpectedConditions
					.elementToBeClickable(showAllBtn));
			scrollToView(showAll);
			showAll.click();
			System.out.println("Success: Show All Button Clicked");

			wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
					resetBtn, "Reset"));
			String expectedText = "Reset";
			String actualText = driver.findElement(resetBtn).getText();
			Assert.assertEquals(actualText, expectedText);
			System.out.println("Success: Text Changed to Reset");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****** Check if all the coupons have "Load To Card" button *********/
	public void checkCouponsLoadToCardText() {

		String loadToCardString = null;
		try {
			List<WebElement> couponContainer = driver.findElements(couponsList);

			for (int index = 0; index < couponContainer.size(); index++) {
				scrollToElement(couponContainer.get(index));

				loadToCardString = couponContainer.get(index)
						.findElement(By.tagName("a")).getText().trim();

				String brandName = couponContainer.get(index)
						.findElement(couponName).getText();

				if (loadToCardString.equals("Load To Card")) {
					Assert.assertEquals(loadToCardString, "Load To Card");
					System.out
							.println("Success: Load to card is present at coupon "
									+ brandName);

				} else if (loadToCardString.equals("Unclip")) {
					System.out.println("Unclip is present at coupon "
							+ brandName + ". It can be ignored");

				} else if (loadToCardString.equals("Log In to Load")) {
					System.out
							.println("Fail: Log In To Load Text present after Log In in Coupon "
									+ brandName);
					Assert.assertEquals(loadToCardString, "Load To Card",
							"Text present in Coupon after Log in =>");
				} else if (loadToCardString.equals("Available Soon")) {
					System.out.println("Available Soon is present at coupon "
							+ brandName + ". It can be ignored");
				} else {
					System.out.println(loadToCardString
							+ " is present at coupon " + brandName
							+ ". This is an unexpected string in testcases");
				}

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

			try {
				randomCoupon.findElement(unClipBtn);
				System.out
						.println("Unclip button for the random coupon found. Clicking another coupon");
				randomNumber = clickLoadToCardOfRandomCoupon();
			} catch (Exception e1) {
				try {
					randomCoupon.findElement(availableSoonBtn);
					System.out
							.println("Available Soon button for the random coupon found. Clicking another coupon");
					randomNumber = clickLoadToCardOfRandomCoupon();

				} catch (Exception e) {

					WebDriverWait wait = new WebDriverWait(driver, 5);
					wait.until(ExpectedConditions
							.elementToBeClickable(randomCoupon
									.findElement(loadToCardBtnXpath)));

					WebElement loadToCard = randomCoupon
							.findElement(loadToCardBtnXpath);
					loadToCard.click();
					System.out
							.println("Success: Load to Card Clicked for random coupon");

					randomNumber = checkIfCouponClickingHasErrors(randomNumber);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return randomNumber;

	}

	/****** Wait for Modal Content (Error) in Page ****/
	public int checkIfCouponClickingHasErrors(int randomNumber) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(modalContent));

			WebElement modalBody = wait.until(ExpectedConditions
					.visibilityOfElementLocated(modalContentBody));
			System.out
					.println("PopUp Occured While Clicking the Load To Card of the Coupon. It shows '"
							+ modalBody.getText() + "'");

			scrollToElement(driver.findElement(printClippedCouponBtn));

			try {

				wait = new WebDriverWait(driver, 5);
				WebElement closeModalBtn = wait.until(ExpectedConditions
						.elementToBeClickable(modalContentCloseBtn));
				closeModalBtn.click();
				System.out
						.println("Close Button found and Clicked. Clicking another random Coupon");
				randomNumber = clickLoadToCardOfRandomCoupon();

			} catch (TimeoutException e) {

				try {
					wait = new WebDriverWait(driver, 5);
					WebElement okModalBtn = wait.until(ExpectedConditions
							.elementToBeClickable(modalContentOkBtn));
					okModalBtn.click();
					System.out
							.println("OK Button found and Clicked for the above MESSAGE");

					wait.until(ExpectedConditions.invisibilityOf(okModalBtn));

				} catch (Exception e1) {

					System.out.println(e1.getMessage());
					reportFail(e1.getMessage());
				}
			}

		} catch (TimeoutException e) {
			System.out
					.println("No PopUp Occured While Clicking the Load To Card of Coupon.");

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

			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(
					randomCoupon, unClipBtn));

			String expectedText = "Loaded -Unclip";

			String actualText = randomCoupon.findElement(loadedText).getText()
					+ randomCoupon.findElement(unClipBtn).getText();

			System.out.println("Success: Text changed to " + actualText);

			Assert.assertEquals(actualText, expectedText,
					"The expected Text is NOT present in the Load To Card Button");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/*** Clicking the Clipped Link *****/
	public void clickClippedLink() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);

			WebElement clipped = wait.until(ExpectedConditions
					.visibilityOfElementLocated(clippedLink));

			int count = 0;
			do {
				try {

					waitForCssTransition(clipped, "transition-duration");

					scrollToView(clipped);
					wait.until(ExpectedConditions.elementToBeClickable(clipped));
					clipped.click();
					System.out
							.println("Success: Clipped Link clicked in attempt no. "
									+ (count + 1));
					break;
				} catch (ElementClickInterceptedException e) {
					try {
						Actions act = new Actions(driver);
						act.moveToElement(clipped).click().build().perform();
						break;

					} catch (Exception e1) {
						System.out
								.println("Clipped Link is NOT clicked due to the following reason");
						System.out.println(e.getMessage());
					}

				} catch (Exception e) {
					System.out
							.println("Clipped Link is NOT clicked due to the following reason");
					System.out.println(e.getMessage());

				}
				count++;
				if (count == 3) {

					System.out
							.println("Css transition NOT completed even after trying for "
									+ (count) + " attempts");
					reportFail("Css transition NOT completed even after trying for "
							+ (count) + " attempts");
				}
			} while (count <= 3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/********** Verifying if the random coupon clipped is present in the Clipped Link *****/
	public void verifyCouponExistsInClipped(String[] randomCouponClipped) {

		String expectedBrandName = randomCouponClipped[0];
		String expectedDiscount = randomCouponClipped[1];
		String expectedExpiryDate = randomCouponClipped[2];

		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement noClippedCoupons = null;
		try {
			noClippedCoupons = wait.until(ExpectedConditions
					.visibilityOfElementLocated(noClippedCouponsText));

			System.out.println("No clipped coupons found. It shows '"
					+ noClippedCoupons.getText() + "'");
			reportFail("No clipped coupons found. It shows '"
					+ noClippedCoupons.getText() + "'");
			return;

		} catch (Exception e) {

			try {
				List<WebElement> clippedCoupons = driver
						.findElements(couponsList);

				boolean flag = false;
				for (int index = 0; index < clippedCoupons.size(); index++) {

					WebElement clippedCoupon = clippedCoupons.get(index);
					scrollToElement(clippedCoupon.findElement(couponExpiryDate));

					String actualBrandName = clippedCoupon.findElement(
							couponName).getText();
					String actualDiscount = clippedCoupon.findElement(
							couponDiscount).getText();
					String actualExpiryDate = clippedCoupon.findElement(
							couponExpiryDate).getText();

					if (actualBrandName.equals(expectedBrandName)
							&& actualDiscount.equals(expectedDiscount)
							&& actualExpiryDate.equals(expectedExpiryDate)) {
						System.out.println("Clipped coupons details: "
								+ actualBrandName + ", " + actualDiscount
								+ ", " + actualExpiryDate);
						System.out.println("The Clipped Coupon is Present");
						flag = true;
						break;
					}
				}
				if (flag == false) {
					System.out
							.println("The Clipped Coupon is NOT present in the Clipped Tab");
					reportFail("The Clipped Coupon is NOT present in the Clipped Tab");
				}
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				reportFail(e1.getMessage());
			}
		}

	}

	/***** Click the UnClip Button ******/
	public void clickUnClipBtnForClippedCoupon(String[] randomCouponClipped) {

		String expectedBrandName = randomCouponClipped[0];
		String expectedDiscount = randomCouponClipped[1];
		String expectedExpiryDate = randomCouponClipped[2];

		WebDriverWait wait = null;
		try {
			wait = new WebDriverWait(driver, 3);

			WebElement noClippedCoupons = wait.until(ExpectedConditions
					.visibilityOfElementLocated(noClippedCouponsText));

			System.out.println("No clipped coupons found. It shows '"
					+ noClippedCoupons.getText() + "'");

			reportFail("No clipped coupons found. It shows '"
					+ noClippedCoupons.getText() + "'");
		} catch (Exception e) {

			List<WebElement> clippedCoupons = driver.findElements(couponsList);

			for (int index = 0; index < clippedCoupons.size(); index++) {
				WebElement clippedCoupon = clippedCoupons.get(index);

				String actualBrandName = clippedCoupon.findElement(couponName)
						.getText();
				String actualDiscount = clippedCoupon.findElement(
						couponDiscount).getText();
				String actualExpiryDate = clippedCoupon.findElement(
						couponExpiryDate).getText();

				if (actualBrandName.equals(expectedBrandName)
						&& actualDiscount.equals(expectedDiscount)
						&& actualExpiryDate.equals(expectedExpiryDate)) {

					scrollToView(clippedCoupon.findElement(unClipBtn));
					wait.until(ExpectedConditions
							.elementToBeClickable(clippedCoupon
									.findElement(unClipBtn)));
					clippedCoupon.findElement(unClipBtn).click();
					System.out
							.println("Success: Coupon UnClip clicked for Coupon "
									+ actualBrandName);
					break;
				}
			}
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
			System.out.println("Success: Print Clipped Coupon Link Clicked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
	}

	/****** Click All Coupons *********/
	public void clickAllCoupons() {

		try {

			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement allCoupons = wait.until(ExpectedConditions
					.visibilityOfElementLocated(allCouponsLink));
			int count = 0;
			do {
				try {

					waitForCssTransition(allCoupons, "transition-duration");

					scrollToView(allCoupons);

					wait.until(ExpectedConditions
							.elementToBeClickable(allCoupons));

					allCoupons.click();

					System.out.println("Success: All coupons is Clicked after "
							+ (count + 1) + " attempts");
					break;

				} catch (ElementClickInterceptedException e) {
					try {
						Actions act = new Actions(driver);
						act.moveToElement(allCoupons).click().build().perform();
						break;
					} catch (Exception e1) {
						System.out
								.println("All Coupons Link is NOT clicked due to the following reason");
						System.out.println(e.getMessage());
					}

				} catch (Exception e) {
					System.out
							.println("All Coupons Link is NOT clicked due to the following reason");
					System.out.println(e.getMessage());

				}
				count++;
				if (count == 3) {

					System.out
							.println("Css transition NOT completed even after trying for "
									+ (count) + " attempts");
					reportFail("Css transition NOT completed even after trying for "
							+ (count) + " attempts");
				}
			} while (count <= 3);

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
			System.out.println("Success: Random category Clicked is "
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
				System.out.println("Success: Coupon number " + (index + 1)
						+ " read successfully");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return couponsArray;

	}

	/****** Set All data into Excel ********/
	public void setAllCouponDataInExcel(String[][] couponsArray,
			String randomCategoryName) {

		try {

			String dir = System.getProperty("user.dir")
					+ "/src/main/resources/TestData";
			String fileNamePrefix = "couponsInCategories";
			String path = readFileWithPrefix(dir, fileNamePrefix);
			ReadExcelDataFile readData = new ReadExcelDataFile(path);

			readData.clearExistingDataInSheet("coupon_details", 2);
			System.out.println("Success: All data cleared in the sheet");

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

				boolean flag5 = readData.setCellData("coupon_details",
						"Category Name", index + 2, randomCategoryName);

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
