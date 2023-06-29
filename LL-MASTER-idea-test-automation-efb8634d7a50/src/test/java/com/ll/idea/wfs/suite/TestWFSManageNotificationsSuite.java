package com.ll.idea.wfs.suite;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSNotificationsMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSVendorMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSWorkFlowMaintenancePage;

/**
 * This test suite to test Event Notifications functionality.
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSManageNotificationsSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	WFSWorkFlowMaintenancePage workFlowMaintenancePage = null;
	WFSNotificationsMaintenancePage notificationsMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	HashMap<String, String> documentTypesMap = null;

	public TestWFSManageNotificationsSuite() {
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
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login.mike3"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1" })
	public void doBeforeMethod(ITestContext iTestContext) {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		homePage = new WFSHomePage(driver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(driver, reportGenerator);
		vendorMaintenancePage = new WFSVendorMaintenancePage(driver, reportGenerator);
		workFlowMaintenancePage = new WFSWorkFlowMaintenancePage(driver, reportGenerator);
		notificationsMaintenancePage = new WFSNotificationsMaintenancePage(driver, reportGenerator);

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

	/**
	 * This method sets users for getting event notifications. It covers the
	 * following Test cases such as 1)C891601-Set Users to get Event Notifications
	 */
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Assign users for notifications functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testAssignUsersForNotifications(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testAssignUsersForNotifications");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			// assign notifications to user.
			notificationsMaintenancePage.assignUserForNotifications(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testAssignUsersForNotifications is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

}
