package ca.ubc.ctlt.ipeerb2.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Department;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {
	@Autowired
	private iPeerWebService webService;
	
	public void setWebService(iPeerWebService webService) {
		this.webService = webService;
	}
	
	@Override
	public List<Department> getDepartmentList() {
		List<Department> departmentList = webService.getDepartmentList();
		return departmentList;
	}

}
