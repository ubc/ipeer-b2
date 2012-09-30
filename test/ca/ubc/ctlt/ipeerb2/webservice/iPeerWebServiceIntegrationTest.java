package ca.ubc.ctlt.ipeerb2.webservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles({"dev", "integration"})
public class iPeerWebServiceIntegrationTest {
	
	@Autowired
	private iPeerWebService webService;
	
	@Autowired
	private Course courseToCreate;
	
	@Autowired
	private Course invalidCourseToCreate;
	
	@Autowired
	private Course courseToUpdate;
	
	@Autowired
	private Course invalidCourseToUpdate;
	
	@Autowired
	private User userToCreate;
	
	@Autowired
	private User invalidUserToCreate;
	
	@Autowired
	private User userToUpdate;
	
	@Autowired
	private User invalidUserToUpdate;
	
	@Resource
	private List<User> userList;
	
	@Autowired
	private Group groupToCreate;
	
	@Autowired
	private Group invalidGroupToCreate;
	
	@Autowired
	private Group groupToUpdate;
	
	@Autowired
	private Group invalidGroupToUpdate;

	/************* Course API Tests ****************/
	
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
		Course course = webService.getCourse(1);
		assertNotNull(course);
		assertTrue(course.getId() == 1);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testGetInvalidCourse() {
		webService.getCourse(999);
		fail("No exception when requesting an invalid course!");
	}
	
	@Test
	public final void testCreateCourse() {
		Course course = webService.createCourse(courseToCreate);
		assertTrue(course.getId() == 1);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testCreateInvalidCourse() {
		webService.createCourse(invalidCourseToCreate);
		fail("No exception when creating an invalid course!");
	}
	
	@Test
	public final void testDeleteCourse() {
		boolean result = webService.deleteCourse(1);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testDeleteNonExistingCourse() {
		webService.deleteCourse(999);
		fail("No exception when deleting an invalid course!");
	}
	
	@Test
	public final void testUpdateCourse() {
		boolean result = webService.updateCourse(courseToUpdate);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testUpdateInvalidCourse() {
		webService.updateCourse(invalidCourseToUpdate);
		fail("No exception when deleting an invalid course!");
	}
	
	/************* User API Tests ****************/
	
	@Test
	public final void testGetUserList() {
		List<User> cl = webService.getUserList();
		assertTrue(cl.size() == 3);
		User c = cl.get(0);
		assertTrue(c.getId() == 1);
		assertTrue("username1".equals(c.getUsername()));
		
		c = cl.get(1);
		assertTrue(c.getId() == 2);
		assertTrue("username2".equals(c.getUsername()));
		
		c = cl.get(2);
		assertTrue(c.getId() == 3);
		assertTrue("username3".equals(c.getUsername()));
	}
	
	@Test
	public final void testGetUser() {
		User user = webService.getUser(1);
		assertNotNull(user);
		assertTrue(user.getId() == 1);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testGetInvalidUser() {
		webService.getUser(999);
		fail("No exception when requesting an invalid user!");
	}
	
	@Test
	public final void testCreateUser() {
		User user = webService.createUser(userToCreate);
		assertTrue(user.getId() == 1);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testCreateInvalidUser() {
		webService.createUser(invalidUserToCreate);
		fail("No exception when creating an invalid user!");
	}
	
	@Test
	public final void testDeleteUser() {
		boolean result = webService.deleteUser(1);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testDeleteNonExistingUser() {
		webService.deleteUser(999);
		fail("No exception when deleting an invalid user!");
	}
	
	@Test
	public final void testUpdateUser() {
		boolean result = webService.updateUser(userToUpdate);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testUpdateInvalidUser() {
		webService.updateUser(invalidUserToUpdate);
		fail("No exception when deleting an invalid user!");
	}
	
	/************* Group API Tests ****************/
	
	@Test
	public final void testGetGroupList() {
		List<Group> cl = webService.getGroupList(1);
		assertTrue(cl.size() == 3);
		Group c = cl.get(0);
		assertTrue(c.getId() == 1);
		assertTrue("groupname1".equals(c.getName()));
		
		c = cl.get(1);
		assertTrue(c.getId() == 2);
		assertTrue("groupname2".equals(c.getName()));
		
		c = cl.get(2);
		assertTrue(c.getId() == 3);
		assertTrue("groupname3".equals(c.getName()));
	}
	
	@Test
	public final void testGetGroup() {
		Group group = webService.getGroup(1);
		assertNotNull(group);
		assertTrue(group.getId() == 1);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testGetInvalidGroup() {
		webService.getGroup(999);
		fail("No exception when requesting an invalid group!");
	}
	
	@Test
	public final void testCreateGroup() {
		Group group = webService.createGroup(1, groupToCreate);
		assertTrue(group.getId() == 1);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testCreateInvalidGroup() {
		webService.createGroup(1, invalidGroupToCreate);
		fail("No exception when creating an invalid group!");
	}
	
	@Test
	public final void testDeleteGroup() {
		boolean result = webService.deleteGroup(1);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testDeleteNonExistingGroup() {
		webService.deleteGroup(999);
		fail("No exception when deleting an invalid group!");
	}
	
	@Test
	public final void testUpdateGroup() {
		boolean result = webService.updateGroup(groupToUpdate);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testUpdateInvalidGroup() {
		webService.updateGroup(invalidGroupToUpdate);
		fail("No exception when deleting an invalid group!");
	}
	
	/************* Course/User API Tests ****************/
	
	@Test
	public final void testGetUsersInCourse() {
		List<User> cl = webService.getUsersInCourse(1);
		assertTrue(cl.size() == 3);
		User c = cl.get(0);
		assertTrue(c.getId() == 1);
		assertTrue("username1".equals(c.getUsername()));
		
		c = cl.get(1);
		assertTrue(c.getId() == 2);
		assertTrue("username2".equals(c.getUsername()));
		
		c = cl.get(2);
		assertTrue(c.getId() == 3);
		assertTrue("username3".equals(c.getUsername()));
	}
	
	@Test(expected=RuntimeException.class)
	public final void testGetUsersInInvalidCourse() {
		webService.getUsersInCourse(999);
		fail("No exception when get users from invalid course!");
	}
	
	@Test
	public final void testEnrolUserInCourse() {
		List<User> ul = webService.enrolUsersInCourse(1, userList);
		assertTrue(ul.size() == userList.size());
	}
	
	@Test(expected=RuntimeException.class)
	public final void testEnrolInvalidUserInCourse() {
		webService.enrolUsersInCourse(1, Arrays.asList(invalidUserToUpdate));
		fail("No exception when enroling invalid users to course!");
	}
	
	@Test
	public final void testRemoveUsersFromCourse() {
		boolean result = webService.removeUserFromCourse(1, userToUpdate);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testRemoveInvalidUsersFromCourse() {
		webService.removeUserFromCourse(1, invalidUserToUpdate);
		fail("No exception when removing invalid users to course!");
	}
	
	/************* Group/User API Tests ****************/
	
	@Test
	public final void testGetUsersInGroup() {
		List<User> cl = webService.getUsersInGroup(1);
		assertTrue(cl.size() == 3);
		User c = cl.get(0);
		assertTrue(c.getId() == 1);
		assertTrue("username1".equals(c.getUsername()));
		
		c = cl.get(1);
		assertTrue(c.getId() == 2);
		assertTrue("username2".equals(c.getUsername()));
		
		c = cl.get(2);
		assertTrue(c.getId() == 3);
		assertTrue("username3".equals(c.getUsername()));
	}
	
	@Test(expected=RuntimeException.class)
	public final void testGetUsersInInvalidGroup() {
		webService.getUsersInGroup(999);
		fail("No exception when get users from invalid group!");
	}
	
	@Test
	public final void testEnrolUserInGroup() {
		List<User> ul = webService.enrolUsersInGroup(1, userList);
		assertTrue(ul.size() == userList.size());
	}
	
	@Test(expected=RuntimeException.class)
	public final void testEnrolInvalidUserInGroup() {
		webService.enrolUsersInGroup(1, Arrays.asList(invalidUserToUpdate));
		fail("No exception when enroling invalid users to group!");
	}
	
	@Test
	public final void testRemoveUsersFromGroup() {
		boolean result = webService.removeUserFromGroup(1, userToUpdate);
		assertTrue(result);
	}
	
	@Test(expected=RuntimeException.class)
	public final void testRemoveInvalidUsersFromGroup() {
		webService.removeUserFromGroup(1, invalidUserToUpdate);
		fail("No exception when removing invalid users to group!");
	}
}
