package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Group;

public interface GroupDao {
	
	List<Group> getGroupList(int courseId);

	Group getGroup(int id);

	Group createGroup(int courseId, Group group);
	
	List<Group> createGroups(int courseId, List<Group> groups);

	boolean deleteGroup(int id);

	boolean updateGroup(Group course);
}
