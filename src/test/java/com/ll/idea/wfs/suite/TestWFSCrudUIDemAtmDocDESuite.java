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
import com.ll.idea.wfs.ui.page.WFSCrudUIDemAtmDocDEMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;
import junit.framework.Assert;

/**
 * This suite is to test create, edit, delete dem_automation_document_de entry
 * through UI.
 * 
 * @author Nagapandyan N
 *
 */
public class TestWFSCrudUIDemAtmDocDESuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	WFSCrudUIDemAtmDocDEMaintenancePage crudUIMaintenancePage = null;
	ReportGenerator reportGenerator = null;
	HashMap<String, String> documentTypesMap = null;

	public TestWFSCrudUIDemAtmDocDESuite() {
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
		crudUIMaintenancePage = new WFSCrudUIDemAtmDocDEMaintenancePage(driver, reportGenerator);
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
	 * This method checks data insert/update/delete into Dem Automation Document De
	 * table from UI It covers the following Test cases such as 1)C907374 - Verify
	 * if the Manage dem_automation_document_de feature is available under in
	 * Security Management 2)C907375 - Create new Manage dem_automation_document_de
	 * details with correct format 3)C907376 - Verify View/Edit functionality for
	 * search Manage dem_automation_document_de 4)C907386 - Validate if Manage Dem
	 * Automation Document De table is created in the database 5)C907516 - Create
	 * new Manage dem_automation_document_de details with incorrect format 6)C907377
	 * - Verify Delete functionality for Manage dem_automation_document_de 7)C907668
	 * - Verify if Duplicate entries not allow in Manage Dem Automation Document De
	 */
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Dem Automation Document De CRUD Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void TestManageDemAutomationDocumentDe(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testManageNewDemAutomationDocumentDe");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Create")) {
				// Creating New Dem Automation Document De with document type id and client
				// product id
				crudUIMaintenancePage.createNewDemAutomationDocumentDe(hashmap);
				Assert.assertTrue(SeleniumUtils.getValue(driver, crudUIMaintenancePage.successMessage)
						.contains(hashmap.get("ExpectedResult")));
				// validating the created documenttypeID, client productID into the dem
				// automation Document De table
				crudUIMaintenancePage.doValidateCreatedDemAutDocDERecord(hashmap);
			} else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Modify")) {

				crudUIMaintenancePage.UpdateDemAutomationDocumentDe(hashmap);
				// validating the updated Document type and client product details
				crudUIMaintenancePage.validateUpdatedDemAutomationDocumentDe(hashmap);
			} else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Delete")) {

				crudUIMaintenancePage.DeleteDemAutomationDocumentDe(hashmap);
			} else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Duplicate")) {

				crudUIMaintenancePage.createNewDemAutomationDocumentDe(hashmap);
			} else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("IncorrectValue")) {

				crudUIMaintenancePage.createNewDemAutomationDocumentDe(hashmap);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("TestManageDemAutomationDocumentDe is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	@Test(priority = 1, groups = {
			"Level1" }, testName = "Test Pagination on the Manage Dem Automation Document De page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void TestPagination(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "TestPagination");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			crudUIMaintenancePage.validatePagination(hashmap);

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testPagination is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
}
