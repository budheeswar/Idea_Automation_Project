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
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoanBatchStatusMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
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
public class TestWFSLoanBatchStatusSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	WFSLoanBatchStatusMaintenancePage loanBatchStatusMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	ConnectToLinuxBox connecttoLinuxBox = null;
	WFSMaintenancePage wfsMaintenancePage = null;

	public TestWFSLoanBatchStatusSuite() {
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
		this.setTestSuiteName("LoanBatchStatus");
		this.initializeBrowser(iTestContext);
		loginPage = new WFSLoginPage(this.getListOfDrivers().get(this.getTestSuiteName()));
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login.mike5"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1" })
	public void doBeforeMethod(ITestContext iTestContext) {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		loginPage.setReportGenerator(reportGenerator);
		homePage = new WFSHomePage(driver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(driver, reportGenerator);
		vendorMaintenancePage = new WFSVendorMaintenancePage(driver, reportGenerator);
		loanBatchStatusMaintenancePage = new WFSLoanBatchStatusMaintenancePage(driver, reportGenerator);
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		connecttoLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
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
	 * This method checks Loan Batch Status. It covers the following Test cases such
	 * as 1)C891682-Check status using valid Loan Batch Id
	 */
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Check status using valid Loan Batch Id", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testLoanBatchStatus(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testLoanBatchStatus");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap, connecttoLinuxBox);
			loanBatchStatusMaintenancePage.checkLoanBatchStatus(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testLoanBatchStatus is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * This method checks Loan Batch Status. It covers the following Test cases such
	 * as 1)C891683-Check LoanBatch Status using alphabets 2)C891684-Check LoanBatch
	 * Status using special characters 3)C891685-Check LoanBatch Status using null
	 * or empty search
	 */
	@Test(priority = 2, groups = {
			"Level1" }, testName = "Check LoanBatch Status using Negative scenarios", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testLoanBatchStatusNegativeScenario(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testLoanBatchStatus");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			loanBatchStatusMaintenancePage.checkLoanBatchStatusNegativeScenarios(hashmap);
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed to validate user login due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testLoanBatchStatusNegativeScenario is failed");
		} finally {
			// Do release clean up clear
		}
	}
}
