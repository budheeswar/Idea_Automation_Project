package com.ll.idea.wfs.suite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

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
public class TestWFSEndToEndWorkFlowsSuite extends TestBase {

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
	public TestWFSEndToEndWorkFlowsSuite() {
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
		this.setTestSuiteName("WFS");
		this.initializeBrowser(iTestContext);
		documentTypesMap = (HashMap<String, String>) IdeaMasterDataManager.loadDocumentTypesTable();
		WebDriver webDriver = this.getListOfDrivers().get(this.getTestSuiteName());
		loginPage = new WFSLoginPage(webDriver);
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
				EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
		indexingDashboardMaintenancePage = new WFSIndexingDashboardMaintenancePage(webDriver);
		indexingDashboardMaintenancePage.moveLoansToOtherVendor();
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1", "Level2","Level13" })
	public void doBeforeMethod(ITestContext iTestContext) throws Exception {
		// Do instantiate required object for this test suite
//		documentTypesMap = (HashMap<String, String>) IdeaMasterDataManager.loadDocumentTypesTable();
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
	 * C898530 - Verify WFS with ManagedAutomatedIndexingOnly workflow using SmartKey
	 */

	@Test(priority = 1, groups = { "Level1",
			"Level2","" }, testName = "Verify WFS with ManagedAutomatedIndexingOnly workflow using SmartKey", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testManagedAutomatedIndexingOnly(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testManagedAutomatedIndexingOnly");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser
					.getDocumentTypes(hashmap, documentTypesMap);

			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
			CommonUtils.sleepForAWhile(25000);
			for (count = 1; count <= noOfLoans; count++) {
				hashmap.put("LoanNumber", hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)));
				wfsMaintenancePage.verifyLoanStateAndStatus(hashmap);
			}
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			for(count = 1; count <= noOfLoans; count++) {
				if(dbConnection.pollAndCheckWorkFlowState(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "MLAI", "Unassigned")) {
					Reporter.log("The workflow status is changed to Index UnAssigned");
				}else {
					Assert.fail(
							"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
				}
			}
			smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
			smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
			try {
				smartkeyLoginPage.selectRoleAndGetTask("MLAI");
				CommonUtils.sleepForAWhile();
//				smartkeyLoginPage.validateTheColors();
				for(count = 1; count <= noOfLoans; count++) {
					skOperationManager.completeMLAI(smartkeyLoginPage.getWebAppDriver());
						CommonUtils.sleepForAWhile();
					if(count==noOfLoans) {
						break;
					}
					smartkeyLoginPage.doClickOk();
				}
						CommonUtils.sleepForAWhile();
						smartkeyLoginPage.doSaveAndOK();
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.logoutSmartkey();
			} catch (Exception ex) {
				smartkeyLoginPage.logoutSmartkey();
				Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
			}
			CommonUtils.sleepForAWhile(20000);
			for (int loans = 1; loans <= noOfLoans; loans++) {
				String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
				hashmap.put("LoanNumber", sLoanNumber);
				if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap) || dbConnection
						.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
					String xmlDir = CommonUtils.getXMLDirPath(hashmap);
					String sPDFDir = CommonUtils.getPDFDirPath(hashmap);

					connecttoLinuxBox.downloadXmlFileFromEFS(
							EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
							EnvironmentPropertyLoader.getPropertyByName("user"),
							EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
							reportGenerator.getReportPath(), sPDFDir);

					xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
				} else {
					Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testManagedAutomatedIndexingOnly is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * As to simulate the scenario, SK will mark default indexing like Miscellaneous
	 * document type = 1099 document type there by Human Index would not be matched
	 * with MLLW
	 * 
	 * @param iTestContext
	 * @param hashmap
	 */
	@Test(priority = 2, groups = {"Level13","MismatchEIR" }, testName = "Verify one pass automated DE with index not matched with MLLW and Human Indexing"
						, dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void	testOnePassAutomatedDEAndEIR(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testOnePassAutomatedDEAndEIR");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser.getDocumentTypes(hashmap, documentTypesMap);
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			//Create input direct if not found and import the loan through simple loan input method
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap,connecttoLinuxBox);
			// launch Smartkey and reterive the task with index role
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			// Unassigned. Complete human indexing using SK
			if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Index", "Unassigned")) {
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("Index");
					CommonUtils.sleepForAWhile(5000);
				} catch(Exception ex) {
					//Handle the exception
					Assert.fail("Failed to retrieve the loan using Index role" + ex.getMessage());
				}
			}
			String actualDocName = hashmap.get("ActualDocName");
			String humanMismatchDocName = hashmap.get("HumanMismatchDocName");
			String whoIsCorrect = hashmap.get("WhoIsCorrect");
			//Indexing but matching with ML
			try {
				if (skOperationManager.completeIndexingNotMatchingML(smartkeyLoginPage.getWebAppDriver(), documentTypeVector,actualDocName,humanMismatchDocName)) {
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.doSaveAndOK();
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} else {
					Assert.fail("testOnePassAutomatedDEAndEIR is failed at indexing ");
				}
			} catch(Exception ex) {
				Assert.fail("testOnePassAutomatedDEAndEIR is failed at indexing ");
			}
			//Completing EIR
			if(wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)|| dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "EIR", "Unassigned")) {
				try {
					smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
					smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator2Login"), hashmap.get("Operator2Password"));
					smartkeyLoginPage.selectRoleAndGetTask("EIR");
					CommonUtils.sleepForAWhile(5000);
					if(!skOperationManager.completeEIR(smartkeyLoginPage.getWebAppDriver(),documentTypeVector, actualDocName, whoIsCorrect, reportGenerator)) {
						Assert.fail("Failed to complete EIR");
					}
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch(Exception ex) {
					//Handle the exception
					Assert.fail("Failed to retrieve the loan using EIR role" + ex.getMessage());
				}
			}
			if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)|| dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Export Batch", "Completed")) {
					String xmlDir  = CommonUtils.getXMLDirPath(hashmap);
					String sPDFDir = CommonUtils.getPDFDirPath(hashmap); 
					connecttoLinuxBox.downloadXmlFileFromEFS(EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
							EnvironmentPropertyLoader.getPropertyByName("user"),
							EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
							reportGenerator.getReportPath(), sPDFDir);

					xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
				} else {
					Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
				}
		}catch(Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testOnePassAutomatedDEAndEIR is failed due to " + ex.getMessage());
		}	finally {
			//Release most significant resources if any
		}
	}
	
	/**
	 * testOnePassAutomatedDE is a test method to verify end to end work flow for
	 * One Pass Automated DE. Steps followed 1) Created client and product directory
	 * in WFS server. Directory path in WFS server "/mnt/manglerdata1/output/<Client
	 * id>.<Product id>" 2) Transfer pre-defined loan PDF into client output /client
	 * id. product id directory 3) Import the loan using Simple loan import option
	 * through WFS console 4) Verify the loan is imported and visible through Index
	 * Dashboard --> Last 24 hours 5) Filter the loan number & verify the loan state
	 * and status of the loan 5.1 Managing the loan if(loan state = Index,loan
	 * status = Unassigned) i) login into SK with BPO operator having Index role
	 * ii)Retrieve the loan by clicking on Get & Save iii) Read the sqeuence and
	 * classification of documents from DataManager Cache and index the documents
	 * accordingly in SK iii) Index the loan documents & save the documents 5.2
	 * Verify the loan state and status in Dashboard either using UI or SQL DB Call
	 * 5.2 Expected that the loan should have exported & verify the batch and
	 * document type details in the XML file
	 * 
	 */
	@Test(priority = 3, groups = { "Level1","Testing","MismatchE","Level2" }, testName = "Verify WFS with OnePassAutomatedDE workflow using smart key", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testOnePassAutomatedDE(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testOnePassAutomatedDE");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser.getDocumentTypes(hashmap, documentTypesMap);
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap,connecttoLinuxBox);
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			// Unassigned. Complete human indexing using SK
			if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Index", "Unassigned")) {
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("Index");
					CommonUtils.sleepForAWhile(5000);
					if (skOperationManager.completeIndexing(smartkeyLoginPage.getWebAppDriver(), documentTypeVector,reportGenerator)) {
						CommonUtils.sleepForAWhile(9000);
						smartkeyLoginPage.doSaveAndOK();
					}
					CommonUtils.sleepForAWhile(9000);
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					// Assuming that SK login would be successful as long as Windows Driver is up
					// and running. Handling logout if there is unexpected error
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
				}
			} else {
				Assert.fail(
						"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
			}
			CommonUtils.sleepForAWhile();
			// ML Indexing and Human Indexing is expected to match. Hence the loan is
			// qualified for export. loan status = Export Batch and Status = Completed
			if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)
					|| dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Export Batch", "Completed")) {
				HashSet<String> uSoftValue = dbConnection.checkUSOFTField(hashmap.get("BatchID"));
				Assert.assertTrue(uSoftValue.size()==1);
				if(uSoftValue.contains(hashmap.get("ExpectedUSOFTValue"))) {
					Reporter.log("The USOFT values for the Particular batch ID displayed correctly");
				}else {
					Assert.fail("The USOFT values for the Particular batch ID not displayed correctly");
				}
				String xmlDir  = CommonUtils.getXMLDirPath(hashmap);
				String sPDFDir = CommonUtils.getPDFDirPath(hashmap); 
				
				connecttoLinuxBox.downloadXmlFileFromEFS(EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
						EnvironmentPropertyLoader.getPropertyByName("user"),
						EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
						reportGenerator.getReportPath(), sPDFDir);

				xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
			} else {
				Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testOnePassAutomatedDE is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * testStandardWithEIR is a test method to verify end to end work flow for
	 * Standard EIR workflow. Steps followed 1) Crfeated client and product
	 * directory in WFS server. Directory path in WFS server
	 * "/mnt/manglerdata1/output/<Client id>.<Product id>" 2) Transfer pre-defined
	 * loan PDF into client output /client id. product id directory 3) Import the
	 * loan using Simple loan import option through WFS console 4) Verify the loan
	 * is imported and visible through Index Dashboard --> Last 24 hours 5) Filter
	 * the loan number & verify the loan state and status of the loan
	 */
	@Test(priority = 4, groups = {"Level1",
			"StdEIR" }, testName = "Verify standard with EIR with index matched with MLLW and Humand Indexing", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testStandardWithEIR(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testStandardWithEIR");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser.getDocumentTypes(hashmap, documentTypesMap);
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.importLoanIntoWFS(iTestContext, hashmap,connecttoLinuxBox);
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			SmartKeyLoginPage smartkeyLoginPage = null;
			
			if (dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Index", "Unassigned")) {
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				//Indexing - Expected to Match with ML
				try {
					smartkeyLoginPage.selectRoleAndGetTask("Index");
					CommonUtils.sleepForAWhile(5000);
					if (skOperationManager.completeIndexing(smartkeyLoginPage.getWebAppDriver(),
							documentTypeVector,reportGenerator)) { CommonUtils.sleepForAWhile(5000);
							smartkeyLoginPage.doSaveAndOK();
					}
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					// Assuming that SK login would be successful as long as Windows Driver is up
					// and running. Handling logout if there is unexpected error
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Failed at Indexing ... " + ex.getStackTrace());
				}
			}
			//Data Entry gets completed by System and Data Entry 2 will be done QC operator
			if(wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)|| dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Data Entry 2", "Unassigned")) {
				try {
					smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
					smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator2Login"), hashmap.get("Operator2Password"));
					smartkeyLoginPage.selectRoleAndGetTask("Data Entry 2");
					CommonUtils.sleepForAWhile(5000);
					HashMap<String,String> mockDataMap = (HashMap<String,String>)csvFileParser.getSmartkeyDEMockData(hashmap.get("DEMockDataCSV"));
					int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
					while(numberOfWorkItemPages>0) {
						skOperationManager.completeDataEntry(smartkeyLoginPage.getWebAppDriver(),mockDataMap,reportGenerator);
						numberOfWorkItemPages--;
						if(numberOfWorkItemPages>0) {
							skOperationManager.clickGetAndSave(smartkeyLoginPage.getWebAppDriver());
						}
					}
					smartkeyLoginPage.doSave();
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch(Exception ex) {
					//Handle the exception
					ex.printStackTrace();
					Assert.fail("Failed at Data Entry 2 ..." + ex.getMessage());
				}
			}
			//Data Review stage
			if(wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)|| dbConnection.pollAndCheckWorkFlowState(hashmap.get("LoanNumber"), "Data Review", "Unassigned")) {
				try {
					smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
					smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator3Login"), hashmap.get("Operator3Password"));
					smartkeyLoginPage.selectRoleAndGetTask("Data Review");
					CommonUtils.sleepForAWhile(5000);
					int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
					while(numberOfWorkItemPages>0) {
						skOperationManager.completeDataReview(smartkeyLoginPage.getWebAppDriver(), hashmap, reportGenerator);
						smartkeyLoginPage.doUlike2Save();
						numberOfWorkItemPages--;
					}
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch(Exception ex) {
					//Handle the exception
					ex.printStackTrace();
					Assert.fail("Failed at Data Entry 2 ..." + ex.getMessage());
				}
			}
			
			CommonUtils.sleepForAWhile(5000);
			//Data Export
			
			if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)) {
				String xmlDir  = CommonUtils.getXMLDirPath(hashmap);
				String sPDFDir = CommonUtils.getPDFDirPath(hashmap); 
				
				connecttoLinuxBox.downloadXmlFileFromEFS(EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
						EnvironmentPropertyLoader.getPropertyByName("user"),
						EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
						reportGenerator.getReportPath(), sPDFDir);

				xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
			} else {
				Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed to validate user login due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testStandardWithEIR is failed" + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	/**
	 * This test method covers below testcase C898532 - Verify WFS with 50/50
	 * workflow split for new IA workflows
	 */

	@Test(priority = 5, groups = { "Level1",
			"Level2" }, testName = "Verify WFS with 50/50 workflow split for new IA workflows", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void test50And50SplitforNewWorkFlows(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "test50And50SplitforNewWorkFlows");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser
					.getDocumentTypes(hashmap, documentTypesMap);

			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
			CommonUtils.sleepForAWhile(20000);
			HashMap<String,List<String>> map = new HashMap<String,List<String>>();
			map = indexingDashboardMaintenancePage.verifyAndAddLoansToParticularWorkflowList(hashmap);
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
				for (count = 1; count <= noOfLoans; count++) {
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
				smartkeyLoginPage.doSaveAndOK();
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.logoutSmartkey();
			} catch (Exception ex) {
				smartkeyLoginPage.logoutSmartkey();
				Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
			}
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
				HashMap<String, String> mockDataMap = (HashMap<String, String>) csvFileParser
						.getSmartkeyDEMockData(hashmap.get("DEMockDataCSV"));
				for (count = 0; count < loansInDEState.size(); count++) {
					int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
					int limit = numberOfWorkItemPages * loansInDEState.size();
					int temp = 0;
					while (numberOfWorkItemPages > 0) {
						CommonUtils.sleepForAWhile(3000);
						skOperationManager.completeDataEntryFlow(smartkey.getWebAppDriver(), mockDataMap,
								reportGenerator);
						CommonUtils.sleepForAWhile(2000);
						numberOfWorkItemPages--;
						temp++;
						if (temp != limit) {
							smartkey.doClickOk();
						}
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
			indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
			for (int loans = 1; loans <= noOfLoans; loans++) {
				String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
				hashmap.put("LoanNumber", sLoanNumber);
				if (indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap)
						|| dbConnection.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
					String xmlDir = CommonUtils.getXMLDirPath(hashmap);
					String sPDFDir = CommonUtils.getPDFDirPath(hashmap);

					connecttoLinuxBox.downloadXmlFileFromEFS(
							EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
							EnvironmentPropertyLoader.getPropertyByName("user"),
							EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
							reportGenerator.getReportPath(), sPDFDir);

					xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
				} else {
					Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("test50And50SplitforNewWorkFlows is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	/**
	 * C898500 - Verify WFS with 90/10 workflow split for existing workflows.
	 * 
	 * testWFS90And10SplitWorkflow is a test method to verify end to end work flow for
	 * One Pass Automated DE. Steps followed 1) Created client and product directory
	 * in WFS server. Directory path in WFS server "/mnt/manglerdata1/output/<Client
	 * id>.<Product id>" 2) Transfer pre-defined loan PDF into client output /client
	 * id. product id directory 3) Import the loan using Simple loan import option
	 * through WFS console 4) Verify the loan is imported and visible through Index
	 * Dashboard --> Last 24 hours 5) Filter the loan number & verify the loan state
	 * and status of the loan 5.1 Managing the loan if(loan state = Index,loan
	 * status = Unassigned) i) login into SK with BPO operator having Index role
	 * ii)Retrieve the loan by clicking on Get & Save iii) Read the sqeuence and
	 * classification of documents from DataManager Cache and index the documents
	 * accordingly in SK iii) Index the loan documents & save the documents 5.2
	 * Verify the loan state and status in Dashboard either using UI or SQL DB Call
	 * 5.2 Expected that the loan should have exported & verify the batch and
	 * document type details in the XML file
	 * 
	 */
	@Test(priority = 6, groups = { "Level1","Level2" }, testName = "Verify WFS with 90/10 workflow split for existing workflows", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testWFS90And10SplitWorkflow(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testWFS90And10SplitWorkflow");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser.getDocumentTypes(hashmap, documentTypesMap);

			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			wfsToolsMaintenancePage.createAutoScheduledImportProfile(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
			CommonUtils.sleepForAWhile(20000);
			indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			for(count = 1; count <= noOfLoans; count++) {
				if(dbConnection.pollAndCheckWorkFlowState(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "Index", "Unassigned")) {
					Reporter.log("The workflow status is changed to Index UnAssigned");
				}else {
					Assert.fail(
							"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
				}
			}
			smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
			smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
			try {
				smartkeyLoginPage.selectRoleAndGetTask("Index");
				CommonUtils.sleepForAWhile();
				for(count = 1; count <= noOfLoans; count++) {
					skOperationManager.completeIndexing(smartkeyLoginPage.getWebAppDriver(), documentTypeVector,reportGenerator);
						CommonUtils.sleepForAWhile();
					if(count==noOfLoans) {
						break;
					}
					smartkeyLoginPage.doClickOk();
				}
				CommonUtils.sleepForAWhile();
				smartkeyLoginPage.doSaveAndOK();
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());			
			}
			CommonUtils.sleepForAWhile();
			indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
			try {
					smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
					smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator2Login"), hashmap.get("Operator2Password"));
					smartkeyLoginPage.selectRoleAndGetTask("Data Entry 2");
					CommonUtils.sleepForAWhile();
					HashMap<String,String> mockDataMap = (HashMap<String,String>)csvFileParser.getSmartkeyDEMockData(hashmap.get("DEMockDataCSV"));
					int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
					while(numberOfWorkItemPages>0) {
						skOperationManager.completeDataEntry(smartkeyLoginPage.getWebAppDriver(),mockDataMap,reportGenerator);
						numberOfWorkItemPages--;
						if(numberOfWorkItemPages>0) {
							skOperationManager.clickGetAndSave(smartkeyLoginPage.getWebAppDriver());
						}
					}
					smartkeyLoginPage.doSave();
					CommonUtils.sleepForAWhile(2000);
					smartkeyLoginPage.logoutSmartkey();
				} catch(Exception ex) {
					//Handle the exception
					ex.printStackTrace();
					Assert.fail("Failed at Data Entry 2 ..." + ex.getMessage());
				}
			indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
				try {
					smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
					smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator3Login"), hashmap.get("Operator3Password"));
					smartkeyLoginPage.selectRoleAndGetTask("Data Review");
					CommonUtils.sleepForAWhile(5000);
					int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
					while(numberOfWorkItemPages>0) {
						skOperationManager.completeDataReview(smartkeyLoginPage.getWebAppDriver(), hashmap, reportGenerator);
						smartkeyLoginPage.doUlike2Save();
						numberOfWorkItemPages--;
					}
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch(Exception ex) {
					//Handle the exception
					ex.printStackTrace();
					Assert.fail("Failed at Data Entry 2 ..." + ex.getMessage());
				}
	//		}
			
			CommonUtils.sleepForAWhile();
			indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
			for (int loans = 1; loans <= noOfLoans; loans++) {
				String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
				hashmap.put("LoanNumber", sLoanNumber);
				if (indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap)
						|| dbConnection.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
					String xmlDir = CommonUtils.getXMLDirPath(hashmap);
					String sPDFDir = CommonUtils.getPDFDirPath(hashmap);

					connecttoLinuxBox.downloadXmlFileFromEFS(
							EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
							EnvironmentPropertyLoader.getPropertyByName("user"),
							EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
							reportGenerator.getReportPath(), sPDFDir);

					xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
				} else {
					Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testOnePassAutomatedDE is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	/**
	 * testIndexOnlyWithEIR is a test method to verify end to end work flow for
	 * index Only With EIR. Steps followed 1) Created client and product directory
	 * in WFS server. Directory path in WFS server "/mnt/manglerdata1/output/<Client
	 * id>.<Product id>" 2) Transfer two pre-defined loan PDF into client output /client
	 * id. product id directory 3) Import the loans using Simple loan import option
	 * through WFS console 4) Verify the loans is imported and visible through Index
	 * Dashboard --> Last 24 hours 5) Filter the loan number & verify the loan state
	 * and status of the loan 5.1 Managing the loan if(loan state = Index,loan
	 * status = Unassigned) i) login into SK with BPO operator having Index role
	 * ii)Retrieve the loan by clicking on Get & Save iii) Read the sqeuence and
	 * classification of documents from DataManager Cache and index the documents
	 * accordingly in SK iii) Index the loan documents & save the documents 5.2
	 * Verify the loan state and status in Dashboard either using UI or SQL DB Call
	 * 5.2 Expected that the loan should have exported & verify the batch and
	 * document type details in the XML file
	 * 
	 */

	@Test(priority = 7, groups = { "Level1", "Testing", "MismatchEIR",
			"Level2" }, testName = "Verify WFS with IndexOnly with EIR workflow using SmartKey", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testIndexOnlyWithEIR(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			CommonUtils.sleepForAWhile(5000);
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testIndexOnlyWithEIR");
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser
					.getDocumentTypes(hashmap, documentTypesMap);

			if (!isSKVersionSet) {
				wfsMaintenancePage.checkAndAppendSkVersion();
				isSKVersionSet = true;
			}
			indexingDashboardMaintenancePage.moveLoansToOtherVendor();
			wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
			wfsMaintenancePage.createInputDirectory(hashmap);
			String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
			int noOfLoans = Integer.parseInt(NoOfLoansToImport);
			int count;
			for (count = 1; count <= noOfLoans; count++) {
				wfsMaintenancePage.transferAndImportLoanTOWFS(iTestContext, hashmap, connecttoLinuxBox);
				hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
			}
			CommonUtils.sleepForAWhile(8000);
			SmartKeyLoginPage smartkeyLoginPage = null;
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			for(count = 1; count <= noOfLoans; count++) {
				if(dbConnection.pollAndCheckWorkFlowState(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "Index", "Unassigned")) {
					Reporter.log("The workflow status is changed to Index UnAssigned");
				}else {
					Assert.fail(
							"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
				}
			}
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("Index");
					CommonUtils.sleepForAWhile();
					for(count = 1; count <= noOfLoans; count++) {
						CommonUtils.sleepForAWhile();
						skOperationManager.completeIndexing(smartkeyLoginPage.getWebAppDriver(), documentTypeVector,
								reportGenerator);
							CommonUtils.sleepForAWhile();
							
						if(count==noOfLoans) {
							break;
						}
						smartkeyLoginPage.doClickOk();
					}
							CommonUtils.sleepForAWhile();
							smartkeyLoginPage.doSaveAndOK();
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
				}
			CommonUtils.sleepForAWhile(20000);
			for (int loans = 1; loans <= noOfLoans; loans++) {
				String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
				hashmap.put("LoanNumber", sLoanNumber);
				if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap) || dbConnection
						.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
					String xmlDir = CommonUtils.getXMLDirPath(hashmap);
					String sPDFDir = CommonUtils.getPDFDirPath(hashmap);

					connecttoLinuxBox.downloadXmlFileFromEFS(
							EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
							EnvironmentPropertyLoader.getPropertyByName("user"),
							EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
							reportGenerator.getReportPath(), sPDFDir);

					xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
				} else {
					Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testIndexOnlyWithEIR is failed " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	/**
	 * This test method covers below test case
	 * C898531 - Verify WFS with ManagedAutomatedIndexing1PassDE workflow using SmartKey
	 */
		@Test(priority = 8, groups = { "Level1","Level2" }, testName = "Verify WFS with ManagedAutomatedIndexingOnly workflow using SmartKey", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
		public void testManagedAutomatedIndexing1PassDE(ITestContext iTestContext, HashMap<String, String> hashmap) {
			try {
				CommonUtils.sleepForAWhile(5000);
				reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testManagedAutomatedIndexing1PassDE");
				iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
				Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser
						.getDocumentTypes(hashmap, documentTypesMap);

				if (!isSKVersionSet) {
					wfsMaintenancePage.checkAndAppendSkVersion();
					isSKVersionSet = true;
				}
				indexingDashboardMaintenancePage.moveLoansToOtherVendor();
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
				for(count = 1; count <= noOfLoans; count++) {
					if(dbConnection.pollAndCheckWorkFlowState(hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "MLAI", "Unassigned")) {
						Reporter.log("The workflow status is changed to Index UnAssigned");
					}else {
						Assert.fail(
								"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
					}
				}
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("MLAI");
					CommonUtils.sleepForAWhile();
					for(count = 1; count <= noOfLoans; count++) {
						skOperationManager.completeMLAI(smartkeyLoginPage.getWebAppDriver());
							CommonUtils.sleepForAWhile();
						if(count==noOfLoans) {
							break;
						}
						smartkeyLoginPage.doClickOk();
					}
							CommonUtils.sleepForAWhile();
							smartkeyLoginPage.doSaveAndOK();
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
				}
				CommonUtils.sleepForAWhile(20000);
				for (count = 1; count <= noOfLoans; count++) {
					if (dbConnection.pollAndCheckWorkFlowState(
							hashmap.get(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count)), "Data Entry 1",
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
					HashMap<String,String> mockDataMap = (HashMap<String,String>)csvFileParser.getSmartkeyDEMockData(hashmap.get("DEMockDataCSV"));
					for (count = 1; count <= noOfLoans; count++) {
					int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
					int limit = numberOfWorkItemPages*noOfLoans;
					int temp = 0;
					while(numberOfWorkItemPages>0) {
						CommonUtils.sleepForAWhile(3000);
						skOperationManager.completeDataEntryFlow(smartkey.getWebAppDriver(),mockDataMap,reportGenerator);
						CommonUtils.sleepForAWhile(2000);
						numberOfWorkItemPages--;
						temp++;
						if(temp!=limit) {
							smartkey.doClickOk();
						}
					}
					}
					CommonUtils.sleepForAWhile();
					smartkey.doClickOk();
			CommonUtils.sleepForAWhile();
			smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					smartkey.logoutSmartkey();
				}
				CommonUtils.sleepForAWhile(20000);
				for (int loans = 1; loans <= noOfLoans; loans++) {
					String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
					hashmap.put("LoanNumber", sLoanNumber);
					if (wfsMaintenancePage.verifyLoanStateAndStatus(hashmap)
							|| dbConnection.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
						HashSet<String> uSoftValue = dbConnection.checkUSOFTField(hashmap.get("BatchID"));
						Assert.assertTrue(uSoftValue.size()==1);
						if(uSoftValue.contains(hashmap.get("ExpectedUSOFTValue"))) {
							Reporter.log("The USOFT values for the Particular batch ID displayed correctly");
						}else {
							Assert.fail("The USOFT values for the Particular batch ID not displayed correctly");
						}
						String xmlDir = CommonUtils.getXMLDirPath(hashmap);
						String sPDFDir = CommonUtils.getPDFDirPath(hashmap);

						connecttoLinuxBox.downloadXmlFileFromEFS(
								EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
								EnvironmentPropertyLoader.getPropertyByName("user"),
								EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
								reportGenerator.getReportPath(), sPDFDir);

						xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
					} else {
						Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
				TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
				Assert.fail("testManagedAutomatedIndexingOnly is failed " + ex.getMessage());
			} finally {
				// Do release clean up clear
			}
		}
		
		/**
		 * C898533 - Verify WFS with 30/30/30/10 workflow split for mixed workflows.
		 * 
		 * testOnePassAutomatedDE is a test method to verify end to end work flow for
		 * One Pass Automated DE. Steps followed 1) Created client and product directory
		 * in WFS server. Directory path in WFS server "/mnt/manglerdata1/output/<Client
		 * id>.<Product id>" 2) Transfer pre-defined loan PDF into client output /client
		 * id. product id directory 3) Import the loan using Simple loan import option
		 * through WFS console 4) Verify the loan is imported and visible through Index
		 * Dashboard --> Last 24 hours 5) Filter the loan number & verify the loan state
		 * and status of the loan 5.1 Managing the loan if(loan state = Index,loan
		 * status = Unassigned) i) login into SK with BPO operator having Index role
		 * ii)Retrieve the loan by clicking on Get & Save iii) Read the sqeuence and
		 * classification of documents from DataManager Cache and index the documents
		 * accordingly in SK iii) Index the loan documents & save the documents 5.2
		 * Verify the loan state and status in Dashboard either using UI or SQL DB Call
		 * 5.2 Expected that the loan should have exported & verify the batch and
		 * document type details in the XML file
		 * 
		 */
		@Test(priority = 2, groups = { "Level1",
				"Level2" }, testName = "Verify WFS with 30/30/30/10 workflow split for mixed workflows", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
		public void testWorkflowSplitForMixedWorkflows(ITestContext iTestContext, HashMap<String, String> hashmap) {
			try {
				reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), "testWorkflowSplitForMixedWorkflows");
				iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
				Vector<IdeaDocumentType> documentTypeVector = (Vector<IdeaDocumentType>) csvFileParser
						.getDocumentTypes(hashmap, documentTypesMap);
				Vector<IdeaDocumentType> documentTypeVectorMLAI = (Vector<IdeaDocumentType>) csvFileParser
						.getDocumentType(hashmap, documentTypesMap);

				if (!isSKVersionSet) {
					wfsMaintenancePage.checkAndAppendSkVersion();
					isSKVersionSet = true;
				}
				indexingDashboardMaintenancePage.moveLoansToOtherVendor();
				wfsToolsMaintenancePage.executeCommandKillJavaProcess(hashmap, connecttoLinuxBox);
				wfsMaintenancePage.createInputDirectory(hashmap);
				wfsToolsMaintenancePage.selectMultipleWorkFlowsInAutoScheduledImportProfile(hashmap);
				String NoOfLoansToImport = hashmap.get("NoOfLoansToImport");
				int noOfLoans = Integer.parseInt(NoOfLoansToImport);
				int count;
				for (count = 1; count <= noOfLoans; count++) {
					wfsMaintenancePage.transferLoanToWFS(iTestContext, hashmap, connecttoLinuxBox);
					hashmap.put(IdeaWFSConstants.LOAN_NUMBER + String.valueOf(count), hashmap.get("LoanNumber"));
				}
				wfsToolsMaintenancePage.executeCommandForAutoImport(hashmap, connecttoLinuxBox);
				CommonUtils.sleepForAWhile(45000);
				HashMap<String, List<String>> map = new HashMap<String, List<String>>();
				map = indexingDashboardMaintenancePage.verifyAndAddLoansToParticularWorkflowList(hashmap);
				SmartKeyLoginPage smartkeyLoginPage = null;
				MySQLDBConnection dbConnection = new MySQLDBConnection();
				List<String> loansInIndexState = new ArrayList<String>();

				List<String> loansInStandardWithEIRFlow = new ArrayList<String>();
				List<String> loansInOnePassAutomatedFlow = new ArrayList<String>();
				loansInStandardWithEIRFlow = map.get("standardWithEIR");
				loansInOnePassAutomatedFlow = map.get("onePassAutomatedDE");
				loansInIndexState.addAll(loansInOnePassAutomatedFlow);
				loansInIndexState.addAll(loansInStandardWithEIRFlow);

				for (count = 0; count < loansInIndexState.size(); count++) {
					if (dbConnection.pollAndCheckWorkFlowState(loansInIndexState.get(count), "Index", "Unassigned")) {
						Reporter.log("The workflow status is changed to Index UnAssigned");
					} else {
						Assert.fail(
								"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
					}
				}
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("OperatorLogin"), hashmap.get("OperatorPwd"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("Index");
					CommonUtils.sleepForAWhile();
					for (count = 0; count < loansInIndexState.size(); count++) {
						skOperationManager.completeIndexing(smartkeyLoginPage.getWebAppDriver(), documentTypeVector,
								reportGenerator);
						CommonUtils.sleepForAWhile();
						if (count == noOfLoans) {
							break;
						}
						CommonUtils.sleepForAWhile();
						smartkeyLoginPage.doClickOk();
						CommonUtils.sleepForAWhile();
					}
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.doClickOk();
					CommonUtils.sleepForAWhile(5000);
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					// Assuming that SK login would be successful as long as Windows Driver is up
					// and running. Handling logout if there is unexpected error
					smartkeyLoginPage.logoutSmartkey();
					Assert.fail("Encountered error while operating with SK " + ex.getStackTrace());
				}
				CommonUtils.sleepForAWhile(10000);
				indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
				for (count = 0; count < loansInStandardWithEIRFlow.size(); count++) {
					if (dbConnection.pollAndCheckWorkFlowState(loansInStandardWithEIRFlow.get(count), "Data Entry 2",
							"Unassigned")) {
						Reporter.log("The workflow status is changed to Data Entry 2 UnAssigned");
					} else {
						Assert.fail(
								"The workflow status is not yet changed to Data Entry 2 UnAssigned. Please check the error and make sure service is up and running");
					}
				}
				try {
					smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
					smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator2Login"), hashmap.get("Operator2Password"));
					smartkeyLoginPage.selectRoleAndGetTask("Data Entry 2");
					CommonUtils.sleepForAWhile(5000);
					HashMap<String, String> mockDataMap = (HashMap<String, String>) csvFileParser
							.getSmartkeyDEMockData(hashmap.get("DEMockDataCSV"));
					for (count = 0; count < loansInStandardWithEIRFlow.size(); count++) {
						int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
						int limit = numberOfWorkItemPages * loansInStandardWithEIRFlow.size();
						int temp = 0;
						while (numberOfWorkItemPages > 0) {
							CommonUtils.sleepForAWhile();
							skOperationManager.completeDataEntryFlow(smartkeyLoginPage.getWebAppDriver(), mockDataMap,
									reportGenerator);
							CommonUtils.sleepForAWhile(2000);
							numberOfWorkItemPages--;
							temp++;
							if (temp != limit) {
								CommonUtils.sleepForAWhile();
								smartkeyLoginPage.doClickOk();
							}
						}
					}
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.doClickOk();
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.logoutSmartkey();
				} catch (Exception ex) {
					// Handle the exception
					ex.printStackTrace();
					Assert.fail("Failed at Data Entry 2 ..." + ex.getMessage());
				}

				CommonUtils.sleepForAWhile(10000);
				indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
				for (count = 0; count < loansInStandardWithEIRFlow.size(); count++) {
					if (dbConnection.pollAndCheckWorkFlowState(loansInStandardWithEIRFlow.get(count), "Data Review",
							"Unassigned")) {
						Reporter.log("The workflow status is changed to Data Review UnAssigned");
					} else {
						Assert.fail(
								"The workflow status is not yet changed to Data Review UnAssigned. Please check the error and make sure service is up and running");
					}
				}
				
					try {
						smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
						smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator3Login"), hashmap.get("Operator3Password"));
						smartkeyLoginPage.selectRoleAndGetTask("Data Review");
						CommonUtils.sleepForAWhile(5000);
				

					for (count = 0; count < loansInStandardWithEIRFlow.size(); count++) {
						int numberOfWorkItemPages = Integer.parseInt(hashmap.get("WorkItemPages"));
						int limit = numberOfWorkItemPages * loansInStandardWithEIRFlow.size();
						int temp = 0;
						while (numberOfWorkItemPages > 0) {
							CommonUtils.sleepForAWhile();
							skOperationManager.completeDataReview(smartkeyLoginPage.getWebAppDriver(), hashmap,
									reportGenerator);
							CommonUtils.sleepForAWhile(2000);
							numberOfWorkItemPages--;
							temp++;
							if (temp != limit) {
								CommonUtils.sleepForAWhile();
								smartkeyLoginPage.doClickOk();
							}
						}
					}
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.doClickOk();
					CommonUtils.sleepForAWhile();
					smartkeyLoginPage.logoutSmartkey();
					
				} catch (Exception ex) {
					// Handle the exception
					ex.printStackTrace();
					Assert.fail("Failed at Data Entry 2 ..." + ex.getMessage());
				}
				
				List<String> loansInManagedIndex = new ArrayList<String>();
				List<String> loansInManaged1Pass = new ArrayList<String>();
				loansInManagedIndex = map.get("managedAutomatedIndexingOnly");// standardWithEIR
				loansInManaged1Pass = map.get("managedAutomatedIndexing1PassDE");
				List<String> loansInMLAIState = new ArrayList<String>();
				loansInMLAIState.addAll(loansInManagedIndex);
				loansInMLAIState.addAll(loansInManaged1Pass);
				indexingDashboardMaintenancePage.verifyListOfLoansInIndexingDashbaordPage(hashmap);
				for (count = 0; count < loansInMLAIState.size(); count++) {
					if (dbConnection.pollAndCheckWorkFlowState(loansInMLAIState.get(count), "MLAI",
							"Unassigned")) {
						Reporter.log("The workflow status is changed to Index UnAssigned");
					} else {
						Assert.fail(
								"The workflow status is not yet changed to Index UnAssigned. Please check the error and make sure OCRW service is up and running");
					}
				}
				smartkeyLoginPage = new SmartKeyLoginPage(reportGenerator);
				smartkeyLoginPage.loginToSmartkey(hashmap.get("Operator4Login"), hashmap.get("Operator4Password"));
				try {
					smartkeyLoginPage.selectRoleAndGetTask("MLAI");
					CommonUtils.sleepForAWhile();
					for (count = 0; count < loansInMLAIState.size(); count++) {
						skOperationManager.completeMLAIFlow(smartkeyLoginPage.getWebAppDriver());
						CommonUtils.sleepForAWhile();
						if (count == noOfLoans) {
							break;
						}
						CommonUtils.sleepForAWhile(10000);
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
							CommonUtils.sleepForAWhile();
							 
				indexingDashboardMaintenancePage.searchListOfLoansInIndexingDashboard(hashmap);
				for (int loans = 1; loans <= noOfLoans; loans++) {
					String sLoanNumber = hashmap.get("LoanNumber" + String.valueOf(loans));
					hashmap.put("LoanNumber", sLoanNumber);
					if (indexingDashboardMaintenancePage.initializeDetailsOfLoan(hashmap)
							|| dbConnection.pollAndCheckWorkFlowState(sLoanNumber, "Export Batch", "Completed")) {
						String xmlDir = CommonUtils.getXMLDirPath(hashmap);
						String sPDFDir = CommonUtils.getPDFDirPath(hashmap);

						connecttoLinuxBox.downloadXmlFileFromEFS(
								EnvironmentPropertyLoader.getPropertyByName("exporter_host"),
								EnvironmentPropertyLoader.getPropertyByName("user"),
								EnvironmentPropertyLoader.getPropertyByName("privatekeypath"), xmlDir,
								reportGenerator.getReportPath(), sPDFDir);
						if(hashmap.get("loanWorkFlowType").equalsIgnoreCase("ManagedAutomatedIndexingOnly")||hashmap.get("loanWorkFlowType").equalsIgnoreCase("ManagedAutomatedIndexing1PassDE")) {
							xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVectorMLAI);
						}else {
							xmlFileHandler.validateBatchXML(hashmap, reportGenerator, documentTypeVector);
						}
					} else {
						Assert.fail("Failed to export the loan. Please troubleshoot and retest again");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
				TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
				Assert.fail("testOnePassAutomatedDE is failed " + ex.getMessage());
			} finally {
				// Do release clean up clear
			}
		}
}
