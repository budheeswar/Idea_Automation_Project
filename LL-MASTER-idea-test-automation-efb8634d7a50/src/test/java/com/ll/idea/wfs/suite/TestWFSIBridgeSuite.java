package com.ll.idea.wfs.suite;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.Keys;
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
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.dataprovider.AutomationTestDataProvider;
import com.ll.idea.management.TestNGTestRailUploader;
import com.ll.idea.masterdata.manager.IdeaMasterDataManager;
import com.ll.idea.model.IdeaDocumentType;
import com.ll.idea.model.LoanValueObject;
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
import com.ll.idea.wfs.ui.page.WFSDashboardPage;
import com.ll.idea.wfs.ui.page.WFSIndexingDashboardMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSMaintenancePage;
import com.ll.idea.wfs.ui.page.WFSToolsMaintenancePage;

import io.appium.java_client.MobileBy.ByAccessibilityId;
import io.appium.java_client.windows.WindowsElement;
import junit.framework.Assert;

/**
 * Pre-conditions & considerations 1) OCR service (mock service) is up and
 * running 2) WinAppDriver is started and running 3) Making sure there is no
 * loans in Index or EIR state. Smartkey is always picking up the lined up loan
 * instead of specific loan number
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSIBridgeSuite extends TestBase {

	WFSLoginPage loginPage = null;
	WFSDashboardPage wfsDashboardPage = null;
	SmartKeyLoginPage smartKeyLoginPage = null;
	ReportGenerator reportGenerator = null;
	WFSMaintenancePage wfsMaintenancePage = null;
	ConnectToLinuxBox connecttoLinuxBox = null;
	SKOperationManager skOperationManager = null;
	CSVFileParser csvFileParser = null;
	XMLFileHandler xmlFileHandler = null;
	WFSIndexingDashboardMaintenancePage indexingDashboardMaintenancePage = null;
	HashMap<String, String> documentTypesMap = null;
	WFSToolsMaintenancePage wfsToolsMaintenancePage = null;
	public TestWFSIBridgeSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1", "Level2","Level13"})
	public void doSetUp(ITestContext iTestContext) throws Exception {
		/**
		 * Create sub directory under <code>/testdata</code> for every suite to
		 * segregate the test data CSV file, helps to manage with ease. For example,
		 * <code>user</code> is a sub test data folder to manage all csv files related
		 * to user module
		 */
		this.setTestSuiteName("IBridge");
		this.initializeBrowser(iTestContext);
		documentTypesMap = (HashMap<String, String>) IdeaMasterDataManager.loadDocumentTypesTable();
		WebDriver webDriver = this.getListOfDrivers().get(this.getTestSuiteName());
		loginPage = new WFSLoginPage(webDriver);
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(webDriver);
//		indexingDashboardMaintenancePage.moveLoansToOtherVendor();
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1", "Level2","Level13" })
	public void doBeforeMethod(ITestContext iTestContext) throws Exception {
		reportGenerator = new ReportGenerator();
		loginPage.setReportGenerator(reportGenerator);
		indexingDashboardMaintenancePage.setReportGenerator(reportGenerator);
		WebDriver driver = this.getListOfDrivers().get(this.getTestSuiteName());
		wfsDashboardPage = new WFSDashboardPage(driver, reportGenerator);
		wfsMaintenancePage = new WFSMaintenancePage(driver, reportGenerator);
		connecttoLinuxBox = new ConnectToLinuxBox(driver, reportGenerator);
		csvFileParser = new CSVFileParser();
		xmlFileHandler = new XMLFileHandler();
		skOperationManager = new SKOperationManager();
		wfsToolsMaintenancePage = new WFSToolsMaintenancePage(driver, reportGenerator);
	}

	@AfterMethod(alwaysRun = true, groups = { "Level1", "Level2","Level13" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1", "Level2","Level13" })
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout();
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}


	
	/**
	 * This test method covers below testcase 
	 * 1)C896739 - Make sure iBridge show new config "one.pass.de.workflow.process.ids" on change config page "Change Configuration Properties"
	 * 
	 */

	@Test(priority = 5, groups = { "Level1",
			"Level2" }, testName = "Make sure iBridge show new config one.pass.de.workflow.process.ids on change config page Change Configuration Properties", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testChangeConfigurationPropertyKey(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testChangeConfigurationPropertyKey");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			wfsMaintenancePage.checkConfigProperty(hashmap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testChangeConfigurationPropertyKey is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	
	
}
