package com.ll.idea.wfs.ui.page;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.MySQLDBConnection;
import com.ll.idea.utils.SeleniumUtils;

public class WFSCrudUIDemAtmDocDEMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	MySQLDBConnection mysqldbconnection  = new MySQLDBConnection();
	
	
	@FindBy(xpath = "//b[contains(text(),'Manage Dem Automation Document De')]")
	public WebElement ManageDemAutomationDocumentDePage;
	
	@FindBy(xpath = "(//input[@value='New DemAutomationDocumentDe'])[1]")
	public WebElement ManageDemAutDocDE_NewDemAutDocDEBtn;

	@FindBy(xpath = "//div[@id='addEditRowModal']")
	public WebElement ManageDemAutDocDE_NewDemAutDocDEPopup;

	@FindBy(xpath = "//input[@id='documentTypeId']")
	public WebElement ManageDemAutDocDE_DocumentTypeID;
	
	@FindBy(xpath = "//input[@id='clientProductId']")
	public WebElement ManageDemAutDocDE_ClientProductID;
	
	@FindBy(xpath = "//select[@id='automationTypeId']")
	public WebElement ManageDemAutDocDE_SelectAutomationID;
	
	@FindBy(xpath = "//input[@id='active']")
	public WebElement ManageDemAutDocDE_Active;	
	
	@FindBy(xpath = "//div[@id='dialog-confirm']/p[2]")
	public WebElement Delete_ConfirmationMsg;

	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement Delete_CnfrmBoxOkBtn;
	
	@FindBy(xpath = "//input[@id='save']")
	public WebElement ManageDemAutDocDE_SaveData;
	
	@FindBy(xpath = "//input[@id='cancel']")
	public WebElement ManageDemAutDocDE_CancelBtn;

	@FindBy(xpath = "//div/font[@color='green']")
	public WebElement successMessage;
	
	@FindBy(xpath = "//div[@id='dialog-alert']/p[2]")
	public WebElement dupAlertMessage;
	
	@FindBy(xpath = "//span[@id='ui-dialog-title-dialog-alert']")
	public WebElement dupAlertPopUp;
	
	@FindBy(xpath = "/html/body/div[7]/div[3]/div/button")
	public WebElement dupAlertOkBtn;		

	@FindBy(xpath = "//table[@id='tblDataList']/thead/tr/th/a")
	public List<WebElement> ManageDemAutDocDE_DemAutDocDETableHeadings;

	@FindBy(xpath = "//table[@id='tblDataList']/tbody/tr")
	public List<WebElement> ManageDemAutDocDE_DemAutDocDERowDetails;


	@FindBy(xpath = "//span[contains(text(),'New DemAutomationDocumentDe')]")
	public WebElement ManageDemAutDocDE_NewDemAutDocDEWindow;
	
	@FindBy(xpath = "//span[contains(text(),'Edit DemAutomationDocumentDe')]")
	public WebElement ManageDemAutDocDE_EditDemAutDocDEWindow;
	
	@FindBy(xpath = "//select[@class='pageSize']")
	public WebElement ManageDemAutDocDE_pageSize;

	public WFSCrudUIDemAtmDocDEMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	/**
	 * This method is used to create new record in dem automation document de table with the following deatils.
	 * DocumentTypeID, ClientProductID, AutomationType
	 * @param hashmap
	 * @return
	 * @throws Exception
	 */
	public boolean createNewDemAutomationDocumentDe(HashMap<String, String> hashmap) throws Exception {
		
		boolean isRecordCreated = false;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_DEM_AUTOMATION_DOCUMENT_DE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageDemAutomationDocumentDePage, "Manage Dem Automation Document De"));
			reportGenerator.logMessage("Manage Dem Automation Document De is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_NewDemAutDocDEBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageDemAutDocDE_NewDemAutDocDEPopup, "New DemAutomationDocumentDe Popup"));

			SeleniumUtils.sendKeys(webDriver, ManageDemAutDocDE_DocumentTypeID, hashmap.get("DocumentTypeID"));
			SeleniumUtils.sendKeys(webDriver, ManageDemAutDocDE_ClientProductID, hashmap.get("ClientProductID"));
			SeleniumUtils.selectFromComboBox(webDriver, ManageDemAutDocDE_SelectAutomationID, hashmap.get("AutomationType"));
			SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_Active);
			reportGenerator.logAndCaptureScreen("The New Dem Automation Document De form is filled up with required values",
					"createNewDemAutomationDocumentDe", Status.PASS, webDriver,ManageDemAutDocDE_NewDemAutDocDEWindow);
			SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_SaveData);
			CommonUtils.sleepForAWhile();
			if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Create")) {
				reportGenerator.logAndCaptureScreen("The Dem Automation Document De data insertion result is captured", "createNewDemAutomationDocumentDe",
						Status.PASS, webDriver,successMessage);
						
				Assert.assertTrue(SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedResult")));			
				isRecordCreated = true;
				
			}else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Duplicate")) {
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, dupAlertPopUp, "Duplicate Alert Popup"));
				Assert.assertTrue(SeleniumUtils.getValue(webDriver, dupAlertMessage).contains(hashmap.get("DuplicateAlertMsg")));
				reportGenerator.logAndCaptureScreen("Duplicate pop up message captured", "validateDupicateLogic",
						Status.PASS, webDriver,dupAlertMessage);
				SeleniumUtils.doClick(webDriver, dupAlertOkBtn);
				SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_CancelBtn);
			
			}else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("IncorrectValue")) {
				Alert alert = webDriver.switchTo().alert();
				String message = alert.getText();
				Assert.assertTrue(message.contains(hashmap.get("ExpectedResult")));
				reportGenerator.logMessage("(ALERT BOX DETECTED) - ALERT MESSAGE : " +message, Status.PASS);				
				alert.accept();								
				SeleniumUtils.sendKeys(webDriver, ManageDemAutDocDE_DocumentTypeID, hashmap.get("DocTypeIDToBeUpdated"));
				SeleniumUtils.sendKeys(webDriver, ManageDemAutDocDE_ClientProductID, hashmap.get("ClientProductIDToBeUpdated"));
				reportGenerator.logAndCaptureScreen("The New Dem Automation Document De form is filled up with required values",
						"createNewDemAutomationDocumentDe", Status.PASS, webDriver,ManageDemAutDocDE_NewDemAutDocDEWindow);
				SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_SaveData);
				CommonUtils.sleepForAWhile();
				alert = webDriver.switchTo().alert();
				message = alert.getText();
				Assert.assertTrue(message.contains(hashmap.get("ExpectedUpdatedMessage")));
				reportGenerator.logMessage("(ALERT BOX DETECTED) - ALERT MESSAGE : " +message, Status.PASS);
				alert.accept();
				SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_CancelBtn);
			
			}
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isRecordCreated;
	}

	/**
	 * This method is used to validate the created dem automated Document DE record.
	 * DocumentType, Client product, AutomationType, Active
	 * @param hashmap
	 * @throws Exception
	 */
	public void doValidateCreatedDemAutDocDERecord(HashMap<String, String> hashmap) throws Exception {
		int index;
		int totalrecord;
		try {
			for (index = 1; index < ManageDemAutDocDE_DemAutDocDERowDetails.size(); index++) {
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("DocumentType"))) {
					break;
				}
			}
			String sDemAutDocDEsXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(sDemAutDocDEsXpath));
			WebElement element = webDriver.findElement(By.xpath(sDemAutDocDEsXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManageDemAutDocDE_DemAutDocDETableHeadings.size(); i++) {
				actualDetails.put(ManageDemAutDocDE_DemAutDocDETableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}
			assertEquals(actualDetails.get("Document Type"), hashmap.get("DocumentType"));
			reportGenerator.logMessage("Document Type is displayed as " + actualDetails.get("Document Type"), Status.PASS);

			assertEquals(actualDetails.get("Client Product"), hashmap.get("ClientProduct"));
			reportGenerator.logMessage("Client Product is displayed as " + actualDetails.get("Client Product"),
					Status.PASS);
			
			assertEquals(actualDetails.get("Automation Type"), hashmap.get("AutomationType"));
			reportGenerator.logMessage("Automation Type is displayed as " + actualDetails.get("Automation Type"),
					Status.PASS);
			
			assertEquals(actualDetails.get("Active"), hashmap.get("Active"));
			reportGenerator.logMessage("Active value is displayed as " + actualDetails.get("Active"),
					Status.PASS);

			reportGenerator.logAndCaptureScreen("All details were displayed correctly in the UI", "doValidateCreatedDemAutDocDERecord",
					Status.PASS, webDriver,element);

			totalrecord=mysqldbconnection.checkDemAutomationDocumentDeExists(hashmap.get("DocumentTypeID"), hashmap.get("ClientProductID"),hashmap.get("AutomationTypeID"),hashmap.get("ActiveID"));
			
			if (totalrecord == 1) {
				reportGenerator.logMessage("Data is inserted into the DB as expected",
						Status.PASS);
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

//	/**
//	 * This method is used to validate the updated Document type and client product details
//	 * @param hashmap
//	 * @throws Exception
//	 */
	public void validateUpdatedDemAutomationDocumentDe(HashMap<String, String> hashmap) throws Exception {
		int index;
		int totalrecord;
	try {
		for (index = 1; index < ManageDemAutDocDE_DemAutDocDERowDetails.size(); index++) {
			String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
			String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
			if (sValue.equalsIgnoreCase(hashmap.get("DocumentTypeToBeUpdated"))) {
				break;
			}
		}
		String sDemAutDocDEsXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td";
		List<WebElement> ele = webDriver.findElements(By.xpath(sDemAutDocDEsXpath));
		WebElement element = webDriver.findElement(By.xpath(sDemAutDocDEsXpath));
		HashMap<String, String> actualDetails = new HashMap<String, String>();
		for (int i = 0; i < ManageDemAutDocDE_DemAutDocDETableHeadings.size(); i++) {
			actualDetails.put(ManageDemAutDocDE_DemAutDocDETableHeadings.get(i).getText().trim(),
					ele.get(i).getText().trim());
		}
		assertEquals(actualDetails.get("Document Type"), hashmap.get("DocumentTypeToBeUpdated"));
		reportGenerator.logMessage("Document Type is displayed as " + actualDetails.get("Document Type"), Status.PASS);

		assertEquals(actualDetails.get("Client Product"), hashmap.get("ClientProductToBeUpdated"));
		reportGenerator.logMessage("Client Product is displayed as " + actualDetails.get("Client Product"),
				Status.PASS);

		reportGenerator.logAndCaptureScreen("All details were displayed correctly", "validateUpdatedDemAutomationDocumentDe",
				Status.PASS, webDriver,element);
		
		totalrecord=mysqldbconnection.checkDemAutomationDocumentDeExists(hashmap.get("DocTypeIDToBeUpdated"), hashmap.get("ClientProductIDToBeUpdated"),hashmap.get("AutomationTypeID"),hashmap.get("ActiveID"));
		
		if (totalrecord == 1) {
			reportGenerator.logMessage("Data is updated in the DB as expected",
					Status.PASS);
		}
	} catch (Exception e) {
		// throw e;
		e.printStackTrace();
		Assert.fail(e.getMessage());
	}
	}
	
//	/**
//	 * This method is used to update Dem Automation Document De details.
//	 * @param hashmap
//	 * @throws Exception
//	 */
	public void UpdateDemAutomationDocumentDe(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_DEM_AUTOMATION_DOCUMENT_DE);
			CommonUtils.sleepForAWhile();
			
			for (index = 1; index < ManageDemAutDocDE_DemAutDocDERowDetails.size(); index++) {
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				
				System.out.println(sValue);
				if (sValue.equalsIgnoreCase(hashmap.get("DocumentType"))) {
					break;
				}
			}
			String sEditLinkXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[7]/a[1]";
			webDriver.findElement(By.xpath(sEditLinkXpath)).click();
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageDemAutDocDE_NewDemAutDocDEPopup, "Edit DemAutomationDocumentDe Popup"));
			SeleniumUtils.sendKeys(webDriver, ManageDemAutDocDE_DocumentTypeID, hashmap.get("DocTypeIDToBeUpdated"));
			SeleniumUtils.sendKeys(webDriver, ManageDemAutDocDE_ClientProductID, hashmap.get("ClientProductIDToBeUpdated"));
			reportGenerator.logAndCaptureScreen("The DocumentType and ClientProduct are updated",
					"UpdateDemAutomationDocumentDe", Status.PASS, webDriver, ManageDemAutDocDE_EditDemAutDocDEWindow);
			SeleniumUtils.doClick(webDriver, ManageDemAutDocDE_SaveData);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The results after DocumentType and ClientProduct update is captured", "UpdateDemAutomationDocumentDe",
					Status.PASS, webDriver, successMessage);
			Assert.assertTrue(SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedUpdatedMessage")));

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
//	/**
//	 * This method is used to Delete Dem Automation Document De records.
//	 * @param hashmap
//	 * @throws Exception
//	 */
	public void DeleteDemAutomationDocumentDe(HashMap<String, String> hashmap) throws Exception {
		int index;
		int totalrecord;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_DEM_AUTOMATION_DOCUMENT_DE);
			CommonUtils.sleepForAWhile();
			for (index = 1; index < ManageDemAutDocDE_DemAutDocDERowDetails.size(); index++) {
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				
				if (sValue.equalsIgnoreCase(hashmap.get("DocumentTypeToBeUpdated"))) {
					break;
				}
			}
			String sDeleteXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[7]/a[2]";
			webDriver.findElement(By.xpath(sDeleteXpath)).click();
			CommonUtils.sleepForAWhile();
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, Delete_ConfirmationMsg),
					hashmap.get("DelConfirmationMsg"));
			reportGenerator.logAndCaptureScreen("Confirmation message is displayed", "DeleteDemAutomationDocumentDe", Status.PASS,
					webDriver);
			SeleniumUtils.doClick(webDriver, Delete_CnfrmBoxOkBtn);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedUpdatedMessage")));
			reportGenerator.logAndCaptureScreen("Successful Deletion message displayed", "DeleteDemAutomationDocumentDe", Status.PASS,
					webDriver);
			
			totalrecord=mysqldbconnection.checkDemAutomationDocumentDeExists(hashmap.get("DocTypeIDToBeUpdated"), hashmap.get("ClientProductIDToBeUpdated"),hashmap.get("AutomationTypeID"),hashmap.get("ActiveID"));
			
			if (totalrecord == 0) {
				reportGenerator.logMessage("Data is deleted from the DB as expected",
						Status.PASS);
			}

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}	
	
//	/**
//	 * This method is used to validate pagination.
//	 * @param hashmap
//	 * @throws Exception
//	 */
	public void validatePagination(HashMap<String, String> hashmap) throws Exception {
		
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_DEM_AUTOMATION_DOCUMENT_DE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageDemAutomationDocumentDePage, "Manage Dem Automation Document De"));
			reportGenerator.logMessage("Manage Dem Automation Document De is successfully clicked", Status.PASS);
			Select pageDropDown = new Select(ManageDemAutDocDE_pageSize);
			WebElement defaultPageSize = pageDropDown.getFirstSelectedOption();
			String DefaultSelectedValue = defaultPageSize.getText();
			Assert.assertEquals(DefaultSelectedValue, hashmap.get("DefaultPageSize"));
			reportGenerator.logAndCaptureScreen("The default page size is set to "+hashmap.get("DefaultPageSize"), "validatePagination",
					Status.PASS, webDriver,defaultPageSize);
			
			Assert.assertEquals(Integer.parseInt(DefaultSelectedValue), ManageDemAutDocDE_DemAutDocDERowDetails.size());
			reportGenerator.logMessage("The number of rows displayed on the page ["+ManageDemAutDocDE_DemAutDocDERowDetails.size()+"] matches with the value selected in the  page size dropdown ",Status.PASS);
			pageDropDown.selectByValue(hashmap.get("ChangePageSize"));
			defaultPageSize = pageDropDown.getFirstSelectedOption();
			 DefaultSelectedValue = defaultPageSize.getText();
			Assert.assertEquals(DefaultSelectedValue, hashmap.get("ChangePageSize"));
			reportGenerator.logAndCaptureScreen("The page size is changed to "+hashmap.get("ChangePageSize"), "validatePagination",
					Status.PASS, webDriver,defaultPageSize);
			 Assert.assertEquals(Integer.parseInt(DefaultSelectedValue), ManageDemAutDocDE_DemAutDocDERowDetails.size());
			 reportGenerator.logMessage("The number of rows displayed on the page ["+ManageDemAutDocDE_DemAutDocDERowDetails.size()+"] matches with the value selected in the  page size dropdown ",Status.PASS);
			
			
			

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}	

}
