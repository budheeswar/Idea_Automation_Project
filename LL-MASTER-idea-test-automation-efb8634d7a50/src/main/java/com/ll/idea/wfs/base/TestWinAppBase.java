package com.ll.idea.wfs.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.ConfigPropertyLoader;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.WinAppDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.functions.AppiumFunction;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class TestWinAppBase {

	private WindowsDriver<WindowsElement> webAppDriver = null;
	private ReportGenerator reportGenerator = null; 
	
	public ReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	public void setReportGenerator(ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	public WindowsDriver<WindowsElement> getWebAppDriver() {
		return webAppDriver;
	}

	public void setWebAppDriver(WindowsDriver<WindowsElement> webAppDriver) {
		this.webAppDriver = webAppDriver;
	}

	private Process process = null;
	
	
	public void setWebAppDriver() {
		try {
			 DesiredCapabilities capabilities = new DesiredCapabilities();
			 capabilities.setCapability("app", ConfigPropertyLoader.getConfigValue("desktopAppPath"));
			 capabilities.setCapability("platformName","Windows");
			 capabilities.setCapability("deviceName", "WindowsPC");
			 this.webAppDriver = new WindowsDriver<>(new URL(EnvironmentPropertyLoader.getPropertyByName("winapp.driver.listener.port")),capabilities);
		} catch (Exception e) {
			Reporter.log("Unable to initialize WebAppDriver");
		}
	}
	
	public void reInitializeAndSetTopWindow() throws NoSuchElementException, MalformedURLException{
		DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
		desktopCapabilities.setCapability("platformName", "Windows");
		desktopCapabilities.setCapability("deviceName", "WindowsPC");
		desktopCapabilities.setCapability("app", "Root");
		// Get desktop session
		WindowsDriver<WindowsElement> desktopSession = new WindowsDriver<>(new URL(EnvironmentPropertyLoader.getPropertyByName("winapp.driver.listener.port")), desktopCapabilities);
		CommonUtils.sleepForAWhile(5000);
		
		// Here you find the already running application and get the handle
		WebElement smartkeyTitleWebElement = desktopSession.findElementByName("SmartKey - Powered by LoanLogics");
		String smartkeyWindowHandle = smartkeyTitleWebElement.getAttribute("NativeWindowHandle");
		int smartkeyHandleInt = Integer.parseInt(smartkeyWindowHandle);
		String smartkeyHandleHex = Integer.toHexString(smartkeyHandleInt);

		// You attach to the already running application
		DesiredCapabilities topLevelWindowCapabilities = new DesiredCapabilities();
		topLevelWindowCapabilities.setCapability("platformName", "Windows");
		topLevelWindowCapabilities.setCapability("deviceName", "WindowsPC");
		// You set the Handle as one of the capabilities
		topLevelWindowCapabilities.setCapability("appTopLevelWindow", smartkeyHandleHex);

		// Smartkey Application Session
		this.setWebAppDriver( new WindowsDriver<>(new URL(EnvironmentPropertyLoader.getPropertyByName("winapp.driver.listener.port")), topLevelWindowCapabilities));
	}
	/** 
	 * Completely closing current WebAppDriver and corresponding child windows
	 */
	public void closeWebAppDriver(){
		//webAppDriver.quit();
		webAppDriver.quit();
	}
	
	
	/**
	 * Starting WebAppDriver programmatically using Java Runtime class
	 * At this moment (03/10/2022), the process is not getting started properly and observed that the communication is not being happend.
	 * Need further troubleshooting and fix as required
	 * @throws IOException
	 */
	public void startDriver() throws IOException {
		Runtime runtime = Runtime.getRuntime();
		if(process == null) {
			String cmd = ConfigPropertyLoader.getConfigValue("windowsAppDriverPath");
			process = runtime.exec(cmd);
		}
	}
	
	/**
	 * Stop WinAppDriver upon completing the test execution using process handler object
	 * @throws IOException
	 */
	public void stopDriver() {
		if(process != null) {
			process.destroy();
		}
	}
	 public void testNotePadApp(){
		 WinAppDriverUtils.doClickElement(webAppDriver, "Search");
		 WinAppDriverUtils.doClickElement(webAppDriver, "Find...");
		 WinAppDriverUtils.doSendKeys(webAppDriver, "Find what :", "Direct");
		 WinAppDriverUtils.doClickElement(webAppDriver, "▼ Find Next");
	 }
	 
	public static void main(String[] args)  {
		TestWinAppBase base = new TestWinAppBase();
		base.setWebAppDriver();
		//base.testNotePadApp();
	}
}
