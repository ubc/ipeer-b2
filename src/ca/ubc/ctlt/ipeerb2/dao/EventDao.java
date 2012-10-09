package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Event;

public interface EventDao {
	public List<Event> getEventsInCourse(int courseId);
	public Event getEvent(int eventId);
	public List<Event> getEventsForUser(String username);
	public List<Event> getEventsForUserInCourse(String username, int courseId);
}
