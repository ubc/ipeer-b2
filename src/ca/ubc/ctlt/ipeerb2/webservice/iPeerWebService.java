package ca.ubc.ctlt.ipeerb2.webservice;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

public class iPeerWebService {
	@Autowired
    private MessageSource messageSource;
	
	private final RestOperations restTemplate;
	private final String serverUrl;

	public static final String API_VERSION = "v1";
	public static final String API_COURSE = "/" + API_VERSION + "/courses";
	public static final String API_USER = "/" + API_VERSION + "/users";
	public static final String API_GROUP = "/" + API_VERSION + "/courses/{course_id}/groups";
	private static final Logger logger = Logger.getLogger(iPeerWebService.class);
	
	public iPeerWebService(RestOperations restTemplate, String serverUrl) {
		this.restTemplate = restTemplate;
		this.serverUrl = serverUrl;
	}

	/************** Course APIs ****************/
	
	public List<Course> getCourseList() {
		Course[] courses = restTemplate.getForObject(serverUrl + API_COURSE, Course[].class);
		List<Course> result = Arrays.asList(courses);
		return result;
	}
	
	public Course getCourse(int id) {
		Course result = null;
		try {
			result = restTemplate.getForObject(serverUrl + API_COURSE + "/{id}", Course.class, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Course with id " + id + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_course", new Object[]{id}, null), e);
	    }
		
		return result;
	}
	
	public Course createCourse(Course course) {
		Course result = null;
		try {
			result = restTemplate.postForObject(serverUrl + API_COURSE, course,
					Course.class);
		} catch (RestClientException e) {
			logger.warn("Course creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_course", null, null), e);
		}
		
		return result;
	}
	
	public boolean deleteCourse(int id) {
		try {
			restTemplate.delete(serverUrl + API_COURSE + "/{id}", String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Course deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_course", null, null), e);
		}
		
		return true;
	}
	
	public boolean updateCourse(Course course) {
		try {
			restTemplate.put(serverUrl + API_COURSE + "/{id}", course, String.valueOf(course.getId()));
		} catch (RestClientException e) {
			logger.warn("Course update failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_update_course", null, null), e);
		}
		
		return true;
	}
	
	/************** User APIs ****************/
	
	public List<User> getUserList() {
		User[] users = restTemplate.getForObject(serverUrl + API_USER, User[].class);
		List<User> result = Arrays.asList(users);
		return result;
	}
	
	public User getUser(int id) {
		User result = null;
		try {
			result = restTemplate.getForObject(serverUrl + API_USER + "/{id}", User.class, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("User with id " + id + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_user", new Object[]{id}, null), e);
	    }
		
		return result;
	}
	
	public User createUser(User user) {
		User result = null;
		try {
			result = restTemplate.postForObject(serverUrl + API_USER, user, User.class);
		} catch (RestClientException e) {
			logger.warn("User creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_user", null, null), e);
		}
		
		return result;
	}
	
	public List<User> createUsers(List<User> users) {
		User[] result = null;
		try {
			result = restTemplate.postForObject(serverUrl + API_USER, users.toArray(new User[users.size()]), User[].class);
		} catch (RestClientException e) {
			logger.warn("User creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_users", null, null), e);
		}
		
		return Arrays.asList(result);
	}
	
	public boolean deleteUser(int id) {
		try {
			restTemplate.delete(serverUrl + API_USER + "/{id}", String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("User deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_user", null, null), e);
		}
		
		return true;
	}
	
	public boolean updateUser(User user) {
		try {
			restTemplate.put(serverUrl + API_USER + "/{id}", user, String.valueOf(user.getId()));
		} catch (RestClientException e) {
			logger.warn("User update failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_update_user", null, null), e);
		}
		
		return true;
	}
	
	/************** Group APIs ****************/
	
	public List<Group> getGroupList(int courseId) {
		Group[] groups = restTemplate.getForObject(serverUrl + API_GROUP, Group[].class, courseId);
		List<Group> result = Arrays.asList(groups);
		return result;
	}
	
	public Group getGroup(int id) {
		Group result = null;
		try {
			result = restTemplate.getForObject(serverUrl + API_GROUP + "/{id}", Group.class, 0, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Group with id " + id + " not found! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_get_group", new Object[]{id}, null), e);
	    }
		
		return result;
	}
	
	public Group createGroup(int courseId, Group group) {
		Group result = null;
		try {
			result = restTemplate.postForObject(serverUrl + API_GROUP, group, Group.class, courseId);
		} catch (RestClientException e) {
			logger.warn("Group creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_group", null, null), e);
		}
		
		return result;
	}
	
	public List<Group> createGroups(int courseId, List<Group> groups) {
		Group[] result = null;
		try {
			result = restTemplate.postForObject(serverUrl + API_GROUP, groups.toArray(new Group[groups.size()]), Group[].class, courseId);
		} catch (RestClientException e) {
			logger.warn("Group creation failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_create_groups", null, null), e);
		}
		
		return Arrays.asList(result);
	}
	
	public boolean deleteGroup(int id) {
		try {
			restTemplate.delete(serverUrl + API_GROUP + "/{id}", 0, String.valueOf(id));
		} catch (RestClientException e) {
			logger.warn("Group deletion failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_delete_group", null, null), e);
		}
		
		return true;
	}
	
	public boolean updateGroup(Group group) {
		try {
			restTemplate.put(serverUrl + API_GROUP + "/{id}", group, 0, String.valueOf(group.getId()));
		} catch (RestClientException e) {
			logger.warn("Group update failed! Status code=" + e.getMessage());
			throw new RuntimeException(messageSource.getMessage("message.failed_update_group", null, null), e);
		}
		
		return true;
	}
}
