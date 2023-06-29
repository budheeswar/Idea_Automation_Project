package com.ll.idea.listener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.utils.CalendarDateHandler;
import com.ll.idea.wfs.base.TestBase;

public class ScreenshotListener extends TestListenerAdapter{

	@Override 
	public void onTestFailure(ITestResult result) {
		this.setTestCaseInfo(result);
		this.captureScreenShot(result);
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		//Capturing screen shot for positive flow is already taken care as part of ReportGenerator. 
		//ScreenShotListerner is also being used to take screen shot if there is un-expected failure
		 this.setTestCaseInfo(result);
		 if(System.getProperty("isScreenShot").equals("True")) {
			 this.captureScreenShot(result);
		 } else {
			 createReportSubFolder(result); //just to create report folder with test case number if screen capturing is suppressed for positve case.
		 }
	}
	
	private void createReportSubFolder(ITestResult result) {
		@SuppressWarnings("unchecked")
		HashMap<String,String> testDataMap = (HashMap<String,String>) result.getTestContext().getAttribute(IdeaWFSConstants.TEST_DATA_MAP);
		TestBase objectBaseTest = (TestBase)result.getInstance();
		objectBaseTest.setsTestCaseNumber(testDataMap.get(IdeaWFSConstants.TEST_CASE_NUMBER));
		objectBaseTest.setsReportDirectory();
		String sReportDirectory = objectBaseTest.getsReportDirectory();
		File reportDirectory = new File(sReportDirectory);
		if(!reportDirectory.exists()) reportDirectory.mkdirs();
		
	}
	private void setTestCaseInfo(ITestResult result) {
		@SuppressWarnings("unchecked")
		HashMap<String,String> hashmap = (HashMap<String,String>) result.getTestContext().getAttribute(IdeaWFSConstants.TEST_DATA_MAP);		
		String testCaseNumber = hashmap.get(IdeaWFSConstants.TEST_CASE_NUMBER);
		TestBase baseTestInitializer = (TestBase) result.getInstance();
		baseTestInitializer.setsTestCaseName(hashmap.get("TestCaseName"));
		baseTestInitializer.setsTestCaseNumber(testCaseNumber);
		baseTestInitializer.setsReportDirectory();
	}
	@SuppressWarnings("unchecked")
	public void captureScreenShot(ITestResult result) {
		HashMap<String,String> testDataMap = (HashMap<String,String>) result.getTestContext().getAttribute(IdeaWFSConstants.TEST_DATA_MAP);
		String dateAppender = CalendarDateHandler.getFormatedDate("MMddYYYYHHmm");
		TestBase objectBaseTest = (TestBase)result.getInstance();
		objectBaseTest.setsTestCaseNumber(testDataMap.get(IdeaWFSConstants.TEST_CASE_NUMBER));
		objectBaseTest.setsReportDirectory();
		WebDriver currentWebDriver = objectBaseTest.getDriver(); 
		String sReportDirectory = objectBaseTest.getsReportDirectory();
		String destinationFileName = sReportDirectory + File.separator + result.getMethod().getMethodName() + dateAppender + ".png";
		if (currentWebDriver != null) {
			File scrFile = ((TakesScreenshot) currentWebDriver).getScreenshotAs(OutputType.FILE);
			File reportDirectory = new File(sReportDirectory);
			try {
				if(!reportDirectory.exists()) {reportDirectory.mkdirs(); }
				File destinationFile =  new File(destinationFileName);
				FileUtils.moveFile(scrFile,	destinationFile);
			} catch (IOException e) {
				Reporter.log("Failed to capture screen shots due to " + e.getMessage());
			}
		}else {
			Reporter.log("Driver is not active to take screenshot",true);
		}
	}

}
