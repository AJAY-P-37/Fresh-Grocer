package PageClasses;

import java.io.File;
import java.io.FilenameFilter;
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

public class CategoriesElements extends PageBaseClass {

	Hashtable<String, String> locators;

	public By categoriesList;

	public CategoriesElements(WebDriver driver) {
		super(driver);
		locators = readLocators("digital_coupons_page");
		assignValuesForLocatorsFromExcel();
	}

	/****** assigning the values for locators *********/
	public void assignValuesForLocatorsFromExcel() {
		categoriesList = getByLocator(locators, "categoriesList_xpath");
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

		String dir = System.getProperty("user.dir")+ "/src/main/resources/TestData";
		String fileNamePrefix = "categories";
		String path = readFileWithPrefix(dir, fileNamePrefix);
		ReadExcelDataFile readData = new ReadExcelDataFile(path);
		
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
			renameFileWithDateTime(path);
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

	public void updatingInExcel(String fileNamePrefix, String sheetName,
			String colName, int fromRowNum, List<String> data) {

		System.out.println("*****Updating the " + sheetName + "******");

		try {
			String dir = System.getProperty("user.dir")+ "/src/main/resources/TestData";

			String path = readFileWithPrefix(dir, fileNamePrefix);
			
			ReadExcelDataFile readData = new ReadExcelDataFile(path);

			readData.clearExistingDataInSheet(sheetName, 2);

			for (int index = 0; index < data.size(); index++) {

				readData.setCellData(sheetName, "S.No", index + 2, ""
						+ (index + 1));

				boolean flag = readData.setCellData(sheetName, colName,
						index + 2, data.get(index));
				Assert.assertEquals(true, flag);
				System.out.println(data.get(index) + " updated in Excel");
				
			}
			renameFileWithDateTime(path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reportFail(e.getMessage());
		}
		
	}

	public void updateCategoriesNotSorted(List<String> categoriesNotSorted) {

		updatingInExcel("categories", "categories_not_sorted",
				"Not Sorted Category Name", 2, categoriesNotSorted);

	}

	public void updateCategoriesNotInPage(List<String> categoriesNotInPage) {

		updatingInExcel("categories", "categories_not_in_page",
				"Not in Page Category Name", 2, categoriesNotInPage);

	}

	public void updateCategoriesNotInExcel(List<String> categoriesNotInExcel) {

		updatingInExcel("categories", "categories_not_in_excel",
				"Not in Excel Category Name", 2, categoriesNotInExcel);

	}

}
