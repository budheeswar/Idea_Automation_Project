package com.ll.idea.management;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.gurock.testrail.APIClient;
import com.ll.idea.utils.ConfigPropertyLoader;
import com.ll.idea.utils.FileIOHandler;

public class TestNGTestRailUploader {

	private static final String BASE_REPORT_DIR = System.getProperty("user.dir") +  File.separator + ConfigPropertyLoader.getConfigValue("reportFolder") ;
	
	
	/**
	 * Uploading test evidence into test rail
	 * @param resultStatus is one of the keyword such as "success" or "failed". it will be translated to 1 or 2 respectively
	 * @param testCaseNumber
	 */
	public static void uploadTestResultsToTestRail(String resultStatus, String testCaseNumber) {
		if("success".equals(resultStatus)) {
			doUploadTestEvidence(testCaseNumber,1,null);
		} else if("failed".equals(resultStatus)) {
			doUploadTestEvidence(testCaseNumber,2,null);
		}
	}
	
	/**
	 * This class takes care of integrating with test rail through 
	 * test rail API. 
	 * As part of this release 1.0
	 * 	 1) Getting test run id from maven goal
	 *   2) Creating result object using the given test case id from input CSV file
	 *   3) Adding test result as zip file and status either PASS or FAIL
	 * @param ctx the iTestResult carries the execution status, iTest context values
	 * 
	 */
	public static void uploadTestResultsToTestRail(ITestResult result) {
		@SuppressWarnings("unchecked")
		HashMap<String,String> hashmap = (HashMap<String,String>) result.getTestContext().getAttribute("testDataMap");		
		String testCaseNumber = hashmap.get("TestCaseNumber");
		doUploadTestEvidence(testCaseNumber, result.getStatus(), result);
	}
	public static void doUploadTestEvidence(String testCaseNumber, int statusCode, ITestResult result) {
		Reporter.log("uploadTestResultsToTestRail");
		String testRailURL = "https://sqaloanlogics.testrail.net";
		APIClient client = new APIClient(testRailURL);
		
		client.setUser(System.getProperty("TestRailUser"));
		client.setPassword(System.getProperty("TestRailPwd"));
		
		String reportDir = null;
		
		try {
			
			reportDir = BASE_REPORT_DIR + File.separator + testCaseNumber;
			
			
			String testRun = null;
			if(System.getProperty("TestRunID")!=null) {
				testRun = System.getProperty("TestRunID");
			}else {
				Reporter.log("Test Run ID is not provided. Please pass the ID from maven goal as below\n"+
						"clean test -Dbrowser=chrome -DEnvironment=llqa -Dgroups=Smoke -DtestngXmlFile=LoanRegistrationSuite.xml -DTestRunID=2368\n", true);
				return;
			}
			
			int status = statusCode; 
			Reporter.log("test result Status ["+status+"]");
			List<String> getTestCaseIDList = getTestCaseID(testCaseNumber);
			for(String sTestCaseId : getTestCaseIDList) {
				String testResultsName = prepareTestResults(reportDir, sTestCaseId);
				String removeCFromTestCase = sTestCaseId.substring(1);
				String addTestResult = "add_result_for_case/"+ testRun +"/"+ removeCFromTestCase;
				HashMap<String, Object> requestData = new HashMap<>();
				if(status == 1) {
					requestData.put("status_id", status);
					requestData.put("comment", "Automation test : " + testCaseNumber + " PASSED, Automation report is attached as evidence");
				}else if(status == 2) {
					//result.
					requestData.put("status_id", 5);
					requestData.put("comment", "Automation test : C" + testCaseNumber + " FAILED, Automation report is attached as evidence \n" +(result !=null?result.getThrowable():""));
				}

				uploadTestEvidence(client,addTestResult,requestData,testResultsName,testCaseNumber);

			}
		}catch(Exception e) {
			e.printStackTrace();
			Reporter.log("Exception is occured when the test evidence is being uploaded into test rail due to " + e.getMessage());
		}

	}
	

	private static void uploadTestEvidence(APIClient client, String testCase, HashMap<String,Object> requestData, String testResultName, String testCaseNumber) {
		try {
			JSONObject resultObject = (JSONObject) client.sendPost(testCase, requestData);
			String resultId = String.valueOf(resultObject.get("id"));
			Reporter.log(resultId);
			String addAttachmentResultId = "add_attachment_to_result/" + resultId;
			Reporter.log(String.valueOf(client.sendPost(addAttachmentResultId, testResultName)));
		} catch(Exception ex) {
			Reporter.log("The test case id is not found or active in the test run. Please check C" + testCaseNumber);
			ex.printStackTrace();
		}
	}
	
	/**
	 * zip test results and return the  
	 * @param testcaseID
	 * @return
	 */
	public static String prepareTestResults(String sReportDir, String sTestcaseNumber) {
		File fReportDir = null;
		String zipFilePath = null;
		try	{
			fReportDir = new File(sReportDir);
			Reporter.log(fReportDir.getAbsolutePath());
			String targetZipFileName = sTestcaseNumber +".zip"; 
			
			if(fReportDir.exists()) {
				boolean isZipped = FileIOHandler.zipFiles(fReportDir.getAbsolutePath(), targetZipFileName);
				zipFilePath = fReportDir.getParent() + File.separator + targetZipFileName;
				if(isZipped) { 
					Reporter.log("The report files are zipped successfully " + zipFilePath);
				} else {
					Reporter.log("The report files are not failed to zip . please check the log for more info " + zipFilePath);
				}
				
			}
		}catch(Exception e)	{
			e.printStackTrace();
			Reporter.log("Exception is occured while gathering test evidence and compressing the file due to "+ e.getMessage());
		}
		return zipFilePath;
	}


	public static List<String> getTestCaseID(String testCaseIds) {
		List<String> getTestCaseIDList =null;
		try	{
			getTestCaseIDList = Arrays.asList(testCaseIds.split(","));
		}catch(Exception e)	{
			Reporter.log("Exception occured while replacing speacial characters in test case number field prior to upload test evidence."
					+ " One of the reason may be " + e.getMessage() );
		}
		return getTestCaseIDList;
	}

	public static void main(String...args)  { 
		String sReportDir = "C:\\LL\\Automation\\Repository\\IDEA\\IDEA-WFS\\target\\test-output\\testReports\\C891566";
		String sTestcaseId = "C891566";
		TestNGTestRailUploader.prepareTestResults(sReportDir, sTestcaseId);
		
	}
}

//This filter will be used to check for all files if a file is html file
class HtmlFileFilter implements FilenameFilter 
{
  @Override
  public boolean accept(File dir, String fileName) 	  {
    return (fileName.endsWith(".html") || fileName.endsWith(".png"));
  }
}
