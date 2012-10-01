package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Grade;

public interface GradeDao {
	public List<Grade> getGradesInEvent(int eventId);
	public Grade getGradeForUserInEvent(int userId, int eventId);
}
