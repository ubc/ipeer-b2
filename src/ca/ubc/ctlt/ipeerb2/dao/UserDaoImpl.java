package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.User;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

public class UserDaoImpl implements UserDao {
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
}
