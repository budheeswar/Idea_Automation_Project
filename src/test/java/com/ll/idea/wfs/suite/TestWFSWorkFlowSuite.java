package com.ll.idea.wfs.suite;

import java.util.HashMap;
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
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.SeleniumUtils;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSVendorMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSWorkFlowMaintenancePage;

import junit.framework.Assert;

/**
 * This test suite to test user login, reset password, clear password, logout
 * functionality The class should be named starting with Test to follow naming
 * convention
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSWorkFlowSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	WFSWorkFlowMaintenancePage workFlowMaintenancePage = null;
	ReportGenerator reportGenerator = null;

	public TestWFSWorkFlowSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1" })
	public void doSetUp() throws Exception {
		/**
		 * Create sub directory under <code>/testdata</code> for every suite to
		 * segregate the test data CSV file, helps to manage with ease. For example,
		 * <code>user</code> is a sub test data folder to manage all csv files related
		 * to user module
		 */
		this.setTestSuiteName("user");
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1"})
	public void doBeforeMethod(ITestContext iTestContext) {
		this.initializeBrowser(iTestContext);
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		loginPage = new WFSLoginPage(this.getLocalDriver(), reportGenerator);
		homePage = new WFSHomePage(this.getLocalDriver(), reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.getLocalDriver(), reportGenerator);
		vendorMaintenancePage = new WFSVendorMaintenancePage(this.getLocalDriver(), reportGenerator);
		workFlowMaintenancePage = new WFSWorkFlowMaintenancePage(this.getLocalDriver(), reportGenerator);
	}

	@AfterMethod(alwaysRun = true, groups = { "Level1" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1" })
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		this.closeWebDriver(iTestContext);
	}

	/**
	 * This method checks vendor creation functionality. It covers the following
	 * Test cases such as 1)C891595-Add New workflow with valid workflow details
	 * 2)C891587-Verify Edit existing workflow
	 */
	@Test(priority = 4, groups = { 
			"Not In Scope" }, testName = "Test Work Flow Creation Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testWorkFlowCreation(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			loginPage.userLogin(hashmap.get("UserName"), hashmap.get("Password"));
			if (hashmap.get("WorkFlowFunctionality").equals("Create")) {
				if (workFlowMaintenancePage.createNewWorkFlow(hashmap)) {
					Assert.assertEquals(
							SeleniumUtils.getValue(this.getLocalDriver(), workFlowMaintenancePage.successMessage),
							hashmap.get("ExpectedResult"));
					workFlowMaintenancePage.doValidateCreatedWorkFlow(hashmap);
				}
			} else if (hashmap.get("WorkFlowFunctionality").equals("Modify")) {
				if (workFlowMaintenancePage.createNewWorkFlow(hashmap)) {
					Assert.assertEquals(
							SeleniumUtils.getValue(this.getLocalDriver(), workFlowMaintenancePage.successMessage),
							hashmap.get("ExpectedResult"));
					workFlowMaintenancePage.doValidateCreatedWorkFlow(hashmap);
					//edit workflow script need to write
				}
			}
			loginPage.userLogout();
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testWorkFlowCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

}
