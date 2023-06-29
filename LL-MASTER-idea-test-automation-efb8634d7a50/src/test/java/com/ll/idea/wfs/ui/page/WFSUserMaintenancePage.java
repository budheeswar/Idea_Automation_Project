package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.SeleniumUtils;

public class WFSUserMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	
	
	
	@FindBy(xpath="//span[contains(text(),'Edit User')]")
	public WebElement editUser_Dialogbox;
	
	@FindBy(xpath = "//div[@id='myjquerymenu']/ul/li")
	public List<WebElement> iDeaMenuTabs;

	@FindBy(xpath = "//div/ul/li/ul/li/a")
	public List<WebElement> iDEASubMenuLinks;

	@FindBy(id = "userEmail")
	public WebElement createNewUser_userEmailTxtBox;

	@FindBy(id = "confirm-email")
	public WebElement createNewUser_confirmEmailTxtBox;

	@FindBy(id = "firstName")
	public WebElement createNewUser_firstNameTxtBox;

	@FindBy(id = "lastName")
	public WebElement createNewUser_lastNameTxtBox;

	@FindBy(xpath = "//select[@id='vendor']")
	public WebElement createNewUser_vendorOption;

	@FindBy(xpath = "//select[@id='lstRoles']/option")
	public List<WebElement> createNewUser_roleOption;

	@FindBy(xpath = "//select[@id='lstRoles']")
	public WebElement createNewUser_role;

	@FindBy(xpath = "//input[@id='autoPass']")
	public WebElement createNewUser_autoPassChkbx;

	@FindBy(id = "userPassword")
	public WebElement createNewUser_userPasswordTxtBox;

	@FindBy(id = "confirmPass")
	public WebElement createNewUser_confirmPassTxtBox;

	@FindBy(xpath = "//input[@value='Save']")
	public WebElement createNewUser_saveBtn;

	@FindBy(xpath = "//div[@id='dialog-alert']/p[@class='dialog-content']")
	public WebElement createNewUser_errorMessage;

	@FindBy(xpath = "//button[contains(text(),'Ok')]")
	public WebElement okButton;

	@FindBy(xpath = "//div/font[@color='green']")
	public WebElement createNewUser_successMessage;

	@FindBy(xpath = "//input[@id='qUserEmail']")
	public WebElement searchUser_UserEmail;

	@FindBy(id = "qLastName")
	public WebElement searchUser_lastNameTxtBox;

	@FindBy(xpath = "//select[@id='qVendor']")
	public WebElement searchUser_vendorOption;

	@FindBy(xpath = "//input[@id='btnSearch']")
	public WebElement searchUser_searchBtn;

	@FindBy(xpath = "//table[@id='results']/tbody/tr/td[1]")
	public WebElement searchUser_searchResults;

	@FindBy(xpath = "//table[@id='results']/thead/tr/th/a")
	public List<WebElement> searchUser_resultHeadings;

	@FindBy(xpath = "//table[@id='results']/tbody/tr/td")
	public List<WebElement> searchUser_resultDetails;

	@FindBy(xpath = "//div[@id='dialog-alert']/p[2]")
	public WebElement searchUser_dialogueBoxMsg;

	@FindBy(xpath = "//div[@id='dialog-alert']/following-sibling::div/div/button[contains(text(),'Ok')]")
	public WebElement searchUser_dialogueBoxOkBtn;

	@FindBy(xpath = "//a[contains(@id,'editUserAction')]")
	public WebElement searchUser_editUserLink;

	@FindBy(xpath = "//select[@id='editVendor']")
	public WebElement editUser_editVendor;

	@FindBy(xpath = "//input[@id='editFirstName']")
	public WebElement editUser_editFirstName;

	@FindBy(xpath = "//input[@id='saveEditUserButton']")
	public WebElement editUser_saveBtn;

	@FindBy(xpath = "//span[@id='ui-dialog-title-editUserPopup']")
	public WebElement editUser_editUserPopup;

	@FindBy(xpath = "//a[@id='deactive']")
	public WebElement searchUser_deactivateLink;

	@FindBy(xpath = "//div[@id='dialog-confirm']/p[2]")
	public WebElement searchUser_ConfirmationMsg;

	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement searchUser_CnfrmBoxOkBtn;

	@FindBy(xpath = "//td/font[@color='red']")
	public WebElement logIn_failedMsg;

	@FindBy(xpath = "//table[@id='results']/tbody/tr/td")
	public WebElement searchUser_NoUserFound;

	@FindBy(xpath = "(//table[@id='results']/tbody/tr/td/a)[1]")
	public WebElement searchUser_ChangeStatusTo;

	@FindBy(xpath = "//a[@id='activeUser']")
	public WebElement searchUser_activateLink;

	@FindBy(xpath = "//td[contains(text(),'Change Password')]")
	public WebElement logIn_ChagePasswordPage;

	@FindBy(xpath = "//a[contains(text(),'Reset Password')]")
	public WebElement searchUser_ResetPassword;

	@FindBy(xpath = "//a[contains(text(),\"User's Roles\")]")
	public WebElement searchUser_UsersRoles;

	@FindBy(xpath = "//a[contains(text(),\"User's Vendors\")]")
	public WebElement searchUser_UsersVendors;

	@FindBy(id = "popUpAssignRoleUser")
	public WebElement searchUser_AssignRolePopup;

	@FindBy(xpath = "//select[@id='avaiableRoles']")
	public WebElement searchUser_AvailableRoles;

	@FindBy(xpath = "//input[@id='moveRightButtonId']")
	public WebElement searchUser_MoveRightBtn;

	@FindBy(xpath = "//input[@id='saveButtonChangeRole']")
	public WebElement searchUser_ChangeRoleSaveButton;

	@FindBy(id = "popUpSetVendorForUser")
	public WebElement searchUser_SetVendorToUserPopup;

	@FindBy(xpath = "//select[@id='availableVendors']")
	public WebElement searchUser_AvailableVendors;

	@FindBy(xpath = "//input[@id='moveVendorToRightButtonId']")
	public WebElement searchUser_MoveVendorToRightBtn;

	@FindBy(xpath = "//input[@id='saveButtonChangeVendor']")
	public WebElement searchUser_ChangeVendorSaveButton;

	@FindBy(id = "resetPasswordDiv")
	public WebElement searchUser_ResetPasswordPopup;

	@FindBy(xpath = "//input[@class='checkedMail']")
	public WebElement searchUser_EmailResetPassword;

	@FindBy(xpath = "//input[@id='pass']")
	public WebElement searchUser_Password;

	@FindBy(xpath = "//input[@id='confirm_pass']")
	public WebElement searchUser_ConfirmPassword;

	@FindBy(id = "submitResetPass")
	public WebElement searchUser_ResetPasswordSaveBtn;

	@FindBy(xpath = "//a[@title='Logout']")
	public WebElement logOutBtn;

	@FindBy(xpath = "//input[@id='txtOldPassword']")
	public WebElement logIn_OldPassword;

	@FindBy(xpath = "//input[@id='txtNewPassword']")
	public WebElement logIn_NewPassword;

	@FindBy(xpath = "//input[@id='txtRePassword']")
	public WebElement logIn_ReTypePassword;

	@FindBy(xpath = "//input[@name='submit']")
	public WebElement logIn_SubmitBtn;

	@FindBy(xpath = "//div/ul/li/ul/li/ul/li")
	public List<WebElement> iDEASubMenuOptions;
	
	@FindBy(xpath = "//div/font[@color='red']")
	public WebElement errorMessage;

	public WFSUserMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	public void wFSMenuSelection(String mainMenu, String subMenu) {
		try {
			Reporter.log("IDEA Menu Selection");
			String sMenuXpath = "//*[@title='" + mainMenu + "']";
			WebElement mainMenuEle = webDriver.findElement(By.xpath(sMenuXpath));
			Actions action = new Actions(webDriver);
			action.moveToElement(mainMenuEle).build().perform();
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectValueFromList(webDriver, iDEASubMenuLinks, subMenu);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public void wFSMenuSelection(String mainMenu, String subMenu, String subMenuoption) throws Exception {
		try {
			Reporter.log("IDEA Menu "+mainMenu+" Selection");
			String sMenuXpath = "//*[@title='" + mainMenu + "']";
			String sSubMenuXpath = "//*[@title='" + subMenu + "']";
			WebElement mainMenuEle = webDriver.findElement(By.xpath(sMenuXpath));
			WebElement subMenuEle = webDriver.findElement(By.xpath(sSubMenuXpath));
			Actions action = new Actions(webDriver);
			action.moveToElement(mainMenuEle).build().perform();
			CommonUtils.sleepForAWhile(2000);
			action.moveToElement(subMenuEle).build().perform();
			CommonUtils.sleepForAWhile(2000);
			SeleniumUtils.selectValueFromList(webDriver, iDEASubMenuOptions, subMenuoption);
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean createNewUser(HashMap<String, String> hashmap) throws Exception {
		String sUserEmail = null;
		String sFirstName = null;
		String sLastName = null;
		boolean isUserCreated = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.CREATE_NEW_USER);
			sFirstName = "FN_" + CommonUtils.getUniqueText();
			sLastName = "LN_" + CommonUtils.getUniqueText();
			if (hashmap.get("CRUDMode").equalsIgnoreCase("IncorrectMailID")) {
				sUserEmail = sFirstName;
			} else {
				sUserEmail = sFirstName + "@loanlogics.com";
			}
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, createNewUser_userEmailTxtBox, sUserEmail);
			SeleniumUtils.sendKeys(webDriver, createNewUser_confirmEmailTxtBox, sUserEmail);
			SeleniumUtils.sendKeys(webDriver, createNewUser_firstNameTxtBox, sFirstName);
			SeleniumUtils.sendKeys(webDriver, createNewUser_lastNameTxtBox, sLastName);
			SeleniumUtils.selectFromComboBox(webDriver, createNewUser_vendorOption, hashmap.get("VendorOption"));
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, createNewUser_role, hashmap.get("RoleOption"));
			hashmap.put("UserEmail", sUserEmail);
			hashmap.put("LastName", sLastName);
			hashmap.put("FirstName", sFirstName);
			SeleniumUtils.doClick(webDriver, createNewUser_autoPassChkbx);
			SeleniumUtils.sendKeys(webDriver, createNewUser_userPasswordTxtBox, hashmap.get("UserPassword"));
			SeleniumUtils.sendKeys(webDriver, createNewUser_confirmPassTxtBox, hashmap.get("UserPassword"));
			reportGenerator.logAndCaptureScreen("The user creation form is filled up with required values",
					"createNewUser", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, createNewUser_saveBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The user creation result is captured", "createNewUser", Status.PASS,
					webDriver);
			if ((hashmap.get("TypeOfScenario").equalsIgnoreCase("Positive"))||(hashmap.get("CRUDMode").equalsIgnoreCase("CreatingWithExistingUser"))) {
				Assert.assertEquals(SeleniumUtils.getValue(webDriver, createNewUser_successMessage),
						hashmap.get("ExpectedResult"));
				reportGenerator.logAndCaptureScreen("User creation functionality is verified successfully",
						"createNewUser", Status.PASS, webDriver);
				isUserCreated = true;
			} else {
				Assert.assertEquals(SeleniumUtils.getValue(webDriver, searchUser_dialogueBoxMsg),
						hashmap.get("ExpectedResult"));
				reportGenerator.logAndCaptureScreen("User creation functionality is verified successfully",
						"createNewUser", Status.PASS, webDriver);
				SeleniumUtils.doClick(webDriver, searchUser_dialogueBoxOkBtn);
			}

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserCreated;
	}

	public void searchUser(HashMap<String, String> hashmap) throws Exception {
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			System.out.println(hashmap.get("SearchFilter"));
			if ((hashmap.get("SearchFilter")) != null) {
				if ((hashmap.get("SearchFilter")).equalsIgnoreCase("AllFields")) {
					SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
					SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
					SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
				} else if ((hashmap.get("SearchFilter")).equalsIgnoreCase("UserEmail")) {
					SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
				} else if ((hashmap.get("SearchFilter")).equalsIgnoreCase("VendorOption")) {
					SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
				} else if ((hashmap.get("SearchFilter")).equalsIgnoreCase("LastName")) {
					SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
				}
			}
			reportGenerator.logAndCaptureScreen("the required details are filed for search user","searchUser",Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			doValidateSearchUser(hashmap);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public void doValidateSearchUser(HashMap<String, String> hashmap) throws Exception {
		try {
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int index = 0; index < searchUser_resultHeadings.size(); index++) {
				actualDetails.put(searchUser_resultHeadings.get(index).getText().trim(),
						searchUser_resultDetails.get(index).getText().trim());
			}
			assertEquals(actualDetails.get("User Email"), hashmap.get("UserEmail"));
			reportGenerator.logMessage("User Email is displayed as " + actualDetails.get("User Email"), Status.PASS);

			assertEquals(actualDetails.get("First Name"), hashmap.get("FirstName"));
			reportGenerator.logMessage("First Name is displayed as " + actualDetails.get("First Name"), Status.PASS);

			assertEquals(actualDetails.get("Last Name"), hashmap.get("LastName"));
			reportGenerator.logMessage("Last Name is displayed as " + actualDetails.get("Last Name"), Status.PASS);

			assertEquals(actualDetails.get("Vendor"), hashmap.get("VendorOption"));
			reportGenerator.logMessage("Vendor Option is displayed as " + actualDetails.get("Vendor"), Status.PASS);

			assertEquals(actualDetails.get("Assigned Roles"), hashmap.get("RoleOption"));
			reportGenerator.logMessage("Assigned Roles is displayed as " + actualDetails.get("Assigned Roles"),
					Status.PASS);

			assertEquals(SeleniumUtils.getValue(webDriver, searchUser_ChangeStatusTo), hashmap.get("UserStatus"));
			reportGenerator.logMessage("Change Status To fields is displayed as " + actualDetails.get("Change Status To:"),
					Status.PASS);
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "doValidateSearchUser",
					Status.PASS, webDriver);
			reportGenerator.logAndCaptureScreen("the required user details are displayed in search user page","doValidateSearchUser",Status.PASS, webDriver);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public boolean modifyUser(HashMap<String, String> hashmap) throws Exception {
		Boolean isUserModified = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			SeleniumUtils.doClick(webDriver, searchUser_editUserLink);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("the edit user dialogbox is displayed","modifyUser",Status.PASS, webDriver,editUser_Dialogbox);
			CommonUtils.sleepForAWhile();
			String modifiedFirstName = hashmap.get("FirstName").substring(0, hashmap.get("FirstName").length() / 2);
			SeleniumUtils.sendKeys(webDriver, editUser_editFirstName, modifiedFirstName);
			hashmap.put("FirstName", modifiedFirstName);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("the user details are updated in edit user dialogbox","modifyUser",Status.PASS, webDriver,editUser_Dialogbox);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, editUser_saveBtn);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, createNewUser_successMessage),
					hashmap.get("ModifyUserResult"));
			reportGenerator.logAndCaptureScreen("Modify user functionality is verified successfully", "modifyUser",
					Status.PASS, webDriver);
			reportGenerator.logAndCaptureScreen("Modify user functionality is verified successfully","modifyUser",Status.PASS, webDriver);
			isUserModified = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserModified;
	}

	public boolean deactivateUser(HashMap<String, String> hashmap) throws Exception {
		Boolean isUserDeleted = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			SeleniumUtils.doClick(webDriver, searchUser_deactivateLink);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, searchUser_ConfirmationMsg),
					hashmap.get("DeleteUserCnfrmMsg"));
			reportGenerator.logAndCaptureScreen("Confirmation message is display", "deactivateUser", Status.PASS,
					webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_CnfrmBoxOkBtn);
			CommonUtils.sleepForAWhile();
			isUserDeleted = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserDeleted;
	}

	public void wildCardSearch(HashMap<String, String> hashmap) throws Exception {
		String sUserMail = null;
		String sLastName = null;
		
		String[] sUserMailSplit = null;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			if (hashmap.get("TypeOfSearch").equalsIgnoreCase("WildCard")) {
				sUserMailSplit = hashmap.get("UserEmail").split("@");
				sUserMail = sUserMailSplit[0];
				sLastName = hashmap.get("LastName").substring(0, hashmap.get("LastName").length() / 2);
			} else if (hashmap.get("TypeOfSearch").equalsIgnoreCase("SpecialCharacter")) {
				sUserMail = hashmap.get("SpecialCharacter");
				sLastName = hashmap.get("SpecialCharacter");
			}
			if ((hashmap.get("SearchFilter")) != null) {
				if ((hashmap.get("SearchFilter")).equalsIgnoreCase("AllFields")) {
					SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, sUserMail);
					SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, sLastName);
					SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
				} else if ((hashmap.get("SearchFilter")).equalsIgnoreCase("UserEmail")) {
					SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, sUserMail);
				} else if ((hashmap.get("SearchFilter")).equalsIgnoreCase("LastName")) {
					SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, sLastName);
				}
			}
			reportGenerator.logAndCaptureScreen("The search user creation is filled up with wild card values",
					"wildCardSearch", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			if (hashmap.get("TypeOfSearch").equalsIgnoreCase("WildCard")) {
				doValidateSearchUser(hashmap);
				reportGenerator.logAndCaptureScreen("The wild card search result is captured", "wildCardSearch",
						Status.PASS, webDriver);
			} else if (hashmap.get("TypeOfSearch").equalsIgnoreCase("SpecialCharacter")) {
				Assert.assertEquals(SeleniumUtils.getValue(webDriver, searchUser_NoUserFound),
						hashmap.get("SearchExpectedResult"));
				CommonUtils.sleepForAWhile();
				reportGenerator.logAndCaptureScreen("The special Character search result is captured", "wildCardSearch",
						Status.PASS, webDriver);
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public void ReActivateUser(HashMap<String, String> hashmap) throws Exception {
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			SeleniumUtils.doClick(webDriver, searchUser_activateLink);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, searchUser_ConfirmationMsg),
					hashmap.get("ReActivateUserCnfrmMsg"));
			reportGenerator.logAndCaptureScreen("Confirmation message is displayed", "ReActivateUser", Status.PASS,
					webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_CnfrmBoxOkBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("Change Status to is displayed as Deactivate User", "ReActivateUser", Status.PASS,
					webDriver);
			SeleniumUtils.doClick(webDriver, logOutBtn);

			// loginPage.userLogout();
			loginPage.userLogin(hashmap.get("UserEmail"), hashmap.get("UserPassword"));
			if (SeleniumUtils.isDisplayed(webDriver, logIn_ChagePasswordPage, "Change Password Page")) {
				reportGenerator.logAndCaptureScreen("Change password page is displayed", "ReActivateUser", Status.PASS,
						webDriver);
			} else {
				Assert.fail("Not able to  log in with Active User");
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	public boolean updateUserRoles(HashMap<String, String> hashmap) throws Exception {
		Boolean isUserRolesUpdated = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			SeleniumUtils.doClick(webDriver, searchUser_UsersRoles);
			boolean status = SeleniumUtils.isDisplayed(webDriver, searchUser_AssignRolePopup, "Assign Role Popup");
			Assert.assertTrue(status);
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_AvailableRoles, hashmap.get("NewRoleOption"));
			SeleniumUtils.doClick(webDriver, searchUser_MoveRightBtn);
			reportGenerator.logAndCaptureScreen("Assign Role Pop up window is displayed", "updateUserRoles",
					Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_ChangeRoleSaveButton);
			CommonUtils.sleepForAWhile();
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, createNewUser_successMessage),
					hashmap.get("SuccessMessage"));
			reportGenerator.logAndCaptureScreen("Success message is displayed", "updateUserRoles", Status.PASS,
					webDriver);
			String sExpAssignedRoles = hashmap.get("RoleOption") + ", " + hashmap.get("NewRoleOption");
			hashmap.put("RoleOption", sExpAssignedRoles);
			isUserRolesUpdated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserRolesUpdated;
	}

	public boolean updateUserVendors(HashMap<String, String> hashmap) throws Exception {
		Boolean isUserVendorsUpdated = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, searchUser_UsersVendors);
			boolean status = SeleniumUtils.isDisplayed(webDriver, searchUser_SetVendorToUserPopup,
					"Set Vendor To User Popup");
			Assert.assertTrue(status);
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_AvailableVendors, hashmap.get("NewVendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_MoveVendorToRightBtn);
			reportGenerator.logAndCaptureScreen("Set Vendor To User Pop up window is displayed", "updateUserVendors",
					Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_ChangeVendorSaveButton);
			CommonUtils.sleepForAWhile();
			String sExpMessage = "Changed vendors for user[email=" + hashmap.get("UserEmail") + "] successully.";
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, createNewUser_successMessage), sExpMessage);
			reportGenerator.logAndCaptureScreen("Success message is displayed", "updateUserVendors", Status.PASS,
					webDriver);
			String sExpAssignedVendors = hashmap.get("VendorOption") + ", " + hashmap.get("NewVendorOption");
			hashmap.put("VendorOption", sExpAssignedVendors);
			isUserVendorsUpdated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserVendorsUpdated;
	}

	public boolean userResetPassWordFunctionality(HashMap<String, String> hashmap) throws Exception {
		Boolean isUserPasswordReset = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.SEARCH_USER);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_UserEmail, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, searchUser_lastNameTxtBox, hashmap.get("LastName"));
			SeleniumUtils.selectFromComboBox(webDriver, searchUser_vendorOption, hashmap.get("VendorOption"));
			SeleniumUtils.doClick(webDriver, searchUser_searchBtn);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, searchUser_ResetPassword);
			boolean status = SeleniumUtils.isDisplayed(webDriver, searchUser_ResetPasswordPopup, "Reset User Password");
			Assert.assertTrue(status);
			SeleniumUtils.doClick(webDriver, searchUser_EmailResetPassword);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, searchUser_Password, hashmap.get("NewPassword"));
			SeleniumUtils.sendKeys(webDriver, searchUser_ConfirmPassword, hashmap.get("NewPassword"));
			reportGenerator.logAndCaptureScreen("The User Password reset form is filled up with required values",
					"userResetPassWordFunctionality", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, searchUser_ResetPasswordSaveBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The Reset Password updation result is captured",
					"userResetPassWordFunctionality", Status.PASS, webDriver);
			loginPage.userLogout();
			loginPage.userLogin(hashmap.get("UserEmail"), hashmap.get("NewPassword"));
			if (SeleniumUtils.isDisplayed(webDriver, logIn_ChagePasswordPage, "Change Password Page")) {
				reportGenerator.logAndCaptureScreen("Change password page is displayed",
						"userResetPassWordFunctionality", Status.PASS, webDriver);
			} else {
				Assert.fail("Not able to log in with given User");
			}
			SeleniumUtils.sendKeys(webDriver, logIn_OldPassword, hashmap.get("NewPassword"));
			SeleniumUtils.sendKeys(webDriver, logIn_NewPassword, hashmap.get("ChangePassword"));
			SeleniumUtils.sendKeys(webDriver, logIn_ReTypePassword, hashmap.get("ChangePassword"));
			SeleniumUtils.doClick(webDriver, logIn_SubmitBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("User is able to login after resetting the password",
					"userResetPassWordFunctionality", Status.PASS, webDriver);
			isUserPasswordReset = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserPasswordReset;
	}

	public boolean createUserWithExistingUserName(HashMap<String, String> hashmap) throws Exception {
		String sFirstName = null;
		String sLastName = null;
		boolean isUserCreated = false;
		try {
			wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.CREATE_NEW_USER);
			sFirstName = "FN_" + CommonUtils.getUniqueText();
			sLastName = "LN_" + CommonUtils.getUniqueText();
			CommonUtils.sleepForAWhile();
			SeleniumUtils.sendKeys(webDriver, createNewUser_userEmailTxtBox, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, createNewUser_confirmEmailTxtBox, hashmap.get("UserEmail"));
			SeleniumUtils.sendKeys(webDriver, createNewUser_firstNameTxtBox, sFirstName);
			SeleniumUtils.sendKeys(webDriver, createNewUser_lastNameTxtBox, sLastName);
			SeleniumUtils.selectFromComboBox(webDriver, createNewUser_vendorOption, hashmap.get("VendorOption"));
			CommonUtils.sleepForAWhile();
			SeleniumUtils.selectFromComboBox(webDriver, createNewUser_role, hashmap.get("RoleOption"));
			SeleniumUtils.doClick(webDriver, createNewUser_autoPassChkbx);
			SeleniumUtils.sendKeys(webDriver, createNewUser_userPasswordTxtBox, hashmap.get("UserPassword"));
			SeleniumUtils.sendKeys(webDriver, createNewUser_confirmPassTxtBox, hashmap.get("UserPassword"));
			reportGenerator.logAndCaptureScreen("The user creation form is filled up with required values",
					"createUserWithExistingUserName", Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, createNewUser_saveBtn);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The user creation result is captured", "createUserWithExistingUserName", Status.PASS,
					webDriver);
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, errorMessage),
					hashmap.get("ErrorMessage"));
			reportGenerator.logAndCaptureScreen("User creation functionality is verified successfully", "createUserWithExistingUserName",
					Status.PASS, webDriver,errorMessage);
			

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isUserCreated;
	}
}
