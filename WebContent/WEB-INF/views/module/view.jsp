<%@page import="blackboard.portal.external.*, com.spvsoftwareproducts.blackboard.utils.B2Context" errorPage="/error.jsp"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url var="gotoipeer" value="/module/gotoipeer?redirect=/home/index" context="${webapp}"/>

<bbData:context>
<c:choose>
	<c:when test="${empty events}">
	    No available events
	</c:when>
	
	<c:otherwise>
	<ul>
	    <c:forEach var="event" items="${events}">
	    	<li><a href="${gotoipeer}" target="_blank"><c:out value="${event.title}"/></a> due at <c:out value="${event.dueDate}"/></li>
	    </c:forEach>
	</ul>
	</c:otherwise>
</c:choose>
</bbData:context>