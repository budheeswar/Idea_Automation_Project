package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.SeleniumUtils;

public class WFSIndexingDashboardMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSMaintenancePage MaintenancePage = null;
	
	
	
	@FindBy(xpath="//span[contains(text(),'Show Loans LoanBatch Request Status')]")
	public WebElement showLoanBatchSatus;
	
	@FindBy(xpath="//select[@id='action']")
	public WebElement action;
	
	@FindBy(xpath="//input[@id='btnGo']")
	public WebElement btnGo;
	
	@FindBy(xpath="//span[contains(text(),'Confirmation')]")
	public WebElement confirmation;
	
	@FindBy(xpath="(//p[@class='dialog-content'])[3]")
	public WebElement confirmationMessage;
	
	@FindBy(xpath="//button[contains(text(),'Yes')]")
	public WebElement btnyes;
	
	@FindBy(xpath="//span[contains(text(),'Do NOT Show LoanBatch Request Status')]")
	public WebElement loanbatchRequestStatus;
	
	@FindBy(xpath="(//input[@value='Refresh'])[3]")
	public WebElement refreshBtn;
	
	@FindBy(xpath="(//*[contains(text(),'Finished')])[3]")
	public WebElement finishMessage;
	
	@FindBy(xpath="//*[@id='viewLoanUpdateRequestStatuses']//input[@value='Close']")
	public WebElement closeBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]" )
	public List<WebElement> confirmationDialogbox;
	
	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement btnYes;
	
	@FindBy(xpath = "//input[@id='chkEditAll']")
	public WebElement editAllCheckBox;
	
	@FindBy(xpath = "//input[@id='btnOkSelectLoansEdit']")
	public WebElement okbtn;
	
	@FindBy(xpath = "(//div[contains(text(),'Filter:')]/../div/input)[2]")
	public WebElement clientTextField;
	
	@FindBy(xpath = "//span[contains(text(),'Edit LoanBatch Request Status')]")
	public WebElement EditLoanBatchRequestStatusPopup;
	
	@FindBy(xpath = "//div[contains(@class,'vendor-filter')]")
	public WebElement vendorFilteradministrativeDashboard;
	
	@FindBy(xpath = "//div[contains(@class,'client-filter')]")
	public WebElement clientFilteradministrativeDashboard;
	
	@FindBy(xpath = "//div[contains(@class,'product-filter')]")
	public WebElement productFilteradministrativeDashboard;
	
	@FindBy(xpath = "//div[contains(@class,'qWorkflowLookupId-filter')]")
	public WebElement workflowFilteradministrativeDashboard;
	
	@FindBy(xpath = "//b[contains(text(),'Administrative Dashboard - Summary')]")
	public WebElement administrativeDashboard;
	
	@FindBy(xpath = "//table[@id='tblHeader']/thead/tr[2]/th" )
	public List<WebElement> administrativeDashboard_Headings;
	
	@FindBy(xpath = "//table[@id='tblStatisticResult']/tbody/tr/td[3]")
	public List<WebElement>administrativeDashboard_Results;
	
	@FindBy(xpath = "//table[@id='tblOperatorDashboard']/thead/tr/th/a")
	public List<WebElement> operatorDashboard_Headings;
	
	@FindBy(xpath = "//table[@id='tblOperatorDashboard']/tbody/tr/td[2]")
	public List<WebElement> operatorDashboard_Results;
	
	@FindBy(xpath = "//b[contains(text(),'List of Operators Logged into IDEA')]")
	public WebElement operatorsDashboard;
	
	@FindBy(xpath = "//span[contains(text(),'Edit LoanBatch Request Status')]")
	public WebElement indexLoanBatchStatus;
	
	@FindBy(xpath = "//*[contains(text(),'On Queue')]" )
	public WebElement statusOnQueue;
	
	@FindBy(xpath = "//table[contains(@class,'tbl-edit-loan-batches display')]/tbody/tr/td[contains(text(),'The Black Hole')]")
	public WebElement newVendorName;
	
	@FindBy(xpath="//table[@id='tblDashboardIndexing']/tbody/tr/td[contains(text(),'Index')]/following-sibling::td/span[contains(text(),'Unassigned')]/following::td[2]")
	public WebElement vendorName;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/tbody/tr/td[2]")
	public List<WebElement> IndexingDashboard_LoanNumber;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/thead/tr/th[17]/a[@id='editAll']")
	public WebElement IndexingDashboard_EditAll;
	
	@FindBy(xpath = "//input[@id='btnOkSelectLoansEdit']")
	public WebElement btnOkSelectLoansEdit;
	
	@FindBy(xpath = "//input[@id='saveEditMultiLoanBatchesButton']")
	public WebElement saveEditMultiLoanBatchesButton;
	
	@FindBy(xpath = "//span[contains(text(),'Edit Data Elements Workflow')]")
	public WebElement editDataWorkflowDialogb;
	
	@FindBy(xpath = "(//select[@id='newVendor'])[2]")
	public WebElement newVendor;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/tbody/tr/td[2]")
	public List<WebElement> IndexingDashboard_LoanNumbers;

	@FindBy(xpath = "//select[@id='qWorkflowLookupId']")
	public WebElement workflowDropdown;
	
	@FindBy(xpath = "//input[@id='btnSearch']")
	public WebElement btnSearch;
	
	@FindBy(xpath = "//td[contains(text(),'Vendor:')]")
	public WebElement vednorText;
	
	@FindBy(xpath = "(//span[contains(@class,'ui-icon-triangle-1-s')])[1]")
	public WebElement vendorDropdown;
	
	@FindBy(xpath = "//div[contains(@class,'vendor-filter')]/div/ul/li/following::label/span")
	public List<WebElement> vendorMenuOptions;
	
	@FindBy(xpath = "(//span[contains(@class,'ui-icon-triangle-1-s')])[2]")
	public WebElement indexProductDropdown;
	
	@FindBy(xpath = "//div[contains(@class,'product-filter')]/div/ul/li/following::label/span")
	public List<WebElement> productMenuOptions;
	
	@FindBy(xpath = "(//span[contains(@class,'ui-icon-triangle-1-s')])[3]" )
	public WebElement indexClientDropdown;
	
	@FindBy(xpath = "//div[contains(@class,'client-filter')]/div/ul/li/following::label/span")
	public List<WebElement> clientMenuOptions;
	
	@FindBy(xpath = "(//span[contains(@class,'ui-icon-triangle-1-s')])[6]")
	public WebElement indexWorkflowDropdown;
	
	@FindBy(xpath = "//div[contains(@class,'qWorkflowLookupId-filter')]/div/ul/li/following::label/span")
	public List<WebElement> workflowMenuOptions;
	
	@FindBy(xpath = "//div[contains(@aria-labelledby,'editDataWorkflowIndexingBean')]")
	public WebElement editDataWorkflowDialogbox;
	
	@FindBy(xpath = "(//select[@id='newPriority'])[1]")
	public WebElement newPriority;
	
	@FindBy(xpath = "//input[@id='saveEditDataWorkflowButton']")
	public WebElement saveEditBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Edit LoanBatch Request Status')]")
	public WebElement loanBatchStatus;
	
	@FindBy(xpath = "//*[contains(text(),'On Queue')]" )
	public WebElement onQueue;
	
	@FindBy(xpath = "(//input[@value='Refresh'])[2]")
	public WebElement btnRefresh;
	
	@FindBy(xpath = "(//*[contains(text(),'Finished')])[2]" )
	public WebElement finished;
	
	@FindBy(xpath = "(//input[@value='Close'])[5]")
	public WebElement btnClose;
	
	@FindBy(xpath = "//input[@Value='Clear']")
	public WebElement SimpleLoanImport_ClearBtn;
	
	@FindBy(xpath = "//div[@id='dialog-alert']/p[2]")
	public WebElement dailogueBoxText;
	
	@FindBy(xpath = "//button[contains(text(),'Ok')]")
	public WebElement dailogueBoxOkButton;
	
	@FindBy(xpath="//*[contains(@id,'chkEdit_')]")
	public WebElement checkEdit;
	
	@FindBy(xpath="//table[@id='tblDashboardIndexing']/tbody/tr[1]/td[2]/a")
	public WebElement loanNumIndex;
	
	@FindBy(xpath="//input[@id='btnSetFlag']")
	public WebElement btnSetFlag;
	
	@FindBy(xpath="//div[contains(@aria-labelledby,'dialog-alert')]")
	public WebElement alertDialogbox;
	
	@FindBy(xpath="//div[contains(@aria-labelledby,'dialog-alert')]//p[2]")
	public WebElement alertDialogboxText;
	
	@FindBy(xpath="//div[contains(@aria-labelledby,'dialog-alert')]//button")
	public WebElement btnOk;
	
	@FindBy(xpath="//a[contains(text(),'Flags')]/following::tr[1]/td[16]")
	public WebElement flagsStatus;
	
	@FindBy(xpath = "//b[contains(text(),'Administrative Dashboard - Indexing')]")
	public WebElement IndexingDashboardPage;

	@FindBy(xpath = "//select[@id='vendor']")
	public WebElement VendorDropdown;
	
	@FindBy(id = "btnSearch")
	public WebElement searchBtn;
	
	@FindBy(xpath = "//a[@title='Edit Data Element Workflow']")
	public WebElement IndexingDashboard_EditBtn;
	
	@FindBy(xpath = "//input[@value='ASC']")
	public WebElement AscendingRadioBtn;
	
	@FindBy(xpath = "//input[@value='DESC']")
	public WebElement DescendingRadioBtn;
	
	@FindBy(xpath = "//input[@id='qFromDate']")
	public WebElement FromDateField;
	
	@FindBy(xpath = "//input[@id='qToDate']")
	public WebElement ToDateField;
	
	@FindBy(xpath = "//select[@id='vendor']")
	public WebElement VendorField;
	
	@FindBy(xpath = "//select[@id='client']")
	public WebElement ClientField;
	
	@FindBy(xpath = "//select[@id='qSla']")
	public WebElement SLADropdown;
	
	@FindBy(id = "qLoanBatchId")
	public WebElement LoanBatchID;
	
	@FindBy(xpath = "//span[contains(text(),'Search For Multiple Loans at Once')]")
	public WebElement LoanBatchSearchDialogbox;
	
	@FindBy(id = "btnViewBatchSearch")
	public WebElement LoanBatchSearchBtn;
	
	@FindBy(xpath = "//textarea[@id='textarea_bSearchValues']")
	public WebElement searchTextBox;
	
	@FindBy(id = "bSearchType_loanBatchId")
	public WebElement LoanBatchIDRadioBtn;
	
	@FindBy(xpath = "//input[@id='btnBatchSearch']")
	public WebElement batchSearchbtn;
	
	@FindBy(id = "btnLast24Hours")
	public WebElement Last24HoursSearchBtn;
	
	@FindBy(xpath = "//select[@id='company']")
	public WebElement CompanyFilterDropdown;
	
	@FindBy(xpath = "//select[@id='qFlag']")
	public WebElement FlagFilterDropdown;
		
	@FindBy(xpath = "//select[@id='product']")
	public WebElement productDropdown;
	
	@FindBy(xpath = "//select[@id='qImportType']")
	public WebElement importTypeDropdown;
	
	@FindBy(xpath = "//select[@id='qState']")
	public WebElement stateDropdown;
	
	@FindBy(xpath = "//select[@id='qcProjectId']")
	public WebElement ProjectIDDropdown;
	
	@FindBy(xpath = "//select[@id='qWorkflowLookupId']")
	public WebElement WorkFlowLookUpIDDropdown;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/thead/tr/th/a")
	public List<WebElement> searchLoan_resultHeadings;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/tbody/tr/td")
	public List<WebElement> searchLoan_resultDetails;
	
	@FindBy(xpath = "//select[@id='qPriority']")
	public WebElement priorityDropdown;
	
	@FindBy(xpath = "//*[@id='bSearchType_loanNumber']")
	public WebElement LoanNumberRadioBtn;
	
	@FindBy(xpath = "(//div[contains(text(),'Showing 0 to 0 of 0 records')])[1]")
	public List<WebElement> noLoansFound;	
	
	@FindBy(xpath = "//*[@id=\"qState\"]") 
	public WebElement loanState;
	
	@FindBy(xpath="//span[contains(text(),'Check LoanBatch Status')]")
	public WebElement checkLoanBatchStatus;
	
	@FindBy(xpath="(//*[@id='tblCurrentWorkflowNodeStatus']/tbody/tr/td[contains(text(),'Data Entry 1')])[1]/following-sibling::td[2]/a")
	public WebElement workItemID;
	
	@FindBy(xpath="//table[@id='tblDashboardIndexing']/tbody/tr/td[2]/a[1]")
	public WebElement loanBatchID;
	
	@FindBy(xpath="(//input[@value='Close'])[2]")
	public WebElement CheckLoanBatchStatus_CloseBtn;
	
	public WFSIndexingDashboardMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
		MaintenancePage = new WFSMaintenancePage(this.webDriver, reportGenerator);
	}
	
	public WFSIndexingDashboardMaintenancePage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(this.webDriver, this);
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		MaintenancePage = new WFSMaintenancePage(this.webDriver, reportGenerator);
	}
	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	/**
	 * this method is used to validate Batch search functionality in indexing dashboard.
	 * @param hashmap
	 * @throws Exception
	 */
	public void batchSearchFunctionality(HashMap<String, String> hashmap) throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);
			SeleniumUtils.doClick(webDriver, Last24HoursSearchBtn);
			CommonUtils.sleepForAWhile();
			String xpath = "//a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/../preceding-sibling::td[1]/a";
			String sLoanBatchID = webDriver.findElement(By.xpath(xpath)).getText();
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanBatchIDRadioBtn);
			SeleniumUtils.sendKeys(webDriver, searchTextBox, sLoanBatchID);//need to give loan batch id
			reportGenerator.logAndCaptureScreen("Loan Batch ID is entered", "batchSearchFunctionality",
					Status.PASS, webDriver,LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
			
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int index = 0; index < searchLoan_resultHeadings.size(); index++) {
				actualDetails.put(searchLoan_resultHeadings.get(index).getText().trim(),
						searchLoan_resultDetails.get(index+1).getText().trim());
			}
			assertEquals(actualDetails.get("Loan Batch ID"), sLoanBatchID);
			reportGenerator.logMessage("Loan Batch ID displayed as " + actualDetails.get("Loan Batch ID"), Status.PASS);//need to give loan batch id
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "batchSearchFunctionality",
					Status.PASS, webDriver);
			
				} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * this method is used to validate Edit functionality in indexing dashboard.
	 * @param hashmap
	 * @throws Exception
	 */
	public void editFunctionalityIndexingDashboardPage(HashMap<String, String> hashmap) throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, VendorDropdown, "Vendor Dropdown"));
			reportGenerator.logMessage("Vendor Dropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, AscendingRadioBtn, "Ascending Radio Btn"));
			reportGenerator.logMessage("Ascending Radio Btn is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, DescendingRadioBtn, "Descending Radio Btn"));
			reportGenerator.logMessage("Descending Radio Btn is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, FromDateField, "FromDateField"));
			reportGenerator.logMessage("FromDateField is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ToDateField, "ToDateField"));
			reportGenerator.logMessage("ToDateField is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ClientField, "ClientField"));
			reportGenerator.logMessage("ClientField is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, SLADropdown, "SLADropdown"));
			reportGenerator.logMessage("SLADropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, CompanyFilterDropdown, "CompanyFilterDropdown"));
			reportGenerator.logMessage("CompanyFilterDropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, FlagFilterDropdown, "FlagFilterDropdown"));
			reportGenerator.logMessage("FlagFilterDropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, productDropdown, "productDropdown"));
			reportGenerator.logMessage("productDropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, importTypeDropdown, "importTypeDropdown"));
			reportGenerator.logMessage("importTypeDropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, stateDropdown, "stateDropdown"));
			reportGenerator.logMessage("stateDropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ProjectIDDropdown, "QC ProjectIDDropdown"));
			reportGenerator.logMessage("QC ProjectIDDropdown is displayed", Status.PASS);
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, priorityDropdown, "priorityDropdown"));
			reportGenerator.logMessage("priorityDropdown is displayed", Status.PASS);
			
			reportGenerator.logAndCaptureScreen("All fields were displayed correctly ", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, vendorDropdown);
			SeleniumUtils.selectValueFromList(webDriver, vendorMenuOptions, hashmap.get("VendorName"));
			reportGenerator.logAndCaptureScreen("Vendor name is selected", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver,vendorFilteradministrativeDashboard);
			SeleniumUtils.doClick(webDriver, vednorText);
			CommonUtils.sleepForAWhile();
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.doClick(webDriver, indexClientDropdown);
			SeleniumUtils.sendKeys(webDriver, clientTextField, sClient);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectValueFromList(webDriver, clientMenuOptions, sClient);
			reportGenerator.logAndCaptureScreen("Client name is selected", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver,clientFilteradministrativeDashboard);
			SeleniumUtils.doClick(webDriver, vednorText);
			CommonUtils.sleepForAWhile();
			
			SeleniumUtils.doClick(webDriver, indexProductDropdown);
			SeleniumUtils.selectValueFromList(webDriver, productMenuOptions, hashmap.get("ProductName"));
			reportGenerator.logAndCaptureScreen("Product name is selected", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver,productFilteradministrativeDashboard);
			SeleniumUtils.doClick(webDriver, vednorText);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, stateDropdown, hashmap.get("stateDropdown"));
			reportGenerator.logAndCaptureScreen("State name is selected", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver,stateDropdown);
			SeleniumUtils.doClick(webDriver, indexWorkflowDropdown);
			SeleniumUtils.selectValueFromList(webDriver, workflowMenuOptions, hashmap.get("WorkflowName"));
			reportGenerator.logAndCaptureScreen("Workflow is selected", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver,workflowFilteradministrativeDashboard);
			SeleniumUtils.doClick(webDriver, vednorText);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, btnSearch);
			CommonUtils.sleepForAWhile();
			String xpathp = "//a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/../following-sibling::td[1]";
			WebElement loanPriority = webDriver.findElement(By.xpath(xpathp));
			reportGenerator.logAndCaptureScreen("Loans displayed correctly", "editFunctionalityIndexingDashboardPage",
					Status.PASS, webDriver,loanPriority);
			String sLoanPriorityNo = webDriver.findElement(By.xpath(xpathp)).getText();
			reportGenerator.logMessage("The loan Priority ["+sLoanPriorityNo+"] is displayed", Status.PASS);
			
			String xpath = "//a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/../following-sibling::td[14]/a";
			 webDriver.findElement(By.xpath(xpath)).click();
			 
			 Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, editDataWorkflowDialogbox, "Edit Data Element Workflow"));
			 reportGenerator.logAndCaptureScreen("editDataWorkflowDialogbox is displayed with details", "editFunctionalityIndexingDashboardPage",
						Status.PASS, webDriver,editDataWorkflowDialogbox);
				reportGenerator.logMessage("Edit Data Element Workflow Dialogbox is displayed", Status.PASS);
				CommonUtils.sleepForAWhile();
				SeleniumUtils.selectFromComboBox(webDriver, newPriority, hashmap.get("newPriority"));
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("Fields were updated with new values", "editFunctionalityIndexingDashboardPage",
						Status.PASS, webDriver,editDataWorkflowDialogbox);
				SeleniumUtils.doClick(webDriver, saveEditBtn);
				CommonUtils.sleepForAWhile();
				 Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, loanBatchStatus, "Edit LoanBatch Request Status"));
				reportGenerator.logMessage("Edit LoanBatch Request Status Dialogbox is displayed", Status.PASS);
				
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, onQueue, "On Queue"));
				reportGenerator.logAndCaptureScreen("EditLoanBatchRequestStatusPopup is displayed with updated values", "editFunctionalityIndexingDashboardPage",
						Status.PASS, webDriver,EditLoanBatchRequestStatusPopup);
				SeleniumUtils.doClick(webDriver, btnRefresh);
				CommonUtils.sleepForAWhile();
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, finished, "Finished"));
				reportGenerator.logAndCaptureScreen("Status is displayed as finished in EditLoanBatchRequestStatusPopup", "editFunctionalityIndexingDashboardPage",
						Status.PASS, webDriver,EditLoanBatchRequestStatusPopup);
				SeleniumUtils.doClick(webDriver, btnClose);
				CommonUtils.sleepForAWhile();
				String pxpath = "//a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/../following-sibling::td[1]";
				String sLoanPriority = webDriver.findElement(By.xpath(pxpath)).getText();
				WebElement loanPriorty =  webDriver.findElement(By.xpath(pxpath));
				reportGenerator.logAndCaptureScreen("Values were updated successfully", "editFunctionalityIndexingDashboardPage",
						Status.PASS, webDriver,loanPriorty);
				reportGenerator.logMessage("The loan Priority ["+sLoanPriority+"] is displayed", Status.PASS);			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * this method is used to validate set flag functionality in indexing dashboard.
	 * @param hashmap
	 * @throws Exception
	 */
	public void setflagFunctionalityIndexingDashboardPage(HashMap<String, String> hashmap) throws Exception {
		try {
			SeleniumUtils.doClick(webDriver, checkEdit);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, btnSetFlag);
			reportGenerator.logAndCaptureScreen("SetFlag dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,MaintenancePage.setFlagDialogbox);
			if(MaintenancePage.setFlagDialogbox.isDisplayed()) {
				reportGenerator.logMessage("As expected SetFlag dialog box is displayed", Status.PASS);
				SeleniumUtils.doClick(webDriver, MaintenancePage.diyCheckbox);
				reportGenerator.logMessage("DIY checkbox is selected in SetFlag dialog box", Status.PASS);
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("DIY checkbix is selected in SetFlag dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,MaintenancePage.setFlagDialogbox);
				SeleniumUtils.doClick(webDriver, MaintenancePage.btnSaveFlag);
				if(alertDialogbox.isDisplayed()) {
					CommonUtils.sleepForAWhile();
					reportGenerator.logAndCaptureScreen("Alert dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,alertDialogbox);
					if (SeleniumUtils.getValue(webDriver, alertDialogboxText).contains("selected loans successfully")) {
						String messag = SeleniumUtils.getValue(webDriver, alertDialogboxText);
						reportGenerator.logMessage("Confirmation message ["+messag+"] is displayed", Status.PASS);
						SeleniumUtils.doClick(webDriver, btnOk);
				}
				}
				String flagStatus=SeleniumUtils.getValue(webDriver, flagsStatus);
				reportGenerator.logMessage("The falgstatus ["+flagStatus+"] is displayed", Status.PASS);
				CommonUtils.sleepForAWhile();
				}else {
					Assert.fail("setflag dialogbox is not displayed.");
				}
			
				SeleniumUtils.doClick(webDriver, checkEdit);
				CommonUtils.sleepForAWhile();
				SeleniumUtils.doClick(webDriver, btnSetFlag);
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("SetFlag dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,MaintenancePage.setFlagDialogbox);
				if(MaintenancePage.setFlagDialogbox.isDisplayed()) {
				SeleniumUtils.doClick(webDriver, MaintenancePage.rushAllCheckbox);
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("Rush All checkboxes are selected in SetFlag dialog box", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,MaintenancePage.setFlagDialogbox);
				CommonUtils.sleepForAWhile();
				boolean rushDocOps=MaintenancePage.rushDocOpsCheckbox.isSelected();
				reportGenerator.logMessage("Checking Rush Doc Ops boolean status:"+rushDocOps,Status.PASS);
				if(rushDocOps) {
					reportGenerator.logMessage("Rush Doc Ops checkbox is selected already in SetFlag dialog box", Status.PASS);
				}else {
					SeleniumUtils.doClick(webDriver,MaintenancePage.rushDocOpsCheckbox);
					reportGenerator.logMessage("Rush Doc Ops checkbox is selected in SetFlag dialog box", Status.PASS);
				}
				boolean runderwritings=MaintenancePage.rushUnderwritingCheckbox.isSelected();
				reportGenerator.logMessage("Checking Rush Underwriting checkbox boolean status:"+runderwritings,Status.PASS);
				if(runderwritings) {
					reportGenerator.logMessage("Rush Underwriting checkbox is selected already in SetFlag dialog box", Status.PASS);
				}else {
					SeleniumUtils.doClick(webDriver,MaintenancePage.rushUnderwritingCheckbox);
					reportGenerator.logMessage("Rush Underwriting checkbox is selected in SetFlag dialog box", Status.PASS);
				}
				
				CommonUtils.sleepForAWhile();
				SeleniumUtils.doClick(webDriver, MaintenancePage.btnSaveFlag);
				}else {
				Assert.fail("setflag dialogbox is not displayed.");
			}		
			if(alertDialogbox.isDisplayed()) {
				reportGenerator.logAndCaptureScreen("Alert dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,alertDialogbox);
				if (SeleniumUtils.getValue(webDriver, alertDialogboxText).contains("selected loans successfully")) {
					String messag = SeleniumUtils.getValue(webDriver, alertDialogboxText);
					reportGenerator.logMessage("Confirmation message ["+messag+"] is displayed", Status.PASS);
					SeleniumUtils.doClick(webDriver, btnOk);
			}
			}
			String flagStatus=SeleniumUtils.getValue(webDriver, flagsStatus);
			reportGenerator.logMessage("The falgstatus ["+flagStatus+"] is displayed", Status.PASS);
			SeleniumUtils.doClick(webDriver, checkEdit);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, btnSetFlag);
			reportGenerator.logAndCaptureScreen("SetFlag dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,MaintenancePage.setFlagDialogbox);
			if (MaintenancePage.setFlagDialogbox.isDisplayed()) {
				reportGenerator.logMessage("As expected SetFlag dialog box is displayed", Status.PASS);
				SeleniumUtils.doClick(webDriver, MaintenancePage.diyCheckbox);
				reportGenerator.logMessage("DIY checkbox is selected in SetFlag dialog box", Status.PASS);
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("DIY checkbix is selected in SetFlag dialog box is displayed",
						"setflagFunctionalityIndexingDashboardPage", Status.PASS, webDriver,
						MaintenancePage.setFlagDialogbox);
				SeleniumUtils.doClick(webDriver, MaintenancePage.rushAllCheckbox);
				CommonUtils.sleepForAWhile();
				reportGenerator.logMessage("Rush All checkboxes are selected in SetFlag dialog box", Status.PASS);
				reportGenerator.logAndCaptureScreen("DIY and Rush All checkboxes are selected in SetFlag dialog box",
						"setflagFunctionalityIndexingDashboardPage", Status.PASS, webDriver,
						MaintenancePage.setFlagDialogbox);
				CommonUtils.sleepForAWhile();
				boolean rushDocOps = MaintenancePage.rushDocOpsCheckbox.isSelected();
				reportGenerator.logMessage("Checking Rush Doc Ops boolean status:" + rushDocOps, Status.PASS);
				if (rushDocOps) {
					reportGenerator.logMessage("Rush Doc Ops checkbox is selected already in SetFlag dialog box",
							Status.PASS);
				} else {
					SeleniumUtils.doClick(webDriver, MaintenancePage.rushDocOpsCheckbox);
					reportGenerator.logMessage("Rush Doc Ops checkbox is selected in SetFlag dialog box", Status.PASS);
				}
				boolean runderwritings = MaintenancePage.rushUnderwritingCheckbox.isSelected();
				reportGenerator.logMessage("Checking Rush Underwriting checkbox boolean status:" + runderwritings,
						Status.PASS);
				if (runderwritings) {
					reportGenerator.logMessage("Rush Underwriting checkbox is selected already in SetFlag dialog box",
							Status.PASS);
				} else {
					SeleniumUtils.doClick(webDriver, MaintenancePage.rushUnderwritingCheckbox);
					reportGenerator.logMessage("Rush Underwriting checkbox is selected in SetFlag dialog box",
							Status.PASS);
				}
				CommonUtils.sleepForAWhile();
				SeleniumUtils.doClick(webDriver, MaintenancePage.btnSaveFlag);
			} else {
				Assert.fail("setflag dialogbox is not displayed.");
			}	
			if(alertDialogbox.isDisplayed()) {
				reportGenerator.logAndCaptureScreen("Alert dialog box is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,alertDialogbox);
				if (SeleniumUtils.getValue(webDriver, alertDialogboxText).contains("selected loans successfully")) {
					String messag = SeleniumUtils.getValue(webDriver, alertDialogboxText);
					reportGenerator.logMessage("Confirmation message ["+messag+"] is displayed", Status.PASS);
					SeleniumUtils.doClick(webDriver, btnOk);
			}
			}
			String flagStatusIndex=SeleniumUtils.getValue(webDriver, flagsStatus);
			reportGenerator.logAndCaptureScreen("Loan flag status is displayed", "setflagFunctionalityIndexingDashboardPage",Status.PASS, webDriver,flagsStatus);
			reportGenerator.logMessage("The falgstatus ["+flagStatusIndex+"] is displayed", Status.PASS);
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}
	
	public void filterByLoanForLast24Hours(HashMap<String, String> hashmap) throws Exception {
		try {
			this.filterByLast24Hours();
			if (hashmap.get("LoanNumber") != null) {
				SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
				SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
				SeleniumUtils.sendKeys(webDriver, searchTextBox, hashmap.get("LoanNumber"));
				reportGenerator.logAndCaptureScreen("Loan Batch ID is entered", "filterByLoanForLast24Hours",
						Status.PASS, webDriver, LoanBatchSearchDialogbox);
				SeleniumUtils.doClick(webDriver, batchSearchbtn);
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}
	public void filterByLast24Hours() throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);
			SeleniumUtils.doClick(webDriver, Last24HoursSearchBtn);
			CommonUtils.sleepForAWhile();
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}
	/**
	 * this method is used to validate clear functionality in simpleloanimportpage.
	 * @param hashmap
	 */
	public void clearFunctionalityInSimpleLoanImportPage(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.SIMPLE_LOAN_IMPORT);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver,MaintenancePage.SimpleLoanImport, "Simple Loan Import"));
			reportGenerator.logMessage("Simple Loan Import clicked", Status.PASS);
			
			SeleniumUtils.doClick(webDriver, SimpleLoanImport_ClearBtn);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, dailogueBoxText), hashmap.get("ExpectedAlertMessage"));
			reportGenerator.logAndCaptureScreen("System is displaying Alert popup with message"+SeleniumUtils.getValue(webDriver, dailogueBoxText),
					  "clearFunctionalityInSimpleLoanImportPage", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, dailogueBoxOkButton);
			
			SeleniumUtils.selectFromComboBox(webDriver, MaintenancePage.manglerNameDropdown, hashmap.get("ManglerName"));
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, MaintenancePage.CreateInputDirectory_ClientInputbox, sClient);
			for(int index =0;index<MaintenancePage.clientsDropDownListedValues.size();index++) {
				if(MaintenancePage.clientsDropDownListedValues.get(index).getText().trim().equalsIgnoreCase(sClient)){
					MaintenancePage.clientsDropDownListedValues.get(index).click();
					break;
				}
			}
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver,MaintenancePage.productIdDropdown, hashmap.get("ProductID"));
			
			SeleniumUtils.doClick(webDriver, SimpleLoanImport_ClearBtn);
			Select sel1 = new Select(MaintenancePage.productIdDropdown);
			String productID = sel1.getFirstSelectedOption().getText();
			Assert.assertFalse(productID.equalsIgnoreCase(hashmap.get("ProductID")));
			reportGenerator.logAndCaptureScreen("Fields are set to their default values after clicking Clear button",
					  "clearFunctionalityInSimpleLoanImportPage", Status.PASS, webDriver);
	
	} catch(Exception ex) {
		ex.printStackTrace();
		Assert.fail(ex.getMessage());
	}
}
	/**
	 * moveLoansToOtherVendor method is used to move the loans from one vendor to other vendor if any loans exists
	 * @param hashmap
	 */
	public void moveLoansToOtherVendor() {
		try { 
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			Reporter.log("Indexing Dashboard page is successfully displayed");
			if (EnvironmentPropertyLoader.getPropertyByName("wfs.vendor.name.to.change").equalsIgnoreCase("All")) {
				// do nothing
			} else {
				SeleniumUtils.doClick(webDriver, vendorDropdown);
				SeleniumUtils.selectValueFromList(webDriver, vendorMenuOptions,
						EnvironmentPropertyLoader.getPropertyByName("wfs.vendor.name.to.change"));
				// Moving Clearpath Vendor to The Black Hole
				SeleniumUtils.selectValueFromList(webDriver, vendorMenuOptions,
						EnvironmentPropertyLoader.getPropertyByName("wfs.vendor.name.to.change.clearpath"));
			}
			SeleniumUtils.doClick(webDriver, Last24HoursSearchBtn);
			CommonUtils.sleepForAWhile();
			if (noLoansFound.size() == 0) {

				SeleniumUtils.doClick(webDriver, editAllCheckBox);
				SeleniumUtils.doClick(webDriver, okbtn);
				SeleniumUtils.doClick(webDriver, IndexingDashboard_EditAll);
				
				if(confirmationDialogbox.size()>0) {
					SeleniumUtils.doClick(webDriver, btnYes);
				}
				
				Assert.assertTrue(
						SeleniumUtils.isDisplayed(webDriver, editDataWorkflowDialogb, "Edit Data Element Workflow"));
				Reporter.log("Edit Data Element Workflow Dialogbox is displayed");

				CommonUtils.sleepForAWhile();
				SeleniumUtils.selectFromComboBox(webDriver, newVendor,
						EnvironmentPropertyLoader.getPropertyByName("wfs.vendor.name.to.park"));
				CommonUtils.sleepForAWhile();
				SeleniumUtils.doClick(webDriver, saveEditMultiLoanBatchesButton);
				CommonUtils.sleepForAWhile();

				Assert.assertTrue(
						SeleniumUtils.isDisplayed(webDriver, indexLoanBatchStatus, "Edit LoanBatch Request Status"));
				Reporter.log("Edit LoanBatch Request Status Dialogbox is displayed");
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, statusOnQueue, "On Queue"));
				Reporter.log("On Queue is displayed");
				SeleniumUtils.doClick(webDriver, btnRefresh);
				CommonUtils.sleepForAWhile();

				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, finished, "Finished"));
				Reporter.log("Finished is displayed");
				SeleniumUtils.doClick(webDriver, btnClose);
				CommonUtils.sleepForAWhile();
			} else {
				Reporter.log("No Loans were displayed in Indexing Dashboard Page");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	
		/**
		 * This method is used to validate summary of imported loans in summary dashboard page
		 * @param hashmap
		 */
	public void summaryOfImportedLoans(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUMMARY_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, administrativeDashboard,
					"Administrative Dashboard - Summary'"));
			reportGenerator.logMessage("Administrative Dashboard - Summary page is successfully displayed",
					Status.PASS);
			String expandVendorxpath = "//a[contains(text(),'" + hashmap.get("VendorName") + "')]/../a[1]";
			WebElement ele = webDriver.findElement(By.xpath(expandVendorxpath));
			SeleniumUtils.doClick(webDriver, ele);
			String sLoanNumberXpath = "//a[contains(text(),'" + hashmap.get("VendorName")
					+ "')]/../../../../tbody/tr/td/a[contains(text(),'" + hashmap.get("LoanNumber") + "')]";
			CommonUtils.sleepForAWhile();
			WebElement LoanNumber = webDriver.findElement(By.xpath(sLoanNumberXpath));
			String sLoanNumber = LoanNumber.getText();
			Assert.assertEquals(sLoanNumber, hashmap.get("LoanNumber"));
			reportGenerator.logAndCaptureScreen(
					"The imported loan " + sLoanNumber + "is displayed under Administrative Dashboard - Summary",
					"summaryOfImportedLoans", Status.PASS, webDriver, LoanNumber);

		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		
	}
		/**
		 * check Do Not Show Functionality In IndexingPage is used to validate loan do not show functionality
		 * @param hashmap
		 */
		public void checkDoNotShowFunctionalityInIndexingPage(HashMap<String, String> hashmap) {
			try {
				SeleniumUtils.doClick(webDriver, checkEdit);
				CommonUtils.sleepForAWhile();
				SeleniumUtils.selectFromComboBox(webDriver, action, hashmap.get("ActionDropdown"));
				reportGenerator.logAndCaptureScreen(
						"The action dropdown is displayed",
						"checkDoNotShowFunctionalityInIndexingPage", Status.PASS, webDriver, btnGo);
				SeleniumUtils.doClick(webDriver, btnGo);
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen(
						"The confirmation dialogbox is displayed",
						"checkDoNotShowFunctionalityInIndexingPage", Status.PASS, webDriver, confirmation);
				String actualMessage=confirmationMessage.getText();
				Assert.assertEquals(actualMessage, hashmap.get("Expectedmesage"));
				SeleniumUtils.doClick(webDriver, btnyes);
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("The Do NOT Show LoanBatch Request Status dialogbox is displayed",
						"checkDoNotShowFunctionalityInIndexingPage", Status.PASS, webDriver, loanbatchRequestStatus);
				 Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, loanbatchRequestStatus, "Do NOT Show LoanBatch Request Status"));					
					Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, onQueue, "On Queue"));
					SeleniumUtils.doClick(webDriver, refreshBtn);
					CommonUtils.sleepForAWhile();
					Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, finishMessage, "Finished"));
					reportGenerator.logAndCaptureScreen("Status is displayed as finished in Do NOT Show LoanBatch Request Status Popup", "checkDoNotShowFunctionalityInIndexingPage",
							Status.PASS, webDriver,loanbatchRequestStatus);
					SeleniumUtils.doClick(webDriver, closeBtn);
					reportGenerator.logAndCaptureScreen("The loan is not displayed in indexing dashboard page", "checkDoNotShowFunctionalityInIndexingPage",
							Status.PASS, webDriver);
					SeleniumUtils.selectFromComboBox(webDriver, stateDropdown, hashmap.get("StateChanging"));
					SeleniumUtils.doClick(webDriver, btnSearch);
					CommonUtils.sleepForAWhile();
					String xpathp = "//a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/../following-sibling::td[1]";
					WebElement loanPriority = webDriver.findElement(By.xpath(xpathp));
					reportGenerator.logAndCaptureScreen("Do NOT Show Loan is displayed correctly", "checkDoNotShowFunctionalityInIndexingPage",
							Status.PASS, webDriver,loanPriority);
					this.checkShowLoansFunctionalityInIndexingPage(hashmap);
				
			}catch (Exception ex) {
				ex.printStackTrace();
				Assert.fail(ex.getMessage());
			}
		}
		
		/**
		 * check Show Loans Functionality In IndexingPage is used to show the do not show loans 
		 * @param hashmap
		 */
	public void checkShowLoansFunctionalityInIndexingPage(HashMap<String, String> hashmap) {
		try {
			SeleniumUtils.doClick(webDriver, checkEdit);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, action, hashmap.get("ActionStateDropdown"));
			reportGenerator.logAndCaptureScreen("The action dropdown is displayed",
					"checkShowLoansFunctionalityInIndexingPage", Status.PASS, webDriver, btnGo);
			SeleniumUtils.doClick(webDriver, btnGo);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The Show Loans LoanBatch Request Status dialogbox is displayed",
					"checkShowLoansFunctionalityInIndexingPage", Status.PASS, webDriver, showLoanBatchSatus);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, showLoanBatchSatus, "Show Loans LoanBatch Request Status"));
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, onQueue, "On Queue"));
			SeleniumUtils.doClick(webDriver, refreshBtn);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, finishMessage, "Finished"));
			reportGenerator.logAndCaptureScreen(
					"Status is displayed as finished in Show Loans LoanBatch Request Status Popup",
					"checkShowLoansFunctionalityInIndexingPage", Status.PASS, webDriver, showLoanBatchSatus);
			SeleniumUtils.doClick(webDriver, closeBtn);
			reportGenerator.logAndCaptureScreen("The loan is not displayed in indexing dashboard page",
					"checkShowLoansFunctionalityInIndexingPage", Status.PASS, webDriver);
			SeleniumUtils.selectFromComboBox(webDriver, stateDropdown, hashmap.get("stateDropdown"));
			SeleniumUtils.doClick(webDriver, btnSearch);
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
			SeleniumUtils.sendKeys(webDriver, searchTextBox, hashmap.get("LoanNumber"));
			reportGenerator.logAndCaptureScreen("Loan Batch ID is entered", "checkShowLoansFunctionalityInIndexingPage",
					Status.PASS, webDriver, LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
			CommonUtils.sleepForAWhile();
			String xpathp = "//a[contains(text(),'" + hashmap.get("LoanNumber") + "')]/../following-sibling::td[1]";
			WebElement loanPriority = webDriver.findElement(By.xpath(xpathp));
			reportGenerator.logAndCaptureScreen("Loan is displayed correctly",
					"checkShowLoansFunctionalityInIndexingPage", Status.PASS, webDriver, loanPriority);

		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	public boolean verifyListOfLoansInIndexingDashbaordPage(HashMap<String, String> hashmap) {
		boolean isExpected = false;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);
			reportGenerator.logAndCaptureScreen("All fields were displayed correctly ", "addParticularWorkflowLoansToList",
					Status.PASS, webDriver);
			SeleniumUtils.selectFromComboBox(webDriver, loanState, "All");
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
			searchTextBox.clear();
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			String sLoanNumbers = "";
			for (int count = 1; count <= noOfLoans; count++) {
				if(count==1) {
					sLoanNumbers = hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
				}else {
				sLoanNumbers = sLoanNumbers +";"+ hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
				}
			}
			searchTextBox.sendKeys(sLoanNumbers);
			reportGenerator.logAndCaptureScreen("Loan Number is entered", "searchListOfLoansInIndexingDashboard", Status.PASS,
					webDriver, LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
			CommonUtils.sleepForAWhile();
			for (int count = 1; count <= noOfLoans; count++) {
				String loanNumberFrmGrid ="//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]";
				WebElement loanNumberField = webDriver.findElement(By.xpath(loanNumberFrmGrid));
				String status="//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]/following::td[3]";
				WebElement loanNumberStatus = webDriver.findElement(By.xpath(status));
				String sworkFlowType = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]/following::td[10]";
				WebElement workFlowEle = webDriver.findElement(By.xpath(sworkFlowType));
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, loanNumberField, "Loans are displayed in Indexing Dashboard Page"));
				reportGenerator.logMessage("Loans are displayed in Indexing Dashboard page is successfully"+loanNumberField.getText()+"and"+loanNumberStatus.getText(), Status.PASS);
				if(count==1 || count==5) {
					reportGenerator.logAndCaptureScreen("Loans were displayed correctly", "verifyAndAddLoansToParticularWorkflowList",
							Status.PASS, webDriver,loanNumberField);
					reportGenerator.logAndCaptureScreen("Loans were displayed correctly", "verifyAndAddLoansToParticularWorkflowList",
							Status.PASS, webDriver,workFlowEle);
				}
			}	
		}catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		return isExpected;
	}
	public HashMap<String,List<String>> verifyAndAddLoansToParticularWorkflowList(HashMap<String, String> hashmap) {
		HashMap<String,List<String>> map = new HashMap<String,List<String>>();
		try {
			CommonUtils.sleepForAWhile();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);
			reportGenerator.logAndCaptureScreen("All fields were displayed correctly ", "addParticularWorkflowLoansToList",
					Status.PASS, webDriver);
			SeleniumUtils.selectFromComboBox(webDriver, loanState, "All");
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
			searchTextBox.clear();
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			String sLoanNumbers = "";
			for (int count = 1; count <= noOfLoans; count++) {
				if(count==1) {
					sLoanNumbers = hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
				}else {
				sLoanNumbers = sLoanNumbers +";"+ hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
				}
			}
			searchTextBox.sendKeys(sLoanNumbers);
			reportGenerator.logAndCaptureScreen("Loan Number is entered", "searchListOfLoansInIndexingDashboard", Status.PASS,
					webDriver, LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
			CommonUtils.sleepForAWhile();
			List<String> onePassAutomatedDE = new ArrayList<String>();
			List<String> standardWithEIR = new ArrayList<String>();
			List<String> managedAutomatedIndexingOnly = new ArrayList<String>();
			List<String> managedAutomatedIndexing1PassDE = new ArrayList<String>();
			List<String> IndexOnlyWithEIR = new ArrayList<String>();
			for (int count = 1; count <= noOfLoans; count++) {
				String loanNumberFrmGrid ="//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]";
				WebElement loanNumberField = webDriver.findElement(By.xpath(loanNumberFrmGrid));
				String status = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]/following::td[3]";
				String state = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]/following::td[2]";
				WebElement loanNumberStatus = webDriver.findElement(By.xpath(status));
				WebElement loanNumberState = webDriver.findElement(By.xpath(state));
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, loanNumberField, "Loans are displayed in Indexing Dashboard Page"));
				reportGenerator.logMessage("Loan "+loanNumberField.getText()+" is displayed with state "+loanNumberState.getText()+" and status as "+loanNumberStatus.getText(), Status.PASS);
				String sworkFlowType = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count))+"')]/following::td[10]";
				WebElement workFlowEle = webDriver.findElement(By.xpath(sworkFlowType));
				String workFlowType = webDriver.findElement(By.xpath(sworkFlowType)).getText();
				if(workFlowType.equalsIgnoreCase("ManagedAutomatedIndexingOnly")) {
					managedAutomatedIndexingOnly.add(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				}else if(workFlowType.equalsIgnoreCase("ManagedAutomatedIndexing1PassDE")){
					managedAutomatedIndexing1PassDE.add(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				}else if(workFlowType.equalsIgnoreCase("onePassAutomatedDE")){
					onePassAutomatedDE.add(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				}else if(workFlowType.equalsIgnoreCase("StandardWithEIR")){
					standardWithEIR.add(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				}else if(workFlowType.equalsIgnoreCase("IndexOnlyWithEIR")){
					IndexOnlyWithEIR.add(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				}
				if(count==1 || count==5) {
					reportGenerator.logAndCaptureScreen("Loans were displayed correctly", "verifyAndAddLoansToParticularWorkflowList",
							Status.PASS, webDriver,loanNumberField);
					reportGenerator.logAndCaptureScreen("Loans were displayed correctly", "verifyAndAddLoansToParticularWorkflowList",
							Status.PASS, webDriver,workFlowEle);
				}
			}	
			System.out.println(managedAutomatedIndexingOnly.size());
			System.out.println(managedAutomatedIndexing1PassDE.size());
			
			map.put("managedAutomatedIndexingOnly", managedAutomatedIndexingOnly);
			map.put("managedAutomatedIndexing1PassDE", managedAutomatedIndexing1PassDE);
			map.put("onePassAutomatedDE", onePassAutomatedDE);
			map.put("standardWithEIR", standardWithEIR);
			map.put("IndexOnlyWithEIR", IndexOnlyWithEIR);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		return map;
	}
	public boolean searchListOfLoansInIndexingDashboard(HashMap<String, String> hashmap) {
		try {
			CommonUtils.sleepForAWhile();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);
			reportGenerator.logAndCaptureScreen("All fields were displayed correctly ",
					"addParticularWorkflowLoansToList", Status.PASS, webDriver);
			SeleniumUtils.selectFromComboBox(webDriver, loanState, "All");
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
			searchTextBox.clear();
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			String sLoanNumbers = "";
			for (int count = 1; count <= noOfLoans; count++) {
				if(count==1) {
					sLoanNumbers = hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
				}else {
				sLoanNumbers = sLoanNumbers +";"+ hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
				}
			}
			searchTextBox.sendKeys(sLoanNumbers);
			reportGenerator.logAndCaptureScreen("Loan Number is entered", "searchListOfLoansInIndexingDashboard", Status.PASS,
					webDriver, LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
		}catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		return true;
	}
			
	public boolean initializeDetailsOfLoan(HashMap<String, String> hashmap) {
		try {	
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			for (int count = 1; count <= noOfLoans; count++) {
				String BatchTypeEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/following::td[9]";
				String PriorityEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/following::td[1]";
				String BatchIDEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/preceding::td[1]";
				String sBatchType = webDriver.findElement(By.xpath(BatchTypeEle)).getText(); 
				String sPriority = webDriver.findElement(By.xpath(PriorityEle)).getText(); 
				String sBatchID = webDriver.findElement(By.xpath(BatchIDEle)).getText(); 
				String sworkFlowType = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/following::td[10]";
				String workFlowType = webDriver.findElement(By.xpath(sworkFlowType)).getText();
				hashmap.put("BatchType",sBatchType);
				hashmap.put("Priority",sPriority);
				hashmap.put("BatchID",sBatchID);
				hashmap.put("loanWorkFlowType", workFlowType);
			}	
		}catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		return true;
	}
	public void checkWorkitemIdInLoanBatchStatusPopup(HashMap<String, String> hashmap) {
		try {
			String BatchIDEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/preceding::td[1]";
			webDriver.findElement(By.xpath(BatchIDEle)).click();
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, checkLoanBatchStatus, "Check LoanBatch Status"));
			reportGenerator.logMessage("Check LoanBatch Status popup is successfully displayed", Status.PASS);
			reportGenerator.logAndCaptureScreen("Check LoanBatch Status popup displayed correctly ",
					"checkWorkitemIdInLoanBatchStatusPopup", Status.PASS, webDriver);
			String workItem=workItemID.getText();
			hashmap.put("workItem", workItem);
			SeleniumUtils.doClick(webDriver, CheckLoanBatchStatus_CloseBtn);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		
	}
}

