package ca.ubc.ctlt.ipeerb2.integration.page;

import java.net.URI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CourseCreationPage extends PageBase<CourseCreationPage> {
	@FindBy(name = "course")
	protected WebElement course;

	@FindBy(name = "title")
	protected WebElement title;

	@FindBy(name = "syncClass")
	protected WebElement syncClass;

	@FindBy(name = "pushGroup")
	protected WebElement pushGroup;

	@FindBy(id = "departments1")
	protected WebElement departments1;

	@FindBy(id = "departments2")
	protected WebElement departments2;

	@FindBy(id = "departments3")
	protected WebElement departments3;

	@FindBy(xpath = "//*[@id='courseCreate']/table/tbody/tr[7]/td/input[3]")
	protected WebElement createCourseButon;

	@FindBy(name = "ipeerId")
	protected WebElement ipeerCourseId;

	@FindBy(xpath = "//*[@id='courseLink']/table/tbody/tr[2]/td/input[3]")
	protected WebElement linkCourseButon;

	protected String courseId;
	
	public CourseCreationPage(WebDriver driver, URI siteBase, String courseId) {
		super(driver, URI.create(siteBase.toString()));
		this.courseId = courseId;
	}

	@Override
	public String getUri() {
		return "webapps/ubc-ipeerb2-BBLEARN/instructor/course?course_id="+courseId;
	}
	
	public CourseManagePage createCourse(String courseTitle, boolean pushClass, boolean pushGroup, boolean dept1, boolean dept2, boolean dept3) {
		clearAndSendKeys(title, courseTitle);
		if (syncClass.isSelected() != pushClass) {
			syncClass.click();
		}
		if (this.pushGroup.isSelected() != pushGroup) {
			this.pushGroup.click();
		}
		if (this.departments1.isSelected() != dept1) {
			this.departments1.click();
		}
		if (this.departments2.isSelected() != dept2) {
			this.departments2.click();
		}
		if (this.departments3.isSelected() != dept3) {
			this.departments3.click();
		}
		createCourseButon.click();
		
		return new CourseManagePage(driver, siteBase, courseId);
	}

	public WebElement getCourse() {
		return course;
	}

	public void setCourse(WebElement course) {
		this.course = course;
	}

	public WebElement getTitle() {
		return title;
	}

	public void setTitle(WebElement title) {
		this.title = title;
	}

	public WebElement getSyncClass() {
		return syncClass;
	}

	public void setSyncClass(WebElement syncClass) {
		this.syncClass = syncClass;
	}

	public WebElement getPushGroup() {
		return pushGroup;
	}

	public void setPushGroup(WebElement pushGroup) {
		this.pushGroup = pushGroup;
	}

	public WebElement getDepartments1() {
		return departments1;
	}

	public void setDepartments1(WebElement departments1) {
		this.departments1 = departments1;
	}

	public WebElement getDepartments2() {
		return departments2;
	}

	public void setDepartments2(WebElement departments2) {
		this.departments2 = departments2;
	}

	public WebElement getDepartments3() {
		return departments3;
	}

	public void setDepartments3(WebElement departments3) {
		this.departments3 = departments3;
	}

	public WebElement getCreateCourseButon() {
		return createCourseButon;
	}

	public void setCreateCourseButon(WebElement createCourseButon) {
		this.createCourseButon = createCourseButon;
	}

	public WebElement getIpeerCourseId() {
		return ipeerCourseId;
	}

	public void setIpeerCourseId(WebElement ipeerCourseId) {
		this.ipeerCourseId = ipeerCourseId;
	}

	public WebElement getLinkCourseButon() {
		return linkCourseButon;
	}

	public void setLinkCourseButon(WebElement linkCourseButon) {
		this.linkCourseButon = linkCourseButon;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public CourseManagePage linkCourse(int ipeerId) {
		clearAndSendKeys(ipeerCourseId, ipeerId+"");
		linkCourseButon.click();
		
		return new CourseManagePage(driver, siteBase, courseId);
	}

}
