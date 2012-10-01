package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Group;

public interface GroupDao {
	
	public List<Group> getGroupList(int courseId);

	public Group getGroup(int id);

	public Group createGroup(int courseId, Group group);
	
	public List<Group> createGroups(int courseId, List<Group> groups);

	public boolean deleteGroup(int id);

	public boolean updateGroup(Group course);
}
