package baseClasses;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BaseTestClass {

	public WebDriver driver;

	/****************** Invoke Browser ***********************/
	public void invokeBrowser(String browserName) {

		try {

			if (browserName.equalsIgnoreCase("chrome")) {

				System.setProperty(
						"webdriver.chrome.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/chromedriver.exe");
//				ChromeOptions options = new ChromeOptions();
//				options.addArguments("headless");
				driver = new ChromeDriver();
			} else if (browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/geckodriver.exe");
//				FirefoxOptions options = new FirefoxOptions();
//				options.setHeadless(true);
				driver = new FirefoxDriver();
			} else if (browserName.equalsIgnoreCase("Opera")) {
				System.setProperty("webdriver.opera.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/operadriver");
				driver = new OperaDriver();
			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty(
						"webdriver.ie.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			} else if (browserName.equals("edge")) {
				System.setProperty(
						"webdriver.edge.driver",
						System.getProperty("user.dir")
								+ "/src/main/resources/Drivers/msedgedriver85.exe");

//				EdgeOptions edgeOptions = new EdgeOptions();
//				edgeOptions.addArguments("headless");

				driver = new EdgeDriver();
			} else if(browserName.equals("safari")) {
				driver = new SafariDriver();
			}
		} catch (Exception e) {
			// reportFail(e.getMessage());
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	/******* Closing the Web driver *********/
	// @AfterClass
	public void flushReports() {
		// report.flush();
		driver.close();
	}

	/************** Switch to New Tab *************/
	public void switchToNewTab() {
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		String mainTab = windowHandles.next();
		String newTab = windowHandles.next();

		driver.switchTo().window(newTab);
	}

	/************** Switch to Main Tab *************/
	public void switchToMainTab() {
		Iterator<String> windowHandles = driver.getWindowHandles().iterator();
		String mainTab = windowHandles.next();

		driver.switchTo().window(mainTab);
	}

	/*******Switch to Parent Frame********/
	public void switchToParentFrame(){
		driver.switchTo().parentFrame();
	}
	
	/***************** Wait Functions in Framework *****************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js
					.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js
					.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
