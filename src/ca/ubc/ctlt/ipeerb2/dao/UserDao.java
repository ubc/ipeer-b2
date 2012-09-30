package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.User;

public interface UserDao {
	public List<User> getUserList();
	public User getUserById(int id);
	public User createUser(User user);
	public List<User> createUsers(List<User> user);
	public boolean deleteUser(int id);
	public boolean updateUser(User user);
	
	public List<User> getUsersInCourse(int courseId);
	public List<User> enrolUsersInCourse(int courseId, List<User> users);
	public List<User> enrolUserInCourse(int courseId, User user);
	public boolean removeUsersFromCourse(int courseId, List<User> users);
	public boolean removeUserFromCourse(int courseId, User user);
	
	public List<User> getUsersInGroup(int groupId);
	public List<User> enrolUsersInGroup(int groupId, List<User> users);
	public List<User> enrolUserInGroup(int groupId, User user);
	public boolean removeUsersFromGroup(int groupId, List<User> users);
	public boolean removeUserFromGroup(int groupId, User user);
}
