package com.ll.idea.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ll.idea.constants.IdeaWFSConstants;


public class SeleniumUtils {
	
	private SeleniumUtils() {
		//prevent instantiation of this object from outside of this class
	}

	/**
	 * To send the Data for the Particular field
	 * @param element  Holds the Web element
	 * @param KeyValue Holds the String Value
	 */
	public static void sendKeys(WebDriver webDriver, WebElement element, String keyValue) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript(IdeaWFSConstants.JS_ARG_SCROLL_VIEW, element);
			WebDriverWait webWait = new WebDriverWait(webDriver, 
											Integer.parseInt(ConfigPropertyLoader.getConfigValue(IdeaWFSConstants.JS_LOADING_TIME)));
			webWait.until(ExpectedConditions.elementToBeClickable(element));
			webWait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			Actions action = new Actions(webDriver);
			action.moveToElement(element).doubleClick().build().perform();
			element.sendKeys(keyValue);
		} catch (Exception ex) {
			Reporter.log("Exception occured while entering value into an UI component " + ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * This method is will performs click action on the element
	 * @param element Holds the Web element
	 * @return return the boolean value
	 */
	public static boolean doClick(WebDriver webDriver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		boolean isClicked = false;
		try {
			js.executeScript(IdeaWFSConstants.JS_ARG_SCROLL_VIEW, element);
			WebDriverWait webWait = new WebDriverWait(webDriver, Integer.parseInt(ConfigPropertyLoader.getConfigValue(IdeaWFSConstants.JS_LOADING_TIME)));
			webWait.until(ExpectedConditions.elementToBeClickable(element));
			webWait.until(ExpectedConditions.visibilityOf(element));

			Actions action = new Actions(webDriver);
			action.moveToElement(element).click().build().perform();
			isClicked = true;
		}  catch (Exception ex) {
			Reporter.log("Exception occured while doClick event " + ex.getMessage());
			throw ex;
		}
		return isClicked;
	}

	/**
	 * This method helps to get text from web element
	 * @param driver The WebDriver
	 * @param element  Holds the web element
	 * 
	 * @return Returns the String
	 */
	public static String getValue(WebDriver webDriver, WebElement element){
		String actualValue = null;
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		try {
			WebDriverWait webWait = new WebDriverWait(webDriver, Integer.parseInt(ConfigPropertyLoader.getConfigValue(IdeaWFSConstants.JS_LOADING_TIME)));
			webWait.until(ExpectedConditions.elementToBeClickable(element));
			webWait.until(ExpectedConditions.visibilityOf(element));
			js.executeScript(IdeaWFSConstants.JS_ARG_SCROLL_VIEW, element);
			actualValue = element.getText();
		}  catch (Exception ex) {
			Reporter.log("Exception occured while getValue event " + ex.getMessage());
			throw ex;
		}
		return actualValue;
	}
	
	/**
	 * jquery load time to load the page
	 * 
	 * @param driver
	 *            current active driver
	 */
	public static void waitForJQueryToLoad(WebDriver driver) {
		try {
			WebDriverWait webWait = new WebDriverWait(driver,Integer.parseInt(ConfigPropertyLoader.getConfigValue("loadingTime")));
			webWait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
			webWait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
					.executeScript("return jQuery.active==0").equals(true));
		} catch (Exception e) {
			Reporter.log("Exceptino occured while waiting for JQuery to load " + e.getMessage());
		}
	}

	/**
	 * This method helps to select value from list
	 * @param driver The WebDriver
	 * @param listOfElements  Holds the list of web element
	 * @param expValue  Holds the value to select 
	 * @return Returns boolean 
	 */
	public static boolean selectValueFromList(WebDriver webDriver, List<WebElement> listOfElements, String expValue) {
		waitForJQueryToLoad(webDriver);
		boolean flag = false;
		try {
			for (WebElement element : listOfElements) {
				if ((element.getAttribute(IdeaWFSConstants.TEXTCONTENT)).trim().equalsIgnoreCase(expValue)
						&& element.isDisplayed()) {
					Actions action = new Actions(webDriver);
					action.moveToElement(element);
					action.click().build().perform(); flag = true;break;
				} 
			}
			if (!flag) {
				Reporter.log("Expected value [" + expValue + "] is not present in list");
			}
		} catch (Exception ex) {
			Reporter.log("Exception occured while doClick event " + ex.getMessage());
		}
		return flag;
	}

	/**
	 * This method is used to validate the element displayed or not
	 */
	public static boolean isDisplayed(WebDriver webDriver, WebElement element, String message) {
		waitForJQueryToLoad(webDriver);
		boolean flag = false;
		try {
			if (element.isEnabled() || element.isDisplayed()) {
				Reporter.log("As expected [" + message + "] is displayed");
				flag = true;
			} else {
				Reporter.log("[" + message + "] is not displayed");
			}
		} catch (Exception e) {
			Reporter.log("Exception is occured while checking element is displayed or not " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method is used to select an option
	 */
	public static boolean selectFromComboBox(WebDriver webDriver, WebElement element, String textValue) {
		waitForJQueryToLoad(webDriver);
		boolean flag = false;
		try {
			((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
			if (element.isDisplayed() || element.isEnabled()) {
				List<WebElement> options = element.findElements(By.tagName("option"));
				for (WebElement option : options) {
					if (textValue.equalsIgnoreCase(option.getText().trim())) {
						option.click();
						Reporter.log("[" + textValue + "] is selected");
						flag = true; break;
					}
				}
			}
		} catch (Exception e) {
			Reporter.log("Exception occured while fnSelectFromComboBox event " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to check whether the alert is present in the web page or
	 * not.
	 *
	 * @param driver used to get the driver object
	 * @return true, if is alert present in the web page
	 */
	public static boolean isAlertPresent(WebDriver webDriver) {
		waitForJQueryToLoad(webDriver);
		boolean flag = false;
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, 50);
			wait.until(ExpectedConditions.alertIsPresent());
			webDriver.switchTo().alert();
			flag = true;
		} catch (Exception e) {
			Reporter.log("Exception occured while handling alert " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to accept the alert messages
	 * @param webDriver used to get the driver object
	 * @return true, if is alert present and accepted
	 */
	public static boolean acceptAlert(WebDriver webDriver, String expAlertMessage) {
		boolean flag = true;
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = webDriver.switchTo().alert();
			String actAlertMessage = alert.getText();
			if(expAlertMessage!= null) {
				if(expAlertMessage.equalsIgnoreCase(actAlertMessage)) {
					alert.accept();
				}else {
					flag = false; //Alert is prompted but not met with condition. returning false
				}
			} else {
				alert.accept(); //Accepting the alert without comparing alert message
			}
		} catch (Exception e) {
			Reporter.log("Exception occured while handling alert " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to switch to a particular window
	 * @param webDriver used to get the driver object
	 * @param title holds the title of window
	 * @return true, if is switched to a window
	 */
	public static boolean switchToWindow(WebDriver webDriver, String title) {
		waitForJQueryToLoad(webDriver);
		boolean flag = false;
		Set<String> availableWindows = webDriver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			try {
				for (String windowId : availableWindows) {
					if (webDriver.switchTo().window(windowId).getTitle().equals(title)) {
						flag = true; // identified the window and set the flag to true
						break;
					}
				}
			} catch (Exception e) {
				Reporter.log("Exception occured while handling switch to window " + e.getMessage());
			}
		}
		return flag;
	}

	/**
	 * This method used to double click on a particular element
	 * @param webDriver used to get the driver object
	 * @param ele holds web element
	 * @return true, if it performs double click
	 */
	public static boolean doubleClick(WebDriver webDriver, WebElement ele) {
		waitForJQueryToLoad(webDriver);
		boolean flag = false;
		try {
			if (ele.isDisplayed()) {
				Actions action = new Actions(webDriver);
				action.doubleClick(ele).build().perform();
				flag = true;
			}
		} catch (Exception e) {
			Reporter.log("Exception occured while fnDoubleClick event " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to right click on a particular element
	 * @param webDriver used to get the driver object
	 * @param ele holds web element
	 * @return true, if it performs right click
	 */
	public static boolean rightClick(WebDriver webDriver, WebElement ele) {
		boolean flag = false;
		try {
			if (ele.isDisplayed()) {
				Actions action = new Actions(webDriver);
				action.contextClick(ele).build().perform();
				flag = true;
			}
		} catch (Exception e) {
			Reporter.log("Exception occured while fnRightClick event " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to switch to default frame
	 * @param webDriver used to get the driver object
	 * @return true, if it switches to default frame
	 */
	public static boolean wSwitchToFrame(WebDriver webDriver) {
		boolean flag = false;
		try {
			webDriver.switchTo().defaultContent();
			flag = true;
		} catch (Exception e) {
			Reporter.log("Exception occured while switchToFrame event " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to switch to default frame
	 * @param webDriver used to get the driver object
	 * @param sFrameName holds name of the frame
	 * @return true, if it switches to a particular frame
	 */
	public static boolean switchToFrame(WebDriver webDriver, String sFrameName) {
		boolean flag = false;
		try {
			webDriver.switchTo().frame(sFrameName);
			flag = true;
		} catch (Exception e) {
			Reporter.log("Exception occured while switchToFrame event " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * This method used to add list of string values to list
	 * @param element contains list of webelements
	 * @return List<String>
	 */
	public static List<String> getWebElementList(List<WebElement> element){
		List<String> webElementList = new ArrayList<>();
	 	try {
			for(WebElement ele : element) {
				webElementList.add(ele.getText().trim());
			}
		} catch (Exception e) {
			Reporter.log("Exception occured while getWebElementList event " + e.getMessage());
		}
		return webElementList;
	}
	/**
	 * This method used to verify drop down values
	 * @param webDriver used to get the driver object
	 * @param sValues holds list of actual values
	 * @return true, if it that value is present in the list
	 */
	public static boolean verifyDropdownValues(WebElement ele, String sValues)  {
		boolean flag = false;
		try{
			String [] expList = sValues.split(";");
			Select select = new Select(ele);
			List<WebElement> options = select.getOptions();
			for(WebElement we:options) {
				for (int index=0; index<expList.length; index++){
					if (we.getText().equals(expList[index])){
						flag=true; break;
					}
				}
			} 
		} catch (Exception e) {
			Reporter.log("Exception occured while fnVerifyDropdownValues event " + e.getMessage());
		}
		return flag;    
    }
	public static void uploadFile(WebDriver driver, String filePath) {
		try {
			Path file = new File(filePath).toPath();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (Files.exists(file)) {
				String autoITExecutable = System.getProperty("user.dir") + File.separator
						+ ConfigPropertyLoader.getConfigValue("testFilePath");
					autoITExecutable = autoITExecutable + File.separator + "AutoITFileUploadWithParam.exe " + filePath;
					Runtime.getRuntime().exec(autoITExecutable);
					CommonUtils.sleepForAWhile(20000);
					Reporter.log("AutoIT script to upload : " + filePath);
			} else {
				Assert.fail("File path is not correct/file doesnt exist " + filePath);
			}
		} catch (Exception e) {
			Assert.fail("Failed to run AutoIT script : " + e.getMessage());
			
		}
	}
	public static void browseAndUploadFile(WebDriver driver, WebElement element,
			String fileName) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).click(element).build().perform();
			Reporter.log("filePath" + fileName);
			uploadFile(driver, fileName);
			Reporter.log("Successfully added the " + fileName);
			waitForJQueryToLoad(driver);
			CommonUtils.sleepForAWhile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void explicitWait(WebDriver webDriver, WebElement element) {
		try {
			WebDriverWait webWait = new WebDriverWait(webDriver,
					Integer.parseInt(ConfigPropertyLoader.getConfigValue(IdeaWFSConstants.JS_LOADING_TIME)));
			webWait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	

