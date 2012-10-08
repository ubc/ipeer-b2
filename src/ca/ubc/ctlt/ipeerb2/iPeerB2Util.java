package ca.ubc.ctlt.ipeerb2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.ctlt.ipeerb2.domain.Group;
import ca.ubc.ctlt.ipeerb2.domain.User;

import com.spvsoftwareproducts.blackboard.utils.B2Context;

public class iPeerB2Util {
	public static final String COURSE_ID = "mapping.course_id.";
	public static final String IPEER_URL = "ipeer.url";
	
	public static int getIpeerCourseId(HttpServletRequest request) {
		return getIpeerCourseId(request, null);
	}
	
	public static int getIpeerCourseId(HttpServletRequest request, String bbCourseId) {
		B2Context b2Context = new B2Context(request);
		if (bbCourseId == null) {
			bbCourseId = b2Context.getContext().getCourseId().toExternalString();
		}
		String idString = b2Context.getSetting(COURSE_ID + bbCourseId);
		if ("".equals(idString)) {
			return -1;
		}
		
		return Integer.parseInt(idString);
	}
	
	public static String getIpeerUrl(HttpServletRequest request) {
		B2Context b2Context = new B2Context(request);
		return b2Context.getSetting(IPEER_URL);
	}
	
	public static boolean connectionExists(HttpServletRequest request) {
		return getIpeerCourseId(request) > -1;
	}
	
	public static void setConnection(HttpServletRequest request, int ipeerCourseId) {
		B2Context b2Context = new B2Context(request);
		b2Context.setSetting(COURSE_ID + b2Context.getContext().getCourseId().toExternalString(), Integer.toString(ipeerCourseId));

		// Saving it system wide. Course configuration doesn't work. Bugs in B2Context
		b2Context.persistSettings();
	}
	
	public static void deleteConnection(HttpServletRequest request, String bbCourseId) {
		B2Context b2Context = new B2Context(request);
		if (!bbCourseId.equals(b2Context.getContext().getCourseId().toExternalString())) {
			throw new RuntimeException("Course ID doesn't match the one in context!");
		}
		
		b2Context.setSetting(COURSE_ID + bbCourseId, "");

		// Saving it system wide. Course configuration doesn't work. Bugs in B2Context
		b2Context.persistSettings();
	}
	
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
