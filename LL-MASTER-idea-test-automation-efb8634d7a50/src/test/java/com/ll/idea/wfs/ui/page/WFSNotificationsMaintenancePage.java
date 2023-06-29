package com.ll.idea.wfs.ui.page;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.SeleniumUtils;

public class WFSNotificationsMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;

	@FindBy(xpath = "//b[contains(text(),'Manage Notification')]")
	public WebElement ManageNotificationsPage;

	@FindBy(xpath = "(//td/a[contains(text(),\"Event's Users\")])[1]")
	public WebElement ManageNotification_EventsUsersBtn;

	@FindBy(xpath = "//div[@id='popUpSetUserForNotification']")
	public WebElement ManageNotification_SetUsersForNotificationspopup;
	
	@FindBy(xpath = "//select[@id='availableUsers']")
	public WebElement ManageNotification_AvailableUsers;
	
	@FindBy(xpath = "//select[@id='availableUsers']/option[1]")
	public WebElement ManageNotification_AvailableFirstUser;
	
	@FindBy(xpath = "//input[@id='moveUserToRightButtonId']")
	public WebElement ManageNotification_MoveUserToRightBtn;
	
	@FindBy(xpath = "//input[@id='saveButtonChangeUser']")
	public WebElement ManageNotification_SaveBtn;
	
	@FindBy(xpath = "//div/font[@color='green']")
	public WebElement successMessage;
	
	public WFSNotificationsMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	/**
	 * This method is used to validate event notification fucnctions in manage notifications page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void assignUserForNotifications(HashMap<String, String> hashmap) throws Exception {
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_NOTIFICATION);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageNotificationsPage, "Manage Notifications Page"));
			reportGenerator.logMessage("Manage Notifications is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManageNotification_EventsUsersBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageNotification_SetUsersForNotificationspopup, "Set Users for Notifications Popup"));
			SeleniumUtils.doClick(webDriver, ManageNotification_AvailableFirstUser);
			hashmap.put("UserToAssign", SeleniumUtils.getValue(webDriver, ManageNotification_AvailableFirstUser));
			//SeleniumUtils.selectFromComboBox(webDriver, ManageNotification_AvailableUsers, hashmap.get("UserToAssign"));
			SeleniumUtils.doClick(webDriver, ManageNotification_MoveUserToRightBtn);
			reportGenerator.logAndCaptureScreen("Assign users to Notifications Pop up window is displayed", "validateUpdatedVendorUsers",
					Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, ManageNotification_SaveBtn);
			CommonUtils.sleepForAWhile();
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, successMessage).trim(),
					hashmap.get("ExpectedResult"));
			reportGenerator.logAndCaptureScreen("Success Message is displayed", "assignUserForNotifications",
					Status.PASS, webDriver);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	
	
}
