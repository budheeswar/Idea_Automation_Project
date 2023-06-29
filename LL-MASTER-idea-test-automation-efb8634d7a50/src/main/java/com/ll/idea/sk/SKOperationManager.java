package com.ll.idea.sk;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.ll.idea.model.IdeaDocumentType;
import com.ll.idea.reporting.ReportGenerator;
import com.ll.idea.utils.CommonUtils;

import io.appium.java_client.MobileBy.ByAccessibilityId;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class SKOperationManager {
	
	private static final String SMARTKEY_HOME_DATAENTRY_TAB1_MASKED_TEXTBOX1_AUTOMATION_ID="maskedTextBox1";
	private static final String SMARTKEY_HOME_LABEL_WORKITEM_FIELDS = "lblWorkItemFields";
	private static final String SMARTKEY_HOME_LABEL_WORKITEM_PAGES ="lblWorkItemPages";
	private static final String SMARTKEY_HOME_TXBX_LABEL_AUTOMATION_ID = "txbxLabel";
	private static final String SMARTKEY_HOME_TXBX_LAYOUTPANEL1="tableLayoutPanel1";
	private static final String SMARTKEY_OK_BUTTON_AUTOMATION_ID_1="1";
	private static final String SMARTKEY_OK_BUTTON_AUTOMATION_ID_2="2";
	

	public SKOperationManager() {
		//Do nothing
	}
	
	public boolean completeDataEntry(WindowsDriver<WindowsElement> windowsDriver,Map<String,String> mockDataMap , ReportGenerator reportGenerator) throws Exception {
		reportGenerator.logMessage("completeDataEntry", Status.INFO);
		WebElement workItemFields = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_LABEL_WORKITEM_FIELDS);
		String lblWorkItemFields = workItemFields.getText();
		lblWorkItemFields = lblWorkItemFields.substring(lblWorkItemFields.indexOf(":") + 1);
		lblWorkItemFields = lblWorkItemFields.trim();
		int numberOfWorkItemFields = Integer.parseInt(lblWorkItemFields);
		
		String tmpLblName = null;
		WebElement tableLayoutOne = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		List<WebElement> elements = tableLayoutOne.findElements(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1));
		Assert.assertEquals(elements.size()-1, numberOfWorkItemFields);
		int index = 0;
		for(WebElement element : elements) {
			if(index >0) {
				WebElement lblElement  = element.findElement(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_TXBX_LABEL_AUTOMATION_ID));
				tmpLblName = lblElement.getText();
				String mockData = mockDataMap.get(tmpLblName);
				if(tmpLblName != null && tmpLblName.equals("State")) {
					element = element.findElement(ByAccessibilityId.AccessibilityId("1001"));
					element.sendKeys(Keys.ARROW_DOWN);
					element.sendKeys(Keys.TAB);
				} else if(mockData!= null){
					element = element.findElement(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_DATAENTRY_TAB1_MASKED_TEXTBOX1_AUTOMATION_ID));
					element.sendKeys(mockData);
					element.sendKeys(Keys.TAB);
				} else {
					throw new Exception ("mock data is not found. please maintain the list of mock data in SmartkeyDEMockData.csv");
				}
				CommonUtils.sleepForAWhile(3000);
			}
			index ++;
		}
		reportGenerator.logAndCaptureScreen("Data Entry 2 is completed successfully", "completeDataEntry", Status.PASS, windowsDriver);
		return true;
	}
	
	public boolean completeIndexing(WindowsDriver<WindowsElement> windowsDriver, Vector<IdeaDocumentType> documentTypeVector, ReportGenerator reportGenerator) throws Exception {
		
		WebElement workItem = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_LABEL_WORKITEM_PAGES);
		String lblWorkItem = workItem.getText();
		lblWorkItem = lblWorkItem.substring(lblWorkItem.indexOf(":") + 1);
		lblWorkItem = lblWorkItem.trim();
		int numberOfWorkItem = Integer.parseInt(lblWorkItem);

		int tracker = 0;
		WebElement tableLayout1 = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		WebElement documentDropDown = tableLayout1.findElement(ByAccessibilityId.AccessibilityId("1001"));
		String lblPageNumber = "lblPageNumber";
		WebElement currentPageNumber = null;
		IdeaDocumentType ideaDocumentType = null;
		
		while (tracker <= numberOfWorkItem) {
			currentPageNumber = windowsDriver.findElementByAccessibilityId(lblPageNumber);
			tracker = Integer.parseInt(currentPageNumber.getText());
		
			documentDropDown.click();
			ideaDocumentType = documentTypeVector.get(tracker-1);
			if(ideaDocumentType.getDocumentPageNumber().equals("0")) {
				reportGenerator.logAndCaptureScreen("completeIndexing document by document", "completeIndexing", Status.INFO, windowsDriver);
				documentDropDown.sendKeys(ideaDocumentType.getDocumentTypeName());
				documentDropDown.sendKeys(Keys.ENTER);
			} else {
				documentDropDown.sendKeys(Keys.ENTER);
			}
			if (tracker == numberOfWorkItem) {
				tracker++;
			}
		}
		return true;
	}

	public boolean completeDataReview(WindowsDriver<WindowsElement> windowsDriver, Map<String,String> inputMap, ReportGenerator reportGenerator) throws Exception {
		reportGenerator.logMessage("completeDataReview", Status.INFO);
		WebElement workItemFields = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_LABEL_WORKITEM_FIELDS);
		String lblWorkItemFields = workItemFields.getText();
		lblWorkItemFields = lblWorkItemFields.substring(lblWorkItemFields.indexOf(":") + 1);
		lblWorkItemFields = lblWorkItemFields.trim();
		int numberOfWorkItem = Integer.parseInt(lblWorkItemFields);
		
		String tmpLblName = null;
		
		WebElement tableLayoutOne = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		List<WebElement> elements = tableLayoutOne.findElements(ByAccessibilityId.AccessibilityId("tableLayoutPanel1"));
		Assert.assertEquals(elements.size()-1, numberOfWorkItem);
		int index = 0;
		String whoIsCorrect = inputMap.get("WhoIsCorrect");
		for(WebElement element : elements) {
			WebElement lblElement  = element.findElement(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_TXBX_LABEL_AUTOMATION_ID));
			tmpLblName = lblElement.getText();
			if(index >0) {
				if(tmpLblName != null && tmpLblName.equals("State")) {
					element = element.findElement(ByAccessibilityId.AccessibilityId("1001"));
				} else {
					element = element.findElement(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_DATAENTRY_TAB1_MASKED_TEXTBOX1_AUTOMATION_ID));
				}
				if(whoIsCorrect!= null && whoIsCorrect.equals("ML") ) {
					element.sendKeys(Keys.ARROW_UP);
				} else {
					element.sendKeys(Keys.ARROW_DOWN);
				}
				CommonUtils.sleepForAWhile(3000);
			}
			reportGenerator.logAndCaptureScreen("Data review ", "completeDataReview",Status.PASS, windowsDriver);
			index ++;
		}
		return true;
	}
	public boolean completeEIR(WindowsDriver<WindowsElement> windowsDriver, Vector<IdeaDocumentType> documentTypeVector, String actualDocName, String whoIsCorrect, ReportGenerator reportGenerator) throws Exception {
		WebElement tableLayout1 = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		WebElement documentDropDown = null;
		WebElement okButton = null;
		boolean isReviewed = false;
		try {
			for (IdeaDocumentType ideaDocumentType : documentTypeVector) {
				documentDropDown = tableLayout1.findElement(ByAccessibilityId.AccessibilityId("1001"));
				if(ideaDocumentType.getDocumentTypeName().contains(actualDocName)) {
					documentDropDown.click();
					if(whoIsCorrect.equals("ML")) {
						documentDropDown.sendKeys(Keys.ARROW_DOWN);
					} else if(whoIsCorrect.equals("HUMAN")) {
						documentDropDown.sendKeys(Keys.ARROW_UP);
					}
					reportGenerator.logAndCaptureScreen("completeIndexing document by document", "completeIndexing", Status.INFO, windowsDriver);
					
					isReviewed = true;
				} 
				CommonUtils.sleepForAWhile(1000);
				okButton = windowsDriver.findElementByAccessibilityId("1");
				okButton.click();
				if(isReviewed ) {
					break;
				}
			} 
			CommonUtils.sleepForAWhile(1000);
			okButton = windowsDriver.findElementByAccessibilityId("2");
			okButton.click();
		} catch(Exception ex) {
			return false;
		}
		return true;
	}
	public boolean completeIndexingNotMatchingML(WindowsDriver<WindowsElement> windowsDriver, Vector<IdeaDocumentType> documentTypeVector, String actualDocName, String humanMismatchDocName) {
		WebElement workItem = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_LABEL_WORKITEM_PAGES);
		String lblWorkItem = workItem.getText();
		lblWorkItem = lblWorkItem.substring(lblWorkItem.indexOf(":") + 1);
		lblWorkItem = lblWorkItem.trim();
		int numberOfWorkItem = Integer.parseInt(lblWorkItem);
		Reporter.log("completeIndexingNotMatchingML");
		int tracker = 0;
		WebElement tableLayout1 = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		WebElement documentDropDown = tableLayout1.findElement(ByAccessibilityId.AccessibilityId("1001"));
		String lblPageNumber = "lblPageNumber";
		WebElement currentPageNumber = null;
		IdeaDocumentType ideaDocumentType = null;
		
		while (tracker <= numberOfWorkItem) {
			currentPageNumber = windowsDriver.findElementByAccessibilityId(lblPageNumber);
			tracker = Integer.parseInt(currentPageNumber.getText());
			documentDropDown.click();
			ideaDocumentType = documentTypeVector.get(tracker-1);
			if(ideaDocumentType.getDocumentPageNumber().equals("0")) {
				if(ideaDocumentType.getDocumentTypeName().contains(actualDocName)) {
					documentDropDown.sendKeys(humanMismatchDocName);
				} else {
					documentDropDown.sendKeys(ideaDocumentType.getDocumentTypeName());
				}
				documentDropDown.sendKeys(Keys.ENTER);
			} else {
				documentDropDown.sendKeys(Keys.ENTER);
			}
			if (tracker == numberOfWorkItem) {
				tracker++;
			}
		}
		return true;
	}
	
	public void clickGetAndSave(WindowsDriver<WindowsElement> windowsDriver) { 
			WebElement element1 = windowsDriver.findElementByName("Get & Save");
			Actions action = new Actions(windowsDriver);
			action.moveToElement(element1).click().build().perform();
	}

	public boolean completeDataEntryFlow(WindowsDriver<WindowsElement> windowsDriver,Map<String,String> mockDataMap , ReportGenerator reportGenerator) throws Exception {
		reportGenerator.logMessage("completeDataEntry", Status.INFO);
		WebElement workItemFields = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_LABEL_WORKITEM_FIELDS);
		String lblWorkItemFields = workItemFields.getText();
		lblWorkItemFields = lblWorkItemFields.substring(lblWorkItemFields.indexOf(":") + 1);
		lblWorkItemFields = lblWorkItemFields.trim();
		int numberOfWorkItemFields = Integer.parseInt(lblWorkItemFields);
		
		String tmpLblName = null;
		WebElement tableLayoutOne = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		List<WebElement> elements = tableLayoutOne.findElements(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1));
		Assert.assertEquals(elements.size()-1, numberOfWorkItemFields);
		int index = 0;
		for(WebElement element : elements) {
			if(index >0) {
				WebElement lblElement  = element.findElement(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_TXBX_LABEL_AUTOMATION_ID));
				tmpLblName = lblElement.getText();
				String mockData = mockDataMap.get(tmpLblName);
				if(tmpLblName != null && tmpLblName.equals("State")) {
					element = element.findElement(ByAccessibilityId.AccessibilityId("1001"));
					element.sendKeys(Keys.ARROW_DOWN);
					element.sendKeys(Keys.TAB);
				} else if(mockData!= null){
					element = element.findElement(ByAccessibilityId.AccessibilityId(SMARTKEY_HOME_DATAENTRY_TAB1_MASKED_TEXTBOX1_AUTOMATION_ID));
					element.sendKeys(mockData);
						element.sendKeys(Keys.ENTER);
					
				} else {
					throw new Exception ("mock data is not found. please maintain the list of mock data in SmartkeyDEMockData.csv");
				}
				CommonUtils.sleepForAWhile(3000);
			}
			index ++;
		}
		reportGenerator.logAndCaptureScreen("Data Entry is completed successfully", "completeDataEntry", Status.PASS, windowsDriver);
		return true;
	}
public boolean completeMLAI(WindowsDriver<WindowsElement> windowsDriver) throws Exception {
		
		WindowsElement tableLayout1 = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
		WindowsElement documentDropDown = (WindowsElement) tableLayout1.findElement(ByAccessibilityId.AccessibilityId("1001"));
		for(int count = 1;count<=14;count++) {
			documentDropDown.sendKeys(Keys.ENTER);
		}
		
		//The below lines of codes are written for retrieving the color. but not working . hence commented for future reference
		/*
		WindowsElement listBoxValues = windowsDriver.findElementByAccessibilityId("listBox");
		listBoxValues.sendKeys(Keys.ARROW_DOWN);
		File scrFile = ((TakesScreenshot)windowsDriver).getScreenshotAs(OutputType.FILE);
		Point point = listBoxValues.getCenter();
		int centerx = point.getX()  +10;
		int centerY = point.getY() + 10;
		BufferedImage image = ImageIO.read(scrFile);
		this.getPixelColor(image, centerx,centerY).getRGB();
		*/
		return true;
	}
public boolean completeMLAIFlow(WindowsDriver<WindowsElement> windowsDriver) throws Exception {
	
	WindowsElement tableLayout1 = windowsDriver.findElementByAccessibilityId(SMARTKEY_HOME_TXBX_LAYOUTPANEL1);
	WindowsElement documentDropDown = (WindowsElement) tableLayout1.findElement(ByAccessibilityId.AccessibilityId("1001"));
	for(int count = 1;count<=6;count++) {
		documentDropDown.sendKeys(Keys.ENTER);
	}
	return true;
}
	
	protected Color getPixelColor(BufferedImage image, int i, int j) {
	  ColorModel cm = image.getColorModel();
	  Raster raster = image.getRaster();
	  Object pixel = raster.getDataElements(i, j, null);
	  Color actual;
	  if (cm.hasAlpha()) {
	    actual =
	        new Color(
	            cm.getRed(pixel),
	            cm.getGreen(pixel),
	            cm.getBlue(pixel),
	            cm.getAlpha(pixel));
	  } else {
		System.out.println("|Red|" + cm.getRed(pixel) + "|Green|"  + cm.getGreen(pixel) + "|Blue|" + cm.getBlue(pixel));
	    actual = new Color(cm.getRed(pixel), cm.getGreen(pixel), cm.getBlue(pixel), 255);
	  }
	  return actual;
	}


}
