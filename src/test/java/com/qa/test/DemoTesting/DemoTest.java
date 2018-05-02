package com.qa.test.DemoTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DemoTest {

	WebDriver driver = null;
	private ExtentReports report = new ExtentReports(Constants.extentReportPath, true);
	private ExtentTest test;

	@Before
	public void setup() {
		ExcelUtils.setExcelFile(Constants.pathTestData + Constants.fileTestData, 0);
		System.setProperty("webdriver.chrome.driver", Constants.chromeWebDriverPath);
		driver = new ChromeDriver();
	}

	@Test
	public void testCreateLogin() {
		ArrayList<Throwable> failures = new ArrayList<Throwable>();
		
		for (int i = 1; i < ExcelUtils.ExcelWSheet.getPhysicalNumberOfRows(); i++) {
			test = report.startTest("Assert login - Excel Data: " + i);
			
			test.log(LogStatus.INFO, "Browser started");
			driver.get(Constants.demoLandingPageUrl);
			DemoLandingPage landingPage = PageFactory.initElements(driver, DemoLandingPage.class);
			landingPage.navigateToAddUser();
			test.log(LogStatus.INFO, "Navigated to add user page");
			
			AddUserPage addUserPage = PageFactory.initElements(driver, AddUserPage.class);
			addUserPage.addUser(ExcelUtils.getCellData(i, 0), ExcelUtils.getCellData(i, 1));
			test.log(LogStatus.INFO, "Added a user");
			addUserPage.navigateToLogin();
			test.log(LogStatus.INFO, "Navigated to login page");
			
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			loginPage.login(ExcelUtils.getCellData(i, 0), ExcelUtils.getCellData(i, 1));
			test.log(LogStatus.INFO, "Attempting login");

			String actualValue = loginPage.getLoginResult();
			String expectedValue = "**Successful Login**";
	
			if (actualValue.equals(expectedValue)) {
				test.log(LogStatus.PASS, "Assert successful login");
				ExcelUtils.setCellData("Pass", i, 2);
			} else {
				test.log(LogStatus.FAIL, "Assert successful login");
				ExcelUtils.setCellData("Fail", i, 2);
			}
			
			try {
				assertEquals(expectedValue, actualValue);
			} catch (Throwable t) {
				failures.add(t);
				System.out.println("\n-----Failure-----");
				System.out.println("Test:" + i + " Input username: " + ExcelUtils.getCellData(i, 0) + " Input password: " + ExcelUtils.getCellData(i, 1));
				System.out.println("Error: " + t.getMessage());
			}
		}
		
		report.endTest(test);
		report.flush();
		
		if (!failures.isEmpty()) {
			fail("Not all inputs passed.");
		}
	}

	@After
	public void tearDown() throws InterruptedException {
		Thread.sleep(3000);
		driver.close();
	}
}
