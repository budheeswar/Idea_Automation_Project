package com.ll.idea.wfs.suite;

import java.util.HashMap;
import java.util.Vector;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ll.idea.dataprovider.AutomationTestDataProvider;
import com.ll.idea.management.TestNGTestRailUploader;
import com.ll.idea.masterdata.manager.IdeaMasterDataManager;
import com.ll.idea.model.IdeaDocumentType;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.sk.SKOperationManager;
import com.ll.idea.utils.CSVFileParser;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.MySQLDBConnection;
import com.ll.idea.utils.XMLFileHandler;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.SmartKeyLoginPage;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSIndexingDashboardMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSQCSummaryDashboardMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSRoleMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSVendorMaintenancePage;

import junit.framework.Assert;

/**
 * This test suite to test user login, reset password, clear password, logout
 * functionality The class should be named starting with Test to follow naming
 * convention
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSQCSummaryDashBoardSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	WFSRoleMaintenancePage roleMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	WFSIndexingDashboardMaintenancePage IndexingDashboardMaintenancePage = null;
	WFSQCSummaryDashboardMaintenancePage qcsummaryDashboardMaintenancePage = null;
	WFSMaintenancePage wfsMaintenancePage = null;
	ConnectToLinuxBox connecttoLinuxBox = null;
	static boolean isSKVersionSet = false;
	HashMap<String, String> documentTypesMap = null;
	CSVFileParser csvFileParser = null;
	SKOperationManager skOperationManager = null;
	XMLFileHandler xmlFileHandler = null;
	WFSIndexingDashboardMaintenancePage indexingDashboardMaintenancePage = null;
	
	public TestWFSQCSummaryDashBoardSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1","Level12" })
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
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(this.getListOfDrivers().get(this.getTestSuiteName()));
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
		indexingDashboardMaintenancePage.moveLoansToOtherVendor();
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1","Level12"})
	public void doBeforeMethod(ITestContext iTestContext) {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		homePage = new WFSHomePage(driver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(driver, reportGenerator);
		vendorMaintenancePage = new WFSVendorMaintenancePage(driver, reportGenerator);
		roleMaintenancePage = new WFSRoleMaintenancePage(driver, reportGenerator);
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		indexingDashboardMaintenancePage.setReportGenerator(reportGenerator);
		qcsummaryDashboardMaintenancePage = new WFSQCSummaryDashboardMaintenancePage(driver, reportGenerator);
		connecttoLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
		csvFileParser = new CSVFileParser();
		xmlFileHandler = new XMLFileHandler();
		skOperationManager = new SKOperationManager();
	}

	@AfterMethod(alwaysRun = true, groups = { "Level1","Level12" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		 TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1","Level12"})
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout();
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}

	/**
	 * This method checks quality control summary dashboard page.
	 * It covers the following Test cases such as 
	 * 1)891726 - Verify Quality Control Dashboard - Summary.
	 * 2)C891721 - Verify Import QC Loan and Documents Selection By Loan
	 */
	@Test(priority = 1, groups = {
			"Level1"}, testName = "Verify Quality Control Dashboard - Summary page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testQualityControlSummaryDashboardPage(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testQualityControlSummaryDashboardPage");
			iTestContext.setAttribute("testDataMap", hashmap); 
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser.getDocumentTypes(hashmap, documentTypesMap);
			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap,connecttoLinuxBox);
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Index", "Unassigned")) {
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("Index");
					CommonUtils.sleepForAWhile(5000);
					if (skOperationManager.completeIndexing(smartkeyLoginPage.getWebAppDriver(), documentTypeVector,reportGenerator)) {
						CommonUtils.sleepForAWhile(5000);
						smartkeyLoginPage.doSaveAndOK();
					}
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
				}
			} else {
				Assert.fail(
						"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
			}
			CommonUtils.sleepForAWhile();
			
			wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			qcsummaryDashboardMaintenancePage.selectQcImportedLoan(hashmap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testQualityControlSummaryDashboardPage is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
}
