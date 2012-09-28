package ca.ubc.ctlt.ipeerb2.webservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
}
