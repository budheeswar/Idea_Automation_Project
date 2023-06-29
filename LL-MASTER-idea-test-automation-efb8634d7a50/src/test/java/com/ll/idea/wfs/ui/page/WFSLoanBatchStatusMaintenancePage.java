package com.ll.idea.wfs.ui.page;

import java.util.HashMap;
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

public class WFSLoanBatchStatusMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;

	@FindBy(xpath = "//b[contains(text(),'Check LoanBatch Status')]")
	public WebElement CheckLoanBatchStatusPage;
	
	@FindBy(xpath = "//input[@id='txtLoanBatchId']")
	public WebElement CheckLoanBatchStatus_LoanBatchID;
	
	@FindBy(xpath = "//input[@value='Check Status']")
	public WebElement CheckLoanBatchStatus_CheckStatusBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Check LoanBatch Status')]")
	public WebElement CheckLoanBatchStatus_CheckStatusPopUp;
	
	@FindBy(id = "spnLoanBatchIdChecked")
	public WebElement CheckLoanBatchStatus_LoanBatchIDInPopup;
	
	@FindBy(xpath = "//div[@id='dialog-alert']")
	public WebElement CheckLoanBatchStatus_AlertPopup;
	
	@FindBy(xpath = "//div[@id='dialog-alert']/p[2]")
	public WebElement CheckLoanBatchStatus_AlertMsg;
	
	@FindBy(xpath = "(//td/input[@value='Close'])[1]")
	public WebElement CheckLoanBatchStatus_CloseBtn;
	
	@FindBy(xpath = "//button[text()='Ok']")
	public WebElement CheckLoanBatchStatus_OkBtn;
	
	

	public WFSLoanBatchStatusMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	public void checkLoanBatchStatus(HashMap<String, String> hashmap) throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.CHECK_LOAN_BATCH_STATUS);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, CheckLoanBatchStatusPage, "Check Loan Batch Status Page"));
			reportGenerator.logMessage("check Loan Batch Status page is displayed successfully", Status.PASS);
			SeleniumUtils.sendKeys(webDriver, CheckLoanBatchStatus_LoanBatchID, hashmap.get("BatchID"));
			SeleniumUtils.doClick(webDriver, CheckLoanBatchStatus_CheckStatusBtn);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, CheckLoanBatchStatus_CheckStatusPopUp, "Check Loan Batch Status Popup"));
			reportGenerator.logAndCaptureScreen("Loan batch Id is entered",
					"checkLoanBatchStatus", Status.PASS, webDriver);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver,
					CheckLoanBatchStatus_LoanBatchIDInPopup), hashmap.get("BatchID"));
			SeleniumUtils.doClick(webDriver, CheckLoanBatchStatus_CloseBtn);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void checkLoanBatchStatusNegativeScenarios(HashMap<String, String> hashmap) throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.CHECK_LOAN_BATCH_STATUS);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, CheckLoanBatchStatusPage, "Check Loan Batch Status Page"));
			reportGenerator.logMessage("check Loan Batch Status page is displayed successfully", Status.PASS);
			SeleniumUtils.sendKeys(webDriver, CheckLoanBatchStatus_LoanBatchID, hashmap.get("LoanBatchID"));
			SeleniumUtils.doClick(webDriver, CheckLoanBatchStatus_CheckStatusBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, CheckLoanBatchStatus_AlertPopup, "Alert Popup"));
			Assert.assertEquals(SeleniumUtils.getValue(webDriver,
					CheckLoanBatchStatus_AlertMsg), hashmap.get("ExpectedResult"));
			reportGenerator.logAndCaptureScreen("Alert Message is displayed as expected",
					"checkLoanBatchStatusNegativeScenarios", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, CheckLoanBatchStatus_OkBtn);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
}
