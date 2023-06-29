package com.ll.idea.wfs.suite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaEFSConstants;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.dataprovider.AutomationTestDataProvider;
import com.ll.idea.management.TestNGTestRailUploader;
import com.ll.idea.model.LoanValueObject;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.MySQLDBConnection;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSIndexingDashboardMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSToolsMaintenancePage;
import junit.framework.Assert;

public class TestWFSToolsSuite  extends TestBase {
	
	WFSLoginPage loginPage = null;
	ReportGenerator reportGenerator = null;
	WFSMaintenancePage wfsMaintenancePage = null;
	WFSToolsMaintenancePage wfsToolsMaintenancePage = null;
	WFSHomePage homePage = null;
	ConnectToLinuxBox connectToLinuxBox = null;
	WFSIndexingDashboardMaintenancePage indexingDashboardMaintenancePage = null;
//	HashMap<String, String> documentTypesMap = null;
	public TestWFSToolsSuite() {
		super();
	}
	
	@BeforeSuite(alwaysRun = true, groups = { "Level1","Level12","Level13"})
	public void doSetUp(ITestContext iTestContext) throws Exception {
		/**
		 * Create sub directory under <code>/testdata</code> for every suite to
		 * segregate the test data CSV file, helps to manage with ease. For example,
		 * <code>user</code> is a sub test data folder to manage all csv files related
		 * to user module
		 */
		this.setTestSuiteName("Tools");
//		documentTypesMap = (HashMap<String, String>) IdeaMasterDataManager.loadDocumentTypesTable();
		this.initializeBrowser(iTestContext);
		loginPage = new WFSLoginPage(this.getListOfDrivers().get(this.getTestSuiteName()));
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
										EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}
	
	@BeforeMethod(alwaysRun = false, groups = { "Level1", "Level12","Level13"})
	public void doBeforeMethod(ITestContext iTestContext) throws Exception {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		loginPage.setReportGenerator(reportGenerator);
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		homePage = new WFSHomePage(driver, reportGenerator);
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(driver, reportGenerator);
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		wfsToolsMaintenancePage = new WFSToolsMaintenancePage(driver, reportGenerator);
		connectToLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
	}

	@AfterMethod(alwaysRun = true, groups = { "Level1", "Level12","Level13"})
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1", "Level12","Level13"})
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout(); 
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}

	/*
	 * testModifyAndUpdateConfigurationProperties to verify the configuration change
	 * through tools --> change config properties Different config parameters can be
	 * modfied and verified through input CSV.
	 * 1)C891696 - Verify Change Configuration Properties
	 */
	@Test(priority = 1, groups = {"Level1"}, testName = "testModifyAndUpdateConfigurationProperties", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testModifyAndUpdateConfigurationProperties(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"),
					"testModifyAndUpdateConfigurationProperties");
			Reporter.log("[Thread - testModifyAndUpdateConfigurationProperties]" + Thread.currentThread().getName()
					+ "[DateTime]" + new Date());
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.modifyAndUpdateConfig(hashmap);
			if (hashmap.get("PropertyKey").contains("SK")) {
				isSKVersionSet = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testModifyAndUpdateConfigurationProperties is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	
	/*
	 * testmethod is used to create input directory in efs.
	 * Test case included in this are
	 * 1)C891697 - Verify Create Input Directory
	 * 2)C891778 - Verify Clear Cache
	 */
	@Test(priority = 2, groups = {"Level1"}, testName = "testCreateInputDirectory", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testCreateInputDirectory(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"),
					"testCreateInputDirectory");
			Reporter.log("[Thread - testCreateInputDirectory]" + Thread.currentThread().getName()
					+ "[DateTime]" + new Date());
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.clearCache();
			wfsMaintenancePage.createInputDirectory(hashmap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testCreateInputDirectory is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	/* 
	 * This method is used to verify scheduler management functionality
	 * This method covers below testcases such as 
	 * 1)C891779: Verify Scheduler Management
	 * 2)C891780: Verify Listener Management
	 */
	@Test(priority = 3, groups = {"Level1"}, testName = "Verify Scheduler And Listener Management", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testSchedulerAndListenerManagement(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"),
					"testSchedulerAndListenerManagement");
			Reporter.log("[Thread - testSchedulerManagement]" + Thread.currentThread().getName()
					+ "[DateTime]" + new Date());
			iTestContext.setAttribute("testDataMap", hashmap); 
			wfsToolsMaintenancePage.verifySchedulerManagement(hashmap);
			wfsToolsMaintenancePage.verifyListenerManagement(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testSchedulerAndListenerManagement is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	/**
	 * C891686 - Verify Auto-Scheduled Import Profile
	 * C892247 - Verify IDEA console display existing AI profile correctly.
	 * C892241 - Verify Split By Company checkbox not visible if client has no company AI profile.
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 4, groups = {"Level1"}, testName = "Verify Auto-Scheduled Import Profile", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testAutoScheduledImportProfile(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testAutoScheduledImportProfile");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			connectToLinuxBox.executeCommand(hashmap,EnvironmentPropertyLoader.getPropertyByName("autoimporter_host"),
					EnvironmentPropertyLoader.getPropertyByName("user"),IdeaEFSConstants.CMD_TO_RESTART_AUTOIMPORTOR,EnvironmentPropertyLoader.getPropertyByName("privatekeypath"));
			String sLoanPdfPath = connectToLinuxBox.copyAndRenameFile(hashmap, reportGenerator.getReportPath());
			String sRemoteDir = IdeaWFSConstants.MANGLAR_BASE_OUTPUT_DIR + hashmap.get("ClientID") + "."
														+ hashmap.get("ProductID") + "_" + hashmap.get("ClientName") + "." + hashmap.get("ProductName");
			connectToLinuxBox.transferLoanInToEFS(EnvironmentPropertyLoader.getPropertyByName("wfs_server_host"),
														EnvironmentPropertyLoader.getPropertyByName("user"), sRemoteDir,
														EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), sLoanPdfPath);
			CommonUtils.sleepForAWhile(15000);
			wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			wfsToolsMaintenancePage.checkExistingAIProfileCorrectlyDisplayed(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testAutoScheduledImportProfile is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	
	/**
	 * C891724 - Verify Manage Auto-Scheduled Import Profile - Company
	 * C892246 - Verify AI display updated SPLIT_BY_COMPANY value for existing client profile after re-logging.
	 * C892242 - Verify Split By Company checkbox is visible if client has company AI profile configured.
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {"Level1"}, testName = "Verify Manage Auto-Scheduled Import Profile - Company", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testAutoScheduledImportProfileCompany(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testAutoScheduledImportProfileCompany");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createCompanyProfile(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			connectToLinuxBox.executeCommand(hashmap,EnvironmentPropertyLoader.getPropertyByName("autoimporter_host"),
					EnvironmentPropertyLoader.getPropertyByName("user"),IdeaEFSConstants.CMD_TO_RESTART_AUTOIMPORTOR,EnvironmentPropertyLoader.getPropertyByName("privatekeypath"));
			String sLoanPdfPath = connectToLinuxBox.copyAndRenameFile(hashmap, reportGenerator.getReportPath());
			String sRemoteDir = IdeaWFSConstants.MANGLAR_BASE_OUTPUT_DIR + hashmap.get("ClientID") + "."
														+ hashmap.get("ProductID") + "_" + hashmap.get("ClientName") + "." + hashmap.get("ProductName");
			connectToLinuxBox.transferLoanInToEFS(EnvironmentPropertyLoader.getPropertyByName("wfs_server_host"),
														EnvironmentPropertyLoader.getPropertyByName("user"), sRemoteDir,
														EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), sLoanPdfPath);
			CommonUtils.sleepForAWhile(45000);
			wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			loginPage.userLogout();
			loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
					EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
			wfsToolsMaintenancePage.checkSplitByCompanyInAIProfile(hashmap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testAutoScheduledImportProfileCompany is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	
	/**
	 * C891679 - Enable Advanced Logging in View Log page
	 * C891680 - Disable Advanced Logging in View Log page
	 * C891681 - Download log
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {"Level1" }, testName = "Enable Advanced Logging in View Log page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testEnableAdvancedLogging(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testEnableAdvancedLogging");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsToolsMaintenancePage.selectEnableAdvancedLogging(hashmap);
			wfsToolsMaintenancePage.verifyLogDownloadFunctionality(hashmap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testEnableAdvancedLogging is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	
	/**
	 * C891698 - Verify Edit functionality in "Force Descriptor to Review" page
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {"Level1", "Test"}, testName = "Verify Edit functionality in Force Descriptor to Review page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testForceSingleDescriptorToReview(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testForceSingleDescriptorToReview");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			String descriptorName = hashmap.get("DiscriptorName");
			wfsToolsMaintenancePage.createDiscriptorInForceDescriptorToReviewPage(hashmap, descriptorName);
			wfsToolsMaintenancePage.editFunctionalityInForceDescriptorToReviewPage(hashmap);	
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testForceSingleDescriptorToReview is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	
	/**
	 * C891700 - Verify editing multiple data functionality in "Force Descriptor to Review" page.
	 * C891699 - Verify Delete functionality in "Force Descriptor to Review" page.
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {"Level1"}, testName = "Verify Edit Multiple data functionality in Force Descriptor to Review page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testMultipleDataFunctionalityInForceDescriptor(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testMultipleDataFunctionalityInForceDescriptor");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			for(int count =1; count<=2; count++) {
//				String descriptorName = hashmap.get("DiscriptorName");
				String descriptorName = hashmap.get("DiscriptorName"+String.valueOf(count));
			wfsToolsMaintenancePage.createDiscriptorInForceDescriptorToReviewPage(hashmap, descriptorName);
			hashmap.put("DescriptorID"+String.valueOf(count), hashmap.get("descriptorid"));
			}
			wfsToolsMaintenancePage.editFunctionalityInMultipleDescriptors(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testMultipleDataFunctionalityInForceDescriptor is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	/**
	 * C891704 - Verify Action functionality in "Force Descriptor to Review" page.
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {"Level1"}, testName = "Verify Action functionality in Force Descriptor to Review page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testActionFunctionalityInForceDescriptor(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testActionFunctionalityInForceDescriptor");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			for(int count =1; count<=2; count++) {
				String descriptorName = hashmap.get("DiscriptorName"+String.valueOf(count));
			wfsToolsMaintenancePage.createDiscriptorInForceDescriptorToReviewPage(hashmap, descriptorName);
			hashmap.put("DescriptorID"+String.valueOf(count), hashmap.get("descriptorid"));
			}
			wfsToolsMaintenancePage.actionFunctionalityInDescriptorsRevewPage(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testActionFunctionalityInForceDescriptor is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	/*
	 * test method is to Verify Simple Loan Import. It covers the following test
	 * 1)C891598 -Verify Simple Loan Import
	 * 2)C891799 - Verify editing workflow in Simple Loan Import page
	 * 3)C891673 - Verify Set Flag in Simple Loan Import page
	 * 4)C891694 - Verify Clear functionality
	 * 5)C891695 - Verify Clear functionality when Client is not selected by the user
	 */
	@Test(priority = 5, groups = { "Level1"
			 }, testName = "Verify Create and Simple Loan Import", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testCreateSimpleLoanImport(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testCreateSimpleLoanImport");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			indexingDashboardMaintenancePage.clearFunctionalityInSimpleLoanImportPage(hashmap);
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap, connectToLinuxBox);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed to validate user login due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testCreateSimpleLoanImport is failed");
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * C891777 - Verify Auto Import Profile - Batch Update.
	 * 
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {
			"Level13" }, testName = "Verify Auto Import Profile - Batch Update", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testAutoImportProfileBatchUpdate(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testAutoImportProfileBatchUpdate");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.checkAutoImportProfileBatchUpdate(hashmap);
			wfsToolsMaintenancePage.clientProfilesBatchUpdate(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testAutoImportProfileBatchUpdate is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}

	/**
	 * C894666 - Verify AI has DB entries in idea_db_211 while importing loans.
	 * 
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {
			"Level1" }, testName = "Verify AI has DB entries in idea_db_211 while importing loans", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testDBEntriesForAIProfile(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testDBEntriesForAIProfile");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connectToLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connectToLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connectToLinuxBox);
			CommonUtils.sleepForAWhile(25000);
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			for (count = 1; count <= noOfLoans; count++) {
				hashmap.put("LoanNumber", hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
				if (dbConnection.pollAndCheckWorkFlowState(
						hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "Index", "Unassigned")) {
					Reporter.log("The workflow status is changed to Index UnAsigned");
					String loanNumber = hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count));
					ArrayList<LoanValueObject> loanValueObjectList = null;
					LoanValueObject loanValueObject = null;
					loanValueObjectList = dbConnection.getLoanDetails(loanNumber);
					loanValueObject = loanValueObjectList.get(0);
					String batchid = loanValueObject.getLoanBatchId();
					if (hashmap.get("BatchID").equals(batchid)) {
						reportGenerator.logMessage(
								"DB entries are happening for AI profile for the loan"
										+ hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)),
								Status.PASS);
					} else {
						Assert.fail("DB Entries are not reflecting for AI Profile");
					}
				} else {
					Assert.fail(
							"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testDBEntriesForAIProfile is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}

	/**
	 * C894364 - Verify AI splits incoming loans based on profile priority for same
	 * client & product.
	 * 
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 5, groups = {
			"Level1" }, testName = "Verify AI splits incoming loans based on profile priority for same client & product", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testWorkflowSplitRatioAsPerAIProfile(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testWorkflowSplitRatioAsPerAIProfile");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connectToLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfileHandlingMultipleWorkFlows(hashmap,
					hashmap.get("NoOfFlowsForProfile1"), hashmap.get("ProfilePriority1"));
			hashmap.put("WorkFlow1", hashmap.get("WorkFlow3"));
			hashmap.put("Percentage1", hashmap.get("Percentage3"));
			hashmap.put("Priority1", hashmap.get("Priority3"));
			wfsToolsMaintenancePage.createAutoScheduledImportProfileHandlingMultipleWorkFlows(hashmap,
					hashmap.get("NoOfFlowsForProfile2"), hashmap.get("ProfilePriority2"));
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connectToLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connectToLinuxBox);
			CommonUtils.sleepForAWhile(25000);
			HashMap<String, List<String>> map = new HashMap<String, List<String>>();
			map = indexingDashboardMaintenancePage.verifyAndAddLoansToParticularWorkflowList(hashmap);
			List<String> loansInStandardWithEIRFlow = new ArrayList<String>();
			List<String> loansInonePassAutomatedDE = new ArrayList<String>();
			List<String> loansInIndexOnlyWithEIR = new ArrayList<String>();
			loansInStandardWithEIRFlow = map.get("standardWithEIR");
			loansInonePassAutomatedDE = map.get("onePassAutomatedDE");
			loansInIndexOnlyWithEIR = map.get("IndexOnlyWithEIR");
			if (hashmap.get("ProfilePriority1").equals("1")) {
				Assert.assertEquals(loansInonePassAutomatedDE.size(),
						Integer.parseInt((hashmap.get("Percentage1"))) / 10);
				reportGenerator.logMessage("As expected all the loans are allocated to OnePassAutomated DE",
						Status.PASS);
				Assert.assertEquals(loansInIndexOnlyWithEIR.size(),
						Integer.parseInt((hashmap.get("Percentage2"))) / 10);
				reportGenerator.logMessage("As expected all the loans are allocated to IndexOnlyWithEIR", Status.PASS);
			} else {
				Assert.assertEquals(loansInStandardWithEIRFlow.size(), noOfLoans);
				reportGenerator.logMessage("As expected all the loans are allocated to StandardWithEIR", Status.PASS);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testWorkflowSplitRatioAsPerAIProfile is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}

	/**
	 * 1) C892248 - Verify existing client splits are not impacted when the column
	 * 'SPLIT_BY_COMPANY' is false. 2) C892245 - Verify AI reads the column
	 * 'SPLIT_BY_COMPANY' every time it re-reads changed profiles 3) C892243 -
	 * Verify checkbox in UI reflects status of the column SPLIT_BY_COMPANY in DB
	 * 4)C892225 - Verify split by company working as expected during AI profile creation
	 * 5)C892244 - Verify date modified is updated when check box status is changed in AI profile
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 4, groups = { "Level1"
			 }, testName = "Verify existing client splits are not impacted when the column 'SPLIT_BY_COMPANY' is false", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testClientSplitsFunctionalitywithSplitByCompany(ITestContext iTestContext,
			HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"),
					"testClientSplitsFunctionalitywithSplitByCompany");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connectToLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createCompanyProfile(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfileWithPriority(hashmap);
			wfsMaintenancePage.clearCache();
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(IdeaWFSConstants.DATE_YYYYMMdd);
			String sExpectedDate= dateFormat.format(date); 
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connectToLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connectToLinuxBox);
			CommonUtils.sleepForAWhile(45000);
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			HashMap<String, List<String>> map = new HashMap<String, List<String>>();
			map = indexingDashboardMaintenancePage.verifyAndAddLoansToParticularWorkflowList(hashmap);
			List<String> loansInAutomatedDEFlow = new ArrayList<String>();
			List<String> loansInStandardWithEIRFlow = new ArrayList<String>();
			loansInAutomatedDEFlow = map.get("onePassAutomatedDE");
			loansInStandardWithEIRFlow = map.get("standardWithEIR");
			int automatedDEWorkLoansSize;
			int standardWithEIRLoansSize;
			String sSplitByCompany = dbConnection.checkSplitByCompany(hashmap.get("ClientID"),
					hashmap.get("ProductID"));
			if (hashmap.get("SplitByCompany").equalsIgnoreCase(IdeaWFSConstants.BOOLEAN_FALSE)) {
				automatedDEWorkLoansSize = Integer.parseInt(hashmap.get("FirstPercentage")) / noOfLoans;
				standardWithEIRLoansSize = Integer.parseInt(hashmap.get("SecondPercentage")) / noOfLoans;
				Assert.assertEquals(hashmap.get("SplitByCompanyValue"), sSplitByCompany);
				reportGenerator.logMessage("As expected split by company value is displayed as " + sSplitByCompany,
						Status.PASS);
			} else {
				automatedDEWorkLoansSize = Integer.parseInt(hashmap.get("CompanyFirstPercentage")) / noOfLoans;
				standardWithEIRLoansSize = Integer.parseInt(hashmap.get("CompanySecondPercentage")) / noOfLoans;
				Assert.assertEquals(hashmap.get("SplitByCompanyValue"), sSplitByCompany);
				reportGenerator.logMessage("As expected split by company value is displayed as " + sSplitByCompany,
						Status.PASS);
			}
			Assert.assertEquals(automatedDEWorkLoansSize, loansInAutomatedDEFlow.size());
			reportGenerator.logMessage(
					"As expected " + automatedDEWorkLoansSize + " loans moved to" + hashmap.get("FirstWorkflowName"),
					Status.PASS);
			Assert.assertEquals(standardWithEIRLoansSize, loansInStandardWithEIRFlow.size());
			reportGenerator.logMessage(
					"As expected " + standardWithEIRLoansSize + " loans moved to" + hashmap.get("SecondWorkflowName"),
					Status.PASS);
			String sActualUpdatedDate = dbConnection.checkSplitByCompanyDate(hashmap.get("ClientID"),
					hashmap.get("ProductID"));
			if(sActualUpdatedDate.contains(sExpectedDate)) {
				reportGenerator.logMessage(
						"As expected Date if getting updated for Split By Company Functionality"+sActualUpdatedDate,
						Status.PASS);
			}else {
				Assert.fail("As expected Date if getting updated for Split By Company Functionality");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testClientSplitsFunctionalitywithSplitByCompany is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}

	/**
	 * C894709 - Verify AI move loans to PageCountExceeded vendor for loans
	 * exceeding the page limit
	 * 
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 4, groups = {"Level1"
			 }, testName = "Verify AI move loans to PageCountExceeded vendor for loans exceeding the page limit", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testAIMoveLoansToPageCountExceededVendor(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"),
					"testAIMoveLoansToPageCountExceededVendor");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsToolsMaintenancePage.createNewLoanImportPageAllocation(hashmap);
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			String pageCount = dbConnection.checkIfAnySpecifiedLoanProduct(hashmap.get("ProductID"));
			int maxPageCount = Integer.parseInt(pageCount);
			Assert.assertEquals(maxPageCount, Integer.parseInt(hashmap.get("Count")));
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connectToLinuxBox);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				hashmap.put("LoanFileName", hashmap.get("LoanFileName" + String.valueOf(count)));
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connectToLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connectToLinuxBox);
			CommonUtils.sleepForAWhile(35000);
			for (count = 1; count <= noOfLoans; count++) {
				hashmap.put("LoanNumber", hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			}
			reportGenerator.logMessage("Vendor[PageCountExceeded] is displayed in dashboard", Status.PASS);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testAutoScheduledImportProfile is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
	/**
	 * C894603 - Verify AI profile trigger email when AI import duplicate loans
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 4, groups = {"Level1" }, testName = "Verify AI profile trigger email when AI import duplicate loans", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testAIImportDuplicateLoans(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testAIImportDuplicateLoans");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			connectToLinuxBox.executeCommand(hashmap,EnvironmentPropertyLoader.getPropertyByName("autoimporter_host"),
					EnvironmentPropertyLoader.getPropertyByName("user"),IdeaEFSConstants.CMD_TO_RESTART_AUTOIMPORTOR,EnvironmentPropertyLoader.getPropertyByName("privatekeypath"));
			String srcFileName = System.getProperty("user.dir") + File.separator
					+ EnvironmentPropertyLoader.getPropertyByName("sourcepath")+File.separator+hashmap.get("LoanFileName");
			String sRemoteDir = IdeaWFSConstants.MANGLAR_BASE_OUTPUT_DIR + hashmap.get("ClientID") + "."
														+ hashmap.get("ProductID") + "_" + hashmap.get("ClientName") + "." + hashmap.get("ProductName");
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
			connectToLinuxBox.transferLoanInToEFS(EnvironmentPropertyLoader.getPropertyByName("wfs_server_host"),
														EnvironmentPropertyLoader.getPropertyByName("user"), sRemoteDir,
														EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), srcFileName);
			String []loanNumber=hashmap.get("LoanFileName").split("\\.");
			hashmap.put(IdeaWFSConstants.LOAN_NUMBER ,loanNumber[0] );
			
			CommonUtils.sleepForAWhile(15000);
			wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			reportGenerator.logMessage("Vendor[DuplicateLoans] is displayed in dashboard ", Status.PASS);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testAIImportDuplicateLoans is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up if any
		}
	}
}
