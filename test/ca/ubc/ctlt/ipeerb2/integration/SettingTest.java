package ca.ubc.ctlt.ipeerb2.integration;

import static org.testng.AssertJUnit.assertTrue;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ca.ubc.ctlt.ipeerb2.integration.page.LoginPage;
import ca.ubc.ctlt.ipeerb2.integration.page.SettingPage;

@ContextConfiguration("functionalTestContext.xml") 
@ActiveProfiles({"dev", "integration"})
public class SettingTest extends BaseTest {
	
	@Autowired
	@Qualifier("defaultSettings")
	private Properties defaultSettings;
	
	@BeforeMethod
	public void setUp() {
		getDriver().manage().deleteAllCookies();
		getDriver().get(siteBase.toString());
		new LoginPage(getDriver(), siteBase).logon("administrator", "bblearn"); 
	}
	
	@Test
	public final void testSettingPage() {
		Properties originalSettings = new SettingPage(getDriver(), siteBase).get().save(defaultSettings);
		
		assertTrue(getDriver().getCurrentUrl().
				startsWith(siteBase+"webapps/blackboard/execute/plugInController?inline_receipt_message=The%20settings%20have%20been%20saved%20successfully"));
		
		// check if the settings are saved correctly
		SettingPage page = new SettingPage(getDriver(), siteBase).get();
		assertTrue(defaultSettings.equals(page.getSettings()));
		
		// restore the original
		page.save(originalSettings);
	}
}
