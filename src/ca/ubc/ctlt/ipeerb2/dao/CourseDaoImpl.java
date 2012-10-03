package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Repository
public class CourseDaoImpl implements CourseDao {
	@Autowired
	private iPeerWebService webService;

	public void setWebService(iPeerWebService webService) {
		this.webService = webService;
	}

	@Override
	public List<Course> getCourseList() {
		List<Course> courseList = webService.getCourseList();
		return courseList;
	}

	@Override
	public Course getCourse(int id) {
		return webService.getCourse(id);
	}

	@Override
	public Course createCourse(Course course) {
		return webService.createCourse(course);
	}

	@Override
	public boolean deleteCourse(int id) {
		return webService.deleteCourse(id);
	}

	@Override
	public boolean updateCourse(Course course) {
		return webService.updateCourse(course);
	}
}
