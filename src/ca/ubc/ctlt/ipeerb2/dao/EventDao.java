package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Event;

public interface EventDao {
	List<Event> getEventsInCourse(int courseId);
	Event getEvent(int eventId);
	List<Event> getEventsForUser(String username);
	List<Event> getEventsForUserInCourse(String username, int courseId);
}
