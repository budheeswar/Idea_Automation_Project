package com.ll.idea.wfs.suite;

import java.util.Date;
import java.util.HashMap;

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
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.SeleniumUtils;
import com.ll.idea.wfs.base.TestBase;
import com.ll.idea.wfs.ui.page.WFSHomePage;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;

import junit.framework.Assert;

/**
 * This test suite to test user login, reset password, clear password, logout
 * functionality The class should be named starting with Test to follow naming
 * convention
 * 
 * @author ramesh.ramanujam
 *
 */
public class TestWFSUserSuite extends TestBase {
	WFSLoginPage loginPage = null;
	WFSHomePage homePage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	ReportGenerator reportGenerator = null;

	public TestWFSUserSuite() {
		super();
	}

	@BeforeSuite(alwaysRun = true, groups = { "Level1","ParallelTest"})
	public void doSetUp(ITestContext iTestContext) throws Exception {
		/**
		 * Create sub directory under <code>/testdata</code> for every suite to
		 * segregate the test data CSV file, helps to manage with ease. For example,
		 * <code>user</code> is a sub test data folder to manage all csv files related
		 * to user module
		 */
		this.setTestSuiteName("user");
		this.initializeBrowser(iTestContext);
		loginPage = new WFSLoginPage(this.getListOfDrivers().get(this.getTestSuiteName()));
		loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
										EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
	}

	@BeforeMethod(alwaysRun = false, groups = { "Level1","ParallelTest" })
	public void doBeforeMethod() {
		
		// Do instantiate required object for this test suite
		reportGenerator = new ReportGenerator();
		WebDriver localDriver = this.getListOfDrivers().get(this.getTestSuiteName());
		//loginPage = new WFSLoginPage(localDriver, reportGenerator);
		loginPage.setReportGenerator(reportGenerator);
		homePage = new WFSHomePage(localDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(localDriver, reportGenerator);

	}

	@AfterMethod(alwaysRun = true, groups = { "Level1","ParallelTest" })
	public void doAfterMethod(ITestResult result) {
		reportGenerator.endReport();
		TestNGTestRailUploader.uploadTestResultsToTestRail(result);
	}

	@AfterSuite(alwaysRun = true, groups = { "Level1","ParallelTest" })
	public void doWrapUp(ITestContext iTestContext) throws Exception {
		loginPage.userLogout(); 
		this.closeWebDriver(this.getListOfDrivers().get(this.getTestSuiteName()));
	}

	/*
	 * <code>testUserLogin</code> test method helps validate user authentication at
	 * WFS Console. It covers the following test such as 
	 * 1)C891566 - Verify successful user login with valid credential
	 * 2)C891567 - Verify unsuccessful user login with invalid credential
	 * 3)C891591 - Verify user successfully logout from IDEA console
	 */
	@Test(priority = 1, groups = { "Level1"}, testName = "Test user login with given credential", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testUserLogin(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			Reporter.log("[Thread - testUserLogin]" + Thread.currentThread().getName() + "[DateTime]" + new Date());
			iTestContext.setAttribute("testDataMap", hashmap); // Test Data Map must be set to reporting purpose
			CommonUtils.sleepForAWhile();
			loginPage.userLogout();
			loginPage.userLogin(hashmap.get("UserName"), hashmap.get("Password"));

			if (hashmap.get("ExpectedResult").equals("HomePage")) {
				homePage.doAssertHomepageTitle(IdeaWFSConstants.HOMEPAGE_ADMINISTRATIVE_DASHBOARD_TITLE);
				loginPage.userLogout();
			} else if (hashmap.get("ExpectedResult").equals("LoginPage")) {
				loginPage.doAssertUnsuccessfulErrorMessage(
						IdeaWFSConstants.LOGIN_PAGE_UNSUCCESSFUL_LOGIN_ATTEMPT_ERR_MSG);
			}
			loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
					EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed to validate user login due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testCreateUser is failed");
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * This method checks user creation functionality. It covers the following Test
	 * cases such as 1)C891596-Create new user with details in correct format
	 * 2)C891597-Search user with valid search criteria 3)C895878-Verify Edit
	 * functionality for Search User 4)C891651-Verify Password policy
	 * 5)C895876-Verify DeActive User functionality 6)C895877-Verify Active User
	 * functionality 7)C891615- Create new user with incorrect email format
	 * 8)C891622 - Create new user with already existing user email
	 */
	@Test(priority = 2, groups = {"Level1" }, testName = "test user creation with different user parameters", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testUserCreation(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			iTestContext.setAttribute("testDataMap", hashmap);
			String crudMode = hashmap.get("CRUDMode");
			if (crudMode.equals("Create")) {
				if (userMaintenancePage.createNewUser(hashmap)) {
					Assert.assertEquals(SeleniumUtils.getValue(this.getLocalDriver(),
							userMaintenancePage.createNewUser_successMessage), hashmap.get("ExpectedResult"));
					if (hashmap.get("SearchFilter") != null) {
						userMaintenancePage.searchUser(hashmap);
					}
				}
			} else if (crudMode.equals("Modify")) {
				if (userMaintenancePage.createNewUser(hashmap)) {
					Assert.assertEquals(SeleniumUtils.getValue(this.getLocalDriver(),
							userMaintenancePage.createNewUser_successMessage), hashmap.get("ExpectedResult"));
					userMaintenancePage.searchUser(hashmap);
					if (userMaintenancePage.modifyUser(hashmap)) {
						if (hashmap.get("SearchFilter") != null) {
							userMaintenancePage.searchUser(hashmap);
						}
					}
				}
			} else if (crudMode.equals("DeActivate")) {
				if (userMaintenancePage.createNewUser(hashmap)) {
					Assert.assertEquals(SeleniumUtils.getValue(this.getLocalDriver(),
							userMaintenancePage.createNewUser_successMessage), hashmap.get("ExpectedResult"));
					if (userMaintenancePage.deactivateUser(hashmap)) {
						if (hashmap.get("SearchFilter") != null) {
							userMaintenancePage.searchUser(hashmap);
							loginPage.userLogout();
							loginPage.userLogin(hashmap.get("UserEmail"), hashmap.get("UserPassword"));
							loginPage.doAssertUnsuccessfulErrorMessage(
									IdeaWFSConstants.LOGIN_PAGE_UNSUCCESSFUL_DEACTIVATED_USER_ERR_MSG);
							loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
									EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
						}
					}
				}
			} else if (crudMode.equals("ReActivate")) {
				if (userMaintenancePage.createNewUser(hashmap)) {
					Assert.assertEquals(SeleniumUtils.getValue(this.getLocalDriver(),
							userMaintenancePage.createNewUser_successMessage), hashmap.get("ExpectedResult"));
					if (userMaintenancePage.deactivateUser(hashmap)) {
						if (hashmap.get("SearchFilter") != null) {
							userMaintenancePage.searchUser(hashmap);
							loginPage.userLogout();
							loginPage.userLogin(hashmap.get("UserEmail"), hashmap.get("UserPassword"));
							loginPage.doAssertUnsuccessfulErrorMessage(
									IdeaWFSConstants.LOGIN_PAGE_UNSUCCESSFUL_DEACTIVATED_USER_ERR_MSG);
							loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
									EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
							userMaintenancePage.ReActivateUser(hashmap);
							this.getLocalDriver().navigate().back();
							loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
									EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
							loginPage.userLogout();
						}
					}
				}
			}else if (crudMode.equals("IncorrectMailID")) {
				loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
						EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
				 userMaintenancePage.createNewUser(hashmap);
			}else if (crudMode.equals("CreatingWithExistingUser")) {
				if (userMaintenancePage.createNewUser(hashmap)) {
					Assert.assertEquals(SeleniumUtils.getValue(this.getLocalDriver(),
							userMaintenancePage.createNewUser_successMessage), hashmap.get("ExpectedResult"));
					userMaintenancePage.searchUser(hashmap);
					userMaintenancePage.createUserWithExistingUserName(hashmap);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testUserCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * This method checks user creation functionality. It covers the following Test
	 * cases such as 1)C891617-Search user with wild card 2)C891616-Search user
	 * using special characters
	 */
	@Test(priority = 3, groups = { "Level1" }, testName = "Search user with wildcard", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testWildCardSearch(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			iTestContext.setAttribute("testDataMap", hashmap);
			if (userMaintenancePage.createNewUser(hashmap)) {
				if (hashmap.get("SearchFilter") != null) {
					userMaintenancePage.wildCardSearch(hashmap);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testUserCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}

	/**
	 * This method checks user Roles,Vendors and Password Reset functionality. It
	 * covers the following Test cases such as 1)C895880-Verify User's Roles
	 * functionality 2)C895881-Verify User's Vendors functionality 3)C895879-Verify
	 * Reset Password functionality
	 */
	@Test(priority = 4, groups = { "Level1" }, testName = "User Roles And Vendors Functionality", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testUserRolesAndVendorsFunctionality(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			iTestContext.setAttribute("testDataMap", hashmap);
			if (userMaintenancePage.createNewUser(hashmap)) {
				Assert.assertEquals(
						SeleniumUtils.getValue(this.getLocalDriver(), userMaintenancePage.createNewUser_successMessage),
						hashmap.get("ExpectedResult"));
				if (hashmap.get("SearchFilter") != null) {
					userMaintenancePage.searchUser(hashmap);
				}
			}
			if (hashmap.get("UserFunctionality").equals("UserRoles")) {
				if (userMaintenancePage.updateUserRoles(hashmap)) {
					if (hashmap.get("SearchFilter") != null) {
						userMaintenancePage.searchUser(hashmap);
					}
				}
			} else if (hashmap.get("UserFunctionality").equals("UserVendors")) {
				if (userMaintenancePage.updateUserVendors(hashmap)) {
					if (hashmap.get("SearchFilter") != null) {
						userMaintenancePage.searchUser(hashmap);
					}
				}
			} else if (hashmap.get("UserFunctionality").equals("ResetPassword")) {
				if (userMaintenancePage.userResetPassWordFunctionality(hashmap)) {
					homePage.doAssertHomepageTitle(IdeaWFSConstants.HOMEPAGE_ADMINISTRATIVE_DASHBOARD_TITLE);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testUserCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	
	/**
	 * This method checks user login reset functionality. It covers the following Test
	 * cases such as 1)C891590-Verify clearing the user email and password texts on the login page.
	 */
	@Test(priority = 3, groups = { "Level1" }, testName = "clearing the user email and password texts on the login page", dataProvider = "CsvMapDataProvider", dataProviderClass = AutomationTestDataProvider.class)
	public void testResetFunctionalityInUserLoginPage(ITestContext iTestContext, HashMap<String, String> hashmap) {
		try {
			reportGenerator.setupExtendedReport(hashmap.get("TestCaseNumber"), hashmap.get("TestCaseName"));
			iTestContext.setAttribute("testDataMap", hashmap);
			loginPage.userLogout();
			loginPage.checkUserLoginRestBtn(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
					EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
			loginPage.userLogin(EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.login"),
					EnvironmentPropertyLoader.getPropertyByName("wfs.console.user.pwd"));
		} catch (Exception ex) {
			ex.printStackTrace();
			reportGenerator.logMessage("Failed due to " + ex.getMessage(), Status.ERROR);
			TestNGTestRailUploader.uploadTestResultsToTestRail("Failed", hashmap.get("TestCaseNumber"));
			Assert.fail("testUserCreation is failed due to " + ex.getMessage());
		} finally {
			// Do release clean up clear
		}
	}
	
}
