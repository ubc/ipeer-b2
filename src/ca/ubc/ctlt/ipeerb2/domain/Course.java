package ca.ubc.ctlt.ipeerb2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import java.util.List;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
	private int id;
	private String course;
	private String title;
	
	private int classSize;

	@JsonIgnore
	private String bbCourseId;

	@JsonIgnore
	private List<Department> departments;
	
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

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	@JsonIgnore
	public int getClassSize() {
		return classSize;
	}

	@JsonProperty("student_count")
	public void setClassSize(int classSize) {
		this.classSize = classSize;
	}
}
