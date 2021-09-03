package PageClasses;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		email = getByLocator(locators, "email_id");
		password = getByLocator(locators, "password_id");
		signInBtn = getByLocator(locators, "signInBtn_xpath");

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

			int count = 0;
			do {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 30);
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(email));

					driver.findElement(email).sendKeys(emailText);
					driver.findElement(password).sendKeys(passwordText);
					driver.findElement(signInBtn).click();
					System.out.println("Success: Logged In in attempt no. "
							+ (count + 1));

					break;
				} catch (Exception e) {
					System.out
							.println("Page did NOT load properly. Refreshing");
					refreshPage();
				}
				count++;
				if (count == 3) {

					System.out.println("Sign In page did NOT load even after "
							+ count + " attempts, for 30 seconds each attempt");
					reportFail("Sign In page did NOT load even after " + count
							+ " attempts, for 30 seconds each attempt");
				}
			} while (count <= 3);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}
}
