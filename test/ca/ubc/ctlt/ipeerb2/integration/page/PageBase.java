package ca.ubc.ctlt.ipeerb2.integration.page;

import static org.testng.AssertJUnit.assertTrue;

import java.net.URI;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PageBase<T extends LoadableComponent<T>> extends LoadableComponent<T> {

	@Autowired 	
    protected WebDriver driver;
	
	@Autowired
	protected URI siteBase;
	
	@Autowired
	protected URI ipeerSiteBase;
	
	public PageBase(WebDriver driver, URI siteBase) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.siteBase = siteBase;
	}
	
	public abstract String getUri();
	
	public String getText() {
		return driver.findElement(By.tagName("body")).getText();
	}
	
	public boolean isAtPage(String pageUri) {
		return driver.getCurrentUrl().equals(siteBase.toString()+pageUri);
	}
	
	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
		assertTrue(this.getClass().getSimpleName()+": not on the page: " + url,
				url.equals(siteBase.toString() + getUri()));
	}

	@Override
	protected void load() {
		driver.get(siteBase + getUri());
	}
	
	protected void clearAndSendKeys(WebElement elem, String keys) {
		elem.clear();
		elem.sendKeys(keys);
	}
	
	public String switchWindowTo() {
		String currentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (!handle.equals(currentWindow)) {
				driver.switchTo().window(handle);
			}
		}
		
		return currentWindow;
	}
	
	public void closeWindow(String previousWindow) {
		driver.close();
		driver.switchTo().window(previousWindow);
	}
}
