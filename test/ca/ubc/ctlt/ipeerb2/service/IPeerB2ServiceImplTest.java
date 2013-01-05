package ca.ubc.ctlt.ipeerb2.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles({"dev", "integration"})
public class IPeerB2ServiceImplTest {
	@Autowired
	private IPeerB2ServiceImpl service;
	
	@Autowired
	private blackboard.data.user.User bbUser;
	
	@Autowired
	private CourseMembership membership;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public final void testCreateCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testDisconnectCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testDeleteCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testSyncClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetGroupsInBbCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetGroupsInIPeerCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testPushGroups() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testPullGroups() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testSyncGrades() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetEventsInCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetEventsForUserInCourse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetDepartments() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testAssignCourseToDepartment() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetBbClassSize() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGetIPeerClassSize() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public final void testBbUserToUserUser() {
		User user = service.bbUserToUser(bbUser);
		assertEquals(user.getUsername(), bbUser.getBatchUid());
		assertEquals(user.getFirstName(), bbUser.getGivenName());
		assertEquals(user.getLastName(), bbUser.getFamilyName());
		assertEquals(user.getStudentId(), bbUser.getStudentId());
		assertEquals(user.getEmail(), bbUser.getEmailAddress());
	}
//
//	@Test
//	public final void testUserToBbUser() {
//		fail("Not yet implemented");
//	}

	@Test
	public final void testBbUserToUserCourseMembership() {
		when(membership.getUser()).thenReturn(bbUser);
		Configuration config = service.getConfiguration();
		when(config.getSetting(Configuration.ROLE_MAPPING_PREFIX + "." +Role.INSTRUCTOR.getIdentifier()))
				.thenReturn(Integer.toString(ca.ubc.ctlt.ipeerb2.domain.Role.INSTRUCTOR));
		when(config.getSetting(Configuration.ROLE_MAPPING_PREFIX + "." +Role.STUDENT.getIdentifier()))
				.thenReturn(Integer.toString(ca.ubc.ctlt.ipeerb2.domain.Role.STUDENT));
			
		// Removed the following assertion temporarily as there is not good way to mock CourseMembership.Role
		// in mockito
//		// test student role
//		CourseMembership.Role courseRole = mock(CourseMembership.Role.class);
//		CourseRole studentCourseRole = mock(CourseRole.class);
//		when(membership.getRole()).thenReturn(courseRole);
//		when(courseRole.getDbRole()).thenReturn(studentCourseRole);
//		when(studentCourseRole.getIdentifier()).thenReturn(CourseRole.Ident.Student.getIdentifier());
//		User user = service.bbUserToUser(membership);
//		assertEquals(ca.ubc.ctlt.ipeerb2.domain.Role.STUDENT, user.getRoleId());
//		
//		// test student role
//		when(membership.getRole()).thenReturn(Role.INSTRUCTOR);
//		when(membership.getRole().getDbRole().getIdentifier()).thenReturn(CourseRole.Ident.Instructor.getIdentifier());
//		user = service.bbUserToUser(membership);
//		assertEquals(ca.ubc.ctlt.ipeerb2.domain.Role.INSTRUCTOR, user.getRoleId());
		
		// test unknown role
		when(membership.getRole()).thenReturn(null);
		User user = service.bbUserToUser(membership);
		assertEquals(ca.ubc.ctlt.ipeerb2.domain.Role.STUDENT, user.getRoleId());
	}

//	@Test
//	public final void testBbGroupToGroup() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGroupToBbGroup() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public final void testGradeToBbScore() {
//		fail("Not yet implemented");
//	}

}
