package com.ll.idea.wfs.ui.page;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.SeleniumUtils;

public class WFSDashboardPage {

	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;

	@FindBy(xpath ="//*[@id=\"myjquerymenu\"]/ul/li[1]/ul/li[1]/a")
	List<WebElement> mainDashboardLink;
	@FindBy (xpath="//*[@id=\"formSearch\"]/tbody/tr/td[1]/fieldset[2]/table/tbody/tr[2]/td[6]")
	WebElement last24Hours;
	
	public WFSDashboardPage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		PageFactory.initElements(this.webDriver, this);
	}
	
	public void wFSMenuSelection(String mainMenu, String subMenu) throws Exception {
		try {
			reportGenerator.logMessage("IDEA Dashboard Menu Selection", Status.INFO);
			String sMenuXpath = "//*[@title='" + mainMenu + "']";
			WebElement mainMenuEle = webDriver.findElement(By.xpath(sMenuXpath));
			Actions action = new Actions(webDriver);
			action.moveToElement(mainMenuEle).build().perform();
			SeleniumUtils.selectValueFromList(webDriver, mainDashboardLink, subMenu);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void searchLoansByLast24Hours(String loanNumber) {
		reportGenerator.logMessage("searchLoansByLast24Hours", Status.INFO);
		last24Hours.click();
	}
}
