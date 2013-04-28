package ca.ubc.ctlt.ipeerb2.integration;


import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ca.ubc.ctlt.ipeerb2.integration.page.LoginPage;

@ContextConfiguration("functionalTestContext.xml") 
@ActiveProfiles({"dev", "integration"})
public class LoginTest extends BaseTest {
	@BeforeMethod
	public void setUp() {
		getDriver().manage().deleteAllCookies();
	}
	
	@Test
	public final void testLogin() {
		new LoginPage(getDriver(), siteBase).get().logon("administrator", "bblearn"); 
	}
}
