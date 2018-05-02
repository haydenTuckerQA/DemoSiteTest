package com.qa.test.DemoTesting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoLandingPage {
	private WebDriver driver;
	
	public DemoLandingPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	@FindBy(xpath = "/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[3]")
	private WebElement addUserLink;

	public void navigateToAddUser() {
		addUserLink.click();
	}
}
