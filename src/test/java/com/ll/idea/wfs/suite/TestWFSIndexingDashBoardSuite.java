package com.ll.idea.wfs.suite;

import java.util.HashMap;

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
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSIndexingDashboardMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSRoleMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSToolsMaintenancePage;
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
public class TestWFSIndexingDashBoardSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	WFSRoleMaintenancePage roleMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	WFSIndexingDashboardMaintenancePage indexingDashboardMaintenancePage = null;
	WFSMaintenancePage wfsMaintenancePage = null;
	ConnectToLinuxBox connecttoLinuxBox = null;
	static boolean isSKVersionSet = false;
	HashMap<String, String> documentTypesMap = null;
	WFSToolsMaintenancePage wfsToolsMaintenancePage = null;

	public TestWFSIndexingDashBoardSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1", "Level12" })
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
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login.mike4"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1", "Level12" })
	public void doBeforeMethod(ITestContext iTestContext) {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		homePage = new WFSHomePage(driver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(driver, reportGenerator);
		vendorMaintenancePage = new WFSVendorMaintenancePage(driver, reportGenerator);
		roleMaintenancePage = new WFSRoleMaintenancePage(driver, reportGenerator);
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		connecttoLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(driver, reportGenerator);
		wfsToolsMaintenancePage = new WFSToolsMaintenancePage(driver, reportGenerator);
	}

	@AfterMethod(alwaysRun = true, groups = { "Level1", "Level12" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1", "Level12" })
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout();
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}

	/**
	 * This method checks Edit Functionality in indexing Dashboard page. It covers
	 * the following Test cases such as 1)C891707-Verify Edit functionality in
	 * Indexing Dashboard page. 2)C891691 - Filter Loans using Batch Search
	 * functionality. 3)C891687 - Verify Filters to display the loans in Indexing
	 * Dashboard page.4)C891715 - Verify Do NOT Show functionality. 5)C891716 - Verify Show Loans functionality.
	 */
	@Test(priority = 1, groups = {
			"Level1","Level12" }, testName = "Verify Edit functionality in Indexing Dashboard page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testBatchSearchFuntionality(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testBatchSearchFuntionality");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			// importing loans into wfs
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap, connecttoLinuxBox);
			// edit functionality in indexing dashboard
			indexingDashboardMaintenancePage.editFunctionalityIndexingDashboardPage(hashmap);
			// batchsearch functionality in indexing dashboard
			indexingDashboardMaintenancePage.batchSearchFunctionality(hashmap);
			//do not show functionality in Indexing dashboard page
			indexingDashboardMaintenancePage.checkDoNotShowFunctionalityInIndexingPage(hashmap);

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testBatchSearchFuntionality is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * This method checks Set Flag Functionality in indexing Dashboard page. It
	 * covers the following Test cases such as 1)C891690 - Verify if "Set Flag"
	 * button is appearing & clickable in the Indexing administrative Dashboard.
	 * 2)C891692 - Verify Set Flag functionality. 3)C891693 - Verify if the flags
	 * are set as per the selection. 4)C891725 - Verify Administrative Dashboard -
	 * Summary
	 */
	@Test(priority = 2, groups = {
			"Level1" }, testName = "Verify Set Flag functionality in Indexing Dashboard page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testSetFlagFuntionalityInIndexingPage(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testSetFlagFuntionalityInIndexingPage");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			// importing loans into wfs
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap, connecttoLinuxBox);
			// validating set falg functionality in indexing dashbaord page
			indexingDashboardMaintenancePage.setflagFunctionalityIndexingDashboardPage(hashmap);
			indexingDashboardMaintenancePage.summaryOfImportedLoans(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testSetFlagFuntionalityInIndexingPage is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
}
