package ca.ubc.ctlt.ipeerb2.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import ca.ubc.ctlt.ipeerb2.CustomDateDeserializer;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	private int id;
	
	private String title;
	
	@JsonProperty("course_id")
	private int courseId;
	
	private String description;
	
	@JsonProperty("self_eval")
	private boolean selfEval;
	
	@JsonProperty("due_date")
	private Date dueDate;
	
	@JsonProperty("release_date_begin")
	private Date releaseDateBegin;
	
	@JsonProperty("release_date_end")
	private Date releaseDateEnd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSelfEval() {
		return selfEval;
	}

	public void setSelfEval(boolean selfEval) {
		this.selfEval = selfEval;
	}

	public Date getDueDate() {
		return dueDate;
	}
	
	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getReleaseDateBegin() {
		return releaseDateBegin;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setReleaseDateBegin(Date releaseDateBegin) {
		this.releaseDateBegin = releaseDateBegin;
	}

	public Date getReleaseDateEnd() {
		return releaseDateEnd;
	}

	@JsonDeserialize(using = CustomDateDeserializer.class)
	public void setReleaseDateEnd(Date releaseDateEnd) {
		this.releaseDateEnd = releaseDateEnd;
	}
}
