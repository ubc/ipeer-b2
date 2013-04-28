package ca.ubc.ctlt.ipeerb2.integration;

import java.net.URI;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestHelper {

    protected WebDriver driver;

	protected URI siteBase;
	
	protected Wait<WebDriver> wait;
	
	public TestHelper(WebDriver driver, URI siteBase) {
		this.driver = driver;
		this.siteBase = siteBase;
		wait = new WebDriverWait(driver, 10);
	}
	
	public String createCourse(Properties courseData) {
		driver.get(siteBase+"webapps/blackboard/execute/launcher?type=CourseAdminAdd&id=&url=");
		// append time after course name so that even if the previous test failed, it will not affect the current test
		courseData.setProperty("courseId", courseData.getProperty("courseId", "") + (new Date()).getTime());
		driver.findElement(By.name("courseName")).sendKeys(courseData.getProperty("courseName", ""));
		driver.findElement(By.name("courseId")).sendKeys(courseData.getProperty("courseId", ""));
		driver.findElement(By.xpath("//*[@id='top_submitButtonRow']/input[2]")).click();
		
		if(!driver.findElement(By.tagName("body")).getText().contains("Success: Course "+courseData.getProperty("courseName", "")+" created.")) {
			System.out.println("Warning: test course exists. Using the existing course instead. The result may vary.");
		}
		
		new Select(driver.findElement(By.name("courseInfoSearchKeyString"))).selectByValue("CourseId");
		new Select(driver.findElement(By.name("courseInfoSearchOperatorString"))).selectByValue("Equals");
		WebElement searchTextElm = driver.findElement(By.name("courseInfoSearchText"));
		searchTextElm.clear();
		searchTextElm.sendKeys(courseData.getProperty("courseId", ""));
		driver.findElement(By.xpath("//*[@id='panelbutton1']/form/fieldset/div[2]/input[5]")).click();
		
		return driver.findElement(By.name("ckbox")).getAttribute("value");
	}
	
	public void deleteCourse(Properties courseData) {
		// find the course
		driver.get(siteBase+"webapps/blackboard/execute/courseManager?sourceType=COURSES");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("courseInfoSearchOperatorString")));
        new Select(driver.findElement(By.name("courseInfoSearchKeyString"))).selectByValue("CourseId");
		new Select(driver.findElement(By.name("courseInfoSearchOperatorString"))).selectByValue("Equals");
		WebElement searchTextElm = driver.findElement(By.name("courseInfoSearchText"));
		searchTextElm.clear();
		searchTextElm.sendKeys(courseData.getProperty("courseId", ""));
		driver.findElement(By.xpath("//*[@id='panelbutton1']/form/fieldset/div[2]/input[5]")).click();

        // remove it
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("removeAllFiles_t")));
		driver.findElement(By.name("ckbox")).click();
		driver.findElement(By.linkText("Delete")).click();
		driver.findElement(By.id("removeAllFiles_t")).click();
		driver.findElement(By.xpath("//*[@id='bottom_submitButtonRow']/input[2]")).click();
	}
	
	public void importUsers() {
		// import doesn't work, fall back to creating the one by one
//		driver.get(siteBase+"webapps/blackboard/execute/batchAddUsers");
//		driver.findElement(By.name("batchFile_LocalFile0")).sendKeys("ipeer_students.csv");
//		driver.findElement(By.name("top_Submit")).click();
		driver.get(siteBase+"webapps/blackboard/execute/userManager");
		WebElement searchTextElm = driver.findElement(By.name("userInfoSearchText"));
		searchTextElm.clear();
		searchTextElm.sendKeys("redshirt");
		driver.findElement(By.xpath("//*[@id='UserInformation']/input[2]")).click();
		
		if (driver.findElement(By.id("listContainer")).getText().contains(UserFixture.USERS[1][3])) {
			// found test users exist, no need to create them
			return;
		}

		for (String[] user : UserFixture.USERS) {
			addUser(user[0], user[1], user[2], user[3]);
		}
	}
	
	public void addUser(String firstName, String LastName, String email, String username) {
		driver.get(siteBase+"webapps/blackboard/execute/launcher?type=UserAdminAdd&id=&url=");
		
		// wait for auto focus on the page to finish before we type in anything
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return driver.switchTo().activeElement().getTagName().equals("input");
			}
		};

		wait.until(expectation);

		driver.findElement(By.name("firstName")).sendKeys(firstName);
		driver.findElement(By.name("lastName")).sendKeys(LastName);
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("userName")).sendKeys(username);
		driver.findElement(By.name("password_input")).sendKeys("12345678");
		driver.findElement(By.name("verifyPassword")).sendKeys("12345678");
		driver.findElement(By.name("top_Submit")).click();
	}
	
	public void enrolUsers(String courseId) {
		// enrol admin as instructor
		enrolUser(courseId, "administrator", "Instructor");
		
		// leave one for enrol/class sync testing
		for (int i = 0; i < UserFixture.USERS.length -1; i++) {
			enrolUser(courseId, UserFixture.USERS[i][3], UserFixture.USERS[i][4]);
		}
	}
	
	public void enrolUser(String courseId, String username, String role) {
		driver.get(siteBase+"webapps/blackboard/execute/editCourseEnrollment?course_id="+courseId+"&sourceType=COURSES");
		driver.findElement(By.name("userName")).sendKeys(username);
		new Select(driver.findElement(By.name("courseRoleId"))).selectByVisibleText(role);
		driver.findElement(By.name("top_Submit")).click();		
	}
	
	public void createGroups(String courseId) {
		// create an empty group
		driver.get(siteBase+"webapps/blackboard/execute/editGroup?course_id="+courseId+"&editGroupAction=createGroup&toggleType=all");
		driver.findElement(By.name("name")).sendKeys("Empty Group ");
		driver.findElement(By.name("top_Submit")).click();
		
		// create groups with members
		// group 1
		driver.get(siteBase+"webapps/blackboard/execute/editGroup?course_id="+courseId+"&editGroupAction=createGroup&toggleType=all");
		driver.findElement(By.name("name")).sendKeys("Group 1");
		
		// add members
		Select s = new Select(driver.findElement(By.name("membership_source_select")));
		for (int j = 1; j<=3; j++) {
			// skip the first fixture as it is an instructor
			String[] user = UserFixture.USERS[j];
			s.selectByVisibleText(user[1] + ", " + user[0]);
		}
		driver.findElement(By.xpath("//*[@id='membership_source_select_membership_dest_select']/div[2]/button[1]")).click();
		driver.findElement(By.name("top_Submit")).click();
		
		// group 2
		driver.get(siteBase+"webapps/blackboard/execute/editGroup?course_id="+courseId+"&editGroupAction=createGroup&toggleType=all");
		driver.findElement(By.name("name")).sendKeys("Group 2");
		
		// add members
		s = new Select(driver.findElement(By.name("membership_source_select")));
		String[] user = UserFixture.USERS[4];
		s.selectByVisibleText(user[1] + ", " + user[0]);
		driver.findElement(By.xpath("//*[@id='membership_source_select_membership_dest_select']/div[2]/button[1]")).click();
		driver.findElement(By.name("top_Submit")).click();
	}
	
	public void enrolGroup(String courseId, String groupName, String[] user) {
		String groupId = getGroupIdByName(courseId, groupName);
		driver.get(siteBase+"webapps/blackboard/execute/editGroup?editGroupAction=editGroup&group_id="+groupId+"&course_id="+courseId+"&group_set_id=&toggleType=all");

		// add members
		Select s = new Select(driver.findElement(By.name("membership_source_select")));
		s.selectByVisibleText(user[1] + ", " + user[0]);
		driver.findElement(By.xpath("//*[@id='membership_source_select_membership_dest_select']/div[2]/button[1]")).click();
		driver.findElement(By.name("top_Submit")).click();
	}

	public void addModule(String courseId) {
		// enable the module
		driver.get(siteBase + "webapps/portal/execute/manageModules");
		driver.findElement(By.name("searchInput")).sendKeys("ipeer");
		driver.findElement(By.xpath("//*[@id='searchForm']/fieldset/div/a[1]")).click();

		Wait<WebDriver> wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("listContainer_datatable"), "iPeer Events"));
		String link = driver.findElement(By.linkText("iPeer Events")).getAttribute("href");
		String moduleId = link.substring(link.indexOf("'") + 1, link.lastIndexOf("'"));
		
		driver.get(siteBase + "webapps/portal/execute/modifyModule?module_id=" + moduleId);
		driver.findElement(By.id("enabledInd_true")).click();
		driver.findElement(By.id("avlCourseInd_true")).click();
		
		driver.findElement(By.name("top_Submit")).click();
		
		//driver.get(siteBase + "webapps/blackboard/execute/modulepage/view?course_id="+courseId+"&cmp_tab_id=_201_1&editMode=true&mode=cpview");
		driver.get(siteBase + "webapps/portal/frameset.jsp?tab_tab_group_id=_2_1&url=%2Fwebapps%2Fblackboard%2Fexecute%2Flauncher%3Ftype%3DCourse%26id%3D"+courseId+"%26url%3D");
		driver.switchTo().frame("contentFrame");
		driver.findElement(By.linkText("Add Course Module")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("txtSearch")));
//		driver.findElement(By.name("txtSearch")).sendKeys("iPeer");
//		driver.findElement(By.xpath("//*[@id='searchBlock']/li/input[2]"));
		driver.findElement(By.linkText("Other")).click();
		
		wait.until(ExpectedConditions.textToBePresentInElement(By.id("containerdiv"), "iPeer Events"));
		driver.findElement(By.linkText("Add")).click();
	}
	
	private String getGroupIdByName(String courseId, String groupName) {
		driver.get(siteBase+"webapps/blackboard/execute/groupInventoryList?course_id="+courseId);
		WebElement elem = driver.findElement(By.linkText(groupName));
		String link = elem.getAttribute("href");
		
		return link.substring(link.indexOf("group_id=")+9);
	}
}
