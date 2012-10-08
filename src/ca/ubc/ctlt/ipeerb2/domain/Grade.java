package ca.ubc.ctlt.ipeerb2.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Grade {
	private int id;
	private double grade;
	
	@JsonProperty("event_id")
	private int eventId;
	
	@JsonProperty("event_title")
	private String eventTitle;
	
	@JsonProperty("user_id")
	private int userId;
	
	private String username;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
