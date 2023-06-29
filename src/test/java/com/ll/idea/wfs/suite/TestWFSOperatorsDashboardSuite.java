package com.ll.idea.wfs.suite;

import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
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
import com.ll.idea.sk.SKOperationManager;
import com.ll.idea.utils.CSVFileParser;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConnectToLinuxBox;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.MySQLDBConnection;
import com.ll.idea.utils.XMLFileHandler;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.SmartKeyLoginPage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSOperatorDashboardMaintenancePage;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import junit.framework.Assert;

public class TestWFSOperatorsDashboardSuite extends TestBase {

	WFSLoginPage loginPage = null;
	ReportGenerator reportGenerator = null;
	WFSMaintenancePage wfsMaintenancePage = null;
	ConnectToLinuxBox connecttoLinuxBox = null;
	XMLFileHandler xmlFileHandler = null;
	SKOperationManager skOperationManager = null;
	WFSOperatorDashboardMaintenancePage operationalDashboardMaintenancePage = null;
	CSVFileParser csvFileParser = null;
	HashMap<String, String> documentTypesMap = null;

	public TestWFSOperatorsDashboardSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1", "Level2" })
	public void doSetUp(ITestContext iTestContext) throws Exception {
		/**
		 * Create sub directory under <code>/testdata</code> for every suite to
		 * segregate the test data CSV file, helps to manage with ease. For example,
		 * <code>user</code> is a sub test data folder to manage all csv files related
		 * to user module
		 */
		this.setTestSuiteName("Tools");
		this.initializeBrowser(iTestContext);
		documentTypesMap = (HashMap<String, String>) IdeaMasterDataManager.loadDocumentTypesTable();
		loginPage = new WFSLoginPage(this.getListOfDrivers().get(this.getTestSuiteName()));
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1", "Level2" })
	public void doBeforeMethod(ITestContext iTestContext) throws Exception {
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		loginPage.setReportGenerator(reportGenerator);
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		connecttoLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
		operationalDashboardMaintenancePage = new WFSOperatorDashboardMaintenancePage(driver, reportGenerator);
		xmlFileHandler = new XMLFileHandler();
		skOperationManager = new SKOperationManager();
		csvFileParser = new CSVFileParser();

	}

	@AfterMethod(alwaysRun = true, groups = { "Level1", "Level2" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1", "Level2" })
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout();
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}

	/**
	 * This method checks the list of operators logged in to IDEA. It covers the
	 * following Test cases such as 1)C891688 - Verify List of Operators Logged into
	 * IDEA. 2)C891722 - Verify Logout and Release Work
	 */
	@Test(priority = 1, groups = {
			"Level1" }, testName = "Verify List of Operators Logged into IDEA", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testListOfOperatorsLoggedIn(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			// importing loans into wfs
			String sLoanNumber = dbConnection.checkIfAnySpecifiedLoanExists(hashmap.get("LoanStatus"), "Index",
					hashmap.get("ProcessingCenter"));

			if (sLoanNumber != null) {
				reportGenerator.logMessage("Loan already exists with required details", Status.PASS);
			} else {
				wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap, connecttoLinuxBox);
			}
			// list of operators logged into operation dashbaord
			operationalDashboardMaintenancePage.getListOfOperatorsLoggedIn(hashmap);
			// Unassigned. Complete human indexing using SK
			smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
			smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
			// list of operators logged into operation dashbaord
			operationalDashboardMaintenancePage.logOutOperatorFromWFSConsole(hashmap);
			CommonUtils.sleepForAWhile(5000);
			DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
			desktopCapabilities.setCapability("platformName", "Windows");
			desktopCapabilities.setCapability("deviceName", "WindowsPC");
			desktopCapabilities.setCapability("app", "Root");
			smartkeyLoginPage.selectRoleAndGetTask("Index");
			WindowsDriver<WindowsElement> desktopSession = new WindowsDriver<>(
					new URL(EnvironmentPropertyLoader.getPropertyByName("winapp.driver.listener.port")),
					desktopCapabilities);
			WebElement errorPopup = desktopSession.findElementByClassName("Static");
			String errorMessage = errorPopup.getAttribute("Name");
			boolean status = errorMessage.contains(hashmap.get("ExpectedErrorMessage"));
			Assert.assertTrue(status);
			reportGenerator.logAndCaptureScreen("As Expected Error pupup is displayed", "testListOfOperatorsLoggedIn",
					Status.INFO, desktopSession);
			CommonUtils.sleepForAWhile(5000);
			smartkeyLoginPage.doClickOk();
			smartkeyLoginPage.logoutSmartkey();

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testListOfOperatorsLoggedIn is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
}
