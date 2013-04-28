package ca.ubc.ctlt.ipeerb2.integration.page;

import java.net.URI;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SettingPage extends PageBase<SettingPage> {
	@FindBy(name = "ipeer.url")
	protected WebElement ipeerUrl;
	@FindBy(name = "ipeer.consumer_key")
	protected WebElement consumerKey;
	@FindBy(name = "ipeer.consumer_secret")
	protected WebElement consumerSecret;
	@FindBy(name = "ipeer.token_key")
	protected WebElement tokenKey;
	@FindBy(name = "ipeer.token_secret")
	protected WebElement tokenSecret;
	@FindBy(xpath = "//*[@id='top_submitButtonRow']/input[2]")
	protected WebElement submitButon;

	public SettingPage(WebDriver driver, URI siteBase) {
		super(driver, siteBase);
	}
	
    public Properties save(Properties settingData){  
    	Properties original = getSettings();
    	
    	ipeerUrl.clear();
    	ipeerUrl.sendKeys(settingData.getProperty("ipeerUrl", ""));
    	
    	consumerKey.clear();
    	consumerKey.sendKeys(settingData.getProperty("consumerKey", ""));
    	
    	consumerSecret.clear();
    	consumerSecret.sendKeys(settingData.getProperty("consumerSecret", ""));
    	
    	tokenKey.clear();
    	tokenKey.sendKeys(settingData.getProperty("tokenKey", ""));
    	
    	tokenSecret.clear();
    	tokenSecret.sendKeys(settingData.getProperty("tokenSecret", ""));
    	
    	submitButon.click();

    	return original;
    }  
    
    public Properties getSettings() {
    	Properties settings = new Properties();
    	settings.setProperty("ipeerUrl", ipeerUrl.getAttribute("value"));
    	settings.setProperty("consumerKey", consumerKey.getAttribute("value"));
    	settings.setProperty("consumerSecret", consumerSecret.getAttribute("value"));
    	settings.setProperty("tokenKey", tokenKey.getAttribute("value"));
    	settings.setProperty("tokenSecret", tokenSecret.getAttribute("value"));
    	
    	return settings;
    }

	@Override
	public String getUri() {
		return "webapps/ubc-ipeerb2-BBLEARN/settings/index";
	}

}
