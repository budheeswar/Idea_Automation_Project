package com.ll.idea.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.FileIOHandler;


public class IdeaWFSTestListener implements ITestListener {

	//ITestListener implementation
	
		@Override		
	    public void onFinish(ITestContext arg0) {					
			Reporter.log("Finished test execution " + arg0.getSuite());
	    }	
	
	    @Override		
	    public void onStart(ITestContext arg0) {					
	    	String baseReportDir = EnvironmentPropertyLoader.getPropertyByName("reportFolder");
	    	FileIOHandler.deleteFiles(baseReportDir);
	    }		

	    @Override		
	    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {					
	    	//Do implement any logic upon failure with certain percentage of success if any
	    }		

	    @Override		
	    public void onTestFailure(ITestResult arg0) {					
	    	//Do implement if there is any logic
	    }		

	    @Override		
	    public void onTestSkipped(ITestResult arg0) {					
	    	//Do implement if there is any logic
	    }		

	    @Override		
	    public void onTestStart(ITestResult arg0) {	
	    	//Do implement if there is any logic
	    }		

	    @Override		
	    public void onTestSuccess(ITestResult arg0) {					
	    	//Do implement if there is any logic
	    }		

}
