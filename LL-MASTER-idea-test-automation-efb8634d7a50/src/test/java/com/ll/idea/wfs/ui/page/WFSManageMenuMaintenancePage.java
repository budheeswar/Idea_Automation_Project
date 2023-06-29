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

public class WFSManageMenuMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	
	
	
	@FindBy(xpath="//b[contains(text(),'Manage Menu')]")
	public WebElement manageMenuPage;
	
	@FindBy(xpath="(//input[@value='New Menu'])[1]")
	public WebElement manageMenu_NewMenuBtn;
	
	@FindBy(xpath="//span[contains(text(),'Add New Menu')]")
	public WebElement manageMenu_AddMenuPopup;
	
	@FindBy(xpath="//input[@id='menuItemName']")
	public WebElement manageMenu_MenuItemName;
	
	@FindBy(xpath="//input[@id='title']")
	public WebElement manageMenu_MenuTitle;
	
	@FindBy(xpath="//input[@id='location']")
	public WebElement manageMenu_Location;
	
	@FindBy(xpath="//select[@id='parentMenuItemId']")
	public WebElement manageMenu_ParentMenu;
	
	@FindBy(xpath="//input[@id='order']")
	public WebElement manageMenu_Order;
	
	@FindBy(xpath="//input[@id='saveButton']")
	public WebElement manageMenu_SaveButton;

	@FindBy(xpath = "//b[contains(text(),'Resources')]")
	public WebElement ManageResourcesPage;

	@FindBy(xpath = "(//input[@value='New Resource'])[1]")
	public WebElement ManageResource_NewResourceBtn;

	@FindBy(xpath = "//div[contains(@id,'addNewResourcePopup')]")
	public WebElement ManageResource_NewResourcePopup;

	@FindBy(xpath = "//input[@id='addResourceName']")
	public WebElement ManageResource_ResourceName;

	@FindBy(xpath = "//textarea[@id='addDescription']")
	public WebElement ManageResource_ResourceDescription;
	
	@FindBy(xpath = "//input[@id='addResourceIdentifier']")
	public WebElement ManageResource_ResourceIdentifier;
	
	@FindBy(xpath = "//input[@id='addResourceSubmit']")
	public WebElement ManageResource_SaveResources;

	@FindBy(xpath = "//table[@id='table1']/thead/tr/th/a")
	public List<WebElement> ManageResource_ResourceTableHeadings;

	@FindBy(xpath = "//table[@id='table1']/tbody/tr")
	public List<WebElement> ManageResource_ResourceRowDetails;
	
	@FindBy(xpath = "//span[contains(text(),'Edit Menu')]")
	public WebElement EditMenuPopUp;
	
	@FindBy(xpath = "//input[@id='menuItemName']")
	public WebElement EditResourceName;
	
	@FindBy(xpath = "//input[@id='saveButton']")
	public WebElement SaveEditResourceButton;
	
	@FindBy(xpath = "//span[contains(text(),'Add New Resource')]")
	public WebElement AddNewResource;
	
	@FindBy(xpath = "//span[contains(text(),'Edit Resource')]")
	public WebElement EditResource;
	
	@FindBy(xpath = "//span[contains(text(),'Change Role')]")
	public WebElement changeRolePopup;
	
	@FindBy(xpath="//select[@id='avaiableRoles']")
	public WebElement availableRolePopup;
	
	@FindBy(xpath="//input[@id='moveRightButtonId']")
	public WebElement moveRightBtn;
	
	@FindBy(xpath="//input[@id='saveButtonChangeRole']")
	public WebElement savetBtnInRole;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]/../../div[2]/p[2]")
	public WebElement ConfirmationPopupMessage;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]/../../div[3]/div/button[contains(text(),'Yes')]")
	public WebElement ConfirmationPopupYesBtn;
	
	public WFSManageMenuMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	/**
	 * This method is used to creater new resource in manage resource page.
	 * @param hashmap
	 * @return
	 * @throws Exception
	 */
	public boolean createNewMenu(HashMap<String, String> hashmap) throws Exception {
		String sMenusName = null;

		boolean isMenuCreated = false;
		try {
			sMenusName = CommonUtils.getUniqueText();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_MENU);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageMenuPage, "Manage Menu Page"));
			reportGenerator.logMessage("Manage Menu is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, manageMenu_NewMenuBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, manageMenu_AddMenuPopup, "Add New Menu Popup"));
			SeleniumUtils.sendKeys(webDriver, manageMenu_MenuItemName, sMenusName);
			SeleniumUtils.sendKeys(webDriver, manageMenu_MenuTitle, hashmap.get("MenuTitle"));
			SeleniumUtils.sendKeys(webDriver, manageMenu_Location, sMenusName);
			SeleniumUtils.selectFromComboBox(webDriver, manageMenu_ParentMenu, hashmap.get("parentmenulist"));
			SeleniumUtils.sendKeys(webDriver, manageMenu_Order, hashmap.get("order"));
			reportGenerator.logAndCaptureScreen("The Add New Menu creation form is filled up with required values",
					"createNewMenu", Status.PASS, webDriver,manageMenu_AddMenuPopup);
			SeleniumUtils.doClick(webDriver, manageMenu_SaveButton);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The Menu creation result is captured", "createNewMenu",
					Status.PASS, webDriver);
			hashmap.put("ResourceName", sMenusName);
			hashmap.put("ResourceIdentifier", sMenusName);
			isMenuCreated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isMenuCreated;
	}

	/**
	 * This method is used to validate created resource in manage resource page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void doValidateCreatedNewMenu(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for (index = 1; index < ManageResource_ResourceRowDetails.size(); index++) {
				String sXpath = "//table[@id='table1']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("ResourceName"))) {
					break;
				}
			}
			String sResourceDetailsXpath = "//table[@id='table1']/tbody/tr[" + index + "]/td";
			WebElement element = webDriver.findElement(By.xpath(sResourceDetailsXpath));
			List<WebElement> ele = webDriver.findElements(By.xpath(sResourceDetailsXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManageResource_ResourceTableHeadings.size(); i++) {
				actualDetails.put(ManageResource_ResourceTableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}
			assertEquals(actualDetails.get("Menu Name"), hashmap.get("ResourceName"));
			reportGenerator.logMessage("Menu Name is displayed as " + actualDetails.get("Menu Name"), Status.PASS);

			assertEquals(actualDetails.get("Menu Title"), hashmap.get("MenuTitle"));
			reportGenerator.logMessage("Menu Title is displayed as " + actualDetails.get("Menu Title"),
					Status.PASS);

//			assertEquals(actualDetails.get("Location"), hashmap.get("ResourceIdentifier"));
//			reportGenerator.logMessage("Location is displayed as " + actualDetails.get("Identifier"),
//					Status.PASS);
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "doValidateCreatedResource",
					Status.PASS, webDriver,element);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * This method is used to update created resource in manage resource page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void doUpdateCreatedMenu(HashMap<String, String> hashmap) throws Exception {
		int index;
		String sUpdatedResourceName;
		try {
			sUpdatedResourceName = CommonUtils.getUniqueText();
			for (index = 1; index < ManageResource_ResourceRowDetails.size(); index++) {
				String sXpath = "//table[@id='table1']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("ResourceName"))) {
					break;
				}
			}
			String sEditResource = "//table[@id='table1']/tbody/tr["+index+"]/td[6]/a[contains(text(),'Edit')]";
			webDriver.findElement(By.xpath(sEditResource)).click();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, EditMenuPopUp, "Edit Menu Popup"));
			SeleniumUtils.sendKeys(webDriver, EditResourceName, sUpdatedResourceName);
			reportGenerator.logAndCaptureScreen("The Menu Name is entered with updated name",
					"doUpdateCreatedMenu", Status.PASS, webDriver,EditMenuPopUp);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, SaveEditResourceButton);
			hashmap.put("ResourceName", sUpdatedResourceName);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * This method is used to update created resource in manage resource page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void doUpadteRoleToCreatedMenu(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for (index = 1; index < ManageResource_ResourceRowDetails.size(); index++) {
				String sXpath = "//table[@id='table1']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("ResourceName"))) {
					break;
				}
			}
			String sRemoveResource = "//table[@id='table1']/tbody/tr["+index+"]/td[6]/a[contains(text(),'Change Role')]";
			webDriver.findElement(By.xpath(sRemoveResource)).click();
			reportGenerator.logAndCaptureScreen("The Change Role Confirmation popup is displayed correctly",
					"doUpadteRoleToCreatedMenu", Status.PASS, webDriver,changeRolePopup);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, changeRolePopup, "Change Role Confirmation Popup"));
			SeleniumUtils.selectFromComboBox(webDriver, availableRolePopup, hashmap.get("NewRoleOption"));
			SeleniumUtils.doClick(webDriver, moveRightBtn);
			reportGenerator.logAndCaptureScreen("The selected Role is displayed in Change Role confirmation popup",
					"doUpadteRoleToCreatedMenu", Status.PASS, webDriver,changeRolePopup);
			SeleniumUtils.doClick(webDriver, savetBtnInRole);
			for (int count = 1; count < ManageResource_ResourceRowDetails.size(); count++) {
				String sXpath = "//table[@id='table1']/tbody/tr[" + count + "]/td[5]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("NewRoleOption"))) {
					break;
				}
			}
			reportGenerator.logAndCaptureScreen("The Role is assigned",
					"doUpadteRoleToCreatedMenu", Status.PASS, webDriver);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
