<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:url var="syncclass"  value="/instructor/course/syncclass?course_id=${course_id}"/>
<c:url var="pushgroups" value="/instructor/course/pushgroups?course_id=${course_id}"/>
<c:url var="pullgroups" value="/instructor/course/pullgroups?course_id=${course_id}"/>
<c:url var="syncgrades" value="/instructor/course/syncgrades?course_id=${course_id}"/>
<c:url var="gotoipeer" value="/instructor/course/gotoipeer?course_id=${course_id}"/>
<c:url var="disconnect" value="/instructor/course/disconnect?course_id=${course_id}"/>
<c:url var="delete"     value="/instructor/course/delete?course_id=${course_id}"/>
	
<spring:message var="page_title" code="page.course_manage.title"/>
	
<bbNG:learningSystemPage title="${page_title}" ctxId="ctx">
	<bbNG:pageHeader instructions="">
		<bbNG:breadcrumbBar>
			<bbNG:breadcrumb>${page_title}</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<bbNG:pageTitleBar>${page_title}</bbNG:pageTitleBar>
	</bbNG:pageHeader>
	<ul>
		<li><a href="${syncclass}">Synchronize Class List</a></li>
		<li><a href="${pushgroups}">Push Groups</a></li>
		<li><a href="${pullgroups}">Pull Groups</a></li>
		<li><a href="${syncgrades}">Synchronize Grades</a></li>
		<li><a href="${gotoipeer}" target="_blank">Manage Course in iPeer</a></li>
		<li><a href="${disconnect}">Disconnect Course</a></li>
		<li><a href="${delete}">Delete Course</a></li>
	</ul>
</bbNG:learningSystemPage>