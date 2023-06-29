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
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.SeleniumUtils;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSVendorMaintenancePage;
import junit.framework.Assert;

/**
 * This test suite to test create new vendors,edit existing vendor and vendor users functionality.
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSVendorSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	HashMap<String, String> documentTypesMap = null;
	public TestWFSVendorSuite() {
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
		vendorMaintenancePage = new WFSVendorMaintenancePage(driver, reportGenerator);
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
	 * This method checks vendor creation functionality and edit existing vendor functionality.
	 *  It covers the following Test cases such as 
	 * 1)C891599-Create New vendor with valid vendor name and description
	 * 2)C891621-Verify Edit existing vendor
	 */
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Vendors Creation Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testVendorsCreation(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testVendorsCreation");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			if (hashmap.get("VendorFunctionality").equals("Create")) {
				//Creating New vendor with valid vendor name and description
				vendorMaintenancePage.createNewVendor(hashmap);
				Assert.assertEquals(SeleniumUtils.getValue(driver, vendorMaintenancePage.successMessage),
						hashmap.get("ExpectedResult"));
				// valiating created vendor name and description
				vendorMaintenancePage.doValidateCreatedVendor(hashmap);
			}else if (hashmap.get("VendorFunctionality").equals("Modify")) {
				//Creating New vendor with valid vendor name and description
				vendorMaintenancePage.createNewVendor(hashmap);
				Assert.assertEquals(SeleniumUtils.getValue(driver, vendorMaintenancePage.successMessage),
						hashmap.get("ExpectedResult"));
				// valiating created vendor name and description
				vendorMaintenancePage.doValidateCreatedVendor(hashmap);
				// updating vendor details
				vendorMaintenancePage.updateVendor(hashmap);
				// valiating created vendor details
				vendorMaintenancePage.doValidateCreatedVendor(hashmap);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testVendorsCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * This method checks vendors users functionality. It covers the following Test
	 * cases such as 
	 * 1)C891624-Verify Vendor's users functionality
	 */
	@Test(priority = 2, groups = {
			"Level1" }, testName = "Test Vendor Users Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testVendorUsersFunctionality(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testVendorUsers");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			// creating new vendor
			vendorMaintenancePage.createNewVendor(hashmap);
			Assert.assertEquals(SeleniumUtils.getValue(driver, vendorMaintenancePage.successMessage),
					hashmap.get("ExpectedResult"));
			// updating vendor users deatils
			vendorMaintenancePage.validateUpdatedVendorUsers(hashmap);
			} catch (Exception ex) {
				ex.printStackTrace();
				reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
				TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
				Assert.fail("testVendorUsersFunctionality is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
}
