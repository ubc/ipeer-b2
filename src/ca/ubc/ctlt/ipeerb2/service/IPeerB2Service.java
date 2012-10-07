package ca.ubc.ctlt.ipeerb2.service;

import ca.ubc.ctlt.ipeerb2.domain.Course;

public interface IPeerB2Service {
	public boolean createCourse(Course course);
	public boolean disconnectCourse(String bbCourseId);
	public boolean deleteCourse(String bbCourseId);
	public boolean syncClass(String bbCourseId);
	public boolean pushGroups(String bbCourseId);
	public boolean pullGroups(String bbCourseId);
	public boolean syncGrades(String bbCourseId);
}
