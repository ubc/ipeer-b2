package ca.ubc.ctlt.ipeerb2.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Course {
	private int id;
	private String course;
	private String title;
	
	@JsonIgnore
	private String bbCourseId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBbCourseId() {
		return bbCourseId;
	}

	public void setBbCourseId(String bbCourseId) {
		this.bbCourseId = bbCourseId;
	}
}
