package ca.ubc.ctlt.ipeerb2.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class Group {
	private int id;
	private String name;
	
	@JsonProperty("set_name")
	private String setName;

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

}
