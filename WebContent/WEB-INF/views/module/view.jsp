<%@page import="blackboard.portal.external.*" errorPage="/error.jsp"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<bbData:context>
<c:choose>
	<c:when test="${empty events}">
	    No available events
	</c:when>
	
	<c:otherwise>
	<ul>
	    <c:forEach var="event" items="${events}">
	    	<li><a href="${ipeer_url}/event/${event.id}?username=${username}&token=${token}"><c:out value="${event.title}"/></a> due at <c:out value="${event.dueDate}"/></li>
	    </c:forEach>
	</ul>
	</c:otherwise>
</c:choose>
</bbData:context>
