package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.SeleniumUtils;

public class WFSLoginPage {

	private  WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	
	
	public WFSLoginPage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		PageFactory.initElements(this.webDriver, this);
	}
	
	public WFSLoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(this.webDriver, this);
	}
	
	@FindBy(xpath="//*[@Name='reset']")
	public WebElement btnReset;
	
	@FindBy(xpath = "//*[@id='userEmail']")
	public WebElement txtUserEmail;
	
	@FindBy(xpath = "//*[@id='password']")
	public WebElement txtPassword;
	
	@FindBy(xpath = "//*[@id='submitButton']")
	public WebElement btnSubmit;
	
	@FindBy (xpath = "//a[@title='Logout']")
	public WebElement imgLogout;
	
	@FindBy (xpath= "/html/body/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/form/table/tbody/tr[1]/td/font")
	public WebElement lblFailedLoginMsg;
	
	
	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public ReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	
	public void userLogin(String username, String password) throws Exception {
		try {
			Reporter.log("userLogin : Enter login credential into user login form");
			SeleniumUtils.sendKeys(webDriver, txtUserEmail, username);
			SeleniumUtils.sendKeys(webDriver, txtPassword, password);
			SeleniumUtils.doClick(webDriver, btnSubmit);
			Reporter.log("user login is successfully clicked");
		} catch(Exception ex) {
			throw ex;
		}
	}
	
	public void userLogout() throws Exception {
		SeleniumUtils.doClick(webDriver, imgLogout);
		Reporter.log("user is logged out successfully");
	}
	public void doAssertUnsuccessfulErrorMessage(String expectedValue) throws Exception {
		String actualValue = SeleniumUtils.getValue(webDriver, lblFailedLoginMsg).trim();
		assertEquals(actualValue, expectedValue);
		reportGenerator.logAndCaptureScreen("unsuccessful login attempt is successfully verified","invalidLoginAttempt", Status.PASS, webDriver);
	}
	
	public void checkUserLoginRestBtn(String username, String password) throws Exception {
		try {
			Reporter.log("userLogin : Enter login credential into user login form");
			SeleniumUtils.sendKeys(webDriver, txtUserEmail, username);
			SeleniumUtils.sendKeys(webDriver, txtPassword, password);
			reportGenerator.logAndCaptureScreen("login details are entered successfully","checkUserLoginRestBtn", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, btnReset);
			String value = txtUserEmail.getAttribute("value");
			if(value.isEmpty()) {
			Reporter.log("user login deatails are cleared successfully");
			}else {
				Assert.fail("user logn details are not cleared");
			}
			reportGenerator.logAndCaptureScreen("login details are cleared successfully","checkUserLoginRestBtn", Status.PASS, webDriver);
		} catch(Exception ex) {
			throw ex;
		}
	}
}
