package com.ll.idea.wfs.ui.page;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaEFSConstants;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.SeleniumUtils;
import net.lingala.zip4j.core.ZipFile;

public class WFSToolsMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSIndexingDashboardMaintenancePage indexingDashboardMaintenancePage = null;
	WFSMaintenancePage MaintenancePage = null;
	
	
	@FindBy(xpath="//input[@id='splitByCompany']" )
	public List<WebElement> splitByCompanyInAI;
	
	@FindBy(xpath="//td[contains(text(),'PennyMac Custom - 43')]")
	public List<WebElement> productPennyMac;
	
	@FindBy(xpath="(//input[@value='New Loan Import Page Allocation'])[1]")
	public WebElement btnLoanImport;
	
	@FindBy(xpath="//span[contains(text(),'Add New Loan Import Page Allocation')]")
	public WebElement addNewLoanAllocationPopup;
	
	@FindBy(xpath="//input[@id='editProduct']")
	public WebElement editProductInAddNewLoanAllocationPopup;
	
	@FindBy(xpath="//input[@id='maxPageCount']")
	public WebElement maxPageCount;
	
	@FindBy(xpath="//input[@id='saveButton']")
	public WebElement saveButton;
	
	@FindBy(xpath="//span[contains(text(),'Added new loan import page allocation')]")
	public WebElement successfullMessage;
	
	@FindBy(xpath="//b[contains(text(),'Manage Loan Import Page Allocation')]")
	public WebElement manageLoanImport;
	
	@FindBy(xpath="//select[@id='priority-0-1']")
	public WebElement priorityOne;
	
	@FindBy(xpath="//select[@id='priority-0-2']")
	public WebElement priorityTwo;
	
	@FindBy(xpath="//select[@id='priority-0-3']")
	public WebElement priorityThree;
	
	@FindBy(xpath="//select[@id='priority-0-4']")
	public WebElement priorityFour;
	
	@FindBy(xpath="//select[@id='processingCenterId-0-3']")
	public WebElement processingCenterThree;
	
	@FindBy(xpath="//select[@id='processingCenterId-0-4']")
	public WebElement processingCenterFour;
	
	@FindBy(xpath="//select[@id='workflowLookupId-0-3']" )
	public WebElement workFlowThree;
	
	@FindBy(xpath="//select[@id='workflowLookupId-0-4']" )
	public WebElement workFlowFour;
	
	@FindBy(xpath="//input[contains(@id,'autoImportPeriodGroupBeans0.autoImportAllocationBeans2.percentage')]")
	public WebElement autoImportPercentageThree;
	
	@FindBy(xpath="//input[contains(@id,'autoImportPeriodGroupBeans0.autoImportAllocationBeans3.percentage')]")
	public WebElement autoImportPercentageFour;
	
	@FindBy(xpath="//input[@value='Download']")
	public WebElement downloadButton;
	
	@FindBy(xpath="//input[@value='Refresh']")
	public WebElement refreshButton;
	
	@FindBy(xpath="//select[@id='logFile']")
	public WebElement logFileDropdown;
	
	@FindBy(xpath="(//div[@class='dataTables_info'])[2]")
	public WebElement showingRecords;
	
	@FindBy(xpath="//a[contains(text(),'Id')]")
	public WebElement reviewId;
	
	@FindBy(xpath="//input[@id='chkEditAll']")
	public WebElement chkEditAll;
	
	@FindBy(xpath="//input[@id='txtNoOfRows']")
	public WebElement txtNoOfRows;
	
	@FindBy(xpath="//input[@id='btnOkSelectRowsEdit']")
	public WebElement btnOkSelectRowsEdit;
	
	@FindBy(xpath="//a[@id='editAll']")
	public WebElement btnEditAll;
	
	@FindBy(xpath="//input[@id='btnShow']")
	public WebElement btnShow;
	
	@FindBy(xpath="//table[@id='tblForDescriptorToReview']/tbody/tr/td[1]")
	public WebElement descriptorId;

	@FindBy(xpath="//p[contains(text(),'Delete data of selected successfully')]")
	public WebElement deleteConfirmationPopup;
	
	@FindBy(xpath="//*[@id='tblForDescriptorToReview']/tbody/tr/td[10]/input")
	public WebElement editCheckbox;
	
	@FindBy(xpath="//a[@id='deleteAll']")
	public WebElement btnDeleteAll;
	
	@FindBy(xpath="//span[contains(text(),'Confirmation')]")
	public WebElement confirmationDialogbox;
	
	@FindBy(xpath="//p[contains(text(),'Are you sure you want to delete?')]")
	public WebElement confirmationText;
	
	@FindBy(xpath="//button[contains(text(),'Yes')]")
	public WebElement confirmationBtnYes;
	
	@FindBy(xpath="//b[contains(text(),'Force Descriptor to Review')]")
	public WebElement forceDescriptorReview;
	
	@FindBy(xpath="//input[@id='txtClient']")
	public WebElement txtClientInforceDescriptorReview;
	
	@FindBy(xpath="//input[@id='btnAddDER']")
	public WebElement btnAddDER;
	
	@FindBy(xpath="//span[contains(text(),'Add Force Descriptor To Review')]")
	public WebElement discriptor;
	
	@FindBy(xpath="//*[@id='tblNonConflictDERs']/tbody/tr[2]/td/input[@class='illegibleInput']")
	public WebElement illegibleCheckbox;
	
	@FindBy(xpath="//*[@id='tblNonConflictDERs']/tbody/tr[2]/td/input[@class='missingInput']")
	public WebElement missingCheckbox;
	
	@FindBy(xpath="//input[@id='btnSaveAddedDER']")
	public WebElement btnSaveAddedDER;
	
	@FindBy(xpath="(//a[contains(@id,'editDescriptorToReviewBean_')])[1]")
	public WebElement btnEditDescriptor;
	
	@FindBy(xpath="//span[contains(text(),'Edit Data Element')]")
	public WebElement editDataPopup;
	
	@FindBy(xpath="//select[@id='illegible']")
	public WebElement illegibleDropdown;
	
	@FindBy(xpath="//select[@id='missing']")
	public WebElement missingDropdown;
	
	@FindBy(xpath="//select[@id='badValue']")
	public WebElement badValueDropdown;
	
	@FindBy(xpath="//select[@id='notApplicable']")
	public WebElement notApplicableDropdown;
	
	@FindBy(xpath="//input[@id='saveEditeditForceDescriptorToReviewButton']")
	public WebElement btnSaveEdited;
	
	@FindBy(xpath="//span[contains(text(),'Info')]")
	public WebElement infoPopup;
	
	@FindBy(xpath="//p[contains(text(),'Updated data of selected successful')]" )
	public WebElement infoTextPopup;
	
	@FindBy(xpath="//button[contains(text(),'Ok')]" )
	public WebElement btnOkInfo;
	
	@FindBy(xpath="//select[@id='listProductId']")
	public WebElement listProductId;
	
	@FindBy(xpath="//select[@id='listDocumentId']")
	public WebElement listDocumentId;
	
	@FindBy(xpath="(//span[contains(@class,'ui-icon-triangle-1-s')])[1]")
	public WebElement discriptorDropdown;
	
	@FindBy(xpath="//div[contains(@class,'ui-multiselect-menu')]/div/ul/li/following::label/span")
	public List<WebElement> discriptorMenuOptions;
	
	@FindBy(xpath = "//b[contains(text(),'View Log')]")
	public WebElement viewLog;
	
	@FindBy(xpath = "//input[@id='logTimeSpentInfo']")
	public WebElement logTimeSpentInfo;
	
	@FindBy(xpath = "//*[@id='txtLogContent']")
	public WebElement txtLogContent;
	
	@FindBy(xpath = "//input[@Value='Refresh']")
	public WebElement btnRefresh;
	
	@FindBy(xpath = "//select[@id='listManglerId']")
	public WebElement listManglerIdProfile;
	
	@FindBy(xpath="//b[contains(text(),'Manage Auto-Scheduled Import Profile - Company')]")
	public WebElement manageAutoScheduledImportProfileCompany;
	
	@FindBy(xpath="//*[contains(text(),'Currently Selected Profile has been saved')]")
	public WebElement selectedProfileHasbeenSaved;
	
	@FindBy(xpath="//b[contains(text(),'Manage Auto-Scheduled Import Profile')]")
	public WebElement manageAutoScheduledImportProfile;
	
	@FindBy(xpath="//input[@id='txtClient']")
	public WebElement txtClient;
	
	@FindBy(xpath="//ul/li[@class='ui-menu-item']/a")
	public List<WebElement> qClientsDropdownList;
	
	@FindBy(xpath="//select[@id='listImportProfileId']")
	public WebElement listImportProfileId;
	
	@FindBy(xpath="//input[@id='isActive']")
	public WebElement isActive;
	
	@FindBy(xpath="//select[@id='listProductId']")
	public WebElement productId;
	
	@FindBy(xpath="//input[@id='isShowAllLoans']")
	public WebElement isShowAllLoans;
	
	@FindBy(xpath="//input[@id='ignoreDuplicateLoan']")
	public WebElement ignoreDuplicateLoan;
	
	@FindBy(xpath="//select[@id='processingCenterId-0-1']")
	public WebElement processingCenterId;
	
	@FindBy(xpath="//select[@id='processingCenterId-0-2']")
	public WebElement processingCenter;
	
	@FindBy(xpath="//select[@id='workflowLookupId-0-1']")
	public WebElement workflowLookupId;
	
	@FindBy(xpath="//select[@id='workflowLookupId-0-2']")
	public WebElement workflowLookupIdtwo;
	
	@FindBy(xpath="//input[contains(@id,'autoImportPeriodGroupBeans0.autoImportAllocationBeans0.percentage')]")
	public WebElement autoImportPercentage;
	
	@FindBy(xpath="//input[contains(@id,'autoImportPeriodGroupBeans0.autoImportAllocationBeans1.percentage')]")
	public WebElement autoImportPercentageOne;
	
	@FindBy(xpath="//input[@value='Save']")
	public WebElement btnSave;
	
	@FindBy(xpath="//label[contains(text(),'Show only clients with profiles')]")
	public WebElement clientProfile;
	
	@FindBy(xpath = "//b[contains(text(),'Scheduler & Listener Summary')]")
	public WebElement SchedulerAndListenerSummary;
	
	@FindBy(xpath = "(//td[contains(text(),'Stop')])[1]")
	public WebElement StopButton;	
	
	@FindBy(xpath = "(//td[contains(text(),'Stop')]/../td[1]/a[contains(@href,'scheduler')])[1]")
	public WebElement SchedulerNameInStopStatus;
	
	@FindBy(xpath = "//Input[@value='Start Scheduler']")
	public WebElement startSchedulerbtn;
	
	@FindBy(xpath = "//span[contains(text(),'Scheduler Info')]")
	public WebElement schedulerInfoPopup;
	
	@FindBy(xpath = "//input[@onclick='_doStartScheduler()']")
	public WebElement startSchedulerOkBtn;
	
	@FindBy(xpath = "//img[contains(@src,'icon_green')]")
	public WebElement greenClrStatus;
	
	@FindBy(xpath = "(//td[contains(text(),'Stop')]/../td[1]/a[contains(@href,'listeners')])[1]")
	public WebElement listenerNameInStopStatus;
	
	@FindBy(xpath = "//input[@value = 'Start Selected']")
	public WebElement startSelectedBtn;
	
	@FindBy(xpath = "//input[@value = 'Stop Selected']")
	public WebElement stopSelectedBtn;
	
	@FindBy(xpath = "//input[@id='txtCompany']")
	public WebElement companyTextField;
	
	@FindBy(xpath="//input[@id='diy']")
	public WebElement diyCheckbox;
	
	@FindBy(xpath="(//input[@value='Save'])[1]")
	public WebElement saveBtn;		
	
	@FindBy(xpath="//span[contains(text(),'Confirmation')]")
	public WebElement confirmationMsgPopUp;
	
	@FindBy(xpath="//div[@id='dialog-confirm']/p[2]")
	public WebElement confirmationMsg;
	
	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement confirmationPopupYesBtn;
	
	@FindBy(xpath = "//input[@id='chkSelectAll']")
	public WebElement selectAllChkbox;
	
	@FindBy(xpath = "//span[contains(text(),'Existing Client Profiles')]")
	public WebElement existingClientProfiles;
	
	@FindBy(xpath = "(//input[@value='Save'])[2]")
	public WebElement existingClientProfilesSavBtn;	
	
	@FindBy(xpath = "//font[@color='green']")
	public WebElement successMessage;
	
	@FindBy(xpath="(//div[contains(@class,'timepicker-dropdown-custom')])[1]/ul/li[2]")
	public WebElement startTimeSelect;
	
	@FindBy(xpath="(//div[contains(@class,'timepicker-dropdown-custom')])[2]/ul/li[2]")
	public WebElement stopTimeSelect;
	
	@FindBy(xpath = "(//span[contains(@class,'ui-icon-triangle-1-s')])[4]" )
	public WebElement ClientDropdown;
	
	@FindBy(xpath="//select[@id='profileIgnoreDuplicateLoan']")
	public WebElement profileIgnoreDuplicateLoan;
	
	@FindBy(xpath="//select[@id='profileShowAllLoans']")
	public WebElement profileShowAllLoans;
	
	@FindBy(xpath="//b[contains(text(),'Client Profiles - Batch Update')]")
	public WebElement clientProfileBatchUpdate;
	
	@FindBy(xpath="//input[@id='autoImportPeriodGroupBeans0.startTime']")
	public WebElement autoImportStartTime;
	
	@FindBy(xpath="//input[@id='autoImportPeriodGroupBeans0.stopTime']")
	public WebElement autoImportStopTime;
	
	@FindBy(xpath="//b[contains(text(),'Auto Import Profile - Batch Update')]")
	public WebElement autoImportProfileBatchUpdate;
	
	@FindBy(xpath="//input[@id='btnProceed']")
	public WebElement btnProceed;
	
	@FindBy(xpath="//select[@id='profilePriority']")
	public WebElement profilePriority;
	
	@FindBy(xpath="//input[@id='splitByCompany']")
	public List<WebElement> splitByCompany;
	
	public WFSToolsMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
		MaintenancePage = new WFSMaintenancePage(this.webDriver, reportGenerator);
	}

	
	
	/**
	 * This method is used to verify scheduler management fucntionality.
	 * @param hashmap
	 * @throws Exception
	 */
	public void verifySchedulerManagement(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.SCHEDULER_LISTERNER_SUMMARY);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, SchedulerAndListenerSummary, "Scheduler And ListenerSummary Page"));
			reportGenerator.logMessage("Scheduler & Listener Summary is successfully displayed", Status.PASS);
			String sSchedulername = SchedulerNameInStopStatus.getText();
			SeleniumUtils.doClick(webDriver, SchedulerNameInStopStatus);
			String sPageTitleXpath = "//b[contains(text(),'Manage "+sSchedulername+"')]";
			String sPageTitle = webDriver.findElement(By.xpath(sPageTitleXpath)).getText();
			Assert.assertEquals(sPageTitle, "Manage "+sSchedulername);
			reportGenerator.logMessage("Manage "+sSchedulername+" Page is successfully displayed", Status.PASS);
			SeleniumUtils.doClick(webDriver, startSchedulerbtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, schedulerInfoPopup, "SchedulerPopupInfoPoppup"));
			SeleniumUtils.doClick(webDriver, startSchedulerOkBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, greenClrStatus, "Status"));
			reportGenerator.logAndCaptureScreen("The Status of the scheduler is changed to Green",
					"verifySchedulerManagement", Status.PASS, webDriver);
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.SCHEDULER_LISTERNER_SUMMARY);
			CommonUtils.sleepForAWhile(2000);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, SchedulerAndListenerSummary, "Scheduler And ListenerSummary Page"));
			reportGenerator.logAndCaptureScreen("The Status of the scheduler is changed to Green",
					"verifySchedulerManagement", Status.PASS, webDriver);
			String sStopXpath = "//a[contains(text(),'"+sSchedulername+"')]/../../td/a[contains(text(),'Stop')]";
			WebElement stopLinkEle = webDriver.findElement(By.xpath(sStopXpath));
			SeleniumUtils.doClick(webDriver, stopLinkEle);
			String schedulerStatus = "//a[contains(text(),'"+sSchedulername+"')]/../../td/img[contains(@src,'icon_red')]";
			WebElement schedulerStatusEle = webDriver.findElement(By.xpath(schedulerStatus));
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, schedulerStatusEle, "Scheduler And ListenerSummary Page"));
			reportGenerator.logAndCaptureScreen("The Scheduler is stopped and status changed to Red",
					"verifySchedulerManagement", Status.PASS, webDriver);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * This method is used to verify Listener management fucntionality.
	 * @param hashmap
	 * @throws Exception
	 */
	public void verifyListenerManagement(HashMap<String, String> hashmap)  {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.SCHEDULER_LISTERNER_SUMMARY);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, SchedulerAndListenerSummary, "Scheduler And ListenerSummary Page"));
			reportGenerator.logMessage("Scheduler & Listener Summary is successfully displayed", Status.PASS);
			String sListenername = listenerNameInStopStatus.getText();
			String sListenerCheckBox = "//a[contains(text(),'"+sListenername+"')]/../../td/input";
			WebElement sListenerCheckBoxEle = webDriver.findElement(By.xpath(sListenerCheckBox));
			SeleniumUtils.doClick(webDriver, sListenerCheckBoxEle);
			reportGenerator.logAndCaptureScreen("Listener which is in stop status is selected",
					"verifyListenerManagement", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, startSelectedBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logMessage("Start Selected button is clicked successfully", Status.PASS);
			String sStatus = "//a[contains(text(),'"+sListenername+"')]/../../td[contains(text(),'Running')]";
			WebElement statusOfListener = webDriver.findElement(By.xpath(sStatus));
			Assert.assertEquals(statusOfListener.getText().trim(), hashmap.get("Status"));
			String eleXpath = "//a[contains(text(),'"+sListenername+"')]";
			WebElement element = webDriver.findElement(By.xpath(eleXpath));
			reportGenerator.logAndCaptureScreen("Selected listener is changed to green color and status changed to Running",
					"verifyListenerManagement", Status.PASS, webDriver,element);
			WebElement sRunningListenerEle = webDriver.findElement(By.xpath(sListenerCheckBox));
			SeleniumUtils.doClick(webDriver, sRunningListenerEle);
			reportGenerator.logAndCaptureScreen("Listener which is in Running status is selected",
					"verifyListenerManagement", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, stopSelectedBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logMessage("Stop Selected button is clicked successfully", Status.PASS);
			String sStopStatus = "//a[contains(text(),'"+sListenername+"')]/../../td[contains(text(),'Stop')]";
			WebElement sStopStatusEle = webDriver.findElement(By.xpath(sStopStatus));
			Assert.assertEquals(sStopStatusEle.getText().trim(), hashmap.get("FinalStatus"));
			reportGenerator.logAndCaptureScreen("Selected listener is changed to red color and status changed to Stop",
					"verifyListenerManagement", Status.PASS, webDriver,sStopStatusEle);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * create AutoScheduled Import Profile is used to create the AutoScheduled Import Profile.
	 * @param hashmap
	 * @throws Exception
	 */
	public void createAutoScheduledImportProfile(HashMap<String, String> hashmap) {
		try {
			
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfile, "Manage Auto-Scheduled Import Profile"));
			reportGenerator.logMessage("Manage Auto-Scheduled Import Profile clicked", Status.PASS);
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
			for(int index =0;index<qClientsDropdownList.size();index++) {
				if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)){
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			
			SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("Profile"));
			boolean activeCheckbox=isActive.isSelected();
			reportGenerator.logMessage("Checking isActive boolean status:"+activeCheckbox,Status.PASS);
			if(activeCheckbox) {
				reportGenerator.logMessage("isActive checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,isActive);
				reportGenerator.logMessage("isActive checkbox is selected ", Status.PASS);
			}		
			SeleniumUtils.selectFromComboBox(webDriver, productId, hashmap.get("ProductName"));
			boolean showAllLoans = isShowAllLoans.isSelected();
			reportGenerator.logMessage("Checking isImportAllLoans boolean status:"+showAllLoans,Status.PASS);
			if(showAllLoans) {
				reportGenerator.logMessage("isImportAllLoans checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,isShowAllLoans);
				reportGenerator.logMessage("isImportAllLoans checkbox is selected ", Status.PASS);
			}
			boolean duplicateLoan = ignoreDuplicateLoan.isSelected();
			reportGenerator.logMessage("Checking ignoreDuplicateLoan boolean status:"+duplicateLoan,Status.PASS);
			if(duplicateLoan) {
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,ignoreDuplicateLoan);
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected ", Status.PASS);
			}
			if(splitByCompanyInAI.size()>0) {
				reportGenerator.logMessage("splitByCompanyCheckbox is not displayed ", Status.PASS);	
			}else {
				reportGenerator.logMessage("splitByCompanyCheckbox is displayed ", Status.PASS);
			}
			if(hashmap.get("TestCaseName").contains("Company")) {
				CommonUtils.sleepForAWhile();
			boolean splitByCompanyCheckbox = splitByCompany.get(0).isSelected();
			if(splitByCompanyCheckbox) {
				reportGenerator.logMessage("splitByCompanyCheckbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,splitByCompany.get(0));
				reportGenerator.logMessage("splitByCompanyCheckbox checkbox is selected ", Status.PASS);
			}
			}
			if(hashmap.get("TestCaseName").contains("duplicate loans")) {
				boolean duplicateLoans = ignoreDuplicateLoan.isSelected();
				reportGenerator.logMessage("Checking ignoreDuplicateLoan boolean status:"+duplicateLoan,Status.PASS);
				if(duplicateLoans) {
					//reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected already ", Status.PASS);
					SeleniumUtils.doClick(webDriver,ignoreDuplicateLoan);
					reportGenerator.logMessage("ignoreDuplicateLoan checkbox is unselected ", Status.PASS);
				}else {
					//SeleniumUtils.doClick(webDriver,ignoreDuplicateLoan);
					reportGenerator.logMessage("ignoreDuplicateLoan checkbox is unselected alredy ", Status.PASS);
				}
				
			}
			this.setDefaultDetailsToWorkFlowsInAutoScheduledImportProfile();
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("CoPWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("OnePassDePercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityOne, IdeaWFSConstants.PRIORITY);
			if(hashmap.get("CPWorkflowName")!="") {
			SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("CPWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("StandedWithEirPercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, IdeaWFSConstants.PRIORITY);
			}
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("All the details were filled successfully for porfile creation",
					"verfiyAutoScheduledImportProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			SeleniumUtils.doClick(webDriver, btnSave);
			CommonUtils.sleepForAWhile(4000);
			reportGenerator.logAndCaptureScreen("profile created Successfully",
					"verfiyAutoScheduledImportProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, selectedProfileHasbeenSaved, hashmap.get("ActualMessage")));
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}
	}
	
	
	/**
	 * create Company Profile is used to create Company Profile.
	 * @param hashmap
	 * @throws Exception
	 */
	public void createCompanyProfile(HashMap<String, String> hashmap) throws Exception {
		
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE_COMPANY);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfileCompany, "Manage Auto-Scheduled Import Profile - Company"));
			reportGenerator.logMessage("Manage Auto-Scheduled Import Profile - Company clicked", Status.PASS);
			
			SeleniumUtils.sendKeys(webDriver, companyTextField, hashmap.get("CompanyName"));
			for(int index =0;index<qClientsDropdownList.size();index++) {
				if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(hashmap.get("CompanyName"))){
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			
			SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("CompanyProfile"));
			boolean activeCheckbox=isActive.isSelected();
			reportGenerator.logMessage("Checking isActive boolean status:"+activeCheckbox,Status.PASS);
			if(activeCheckbox) {
				reportGenerator.logMessage("isActive checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,isActive);
				reportGenerator.logMessage("isActive checkbox is selected ", Status.PASS);
			}
			SeleniumUtils.selectFromComboBox(webDriver, productId, hashmap.get("CompanyProductName"));
			boolean showAllLoans = isShowAllLoans.isSelected();
			reportGenerator.logMessage("Checking isImportAllLoans boolean status:"+showAllLoans,Status.PASS);
			if(showAllLoans) {
				reportGenerator.logMessage("isImportAllLoans checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,isShowAllLoans);
				reportGenerator.logMessage("isImportAllLoans checkbox is selected ", Status.PASS);
			}
			boolean duplicateLoan = ignoreDuplicateLoan.isSelected();
			reportGenerator.logMessage("Checking ignoreDuplicateLoan boolean status:"+duplicateLoan,Status.PASS);
			if(duplicateLoan) {
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,ignoreDuplicateLoan);
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected ", Status.PASS);
			}
			
			boolean diyCompanyCheckbox = diyCheckbox.isSelected();
			
			reportGenerator.logMessage("Checking DIY checkbox boolean status:"+diyCompanyCheckbox,Status.PASS);
			if(diyCompanyCheckbox) {
				reportGenerator.logMessage("DIY checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,diyCheckbox);
				reportGenerator.logMessage("DIY checkbox is selected ", Status.PASS);
			}
			this.setDefaultDetailsToWorkFlowsInAutoScheduledImportProfile();
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("CompanyProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("CoPWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("OnePassDePercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityOne, IdeaWFSConstants.PRIORITY);
			SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("CompanyProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("CPWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("StandedWithEirPercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, IdeaWFSConstants.PRIORITY);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("All the details were filled successfully for porfile creation",
					"createCompanyProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			SeleniumUtils.doClick(webDriver, saveBtn);
			CommonUtils.sleepForAWhile();
			if(SeleniumUtils.isDisplayed(webDriver, confirmationMsgPopUp, "Confirmation Popup")) {
				Assert.assertEquals(confirmationMsg.getText().trim(), hashmap.get("ConfirmationPopUpMsg"));
				reportGenerator.logAndCaptureScreen("Confirmation msg is displayed successfully",
						"createCompanyProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
				SeleniumUtils.doClick(webDriver, confirmationPopupYesBtn);
			}
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, existingClientProfiles, "Existing Client Profiles Popup"));
			reportGenerator.logMessage("Existing Client Profiles pop up is displayed", Status.PASS);
			SeleniumUtils.doClick(webDriver, selectAllChkbox);
			SeleniumUtils.doClick(webDriver, existingClientProfilesSavBtn);
			
			reportGenerator.logAndCaptureScreen("profile created Successfully",
					"verfiyAutoScheduledImportProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, successMessage, hashmap.get("ActualMessage")));
			
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}	
		
	}
	
	/**
	 * This method is used to validate latest log time
	 * @param hashmap
	 * @throws Exception
	 */
	public void selectEnableAdvancedLogging(HashMap<String, String> hashmap) throws Exception {
		
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.UTILITIES,
					IdeaWFSConstants.VIEW_LOG);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, viewLog, "View Log Page"));
			reportGenerator.logMessage("View Log is successfully clicked", Status.PASS);
			
			String txtLog=txtLogContent.getText();
			
			CommonUtils.sleepForAWhile();
			
			reportGenerator.logAndCaptureScreen("before selecting Enable Advanced Logging text log content",
					"selectEnableAdvancedLogging", Status.PASS, webDriver,txtLogContent);
			
			boolean logTimeSpentDetails=logTimeSpentInfo.isSelected();
			if(logTimeSpentDetails) {
				reportGenerator.logAndCaptureScreen("Enable Advanced Logging checkbox is selected already ",
						"selectEnableAdvancedLogging", Status.PASS, webDriver,logTimeSpentInfo);
			}else {
				SeleniumUtils.doClick(webDriver,logTimeSpentInfo);
				reportGenerator.logAndCaptureScreen("Enable Advanced Logging checkbox is selected already ",
						"selectEnableAdvancedLogging", Status.PASS, webDriver,logTimeSpentInfo);
			}
			
			String logContent = txtLogContent.getText();
			
			if(txtLog.equals(logContent)) {
				Assert.fail("latest logged time is not displayed");
			}else {
				reportGenerator.logAndCaptureScreen("latest logged time is displayed",
						"selectEnableAdvancedLogging", Status.PASS, webDriver,txtLogContent);
			}
			CommonUtils.sleepForAWhile();
			
			SeleniumUtils.doClick(webDriver, btnRefresh);
			
			String refreshLogContent = txtLogContent.getText();
			
			if(logContent.equals(refreshLogContent)) {
				Assert.fail("latest logged time is not displayed");
			}else {
				reportGenerator.logAndCaptureScreen("latest logged time is displayed after clicking refresh",
						"selectEnableAdvancedLogging", Status.PASS, webDriver,txtLogContent);
			}
			
			boolean logTimeSpent=logTimeSpentInfo.isSelected();
			if(logTimeSpent) {
				SeleniumUtils.doClick(webDriver,logTimeSpentInfo);
				reportGenerator.logMessage("Enable Advanced Logging checkbox is unchecked ", Status.PASS);	
			}else {
				reportGenerator.logMessage("Enable Advanced Logging checkbox is unchecked already ", Status.PASS);
			}
			reportGenerator.logAndCaptureScreen("latest logged time is displayed after unchecking checkbox",
					"selectEnableAdvancedLogging", Status.PASS, webDriver,txtLogContent);
			
			CommonUtils.sleepForAWhile();
			
			SeleniumUtils.doClick(webDriver, btnRefresh);
			
			String refreshLogContentDetails = txtLogContent.getText();
			
			if(logContent.equals(refreshLogContentDetails)) {
				Assert.fail("latest logged time is not displayed");
			}else {
				reportGenerator.logAndCaptureScreen("latest logged time is displayed after clicking refresh",
						"selectEnableAdvancedLogging", Status.PASS, webDriver,txtLogContent);
			}
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
		Assert.fail(e.getMessage());
		}		
	}
	public void executeCommandForAutoImport(HashMap<String, String> hashmap, ConnectToLinuxBox connectToLinuxBox) {
			try {
				connectToLinuxBox.executeCommand(hashmap,
						EnvironmentPropertyLoader.getPropertyByName("autoimporter_host"),
						EnvironmentPropertyLoader.getPropertyByName("user"),
						IdeaEFSConstants.CMD_TO_RESTART_AUTOIMPORTOR,
						EnvironmentPropertyLoader.getPropertyByName("privatekeypath"));
				CommonUtils.sleepForAWhile(35000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	/**
	 * @param hashmap
	 * @param DescriptorName
	 * @throws Exception
	 */
	public void createDiscriptorInForceDescriptorToReviewPage(HashMap<String, String> hashmap, String DescriptorName)throws Exception{
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS,IdeaWFSConstants.FORCE_DESCRIPTOR_TO_REVIEW);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, forceDescriptorReview, "Force Descriptor to Review"));
			reportGenerator.logMessage("Force Descriptor to Review is successfully clicked", Status.PASS);
			
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, MaintenancePage.CreateInputDirectory_ClientInputbox, sClient);
			for(int index =0;index<MaintenancePage.clientsDropDownListedValues.size();index++) {
				if(MaintenancePage.clientsDropDownListedValues.get(index).getText().trim().equalsIgnoreCase(sClient)){
					MaintenancePage.clientsDropDownListedValues.get(index).click();
					break;
				}
			}
			SeleniumUtils.selectFromComboBox(webDriver, listProductId, hashmap.get("ProductName"));
			SeleniumUtils.selectFromComboBox(webDriver, listDocumentId, hashmap.get("DocumentName"));
			SeleniumUtils.doClick(webDriver, discriptorDropdown);
			SeleniumUtils.selectValueFromList(webDriver, discriptorMenuOptions, DescriptorName);				
			reportGenerator.logAndCaptureScreen(
					"The Force Descriptor To Review page fields are filled up with required values", "createDiscriptorInForceDescriptorToReviewPage",
					Status.PASS, webDriver,forceDescriptorReview);
			
			SeleniumUtils.doClick(webDriver, btnAddDER);
			CommonUtils.sleepForAWhile();				
			SeleniumUtils.doClick(webDriver, illegibleCheckbox);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, missingCheckbox);
			reportGenerator.logAndCaptureScreen(
					"Add Force Descriptor To Review dialogbox Displayed successfully ", "createDiscriptorInForceDescriptorToReviewPage",
					Status.PASS, webDriver,discriptor);
			
			SeleniumUtils.doClick(webDriver, btnSaveAddedDER);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen(
					"The created descriptor is displayed", "createDiscriptorInForceDescriptorToReviewPage",
					Status.PASS, webDriver,descriptorId);
			String id=descriptorId.getText();
			hashmap.put("descriptorid",id);
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
		Assert.fail(e.getMessage());
		}		
	}
		/**
		 * This method is used to validate edit Functionality of Force Descriptor To Review Page
		 * @param hashmap
		 * @throws Exception
		 */
		public void editFunctionalityInForceDescriptorToReviewPage(HashMap<String, String> hashmap) throws Exception{
				try {							
				SeleniumUtils.doClick(webDriver, btnEditDescriptor);
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, editDataPopup, "Edit Data Element"));
				CommonUtils.sleepForAWhile();
				SeleniumUtils.selectFromComboBox(webDriver, illegibleDropdown, hashmap.get("illegibleName"));
				SeleniumUtils.selectFromComboBox(webDriver, missingDropdown, hashmap.get("missingName"));
				SeleniumUtils.selectFromComboBox(webDriver, badValueDropdown, hashmap.get("BadValue"));
				SeleniumUtils.selectFromComboBox(webDriver, notApplicableDropdown, hashmap.get("NotApplicableName"));
				reportGenerator.logAndCaptureScreen(
						"Filled required details", "editFunctionalityInForceDescriptorToReviewPage",
						Status.PASS, webDriver,editDataPopup);
				SeleniumUtils.doClick(webDriver, btnSaveEdited);
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, infoTextPopup, "Updated data of selected successful"));
				reportGenerator.logAndCaptureScreen(
						"Info Dialogbox", "editFunctionalityInForceDescriptorToReviewPage",
						Status.PASS, webDriver,infoPopup);
				
				SeleniumUtils.doClick(webDriver, btnOkInfo);
				
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen(
						"Successfully displayed", "editFunctionalityInForceDescriptorToReviewPage",
						Status.PASS, webDriver,forceDescriptorReview);				
				this.deleteCreatedDiscriptor(hashmap);
			}catch (Exception e) {
					// throw e;
					e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
		
		public void deleteCreatedDiscriptor(HashMap<String, String> hashmap)throws Exception {
			
			try {
				
			SeleniumUtils.doClick(webDriver, editCheckbox);

			SeleniumUtils.doClick(webDriver, btnDeleteAll);

			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, confirmationText, "Are you sure you want to delete?"));

			reportGenerator.logAndCaptureScreen("Confirmation Dialogbox",
					"deleteCraetedDiscriptor", Status.PASS, webDriver, confirmationDialogbox);

			SeleniumUtils.doClick(webDriver, confirmationBtnYes);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, deleteConfirmationPopup,
					"Delete data of selected successfully"));
			reportGenerator.logAndCaptureScreen("Delete data of selected successfully",
					"deleteCreatedDiscriptor", Status.PASS, webDriver, infoPopup);
			SeleniumUtils.doClick(webDriver, btnOkInfo);				
			}catch (Exception e) {
				// throw e;
				e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		}
		
	/**
	 * edit Functionality In Multiple Descriptors is used to edit the multiple created descriptors.
	 * @param hashmap
	 */
	public void editFunctionalityInMultipleDescriptors(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.FORCE_DESCRIPTOR_TO_REVIEW);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, forceDescriptorReview, "Force Descriptor to Review"));
			reportGenerator.logMessage("Force Descriptor to Review is successfully clicked", Status.PASS);

			SeleniumUtils.doClick(webDriver, btnShow);
			String sDescriptorID1 = "//input[@id='chkEdit_" + hashmap.get("DescriptorID1") + "']";
			webDriver.findElement(By.xpath(sDescriptorID1)).click();
			String sDescriptorID2 = "//input[@id='chkEdit_" + hashmap.get("DescriptorID2") + "']";
			webDriver.findElement(By.xpath(sDescriptorID2)).click();
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("multiple data Descriptors are selected ",
					"editFunctionalityInMultipuleDescriptors", Status.PASS, webDriver,showingRecords);
			SeleniumUtils.doClick(webDriver, btnEditAll);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, editDataPopup, "Edit Data Element"));
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, illegibleDropdown, hashmap.get("illegibleName"));
			SeleniumUtils.selectFromComboBox(webDriver, missingDropdown, hashmap.get("missingName"));
			SeleniumUtils.selectFromComboBox(webDriver, badValueDropdown, hashmap.get("BadValue"));
			SeleniumUtils.selectFromComboBox(webDriver, notApplicableDropdown, hashmap.get("NotApplicableName"));
			reportGenerator.logAndCaptureScreen("Filled required details", "editFunctionalityInMultipleDescriptors",
					Status.PASS, webDriver, editDataPopup);
			SeleniumUtils.doClick(webDriver, btnSaveEdited);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, infoTextPopup, "Updated data of selected successful"));
			reportGenerator.logAndCaptureScreen("Info Dialogbox", "editFunctionalityInMultipleDescriptors", Status.PASS,
					webDriver, infoPopup);
			SeleniumUtils.doClick(webDriver, btnOkInfo);
			CommonUtils.sleepForAWhile();
			String sDescriptorID3 = "//input[@id='chkEdit_" + hashmap.get("DescriptorID1") + "']";
			webDriver.findElement(By.xpath(sDescriptorID3)).click();
			String sDescriptorID4 = "//input[@id='chkEdit_" + hashmap.get("DescriptorID2") + "']";
			webDriver.findElement(By.xpath(sDescriptorID4)).click();
			reportGenerator.logAndCaptureScreen("descriptor checkbox is selected Successfully",
					"editFunctionalityInMultipleDescriptors", Status.PASS, webDriver,showingRecords);
			SeleniumUtils.doClick(webDriver, btnDeleteAll);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, confirmationText, "Are you sure you want to delete?"));
			reportGenerator.logAndCaptureScreen("Confirmation Dialogbox", "editFunctionalityInMultipleDescriptors",
					Status.PASS, webDriver, confirmationDialogbox);
			SeleniumUtils.doClick(webDriver, confirmationBtnYes);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, deleteConfirmationPopup,
					"Delete data of selected successfully"));
			reportGenerator.logAndCaptureScreen("Delete data of selected successfully",
					"editFunctionalityInMultipleDescriptors", Status.PASS, webDriver, infoPopup);
			SeleniumUtils.doClick(webDriver, btnOkInfo);
			reportGenerator.logAndCaptureScreen("selected descriptors are deleted",
					"editFunctionalityInMultipleDescriptors", Status.PASS, webDriver);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * action Functionality In Multiple Descriptors is used to select the multiple created descriptors to delete.
	 * @param hashmap
	 */
	public void actionFunctionalityInDescriptorsRevewPage(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.FORCE_DESCRIPTOR_TO_REVIEW);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, forceDescriptorReview, "Force Descriptor to Review"));
			reportGenerator.logMessage("Force Descriptor to Review is successfully clicked", Status.PASS);

			SeleniumUtils.doClick(webDriver, btnShow);
			String sDescriptorID1 = "//input[@id='chkEdit_" + hashmap.get("DescriptorID1") + "']";
			webDriver.findElement(By.xpath(sDescriptorID1)).click();
			String sDescriptorID2 = "//input[@id='chkEdit_" + hashmap.get("DescriptorID2") + "']";
			webDriver.findElement(By.xpath(sDescriptorID2)).click();
			reportGenerator.logAndCaptureScreen("Selected multiple data Descriptors are displayed",
					"actionFunctionalityInDescriptorsRevewPage", Status.PASS, webDriver,showingRecords);
			SeleniumUtils.doClick(webDriver, btnEditAll);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, editDataPopup, "Edit Data Element"));
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, illegibleDropdown, hashmap.get("illegibleName"));
			SeleniumUtils.selectFromComboBox(webDriver, missingDropdown, hashmap.get("missingName"));
			SeleniumUtils.selectFromComboBox(webDriver, badValueDropdown, hashmap.get("BadValue"));
			SeleniumUtils.selectFromComboBox(webDriver, notApplicableDropdown, hashmap.get("NotApplicableName"));
			reportGenerator.logAndCaptureScreen("Filled required details", "actionFunctionalityInDescriptorsRevewPage",
					Status.PASS, webDriver, editDataPopup);
			SeleniumUtils.doClick(webDriver, btnSaveEdited);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, infoTextPopup, "Updated data of selected successful"));
			reportGenerator.logAndCaptureScreen("Info Dialogbox", "actionFunctionalityInDescriptorsRevewPage", Status.PASS,
					webDriver, infoPopup);
			SeleniumUtils.doClick(webDriver, btnOkInfo);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, reviewId);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, chkEditAll);
			SeleniumUtils.sendKeys(webDriver, txtNoOfRows, hashmap.get("NoRows"));
			reportGenerator.logAndCaptureScreen("enter the number of rows in textfield",
					"actionFunctionalityInDescriptorsRevewPage", Status.PASS, webDriver,forceDescriptorReview);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, btnOkSelectRowsEdit);
			reportGenerator.logAndCaptureScreen("descriptor checkbox is selected Successfully",
					"actionFunctionalityInDescriptorsRevewPage", Status.PASS, webDriver,forceDescriptorReview);
			SeleniumUtils.doClick(webDriver, btnDeleteAll);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, confirmationText, "Are you sure you want to delete?"));
			reportGenerator.logAndCaptureScreen("Confirmation Dialogbox", "actionFunctionalityInDescriptorsRevewPage",
					Status.PASS, webDriver, confirmationDialogbox);
			SeleniumUtils.doClick(webDriver, confirmationBtnYes);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, deleteConfirmationPopup,
					"Delete data of selected successfully"));
			reportGenerator.logAndCaptureScreen("Delete data of selected successfully",
					"actionFunctionalityInDescriptorsRevewPage", Status.PASS, webDriver, infoPopup);
			SeleniumUtils.doClick(webDriver, btnOkInfo);
			reportGenerator.logAndCaptureScreen("selected descriptrs are deleted",
					"actionFunctionalityInDescriptorsRevewPage", Status.PASS, webDriver);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * This method is used to validate latest log time
	 * @param hashmap
	 * @throws Exception
	 */
	public void verifyLogDownloadFunctionality1(HashMap<String, String> hashmap) throws Exception {
		
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.UTILITIES,
					IdeaWFSConstants.VIEW_LOG);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, viewLog, "View Log Page"));
			reportGenerator.logMessage("View Log is successfully clicked", Status.PASS);
			
			SeleniumUtils.selectFromComboBox(webDriver, logFileDropdown, hashmap.get("TypeOfLog"));
			SeleniumUtils.doClick(webDriver, refreshButton);
			CommonUtils.sleepForAWhile(2000);
			String txtLog=txtLogContent.getText();
			SeleniumUtils.doClick(webDriver, downloadButton);
			String downloadedFilepath = System.getProperty("user.dir")+File.separator+"downloadedfiles";
			FileUtils.copyFile(new File(downloadedFilepath), new File(reportGenerator.getReportPath()));
			CommonUtils.sleepForAWhile();
			String sZipFilePath = reportGenerator.getReportPath() + File.separator + "idea.log.zip";
			ZipFile zipFile = new ZipFile(sZipFilePath);
			zipFile.extractAll(reportGenerator.getReportPath());
			String unZippedFilePath = reportGenerator.getReportPath() + File.separator + "idea.log" + File.separator + "idea.log.txt";
			String actualLogData = FileUtils.readFileToString(new File(unZippedFilePath), "UTF-8");
			System.out.println(actualLogData);
			Assert.assertEquals(actualLogData, txtLog);
//			reportGenerator.logAndCaptureScreen("before selecting Enable Advanced Logging text log content",
//					"verifyLogDownloadFunctionality", Status.PASS, webDriver,txtLogContent);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * This method is used to validate downloaded logs are same as it is showing in wfs
	 * @param hashmap
	 * @throws Exception
	 */
	public void verifyLogDownloadFunctionality(HashMap<String, String> hashmap) throws Exception {

		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.UTILITIES,
					IdeaWFSConstants.VIEW_LOG);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, viewLog, "View Log Page"));
			reportGenerator.logMessage("View Log is successfully clicked", Status.PASS);

			SeleniumUtils.selectFromComboBox(webDriver, logFileDropdown, hashmap.get("TypeOfLog"));
			SeleniumUtils.doClick(webDriver, refreshButton);
			CommonUtils.sleepForAWhile(2000);
			String txtLog = txtLogContent.getText().trim();
			reportGenerator.logAndCaptureScreen("Log descriptor is displayed",
					"verifyLogDownloadFunctionality", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, downloadButton);
			CommonUtils.sleepForAWhile();
			String downloadedFilepath = System.getProperty("user.dir") + File.separator + "downloadedfiles"
					+ File.separator + "idea.log.zip";

			FileUtils.copyFile(new File(downloadedFilepath),
					new File(reportGenerator.getReportPath() + File.separator + "idea.log.zip"));
			CommonUtils.sleepForAWhile();
			String sZipFilePath = reportGenerator.getReportPath() + File.separator + "idea.log.zip";
			String sDestinationPath = reportGenerator.getReportPath() + File.separator + "idea.log";
			ZipFile zipFile = new ZipFile(sZipFilePath);
			zipFile.extractAll(sDestinationPath);
			CommonUtils.sleepForAWhile();
			String unZippedFilePath = reportGenerator.getReportPath() + File.separator + "idea.log" + File.separator
					+ "idea.log";
			File file = new File(unZippedFilePath);
			if(file.exists()) {
				reportGenerator.logMessage("Idea.log file is displayed in zip file", Status.PASS);
			}else {
				Assert.fail("Idea.log file is not displayed in the zip file");
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * check Auto Import Profile Batch Update is used to update the auto import
	 * profile.
	 * 
	 * @param hashmap
	 * @throws Exception
	 */
	public void checkAutoImportProfileBatchUpdate(HashMap<String, String> hashmap) throws Exception {
		try {
			this.createAutoScheduledImportProfile(hashmap);
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.AUTO_SCHEDULED_IMPORT_PROFILE_BATCH_UPDATE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, autoImportProfileBatchUpdate,
					"Auto-Scheduled Import Profile - Batch Update"));
			reportGenerator.logMessage("Auto Import Profile Batch Update is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.vendorDropdown);
			SeleniumUtils.selectValueFromList(webDriver, indexingDashboardMaintenancePage.vendorMenuOptions,
					hashmap.get("ProcessingCenter"));
			reportGenerator.logAndCaptureScreen("Vendor name is selected", "checkAutoImportProfileBatchUpdate",
					Status.PASS, webDriver, autoImportProfileBatchUpdate);
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.vednorText);
			CommonUtils.sleepForAWhile();
			String sClient = hashmap.get("ClientName") + " - " + hashmap.get("ClientID");
			SeleniumUtils.doClick(webDriver, ClientDropdown);
			SeleniumUtils.sendKeys(webDriver, indexingDashboardMaintenancePage.clientTextField, sClient);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectValueFromList(webDriver, indexingDashboardMaintenancePage.clientMenuOptions, sClient);
			reportGenerator.logAndCaptureScreen("Client name is selected", "checkAutoImportProfileBatchUpdate",
					Status.PASS, webDriver, autoImportProfileBatchUpdate);
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.vednorText);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.btnSearch);
			CommonUtils.sleepForAWhile();
			String xpath = "//table[@id='tbl-auto-import-profile']/tbody/tr/td[contains(text(),'"
					+ hashmap.get("Profile") + "')]/following-sibling::td/input";
			webDriver.findElement(By.xpath(xpath)).click();
			reportGenerator.logAndCaptureScreen("profile is selected", "checkAutoImportProfileBatchUpdate", Status.PASS,
					webDriver, autoImportProfileBatchUpdate);
			SeleniumUtils.doClick(webDriver, btnProceed);
			CommonUtils.sleepForAWhile();

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * client Profiles Batch Update is used to update created auto import profile in
	 * batch update page
	 * 
	 * @param hashmap
	 * @throws Exception
	 */
	public void clientProfilesBatchUpdate(HashMap<String, String> hashmap) throws Exception {
		try {
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, clientProfileBatchUpdate, "Client Profiles - Batch Update"));
			reportGenerator.logMessage("Client Profiles - Batch Update", Status.PASS);
			reportGenerator.logAndCaptureScreen("Client Profiles - Batch Update", "clientProfilesBatchUpdate",
					Status.PASS, webDriver, clientProfileBatchUpdate);
			SeleniumUtils.selectFromComboBox(webDriver, listManglerIdProfile, hashmap.get("ManglerName"));
			SeleniumUtils.selectFromComboBox(webDriver, profileIgnoreDuplicateLoan, hashmap.get("ProfileIgnore"));
			SeleniumUtils.selectFromComboBox(webDriver, profileShowAllLoans, hashmap.get("ProfileShowLoans"));
			SeleniumUtils.doClick(webDriver, autoImportStartTime);
			SeleniumUtils.doClick(webDriver, startTimeSelect);
			SeleniumUtils.doClick(webDriver, autoImportStopTime);
			SeleniumUtils.doClick(webDriver, stopTimeSelect);
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("bProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("bWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("OnePassDePercentage"));
			reportGenerator.logAndCaptureScreen("all the details are entered in Client Profiles - Batch Update",
					"clientProfilesBatchUpdate", Status.PASS, webDriver, clientProfileBatchUpdate);
			SeleniumUtils.doClick(webDriver, btnSave);
			CommonUtils.sleepForAWhile(3000);
			reportGenerator.logAndCaptureScreen("profile created Successfully", "clientProfilesBatchUpdate",
					Status.PASS, webDriver, clientProfileBatchUpdate);
			if (SeleniumUtils.isDisplayed(webDriver, confirmationMsgPopUp, "Confirmation Popup")) {
				Assert.assertEquals(confirmationMsg.getText().trim(), hashmap.get("ConfirmationPopUpMsg"));
				reportGenerator.logAndCaptureScreen("Confirmation msg is displayed successfully",
						"clientProfilesBatchUpdate", Status.PASS, webDriver, confirmationPopupYesBtn);
				SeleniumUtils.doClick(webDriver, confirmationPopupYesBtn);
			}
			reportGenerator.logAndCaptureScreen("success Message is displayed", "clientProfilesBatchUpdate",
					Status.PASS, webDriver);
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.AUTO_SCHEDULED_IMPORT_PROFILE_BATCH_UPDATE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, autoImportProfileBatchUpdate,
					"Auto-Scheduled Import Profile - Batch Update"));
			reportGenerator.logMessage("Auto Import Profile Batch Update is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.vendorDropdown);
			SeleniumUtils.selectValueFromList(webDriver, indexingDashboardMaintenancePage.vendorMenuOptions,
					hashmap.get("bProcessingCenter"));
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.vednorText);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, indexingDashboardMaintenancePage.btnSearch);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("selected profile is displayed", "clientProfilesBatchUpdate",
					Status.PASS, webDriver, autoImportProfileBatchUpdate);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * create AutoScheduled Import Profile is used to create the AutoScheduled Import Profile.
	 * @param hashmap
	 * @throws Exception
	 */
	public void selectMultipleWorkFlowsInAutoScheduledImportProfile(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfile, "Manage Auto-Scheduled Import Profile"));
			reportGenerator.logMessage("Manage Auto-Scheduled Import Profile clicked", Status.PASS);
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
			for(int index =0;index<qClientsDropdownList.size();index++) {
				if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)){
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			
			SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("Profile"));
			boolean activeCheckbox=isActive.isSelected();
			reportGenerator.logMessage("Checking isActive boolean status:"+activeCheckbox,Status.PASS);
			if(activeCheckbox) {
				reportGenerator.logMessage("isActive checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,isActive);
				reportGenerator.logMessage("isActive checkbox is selected ", Status.PASS);
			}		
			SeleniumUtils.selectFromComboBox(webDriver, productId, hashmap.get("ProductName"));
			boolean showAllLoans = isShowAllLoans.isSelected();
			reportGenerator.logMessage("Checking isImportAllLoans boolean status:"+showAllLoans,Status.PASS);
			if(showAllLoans) {
				reportGenerator.logMessage("isImportAllLoans checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,isShowAllLoans);
				reportGenerator.logMessage("isImportAllLoans checkbox is selected ", Status.PASS);
			}
			boolean duplicateLoan = ignoreDuplicateLoan.isSelected();
			reportGenerator.logMessage("Checking ignoreDuplicateLoan boolean status:"+duplicateLoan,Status.PASS);
			if(duplicateLoan) {
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected already ", Status.PASS);
			}else {
				SeleniumUtils.doClick(webDriver,ignoreDuplicateLoan);
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected ", Status.PASS);
			}
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("CoPWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("OnePassDePercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityOne, hashmap.get("PriorityOne"));
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("CPWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("StandedWithEirPercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, hashmap.get("PriorityTwo"));
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterThree, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workFlowThree, hashmap.get("WorkflowNameThree"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageThree, hashmap.get("PercentageThree"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityThree, hashmap.get("PriorityThree"));
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterFour, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workFlowFour, hashmap.get("WorkflowNameFour"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageFour, hashmap.get("Percentagefour"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityFour, hashmap.get("PriorityFour"));
			
			CommonUtils.sleepForAWhile(1000);
			reportGenerator.logAndCaptureScreen("All the details were filled successfully for porfile creation",
					"selectMultipuleWorkFlowsInAutoScheduledImportProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			SeleniumUtils.doClick(webDriver, btnSave);
			CommonUtils.sleepForAWhile(3000);
			reportGenerator.logAndCaptureScreen("profile created Successfully",
					"selectMultipuleWorkFlowsInAutoScheduledImportProfile", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, selectedProfileHasbeenSaved, hashmap.get("ActualMessage")));
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}
	}
	public void executeCommandKillJavaProcess(HashMap<String, String> hashmap, ConnectToLinuxBox connectToLinuxBox) {
		try {
			connectToLinuxBox.executeCommandTokillJavaProcess(hashmap,
					EnvironmentPropertyLoader.getPropertyByName("autoimporter_host"),
					EnvironmentPropertyLoader.getPropertyByName("user"),
					IdeaEFSConstants.CMD_TO_KILL_JAVA,
					EnvironmentPropertyLoader.getPropertyByName("privatekeypath"));
			CommonUtils.sleepForAWhile(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setDefaultDetailsToWorkFlowsInAutoScheduledImportProfile() {
		try {
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, IdeaWFSConstants.PROCESSING_CENTER);
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, IdeaWFSConstants.WORK_FLOW);
			SeleniumUtils.sendKeys(webDriver, autoImportPercentage, IdeaWFSConstants.PERCENTAGE);
			SeleniumUtils.selectFromComboBox(webDriver, priorityOne, IdeaWFSConstants.PRIORITY);
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenter, IdeaWFSConstants.PROCESSING_CENTER);
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, IdeaWFSConstants.WORK_FLOW);
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, IdeaWFSConstants.PERCENTAGE);
			SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, IdeaWFSConstants.PRIORITY_TWO);
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterThree, IdeaWFSConstants.PROCESSING_CENTER);
			SeleniumUtils.selectFromComboBox(webDriver, workFlowThree, IdeaWFSConstants.WORK_FLOW);
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageThree, IdeaWFSConstants.PERCENTAGE);
			SeleniumUtils.selectFromComboBox(webDriver, priorityThree, IdeaWFSConstants.PRIORITY_THREE);
			
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterFour, IdeaWFSConstants.PROCESSING_CENTER);
			SeleniumUtils.selectFromComboBox(webDriver, workFlowFour, IdeaWFSConstants.WORK_FLOW);
			SeleniumUtils.sendKeys(webDriver, autoImportPercentageFour,IdeaWFSConstants.PERCENTAGE);
			SeleniumUtils.selectFromComboBox(webDriver, priorityFour, IdeaWFSConstants.PRIORITY_FOUR);		
			
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * checkExistingAIProfileCorrectly is used to check existing Al profile is disaplying or not
	 * @param hashmap
	 */
	public void checkExistingAIProfileCorrectlyDisplayed(HashMap<String, String> hashmap) {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfile, "Manage Auto-Scheduled Import Profile"));
			reportGenerator.logMessage("Manage Auto-Scheduled Import Profile clicked", Status.PASS);
			String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
			for(int index =0;index<qClientsDropdownList.size();index++) {
				if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)){
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("Profile"));
			
			Select sel = new Select(processingCenterId);
			WebElement ele = sel.getFirstSelectedOption();
			String value = ele.getText();
			System.out.println(value);
			Select sele = new Select(processingCenter);
			WebElement elem =sele.getFirstSelectedOption();
			String prCenter =ele.getText();
			System.out.println(prCenter);
			Select selDrop = new Select(workflowLookupId);
			WebElement drop=selDrop.getFirstSelectedOption();
			String wFlow = drop.getText();
			
			Select dropdown = new Select(workflowLookupIdtwo);
			WebElement wfDropdwon= dropdown.getFirstSelectedOption();
			String wrFlow = wfDropdwon.getText();
			
			String percentageOne=autoImportPercentage.getAttribute("value");
			String percentage =autoImportPercentageOne.getAttribute("value");
			
			if(value.equals(hashmap.get("ProcessingCenter"))) {
			Reporter.log("The existing processing center is displayed "+ value);	
			}else {
				Assert.fail("The existing processing center is not displayed ");
			}
			
			if(prCenter.equals(hashmap.get("ProcessingCenter"))) {
				Reporter.log("The existing processing center is displayed "+ prCenter);	
				}else {
					Assert.fail("The existing processing center is not displayed ");
				}
			if(wFlow.equals(hashmap.get("CoPWorkflowName"))) {
				Reporter.log("The existing workflow is displayed "+ wFlow);	
				}else {
					Assert.fail("The existing workflow is not displayed ");
				}
			if(wrFlow.equals(hashmap.get("CPWorkflowName"))) {
				Reporter.log("The existing workflow is displayed "+ wrFlow);	
				}else {
					Assert.fail("The existing workflow is not displayed ");
				}
			if(percentageOne.equals(hashmap.get("OnePassDePercentage"))) {
				Reporter.log("The existing OnePassAutomatedDE percentage is displayed "+ percentageOne);	
				}else {
					Assert.fail("The existing OnePassAutomatedDE percentage is not displayed ");
				}
			if(percentage.equals(hashmap.get("StandedWithEirPercentage"))) {
				Reporter.log("The existing StandedWithEIR percentage is displayed "+ percentage);	
				}else {
					Assert.fail("The existing StandedWithEIR percentage is not displayed ");
				}
			reportGenerator.logAndCaptureScreen("existing profile details are displayed Successfully",
					"checkExistingAIProfileCorrectlyDisplayed", Status.PASS, webDriver,manageAutoScheduledImportProfile);
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}
	
	public void createNewLoanImportPageAllocation(HashMap<String, String> hashmap) {
		try {
			
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.UTILITIES,
					IdeaWFSConstants.MANAGE_LOAN_IMPORT_PAGE_ALLOCATION);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageLoanImport, "Manage Loan Import Page Allocation"));
			reportGenerator.logMessage("Manage Loan Import Page Allocation", Status.PASS);
			if(productPennyMac.size()>0) {
				Reporter.log("the added new loan import page allocation is already created");
				reportGenerator.logAndCaptureScreen("added new loan import page allocation is already created",
						"createNewLoanImportPageAllocation", Status.PASS, webDriver,manageLoanImport);
			}else {
				SeleniumUtils.doClick(webDriver, btnLoanImport);
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, addNewLoanAllocationPopup, "Add New Loan Import Page Allocation"));
				reportGenerator.logAndCaptureScreen("added new loan import page allocation popup",
						"createNewLoanImportPageAllocation", Status.PASS, webDriver,addNewLoanAllocationPopup);
				String sClient = hashmap.get("sClientName")+" - "+hashmap.get("sClientID");
				SeleniumUtils.sendKeys(webDriver, editProductInAddNewLoanAllocationPopup, sClient);
				for(int index =0;index<qClientsDropdownList.size();index++) {
					if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)){
						qClientsDropdownList.get(index).click();
						break;
					}
				}
				
				CommonUtils.sleepForAWhile();
				SeleniumUtils.sendKeys(webDriver, maxPageCount, hashmap.get("Count"));
				reportGenerator.logAndCaptureScreen("details are entered in added new loan import page allocation popup",
						"createNewLoanImportPageAllocation", Status.PASS, webDriver,addNewLoanAllocationPopup);
				SeleniumUtils.doClick(webDriver, saveButton);
				CommonUtils.sleepForAWhile();
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, successfullMessage, "Added new loan import page allocation"));
				reportGenerator.logAndCaptureScreen("As expected Max page count is displayed", "addNewLoanAllocationPopup",
						Status.PASS, webDriver);
				
			}
			
			
			
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * checkSplitByCompanyInAIProfile is used to check split by checkbox.
	 * @param hashmap
	 */
	public void checkSplitByCompanyInAIProfile(HashMap<String, String> hashmap) {
		try {
		userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
				IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE);
		CommonUtils.sleepForAWhile();
		Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfile, "Manage Auto-Scheduled Import Profile"));
		reportGenerator.logMessage("Manage Auto-Scheduled Import Profile clicked", Status.PASS);
		String sClient = hashmap.get("ClientName")+" - "+hashmap.get("ClientID");
		SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
		for(int index =0;index<qClientsDropdownList.size();index++) {
			if(qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)){
				qClientsDropdownList.get(index).click();
				break;
			}
		}
		SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("Profile"));
		boolean splitByCompanyCheckbox = splitByCompany.get(0).isSelected();
		if(splitByCompanyCheckbox) {
			reportGenerator.logMessage("splitByCompanyCheckbox checkbox is selected already ", Status.PASS);
		}else {
			Assert.fail("splitByCompanyCheckbox checkbox is not selected");
		}		
		}catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}	
	}
	
		
	
	/**
	 * create AutoScheduled Import Profile is used to create the AutoScheduled Import Profile.
	 * @param hashmap
	 * @throws Exception
	 */
	public void createAutoScheduledImportProfileWithPriority(HashMap<String, String> hashmap) {
		try {

			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfile,
					"Manage Auto-Scheduled Import Profile"));
			reportGenerator.logMessage("Manage Auto-Scheduled Import Profile clicked", Status.PASS);
			String sClient = hashmap.get("ClientName") + " - " + hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
			for (int index = 0; index < qClientsDropdownList.size(); index++) {
				if (qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)) {
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("Profile"));
			SeleniumUtils.selectFromComboBox(webDriver, profilePriority, hashmap.get("ProfilePriority"));
			boolean activeCheckbox = isActive.isSelected();
			reportGenerator.logMessage("Checking isActive boolean status:" + activeCheckbox, Status.PASS);
			if (activeCheckbox) {
				reportGenerator.logMessage("isActive checkbox is selected already ", Status.PASS);
			} else {
				SeleniumUtils.doClick(webDriver, isActive);
				reportGenerator.logMessage("isActive checkbox is selected ", Status.PASS);
			}
			SeleniumUtils.selectFromComboBox(webDriver, productId, hashmap.get("ProductName"));
			boolean showAllLoans = isShowAllLoans.isSelected();
			reportGenerator.logMessage("Checking isImportAllLoans boolean status:" + showAllLoans, Status.PASS);
			if (showAllLoans) {
				reportGenerator.logMessage("isImportAllLoans checkbox is selected already ", Status.PASS);
			} else {
				SeleniumUtils.doClick(webDriver, isShowAllLoans);
				reportGenerator.logMessage("isImportAllLoans checkbox is selected ", Status.PASS);
			}
			boolean duplicateLoan = ignoreDuplicateLoan.isSelected();
			reportGenerator.logMessage("Checking ignoreDuplicateLoan boolean status:" + duplicateLoan, Status.PASS);
			if (duplicateLoan) {
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected already ", Status.PASS);
			} else {
				SeleniumUtils.doClick(webDriver, ignoreDuplicateLoan);
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected ", Status.PASS);
			}
			if (splitByCompany.size() > 0) {
				if (hashmap.get("TestCaseNumber").contains("C892248")) {
					if (splitByCompany.get(0).isSelected()) {
						splitByCompany.get(0).click();
						reportGenerator.logMessage("SplitByCompany checkbox is unchecked ", Status.PASS);
					} else {
						reportGenerator.logMessage("SplitByCompany checkbox is unchecked ", Status.PASS);
					}
				} else if (hashmap.get("TestCaseNumber").contains("C892245")) {
					if (splitByCompany.get(0).isSelected()) {
						reportGenerator.logMessage("SplitByCompany checkbox is checked ", Status.PASS);
					} else {
						splitByCompany.get(0).click();
						reportGenerator.logMessage("SplitByCompany checkbox is checked ", Status.PASS);
					}

				}
			}
			
			this.setDefaultDetailsToWorkFlowsInAutoScheduledImportProfile();
			SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter"));
			SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("FirstWorkflowName"));
			SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("FirstPercentage"));
			SeleniumUtils.selectFromComboBox(webDriver, priorityOne, hashmap.get("FirstPriority"));
			// if(hashmap.get("CPWorkflowName")!="") {
			if (hashmap.get("SecondWorkflowName").trim().length()!= 0) {
				SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("ProcessingCenter"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("SecondWorkflowName"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("SecondPercentage"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, hashmap.get("SecondPriority"));
			}
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("All the details were filled successfully for profile creation",
					"verfiyAutoScheduledImportProfile", Status.PASS, webDriver, manageAutoScheduledImportProfile);
			SeleniumUtils.doClick(webDriver, btnSave);
			CommonUtils.sleepForAWhile(4000);
			reportGenerator.logAndCaptureScreen("profile created Successfully", "verfiyAutoScheduledImportProfile",
					Status.PASS, webDriver, manageAutoScheduledImportProfile);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, selectedProfileHasbeenSaved, hashmap.get("ActualMessage")));

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * create AutoScheduled Import Profile is used to create the AutoScheduled Import Profile.
	 * @param hashmap
	 * @throws Exception
	 */
	public void createAutoScheduledImportProfileHandlingMultipleWorkFlows(HashMap<String, String> hashmap,String NoOfWorkflows,String ProfilePriority) {
		try {

			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS, IdeaWFSConstants.IMPORT,
					IdeaWFSConstants.MANAGE_AUTO_SCHEDULED_IMPORT_PROFILE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageAutoScheduledImportProfile,
					"Manage Auto-Scheduled Import Profile"));
			reportGenerator.logMessage("Manage Auto-Scheduled Import Profile clicked", Status.PASS);
			String sClient = hashmap.get("ClientName") + " - " + hashmap.get("ClientID");
			SeleniumUtils.sendKeys(webDriver, txtClient, sClient);
			for (int index = 0; index < qClientsDropdownList.size(); index++) {
				if (qClientsDropdownList.get(index).getText().trim().equalsIgnoreCase(sClient)) {
					qClientsDropdownList.get(index).click();
					break;
				}
			}
			SeleniumUtils.selectFromComboBox(webDriver, listImportProfileId, hashmap.get("Profile"));
			SeleniumUtils.selectFromComboBox(webDriver, profilePriority, ProfilePriority);
			boolean activeCheckbox = isActive.isSelected();
			reportGenerator.logMessage("Checking isActive boolean status:" + activeCheckbox, Status.PASS);
			if (activeCheckbox) {
				reportGenerator.logMessage("isActive checkbox is selected already ", Status.PASS);
			} else {
				SeleniumUtils.doClick(webDriver, isActive);
				reportGenerator.logMessage("isActive checkbox is selected ", Status.PASS);
			}
			SeleniumUtils.selectFromComboBox(webDriver, productId, hashmap.get("ProductName"));
			boolean showAllLoans = isShowAllLoans.isSelected();
			reportGenerator.logMessage("Checking isImportAllLoans boolean status:" + showAllLoans, Status.PASS);
			if (showAllLoans) {
				reportGenerator.logMessage("isImportAllLoans checkbox is selected already ", Status.PASS);
			} else {
				SeleniumUtils.doClick(webDriver, isShowAllLoans);
				reportGenerator.logMessage("isImportAllLoans checkbox is selected ", Status.PASS);
			}
			boolean duplicateLoan = ignoreDuplicateLoan.isSelected();
			reportGenerator.logMessage("Checking ignoreDuplicateLoan boolean status:" + duplicateLoan, Status.PASS);
			if (duplicateLoan) {
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected already ", Status.PASS);
			} else {
				SeleniumUtils.doClick(webDriver, ignoreDuplicateLoan);
				reportGenerator.logMessage("ignoreDuplicateLoan checkbox is selected ", Status.PASS);
			}
			this.setDefaultDetailsToWorkFlowsInAutoScheduledImportProfile();
			if(NoOfWorkflows.equals("1")) {
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter1"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("WorkFlow1"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("Percentage1"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityOne, hashmap.get("Priority1"));
			}else if(NoOfWorkflows.equals("2")){
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter1"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("WorkFlow1"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("Percentage1"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityOne, hashmap.get("Priority1"));
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("ProcessingCenter2"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("WorkFlow2"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("Percentage2"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, hashmap.get("Priority2"));
				
			}else if(NoOfWorkflows.equals("3")){
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter1"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("WorkFlow1"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("Percentage1"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityOne, hashmap.get("Priority1"));
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("ProcessingCenter2"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("WorkFlow2"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("Percentage2"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, hashmap.get("Priority2"));
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterThree, hashmap.get("ProcessingCenter3"));
				SeleniumUtils.selectFromComboBox(webDriver, workFlowThree, hashmap.get("WorkFlow3"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageThree, hashmap.get("Percentage3"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityThree, hashmap.get("Priority3"));
				
			}else if(NoOfWorkflows.equals("4")){
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterId, hashmap.get("ProcessingCenter1"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupId, hashmap.get("WorkFlow1"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentage, hashmap.get("Percentage1"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityOne, hashmap.get("Priority1"));
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenter, hashmap.get("ProcessingCenter2"));
				SeleniumUtils.selectFromComboBox(webDriver, workflowLookupIdtwo, hashmap.get("WorkFlow2"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageOne, hashmap.get("Percentage2"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityTwo, hashmap.get("Priority2"));
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterThree, hashmap.get("ProcessingCenter3"));
				SeleniumUtils.selectFromComboBox(webDriver, workFlowThree, hashmap.get("WorkFlow3"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageThree, hashmap.get("Percentage3"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityThree, hashmap.get("Priority3"));
				
				SeleniumUtils.selectFromComboBox(webDriver, processingCenterFour, hashmap.get("ProcessingCenter4"));
				SeleniumUtils.selectFromComboBox(webDriver, workFlowFour, hashmap.get("WorkFlow4"));
				SeleniumUtils.sendKeys(webDriver, autoImportPercentageFour,hashmap.get("Percentage4"));
				SeleniumUtils.selectFromComboBox(webDriver, priorityFour, hashmap.get("Priority4"));
			}
			
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("All the details were filled successfully for porfile creation",
					"verfiyAutoScheduledImportProfile", Status.PASS, webDriver, manageAutoScheduledImportProfile);
			SeleniumUtils.doClick(webDriver, btnSave);
			CommonUtils.sleepForAWhile(4000);
			reportGenerator.logAndCaptureScreen("profile created Successfully", "verfiyAutoScheduledImportProfile",
					Status.PASS, webDriver, manageAutoScheduledImportProfile);
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, selectedProfileHasbeenSaved, hashmap.get("ActualMessage")));

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
//			Assert.fail(e.getMessage());
		}
	}
}
