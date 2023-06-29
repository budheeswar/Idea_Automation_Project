package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.SeleniumUtils;

public class WFSOperatorDashboardMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	
	
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]")
	public WebElement confirmationoInperatorsDashboard;
	
	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement btnYes;
	
	@FindBy(xpath = "//b[contains(text(),'List of Operators Logged into IDEA')]")
	public WebElement operatorsDashboard;
	
	@FindBy(xpath = "//table[@id='tblOperatorDashboard']/tbody/tr/td[2]")
	public List<WebElement> operatorDashboard_Results;
	
	@FindBy(xpath = "//table[@id='tblOperatorDashboard']/thead/tr/th/a")
	public List<WebElement> operatorDashboard_Headings;
	
	public WFSOperatorDashboardMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	
	/**
	 * This method is used to validate list of operators are logged into Operation Dashbaord.
	 * @param hashmap
	 */
	public void getListOfOperatorsLoggedIn(HashMap<String, String> hashmap) {
		try {
			
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.OPERATOR_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, operatorsDashboard, "Operator Dashboard Page"));
			reportGenerator.logMessage("Operator Dashboard page is successfully displayed",Status.PASS);
			
			int index;
			int lineNumber=0;
			for(index=0;index<=operatorDashboard_Results.size();index++) {
			lineNumber = index+1;
			String xpath = "//table[@id='tblOperatorDashboard']/tbody/tr["+lineNumber+"]/td[2]";
			String sOperatorName = webDriver.findElement(By.xpath(xpath)).getText(); 
			if(sOperatorName.equalsIgnoreCase(hashmap.get("Loggedinuser"))) {//data need to come from hashmap
				reportGenerator.logMessage("the operational dashboard of Operator Name ["+sOperatorName+"] is displayed", Status.PASS);
				break;
			}
			}
			String lineDetailsxpath = "//table[@id='tblOperatorDashboard']/tbody/tr["+lineNumber+"]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(lineDetailsxpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int count = 0; count < operatorDashboard_Headings.size(); count++) {
				actualDetails.put(operatorDashboard_Headings.get(count).getText().trim(),
						ele.get(count).getText().trim());
			}
			
			assertEquals(actualDetails.get("Operator Name"), hashmap.get("Loggedinuser"));//hashmapp need to add
			reportGenerator.logMessage("Operator Name is displayed as " + actualDetails.get("Operator Name"), Status.PASS);

			assertEquals(actualDetails.get("Current Role"), hashmap.get("CurrentRole"));
			reportGenerator.logMessage("Current Role is displayed as " + actualDetails.get("Current Role"), Status.PASS);
			
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "listOfOperatorsLoggedIntoOperationDashbaord",
					Status.PASS, webDriver);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		
	}
	
	public void logOutOperatorFromWFSConsole(HashMap<String, String> hashmap) {
		try {
			
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.OPERATOR_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, operatorsDashboard, "Operator Dashboard Page"));
			reportGenerator.logMessage("Operator Dashboard page is successfully displayed",Status.PASS);
			
			int index;
			int lineNumber=0;
			for(index=0;index<=operatorDashboard_Results.size();index++) {
			lineNumber = index+1;
			String xpath = "//table[@id='tblOperatorDashboard']/tbody/tr["+lineNumber+"]/td[2]";
			String sOperatorName = webDriver.findElement(By.xpath(xpath)).getText(); 
			if(sOperatorName.equalsIgnoreCase(hashmap.get("OperatorLogin"))) {
				reportGenerator.logMessage("the operational dashboard of Operator Name ["+sOperatorName+"] is displayed", Status.PASS);
				break;
			}
			}
			CommonUtils.sleepForAWhile(5000);
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "validateListOperatores",
					Status.PASS, webDriver);
			
			String sxpath="//table[@id='tblOperatorDashboard']/tbody/tr["+lineNumber+"]/td[2]/following-sibling::td/a";
			webDriver.findElement(By.xpath(sxpath)).click();
			CommonUtils.sleepForAWhile(5000);
			reportGenerator.logAndCaptureScreen("Comfirmation dialogbox is displayed", "validateListOperatores",
					Status.PASS, webDriver,confirmationoInperatorsDashboard);
			
			SeleniumUtils.doClick(webDriver, btnYes);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "validateListOperatores",
					Status.PASS, webDriver);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
}
