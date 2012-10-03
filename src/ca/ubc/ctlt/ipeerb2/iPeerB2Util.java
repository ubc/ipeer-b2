package ca.ubc.ctlt.ipeerb2;

import javax.servlet.http.HttpServletRequest;

import com.spvsoftwareproducts.blackboard.utils.B2Context;

public class iPeerB2Util {
	public static final String COURSE_ID = "mapping.course_id.";
	
	public static int getIpeerCourseId(HttpServletRequest request) {
		B2Context b2Context = new B2Context(request);
		String idString = b2Context.getSetting(COURSE_ID + b2Context.getContext().getCourseId().toExternalString());
		if ("".equals(idString)) {
			return -1;
		}
		
		return Integer.parseInt(idString);
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
}
