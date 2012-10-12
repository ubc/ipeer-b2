package ca.ubc.ctlt.ipeerb2.controller;

import java.beans.PropertyEditorSupport;

import ca.ubc.ctlt.ipeerb2.domain.Department;

public class CourseDepartmentPropertyEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Integer id = new Integer(text);
		Department department = new Department();
		department.setId(id);
		super.setValue(department);
	}
}
