import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.record.cf.Threshold;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;
import com.ll.idea.utils.MySQLDBConnection;
import com.ll.idea.utils.SeleniumUtils;
import com.ll.idea.wfs.ui.page.WFSLoginPage;
import com.ll.idea.wfs.ui.page.WFSUserMaintenancePage;

public class WFSCRUDUIPagecountThresholdPage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;
	MySQLDBConnection mysqldbconnection = new MySQLDBConnection();

	@FindBy(xpath = "//b[contains(text(),'Manage Pagecount Threshold')]")
	public WebElement ManagePagecountThresholdPage;

	@FindBy(xpath = "(//input[@value='New Pagecount Threshold'])[1]")
	public WebElement ManagePagecountThreshold_NewPagecountThresholdBtn;

	@FindBy(xpath = "//div[@id='addEditRowForm']")
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

	public WFSCRUDUIPagecountThresholdPage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	/**
	 * This method is used to create new record in dem automation document de table
	 * with the following deatils. DocumentTypeID, ClientProductID, AutomationType
	 * 
	 * @param hashmap
	 * @return
	 * @throws Exception
	 */
	public boolean createNewDemAutomationDocumentDe(HashMap<String, String> hashmap) throws Exception {

		boolean isRecordCreated = false;
		try {
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT,
					IdeaWFSConstants.MANAGE_DEM_AUTOMATION_DOCUMENT_DE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(
					SeleniumUtils.isDisplayed(webDriver, ManagePagecountThresholdPage, "Manage Pagecount Threshold"));
			reportGenerator.logMessage("Manage Pagecount Threshold is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_NewPagecountThresholdBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManagePagecountThreshold_NewPagecountThresholdPopUp,
					"New Pagecount Threshold Popup"));

			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ClientID, hashmap.get("ClientID"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ProductID, hashmap.get("ProductID"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MinPagecount, hashmap.get("MinPagecount"));
			SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_MaxPagecount, hashmap.get("MaxPagecount"));

			reportGenerator.logAndCaptureScreen(
					"The New Dem Automation Document De form is filled up with required values",
					"createNewDemAutomationDocumentDe", Status.PASS, webDriver, ManageDemAutDocDE_NewDemAutDocDEWindow);
			SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_SaveData);
			CommonUtils.sleepForAWhile();
			if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Create")) {
				reportGenerator.logAndCaptureScreen("The Dem Automation Document De data insertion result is captured",
						"createNewDemAutomationDocumentDe", Status.PASS, webDriver, successMessage);

				Assert.assertTrue(
						SeleniumUtils.getValue(webDriver, successMessage).contains(hashmap.get("ExpectedResult")));
				isRecordCreated = true;

			} else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("Duplicate")) {
				Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, dupAlertPopUp, "Duplicate Alert Popup"));
				Assert.assertTrue(
						SeleniumUtils.getValue(webDriver, dupAlertMessage).contains(hashmap.get("DuplicateAlertMsg")));
				reportGenerator.logAndCaptureScreen("Duplicate pop up message captured", "validateDupicateLogic",
						Status.PASS, webDriver, dupAlertMessage);
				SeleniumUtils.doClick(webDriver, dupAlertOkBtn);
				SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_CancelBtn);

			} else if (hashmap.get("DemAutomationDocumentDeFunctionality").equals("IncorrectValue")) {
				Alert alert = webDriver.switchTo().alert();
				String message = alert.getText();
				Assert.assertTrue(message.contains(hashmap.get("ExpectedResult")));
				reportGenerator.logMessage("(ALERT BOX DETECTED) - ALERT MESSAGE : " + message, Status.PASS);
				alert.accept();
				SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ClientID,
						hashmap.get("ClientIDToBeUpdated"));
				SeleniumUtils.sendKeys(webDriver, ManagePagecountThreshold_ProductID,
						hashmap.get("ProductIDToBeUpdated"));
				reportGenerator.logAndCaptureScreen(
						"The New Dem Automation Document De form is filled up with required values",
						"createNewDemAutomationDocumentDe", Status.PASS, webDriver,
						ManageDemAutDocDE_NewDemAutDocDEWindow);
				SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_SaveData);
				CommonUtils.sleepForAWhile();
				alert = webDriver.switchTo().alert();
				message = alert.getText();
				Assert.assertTrue(message.contains(hashmap.get("ExpectedUpdatedMessage")));
				reportGenerator.logMessage("(ALERT BOX DETECTED) - ALERT MESSAGE : " + message, Status.PASS);
				alert.accept();
				SeleniumUtils.doClick(webDriver, ManagePagecountThreshold_CancelBtn);

			}

		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isRecordCreated;
	}

}
