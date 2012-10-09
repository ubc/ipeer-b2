package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.ubc.ctlt.ipeerb2.domain.Event;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Repository
public class EventDaoImpl implements EventDao {
	@Autowired
	private iPeerWebService webService;

	public void setWebService(iPeerWebService webService) {
		this.webService = webService;
	}
	
	@Override
	public List<Event> getEventsInCourse(int courseId) {
		return webService.getEventsInCourse(courseId);
	}

	@Override
	public Event getEvent(int eventId) {
		return webService.getEvent(eventId);
	}

	@Override
	public List<Event> getEventsForUser(String username) {
		return webService.getEventsForUser(username);
	}

	@Override
	public List<Event> getEventsForUserInCourse(String username, int courseId) {
		return webService.getEventsForUserInCourse(username, courseId);
	}

}
