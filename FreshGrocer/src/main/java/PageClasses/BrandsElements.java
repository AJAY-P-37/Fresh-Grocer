package PageClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import utilities.ReadExcelDataFile;
import baseClasses.PageBaseClass;

public class BrandsElements extends PageBaseClass {

	Hashtable<String, String> locators;

	public By brandsList;

	public BrandsElements(WebDriver driver) {
		super(driver);
		locators = readLocators("digital_coupons_page");
		assignValuesForLocatorsFromExcel();
	}

	/****** assigning the values for locators *********/
	public void assignValuesForLocatorsFromExcel() {
		brandsList = getByLocator(locators, "brandsList_xpath");
	}

	/******* Get All the Brands from the Page *********/
	public String[] getAllBrandsFromPage() {

		System.out.println("*******Get All the Brands from the Page*********");
		List<WebElement> brandsElements = driver.findElements(brandsList);

		String[] brandsText = new String[brandsElements.size()];

		try {
			for (int index = 0; index < brandsElements.size(); index++) {

				WebElement brand = brandsElements.get(index);

				scrollToView(brand);

				String brandText = brand.getText().trim();
				brandsText[index] = brandText;
				System.out.println(brandsText[index] + " <= is in page");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}

		return brandsText;
	}

	/********
	 * Extracting only the Brands Name by omitting the No of items in the Brand
	 * ()
	 *******/
	public String[] extractOnlyBrandName(String[] brandsInPage) {

		System.out.println("*****Extracting only the Brand Name*******");
		String[] extractedBrandNames = new String[brandsInPage.length];
		try {
			for (int index = 0; index < brandsInPage.length; index++) {
				String[] brand = brandsInPage[index].trim().split(" ");
				String temp = "";
				for (int word = 0; word < brand.length - 1; word++) {
					temp = temp + brand[word] + " ";
				}
				extractedBrandNames[index] = temp.trim();
				System.out.println(extractedBrandNames[index]
						+ " <= is the extracted Brand name from =>"
						+ brandsInPage[index]);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return extractedBrandNames;
	}

	/***** Reading all the Brands from Excel ********/
	public String[] readAllBrandsFromExcel() {

		System.out.println("*****Reading all the Brands from Excel*******");
		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir")
						+ "/src/main/resources/TestData/" + "brands.xlsx");
		int rowCount = readData.getRowCount("brands") - 1;
		if (rowCount == -1) {
			System.out.println("Sheet brands NOT found");
			reportFail("Sheet brands NOT found");
		}
		String[] brandsText = new String[rowCount];
		try {
			for (int index = 0; index < rowCount; index++) {
				String temp = readData.getCellData("brands", "Brand Name",
						index + 2);

				// Trimming the leading and Trailing Spaces in the Excel Sheet
				brandsText[index] = temp.replaceAll("^\\p{Z}+", "").replaceAll(
						"\\p{Z}+$", "");

				System.out.println(brandsText[index] + " <= is in Excel");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return brandsText;

	}

	/******* Check if All the Brand elements are sorted Alphabetically ************/
	public List<String> getBrandsNotSorted(String[] brandsInPage) {

		System.out
				.println("*******Check if All the Brand elements are sorted Alphabetically************");

		List<String> notSortedBrands = new ArrayList<String>();
		try {
			String[] sortedBrands = brandsInPage.clone();
			Arrays.sort(sortedBrands);

			List<WebElement> brandsElements = driver.findElements(brandsList);

			for (int index = 0; index < brandsInPage.length; index++) {

				scrollToView(brandsElements.get(index));

				if (!brandsInPage[index].equals(sortedBrands[index])) {
					System.out.println(brandsInPage[index]
							+ " in page is NOT sorted. The element should be "
							+ sortedBrands[index]);
					notSortedBrands.add(brandsInPage[index]);
				} else {
					System.out
							.println(brandsInPage[index]
									+ " in page is sorted. The corresponding element is "
									+ sortedBrands[index]);
				}
			}
			if (notSortedBrands.size() == 0) {
				System.out.println("===All the categories are Sorted===");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return notSortedBrands;

	}

	/*****
	 * Check if All the Brand elements in the Page are present in Excel
	 *******/
	public List<String> getBrandsNotInExcel(String[] brandsInPage,
			String[] brandsInExcel) {

		System.out
				.println("*****Check if All the Brand elements are present in Excel******");

		List<WebElement> brandsElements = driver.findElements(brandsList);
		List<String> brandsNotPresentInExcel = new ArrayList<String>();
		try {
			for (int index = 0; index < brandsInPage.length; index++) {

				scrollToView(brandsElements.get(index));

				boolean isPresent = Arrays.asList(brandsInExcel).contains(
						brandsInPage[index]);

				if (isPresent) {
					// System.out.println(brandsInPage[categoryInPage] +
					// " is present in Excel");
				} else {
					System.out.println(brandsInPage[index]
							+ " is NOT present in Excel");
					brandsNotPresentInExcel.add(brandsInPage[index]);
				}
			}
			if (brandsNotPresentInExcel.size() == 0) {
				System.out
						.println("All the elements in the Page are Present in the Excel");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return brandsNotPresentInExcel;

	}

	/**
	 * Check if all the Excel elements are present in the Page
	 **/
	public List<String> getBrandsNotInPage(String[] brandsInPage,
			String[] brandsInExcel) {

		System.out
				.println("*****Check if All the Brand elements are present in Page******");

		List<String> brandsNotPresentInPage = new ArrayList<String>();
		try {
			for (int index = 0; index < brandsInExcel.length; index++) {

				boolean isPresent = Arrays.asList(brandsInPage).contains(
						brandsInExcel[index]);

				if (isPresent) {
					// System.out.println(brandsInExcel[categoryInExel] +
					// " is present in the Page");
				} else {
					System.out.println(brandsInExcel[index]
							+ " is NOT present in the Page");
					brandsNotPresentInPage.add(brandsInExcel[index]);

				}
			}

			if (brandsNotPresentInPage.size() == 0) {
				System.out
						.println("All the elements in the Excel are present in Page");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		return brandsNotPresentInPage;

	}

	public void updatingInExcel(String fileName, String sheetName,
			String colName, int fromRowNum, List<String> data) {
		System.out.println("*****Updating the " + sheetName + "******");

		ReadExcelDataFile readData = new ReadExcelDataFile(
				System.getProperty("user.dir")
						+ "/src/main/resources/TestData/" + fileName);

		readData.clearExistingDataInSheet(sheetName, 2);

		for (int index = 0; index < data.size(); index++) {

			readData.setCellData(sheetName, "S.No", index + 2, "" + (index + 1));

			boolean flag = readData.setCellData(sheetName, colName, index + 2,
					data.get(index));
			Assert.assertEquals(true, flag);
			System.out.println(data.get(index) + " updated in Excel");
		}
	}

	public void updateBrandsNotSorted(List<String> brandsNotSorted) {

		updatingInExcel("brands.xlsx", "brands_not_sorted",
				"Not Sorted Brand Name", 2, brandsNotSorted);

	}

	public void updateBrandsNotInPage(List<String> brandsNotInPage) {

		updatingInExcel("brands.xlsx", "brands_not_in_page",
				"Not in Page Brand Name", 2, brandsNotInPage);

	}

	public void updateBrandsNotInExcel(List<String> brandsNotInExcel) {

		updatingInExcel("brands.xlsx", "brands_not_in_excel",
				"Not in Excel Brand Name", 2, brandsNotInExcel);

	}

}
