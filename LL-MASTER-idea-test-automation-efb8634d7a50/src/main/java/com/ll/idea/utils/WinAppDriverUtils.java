package com.ll.idea.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class WinAppDriverUtils {
	private WinAppDriverUtils() {
		// Do not do anything here and private new instantiation from out side
	}

	public static void doClickElement(WindowsDriver<WindowsElement> windowsDriver, String elementName) {
		windowsDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement element = windowsDriver.findElementByAccessibilityId(elementName);
		element.click();
	}

	public static void doSendKeys(WindowsDriver<WindowsElement> windowsDriver, String elementName, String value) {
		windowsDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement element = windowsDriver.findElementByAccessibilityId(elementName);
		element.sendKeys(value);
	}

	public static boolean waitToLoad() {
		boolean isLoaded = true;
		int maxCount = 1000;
		while (maxCount > 0) {
			maxCount--;
		}
		return isLoaded;
	}
}