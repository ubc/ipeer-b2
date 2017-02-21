package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.User;

public interface UserDao {
	List<User> getUserList();
	User getUserById(int id);
	User createUser(User user);
	List<User> createUsers(List<User> user);
	boolean deleteUser(int id);
	boolean updateUser(User user);
	
	List<User> getUsersInCourse(int courseId);
	List<User> enrolUsersInCourse(int courseId, List<User> users);
	List<User> enrolUserInCourse(int courseId, User user);
	boolean removeUsersFromCourse(int courseId, List<User> users);
	boolean removeUserFromCourse(int courseId, User user);
	
	List<User> getUsersInGroup(int groupId);
	List<User> enrolUsersInGroup(int groupId, List<User> users);
	List<User> enrolUserInGroup(int groupId, User user);
	boolean removeUsersFromGroup(int groupId, List<User> users);
	boolean removeUserFromGroup(int groupId, User user);
}
