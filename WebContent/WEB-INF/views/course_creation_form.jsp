<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:message var="page_title" code="page.course_create.title"/>

 <bbNG:learningSystemPage title="${page_title}" ctxId="ctx">
	<bbNG:pageHeader instructions="Creating a iPeer course or linking a exiting one">
		<bbNG:breadcrumbBar>
			<bbNG:breadcrumb>${page_title}</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<bbNG:pageTitleBar>${page_title}</bbNG:pageTitleBar>
	</bbNG:pageHeader>

	<h3>Creating Course in iPeer</h3>
	<p class="helphelp">This feature creates a new course in iPeer automatically using the given information. 
	Selecting correct Faculty/Department will allow iPeer faculty admins to access your course when you need help.</p>
	<br/>
	<form:form method="post" action="course" commandName="courseCreate">
	    <table>
	    <tr>
	        <td><form:label path="course"><spring:message code="label.course_name"/></form:label></td>
	        <td><form:input path="course" size="50"/> <font color="red"><form:errors path="course" /></font></td> 
	    </tr>
	    <tr>
	        <td><form:label path="title"><spring:message code="label.course_title"/></form:label></td>
	        <td><form:input path="title" size="50"/> <font color="red"><form:errors path="title" /></font></td>
	    </tr>
	    <tr>
	        <td><form:label path="syncClass"><spring:message code="label.sync_class"/></form:label></td>
	        <td><form:checkbox path="syncClass" value="selected"/></td>
	    </tr>
	    <tr>
	        <td><form:label path="pushGroup"><spring:message code="label.push_group"/></form:label></td>
	        <td><form:checkbox path="pushGroup" /></td>
	    </tr>
	    <tr>
	        <td><form:label path="pullGroup"><spring:message code="label.pull_group"/></form:label></td>
	        <td><form:checkbox path="pullGroup" /></td>
	    </tr>
 	    <tr>
	        <td style="vertical-align:top;"><form:label path="departments"><spring:message code="label.department"/></form:label></td>
	        <td><form:checkboxes items="${departments}" path="departments" itemLabel="name" itemValue="id" delimiter="<br/>"/></td>
	    </tr>
	    <tr>
	        <td colspan="2">
	        	<form:hidden path="bbCourseId" />
	        	<input type="hidden" name="course_id" value="${courseCreate.bbCourseId}" />
	            <input type="submit" name="create" value="Create Course"/>
	        </td>
	    </tr>
		</table>  
	     
	</form:form>
	<br />
	
	<hr />
	
	<br />
	<h3>Linking This Course to Existing iPeer Course</h3>
	<p class="helphelp">This feature can be used when you already have an iPeer course and want to link this course to the existing iPeer course.<br />
	Caution: By linking the wrong course, there maybe unexpected result. Please make sure you are using the correct iPeer Course ID.
	(<a href="http://ipeer.ctlt.ubc.ca/trac/wiki/HowToFindCourseId" target="_blank">How to find iPeer Course ID?</a>)
	</p>
	<br />
	<form:form method="post" action="course" commandName="courseLink">
	    <table>
	    <tr>
	        <td><form:label path="ipeerId"><spring:message code="label.ipeer_course_id"/></form:label></td>
	        <td><form:input path="ipeerId" size="50"/> <font color="red"><form:errors path="ipeerId" /></font></td> 
	    </tr>
	    <tr>
	        <td colspan="2">
	        	<form:hidden path="bbCourseId" />
	        	<input type="hidden" name="course_id" value="${courseLink.bbCourseId}" />
	            <input type="submit" name="link" value="Link Course"/>
	        </td>
	    </tr>
		</table>  
	     
	</form:form>
</bbNG:learningSystemPage>