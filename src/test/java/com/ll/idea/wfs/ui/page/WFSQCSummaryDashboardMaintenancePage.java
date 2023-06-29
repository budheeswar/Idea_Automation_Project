package com.ll.idea.wfs.ui.page;

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

public class WFSQCSummaryDashboardMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSMaintenancePage MaintenancePage = null;
	
	
	
	@FindBy(xpath="//select[@id='qImportType']")
	public WebElement qImportType;
	
	@FindBy(xpath="//select[@id='qVendor']")
	public WebElement qVendor;
	
	@FindBy(xpath="//select[@id='qProduct']")
	public WebElement qProduct;
	
	@FindBy(xpath="//input[@id='txtClient']")
	public WebElement txtClient;
	
	@FindBy(xpath="//ul/li[@class='ui-menu-item']/a")
	public List<WebElement> qClientsDropdownList;	
	
	@FindBy(xpath="//b[contains(text(),'Import QC Loan and Documents')]")
	public WebElement importQcLoans;
	
	@FindBy(xpath="//select[@id='qcVendorId']")
	public WebElement qcImport_ProcessingCenter;
	
	@FindBy(xpath="//select[@id='qcWorkflowId']")
	public WebElement qcImport_Workflow;
	
	@FindBy(xpath="//input[@id='btnChooseImportScreen']")
	public WebElement btnChooseImportScreen;
	
	@FindBy(xpath="//b[contains(text(),'QCIndex Loan Selection By Date and Vendor')]")
	public WebElement qcIndexLoanSelection;
	
	@FindBy(xpath="//input[@id='btnSearch']")
	public WebElement btnSearchInQc;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/tbody/tr/td[4]")
	public List<WebElement> qcLoanImport_LoanNumbers;
	
	@FindBy(xpath="//input[@id='btnNext']")
	public WebElement btnNext;
	
	@FindBy(xpath="//b[contains(text(),'Review QC Loan Import')]")
	public WebElement reviewQcImportLoans;
	
	@FindBy(xpath="(//input[@value='Save'])[2]")
	public WebElement btnSave;
	
	@FindBy(xpath="//b[contains(text(),'QC Loan Import Confirm')]")
	public WebElement qcLoanImportConfirm;
	
	@FindBy(xpath="//b[contains(text(),'Quality Control Dashboard - Summary')]")
	public WebElement qcDashbaord;
	
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
	
	public WFSQCSummaryDashboardMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
		MaintenancePage = new WFSMaintenancePage(this.webDriver, reportGenerator);
	}
	
	public WFSQCSummaryDashboardMaintenancePage(WebDriver webDriver) {
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
	 * This method is used to validate export loan in Qc import page.
	 * 
	 * @param hashmap
	 */
	public void selectQcImportedLoan(HashMap<String, String> hashmap) {
		try {

			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.QC_IMPORT);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, importQcLoans, "QC Import"));
			reportGenerator.logMessage("Qc Import clicked", Status.PASS);

			SeleniumUtils.selectFromComboBox(webDriver, qcImport_ProcessingCenter, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, qcImport_Workflow, hashmap.get("WorkflowId"));
			SeleniumUtils.doClick(webDriver, btnChooseImportScreen);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, qcIndexLoanSelection,
					"QCIndex Loan Selection By Date and Vendor"));
			SeleniumUtils.selectFromComboBox(webDriver, qImportType, hashmap.get("ImportType"));
			SeleniumUtils.selectFromComboBox(webDriver, qVendor, hashmap.get("VendorName"));
			SeleniumUtils.selectFromComboBox(webDriver, qProduct, hashmap.get("ProductName"));
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
			for(int index =0;index<qClientsDropdownList.size();index++) {
				if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)){
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			
			CommonUtils.sleepForAWhile();			
			SeleniumUtils.doClick(webDriver, btnSearchInQc);
			CommonUtils.sleepForAWhile();
			int valueIndex = 0;
			for (int index = 0; index < qcLoanImport_LoanNumbers.size(); index++) {
				if (qcLoanImport_LoanNumbers.get(index).getText().trim().equalsIgnoreCase(hashmap.get("LoanNumber"))) {
					valueIndex = index + 1;
					String sLoanNumberCheckbox = "//table[@id='tblDashboardIndexing']/tbody/tr[" + valueIndex
							+ "]/td[2]/preceding-sibling::td/input[1]";
					webDriver.findElement(By.xpath(sLoanNumberCheckbox)).click();
					break;
				}
			}

			String actualLoanNumberPath = "//table[@id='tblDashboardIndexing']/tbody/tr[" + valueIndex + "]/td[4]";

			Assert.assertEquals(webDriver.findElement(By.xpath(actualLoanNumberPath)).getText().trim(),
					hashmap.get("LoanNumber"));
			reportGenerator.logMessage(
					"the Loan Number [" + hashmap.get("LoanNumber") + "] is displayed in QCIndex Loan Selection page",
					Status.PASS);

			reportGenerator.logAndCaptureScreen(
					"The imported loan [" + hashmap.get("LoanNumber")
							+ "]is displayed under QCIndex Loan Selection page",
					"selectQcImportedLoan", Status.PASS, webDriver);

			SeleniumUtils.doClick(webDriver, btnNext);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, reviewQcImportLoans, "Review QC Loan Import"));
			SeleniumUtils.doClick(webDriver, btnSave);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, qcLoanImportConfirm, "QC Loan Import Confirm"));
			this.summaryOfQcImportedLoans(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}

	/**
	 * This method is used to validate summary of imported loans in Quality control summary
	 * dashboard page
	 * 
	 * @param hashmap
	 */
	public void summaryOfQcImportedLoans(HashMap<String, String> hashmap) {
		try {
			CommonUtils.sleepForAWhile(15000);
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.QC_SUMMARY_DASHBOARD);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, qcDashbaord, "Quality Control Dashboard - Summary"));
			reportGenerator.logMessage("Quality Control Dashboard - Summary page is successfully displayed",
					Status.PASS);
			String expandVendorxpath = "//a[contains(text(),'" + hashmap.get("VendorName") + "')]/../a[1]";
			WebElement ele = webDriver.findElement(By.xpath(expandVendorxpath));
			SeleniumUtils.doClick(webDriver, ele);
			String sLoanNumberXpath = "//a[contains(text(),'" + hashmap.get("VendorName")
					+ "')]/../../../../tbody/tr/td/a[contains(text(),'" + hashmap.get("LoanNumber") + "')]";
			WebElement LoanNumber = webDriver.findElement(By.xpath(sLoanNumberXpath));
			String sLoanNumber = LoanNumber.getText();
			Assert.assertEquals(sLoanNumber, hashmap.get("LoanNumber"));
			reportGenerator.logAndCaptureScreen(
					"The imported loan " + sLoanNumber + "is displayed under Quality Control Dashboard - Summary",
					"summaryOfQcImportedLoans", Status.PASS, webDriver, LoanNumber);

		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
}

