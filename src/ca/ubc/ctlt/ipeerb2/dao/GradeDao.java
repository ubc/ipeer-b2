package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Grade;

public interface GradeDao {
	List<Grade> getGradesInEvent(int eventId);
	Grade getGradeForUserInEvent(int userId, int eventId);
}
