package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Department;

public interface CourseDao {
	public List<Course> getCourseList();
	public Course getCourse(int id);
	public Course createCourse(Course course);
	public boolean deleteCourse(int id);
	public boolean updateCourse(Course course);
	public boolean assignCourseToDepartment(Course course, Department department);
	public boolean assignCourseToDepartment(int courseId, int departmentId);
}
