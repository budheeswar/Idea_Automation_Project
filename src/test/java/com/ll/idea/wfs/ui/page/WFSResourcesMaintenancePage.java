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

public class WFSResourcesMaintenancePage {
	private WebDriver webDriver = null;
	private ReportGenerator reportGenerator = null;
	WFSLoginPage loginPage = null;
	WFSUserMaintenancePage userMaintenancePage = null;

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
	
	@FindBy(xpath = "//div[contains(@id,'editResource')]")
	public WebElement EditResourcePopUp;
	
	@FindBy(xpath = "//input[@id='editResourceName']")
	public WebElement EditResourceName;
	
	@FindBy(xpath = "//input[@id='saveEditResourceButton']")
	public WebElement SaveEditResourceButton;
	
	@FindBy(xpath = "//span[contains(text(),'Add New Resource')]")
	public WebElement AddNewResource;
	
	@FindBy(xpath = "//span[contains(text(),'Edit Resource')]")
	public WebElement EditResource;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]")
	public WebElement ConfirmationPopup;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]/../../div[2]/p[2]")
	public WebElement ConfirmationPopupMessage;
	
	@FindBy(xpath = "//span[contains(text(),'Confirmation')]/../../div[3]/div/button[contains(text(),'Yes')]")
	public WebElement ConfirmationPopupYesBtn;
	
	public WFSResourcesMaintenancePage(WebDriver webDriver, ReportGenerator reportGenerator) {
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
	public boolean createNewResource(HashMap<String, String> hashmap) throws Exception {
		String sResourcesName = null;

		boolean isResourceCreated = false;
		try {
			sResourcesName = CommonUtils.getUniqueText();
			userMaintenancePage.wFSMenuSelection(IdeaWFSConstants.SECUTIRY_MANAGEMENT, IdeaWFSConstants.MANAGE_RESOURCE);
			CommonUtils.sleepForAWhile();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageResourcesPage, "Manage resource Page"));
			reportGenerator.logMessage("Manage Vendor is successfully clicked", Status.PASS);
			SeleniumUtils.doClick(webDriver, ManageResource_NewResourceBtn);
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ManageResource_NewResourcePopup, "New Resource Popup"));
			SeleniumUtils.sendKeys(webDriver, ManageResource_ResourceName, sResourcesName);
			SeleniumUtils.sendKeys(webDriver, ManageResource_ResourceDescription, hashmap.get("ResourceDescription"));
			SeleniumUtils.sendKeys(webDriver, ManageResource_ResourceIdentifier, sResourcesName);
			reportGenerator.logAndCaptureScreen("The Resource creation form is filled up with required values",
					"createNewResource", Status.PASS, webDriver,AddNewResource);
			SeleniumUtils.doClick(webDriver, ManageResource_SaveResources);
			CommonUtils.sleepForAWhile();
			reportGenerator.logAndCaptureScreen("The Resource creation result is captured", "createNewResource",
					Status.PASS, webDriver);
			hashmap.put("ResourceName", sResourcesName);
			hashmap.put("ResourceIdentifier", sResourcesName);
			isResourceCreated = true;
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return isResourceCreated;
	}

	/**
	 * This method is used to validate created resource in manage resource page.
	 * @param hashmap
	 * @throws Exception
	 */
	public void doValidateCreatedResource(HashMap<String, String> hashmap) throws Exception {
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
			assertEquals(actualDetails.get("Resource Name"), hashmap.get("ResourceName"));
			reportGenerator.logMessage("Resource Name is displayed as " + actualDetails.get("Resource Name"), Status.PASS);

			assertEquals(actualDetails.get("Description"), hashmap.get("ResourceDescription"));
			reportGenerator.logMessage("Resources description is displayed as " + actualDetails.get("Description"),
					Status.PASS);

			assertEquals(actualDetails.get("Identifier"), hashmap.get("ResourceIdentifier"));
			reportGenerator.logMessage("Resources Identifier is displayed as " + actualDetails.get("Identifier"),
					Status.PASS);
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
	public void doUpdateCreatedResource(HashMap<String, String> hashmap) throws Exception {
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
			String sEditResource = "//table[@id='table1']/tbody/tr["+index+"]/td[4]/a[contains(text(),'Edit')]";
			webDriver.findElement(By.xpath(sEditResource)).click();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, EditResourcePopUp, "Edit Resource Popup"));
			SeleniumUtils.sendKeys(webDriver, EditResourceName, sUpdatedResourceName);
			reportGenerator.logAndCaptureScreen("The Resource Name is entered with updated name",
					"doUpdateCreatedResource", Status.PASS, webDriver,EditResource);
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
	public void doDeleteCreatedResource(HashMap<String, String> hashmap) throws Exception {
		int index;
		try {
			for (index = 1; index < ManageResource_ResourceRowDetails.size(); index++) {
				String sXpath = "//table[@id='table1']/tbody/tr[" + index + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("ResourceName"))) {
					break;
				}
			}
			String sRemoveResource = "//table[@id='table1']/tbody/tr[\"+index+\"]/td[4]/a[contains(text(),'Remove')]";
			webDriver.findElement(By.xpath(sRemoveResource)).click();
			Assert.assertTrue(SeleniumUtils.isDisplayed(webDriver, ConfirmationPopup, "Remove Resource Confirmation Popup"));
			Assert.assertEquals(ConfirmationPopupMessage.getText(), hashmap.get("RemoveResourcePopUP"));
			reportGenerator.logAndCaptureScreen("The Confirmation popup is displayed correctly",
					"doDeleteCreatedResource", Status.PASS, webDriver,ConfirmationPopup);
			
			SeleniumUtils.doClick(webDriver, ConfirmationPopupYesBtn);
			boolean isRemoved = false;
			for (int count = 1; count < ManageResource_ResourceRowDetails.size(); count++) {
				String sXpath = "//table[@id='table1']/tbody/tr[" + count + "]/td[1]";
				String sValue = webDriver.findElement(By.xpath(sXpath)).getText();
				if (sValue.equalsIgnoreCase(hashmap.get("ResourceName"))) {
					isRemoved = true;
					break;
				}
			}
			Assert.assertTrue(isRemoved);
			
			reportGenerator.logAndCaptureScreen("The Resource is removed from the list",
					"doDeleteCreatedResource", Status.PASS, webDriver);
			
		} catch (Exception e) {
			// throw e;
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
