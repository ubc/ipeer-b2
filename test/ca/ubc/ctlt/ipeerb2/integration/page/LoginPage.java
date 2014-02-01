package ca.ubc.ctlt.ipeerb2.integration.page;

import java.net.URI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase<LoginPage> {
	@FindBy(name = "user_id")
	protected WebElement userNameField;
	@FindBy(name = "password")
	protected WebElement passwordField;
	@FindBy(xpath = "//input[@value = 'Login']")
	protected WebElement logonButon;
	
	public LoginPage(WebDriver driver, URI siteBase) {
		super(driver, siteBase);
	}
	
    public PortalPage logon(String username, String password){  
        userNameField.sendKeys(username);  
        passwordField.sendKeys(password);  
        logonButon.click(); 
        
        return new PortalPage(driver, siteBase);
    }

	@Override
	public String getUri() {
		return "";
	}
}
