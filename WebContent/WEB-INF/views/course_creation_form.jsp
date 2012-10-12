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
	<bbNG:pageHeader instructions="Creating a iPeer course">
		<bbNG:breadcrumbBar>
			<bbNG:breadcrumb>${page_title}</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<bbNG:pageTitleBar>${page_title}</bbNG:pageTitleBar>
	</bbNG:pageHeader>


	<form:form method="post" action="course/create" commandName="course">
	    <table>
	    <tr>
	        <td><form:label path="course"><spring:message code="label.course_name"/></form:label></td>
	        <td><form:input path="course" size="50"/></td> 
	    </tr>
	    <tr>
	        <td><form:label path="title"><spring:message code="label.course_title"/></form:label></td>
	        <td><form:input path="title" size="50"/></td>
	    </tr>
 	    <tr>
	        <td><form:label path="departments"><spring:message code="label.department"/></form:label></td>
	        <td><form:checkboxes items="${departments}" path="departments" itemLabel="name" itemValue="id" delimiter="&nbsp;&nbsp;&nbsp;"/></td>
	    </tr>
	    <tr>
	        <td colspan="2">
	        	<form:hidden path="bbCourseId" />
	        	<input type="hidden" name="course_id" value="${course.bbCourseId}" />
	            <input type="submit" value="Create Course"/>
	        </td>
	    </tr>
		</table>  
	     
	</form:form>
</bbNG:learningSystemPage>