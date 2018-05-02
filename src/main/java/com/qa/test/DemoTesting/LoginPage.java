package com.qa.test.DemoTesting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	@FindBy(xpath = "/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/p/input")
	private WebElement usernameInput;
	
	@FindBy(xpath = "/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/p/input")
	private WebElement passwordInput;
	
	@FindBy(xpath = "/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/p/input")
	private WebElement submitForm;

	public void login(String username, String password) {
		usernameInput.sendKeys(username);
		passwordInput.sendKeys(password);
		submitForm.click();
		
		if (isAlertPresent()) {
		    driver.switchTo().alert();
		    driver.switchTo().alert().accept();
		    driver.switchTo().defaultContent();
		}
	}
	
	@FindBy(xpath = "/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b")
	private WebElement loginResult;

	public String getLoginResult() {
		return loginResult.getText();
	}
	
	public boolean isAlertPresent() {
	    try {
	        driver.switchTo().alert();
	        return true;
	    } // try
	    catch (Exception e) {
	        return false;
	    } // catch
	}
}
