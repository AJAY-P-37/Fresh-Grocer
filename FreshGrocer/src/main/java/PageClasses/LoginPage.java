package PageClasses;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.ReadExcelDataFile;
import baseClasses.PageBaseClass;

public class LoginPage extends PageBaseClass {

	Hashtable<String, String> locators;

	public LoginPage(WebDriver driver) {
		super(driver);
		locators = readLocators("login_page");
		assignValuesForLocatorsFromExcel();
	}

	public By email;
	public By password;
	public By signInBtn;


	/****** Assign values to the By locators **********/
	public void assignValuesForLocatorsFromExcel() {
		email = getByLocator(locators,"email_id");
		password = getByLocator(locators,"password_id");
		signInBtn = getByLocator(locators,"signInBtn_xpath");

	}

	public void enterCredentials(String environment) {
		try {

			ReadExcelDataFile readData = new ReadExcelDataFile(
					System.getProperty("user.dir")
							+ "/src/main/resources/TestData/"
							+ "url_username_pwd.xlsx");
			String emailText = readData.getCellData(environment, "username", 2);
			String passwordText = readData.getCellData(environment, "password",
					2);

			driver.findElement(email).sendKeys(emailText);
			driver.findElement(password).sendKeys(passwordText);
			driver.findElement(signInBtn).click();

			System.out.println("Successfully Logged In");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

}
