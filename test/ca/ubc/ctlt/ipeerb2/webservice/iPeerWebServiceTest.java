package ca.ubc.ctlt.ipeerb2.webservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestOperations;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Department;
import ca.ubc.ctlt.ipeerb2.domain.Event;
import ca.ubc.ctlt.ipeerb2.domain.Grade;
import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles({"dev", "integration"})
public class iPeerWebServiceTest {
	@Autowired
	private iPeerWebService webService;
	
	@Autowired
	private RestOperations restOperations;
	
	@Autowired
	private Course course;
	
	@Autowired
	private List<Course> courseList;
	
	@Autowired
	private User user;
	
	@Autowired
	private List<User> userList;
	
	@Autowired
	private Group group;
	
	@Autowired
	private List<Group> groupList;
	
	@Autowired
	private Event event;
	
	@Autowired
	private List<Event> eventList;

	@Autowired
	private Grade grade;
	
	@Autowired
	private List<Grade> gradeList;
	
	@Autowired
	private Department department;
	
	@Autowired
	private List<Department> departmentList;
	
	@Before
	public void setUp() throws Exception {
		// course mocking
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Course.class), Matchers.anyString()) ).thenReturn(course);
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Course[].class)) ).thenReturn(courseList.toArray(new Course[courseList.size()]));
		when(restOperations.postForObject( Matchers.anyString(), Matchers.any(Course.class), Matchers.eq(Course.class)) ).thenReturn(course);
		
		// user mocking
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(User.class), Matchers.anyString()) ).thenReturn(user);
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(User[].class)) ).thenReturn(userList.toArray(new User[userList.size()]));
		when(restOperations.postForObject( Matchers.anyString(), Matchers.any(User.class), Matchers.eq(User.class)) ).thenReturn(user);
		when(restOperations.postForObject( Matchers.anyString(), Matchers.any(User[].class), Matchers.eq(User[].class)) ).thenReturn(userList.toArray(new User[userList.size()]));
		
		// group mocking
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Group.class), Matchers.anyInt(), Matchers.anyString()) ).thenReturn(group);
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Group[].class), Matchers.anyInt()) ).thenReturn(groupList.toArray(new Group[groupList.size()]));
		when(restOperations.postForObject( Matchers.anyString(), Matchers.any(Group.class), Matchers.eq(Group.class), Matchers.anyInt()) ).thenReturn(group);
		when(restOperations.postForObject( Matchers.anyString(), Matchers.any(Group[].class), Matchers.eq(Group[].class), Matchers.anyInt()) ).thenReturn(groupList.toArray(new Group[groupList.size()]));
		
		// course/user mocking
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(User[].class), Matchers.anyInt()) ).thenReturn(userList.toArray(new User[userList.size()]));
		when(restOperations.postForObject( Matchers.anyString(), Matchers.any(User[].class), Matchers.eq(User[].class), Matchers.anyInt()) ).thenReturn(userList.toArray(new User[userList.size()]));
		
		// event mocking
		when(restOperations.getForObject( Matchers.matches("/[^/]*/courses/\\d+/events"), Matchers.eq(Event[].class), Matchers.anyInt()) ).thenReturn(eventList.toArray(new Event[eventList.size()]));
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Event.class), Matchers.anyInt(), Matchers.anyInt()) ).thenReturn(event);
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Event[].class), Matchers.anyString()) ).thenReturn(eventList.toArray(new Event[eventList.size()]));
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Event[].class), Matchers.anyString(), Matchers.anyInt()) ).thenReturn(eventList.toArray(new Event[eventList.size()]));
		
		// grade mocking
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Grade[].class), Matchers.anyInt()) ).thenReturn(gradeList.toArray(new Grade[gradeList.size()]));
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Grade.class), Matchers.anyInt(), Matchers.anyInt()) ).thenReturn(grade);
		
		// department mocking
		when(restOperations.getForObject( Matchers.anyString(), Matchers.eq(Department[].class)) ).thenReturn(departmentList.toArray(new Department[departmentList.size()]));
		when(restOperations.postForLocation( Matchers.anyString(), Matchers.any(), Matchers.anyInt(), Matchers.anyInt()) ).thenReturn(new URI(""));
	}

	@Test
	public final void testIPeerWebService() {
		assertNotNull(webService);
	}

	@Test
	public final void testGetCourseList() {
		List<Course> cl = webService.getCourseList();
		assertTrue(cl.size() == 3);
		Course c = cl.get(0);
		assertTrue(c.getId() == 1);
		assertTrue("Test Course".equals(c.getTitle()));
		
		c = cl.get(1);
		assertTrue(c.getId() == 2);
		assertTrue("Test Course 2".equals(c.getTitle()));
		
		c = cl.get(2);
		assertTrue(c.getId() == 3);
		assertTrue("Test Course 3".equals(c.getTitle()));
	}

	@Test
	public final void testGetCourse() {
		Course c = webService.getCourse(1);
		assertTrue(c.getId() == 1);
		assertTrue("Test Course".equals(c.getTitle()));
	}
	
	@Test
	public final void testCreateCourse() {
		Course c = webService.createCourse(course);
		assertTrue(c.getId() == 1);
	}
	
	@Test
	public final void testDeleteCourse() {
		boolean result = webService.deleteCourse(1);
		assertTrue(result);
	}
	
	@Test
	public final void testUpdateCourse() {
		boolean result = webService.updateCourse(course);
		assertTrue(result);
	}

	/************* User API Test *************/
	
	@Test
	public final void testGetUserList() {
		List<User> ul = webService.getUserList();
		assertTrue(ul.size() == 3);
		User u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("username1".equals(u.getUsername()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("username2".equals(u.getUsername()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("username3".equals(u.getUsername()));
	}
	
	@Test
	public final void testGetUser() {
		User c = webService.getUser(1);
		assertTrue(c.getId() == 1);
		assertTrue("username1".equals(c.getUsername()));
	}
	
	@Test
	public final void testCreateUser() {
		User c = webService.createUser(user);
		assertTrue(c.getId() == 1);
	}
	
	@Test
	public final void testCreateUsers() {
		List<User> ul = webService.createUsers(userList);
		assertTrue(ul.size() == 3);
		User u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("username1".equals(u.getUsername()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("username2".equals(u.getUsername()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("username3".equals(u.getUsername()));
	}
	
	@Test
	public final void testDeleteUser() {
		boolean result = webService.deleteUser(1);
		assertTrue(result);
	}
	
	@Test
	public final void testUpdateUser() {
		boolean result = webService.updateUser(user);
		assertTrue(result);
	}
	
	/************* Group API Test *************/
	
	@Test
	public final void testGetGroupList() {
		List<Group> ul = webService.getGroupList(1);
		assertTrue(ul.size() == 3);
		Group u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("groupname1".equals(u.getName()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("groupname2".equals(u.getName()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("groupname3".equals(u.getName()));
	}
	
	@Test
	public final void testGetGroup() {
		Group c = webService.getGroup(1);
		assertTrue(c.getId() == 1);
		assertTrue("groupname1".equals(c.getName()));
	}
	
	@Test
	public final void testCreateGroup() {
		Group c = webService.createGroup(1, group);
		assertTrue(c.getId() == 1);
	}
	
	@Test
	public final void testCreateGroups() {
		List<Group> ul = webService.createGroups(1, groupList);
		assertTrue(ul.size() == 3);
		Group u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("groupname1".equals(u.getName()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("groupname2".equals(u.getName()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("groupname3".equals(u.getName()));
	}
	
	@Test
	public final void testDeleteGroup() {
		boolean result = webService.deleteGroup(1);
		assertTrue(result);
	}
	
	@Test
	public final void testUpdateGroup() {
		boolean result = webService.updateGroup(group);
		assertTrue(result);
	}
	
	/************* Course/User API Test *************/
	
	@Test
	public final void testGetUsersInCourse() {
		List<User> ul = webService.getUsersInCourse(1);
		assertTrue(ul.size() == 3);
		User u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("username1".equals(u.getUsername()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("username2".equals(u.getUsername()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("username3".equals(u.getUsername()));
	}
	
	@Test
	public final void testEnrolUsersInCourse() {
		List<User> ul = webService.enrolUsersInCourse(1, userList);
		assertTrue(ul.size() == 3);
		User u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("username1".equals(u.getUsername()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("username2".equals(u.getUsername()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("username3".equals(u.getUsername()));
	}
	
	@Test
	public final void testRemoveUsersFromCourse() {
		boolean result = webService.removeUserFromCourse(1, user);
		assertTrue(result);
	}
	
	/************* Group/User API Test *************/
	
	@Test
	public final void testGetUsersInGroup() {
		List<User> ul = webService.getUsersInGroup(1);
		assertTrue(ul.size() == 3);
		User u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("username1".equals(u.getUsername()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("username2".equals(u.getUsername()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("username3".equals(u.getUsername()));
	}
	
	@Test
	public final void testEnrolUsersInGroup() {
		List<User> ul = webService.enrolUsersInGroup(1, userList);
		assertTrue(ul.size() == 3);
		User u = ul.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("username1".equals(u.getUsername()));
		
		u = ul.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("username2".equals(u.getUsername()));
		
		u = ul.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("username3".equals(u.getUsername()));
	}
	
	@Test
	public final void testRemoveUsersFromGroup() {
		boolean result = webService.removeUserFromGroup(1, user);
		assertTrue(result);
	}
	
	/************* Event API Test *************/
	
	@Test
	public final void testGetEventsInCourse() {
		List<Event> el = webService.getEventsInCourse(1);
		assertTrue(el.size() == 3);
		Event u = el.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("eventname1".equals(u.getTitle()));
		
		u = el.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("eventname2".equals(u.getTitle()));
		
		u = el.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("eventname3".equals(u.getTitle()));
	}
	
	@Test
	public final void testGetEvent() {
		Event event = webService.getEvent(1);
		assertTrue(1 == event.getId());
		assertTrue("eventname1".equals(event.getTitle()));
	}
	
	@Test
	public final void testGetEventsForUser() {
		List<Event> el = webService.getEventsForUser("administrator");
		assertTrue(el.size() == 3);
		Event u = el.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("eventname1".equals(u.getTitle()));
		
		u = el.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("eventname2".equals(u.getTitle()));
		
		u = el.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("eventname3".equals(u.getTitle()));
	}
	
	@Test
	public final void testGetEventsForUserInCourse() {
		List<Event> el = webService.getEventsForUserInCourse("administrator",1);
		assertTrue(el.size() == 3);
		Event u = el.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("eventname1".equals(u.getTitle()));
		
		u = el.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("eventname2".equals(u.getTitle()));
		
		u = el.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("eventname3".equals(u.getTitle()));
	}
	
	/************* Grade API Test *************/
	
	@Test
	public final void testGetGradesInCourse() {
		List<Grade> el = webService.getGradesInEvent(1);
		assertTrue(el.size() == 3);
		Grade u = el.get(0);
		assertTrue(u.getId() == 1);
		assertTrue(10.5 == u.getGrade());
		
		u = el.get(1);
		assertTrue(u.getId() == 2);
		assertTrue(10.6 == (u.getGrade()));
		
		u = el.get(2);
		assertTrue(u.getId() == 3);
		assertTrue(10.7 == u.getGrade());
	}
	
	@Test
	public final void testGetGradesForUser() {
		Grade grade = webService.getGradesForUserInEvent(1,1);
		
		assertTrue(grade.getId() == 1);
		assertTrue(10.5 == grade.getGrade());
	}
	
	/************* Department API Test *************/
	
	@Test
	public final void testGetDepartmentList() {
		List<Department> dl = webService.getDepartmentList();
		assertTrue(dl.size() == 3);
		Department u = dl.get(0);
		assertTrue(u.getId() == 1);
		assertTrue("department1".equals(u.getName()));
		
		u = dl.get(1);
		assertTrue(u.getId() == 2);
		assertTrue("department2".equals(u.getName()));
		
		u = dl.get(2);
		assertTrue(u.getId() == 3);
		assertTrue("department3".equals(u.getName()));
	}
	
	@Test
	public final void testAssignCourseToDepartment() {
		assertTrue(webService.assignCourseToDepartment(1,1));
	}
}
