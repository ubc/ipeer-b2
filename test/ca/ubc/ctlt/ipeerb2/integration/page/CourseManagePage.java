package ca.ubc.ctlt.ipeerb2.integration.page;

import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CourseManagePage extends PageBase<CourseManagePage> {
	
	@FindBy(linkText = "Synchronize Class List")
	protected WebElement syncClassButton;
	
	@FindBy(linkText = "Push Groups")
	protected WebElement pushGroupsButton;
	
	@FindBy(linkText = "Pull Groups")
	protected WebElement pullGroupsButton;
	
	@FindBy(linkText = "Synchronize Grades")
	protected WebElement syncGradesButton;
	
	@FindBy(linkText = "Manage Course in iPeer")
	protected WebElement manageCourseButton;

	@FindBy(linkText = "Disconnect Course")
	protected WebElement disconnectButton;
	
//	@FindBy(linkText = "Delete Course")
//	protected WebElement deleteButon;
	
	protected String courseId;
	
	public CourseManagePage(WebDriver driver, URI siteBase, String courseId) {
		super(driver, siteBase);
		this.courseId = courseId;
	}

	@Override
	public String getUri() {
		return "webapps/ubc-ipeerb2-BBLEARN/instructor/course?course_id="+courseId;
	}
	
	public IPeerCourseHomePage manageiPeerCourse() {
		int ipeerCourseId = getiPeerCourseId(); 
		manageCourseButton.click();
		
		return new IPeerCourseHomePage(driver, URI.create("http://ipeer.dev/"), ipeerCourseId);
	}
	
	public CourseCreationPage disconnectCourse() {
		disconnectButton.click();
		return new CourseCreationPage(driver, siteBase, courseId);
	}
	
	public CourseCreationPage deleteCourse() {
		// find the element, can't be injected as instructor doesn't have this link
		driver.findElement(By.linkText("Delete Course")).click();
		return new CourseCreationPage(driver, siteBase, courseId);
	}

	public CourseManagePage syncClass() {
		syncClassButton.click();
		return new CourseManagePage(driver, siteBase, courseId);	
	}

	public CourseManagePage pushGroups() {
		pushGroupsButton.click();
		return new CourseManagePage(driver, siteBase, courseId);	
	}

	public CourseManagePage pullGroups() {
		pullGroupsButton.click();
		return new CourseManagePage(driver, siteBase, courseId);
	}
	
	public int getiPeerCourseId() {
		String link = manageCourseButton.getAttribute("href");
		return Integer.parseInt(link.substring(link.lastIndexOf("/courses/home/")+14));
	}

	public void logout() {
		driver.get(siteBase+"webapps/login?action=logout");
	}

    public CourseManagePage syncGrade() {
        syncGradesButton.click();

        return new CourseManagePage(driver, siteBase, courseId);
    }
}
