package ca.ubc.ctlt.ipeerb2.webservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.ubc.ctlt.ipeerb2.domain.Course;
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
}
