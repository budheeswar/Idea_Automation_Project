package com.ll.idea.wfs.ui.page;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaEFSConstants;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.SeleniumUtils;

public class WFSMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	
	
	@FindBy(xpath = "//span[contains(text(),'Search For Multiple Loans at Once')]")
	public WebElement LoanBatchSearchDialogbox;
	
	@FindBy(xpath = "//select[@id='vendorId']")
	public WebElement SimpleLoanImport_ProcessingCenter;
	
	@FindBy(xpath = "//input[@Value='Clear']")
	public WebElement SimpleLoanImport_ClearBtn;
	
	@FindBy(xpath = "//div[@id='dialog-alert']/p[2]")
	public WebElement dailogueBoxText;
	
	@FindBy(xpath = "//button[contains(text(),'Ok')]")
	public WebElement dailogueBoxOkButton;
	
	@FindBy(xpath = "//input[@id='btnSetFlag']")
	public WebElement btnSetFlag;
	
	@FindBy(xpath = "//div[contains(@aria-labelledby,'dialog-title-setFlag')]")
	public WebElement setFlagDialogbox;
	
	@FindBy(xpath = "//input[@id='diy']")
	public WebElement diyCheckbox;
	
	@FindBy(xpath = "//input[@id='rushAll']")
	public WebElement rushAllCheckbox;
	
	@FindBy(xpath = "//input[@id='rushDocOps']")
	public WebElement rushDocOpsCheckbox;
	
	@FindBy(xpath = "//input[@id='rushUnderwriting']")
	public WebElement rushUnderwritingCheckbox;
	
	@FindBy(xpath = "//input[@id='btnSaveFlag']")
	public WebElement btnSaveFlag;
	
	@FindBy(xpath = "//*[contains(text(),'already existed')]")
	public WebElement alreadyExistMessage;
	
	@FindBy(xpath = "//a[@id='editWorkflow']")
	public WebElement editWorkflowBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]")
	public WebElement Confirmation;
	
	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement Yesbtn;
	
	@FindBy(id = "btnLast24Hours")
	public WebElement Last24HoursSearchBtn;
	
	@FindBy(xpath = "//input[@id='btnBatchSearch']")
	public WebElement batchSearchbtn;
	
	@FindBy(id = "btnViewBatchSearch")
	public WebElement LoanBatchSearchBtn;
	
	@FindBy(xpath = "//*[@id='bSearchType_loanNumber']")
	public WebElement LoanNumberRadioBtn;
	
	@FindBy(xpath = "//textarea[@id='textarea_bSearchValues']")
	public WebElement searchTextBox;
	
	@FindBy(xpath = "//b[contains(text(),'Administrative Dashboard - Indexing')]")
	public WebElement IndexingDashboardPage;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/tbody/tr[3]/td[3]/a[1]")
	public WebElement LoannumberIndexingDashboardPage;
	
	@FindBy(xpath = "//table[@id='tblDashboardIndexing']/tbody/tr[3]/td[6]/span[contains(text(),'Unassigned')]")
	public WebElement LoannumberStatusIndexingDashboardPage;
	
	@FindBy(xpath = "//input[@value='Save']")
	public WebElement SaveInReviewLoanImport;
	
	@FindBy(xpath = "//b[contains(text(),'Single Loan Import - Confirmation')]")
	public WebElement SingleLoanImportConfirmation;
	
	@FindBy(xpath = "//td[contains(text(),'The following have bean commited to the IDEA workflow.')]")
	public WebElement SuccessMessage;
	
	@FindBy(xpath = "//select[@id = 'workflowLookupId']")
	public WebElement workflowDropdown;
	
	@FindBy(xpath = "//input[@id='showAllLoans']" )
	public WebElement showAllLoans;
	
	@FindBy(xpath = "//table[@id='loanFileTable']/tbody/tr/td[2]")
	public WebElement loanNumber;
	
	@FindBy(xpath = "//input[@id='selectall']")
	public WebElement selectall;
		
	@FindBy(xpath = "//input[@value='Next']")
	public WebElement Next;
	
	@FindBy(xpath = "//div[contains(@aria-labelledby,'title-dialog-confirm')]")
	public WebElement confirmationDialog;
	
	@FindBy(xpath = "//p[contains(text(),'Loan numbers highlighted in yellow')]")
	public WebElement confirmationMessage;
	
	@FindBy(xpath="//p[contains(text(),'are already in IDEA.')]")
	public WebElement confirmationMessageAlreadyInIdea;
	
	@FindBy(xpath = "//button[contains(text(),'Yes')]" )
	public WebElement Yes;
	
	@FindBy(xpath = "//b[contains(text(),'Review Loan Import')]" )
	public WebElement ReviewLoanImport;
	
	@FindBy(xpath = "//table//td[contains(text(),'Loan Number')]/following::tr[1]/td[1]")
	public WebElement loannumberInReviewPage;
	
	@FindBy(xpath = "//select[@name = 'manglerName']")
	public WebElement manglerNameDropdown;
	
	@FindBy(xpath = "//select[@id='productId']")
	public WebElement productIdDropdown;
	
	@FindBy(xpath = "//input[@id='txtClient']")
	public WebElement txtClient;
	
	@FindBy(xpath = "//a[@id = 'ui-active-menuitem']")
	public WebElement ClickClient;
	
	@FindBy(xpath = "//b[contains(text(),'Simple Loan Import')]")
	public WebElement SimpleLoanImport;
	
	@FindBy(xpath = "//select[@id = 'propertyNameId']")
	public WebElement PropertyIdDropdown;
	
	@FindBy(xpath ="//b[contains(text(),'Change Configuration Properties')]")
	public WebElement ChangeConfigurationPropertiesPage;
	
	@FindBy(xpath ="//input[@id = 'propertyValueId']")
	public WebElement PropertyValue;
	
	@FindBy(xpath = "//input[@value='Save']")
	public WebElement save;
	
	@FindBy(xpath = "//*[@id='propertiesId']")
	public WebElement Properties;
	
	@FindBy(xpath = "//b[contains(text(),'Create Input Directory')]")
	public WebElement CreateInputDirectoryPage;

	@FindBy(id = "folderSourceId")
	public WebElement folderSourceIdDropdown;

	@FindBy(id = "manglerId")
	public WebElement manglerIdDropdown;

	@FindBy(xpath = "//input[@id='txtClient']")
	public WebElement CreateInputDirectory_ClientInputbox;
	
	@FindBy(xpath = "//li[@role='menuitem']/a")
	public List<WebElement> clientsDropDownListedValues;
	
	@FindBy(id = "productId")
	public WebElement CreateInputDirectory_productId;

	@FindBy(xpath = "//input[@value='Create Directory']")
	public WebElement CreateInputDirectory_CreateDirectoryBtn;

	@FindBy(xpath = "//font[@color='blue']")
	public WebElement successMessageInBlueColor;

	@FindBy(id = "txtNameBase")
	public WebElement SuggestedNameBase;

	@FindBy(id = "txtDirectoryName")
	public WebElement txtDirectoryName;

	@FindBy(xpath = "//body/pre")
	public WebElement clearCachetxt;
	
	@FindBy(xpath = "//table[@id='loanFileTable']/tbody/tr/td[2]")
	public List<WebElement> sampleLoanImport_LoanNumbers;
	
	@FindBy(xpath = "//*[@id=\"qState\"]") 
	public WebElement loanState;
	
	//vendor change
	@FindBy(xpath = "//td/a[contains(text(),'Edit')]")
	public WebElement Edit;
	
	//dropdown
	@FindBy(xpath = "//select[@id='newVendor']")
	public WebElement NewVendor;
	
	//save
	@FindBy(xpath ="//input[@id='saveEditDataWorkflowButton']")
	public WebElement saveVendor;
	
	//Xpath from WFSINDEX
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
	
	
	
	@FindBy(xpath="//*[contains(@id,'chkEdit_')]")
	public WebElement checkEdit;
	
	@FindBy(xpath="//table[@id='tblDashboardIndexing']/tbody/tr[1]/td[2]/a")
	public WebElement loanNumIndex;
	
	
	@FindBy(xpath="//div[contains(@aria-labelledby,'dialog-alert')]")
	public WebElement alertDialogbox;
	
	@FindBy(xpath="//div[contains(@aria-labelledby,'dialog-alert')]//p[2]")
	public WebElement alertDialogboxText;
	
	@FindBy(xpath="//div[contains(@aria-labelledby,'dialog-alert')]//button")
	public WebElement btnOk;
	
	@FindBy(xpath="//a[contains(text(),'Flags')]/following::tr[1]/td[16]")
	public WebElement flagsStatus;
	
	

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
	
	
	
	@FindBy(id = "bSearchType_loanBatchId")
	public WebElement LoanBatchIDRadioBtn;
	
	
	
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
	
	
	
	@FindBy(xpath = "(//div[contains(text(),'Showing 0 to 0 of 0 records')])[1]")
	public List<WebElement> noLoansFound;	
	
	
	
	@FindBy(xpath="//span[contains(text(),'Check LoanBatch Status')]")
	public WebElement checkLoanBatchStatus;
	
	@FindBy(xpath="(//*[@id='tblCurrentWorkflowNodeStatus']/tbody/tr/td[contains(text(),'Data Entry 1')])[1]/following-sibling::td[2]/a")
	public WebElement workItemID;
	
	@FindBy(xpath="//table[@id='tblDashboardIndexing']/tbody/tr/td[2]/a[1]")
	public WebElement loanBatchID;
	
	@FindBy(xpath="(//input[@value='Close'])[2]")
	public WebElement CheckLoanBatchStatus_CloseBtn;
	
	



	public WFSMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	
	public void importLoanIntoWFS(ITestContext iTestContext, HashMap<String, String> hashmap,ConnectToLinuxBox connectToLinuxBox) {
		// create client input directory through wfs console <-Tools-utility.
		try {
			this.createInputDirectory(hashmap);
		// Invoke checkAndCreateInputDirInEFS
		String sLoanPdfPath = connectToLinuxBox.copyAndRenameFile(hashmap, reportGenerator.getReportPath());
		String sRemoteDir = IdeaWFSConstants.MANGLAR_BASE_OUTPUT_DIR + hashmap.get("ClientID") + "."
													+ hashmap.get("ProductID") + "_" + hashmap.get("ClientName") + "." + hashmap.get("ProductName");
		connectToLinuxBox.transferLoanInToEFS(EnvironmentPropertyLoader.getPropertyByName("wfs_server_host"),
													EnvironmentPropertyLoader.getPropertyByName("user"), sRemoteDir,
													EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), sLoanPdfPath);
		// create Simple LoanImport and validate loans in dashboard
		this.createSimpleLoanImport(hashmap); 
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}
	/**
	 * createInputDirectory  is a method used to create the input directory in EFS server
	 * @param hashmap
	 * @throws Exception
	 */
	public void createInputDirectory(HashMap<String, String> hashmap) throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.UTILITIES,
					IdeaWFSConstants.CREATE_INPUT_DIRECTORY);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, CreateInputDirectoryPage, "Create Input Directory Page"));
			reportGenerator.logMessage("Create Input Directory is successfully clicked", Status.PASS);
			SeleniumUtils.selectFromComboBox(webDriver, folderSourceIdDropdown, hashmap.get("FolderSource"));
			SeleniumUtils.selectFromComboBox(webDriver, manglerIdDropdown, hashmap.get("ManglerName"));
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, CreateInputDirectory_ClientInputbox, sClient);
			for(int index =0;index<clientsDropDownListedValues.size();index++) {
				if(clientsDropDownListedValues.get(index).getText().trim().equalsIgnoreCase(sClient)){
					clientsDropDownListedValues.get(index).click();
					break;
				}
			}
			SeleniumUtils.selectFromComboBox(webDriver, CreateInputDirectory_productId, hashmap.get("ProductName"));
			reportGenerator.logAndCaptureScreen(
					"The Create Input Directory page fields are filled up with required values", "createInputDirectory",
					Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, CreateInputDirectory_CreateDirectoryBtn);
			CommonUtils.sleepForAWhile();
			
			try {
			if(successMessageInBlueColor.isDisplayed()) {
				reportGenerator.logAndCaptureScreen("Input Directory is created successfully", "createInputDirectory",
						Status.PASS, webDriver);
			}
			} catch(Exception e) {
				try {
					if(alreadyExistMessage.isDisplayed() & alreadyExistMessage.isEnabled()) {
						reportGenerator.logAndCaptureScreen("create Input Directory is already exist", "createInputDirectory",
								Status.PASS, webDriver);
					}
				}catch(Exception e1) {
					Assert.fail("Input Directory is not created");
				}
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * clearCache is a method used to clear cache data in the WFS application
	 * @param hashmap
	 * @throws Exception
	 */
	public void clearCache() throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.UTILITIES,
					IdeaWFSConstants.CLEAR_CACHE);
			CommonUtils.sleepForAWhile();
			if (SeleniumUtils.getValue(webDriver, clearCachetxt).contains("Cleared cache")) {
				reportGenerator.logAndCaptureScreen("Cache is cleared successfully", "clearCache", Status.PASS,
						webDriver);
			} else {
				Assert.fail("Cache is not cleared");
			}
			CommonUtils.sleepForAWhile();
			webDriver.navigate().back();
			webDriver.navigate().refresh();
			CommonUtils.sleepForAWhile();
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	/**
	 * checkAndAppendSkVersion is used to update the SK version in change config properties page 
	 * @throws Exception
	 */
	public void checkAndAppendSkVersion()throws Exception{
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.CHAGE_CINFIG_PROPERIES);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ChangeConfigurationPropertiesPage, "Change Configuration Properties"));
			Reporter.log("Change Configuration Properties is successfully clicked");
			SeleniumUtils.selectFromComboBox(webDriver, PropertyIdDropdown, IdeaWFSConstants.SUPPORTED_SK_VERSION_LBL);
			SeleniumUtils.doClick(webDriver, PropertyValue);
			String supportedVersions = PropertyValue.getAttribute("value");
			String sCurrentSKVersion = EnvironmentPropertyLoader.getPropertyByName("Smartkey.version");
			if(!(supportedVersions.contains(sCurrentSKVersion))) {
				supportedVersions = supportedVersions + ";" + sCurrentSKVersion;
				SeleniumUtils.sendKeys(webDriver, PropertyValue,supportedVersions);
				CommonUtils.sleepForAWhile();
			}
			Reporter.log(sCurrentSKVersion + " is set successfully");
			SeleniumUtils.doClick(webDriver, save);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public void modifyAndUpdateConfig(HashMap<String, String> hashmap)throws Exception{
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.CHAGE_CINFIG_PROPERIES);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ChangeConfigurationPropertiesPage, "Change Configuration Properties"));
			reportGenerator.logMessage("Change Configuration Properties is successfully clicked", Status.PASS);
			SeleniumUtils.selectFromComboBox(webDriver, PropertyIdDropdown, hashmap.get("PropertyKey"));
			SeleniumUtils.doClick(webDriver, PropertyValue);
			String sCurrentValue = PropertyValue.getAttribute("value");
			String sNewValue = hashmap.get("PropertyValue");
			if(hashmap.get("OverrideFlag").equals("Replace")) {
				SeleniumUtils.sendKeys(webDriver, PropertyValue,sNewValue);
			} else if(!(sCurrentValue.contains(sNewValue))) {
				sNewValue = sCurrentValue + ";" + sNewValue;
				SeleniumUtils.sendKeys(webDriver, PropertyValue,sNewValue);
			}
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("Configuration value is displayed as " + hashmap.get("PropertyValue"), "modifyAndUpdateConfig" , Status.PASS,webDriver);
			SeleniumUtils.doClick(webDriver, save);
			String text=SeleniumUtils.getValue(webDriver, Properties);
			reportGenerator.logMessage("Configuration value is displayed as " + text, Status.PASS);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * createSimpleLoanImport is used to import the loan from EFS server to WFS application
	 * @param hashmap
	 * @throws Exception
	 */
	public void createSimpleLoanImport(HashMap<String, String> hashmap) throws Exception {
		try {
			setFlagsInSimpleLoanImportPage(hashmap);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, Next);
			CommonUtils.sleepForAWhile();
			
			/*try {
				if(confirmationDialog.isDisplayed()) {
					reportGenerator.logMessage("Confirmation dialog box is displayed", Status.PASS);
					if (SeleniumUtils.getValue(webDriver, confirmationMessage).contains(hashmap.get("Message"))) {
						reportGenerator.logMessage("Confirmation message ["+hashmap.get("Message")+"] is displayed", Status.PASS);
						SeleniumUtils.doClick(webDriver, Yes);
				}
				}
				}catch(Exception e) {
					try {
						if(confirmationDialog.isDisplayed() & confirmationDialog.isEnabled()) {
							reportGenerator.logMessage("Confirmation dialog box is displayed", Status.PASS);
							if (SeleniumUtils.getValue(webDriver, confirmationMessageAlreadyInIdea).contains("are already in IDEA.")) {
								String message=SeleniumUtils.getValue(webDriver, confirmationMessageAlreadyInIdea);
								reportGenerator.logMessage("Confirmation message ["+message+"] is displayed", Status.PASS);
								SeleniumUtils.doClick(webDriver, Yes);
								CommonUtils.sleepForAWhile();
						}
					}
					}catch(Exception e1) {
						Assert.fail("Confirmation dialogbox is not displayed");
					}
				} */
			
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ReviewLoanImport, "Review Loan Import"));
			
			String loanNum=loannumberInReviewPage.getText();
			reportGenerator.logMessage("the Loan Number ["+loanNum+"] is displayed in review loan import page", Status.PASS);
			if(hashmap.get("LoanNumber").equals(loanNum)) {
				reportGenerator.logMessage("As Expected, the Loan Number is displayed in review loan import page", Status.PASS);
			}else {
				Assert.fail("the Loan Number is not displayed");
			}
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, SaveInReviewLoanImport);
			CommonUtils.sleepForAWhile(10000);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, SingleLoanImportConfirmation, "Single Loan Import - Confirmation"));
			if (SeleniumUtils.getValue(webDriver, SuccessMessage).contains(hashmap.get("SuccessMessage"))) {
				reportGenerator.logMessage("message ["+hashmap.get("SuccessMessage")+"] is displayed", Status.PASS);				
		}else {
			Assert.fail("message is not displayed");
		}
			this.verifyLoanStateAndStatus(hashmap);
				
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * verifyLoanStateAndStatus method used to check the status of the loan
	 * @param hashmap
	 * @return
	 */
	public boolean verifyLoanStateAndStatus(HashMap<String, String> hashmap) {
		boolean isExpected = false;
		try {
			CommonUtils.sleepForAWhile();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.DASHBOARD, IdeaWFSConstants.SUB_INDEXING_DASHBOARD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, IndexingDashboardPage, "Indexing Dashboard Page"));
			reportGenerator.logMessage("Indexing Dashboard page is successfully displayed", Status.PASS);	
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, loanState, "All");
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
			SeleniumUtils.sendKeys(webDriver, searchTextBox,  hashmap.get("LoanNumber"));
			reportGenerator.logAndCaptureScreen("Loan Number is entered", "verifyLoanStateAndStatus",
					Status.PASS, webDriver,LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
			CommonUtils.sleepForAWhile(10000);
			String loanNumberFrmGrid ="//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]";
			String loanid=webDriver.findElement(By.xpath(loanNumberFrmGrid)).getText(); 
			System.out.println("loan id:" + loanid);
			Assert.assertEquals(loanid, hashmap.get("LoanNumber"));
			reportGenerator.logMessage("the loan number ["+loanid+"] is displayed in dashboard", Status.PASS);
			String status="//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/following::td[3]";
			String loanStatus=webDriver.findElement(By.xpath(status)).getText();
			WebElement loanNumberXpath = webDriver.findElement(By.xpath(status));
			reportGenerator.logAndCaptureScreen("the loan status ["+loanStatus+"] is displayed in dashboard","verifyLoanStateAndStatus",Status.PASS, webDriver,loanNumberXpath);
			isExpected = true;
			String BatchTypeEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/following::td[9]";
			String PriorityEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/following::td[1]";
			String BatchIDEle = "//table[@id='tblDashboardIndexing']/tbody/tr/td/a[contains(text(),'"+hashmap.get("LoanNumber")+"')]/preceding::td[1]";
			String sBatchType = webDriver.findElement(By.xpath(BatchTypeEle)).getText(); 
			String sPriority = webDriver.findElement(By.xpath(PriorityEle)).getText(); 
			String sBatchID = webDriver.findElement(By.xpath(BatchIDEle)).getText(); 
			hashmap.put("BatchType",sBatchType);
			hashmap.put("Priority",sPriority);
			hashmap.put("BatchID",sBatchID);
		} catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		return isExpected;
	}
	// Vendor changfe
	  
	 public void convertProblemLoanToVendorLoan(HashMap<String, String> hashmap) {
		 CommonUtils.sleepForAWhile();
		 SeleniumUtils.doClick(webDriver, Edit);
		 //CommonUtils.sleepForAWhile();
		/* SeleniumUtils.selectFromComboBox(webDriver, NewVendor, "Aklero");
		 SeleniumUtils.doClick(webDriver, saveVendor);
		 CommonUtils.sleepForAWhile();
		 
		 if(confirmationDialogbox.size()>0) {
				SeleniumUtils.doClick(webDriver, btnYes);
			}*/
			
			//Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, Edit, "Edit Data Element Workflow"));
			//Reporter.log("Edit Data Element Workflow Dialogbox is displayed");
			 CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, NewVendor,
					EnvironmentPropertyLoader.getPropertyByName("wfs.vendor.name.to.change.clearpath"));
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, saveVendor);
			CommonUtils.sleepForAWhile();

			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, indexLoanBatchStatus, "Edit LoanBatch Request Status"));
			Reporter.log("Edit LoanBatch Request Status Dialogbox is displayed");
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, statusOnQueue, "On Queue"));
			Reporter.log("On Queue is displayed");
			SeleniumUtils.doClick(webDriver, btnRefresh);
			CommonUtils.sleepForAWhile();

			//Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, finished, "Finished"));
			//Reporter.log("Finished is displayed");
			SeleniumUtils.doClick(webDriver, btnClose);
			CommonUtils.sleepForAWhile();
			
			
			SeleniumUtils.selectFromComboBox(webDriver, loanState, "All");
			SeleniumUtils.doClick(webDriver, LoanBatchSearchBtn);
			SeleniumUtils.doClick(webDriver, LoanNumberRadioBtn);
			SeleniumUtils.sendKeys(webDriver, searchTextBox,  hashmap.get("LoanNumber"));
			reportGenerator.logAndCaptureScreen("Loan Number is entered", "verifyLoanStateAndStatus",
					Status.PASS, webDriver,LoanBatchSearchDialogbox);
			SeleniumUtils.doClick(webDriver, batchSearchbtn);
			CommonUtils.sleepForAWhile(10000);
		} 
	
		 
		 
 
	
	/**
	 * navigateToSimpleLoanImportPage is used to navigate to SimpleLoanimport page
	 * @throws Exception
	 */
	public void navigateToSimpleLoanImportPage() throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.SIMPLE_LOAN_IMPORT);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, SimpleLoanImport, "Simple Loan Import"));
			reportGenerator.logMessage("Simple Loan Import clicked", Status.PASS);
		} catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		
	}
	
	/**
	 * selectImportedLoan is used to select the imported loan as per the input
	 * @throws Exception
	 */
	public void selectImportedLoan(HashMap<String, String> hashmap) throws Exception {
		try {
			this.navigateToSimpleLoanImportPage();
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.selectFromComboBox(webDriver, manglerNameDropdown, hashmap.get("ManglerName"));
			SeleniumUtils.sendKeys(webDriver, CreateInputDirectory_ClientInputbox, sClient);
			for(int index =0;index<clientsDropDownListedValues.size();index++) {
				if(clientsDropDownListedValues.get(index).getText().trim().equalsIgnoreCase(sClient)){
					clientsDropDownListedValues.get(index).click();
					break;
				}
			}
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, productIdDropdown, hashmap.get("ProductName"));
			SeleniumUtils.selectFromComboBox(webDriver, SimpleLoanImport_ProcessingCenter, hashmap.get("ProcessingCenter"));
			SeleniumUtils.doClick(webDriver, editWorkflowBtn);
			SeleniumUtils.doClick(webDriver, Confirmation);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, Yesbtn);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, workflowDropdown, hashmap.get("WorkflowName"));
			Select sel = new Select(workflowDropdown);
			String workflow = sel.getFirstSelectedOption().getText();
			if(workflow.equals(hashmap.get("WorkflowName"))) {
				reportGenerator.logMessage("As expected["+hashmap.get("WorkflowName")+"] is selected in workflow dropdown", Status.PASS);
			}else {
				Assert.fail("OnePassAutomatedDE is not selected in workflow dropdown");
			}

			SeleniumUtils.doClick(webDriver, showAllLoans);
			CommonUtils.sleepForAWhile();
			int valueIndex=0;
			for(int index=0;index<sampleLoanImport_LoanNumbers.size();index++) {
				if(sampleLoanImport_LoanNumbers.get(index).getText().trim().equalsIgnoreCase(hashmap.get("LoanNumber"))){
					valueIndex = index+1;
					String sLoanNumberCheckbox = "//table[@id='loanFileTable']/tbody/tr["+valueIndex+"]/td[2]/preceding-sibling::td/input[1]";
					webDriver.findElement(By.xpath(sLoanNumberCheckbox)).click();
					break;
				}
			}
			String actualLoanNumberPath	= "//table[@id='loanFileTable']/tbody/tr["+valueIndex+"]/td[2]";
			
			Assert.assertEquals(webDriver.findElement(By.xpath(actualLoanNumberPath)).getText().trim(), hashmap.get("LoanNumber"));	
			reportGenerator.logMessage("the Loan Number ["+hashmap.get("LoanNumber")+"] is displayed in simple loan import page", Status.PASS);
		} catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		
	}
	/**
	 * navigateToSimpleLoanImportPage is used to navigate to SimpleLoanimport page
	 * @throws Exception
	 */
	public void setFlagsInSimpleLoanImportPage(HashMap<String, String> hashmap) throws Exception {
		try {
			this.selectImportedLoan(hashmap);
			SeleniumUtils.doClick(webDriver, btnSetFlag);
			if(setFlagDialogbox.isDisplayed()) {
				reportGenerator.logMessage("As expected SetFlag dialog box is displayed", Status.PASS);
				SeleniumUtils.doClick(webDriver, diyCheckbox);
				reportGenerator.logMessage("DIY checkbox is selected in SetFlag dialog box", Status.PASS);
				CommonUtils.sleepForAWhile();
				SeleniumUtils.doClick(webDriver, rushAllCheckbox);
				reportGenerator.logMessage("Rush All checkboxes are selected in SetFlag dialog box", Status.PASS);
				CommonUtils.sleepForAWhile();
				boolean rushDocOps=rushDocOpsCheckbox.isSelected();
				reportGenerator.logMessage("Checking Rush Doc Ops boolean status:"+rushDocOps,Status.PASS);
				if(rushDocOps) {
					reportGenerator.logMessage("Rush Doc Ops checkbox is selected already in SetFlag dialog box", Status.PASS);
				}else {
					SeleniumUtils.doClick(webDriver,rushDocOpsCheckbox);
					reportGenerator.logMessage("Rush Doc Ops checkbox is selected in SetFlag dialog box", Status.PASS);
				}
				boolean runderwritings=rushUnderwritingCheckbox.isSelected();
				reportGenerator.logMessage("Checking Rush Underwriting checkbox boolean status:"+runderwritings,Status.PASS);
				if(runderwritings) {
					reportGenerator.logMessage("Rush Underwriting checkbox is selected already in SetFlag dialog box", Status.PASS);
				}else {
					SeleniumUtils.doClick(webDriver,rushUnderwritingCheckbox);
					reportGenerator.logMessage("Rush Underwriting checkbox is selected in SetFlag dialog box", Status.PASS);
				}
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("All flags were checked correctly","setFlagFunctionalityInSimpleLoanImportPage",Status.PASS, webDriver);
				
				SeleniumUtils.doClick(webDriver, btnSaveFlag);
			}else {
				Assert.fail("setflag dialogbox is not displayed.");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
		
	}

	
	public void transferAndImportLoanTOWFS(ITestContext iTestContext, HashMap<String, String> hashmap,ConnectToLinuxBox connectToLinuxBox) {
		try {
		this.transferLoanToWFS(iTestContext, hashmap, connectToLinuxBox);
		this.createSimpleLoanImport(hashmap); 
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		}
	public void transferLoanToWFS(ITestContext iTestContext, HashMap<String, String> hashmap,
			ConnectToLinuxBox connectToLinuxBox) {
		try {
			String sLoanPdfPath = connectToLinuxBox.copyAndRenameFile(hashmap, reportGenerator.getReportPath());
			String sRemoteDir = IdeaWFSConstants.MANGLAR_BASE_OUTPUT_DIR + hashmap.get("ClientID") + "."
					+ hashmap.get("ProductID") + "_" + hashmap.get("ClientName") + "." + hashmap.get("ProductName");
			connectToLinuxBox.transferLoanInToEFS(EnvironmentPropertyLoader.getPropertyByName("wfs_server_host"),
					EnvironmentPropertyLoader.getPropertyByName("user"), sRemoteDir,
					EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), sLoanPdfPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void modifyAndUpdateMultipleConfigValues(HashMap<String, String> hashmap)throws Exception{
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.CHAGE_CINFIG_PROPERIES);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ChangeConfigurationPropertiesPage, "Change Configuration Properties"));
			reportGenerator.logMessage("Change Configuration Properties is successfully clicked", Status.PASS);
			int count = 0;
			for(int i=0;i<Integer.parseInt(hashmap.get("NoofPropertyKeys"));i++) {
				count = i+1;
				String value = hashmap.get("PropertyKey"+(String.valueOf(count)));
			SeleniumUtils.selectFromComboBox(webDriver, PropertyIdDropdown, value);
			SeleniumUtils.doClick(webDriver, PropertyValue);
			String sNewValue = hashmap.get("PropertyValue");
				SeleniumUtils.sendKeys(webDriver, PropertyValue,sNewValue);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("Configuration value is displayed as " + hashmap.get("PropertyValue"), "modifyAndUpdateConfig" , Status.PASS,webDriver);
			SeleniumUtils.doClick(webDriver, save);
			}
			String text=SeleniumUtils.getValue(webDriver, Properties);
			reportGenerator.logMessage("Configuration value is displayed as " + text, Status.PASS);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	public boolean checkConfigProperty(HashMap<String, String> hashmap)throws Exception{
		boolean status = false;
		try {
			webDriver.get(EnvironmentPropertyLoader.getPropertyByName("idea_wfs_ibridge_url"));
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ChangeConfigurationPropertiesPage, "Change Configuration Properties"));
			reportGenerator.logMessage("Change Configuration Properties is successfully displayed", Status.PASS);
			String value = hashmap.get("PropertyKey");
			SeleniumUtils.selectFromComboBox(webDriver, PropertyIdDropdown, value);
			SeleniumUtils.doClick(webDriver, PropertyValue);
			String onePassDEWorkflowProcessIDs = PropertyValue.getAttribute("value");
			String expectedValue = hashmap.get("PropertyValue");
			Assert.assertEquals(onePassDEWorkflowProcessIDs, expectedValue);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("Configuration value is displayed as " + hashmap.get("PropertyValue"), "checkConfigProperty" , Status.PASS,webDriver);
			SeleniumUtils.doClick(webDriver, save);
			status = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return status;
	}


	
	
}
