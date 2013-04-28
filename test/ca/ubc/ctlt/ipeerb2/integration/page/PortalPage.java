package ca.ubc.ctlt.ipeerb2.integration.page;

import java.net.URI;

import org.openqa.selenium.WebDriver;


public class PortalPage extends PageBase<PortalPage> {
	
	public PortalPage(WebDriver driver, URI siteBase) {
		super(driver, siteBase);
	}

	@Override
	public String getUri() {
		return "webapps/portal/frameset.jsp";
	}
}
