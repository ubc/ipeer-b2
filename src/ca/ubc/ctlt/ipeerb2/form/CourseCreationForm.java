package ca.ubc.ctlt.ipeerb2.form;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Department;

public class CourseCreationForm {
	private String name;
	private String title;
	private List<Department> departments;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}
