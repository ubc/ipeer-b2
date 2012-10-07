package ca.ubc.ctlt.ipeerb2.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.ubc.ctlt.ipeerb2.domain.User;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private iPeerWebService webService;

	public void setWebService(iPeerWebService webService) {
		this.webService = webService;
	}
	
	@Override
	public List<User> getUserList() {
		List<User> userList = webService.getUserList();
		return userList;
	}

	@Override
	public User getUserById(int id) {
		return webService.getUser(id);
	}

	@Override
	public User createUser(User user) {
		return webService.createUser(user);
	}

	@Override
	public List<User> createUsers(List<User> users) {
		return webService.createUsers(users);
	}
	
	@Override
	public boolean deleteUser(int id) {
		return webService.deleteUser(id);
	}

	@Override
	public boolean updateUser(User user) {
		return webService.updateUser(user);
	}

	// Course/User API
	@Override
	public List<User> getUsersInCourse(int courseId) {
		return webService.getUsersInCourse(courseId);
	}

	@Override
	public List<User> enrolUsersInCourse(int courseId, List<User> users) {
		return webService.enrolUsersInCourse(courseId, users);
	}

	@Override
	public List<User> enrolUserInCourse(int courseId, User user) {
		List<User> users = new ArrayList<User>();
		users.add(user);
		return enrolUsersInCourse(courseId, users);
	}

	@Override
	public boolean removeUsersFromCourse(int courseId, List<User> users) {
		for (User user : users) {
			if(!removeUserFromCourse(courseId, user)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean removeUserFromCourse(int courseId, User user) {
		return webService.removeUserFromCourse(courseId, user);
	}

	// Group/User API
	@Override
	public List<User> getUsersInGroup(int groupId) {
		return webService.getUsersInGroup(groupId);
	}

	@Override
	public List<User> enrolUsersInGroup(int groupId, List<User> users) {
		return webService.enrolUsersInGroup(groupId, users);
	}

	@Override
	public List<User> enrolUserInGroup(int groupId, User user) {
		List<User> users = new ArrayList<User>();
		users.add(user);
		return enrolUsersInGroup(groupId, users);
	}

	@Override
	public boolean removeUsersFromGroup(int groupId, List<User> users) {
		for (User user : users) {
			if(!removeUserFromGroup(groupId, user)) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean removeUserFromGroup(int groupId, User user) {
		return webService.removeUserFromGroup(groupId, user);
	}
}
