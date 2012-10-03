package ca.ubc.ctlt.ipeerb2.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ubc.ctlt.ipeerb2.iPeerB2Util;
import ca.ubc.ctlt.ipeerb2.dao.CourseDao;
import ca.ubc.ctlt.ipeerb2.domain.Course;

@Service
public class IPeerB2ServiceImpl implements IPeerB2Service {
	@Autowired
	private CourseDao courseDao;
	
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
	
	
}
