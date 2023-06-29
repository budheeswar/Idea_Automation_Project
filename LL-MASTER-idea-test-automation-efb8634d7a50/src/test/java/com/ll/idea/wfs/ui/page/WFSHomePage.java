package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.SeleniumUtils;

public class WFSHomePage {

	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	
	@FindBy(xpath = "/html/body/table/tbody/tr[1]/td/table/tbody/tr[1]/td[2]/table/tbody/tr[4]/td/span/b")
	public WebElement lblDashboardTitle;
	
	public WFSHomePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		PageFactory.initElements(this.webDriver, this);
	}
	
	public void doAssertHomepageTitle(String expectedValue) throws Exception  {
		reportGenerator.logAndCaptureScreen("doAssertHomepageTitle","HomePage", Status.INFO, webDriver);
		String actualValue = SeleniumUtils.getValue(webDriver, lblDashboardTitle);
		assertEquals(actualValue, expectedValue);
		reportGenerator.logMessage("Successfully launched home page & verified", Status.PASS);
	}
}
