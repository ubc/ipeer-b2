<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <bbNG:learningSystemPage title="${page_title}" ctxId="ctx">
	<bbNG:pageHeader instructions="">
		<bbNG:breadcrumbBar>
			<bbNG:breadcrumb>iPeer</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<bbNG:pageTitleBar>${page_title}</bbNG:pageTitleBar>

	</bbNG:pageHeader>


	<form:form method="post" action="course/create" commandName="course">
	    <table>
	    <tr>
	        <td><form:label path="course">Course Name</form:label></td>
	        <td><form:input path="course" /></td> 
	    </tr>
	    <tr>
	        <td><form:label path="title">Course Title</form:label></td>
	        <td><form:input path="title" /></td>
	    </tr>
<%-- 	    <tr>
	        <td><form:label path="department">Department</form:label></td>
	        <td><form:input path="department" /></td>
	    </tr> --%>
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