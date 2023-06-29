package com.ll.idea.reporting;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.utils.CalendarDateHandler;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConfigPropertyLoader;
import com.ll.idea.utils.EnvironmentPropertyLoader;

public class ReportGenerator {

	
	private ExtentHtmlReporter htmlReporter = null;
	private ExtentReports extent = null;
	private ExtentTest test = null;
	private static final String BASE_REPORT_DIR = System.getProperty("user.dir") +  File.separator + ConfigPropertyLoader.getConfigValue("reportFolder") ;
	private String reportPath = null;
	
	
	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public ReportGenerator() {
		//Empty constructor to instantiate from test method 
	}
	
	public void setupExtendedReport(String testCaseNumber, String testMethodName) {
		// location of the extent report
		reportPath = BASE_REPORT_DIR + File.separator+ testCaseNumber ;
		String htmlFilePath = reportPath + File.separator+ testMethodName + "_" + CalendarDateHandler.getFormatedDate("MMddyyyyhh_mm_ss") +".html";
		htmlReporter = new ExtentHtmlReporter(htmlFilePath);
		extent = new ExtentReports(); // create object of ExtentReports
		extent.attachReporter(htmlReporter);

		htmlReporter.config().setDocumentTitle("IDEA Test Automation Report"); // Title of Report
		htmlReporter.config().setReportName("IDEA Test Automation Report"); // Name of the report
		htmlReporter.config().setTheme(Theme.STANDARD);// Default Theme of Report

		// General information related to application
		extent.setSystemInfo("Application Name", "IDEA WFS");
		extent.setSystemInfo("Author", "ramesh.ramanujam@loanlogics");
		extent.setSystemInfo("Envirnoment", System.getProperty("Environment"));
		extent.setSystemInfo("Browser", System.getProperty("Browser"));
		extent.setSystemInfo("URL", EnvironmentPropertyLoader.getPropertyByName("idea_wfs_console_base_url"));
		test = extent.createTest(testMethodName);
		
	}
	
	
	/** 
	 * To log the test message to capture in the report. 
	 * @param message string value to be printed in the report
	 * @param logLevel logLevel could be one of the following values such as 
	 * PASS, FAIL, FATAL,ERROR, WARNING, INFO, DEBUG, SKIP
	 */
	public void logMessage(String message, Status logLevel) {
		Status level = Status.INFO;
		if(logLevel != null) level = logLevel;
		test.log(level, message);
		Reporter.log(message); //routing the message to testng reporter
	}

	public void logAndCaptureScreen(String message, String methodName, Status logLevel, WebDriver webDriver) {
		CommonUtils.sleepForAWhile(1000);
		if(System.getProperty("isScreenShot").equalsIgnoreCase("True")) { //if isScreenShot value is false, then ignore screen shot
			captureScreenShot(webDriver, methodName);
		}
		logMessage(message, logLevel);
	}
	
	public void logAndCaptureScreen(String message, String methodName, Status logLevel, WebDriver webDriver, WebElement ele) {
		((JavascriptExecutor) webDriver).executeScript(IdeaWFSConstants.JS_ARG_SCROLL_VIEW, ele);
		logAndCaptureScreen(message, methodName, logLevel, webDriver);
	}
	
	
	private void captureScreenShot(WebDriver webDriver, String methodName) {
		String destinationFileName = reportPath + File.separator + methodName+"-"+CalendarDateHandler.getFormatedDate("MMddYYYYhhmmss")+ ".png";
		if (webDriver != null) {
			File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			File reportDirectory = new File(reportPath);
			try {
				if(!reportDirectory.exists()) {reportDirectory.mkdirs(); }
				File destinationFile =  new File(destinationFileName);
				FileUtils.moveFile(scrFile,	destinationFile);
			} catch (IOException e) {
				Reporter.log("Failed to capture screen shots due to " + e.getMessage());
			}
		}
	}
	public void endReport() {
		try {
			extent.flush();
		} catch (Exception e) {
			Reporter.log("Exception occured while pushing the test execution report due to " + e.getMessage());
		} finally {
			test = null;extent = null;htmlReporter = null;			
		}
	}
}
