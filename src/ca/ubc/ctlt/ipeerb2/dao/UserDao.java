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
}
