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

public class WFSWorkFlowMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;

	@FindBy(xpath = "//b[contains(text(),'Manage Workflow')]")
	public WebElement ManageWorkFlowPage;

	@FindBy(xpath = "(//input[@value='New Workflow'])[1]")
	public WebElement ManageWorkFlow_NewWorkFlowBtn;

	@FindBy(xpath = "//div[@id='workflowDiv']")
	public WebElement ManageWorkFlow_NewWorkFlowPopup;

	@FindBy(xpath = "//input[@id='workflowName']")
	public WebElement ManageWorkFlow_WorkFlowName;

	@FindBy(xpath = "//textarea[@id='workflowDescription']")
	public WebElement ManageWorkFlow_WorkFlowDescription;
	
	@FindBy(xpath = "//select[@id='workflowType']")
	public WebElement ManageWorkFlow_WorkFlowType;
	
	@FindBy(id = "workflowFile")
	public WebElement ManageWorkFlow_ChooseFile;
	
	@FindBy(xpath = "//input[@id='saveButton']")
	public WebElement ManageWorkFlow_SaveWorkFlow;

	@FindBy(xpath = "//div/font[@color='green']")
	public WebElement successMessage;

	@FindBy(xpath = "//table[@id='tblManageWorkflow']/thead/tr/th/a")
	public List<WebElement> ManageWorkFlow_WorkFlowTableHeadings;
	
	@FindBy(xpath = "//table[@id='tblManageWorkflow']/tbody/tr")
	public List<WebElement> ManageWorkFlow_WorkFlowRowDetails;
	
	
	public WFSWorkFlowMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.webDriver = webDriver;
		this.reportGenerator = reportGenerator;
		loginPage = new WFSLoginPage(this.webDriver, reportGenerator);
		userMaintenancePage = new WFSUserMaintenancePage(this.webDriver, reportGenerator);
		PageFactory.initElements(this.webDriver, this);
	}

	public boolean createNewWorkFlow(HashMap<String, String> hashmap) throws Exception {
		String sWorkFlowName = null;

		boolean isWorkFlowCreated = false;
		try {
			sWorkFlowName = "StandardWithEIR-Copy";
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_WORKFLOW);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageWorkFlowPage, "Manage Work Flow Page"));
			reportGenerator.logMessage("Manage Work Flow is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManageWorkFlow_NewWorkFlowBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageWorkFlow_NewWorkFlowPopup, "New Work Flow Popup"));
			SeleniumUtils.sendKeys(webDriver, ManageWorkFlow_WorkFlowName, sWorkFlowName);
			SeleniumUtils.sendKeys(webDriver, ManageWorkFlow_WorkFlowDescription, hashmap.get("WorkFlowDescription"));
			SeleniumUtils.selectFromComboBox(webDriver, ManageWorkFlow_WorkFlowType, hashmap.get("WorkFlowType"));
			SeleniumUtils.browseAndUploadFile(webDriver, ManageWorkFlow_ChooseFile, "WorkflowXML.xml");//pass the file name
			reportGenerator.logAndCaptureScreen("The Work Flow creation form is filled up with required values",
					"createNewWorkFlow", Status.PASS, webDriver);
			CommonUtils.sleepForAWhile();
			SeleniumUtils.doClick(webDriver, ManageWorkFlow_SaveWorkFlow);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The Work Flow creation result is captured", "createNewWorkFlow",
					Status.PASS, webDriver);
			//Assert.assertEquals(SeleniumUtils.getValue(webDriver, successMessage), hashmap.get("ExpectedResult"));
			hashmap.put("WorkFlowName", sWorkFlowName);
			isWorkFlowCreated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isWorkFlowCreated;
	}

	public void doValidateCreatedWorkFlow(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for(index = 1; index<ManageWorkFlow_WorkFlowRowDetails.size();index++) {
				String sXpath = "//table[@id='tblManageWorkflow']/tbody/tr["+index+"]/td[1]";
				String sValue =  webDriver.findElement(By.xpath(sXpath)).getText();
				if(sValue.equalsIgnoreCase(hashmap.get("VendorName"))) {
					break;
				}
			}
			String sVendorDetailsXpath = "//table[@id='tblManageWorkflow']/tbody/tr["+index+"]/td";
			List<WebElement> ele = webDriver.findElements(By.xpath(sVendorDetailsXpath));
			HashMap<String, String> actualDetails = new HashMap<String, String>();
			for (int i = 0; i < ManageWorkFlow_WorkFlowTableHeadings.size(); i++) {
				actualDetails.put(ManageWorkFlow_WorkFlowTableHeadings.get(i).getText().trim(),
						ele.get(i).getText().trim());
			}
			assertEquals(actualDetails.get("Workflow Name"), hashmap.get("WorkFlowName"));
			reportGenerator.logMessage("Workflow Name is displayed as " + actualDetails.get("Workflow Name"), Status.PASS);

			assertEquals(actualDetails.get("Workflow Description"), hashmap.get("WorkFlowDescription"));
			reportGenerator.logMessage("Workflow description is displayed as " + actualDetails.get("Workflow Description"), Status.PASS);
			
			assertEquals(actualDetails.get("Type"), hashmap.get("WorkFlowType"));
			reportGenerator.logMessage("Workflow Type is displayed as " + actualDetails.get("Type"), Status.PASS);
			
			reportGenerator.logAndCaptureScreen("All details were displayed correctly", "doValidateCreatedVendor",
					Status.PASS, webDriver);
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
}
