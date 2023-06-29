package com.ll.idea.wfs.suite;

import org.testng.annotations.Test;

import com.ll.idea.management.TestRailTestRunManager;

import junit.framework.Assert;

public class TestAddTestCasesIntoTestRunSuite {
	
	public TestAddTestCasesIntoTestRunSuite() {
		
	}

	/* 
	 * <code>testWFS</code> test method to test cases id from all input CSV files
	 * and add into the given test plan id. The test plan id is specified in the env. property 
	 * file. TestRailTestRunManager class would find the appropriate test run based on the given
	 * test run name which is specified in env. property file.
	 * 
	 * Adding all test cases into Test Run using Test Rail API
	 */
	@Test(priority = 1, groups = {"Prerequisite"}, testName = "Loading test cases into test run") 
	public void loadTestCasesIntoTestRun() {
		try {
			TestRailTestRunManager testRunManager = new TestRailTestRunManager();
			testRunManager.addTestCasesIntoTestRun();
			Assert.assertTrue("Test Cases are added into Test Run", true);
		} catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail("Failed to load test cases into test run");
		}
	}
}
