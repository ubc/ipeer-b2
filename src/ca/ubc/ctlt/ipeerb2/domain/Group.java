package ca.ubc.ctlt.ipeerb2.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class Group {
	private int id;
	private String name;
	
	@JsonProperty("set_name")
	private String setName;
	
	@JsonIgnore
	private blackboard.data.course.Group bbGroup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public blackboard.data.course.Group getBbGroup() {
		return bbGroup;
	}

	public void setBbGroup(blackboard.data.course.Group bbGroup) {
		this.bbGroup = bbGroup;
	}
	
	@Override
	public String toString() {
		return "{id:" + this.getId() + ", name:" + this.getName() + ", set name: " + this.getSetName() + ", bbGroup: " + bbGroup + "}";
	}
}
