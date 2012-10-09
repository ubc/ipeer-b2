package ca.ubc.ctlt.ipeerb2.webservice;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Event;
import ca.ubc.ctlt.ipeerb2.domain.Grade;
import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

@Component
public class iPeerWebService {
	@Autowired
    private MessageSource messageSource;
	
	@Resource(name="restTemplate")
	private RestTemplate restTemplate;
	
	@Autowired
	private Configuration configuration;

	public static final String API_VERSION = "v1";
	public static final String API_COURSE = "/" + API_VERSION + "/courses";
	public static final String API_USER = "/" + API_VERSION + "/users";
	public static final String API_GROUP = "/" + API_VERSION + "/courses/{course_id}/groups";
	public static final String API_COURSE_USER = "/" + API_VERSION + "/courses/{course_id}/users";
	public static final String API_GROUP_USER = "/" + API_VERSION + "/groups/{group_id}/users";
	public static final String API_EVENT = "/" + API_VERSION + "/courses/{course_id}/events";
	public static final String API_GRADE = "/" + API_VERSION + "/events/{event_id}/grades";
	private static final Logger logger = Logger.getLogger(iPeerWebService.class);
	
	public String getServerUrl() {
		return configuration.getSetting(Configuration.IPEER_URL);
	}

	/************** Course APIs ****************/
	
	public List<Course> getCourseList() {
		Course[] courses = restTemplate.getForObject(getServerUrl() + API_COURSE, Course[].class);
		List<Course> result = Arrays.asList(courses);
		return result;
	}

	public Course getCourse(int id) {
		Course result = null;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_COURSE + "/{id}", Course.class, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Course with id " + id + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_course", new Object[]{id}, null), e);
	    }
		
		return result;
	}
	
	public Course createCourse(Course course) {
		Course result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_COURSE, course,
					Course.class);
		} catch (RestClientException e) {
			logger.warn("Course creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_course", null, null), e);
		}
		
		return result;
	}
	
	public boolean deleteCourse(int id) {
		try {
			restTemplate.delete(getServerUrl() + API_COURSE + "/{id}", String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Course deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_course", null, null), e);
		}
		
		return true;
	}
	
	public boolean updateCourse(Course course) {
		try {
			restTemplate.put(getServerUrl() + API_COURSE + "/{id}", course, String.valueOf(course.getId()));
		} catch (RestClientException e) {
			logger.warn("Course update failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_update_course", null, null), e);
		}
		
		return true;
	}
	
	/************** User APIs ****************/
	
	public List<User> getUserList() {
		User[] users = restTemplate.getForObject(getServerUrl() + API_USER, User[].class);
		List<User> result = Arrays.asList(users);
		return result;
	}
	
	public User getUser(int id) {
		User result = null;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_USER + "/{id}", User.class, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("User with id " + id + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_user", new Object[]{id}, null), e);
	    }
		
		return result;
	}
	
	public User createUser(User user) {
		User result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_USER, user, User.class);
		} catch (RestClientException e) {
			logger.warn("User creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_user", null, null), e);
		}
		
		return result;
	}
	
	public List<User> createUsers(List<User> users) {
		User[] result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_USER, users.toArray(new User[users.size()]), User[].class);
		} catch (RestClientException e) {
			logger.warn("User creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_users", null, null), e);
		}
		
		return Arrays.asList(result);
	}
	
	public boolean deleteUser(int id) {
		try {
			restTemplate.delete(getServerUrl() + API_USER + "/{id}", String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("User deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_user", null, null), e);
		}
		
		return true;
	}
	
	public boolean updateUser(User user) {
		try {
			restTemplate.put(getServerUrl() + API_USER + "/{id}", user, String.valueOf(user.getId()));
		} catch (RestClientException e) {
			logger.warn("User update failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_update_user", null, null), e);
		}
		
		return true;
	}
	
	/************** Group APIs ****************/
	
	public List<Group> getGroupList(int courseId) {
		Group[] groups = restTemplate.getForObject(getServerUrl() + API_GROUP, Group[].class, courseId);
		List<Group> result = Arrays.asList(groups);
		return result;
	}
	
	public Group getGroup(int id) {
		Group result = null;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_GROUP + "/{id}", Group.class, 0, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Group with id " + id + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_group", new Object[]{id}, null), e);
	    }
		
		return result;
	}
	
	public Group createGroup(int courseId, Group group) {
		Group result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_GROUP, group, Group.class, courseId);
		} catch (RestClientException e) {
			logger.warn("Group creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_group", null, null), e);
		}
		
		return result;
	}
	
	public List<Group> createGroups(int courseId, List<Group> groups) {
		Group[] result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_GROUP, groups.toArray(new Group[groups.size()]), Group[].class, courseId);
		} catch (RestClientException e) {
			logger.warn("Group creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_groups", null, null), e);
		}
		
		return Arrays.asList(result);
	}
	
	public boolean deleteGroup(int id) {
		try {
			restTemplate.delete(getServerUrl() + API_GROUP + "/{id}", 0, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Group deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_group", null, null), e);
		}
		
		return true;
	}
	
	public boolean updateGroup(Group group) {
		try {
			restTemplate.put(getServerUrl() + API_GROUP + "/{id}", group, 0, String.valueOf(group.getId()));
		} catch (RestClientException e) {
			logger.warn("Group update failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_update_group", null, null), e);
		}
		
		return true;
	}
	
	/************** Course/User APIs ****************/
	
	public List<User> getUsersInCourse(int courseId) {
		User[] users = restTemplate.getForObject(getServerUrl() + API_COURSE_USER, User[].class, courseId);
		List<User> result = Arrays.asList(users);
		return result;
	}
	
	public List<User> enrolUsersInCourse(int courseId, List<User> users) {
		User[] result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_COURSE_USER, users.toArray(), User[].class, courseId);
		} catch (RestClientException e) {
			logger.warn("User enrolment failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_enrol_user", null, null), e);
		}
		
		return Arrays.asList(result);
	}
	
	public boolean removeUserFromCourse(int courseId, long userId) {
		try {
			restTemplate.delete(getServerUrl() + API_COURSE_USER + "/{user_id}", courseId, userId);
		} catch (RestClientException e) {
			logger.warn("User enrolment deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_enrolment", null, null), e);
		}
		
		return true;	
	}
	
	public boolean removeUserFromCourse(int courseId, User user) {
		return removeUserFromCourse(courseId, user.getId());
	}
	
	/************** Group/User APIs ****************/
	
	public List<User> getUsersInGroup(int groupId) {
		User[] users = restTemplate.getForObject(getServerUrl() + API_GROUP_USER, User[].class, groupId);
		List<User> result = Arrays.asList(users);
		return result;
	}
	
	public List<User> enrolUsersInGroup(int groupId, List<User> users) {
		User[] result = null;
		try {
			result = restTemplate.postForObject(getServerUrl() + API_GROUP_USER, users.toArray(), User[].class, groupId);
		} catch (RestClientException e) {
			logger.warn("User enrolment failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_enrol_user", null, null), e);
		}
		
		return Arrays.asList(result);
	}
	
	public boolean removeUserFromGroup(int groupId, long userId) {
		try {
			restTemplate.delete(getServerUrl() + API_GROUP_USER + "/{user_id}", groupId, userId);
		} catch (RestClientException e) {
			logger.warn("User enrolment deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_enrolment", null, null), e);
		}
		
		return true;	
	}
	
	public boolean removeUserFromGroup(int groupId, User user) {
		return removeUserFromGroup(groupId, user.getId());
	}
	
	/************** Event APIs ****************/
	
	public List<Event> getEventsInCourse(int courseId) {
		Event[] events = restTemplate.getForObject(getServerUrl() + API_EVENT, Event[].class, courseId);
		List<Event> result = Arrays.asList(events);
		return result;	
	}
	
	public Event getEvent(int eventId) {
		Event result = null;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_EVENT + "/{id}", Event.class, 0, eventId);
		} catch (RestClientException e) {
			logger.warn("Event with id " + eventId + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_event", new Object[]{eventId}, null), e);
	    }
		
		return result;	
	}
	
	public List<Event> getEventsForUser(String username) {
		Event[] result;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_USER + "/{username}/events", Event[].class, username);
		} catch (RestClientException e) {
			logger.warn("Events for user " + username + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_events_for_user", new Object[]{username}, null), e);
	    }
		
		return Arrays.asList(result);	
	}
	
	public List<Event> getEventsForUserInCourse(String username, int courseId) {
		Event[] result;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_COURSE + "/{course_id}/users/{username}/events", Event[].class, courseId, username);
		} catch (RestClientException e) {
			logger.warn("Events for user " + username + " in course not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_events_for_user_in_course", new Object[]{username, courseId}, null), e);
	    }
		
		return Arrays.asList(result);	
	}
	
	/************** Grade APIs ****************/
	
	public List<Grade> getGradesInEvent(int eventId) {
		Grade[] grades = restTemplate.getForObject(getServerUrl() + API_GRADE, Grade[].class, eventId);
		List<Grade> result = Arrays.asList(grades);
		return result;	
	}
	
	public Grade getGradesForUserInEvent(int userId, int eventId) {
		Grade result;
		try {
			result = restTemplate.getForObject(getServerUrl() + API_GRADE + "/{user_id}", Grade.class, eventId, userId);
		} catch (RestClientException e) {
			logger.warn("Grade for user with id " + userId + " in event with id " + eventId + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_grade_for_user", new Object[]{userId, eventId}, null), e);
	    }
		
		return result;	
	}
	
}
