package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
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

public class WFSVendorMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;

	@FindBy(xpath = "//b[contains(text(),'Manage Vendor')]")
	public WebElement ManageVendorPage;

	@FindBy(xpath = "(//input[@value='New Vendor'])[1]")
	public WebElement ManageVendor_NewVendorBtn;

	@FindBy(xpath = "//div[@id='vendorDiv']")
	public WebElement ManageVendor_NewVendorPopup;

	@FindBy(xpath = "//input[@id='vendorName']")
	public WebElement ManageVendor_VendorName;

	@FindBy(xpath = "//textarea[@id='description']")
	public WebElement ManageVendor_VendorDescription;

	@FindBy(xpath = "//input[@id='saveVendor']")
	public WebElement ManageVendor_SaveVendor;

	@FindBy(xpath = "//div/font[@color='green']")
	public WebElement successMessage;

	@FindBy(xpath = "//table[@id='vendorTable']/thead/tr/th/a")
	public List<WebElement> ManageVendors_VendorTableHeadings;

	@FindBy(xpath = "//table[@id='vendorTable']/tbody/tr")
	public List<WebElement> ManageVendors_VendorRowDetails;

	@FindBy(xpath = "//div[@id='popUpSetUserForVendor']")
	public WebElement ManageVendors_SetUsersToVendorpopup;

	@FindBy(xpath = "//select[@id='availableUsers']")
	public WebElement ManageVendors_AvailableUsers;

	@FindBy(xpath = "//input[@id='moveUserToRightButtonId']")
	public WebElement ManageVendor_MoveUserToRightBtn;

	@FindBy(xpath = "//input[@id='saveButtonChangeUser']")
	public WebElement ManageVendor_SaveBtn;
	
	@FindBy(xpath = "//span[contains(text(),'New Vendor')]")
	public WebElement ManageVendor_NewVendorwindow;
	
	@FindBy(xpath = "//span[contains(text(),'Edit Vendor')]")
	public WebElement ManageVendor_EditVendorwindow;
	
	@FindBy(xpath = "//span[contains(text(),'Set Users To Vendor')]")
	public WebElement ManageVendor_SetUsersToVendorWindow;

	public WFSVendorMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	/**
	 * This method is used to create new vendor with the following deatils.
	 * VendorName, VendorDescription
	 * @param hashmap
	 * @return
	 * @throws Exception
	 */
	public boolean createNewVendor(HashMap<String, String> hashmap) throws Exception {
		String sVendorName = null;

		boolean isVendorCreated = false;
		try {
			sVendorName = CommonUtils.getUniqueText();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_VENDOR);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageVendorPage, "Manage Vendor Page"));
			reportGenerator.logMessage("Manage Vendor is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManageVendor_NewVendorBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageVendor_NewVendorPopup, "New Vendor Popup"));

			SeleniumUtils.sendKeys(webDriver, ManageVendor_VendorName, sVendorName);
			SeleniumUtils.sendKeys(webDriver, ManageVendor_VendorDescription, hashmap.get("VendorDescription"));
			reportGenerator.logAndCaptureScreen("The Vendor creation form is filled up with required values",
					"createNewVendor", Status.PASS, webDriver,ManageVendor_NewVendorwindow);
			SeleniumUtils.doClick(webDriver, ManageVendor_SaveVendor);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The vendor creation result is captured", "createNewVendor",
					Status.PASS, webDriver,successMessage);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, successMessage), hashmap.get("ExpectedResult"));
			
			hashmap.put("VendorName", sVendorName);
			isVendorCreated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isVendorCreated;
	}

	/**
	 * This method is used to validate created vendor details.
	 * VendorName, VendorDescription
	 * @param hashmap
	 * @throws Exception
	 */
	public void doValidateCreatedVendor(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for (index = 1; index < ManageVendors_VendorRowDetails.size(); index++) {
				String sXpath = "//table[@id='vendorTable']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("VendorName"))) {
					break;
				}
			}
			String sVendorDetailsXpath = "//table[@id='vendorTable']/tbody/tr[" + index + "]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(sVendorDetailsXpath));
			WebElement element = webDriver.findElement(By.xpath(sVendorDetailsXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManageVendors_VendorTableHeadings.size(); i++) {
				actualDetails.put(ManageVendors_VendorTableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}
			assertEquals(actualDetails.get("Vendor Name"), hashmap.get("VendorName"));
			reportGenerator.logMessage("Vendor Name is displayed as " + actualDetails.get("Vendor Name"), Status.PASS);

			assertEquals(actualDetails.get("Description"), hashmap.get("VendorDescription"));
			reportGenerator.logMessage("Vendor description is displayed as " + actualDetails.get("Description"),
					Status.PASS);

			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "doValidateCreatedVendor",
					Status.PASS, webDriver,element);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * This method is used to validate updated vendor users details.
	 * @param hashmap
	 * @throws Exception
	 */
	public void validateUpdatedVendorUsers(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for (index = 1; index < ManageVendors_VendorRowDetails.size(); index++) {
				String sXpath = "//table[@id='vendorTable']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("VendorName"))) {
					break;
				}
			}
			String sVendoruserLinkXpath = "//table[@id='vendorTable']/tbody/tr[" + index + "]/td[3]/a[2]";
			webDriver.findElement(By.xpath(sVendoruserLinkXpath)).click();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageVendors_SetUsersToVendorpopup,
					"Set Users To Vendor Popup"));
			SeleniumUtils.explicitWait(webDriver, ManageVendors_AvailableUsers);
			SeleniumUtils.selectFromComboBox(webDriver, ManageVendors_AvailableUsers, hashmap.get("VendorUser"));
			SeleniumUtils.doClick(webDriver, ManageVendor_MoveUserToRightBtn);
			reportGenerator.logAndCaptureScreen("Assign users to Vendor Pop up window is displayed",
					"validateUpdatedVendorUsers", Status.PASS, webDriver,ManageVendor_SetUsersToVendorWindow);
			CommonUtils.sleepForAWhile(15000);
			SeleniumUtils.doClick(webDriver, ManageVendor_SaveBtn);
			CommonUtils.sleepForAWhile(15000);
			String sExpMessage = "Changed users of vendor[" + hashmap.get("VendorName") + "] successfully";
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, successMessage), sExpMessage);
			reportGenerator.logAndCaptureScreen("Success Message is displayed", "validateUpdatedVendorUsers",
					Status.PASS, webDriver,successMessage);

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * This method is used to validate updated vendor details.
	 * @param hashmap
	 * @throws Exception
	 */
	public void updateVendor(HashMap<String, String> hashmap) throws Exception {
		int index;
		String sVendorName = null;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_VENDOR);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageVendorPage, "Manage Vendor Page"));
			reportGenerator.logMessage("Manage Vendor is successfully clicked", Status.PASS);
			for (index = 1; index < ManageVendors_VendorRowDetails.size(); index++) {
				String sXpath = "//table[@id='vendorTable']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("VendorName"))) {
					break;
				}
			}
			String sEditLinkXpath = "//table[@id='vendorTable']/tbody/tr[" + index + "]/td[3]/a[1]";
			webDriver.findElement(By.xpath(sEditLinkXpath)).click();
			sVendorName = CommonUtils.getUniqueText();
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageVendor_NewVendorPopup, "Edit Vendor Popup"));
			SeleniumUtils.sendKeys(webDriver, ManageVendor_VendorName, sVendorName);
			reportGenerator.logAndCaptureScreen("The Vendor Name is updated",
					"updateVendor", Status.PASS, webDriver, ManageVendor_EditVendorwindow);
			SeleniumUtils.doClick(webDriver, ManageVendor_SaveVendor);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The vendor creation result is captured", "updateVendor",
					Status.PASS, webDriver, successMessage);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, successMessage), hashmap.get("ExpectedUpdatedMessage"));
			hashmap.put("VendorName", sVendorName);

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
