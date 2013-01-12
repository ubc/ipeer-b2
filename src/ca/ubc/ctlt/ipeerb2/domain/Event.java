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
	public static final int TYPE_SIMPLE = 1;
	public static final int TYPE_RUBRIC = 2;
	public static final int TYPE_SURVEY = 3;
	public static final int TYPE_MIXEVAL = 4;
	
	private int id;
	
	private String title;
	
	@JsonProperty("course_id")
	private int courseId;
	
	private String description;
	
	@JsonProperty("event_template_type_id")
	private int templateType;
	
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

	public int getTemplateType() {
		return templateType;
	}

	public void setTemplateType(int templateType) {
		this.templateType = templateType;
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

	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", courseId="
				+ courseId + ", description=" + description + ", templateType="
				+ templateType + ", selfEval=" + selfEval + ", dueDate="
				+ dueDate + ", releaseDateBegin=" + releaseDateBegin
				+ ", releaseDateEnd=" + releaseDateEnd + "]";
	}
}
