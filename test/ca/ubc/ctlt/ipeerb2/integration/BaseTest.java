package ca.ubc.ctlt.ipeerb2.integration;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

public class BaseTest extends AbstractTestNGSpringContextTests {

	@Autowired
	protected URI siteBase;
	@Autowired
	protected EventFiringWebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void takeScreenshot(String screenshotName) {
	    if (driver instanceof TakesScreenshot) {
	        File tempFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	        try {
	            FileUtils.copyFile(tempFile, new File("screenshots/" + screenshotName + ".png"));
	        } catch (IOException e) {
	            System.err.println("Failed to copy file!");
	        }
	    }
	}
}