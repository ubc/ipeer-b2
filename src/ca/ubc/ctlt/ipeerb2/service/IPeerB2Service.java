package ca.ubc.ctlt.ipeerb2.service;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Department;
import ca.ubc.ctlt.ipeerb2.domain.Event;

public interface IPeerB2Service {
	public boolean createCourse(Course course);
	public boolean disconnectCourse(String bbCourseId);
	public boolean deleteCourse(String bbCourseId);
	public boolean syncClass(String bbCourseId);
	public boolean pushGroups(String bbCourseId);
	public boolean pullGroups(String bbCourseId);
	public boolean syncGrades(String bbCourseId);
	public List<Event> getEventsForUserInCourse(String username, String bbCourseId);
	public List<Department> getDepartments();
	public boolean assignCourseToDepartment(int courseId, int departmentId);
}
