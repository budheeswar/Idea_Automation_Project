package com.ll.idea.wfs.suite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
public class TestWFSAMLLWSuite extends TestBase {

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
	public TestWFSAMLLWSuite() {
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
		this.setTestSuiteName("AMLLW");
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
	 * 1)C897552 - Verify loans export without error with two new workflows
	 * 2)C898063 - Validate if data sent from AMLLW is persisted into ESPY_DOCUMENT table for new workflows
	 * 3)C898116 - Verify if AMLLW Automation payload has document with XML content
	 * 4)C898065 - Validate if documents are sent to Index/DE based on threshold values in the threshold tables
	 */

	@Test(priority = 5, groups = { "Level1",
			"Level2" }, testName = "Verify loans export without error with two new workflows", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testLoansExportWithOutErrorForNewWorkFlow(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testLoansExportWithOutErrorForNewWorkFlow");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose

			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			wfsMaintenancePage.modifyAndUpdateMultipleConfigValues(hashmap);
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			String[] sWorkFlowNames = {"ManagedAutomatedIndexingOnly.pdf","LoanWithOutMLAI.pdf"};
			for (count = 0; count < sWorkFlowNames.length; count++) {
				hashmap.put("LoanFileName",sWorkFlowNames[count]);
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
				int temp = count+1;
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(temp), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
			CommonUtils.sleepForAWhile(45000);
			HashMap<String,List<String>> map = new HashMap<String,List<String>>();
			map = indexingDashboardMaintenancePage.verifyAndAddLoansToParticularWorkflowList(hashmap);
			MySQLDBConnection dbConnection = new MySQLDBConnection();
				hashmap.put("LoanNumber", hashmap.get("LoanNumber1"));
				indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);
				String xmlPayLoad = dbConnection.retrieveAutomationPayload(hashmap.get("BatchID"));
				System.out.println(xmlPayLoad);
				Set<String> values = xmlFileHandler.validatePayLoadXML(hashmap, reportGenerator, xmlPayLoad);
				if (values.size() > 0) {
					if (values.contains("true")) {
						if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber1"), "MLAI", "Unassigned")) {
							Reporter.log("Loan is moved to MLAI UnAssigned state");
						} else {
							Assert.fail("Loan is not in MLAI UnAssigned state");
						}
					} else {
						Assert.fail("Loan is not in MLAI UnAssigned state");
					}
				} else {
					Assert.fail("Automation payload doesn't have document with XML content");
				}
				
				double calculatedConfidenceValue;
				double seperationalConfidenceValue;
				int thresholdSeprationValue;
				int thresholdClassificationValue;
				ArrayList<LoanValueObject> loanValueObjectList  = null;
				LoanValueObject loanValueObject = null;
				indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
				hashmap.put("LoanNumber", hashmap.get("LoanNumber2"));
				indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);
				loanValueObjectList = dbConnection.checkConfidenceValues(hashmap.get("BatchID"));
				for(int i = 0;i<loanValueObjectList.size();i++) {
					loanValueObject = loanValueObjectList.get(i);
					System.out.println();
					dbConnection.checkThreasholdSeprationAndClassificationValues(hashmap.get("ProductID"),loanValueObject.getMlDocumentTypeValue(),hashmap);
					calculatedConfidenceValue = Double.parseDouble(loanValueObject.getCalculatedConfidenceValue());
					seperationalConfidenceValue = Double.parseDouble(loanValueObject.getSeperationalConfidenceValue());
					thresholdSeprationValue = Integer.parseInt(hashmap.get("thresholdSeprationValue"));
					thresholdClassificationValue = Integer.parseInt(hashmap.get("thresholdClassificationValue"));
					if ((calculatedConfidenceValue >= thresholdClassificationValue)||(seperationalConfidenceValue >= thresholdSeprationValue)) {
						if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber2"), "Data Entry 1", "Unassigned")) {
							Reporter.log("The workflow status is changed to Data Entry 1 UnAssigned");
						} else {
							Assert.fail(
									"The workflow status is not yet changed to Data Entry 1 UnAssigned. Please check the error and make sure OCRW service is up and running");
						}
					} else {
						Assert.fail(
								"Loans are not moving to Data Entry state when Calculated confidence is greater than Threashold Classification Value");
					}
					
				}
				
				CommonUtils.sleepForAWhile();
				SmartKeyLoginPage smartkeyLoginPage = null;
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("MLAI");
					CommonUtils.sleepForAWhile();
					for (count = 1; count < noOfLoans; count++) {
						CommonUtils.sleepForAWhile();
						skOperationManager.completeMLAI(smartkeyLoginPage.getWebAppDriver());
						CommonUtils.sleepForAWhile(10000);
						if (count == noOfLoans) {
							break;
						}
						smartkeyLoginPage.doClickOk();
						CommonUtils.sleepForAWhile();
					}
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.doClickOk();
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
				}
				hashmap.put("LoanNumber", hashmap.get("LoanNumber1"));
				indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);
				loanValueObjectList = dbConnection.checkConfidenceValues(hashmap.get("BatchID"));
				boolean status = false;
				for(int i = 0;i<loanValueObjectList.size();i++) {
					loanValueObject = loanValueObjectList.get(i);
					dbConnection.checkThreasholdSeprationAndClassificationValues(hashmap.get("ProductID"),loanValueObject.getMlDocumentTypeValue(),hashmap);
					calculatedConfidenceValue = Double.parseDouble(loanValueObject.getCalculatedConfidenceValue());
					seperationalConfidenceValue = Double.parseDouble(loanValueObject.getSeperationalConfidenceValue());
					thresholdSeprationValue = Integer.parseInt(hashmap.get("thresholdSeprationValue"));
					thresholdClassificationValue = Integer.parseInt(hashmap.get("thresholdClassificationValue"));
					if ((calculatedConfidenceValue< thresholdClassificationValue)||(seperationalConfidenceValue < thresholdSeprationValue)) {
							Reporter.log("The workflow status is changed to MLAI UnAssigned");
							status = true;
							break;
					} 
				}
				Assert.assertTrue(status);
				Reporter.log("The workflow status is changed to MLAI UnAssigned");
				CommonUtils.sleepForAWhile(20000);
				
				indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
				List<String> loansInDEState = new ArrayList<String>();
				loansInDEState = map.get("managedAutomatedIndexing1PassDE");
				for (int temp = 0; temp < loansInDEState.size(); temp++) {
					if (dbConnection.pollAndCheckWorkFlowState(
							loansInDEState.get(temp), "Data Entry 1",
							"Unassigned")) {
						Reporter.log("The workflow status is changed to Data Entry 1 UnAssigned");
					} else {
						Assert.fail(
								"The workflow status is not yet changed to Data Entry 1 UnAssigned. Please check the error and make sure OCRW service is up and running");
					}
				}
				SmartKeyLoginPage smartkey = new SmartKeyLoginPage(reportGenerator);
				smartkey.loginToSmartkey(hashmap.get("Operator2Login"), hashmap.get("Operator2Password"));
				try {

					smartkey.selectRoleAndGetTask("Data Entry 1");
					CommonUtils.sleepForAWhile();
					HashMap<String, String> mockDataMap = (HashMap<String, String>) csvFileParser
							.getSmartkeyDEMockData(hashmap.get("DEMockDataCSV"));
					for (count = 0; count < loansInDEState.size(); count++) {
						int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
						int limit = numberOfWorkItemPages * loansInDEState.size();
						int temp = 0;
						while (numberOfWorkItemPages > 0) {
							CommonUtils.sleepForAWhile();
							skOperationManager.completeDataEntryFlow(smartkey.getWebAppDriver(), mockDataMap,
									reportGenerator);
							CommonUtils.sleepForAWhile(10000);
							numberOfWorkItemPages--;
							temp++;
							if (temp != limit) {
								smartkey.doClickOk();
							}
							CommonUtils.sleepForAWhile();
						}
					}
					CommonUtils.sleepForAWhile();
					smartkey.doClickOk();
					CommonUtils.sleepForAWhile();
					smartkey.logoutSmartkey();
				} catch (Exception ex) {
					smartkey.logoutSmartkey();
				}
				CommonUtils.sleepForAWhile();
				CommonUtils.sleepForAWhile();
				indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
				for (int loans = 1; loans <= noOfLoans; loans++) {
					String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
					hashmap.put("LoanNumber", sLoanNumber);
					if (indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap)
							|| dbConnection.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
						Reporter.log(
								"As Expected Loans Moved to Export Batch Completed status");
					} else {
						Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
					}
				
				}
			indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
			hashmap.put("LoanNumber", hashmap.get("LoanNumber1"));
				indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);
				if(xmlFileHandler.validateDetailsOfXMLPayLoad(hashmap, reportGenerator, xmlPayLoad)) {
					Reporter.log(
							"As Expected all the values are correctly updated to the PayLoad XML");
				}else {
					Assert.fail("Values are not updated correctly to the PayLoad XML");
				}
				String[] sLoansForDeletingRecords = {"MLAILoanForDelete.pdf","NonMLAILoanForDelete.pdf"};
				for (count = 0; count < sLoansForDeletingRecords.length; count++) {
					hashmap.put("LoanFileName",sLoansForDeletingRecords[count]);
					wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
					int temp = count+1;
					hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(temp), hashmap.get("LoanNumber"));
				}
				wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
				wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);	
				CommonUtils.sleepForAWhile(35000);
				for (count = 1; count <= sLoansForDeletingRecords.length; count++) {
					hashmap.put("LoanNumber", hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
					wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
				}
				
				indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
				hashmap.put("LoanNumber", hashmap.get("LoanNumber1"));
				indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);
				loanValueObjectList = dbConnection.checkConfidenceValues(hashmap.get("BatchID"));
			for (int i = 0; i < loanValueObjectList.size(); i++) {
				loanValueObject = loanValueObjectList.get(i);
				System.out.println();
				if (dbConnection.deleteRecordFromAutoindexThreasholdtable(hashmap.get("ProductID"), "1889")) {
					dbConnection.checkThreasholdSeprationAndClassificationValues(hashmap.get("ProductID"),
							loanValueObject.getMlDocumentTypeValue(), hashmap);
					calculatedConfidenceValue = Double.parseDouble(loanValueObject.getCalculatedConfidenceValue());
					seperationalConfidenceValue = Double.parseDouble(loanValueObject.getSeperationalConfidenceValue());
					thresholdSeprationValue = Integer.parseInt(hashmap.get("PropertyValue"));
					thresholdClassificationValue = Integer.parseInt(hashmap.get("PropertyValue"));
					if ((calculatedConfidenceValue >= thresholdClassificationValue)
							|| (seperationalConfidenceValue >= thresholdSeprationValue)) {
						if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber1"), "Data Entry 1",
								"Unassigned")) {
							Reporter.log("The workflow status is changed to Data Entry 1 UnAssigned");
							break;
						} else {
							Assert.fail(
									"The workflow status is not yet changed to Data Entry 1 UnAssigned. Please check the error and make sure OCRW service is up and running");
						}
					} else {
						Assert.fail(
								"Loans are not moving to Data Entry state when Calculated confidence is greater than Threashold Classification Value");
					}
				}
			}
				
			hashmap.put("LoanNumber", hashmap.get("LoanNumber2"));
			indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);
			loanValueObjectList = dbConnection.checkConfidenceValues(hashmap.get("BatchID"));
			if (loanValueObjectList.size() >= 1) {
				for (int i = 0; i < loanValueObjectList.size(); i++) {
					loanValueObject = loanValueObjectList.get(i);
					System.out.println();
					if (dbConnection.deleteRecordFromAutoindexThreasholdtable(hashmap.get("ProductID"), "653")) {
						dbConnection.checkThreasholdSeprationAndClassificationValues(hashmap.get("ProductID"),
								loanValueObject.getMlDocumentTypeValue(), hashmap);
						calculatedConfidenceValue = Double.parseDouble(loanValueObject.getCalculatedConfidenceValue());
						seperationalConfidenceValue = Double
								.parseDouble(loanValueObject.getSeperationalConfidenceValue());
						thresholdSeprationValue = Integer.parseInt(hashmap.get("PropertyValue"));
						thresholdClassificationValue = Integer.parseInt(hashmap.get("PropertyValue"));
						if ((calculatedConfidenceValue < thresholdClassificationValue)
								|| (seperationalConfidenceValue < thresholdSeprationValue)) {
							if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber2"), "Index",
									"Unassigned")) {
								Reporter.log("The workflow status is changed to Index UnAssigned");
								break;
							} else {
								Assert.fail(
										"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
							}
						} else {
							Assert.fail(
									"Loans are not moving to Index state when Calculated confidence is less than Threashold Classification Value");
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testLoansExportWithOutErrorForNewWorkFlow is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	
	/**
	 * This test method covers below testcase 
	 * 1)C898067 - Validate data persistence into tables if there are changes to doctype in MLAI
	 */

	@Test(priority = 5, groups = { "Level13",
			"" }, testName = "Validate data persistence into tables if there are changes to doctype in MLAI", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testDataPersistenceInMLAI(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testDataPersistenceInMLAI");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
//			if (!isSKVersionSet) {
//				wfsMaintenancePage.checkAndAppendSkVersion();
//				isSKVersionSet = true;
//			}
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			CommonUtils.sleepForAWhile(35000);
			for (count = 1; count <= noOfLoans; count++) {
				hashmap.put("LoanNumber", hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			}
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			for (count = 1; count <= noOfLoans; count++) {
				if (dbConnection.pollAndCheckWorkFlowState(
						hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "MLAI", "Unassigned")) {
					Reporter.log("The workflow status is changed to Index UnAssigned");
				} else {
					Assert.fail(
							"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
				}
			}
			smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
			smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
			try {
				smartkeyLoginPage.selectRoleAndGetTask("MLAI");
				CommonUtils.sleepForAWhile();
				WindowsElement tableLayout1 = smartkeyLoginPage.getWebAppDriver()
						.findElementByAccessibilityId("tableLayoutPanel1");
				WindowsElement documentDropDown = (WindowsElement) tableLayout1
						.findElement(ByAccessibilityId.AccessibilityId("comboBox1"));
				
				CommonUtils.sleepForAWhile();
				documentDropDown.sendKeys(hashmap.get("DocumentToReplace"));
				CommonUtils.sleepForAWhile();
				for (count = 1; count <= 14; count++) {
					documentDropDown.sendKeys(Keys.ENTER);
				}
				CommonUtils.sleepForAWhile(10000);
				smartkeyLoginPage.doClickOk();
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.logoutSmartkey();
			} catch (Exception ex) {
				smartkeyLoginPage.logoutSmartkey();
				Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
			}
			CommonUtils.sleepForAWhile();
			indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
			hashmap.put("LoanNumber", hashmap.get("LoanNumber1"));
			indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);

			System.out.println(hashmap.get("BatchID"));
			System.out.println(hashmap.get("ReplaceDocumentBy"));
			System.out.println(hashmap.get("DocumentIDToBeReplaced"));
			
			if (dbConnection.checkReplacedDocumentInESPYDocument(hashmap.get("BatchID"),
					hashmap.get("ReplaceDocumentBy"), hashmap.get("DocumentIDToBeReplaced"))) {
				Reporter.log("EIR Document type ID is successfully updated for the document that user updated");
			} else {
				Assert.fail("EIR Document type ID is not updated for the document that user have updated");
			}

			smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
			smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
			try {
				smartkeyLoginPage.selectRoleAndGetTask("MLAI");
				CommonUtils.sleepForAWhile();
				WindowsElement tableLayout1 = smartkeyLoginPage.getWebAppDriver()
						.findElementByAccessibilityId("tableLayoutPanel1");
				WindowsElement documentDropDown = (WindowsElement) tableLayout1
						.findElement(ByAccessibilityId.AccessibilityId("comboBox1"));
				documentDropDown.sendKeys(Keys.ENTER);
				documentDropDown.sendKeys(Keys.ENTER);
				documentDropDown.sendKeys(hashmap.get("ReplaceFollowingPageBy"));
				for (count = 1; count <= 12; count++) {
					documentDropDown.sendKeys(Keys.ENTER);
				}
				CommonUtils.sleepForAWhile(10000);
				smartkeyLoginPage.doClickOk();
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.logoutSmartkey();
			} catch (Exception ex) {
				smartkeyLoginPage.logoutSmartkey();
				Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
			}

			indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
			hashmap.put("LoanNumber", hashmap.get("LoanNumber2"));
			indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);

			int replacedRecord = dbConnection.checkReplacedDocumentupdatedInESPYDocument(hashmap.get("BatchID"),
					hashmap.get("ReplacedDocumentIDForFollowingPage"));
			if (replacedRecord >= 1) {
				Reporter.log("Replaced Document is displayed in ESPY document");
			} else {
				Assert.fail("Replaced Document is not displayed in ESPY document");
			}

			int NoOfRecords = dbConnection.checkNoOfRecordsInESPYDocument(hashmap.get("BatchID"));
			if (NoOfRecords > Integer.parseInt(hashmap.get("NoOfDocuments"))) {
				Reporter.log("No of documents within the loan is increased by 1");
			} else {
				Assert.fail("No of documents within the loan is increased by 1");
			}

			smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
			smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
			try {
				smartkeyLoginPage.selectRoleAndGetTask("MLAI");
				CommonUtils.sleepForAWhile();
				WindowsElement tableLayout1 = smartkeyLoginPage.getWebAppDriver()
						.findElementByAccessibilityId("tableLayoutPanel1");
				WindowsElement documentDropDown = (WindowsElement) tableLayout1
						.findElement(ByAccessibilityId.AccessibilityId("comboBox1"));
				documentDropDown.sendKeys(hashmap.get("FollowingPage"));
				for (count = 1; count <= 14; count++) {
					documentDropDown.sendKeys(Keys.ENTER);
				}
				CommonUtils.sleepForAWhile(10000);
				smartkeyLoginPage.doClickOk();
				CommonUtils.sleepForAWhile(10000);
				smartkeyLoginPage.doClickOk();
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.logoutSmartkey();
			} catch (Exception ex) {
				smartkeyLoginPage.logoutSmartkey();
				Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
			}

			indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
			hashmap.put("LoanNumber", hashmap.get("LoanNumber3"));
			indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap);

			int replacedDocumentRecord = dbConnection.checkReplacedDocumentupdatedInESPYDocument(hashmap.get("BatchID"),
					hashmap.get("DocumentIDToBeReplaced"));
			if (replacedDocumentRecord <= 0) {
				Reporter.log("Document is updated to Following page in ESPY document");
			} else {
				Assert.fail("Document is not updated to Following page in ESPY document");
			}

			int recordsSize = dbConnection.checkNoOfRecordsInESPYDocument(hashmap.get("BatchID"));
			if (recordsSize < Integer.parseInt(hashmap.get("NoOfDocuments"))) {
				Reporter.log("No of documents within the loan is reduced by 1");
			} else {
				Assert.fail("No of documents within the loan is reduced by 1");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testDocumentsBehaviourBasedOnThreasholdValues is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
}
