package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ca.ubc.ctlt.ipeerb2.domain.Grade;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Repository
public class GradeDaoImpl implements GradeDao {
	private iPeerWebService webService;

	public void setWebService(iPeerWebService webService) {
		this.webService = webService;
	}
	
	@Override
	public List<Grade> getGradesInEvent(int eventId) {
		return webService.getGradesInEvent(eventId);
	}

	@Override
	public Grade getGradeForUserInEvent(int userId, int eventId) {
		return webService.getGradesForUserInEvent(userId, eventId);
	}

}
