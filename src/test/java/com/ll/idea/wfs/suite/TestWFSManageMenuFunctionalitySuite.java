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
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSManageMenuMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSResourcesMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSVendorMaintenancePage;
import junit.framework.Assert;

/**
 * This test suite to test Resources creation functionality and edit existing resources functionality.
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSManageMenuFunctionalitySuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSVendorMaintenancePage vendorMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	WFSResourcesMaintenancePage resourceMaintenance = null;
	WFSManageMenuMaintenancePage manageMenuMaintenance = null;
	HashMap<String, String> documentTypesMap = null;
	
	public TestWFSManageMenuFunctionalitySuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1"})
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
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login.mike1"),
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
		resourceMaintenance = new WFSResourcesMaintenancePage(driver, reportGenerator);
		manageMenuMaintenance = new WFSManageMenuMaintenancePage(driver, reportGenerator);
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
	 * This method checks Addnew menu creation functionality and edit existing menu functionality.
	 * It covers the following Test cases such as 
	 * 1)C891593 - Add New menu with valid details.
	 * 2)C891592 - Verify Edit existing menu.
	 * 3)C891662 - Verify Remove functionality for existing resources
	 */
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Manage Menu Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testManageMenuFunctionality(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testManageMenuFunctionality");
			iTestContext.setAttribute("testDataMap", hashmap);
			if (hashmap.get("ResourcesFunctionality").equals("Create")) {
				// crearting new resource
				manageMenuMaintenance.createNewMenu(hashmap);
				//validating created resource
				manageMenuMaintenance.doValidateCreatedNewMenu(hashmap);
			}else if (hashmap.get("ResourcesFunctionality").equals("Modify")) {
				// crearting new resource
				manageMenuMaintenance.createNewMenu(hashmap);
				//validating created resource
				manageMenuMaintenance.doValidateCreatedNewMenu(hashmap);
				//updating created resource
				manageMenuMaintenance.doUpdateCreatedMenu(hashmap);
				//validating created resource
				manageMenuMaintenance.doValidateCreatedNewMenu(hashmap);
			
			}else if (hashmap.get("ResourcesFunctionality").equals("ChangeRole")) {
				// crearting new resource
				manageMenuMaintenance.createNewMenu(hashmap);
				//validating created resource
				manageMenuMaintenance.doValidateCreatedNewMenu(hashmap);
				//updating created resource
				manageMenuMaintenance.doUpdateCreatedMenu(hashmap);
				//validating created resource
				manageMenuMaintenance.doValidateCreatedNewMenu(hashmap);
				//updating created resource
				manageMenuMaintenance.doUpadteRoleToCreatedMenu(hashmap);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testResourcesCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	
}
