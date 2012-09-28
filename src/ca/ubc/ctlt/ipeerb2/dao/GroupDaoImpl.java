package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

public class GroupDaoImpl implements GroupDao {
	private iPeerWebService webService;

	public void setWebService(iPeerWebService webService) {
		this.webService = webService;
	}
	
	@Override
	public List<Group> getGroupList(int courseId) {
		List<Group> list = webService.getGroupList(courseId);
		return list;
	}

	@Override
	public Group getGroup(int id) {
		return webService.getGroup(id);
	}

	@Override
	public Group createGroup(int courseId, Group group) {
		return webService.createGroup(courseId, group);
	}
	
	@Override
	public List<Group> createGroups(int courseId, List<Group> groups) {
		return webService.createGroups(courseId, groups);
	}

	@Override
	public boolean deleteGroup(int id) {
		return webService.deleteGroup(id);
	}

	@Override
	public boolean updateGroup(Group group) {
		return webService.updateGroup(group);
	}

}
