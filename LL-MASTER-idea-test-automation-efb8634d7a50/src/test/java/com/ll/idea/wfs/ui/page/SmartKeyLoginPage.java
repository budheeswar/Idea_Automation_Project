package com.ll.idea.wfs.ui.page;

import java.net.MalformedURLException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.Status;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.EnvironmentPropertyLoader;
import com.ll.idea.utils.WinAppDriverUtils;
import com.ll.idea.wfs.base.TestWinAppBase;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import junit.framework.Assert;

public class SmartKeyLoginPage extends TestWinAppBase {
	
	
	private final String SMARTKEY_LOGIN_CONNECTION_AUTOMATION_ID_TXT = "1001";
	private final String SMARTKEY_LOGIN_USERNAME_AUTOMATION_ID_TXT= "tbUserName";
	private final String SMARTKEY_LOGIN_PASSWORD_AUTOMATION_ID_TXT= "tbPassword";
	private final String SMARTKEY_LOGIN_OK_AUTOMATION_ID_BTN= "btnOK";
	
	private final String SMARTKEY_HOME_ELIGIBLE_ROLE_AUTOMATION_ID_CMB = "cbOperatorRoles";
	
	public SmartKeyLoginPage(ReportGenerator reportGenerator) {
		//Do initialize setup variables if any
		this.setReportGenerator(reportGenerator);
		this.setWebAppDriver();
	}
	
	public boolean loginToSmartkey(String username, String password) throws InterruptedException,NoSuchElementException, MalformedURLException {
		
		this.getReportGenerator().logMessage("loggin into smart key", Status.INFO);
		WinAppDriverUtils.doSendKeys(this.getWebAppDriver(), SMARTKEY_LOGIN_CONNECTION_AUTOMATION_ID_TXT, 
														EnvironmentPropertyLoader.getPropertyByName("idea_wfs_console_base_url"));
		WinAppDriverUtils.doSendKeys(this.getWebAppDriver(), SMARTKEY_LOGIN_USERNAME_AUTOMATION_ID_TXT, 
														username);
		WinAppDriverUtils.doSendKeys(this.getWebAppDriver(), SMARTKEY_LOGIN_PASSWORD_AUTOMATION_ID_TXT, 
														password);
		WinAppDriverUtils.doClickElement(this.getWebAppDriver(), SMARTKEY_LOGIN_OK_AUTOMATION_ID_BTN);

		this.reInitializeAndSetTopWindow();
		return true;
	}
	
	public void selectRoleAndGetTask(String roleName) throws NoSuchElementException, MalformedURLException{
		WindowsDriver<WindowsElement> windowsDriver =  this.getWebAppDriver();
		WebElement comboplayer = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_ELIGIBLE_ROLE_AUTOMATION_ID_CMB);
		if(this.selectEligibleRole(comboplayer, roleName)) {
			WebElement element1 = windowsDriver.findElementByName("Get & Save");
			Actions action = new Actions(windowsDriver);
			CommonUtils.sleepForAWhile();
			action.moveToElement(element1).click().build().perform();
		} else {
			Assert.fail("Failed at selecting role and get & save operation. Please troubleshoot & retest");
		}
	}
	public boolean selectEligibleRole(WebElement eligibleRoleElement, String roleName) {
		int roleIndex = 0;
		if(roleName.equals("Index")) {
			roleIndex = 1;
		} else if(roleName.equals("Data Entry 1")) {
			roleIndex = 3;
		} else if(roleName.equals("Data Entry 2")) {
			roleIndex = 4;
		} else if(roleName.equals("Data Review")) {
			roleIndex = 5;
		} else if(roleName.equals("EIR")) {
			roleIndex = 17;
		} else if(roleName.equals("MLAI")) {
			roleIndex = 18;
		}
		
		while(roleIndex>0) {
			eligibleRoleElement.sendKeys(Keys.ARROW_DOWN);
			roleIndex--;
		}
		return eligibleRoleElement.getText().equals(roleName);
	}
	
	public void doSaveAndOK() throws Exception{
		this.doSave();
		WebElement btnOK = this.getWebAppDriver().findElementByAccessibilityId("1");
		btnOK.click();
		CommonUtils.sleepForAWhile(5000);
		btnOK = this.getWebAppDriver().findElementByAccessibilityId("2");
		btnOK.click();
	}
	
	public void doUlike2Save() throws Exception {
		WebElement btnOK = this.getWebAppDriver().findElementByAccessibilityId("1");
		btnOK.click();
		CommonUtils.sleepForAWhile(5000);
		btnOK = this.getWebAppDriver().findElementByAccessibilityId("2");
		if(btnOK.isDisplayed()) {
			btnOK.click();	
		}
		
	}
	
	public void doClickOk() throws Exception {
		WebElement okBtn = this.getWebAppDriver().findElementByName("OK");
		CommonUtils.sleepForAWhile(5000);
		if(okBtn.isDisplayed()) {
			okBtn.click();	
		}
		
	}
	
	public void doSaveHandleInvalidSessionId(String expPopupMsg) throws Exception{
		this.doSave();
		WebElement invalidSessionId = this.getWebAppDriver().findElementByAccessibilityId("65535");
		String actualPopupMsg = invalidSessionId.getText();
		
		WebElement btnOK = null;
		if(actualPopupMsg.contains(expPopupMsg)) {
			btnOK = this.getWebAppDriver().findElementByAccessibilityId("2");
			btnOK.click();
		} else {
			throw new Exception("Expected popup message "+ expPopupMsg + " is not observed. Marked as failed for further analysis");
		}
	}
	
	public void doSave() {
		WebElement btnSave = this.getWebAppDriver().findElementByAccessibilityId("btnSave");
		btnSave.click();
		CommonUtils.sleepForAWhile(5000);
	}
	public void logoutSmartkey() {
		Actions action = new Actions(this.getWebAppDriver());
		WebElement logoutElement = this.getWebAppDriver().findElementByName("LOGOUT");
		action.moveToElement(logoutElement).click().build().perform();
		CommonUtils.sleepForAWhile(1000);
		this.getWebAppDriver().findElementByName("OK").click();
	}
	
	
	public static void main(String[] args)  {
		ReportGenerator reportGenerator = new ReportGenerator();
		reportGenerator.setupExtendedReport("C12345", "testWFS");
		SmartKeyLoginPage loginPage = new SmartKeyLoginPage(reportGenerator);
		try {
			loginPage.loginToSmartkey("mike1@aklero.com", "password");
		} catch (InterruptedException | MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			loginPage.selectRoleAndGetTask("Index Review");
		} catch (NoSuchElementException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void validateTheColors() {
		WindowsDriver<WindowsElement> windowsDriver =  this.getWebAppDriver();
		try {
		WebElement element1 = windowsDriver.findElementByName("3. INSURANCE RATING ");
		String cssValue = element1.getCssValue("background-color");
		System.out.println(cssValue);
		CommonUtils.sleepForAWhile(5000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
