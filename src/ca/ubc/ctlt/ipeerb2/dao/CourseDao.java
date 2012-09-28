package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Course;

public interface CourseDao {
	public List<Course> getCourseList();
	public Course getCourse(int id);
	public Course createCourse(Course course);
	public boolean deleteCourse(int id);
	public boolean updateCourse(Course course);
}
