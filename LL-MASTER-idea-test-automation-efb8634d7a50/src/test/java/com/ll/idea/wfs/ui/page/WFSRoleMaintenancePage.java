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

public class WFSRoleMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;

	@FindBy(xpath = "//b[contains(text(),'Manage Role')]")
	public WebElement ManageRolePage;

	@FindBy(xpath = "(//input[@value='New Role'])[1]")
	public WebElement ManageRole_NewRoleBtn;

	@FindBy(xpath = "//div[@id='addNewRowDiv']")
	public WebElement ManageRole_AddNewRolePopup;

	@FindBy(xpath = "//input[@id='roleName']")
	public WebElement ManageRole_RoleName;

	@FindBy(xpath = "//textarea[@id='roleDescription']")
	public WebElement ManageRole_RoleDescription;

	@FindBy(xpath = "//input[@id='saveButton']")
	public WebElement ManageRole_SaveRole;
	
	@FindBy(xpath = "//select[@id='availableRoles']")
	public WebElement ManageRole_AvailableRoles;
	
	@FindBy(id = "moveRoleToRightButtonId")
	public WebElement ManageRole_MoveRoleToRight;
	
	@FindBy(xpath = "//table[@id='table1']/thead/tr/th/a")
	public List<WebElement> ManageRole_RolesTableHeadings;
	
	@FindBy(xpath = "//table[@id='table1']/tbody/tr")
	public List<WebElement> ManageRole_RolesRowDetails;
	
	@FindBy(xpath = "//span[contains(text(),'Add New Role')]")
	public WebElement ManageRole_AddNewRoles;
	
	@FindBy(xpath = "//span[contains(text(),'Edit Role')]")
	public WebElement ManageRole_EditRole;

	public WFSRoleMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	/**
	 * This method is used to validate new role creation in manage role dashboard page.
	 * @param hashmap
	 * @return
	 * @throws Exception
	 */
	public boolean createNewRole(HashMap<String, String> hashmap) throws Exception {
		String sRoleName = null;

		boolean isRoleCreated = false;
		try {
			sRoleName = CommonUtils.getUniqueText();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_ROLE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageRolePage, "Manage Role Page"));
			reportGenerator.logMessage("Manage Role is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManageRole_NewRoleBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageRole_AddNewRolePopup, "New Role Popup"));
			SeleniumUtils.sendKeys(webDriver, ManageRole_RoleName, sRoleName);
			SeleniumUtils.sendKeys(webDriver, ManageRole_RoleDescription, hashmap.get("RoleDescription"));
			SeleniumUtils.selectFromComboBox(webDriver, ManageRole_AvailableRoles, hashmap.get("AvailableRole"));
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver,ManageRole_MoveRoleToRight );
			reportGenerator.logAndCaptureScreen("The Role creation form is filled up with required values",
					"createNewRole", Status.PASS, webDriver, ManageRole_AddNewRoles);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, ManageRole_SaveRole);
			CommonUtils.sleepForAWhile();
			hashmap.put("RoleName", sRoleName);
			isRoleCreated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isRoleCreated;
	}

	/**
	 * Validating created role validation in manage role dashboard page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void doValidateCreatedRole(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for(index = 1; index<ManageRole_RolesRowDetails.size();index++) {
				String sXpath = "//table[@id='table1']/tbody/tr["+index+"]/td[1]";
				String sValue =  webDriver.findElement(By.xpath(sXpath)).getText();
				if(sValue.equalsIgnoreCase(hashmap.get("RoleName"))) {
					break;
				}
			}
			String sVendorDetailsXpath = "//table[@id='table1']/tbody/tr["+index+"]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(sVendorDetailsXpath));
			WebElement element = webDriver.findElement(By.xpath(sVendorDetailsXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManageRole_RolesTableHeadings.size(); i++) {
				actualDetails.put(ManageRole_RolesTableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}
			assertEquals(actualDetails.get("Role Name"), hashmap.get("RoleName"));
			reportGenerator.logMessage("Role Name is displayed as " + actualDetails.get("Role Name"), Status.PASS);

			assertEquals(actualDetails.get("Role Description"), hashmap.get("RoleDescription"));
			reportGenerator.logMessage("Role description is displayed as " + actualDetails.get("Role Description"), Status.PASS);
			
			assertEquals(actualDetails.get("Parent Role Name"), hashmap.get("AvailableRole"));
			reportGenerator.logMessage("Parent Role Name is displayed as " + actualDetails.get("Parent Role Name"), Status.PASS);
			
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "doValidateCreatedRole",
					Status.PASS, webDriver,element);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	/**
	 * Updating the created role in manage role dashboard page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void doUpdateCreatedRole(HashMap<String, String> hashmap) throws Exception {
		int index;
		String sUpdatedRoleName;
		try {
			sUpdatedRoleName = CommonUtils.getUniqueText();
			for(index = 1; index<ManageRole_RolesRowDetails.size();index++) {
				String sXpath = "//table[@id='table1']/tbody/tr["+index+"]/td[1]";
				String sValue =  webDriver.findElement(By.xpath(sXpath)).getText();
				if(sValue.equalsIgnoreCase(hashmap.get("RoleName"))) {
					break;
				}
			}
			String sEditRole = "//table[@id='table1']/tbody/tr["+index+"]/td[5]/a[contains(text(),'Edit')]";
			webDriver.findElement(By.xpath(sEditRole)).click();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageRole_AddNewRolePopup, "Edit Role Popup"));
			SeleniumUtils.sendKeys(webDriver, ManageRole_RoleName, sUpdatedRoleName);
			reportGenerator.logAndCaptureScreen("The Role Name is entered with updated name",
					"doUpdateCreatedRole", Status.PASS, webDriver,ManageRole_EditRole);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, ManageRole_SaveRole);
			hashmap.put("RoleName", sUpdatedRoleName);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
		
}
