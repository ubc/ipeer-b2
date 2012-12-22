package ca.ubc.ctlt.ipeerb2.form;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.Department;

public class CourseCreationForm {
	private String course;
	private String title;
	private String bbCourseId;
	private boolean syncClass = true;
	private boolean pushGroup;
	private boolean pullGroup;
	private List<Department> departments;

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

	public boolean isSyncClass() {
		return syncClass;
	}

	public void setSyncClass(boolean syncClass) {
		this.syncClass = syncClass;
	}

	public boolean isPushGroup() {
		return pushGroup;
	}

	public void setPushGroup(boolean syncGroup) {
		this.pushGroup = syncGroup;
	}

	public boolean isPullGroup() {
		return pullGroup;
	}

	public void setPullGroup(boolean pullGroup) {
		this.pullGroup = pullGroup;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}
