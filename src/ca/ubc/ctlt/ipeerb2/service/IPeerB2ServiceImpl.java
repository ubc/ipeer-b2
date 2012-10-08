package ca.ubc.ctlt.ipeerb2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackboard.data.ValidationException;
import blackboard.data.course.CourseMembership;
import blackboard.data.course.CourseMembership.Role;
import blackboard.data.gradebook.Score;
import blackboard.db.ConnectionNotAvailableException;
import blackboard.persist.PersistenceException;
import ca.ubc.ctlt.blackboardb2util.B2Util;
import ca.ubc.ctlt.blackboardb2util.GradeAdapter;
import ca.ubc.ctlt.blackboardb2util.GroupAdapter;
import ca.ubc.ctlt.blackboardb2util.UserAdapter;
import ca.ubc.ctlt.ipeerb2.iPeerB2Util;
import ca.ubc.ctlt.ipeerb2.dao.CourseDao;
import ca.ubc.ctlt.ipeerb2.dao.EventDao;
import ca.ubc.ctlt.ipeerb2.dao.GradeDao;
import ca.ubc.ctlt.ipeerb2.dao.GroupDao;
import ca.ubc.ctlt.ipeerb2.dao.UserDao;
import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Event;
import ca.ubc.ctlt.ipeerb2.domain.Grade;
import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

@Service
public class IPeerB2ServiceImpl implements IPeerB2Service, UserAdapter<User>, GroupAdapter<Group>, GradeAdapter<Grade> {
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private GradeDao gradeDao;
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private  HttpServletRequest request;
	
	@Override
	public boolean createCourse(Course course) {
		Course result = courseDao.createCourse(course);
		iPeerB2Util.setConnection(request, result.getId());
		
		return true;
	}

	@Override
	public boolean disconnectCourse(String bbCourseId) {
		iPeerB2Util.deleteConnection(request, bbCourseId);
		
		return true;
	}

	@Override
	public boolean deleteCourse(String bbCourseId) {
		if (courseDao.deleteCourse(iPeerB2Util.getIpeerCourseId(request))) {
			disconnectCourse(bbCourseId);
			return true;
		} 
			
		return false;
	}

	@Override
	public boolean syncClass(String bbCourseId) {
		try {
			userDao.enrolUsersInCourse(iPeerB2Util.getIpeerCourseId(request), B2Util.getUsersInCourse(bbCourseId, this));
		} catch (PersistenceException e) {
			throw new RuntimeException("Failed to load class list!", e);
		}
		
		return true;
	}

	@Override
	public boolean pushGroups(String bbCourseId) {
		int iPeerCourseId = iPeerB2Util.getIpeerCourseId(request);
		// we need groups from both
		List<Group> groupsInBb = null;
		try {
			groupsInBb = B2Util.getGroupsInCourse(bbCourseId, this);
		} catch (PersistenceException e) {
			throw new RuntimeException("Failed to load groups!", e);
		}
		List<Group> groupsInIpeer = groupDao.getGroupList(iPeerCourseId);
		
		// compare the groups
		for(Group group : groupsInBb) {
			Group groupInIpeer = iPeerB2Util.findGroupInList(group, groupsInIpeer);
			if (null == groupInIpeer) {
				// not found group in iPeer, so we create a new group
				Group newGroup = groupDao.createGroup(iPeerCourseId, group);
				List<User> users = null;
				try {
					users = B2Util.getUsersInGroup(group.getBbGroup(), this);
				} catch (PersistenceException e) {
					throw new RuntimeException("Failed to load group members!", e);
				} catch (ConnectionNotAvailableException e) {
					throw new RuntimeException("No connection is available!", e);
				}
				userDao.enrolUsersInGroup(newGroup.getId(), users);
			} else {
				// found group in iPeer, we check the group memberships
				List<User> usersInIpeer = userDao.getUsersInGroup(groupInIpeer.getId());
				List<User> usersInBb = null;
				try {
					usersInBb = B2Util.getUsersInGroup(group.getBbGroup(), this);
				} catch (PersistenceException e) {
					throw new RuntimeException("Failed to load group members!", e);
				} catch (ConnectionNotAvailableException e) {
					throw new RuntimeException("No connection is available!", e);
				}
				
				for (User userInIpeer : usersInIpeer) {
					User userInBb = iPeerB2Util.findUserInList(userInIpeer, usersInBb);
					// user not in bb group, so we remove it from ipeer
					if (null == userInBb) {
						userDao.removeUserFromGroup(groupInIpeer.getId(), userInBb);
					}
				}
				
				for (User userInBb : usersInBb) {
					User userInIpeer = iPeerB2Util.findUserInList(userInBb, usersInIpeer);
					// user not in ipeer group, so we enrol it in ipeer
					if (null == userInIpeer) {
						userDao.enrolUserInGroup(groupInIpeer.getId(), userInBb);
					}
				}
			}
		}
		
		return true;
	}

	@Override
	public boolean pullGroups(String bbCourseId) {
		int iPeerCourseId = iPeerB2Util.getIpeerCourseId(request);
		// we need groups from both
		List<Group> groupsInBb = null;
		try {
			groupsInBb = B2Util.getGroupsInCourse(bbCourseId, this);
		} catch (PersistenceException e) {
			throw new RuntimeException("Failed to load groups!", e);
		}
		List<Group> groupsInIpeer = groupDao.getGroupList(iPeerCourseId);
		
		// compare the groups
		for(Group group : groupsInIpeer) {
			Group groupInBb = iPeerB2Util.findGroupInList(group, groupsInBb);
			if (null == groupInBb) {
				// new group in BB, create one
				blackboard.data.course.Group bbGroup = null;
				try {
					bbGroup = B2Util.createGroup(bbCourseId, group, this);
				} catch (PersistenceException e) {
					throw new RuntimeException("Failed to create group in BB!", e);
				} catch (ValidationException e) {
					throw new RuntimeException("Invalid group to create! "+group, e);
				}
				groupInBb = bbGroupToGroup(bbGroup);
			}
			// get members from iPeer
			List<User> usersInIpeer = userDao.getUsersInGroup(group.getId());
			try {
				// enrol them into BB group, setUsersInGroup will handle the diff, 
				// e.g. only enrol the new ones and remove the missing ones.
				B2Util.setUsersInGroup(groupInBb.getBbGroup(), usersInIpeer, this);
				List<User> usersInBb = B2Util.getUsersInGroup(groupInBb.getBbGroup(), this);
				if (usersInIpeer.size() != usersInBb.size()) {
					for (User user : usersInIpeer) {
						if(null == iPeerB2Util.findUserInList(user, usersInBb)) {
							throw new RuntimeException("User " + user.getUsername() + " from iPeer is not enrolled in this course. Please enrol the student first!");
						}
					}
				}
			} catch (PersistenceException e) {
				throw new RuntimeException("Failed to enrol users into group!", e);
			} catch (ConnectionNotAvailableException e) {
				throw new RuntimeException("Connection is not available!", e);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean syncGrades(String bbCourseId) {
		List<Event> events = eventDao.getEventsInCourse(iPeerB2Util.getIpeerCourseId(request, bbCourseId));
		
		for(Event event : events) {
			List<Grade> grades = gradeDao.getGradesInEvent(event.getId());
			B2Util.setGradebook(bbCourseId, event.getTitle(), grades, this);
		}
		
		return true;
	}

	/************* Adapter functions *****************/
	
	@Override
	public User bbUserToUser(blackboard.data.user.User bbUser) {
		User user = new User();
		user.setUsername(bbUser.getUserName());
		user.setFirstName(bbUser.getGivenName());
		user.setLastName(bbUser.getFamilyName());
		user.setUsername(bbUser.getBatchUid());
		user.setStudentId(bbUser.getStudentId());
		user.setEmail(bbUser.getEmailAddress());
		return user;
	}

	@Override
	public blackboard.data.user.User userToBbUser(User user) {
		blackboard.data.user.User bbUser = new blackboard.data.user.User();
		bbUser.setUserName(user.getUsername());
		bbUser.setGivenName(user.getFirstName());
		bbUser.setFamilyName(user.getLastName());
		bbUser.setBatchUid(user.getUsername());
		bbUser.setStudentId(user.getStudentId());
		bbUser.setEmailAddress(user.getEmail());
		
		return bbUser;
	}

	//TODO: role mapping table for administrator
	@Override
	public User bbUserToUser(CourseMembership membership) {
		User user = bbUserToUser(membership.getUser());
		Map<String, Integer> roleMapping = new HashMap<String, Integer>();
		roleMapping.put(Role.STUDENT.getFieldName(), 3);
		roleMapping.put(Role.INSTRUCTOR.getFieldName(), 2);
		user.setRoleId(roleMapping.get(membership.getRoleAsString()));
		
		return user;
	}

	@Override
	public Group bbGroupToGroup(blackboard.data.course.Group bbGroup) {
		Group group = new Group();
		group.setName(bbGroup.getTitle());
		group.setBbGroup(bbGroup);
		
		return group;
	}

	@Override
	public blackboard.data.course.Group groupToBbGroup(Group group) {
		blackboard.data.course.Group bbGroup = new blackboard.data.course.Group();
		bbGroup.setTitle(group.getName());
		
		return bbGroup;
	}

	@Override
	public Score gradeToBbScore(Grade grade, List<CourseMembership> memberships) {
		Score score = new Score();
		for (CourseMembership membership : memberships) {
			if (membership.getUser().getUserName().equals(grade.getUsername())) {
				score.setCourseMembershipId(membership.getId());
			}
		}
		score.setDateAdded();
		score.setGrade(Double.toString(grade.getGrade()));
		
		return score;
	}
}
