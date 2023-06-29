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

public class WFSCRUDUIPagecountThresholdMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	MySQLDBConnection mysqldbconnection = new MySQLDBConnection();

	@FindBy(xpath = "//b[contains(text(),'Manage Pagecount Threshold')]")
	public WebElement ManagePagecountThresholdPage;

	@FindBy(xpath = "(//input[@value='New Pagecount Threshold'])[1]")
	public WebElement ManagePagecountThreshold_NewPagecountThresholdBtn;

	//@FindBy(xpath = "//div[@id='addEditRowForm']")
	@FindBy(xpath = "//*[@id=\"addEditRowForm\"]")
	public WebElement ManagePagecountThreshold_NewPagecountThresholdPopUp;

	@FindBy(xpath = "//input[@id='clientId']")
	public WebElement ManagePagecountThreshold_ClientID;

	@FindBy(xpath = "//input[@id='productId']")
	public WebElement ManagePagecountThreshold_ProductID;

	@FindBy(xpath = "//input[@id='minPageCount']")
	public WebElement ManagePagecountThreshold_MinPagecount;

	@FindBy(xpath = "//input[@id='maxPageCount']")
	public WebElement ManagePagecountThreshold_MaxPagecount;

	@FindBy(xpath = "//div[@id='dialog-confirm']/p[2]")
	public WebElement Delete_ConfirmationMsg;

	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	public WebElement Delete_CnfrmBoxOkBtn;

	@FindBy(xpath = "//input[@id='save']")
	public WebElement ManagePagecountThreshold_SaveData;

	@FindBy(xpath = "//input[@id='cancel']")
	public WebElement ManagePagecountThreshold_CancelBtn;

	@FindBy(xpath = "//div/font[@color='green']")
	public WebElement successMessage;

	@FindBy(xpath = "//div[@id='dialog-alert']/p[2]")
	public WebElement dupAlertMessage;

	@FindBy(xpath = "//*[@id='ui-dialog-title-dialog-alert']")
	public WebElement dupAlertPopUp;

	@FindBy(xpath = "/html/body/div[7]/div[3]/div/button")
	
	public WebElement dupAlertOkBtn; 

	@FindBy(xpath = "//table[@id='tblDataList']/thead/tr/th/a")
	public List<WebElement> ManagePagecountThreshold_PagecountThresholdTableHeadings;

	@FindBy(xpath = "//table[@id='tblDataList']/tbody/tr")
	public List<WebElement> ManagePagecountThreshold_ManagePagecountThresholdRowDetails;

	@FindBy(xpath = "//span[contains(text(),'New Pagecount Threshold')]")
	public WebElement ManagePagecountThreshold_NewPagecountThresholdWindow;

	@FindBy(xpath = "//span[contains(text(),'Edit PagecountThreshold')]")
	public WebElement ManagePagecountThreshold_EditPagecountThresholdWindow;

	@FindBy(xpath = "//select[@class='pageSize']")
	public WebElement ManagePagecountThreshold_pageSize;

	public WFSCRUDUIPagecountThresholdMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}
	/**
	 * this method is to validate Pagecount Threshold Link 
	 * @param hashmap
	 */
	public void validatePagecountThresholdLink(HashMap<String, String> hashmap) {
		reportGenerator.logMessage("Login Successfull with valid Credentials", Status.PASS);
		reportGenerator.logMessage("Manage PagecountThreshold Link Displayed", Status.PASS);
		userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT,
				IdeaWFSConstants.MANAGE_PAGECOUNT_THRESHOLD);
		CommonUtils.sleepForAWhile();
		// delete the existing records in PagecountThreshold db
		
		Assert.assertTrue(
				SeleniumUtils.isDisplayed(webDriver, ManagePagecountThresholdPage, "Manage Pagecount Threshold"));
	}

	/**
	 * This method is used to create new record in dem automation document de table
	 * with the following deatils. DocumentTypeID, ClientProductID, AutomationType
	 * 
	 * @param hashmap
	 * @return
	 * @throws Exception
	 */
	public boolean createNewPagecountThreshold(HashMap<String, String> hashmap) throws Exception {

		boolean isRecordCreated = false;
		try {
			
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT,
						IdeaWFSConstants.MANAGE_PAGECOUNT_THRESHOLD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, ManagePagecountThresholdPage, "Manage Pagecount Threshold"));
			reportGenerator.logMessage("Manage PagecountThreshold Link is Displayed", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_NewPagecountThresholdBtn);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver,
			ManagePagecountThreshold_NewPagecountThresholdPopUp, "New PagecountThresholdPopup"));

			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ClientID, hashmap.get("ClientID"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ProductID, hashmap.get("ProductID"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MinPagecount, hashmap.get("MinPagecount"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MaxPagecount, hashmap.get("MaxPagecount"));

			reportGenerator.logAndCaptureScreen("The New pagecount threshold form is filled up with required values",
					"createNewPagecountThresholdValues", Status.PASS, webDriver,
					ManagePagecountThreshold_NewPagecountThresholdWindow);
			SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_SaveData);
			CommonUtils.sleepForAWhile();
			if (hashmap.get("PagecountThresholdFunctionality").equals("Create")) {
				reportGenerator.logAndCaptureScreen("The Pagecount Threshold data insertion result is captured",
						"createdNewPagecountThreshold", Status.PASS, webDriver, successMessage);

				Assert.assertTrue(
						SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedResult")));
				isRecordCreated = true;

			} else if (hashmap.get("PagecountThresholdFunctionality").equals("Duplicate")) {
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, dupAlertPopUp, "Duplicate Alert Popup"));
				System.out.println("****** "+SeleniumUtils.getValue(webDriver, dupAlertMessage));
				Assert.assertTrue(SeleniumUtils.getValue(webDriver, dupAlertMessage).contains(hashmap.get("DuplicateAlertMsg")));
				reportGenerator.logMessage("The ClientID "+hashmap.get("ClientID")+" and ProductID "+hashmap.get("ProductID")+" are already existing.", Status.PASS);
				reportGenerator.logAndCaptureScreen("Duplicate pop up captured", "DupicatePopupCaptured",
						Status.PASS, webDriver, dupAlertMessage);
				SeleniumUtils.doClick(webDriver, dupAlertOkBtn);
				SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_CancelBtn);

			} else if (hashmap.get("PagecountThresholdFunctionality").equals("IncorrectValue")) {
				Alert alert = webDriver.switchTo().alert();
				String message = alert.getText();
				
				Assert.assertTrue(message.contains(hashmap.get("ExpectedResult")));
				reportGenerator.captureScreenshotByRobot(webDriver, "InvalidClientIDPopupScreenshot");
				reportGenerator.logMessage("Invalid ClientID PopUp Screenshot Taken", Status.PASS);

				reportGenerator.logMessage("(ALERT BOX DETECTED) - ALERT MESSAGE : " + message, Status.PASS);
				alert.accept();
				
				SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ClientID,
						hashmap.get("ClientIDToBeUpdated"));
				SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ProductID,
						hashmap.get("ProductIDToBeUpdated"));
				SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MinPagecount,
						hashmap.get("MinPagecountToBeUpdated"));
				SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MaxPagecount,
						hashmap.get("MaxPagecountToBeUpdated"));

				reportGenerator.logAndCaptureScreen(
						"The New Pagecount Threshold form is filled up with required values",
						"newInvalidDetailsEntered", Status.PASS, webDriver,
						ManagePagecountThreshold_NewPagecountThresholdWindow);
				SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_SaveData);
				CommonUtils.sleepForAWhile();
				reportGenerator.captureScreenshotByRobot(webDriver, "InvalidProductIDPopupScreenshotTaken");
				reportGenerator.logMessage("Invalid ProductID PopUp Screenshot Taken", Status.PASS);
				alert = webDriver.switchTo().alert();
				message = alert.getText();
				System.out.println("**** ALERT MSG "+message);
				Assert.assertTrue(message.contains(hashmap.get("ExpectedUpdatedResult")));
				reportGenerator.logMessage("(ALERT BOX DETECTED) - ALERT MESSAGE : " + message, Status.PASS);
				alert.accept();
				reportGenerator.logMessage("Clicked Cancel Button", Status.PASS);
				SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_CancelBtn);

			}

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isRecordCreated;
	}

	/**
	 * This method is used to validate the created Pagecount Threshold record.
	 * clientid, productID, MinPagecount, MaxPagecount
	 * 
	 * @param hashmap
	 * @throws Exception
	 */
	public void doValidateCreatedPagecountThresholdRecord(HashMap<String, String> hashmap) throws Exception {
		int index;
		int totalrecord;
		try {
			for (index = 1; index < ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size(); index++) {
				System.out.println("****** SIZE  "+ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size());
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				System.out.println("******** SVALUE "+sValue+"  hashmap client id value "+hashmap.get("Client"));
				if (sValue.equalsIgnoreCase(hashmap.get("Client"))) {
					break;
				}
			}
			String sPagecountThresholdXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(sPagecountThresholdXpath));
			WebElement element = webDriver.findElement(By.xpath(sPagecountThresholdXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManagePagecountThreshold_PagecountThresholdTableHeadings.size(); i++) {
				actualDetails.put(ManagePagecountThreshold_PagecountThresholdTableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}			
			assertEquals(actualDetails.get("Client"), hashmap.get("Client"));
			reportGenerator.logMessage("Client is displayed as " + actualDetails.get("Client"),
					Status.PASS);

			assertEquals(actualDetails.get("Product"), hashmap.get("Product"));
			reportGenerator.logMessage(" Product is displayed as " + actualDetails.get("Product"),
					Status.PASS);			
			
			assertEquals(actualDetails.get("Min Pagecount"), hashmap.get("MinPagecount"));
			reportGenerator.logMessage("Min Pagecount is displayed as " + (actualDetails.get("Min Pagecount")),
					Status.PASS);
			
			assertEquals(actualDetails.get("Max Pagecount"), hashmap.get("MaxPagecount"));
			reportGenerator.logMessage("Max Pagecount is displayed as " + (actualDetails.get("Max Pagecount")),
					Status.PASS);
			reportGenerator.logAndCaptureScreen("All details were displayed correctly in the UI",
					"ValidateCreatedPagecountThresholdRecord", Status.PASS, webDriver, element);

			totalrecord = mysqldbconnection.checkPagecountThresholdExists(hashmap.get("ClientID"),
					hashmap.get("ProductID"), hashmap.get("MinPagecount"), hashmap.get("MaxPagecount"));

			if (totalrecord == 1) {
				reportGenerator.logMessage("Data is inserted into the DB as expected", Status.PASS);
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

//		/**
//		 * This method is used to validate the updated  clientID and productID details
//		 * @param hashmap
//		 * @throws Exception
//		 */
	public void validateUpdatedPagecountThreshold(HashMap<String, String> hashmap) throws Exception {
		int index;
		int totalrecord;
		try {
			for (index = 1; index < ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size(); index++) {
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("DocumentTypeToBeUpdated"))) {
					break;
				}
			}
			String sPagecountThresholdXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(sPagecountThresholdXpath));
			WebElement element = webDriver.findElement(By.xpath(sPagecountThresholdXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManagePagecountThreshold_PagecountThresholdTableHeadings.size(); i++) {
				actualDetails.put(ManagePagecountThreshold_PagecountThresholdTableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}
			assertEquals(actualDetails.get("Client"), hashmap.get("ClientToBeUpdated"));
			reportGenerator.logMessage("Modified ClientId is displayed as " + actualDetails.get("Client"), Status.PASS);

			assertEquals(actualDetails.get("Product"), hashmap.get("ProductToBeUpdated"));
			reportGenerator.logMessage("Modified ProductID is displayed as " + actualDetails.get("Product"),
					Status.PASS);
			
			assertEquals(actualDetails.get("Min Pagecount"), hashmap.get("MinPagecountToBeUpdated"));
			reportGenerator.logMessage("Modified Min Pagecount is displayed as " + (actualDetails.get("Min Pagecount")),
					Status.PASS);
			
			assertEquals(actualDetails.get("Max Pagecount"), hashmap.get("MaxPagecountToBeUpdated"));
			reportGenerator.logMessage("Modified Max Pagecount is displayed as " + (actualDetails.get("Max Pagecount")),
					Status.PASS);

			reportGenerator.logAndCaptureScreen("The Modified Details were displayed correctly",
					"validateUpdatedPagecountThresholdDetails", Status.PASS, webDriver, element);

			totalrecord = mysqldbconnection.checkPagecountThresholdExists(hashmap.get("ClientIDToBeUpdated"),
					hashmap.get("ProductIDToBeUpdated"), hashmap.get("MinPagecountToBeUpdated"),
					hashmap.get("MaxPagecountToBeUpdated"));

			if (totalrecord == 1) {
				reportGenerator.logMessage("Data is updated in the DB as expected", Status.PASS);
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

//		/**
//		 * This method is used to update Pagecount Threshold details.
//		 * @param hashmap
//		 * @throws Exception
//		 */
	public void UpdatePagecountThreshold(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT,
					IdeaWFSConstants.MANAGE_PAGECOUNT_THRESHOLD);
			CommonUtils.sleepForAWhile();

			for (index = 1; index < ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size(); index++) {
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("ClientId"))) { 
					break;
				}
			}
			String sEditLinkXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[7]/a[1]";
			webDriver.findElement(By.xpath(sEditLinkXpath)).click();
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver,
			 ManagePagecountThreshold_NewPagecountThresholdPopUp, "Edit PagecountThreshold Popup"));
			reportGenerator.logMessage("Edit button clicked ", Status.PASS);
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ClientID, hashmap.get("ClientIDToBeUpdated"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ProductID, hashmap.get("ProductIDToBeUpdated"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MinPagecount, hashmap.get("MinPagecountToBeUpdated"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MaxPagecount, hashmap.get("MaxPagecountToBeUpdated"));
			reportGenerator.logMessage("ClientID updating to "+hashmap.get("ClientIDToBeUpdated")+" and MaxPagecount updating to "+hashmap.get("MaxPagecountToBeUpdated"), Status.PASS);
			reportGenerator.logAndCaptureScreen("The Details are updated", "enteredPagecountThresholdUpdatedValues",
					Status.PASS, webDriver, ManagePagecountThreshold_EditPagecountThresholdWindow);
			SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_SaveData);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The results after saving updated details is captured",
					"savedUpdatingPagecountThresholdValues", Status.PASS, webDriver, successMessage);
			Assert.assertTrue(
					SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedUpdatedMessage")));

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

//		/**
//		 * This method is used to Delete Dem Automation Document De records.
//		 * @param hashmap
//		 * @throws Exception
//		 */
	public void DeletePagecountThreshold(HashMap<String, String> hashmap) throws Exception {
		int index;
		int totalrecord;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT,
					IdeaWFSConstants.MANAGE_PAGECOUNT_THRESHOLD);
			CommonUtils.sleepForAWhile();
			for (index = 1; index < ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size(); index++) {
				String sXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[2]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();

				if (sValue.equalsIgnoreCase(hashmap.get("ClientToBeUpdated"))) {
					break;
				}
			}
			String sDeleteXpath = "//table[@id='tblDataList']/tbody/tr[" + index + "]/td[7]/a[2]";
			reportGenerator.logMessage("Clicking Delete Button", Status.PASS);
			webDriver.findElement(By.xpath(sDeleteXpath)).click();
			CommonUtils.sleepForAWhile();
			Assert.assertEquals(SeleniumUtils.getValue(webDriver, Delete_ConfirmationMsg),
					hashmap.get("DelConfirmationMsg"));
			reportGenerator.logAndCaptureScreen("Delete Confirmation Popup is displayed", "validateDeletePagecountThresholdPopup",
					Status.PASS, webDriver);
			SeleniumUtils.doClick(webDriver, Delete_CnfrmBoxOkBtn);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedUpdatedMessage")));
			reportGenerator.logAndCaptureScreen("After clicking Yes, The Successful Deletion message displayed",
					"successDeletePagecountThreshold", Status.PASS, webDriver);

			totalrecord = mysqldbconnection.checkPagecountThresholdExists(hashmap.get("ClientIDToBeUpdated"),
					hashmap.get("ProductIDToBeUpdated"), hashmap.get("MinPagecount"),
					hashmap.get("MaxPagecount"));

			if (totalrecord == 0) {
				reportGenerator.logMessage("Data is deleted from the DB as expected", Status.PASS);
			}

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

//		/**
//		 * This method is used to validate pagination.
//		 * @param hashmap
//		 * @throws Exception
//		 */
	public void validatePaginationForPagecountThreshold(HashMap<String, String> hashmap) throws Exception {

		try {
			
			int recordsSize = 230;
			boolean isRecordsCreated = mysqldbconnection.createPagecountThreholdRecordsInDB(recordsSize);
			Assert.assertTrue(isRecordsCreated);
			reportGenerator.logMessage("Pagecount Threshold records of "+recordsSize+" records has been created", Status.PASS);
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT,
					IdeaWFSConstants.MANAGE_PAGECOUNT_THRESHOLD);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, ManagePagecountThresholdPage, "Manage Pagecount Threshold"));
			reportGenerator.logMessage("Manage Pagecount Threshold is successfully clicked", Status.PASS);
			Select pageDropDown = new Select(ManagePagecountThreshold_pageSize);
			WebElement defaultPageSize = pageDropDown.getFirstSelectedOption();
			String DefaultSelectedValue = defaultPageSize.getText();
			Assert.assertEquals(DefaultSelectedValue, hashmap.get("DefaultPageSize"));
			reportGenerator.logAndCaptureScreen("The default page size is set to " + hashmap.get("DefaultPageSize"),
					"validatePagination", Status.PASS, webDriver, defaultPageSize);
            
			Assert.assertEquals(Integer.parseInt(DefaultSelectedValue),
					ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size());
			reportGenerator.logMessage("The number of rows displayed on the page ["
					+ ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size()
					+ "] matches with the value selected in the  page size dropdown ", Status.PASS);
			CommonUtils.sleepForAWhile();
			pageDropDown.selectByValue(hashmap.get("ChangePageSize"));
			defaultPageSize = pageDropDown.getFirstSelectedOption();
			DefaultSelectedValue = defaultPageSize.getText();
			Assert.assertEquals(DefaultSelectedValue, hashmap.get("ChangePageSize"));
			reportGenerator.logAndCaptureScreen("The page size is changed to " + hashmap.get("ChangePageSize"),
					"validatePagination", Status.PASS, webDriver, defaultPageSize);
			
			Assert.assertEquals(Integer.parseInt(DefaultSelectedValue),
					ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size()) ;
			reportGenerator.logMessage("The number of rows displayed on the page ["
					+ ManagePagecountThreshold_ManagePagecountThresholdRowDetails.size()
					+ "] matches with the value selected in the  page size dropdown ", Status.PASS);
			// Deleting records
			try {
				mysqldbconnection.deletePagecountThreholdRecordsInDB();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
		
		
	}
	
	


