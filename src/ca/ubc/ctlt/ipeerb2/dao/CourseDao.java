package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Department;

public interface CourseDao {
	List<Course> getCourseList();
	Course getCourse(int id);
	Course createCourse(Course course);
	boolean deleteCourse(int id);
	boolean updateCourse(Course course);
	boolean assignCourseToDepartment(Course course, Department department);
	boolean assignCourseToDepartment(int courseId, int departmentId);
	int getClassSize(int ipeerCourseId);
}
