package com.ll.idea.wfs.suite;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.dataprovider.AutomationTestDataProvider;
import com.ll.idea.management.TestNGTestRailUploader;
import com.ll.idea.masterdata.manager.IdeaMasterDataManager;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.sk.SKOperationManager;
import com.ll.idea.utils.CSVFileParser;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.MySQLDBConnection;
import com.ll.idea.utils.SeleniumUtils;
import com.ll.idea.utils.XMLFileHandler;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.SmartKeyLoginPage;
import com.ll.idea.wfs.ui.page.WFSCRUDUIPagecountThresholdMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSIndexingDashboardMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSToolsMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;

import junit.framework.Assert;

public class TestWFSCRUDUIPagecountThresholdSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSCRUDUIPagecountThresholdMaintenancePage crudUIMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	HashMap<String, String> documentTypesMap = null;

	SmartKeyLoginPage smartKeyLoginPage = null;
	WFSMaintenancePage wfsMaintenancePage = null;
	ConnectToLinuxBox connecttoLinuxBox = null;
	SKOperationManager skOperationManager = null;
	CSVFileParser csvFileParser = null;
	XMLFileHandler xmlFileHandler = null;
	WFSIndexingDashboardMaintenancePage indexingDashboardMaintenancePage = null;
	WFSToolsMaintenancePage wfsToolsMaintenancePage = null;

	public TestWFSCRUDUIPagecountThresholdSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1" })
	public void doSetUp(ITestContext iTestContext) throws Exception {
		/**
		 * Create sub directory under <code>/testdata</code> for every suite to
		 * segregate the test data CSV file, helps to manage with ease. For example,
		 * <code>user</code> is a sub test data folder to manage all csv files related
		 * to user module
		 */
		this.setTestSuiteName("user");
		documentTypesMap = (HashMap<String, String>) IdeaMasterDataManager.loadDocumentTypesTable();
		this.initializeBrowser(iTestContext);
		loginPage = new WFSLoginPage(this.getListOfDrivers().get(this.getTestSuiteName()));
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1" })
	public void doBeforeMethod(ITestContext iTestContext) {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		homePage = new WFSHomePage(driver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(driver, reportGenerator);
		crudUIMaintenancePage = new WFSCRUDUIPagecountThresholdMaintenancePage(driver, reportGenerator);
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		wfsToolsMaintenancePage = new WFSToolsMaintenancePage(driver, reportGenerator);
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(driver, reportGenerator);
		connecttoLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
		loginPage.setReportGenerator(reportGenerator);
	}

	@AfterMethod(alwaysRun = true, groups = { "Level1" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1" })
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout();
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}

	/*
	Pagecount Threshold Test cases
	 */
    @Ignore
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Pagecount Threshold CRUD Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void TestManagePagecountThreshold(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "TestManagePagecountThreshold");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			if(hashmap.get("PagecountThresholdFunctionality").equals("Login")) {
				crudUIMaintenancePage.validatePagecountThresholdLink(hashmap);
			}
			if (hashmap.get("PagecountThresholdFunctionality").equals("Create")) {
				// Creating New Dem Automation Document De with document type id and client
				// product id
				crudUIMaintenancePage.createNewPagecountThreshold(hashmap);
				Assert.assertTrue(SeleniumUtils.getValue(driver, crudUIMaintenancePage.successMessage)
						.contains(hashmap.get("ExpectedResult")));
				// validating the created documenttypeID, client productID into the dem
				// automation Document De table
				crudUIMaintenancePage.doValidateCreatedPagecountThresholdRecord(hashmap);
			} else if (hashmap.get("PagecountThresholdFunctionality").equals("Modify")) {

				crudUIMaintenancePage.UpdatePagecountThreshold(hashmap);
				// validating the updated Document type and client product details
				crudUIMaintenancePage.validateUpdatedPagecountThreshold(hashmap);

			} else if (hashmap.get("PagecountThresholdFunctionality").equals("Delete")) {

				crudUIMaintenancePage.DeletePagecountThreshold(hashmap);
			} else if (hashmap.get("PagecountThresholdFunctionality").equals("Duplicate")) {

				crudUIMaintenancePage.createNewPagecountThreshold(hashmap);

			} else if (hashmap.get("PagecountThresholdFunctionality").equals("IncorrectValue")) {

				crudUIMaintenancePage.createNewPagecountThreshold(hashmap);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("TestManagePagecountThreshold is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
    @Ignore
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Pagination on the Manage Pagecount Threshold page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void TestPagination(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "TestPagination");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			crudUIMaintenancePage.validatePaginationForPagecountThreshold(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testPagination is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	
	@Test(priority = 1, groups = { "Level1",
	"Level2" }, testName = "Validate loan import if the record in Manage pagecount Threshold page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
public void testImportLoanPagecountThrehsold(ITestContext iTestContext, HashMap<String, String> hashmap) {
try {
	reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testImportLoanPagecountThrehsold");
	iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose

	if (!isSKVersionSet) {
		wfsMaintenancePage.checkAndAppendSkVersion();
		isSKVersionSet = true;
	}

	
	wfsMaintenancePage.createInputDirectory(hashmap);	
	wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
	indexingDashboardMaintenancePage.moveLoansToOtherVendor();
	
	MySQLDBConnection dbConnection = new MySQLDBConnection();
	
	int isRecordUpdated = dbConnection.updatePagecountThresholdByClientIDAndProductID(hashmap.get("ClientID"), hashmap.get("ProductID"), hashmap.get("MinPagecount"), hashmap.get("Maxpagecount"));
	if(isRecordUpdated ==1) {
		reportGenerator.logMessage("The database record has been updated successfully", Status.PASS);
	}
	
	userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.TOOLS,
			IdeaWFSConstants.UTILITIES,reportGenerator,"Utilities is clicked","createNewPagecountThreshold",Status.PASS,this.getListOfDrivers().get(this.getTestSuiteName()));
	
	userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.UTILITIES,
			IdeaWFSConstants.CLEAR_CACHE,reportGenerator,"Clear Cache is clicked","createNewPagecountThreshold",Status.PASS,this.getListOfDrivers().get(this.getTestSuiteName()));
	
	//Refresh page 
	loginPage.navigateToIdeaMainPage();
	wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
	String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
	int noOfLoans = Integer.parseInt(NoOfLoansToImport);
	int count;
	for (count = 1; count <= noOfLoans; count++) {
		wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
		hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
	}
	wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
	CommonUtils.sleepForAWhile(10000);
	for (count = 1; count <= noOfLoans; count++) {
		hashmap.put("LoanNumber", hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
		wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);

	}
	CommonUtils.sleepForAWhile(10000);
	for (int loans = 1; loans <= noOfLoans; loans++) {
		String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
		hashmap.put("LoanNumber", sLoanNumber);
		if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap) || dbConnection
				.pollAndCheckWorkFlowState(sLoanNumber, "FinishOCRW", "Unassigned")) {
			
			CommonUtils.sleepForAWhile(3000);
			wfsMaintenancePage.convertProblemLoanToVendorLoan(hashmap);
			
			reportGenerator.logMessage("The Loan is in Unassigned status", Status.PASS);
		} else {
			Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
		}
	}
	CommonUtils.sleepForAWhile(10000);
	SmartKeyLoginPage smartkeyLoginPage = null;
	for(count = 1; count <= noOfLoans; count++) {
		if(dbConnection.pollAndCheckWorkFlowState(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "MLAI", "Unassigned")) {
			Reporter.log("The workflow status is changed to Index UnAssigned");
		}else {
			Assert.fail("The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
		}
	}
	smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
	smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
	try {
		smartkeyLoginPage.selectRoleAndGetTask("MLAI");
		CommonUtils.sleepForAWhile();
//		smartkeyLoginPage.validateTheColors();
		for(count = 1; count <= noOfLoans; count++) {
			skOperationManager.completeMLAI(smartkeyLoginPage.getWebAppDriver());
				CommonUtils.sleepForAWhile();
			if(count==noOfLoans) {
				break;
			}
			smartkeyLoginPage.doClickOk();
		}
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.doSaveAndOK();
		CommonUtils.sleepForAWhile();
		smartkeyLoginPage.logoutSmartkey();
	} catch (Exception ex) {
		smartkeyLoginPage.logoutSmartkey();
		Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
	}
	
	
	
	
	CommonUtils.sleepForAWhile(10000);
	for (int loans = 1; loans <= noOfLoans; loans++) {
		String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
		hashmap.put("LoanNumber", sLoanNumber);
		if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap) || dbConnection
				.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
			reportGenerator.logMessage("The Loan is exported successfully", Status.PASS);
		} else {
			Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
		}
	}
	
	
} catch (Exception ex) {
	ex.printStackTrace();
	reportGenerator.logMessage(" Failed due to " + ex.getMessage(), Status.ERROR);
	TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
	Assert.fail("testImportLoanPagecountThrehsold " + ex.getMessage());
} finally {
	
	// Do release clean up clear
}
}


}