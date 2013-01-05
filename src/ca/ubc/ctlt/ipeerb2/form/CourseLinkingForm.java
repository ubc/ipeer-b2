package ca.ubc.ctlt.ipeerb2.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class CourseLinkingForm {
	@NotNull
	@NumberFormat(style = Style.NUMBER)
    @Min(1)
	private int ipeerId;
	@NotNull
	private String bbCourseId;
	
	public int getIpeerId() {
		return ipeerId;
	}

	public void setIpeerId(int ipeerId) {
		this.ipeerId = ipeerId;
	}
	
	public String getBbCourseId() {
		return bbCourseId;
	}

	public void setBbCourseId(String bbCourseId) {
		this.bbCourseId = bbCourseId;
	}
}
