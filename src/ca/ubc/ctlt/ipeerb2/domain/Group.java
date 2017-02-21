package ca.ubc.ctlt.ipeerb2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
	private int id;
	
	@JsonProperty("group_name")
	private String name;
	
	@JsonProperty("set_name")
	private String setName;
	
	@JsonProperty("member_count")
	private int size;
	
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@JsonIgnore
	public blackboard.data.course.Group getBbGroup() {
		return bbGroup;
	}

	@JsonIgnore
	public void setBbGroup(blackboard.data.course.Group bbGroup) {
		this.bbGroup = bbGroup;
	}
	
	@Override
	public String toString() {
		return "{id:" + this.getId() + ", name:" + this.getName() + ", set name: " + this.getSetName() + ", bbGroup: " + bbGroup + "}";
	}
}
