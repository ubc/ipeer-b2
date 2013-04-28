package ca.ubc.ctlt.ipeerb2.integration.page;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IPeerCourseHomePage extends PageBase<IPeerCourseHomePage> {

	protected int courseId;
	
	public IPeerCourseHomePage(WebDriver driver, URI siteBase, int ipeerCourseId) {
		super(driver, siteBase);
		this.courseId = ipeerCourseId;
	}

	@Override
	public String getUri() {
		return "courses/home/"+courseId;
	}
	
	@Override
	protected void isLoaded() throws Error {
		String url = driver.getCurrentUrl();
//		assertTrue(this.getClass().getSimpleName()+": not on the page: " + url,
//				url.startsWith(siteBase.toString() + getUri()));
	}
	
	public void createGroup(String groupName, String[] names) {
		driver.findElement(By.linkText("Create Groups (Manual)")).click();
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("GroupGroupName"))).sendKeys(groupName);
		
		Select members = new Select(driver.findElement(By.id("all_groups")));
		
		for (String name : names) {
			members.selectByVisibleText(name);
		}
		// move members
		driver.findElement(By.xpath("//*[@id='frm']/div[5]/div/table/tbody/tr/td[2]/input[1]")).click();
		
		// submit
		driver.findElement(By.xpath("//*[@id='frm']/div[6]/input")).click();
	}
	

	public void createEvent(String eventName, String type, String template, Date dueDate, Date evalFrom, 
			Date evalTo, Date resultFrom, Date resultTo, String[] groups) {
		driver.get(siteBase + "/events/add/" + courseId);
		
		driver.findElement(By.id("EventTitle")).sendKeys(eventName);
		new Select(driver.findElement(By.id("EventEventTemplateTypeId"))).selectByVisibleText(type);
		new Select(driver.findElement(By.id("EventSimpleEvaluation"))).selectByVisibleText(template);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// just js for workaround. Normal elem.sendKeys conflict with JQuery datepicker in result of invalid input 
		((JavascriptExecutor)driver).executeScript("jQuery('#EventDueDate').datepicker('setDate', '"+dateFormat.format(dueDate)+"')");
		((JavascriptExecutor)driver).executeScript("jQuery('#EventReleaseDateBegin').datepicker('setDate', '"+dateFormat.format(evalFrom)+"')");
		((JavascriptExecutor)driver).executeScript("jQuery('#EventReleaseDateEnd').datepicker('setDate', '"+dateFormat.format(evalTo)+"')");
		((JavascriptExecutor)driver).executeScript("jQuery('#EventResultReleaseDateBegin').datepicker('setDate', '"+dateFormat.format(resultFrom)+"')");
		((JavascriptExecutor)driver).executeScript("jQuery('#EventResultReleaseDateEnd').datepicker('setDate', '"+dateFormat.format(resultTo)+"')");
		
		Select groupSelect = new Select(driver.findElement(By.id("GroupGroup")));
		for (String group : groups) {
			groupSelect.selectByVisibleText(group);
		}
		
		driver.findElement(By.xpath("//form/div[19]/input")).click();
	}
	
	public void createOpenEvent(String eventName, String type, String template, String[] groups) {
		Calendar c = Calendar.getInstance();
		Date evalFrom = c.getTime();
		c.add(Calendar.DATE, 1);
		Date dueDate = c.getTime();
		c.add(Calendar.DATE, 1);
		Date evalTo = c.getTime();
		Date resultFrom = c.getTime();
		c.add(Calendar.DATE, 1);
		Date resultTo = c.getTime();
		
		createEvent(eventName, type, template, 
				dueDate, 
				evalFrom, 
				evalTo, 
				resultFrom, 
				resultTo, 
				groups);	
	}

}
