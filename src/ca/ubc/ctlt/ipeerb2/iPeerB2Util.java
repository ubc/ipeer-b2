package ca.ubc.ctlt.ipeerb2;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

public class iPeerB2Util {
	
	public static Group findGroupInList(Group group, List<Group> groupList) {
		for(Group g : groupList) {
			if (g.getName() != null && g.getName().equals(group.getName())) {
				return g;
			}
		}
		
		return null;
	}
	
	public static User findUserInList(User user, List<User> userList) {
		for(User u : userList) {
			if (u.getUsername() != null && u.getUsername().equals(user.getUsername())) {
				return u;
			}
		}
		
		return null;
	}
}
