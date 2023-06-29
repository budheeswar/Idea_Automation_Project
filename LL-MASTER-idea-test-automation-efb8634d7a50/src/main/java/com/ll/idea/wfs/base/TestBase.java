package com.ll.idea.wfs.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestContext;
import org.testng.Reporter;

import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.utils.ConfigPropertyLoader;
import com.ll.idea.utils.EnvironmentPropertyLoader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	protected WebDriver driver = null;
	protected String testSuiteName = null;
	protected String sTestCaseNumber = null;
	protected String sTestCaseName = null;
	protected String sReportDirectory = null;
	protected static boolean isSKVersionSet = false;
	
	
	ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();
	
	protected HashMap<String,WebDriver> driversMap = new HashMap<>();
 	
	public Map<String,WebDriver> getListOfDrivers() {
		return driversMap;
	}

	public void setListOfDrivers(Map<String,WebDriver> driversMap) {
		this.driversMap = (HashMap<String, WebDriver>)driversMap;
	}

	public static boolean isSKVersionSet() {
		return isSKVersionSet;
	}

	public static void setSKVersionSet(boolean isSKVersionSet) {
		TestBase.isSKVersionSet = isSKVersionSet;
	}

	public WebDriver getLocalDriver() {
		return localDriver.get();
	}

	public void setLocalDriver(WebDriver webDriver) {
		this.localDriver.set(webDriver);
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	
	public String getsReportDirectory() {
		return sReportDirectory;
	}

	public void setsReportDirectory() {
		if(this.getsTestCaseNumber() != null) {
			this.sReportDirectory = System.getProperty(IdeaWFSConstants.USER_DIR) +  File.separator + ConfigPropertyLoader.getConfigValue("reportFolder") + File.separator + this.getsTestCaseNumber() ;
		} else {
			this.sReportDirectory = System.getProperty(IdeaWFSConstants.USER_DIR) +  File.separator + ConfigPropertyLoader.getConfigValue("reportFolder") ;
		}
	}

	public String getsTestCaseNumber() {
		return this.sTestCaseNumber;
	}

	public void setsTestCaseNumber(String sTestCaseNumber) {
		this.sTestCaseNumber = sTestCaseNumber;
	}

	public String getsTestCaseName() {
		return this.sTestCaseName;
	}

	public void setsTestCaseName(String sTestCaseName) {
		this.sTestCaseName = sTestCaseName;
	}

	public void initializeBrowser(ITestContext iTestContext) {
		String baseURL = null;
		String loginPage = null;
		String browserName = System.getProperty("browser");
		
		try {
			if(browserName.equals("chrome")){
				synchronized (WebDriverManager.class) {
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(chromeDriverOptions());
					this.setLocalDriver(driver);
					driversMap.put(testSuiteName,this.getLocalDriver());
					driver = this.getLocalDriver();
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
					driver.manage().timeouts().pageLoadTimeout(IdeaWFSConstants.IDEA_WFS_PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(IdeaWFSConstants.IMPLICIT_WAIT, TimeUnit.SECONDS);

					baseURL = EnvironmentPropertyLoader.getPropertyByName("idea_wfs_console_base_url");
					loginPage = EnvironmentPropertyLoader.getPropertyByName("login_page");
					driver.get(baseURL + loginPage);
				}
			}
		}	catch(Exception e) {
			Reporter.log("Exception occured while loading IDEA WFS console home page");
		}
		this.manageWebDriver(iTestContext);
	}
	
	@SuppressWarnings("unchecked")
	private void manageWebDriver(ITestContext iTestContext) {
		//store and manage the web driver list at class level 
		String invokingClsName = iTestContext.getClass().getName();
		List<WebDriver> webDriverList = null;
		if(iTestContext.getAttribute(invokingClsName) != null) {
			webDriverList = (ArrayList<WebDriver>)iTestContext.getAttribute(invokingClsName);
		} else {
			webDriverList = new ArrayList<>();
		}
		webDriverList.add(driver);
		iTestContext.setAttribute(invokingClsName, webDriverList);
	}
	
	/**
	 * Automatically downloads browser drivers into the <code>downloadedfiles</code> folder
	 * under user directory
	 * @return ChromeOptions to setup driver
	 */
	ChromeOptions chromeDriverOptions() {
		String downloadFilepath = System.getProperty("user.dir")+File.separator+"downloadedfiles"; 
		ChromeOptions options = new ChromeOptions();
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(ChromeOptions.CAPABILITY, options);
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		
		
		options.setHeadless(Boolean.parseBoolean(System.getProperty("headless")));
		List<String> arguments = new ArrayList<>();
		arguments.add("−−incognito");
		arguments.add("disable-infobars");
		options.addArguments(arguments);
		
		
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		chromePrefs.put("safebrowsing.enabled", "true"); 
		chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);

		//To Turns off multiple download warning
		chromePrefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads",1);//Turns off download prompt
		chromePrefs.put("download.prompt_for_download",false);
		options.setExperimentalOption("prefs", chromePrefs);
		return options;
	}
	
	public synchronized void closeWebDriver(ITestContext iTestContext) {
		//update the report and close the driver
		@SuppressWarnings("unchecked")
		List<WebDriver> webDriverList = (ArrayList<WebDriver>) iTestContext.getAttribute(iTestContext.getClass().getName());
		for(WebDriver webDriver:webDriverList) {
			if(driver != null) {
				try {
					webDriver.quit();
				} catch(Exception ex) {
					//Ignore the exception if there is any exception triggered while closing
				}
			}
		}
	}
	
	public synchronized void closeWebDriver(WebDriver webDriver) {
		try {
			webDriver.quit();
		} catch(Exception ex) {
			//Ignore if there is an exception. no need to take any action here
		}
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}
}
