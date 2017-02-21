package ca.ubc.ctlt.ipeerb2.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Grade {
	private int id;
	@JsonProperty("score")
	private double grade;
	
	@JsonProperty("event_id")
	private int eventId;
	
	@JsonProperty("event_title")
	private String eventTitle;
	
	@JsonProperty("evaluatee")
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

	@Override
	public String toString() {
		return "Grade [id=" + id + ", grade=" + grade + ", eventId=" + eventId
				+ ", eventTitle=" + eventTitle + ", userId=" + userId
				+ ", username=" + username + "]";
	}
}
