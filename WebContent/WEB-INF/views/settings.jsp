<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.spvsoftwareproducts.blackboard.utils.B2Context,
	ca.ubc.ctlt.ipeerb2.Configuration,
	ca.ubc.ctlt.ipeerb2.domain.Role"
   errorPage="../error.jsp"%>
<%@taglib prefix="bbNG" uri="/bbNG"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	pageContext.setAttribute("roleNames", Role.ALL_ROLE_NAMES);
%>
<bbNG:genericPage title="iPeer Building Block Settings" entitlement="system.admin.VIEW">
  <bbNG:pageHeader instructions="Instruction">
    <bbNG:breadcrumbBar environment="SYS_ADMIN_PANEL" navItem="admin_plugin_manage">
      <bbNG:breadcrumb title="iPeer Building Block Settings" />
    </bbNG:breadcrumbBar>
    <bbNG:pageTitleBar showTitleBar="true" title="iPeerB2 Settings"/>
  </bbNG:pageHeader>
  
  <bbNG:form action="save" id="id_simpleForm" name="simpleForm" method="post" onsubmit="return validateForm();">
  <bbNG:dataCollection markUnsavedChanges="true" showSubmitButtons="true">
    <bbNG:step hideNumber="false" id="stepOne" title="iPeer Server URL" instructions="">
      <bbNG:dataElement isRequired="true" label="iPeer Server URL">
      	<bbNG:textElement name="<%=Configuration.IPEER_URL%>" value="${ipeerUrl}" isRequired="true" title="iPeer Server URL" size="50" helpText="e.g. https://ipeer.your.edu.ca"/>
      </bbNG:dataElement>
    </bbNG:step>
    
    <bbNG:step hideNumber="false" id="stepTwo" title="Authentication" instructions="Setup integration authentication">
      
      <bbNG:dataElement isRequired="true" label="Client Credential Key">
      	<bbNG:textElement name="<%=Configuration.CONSUMER_KEY%>" value="${consumerKey}" isRequired="true" title="Consumer Key" size="50" helpText="The key used to identify the secret. This value must match the one set in iPeer"/>
      </bbNG:dataElement>
      
      <bbNG:dataElement isRequired="true" label="Client Credential Secret">
      	<bbNG:textElement name="<%=Configuration.SHARED_SECRET%>" value="${secret}" isRequired="true" title="Shared Secret" size="50" helpText="The secret used to sign the message. This value must match the one set in iPeer"/>
      </bbNG:dataElement>
      
      <bbNG:dataElement isRequired="true" label="Token Credential Key">
      	<bbNG:textElement name="<%=Configuration.TOKEN_KEY%>" value="${tokenKey}" isRequired="true" title="Token Key" size="50" helpText="The key used to identify the token secret. This value must match the one set in iPeer"/>
      </bbNG:dataElement>
      
      <bbNG:dataElement isRequired="true" label="Token Credential Secret">
      	<bbNG:textElement name="<%=Configuration.TOKEN_SECRET%>" value="${tokenSecret}" isRequired="true" title="Token Secret" size="50" helpText="The secret used to sign the message. This value must match the one set in iPeer"/>
      </bbNG:dataElement>
      
    </bbNG:step>
    
    <bbNG:step hideNumber="false" id="stepThree" title="Role Mapping" instructions="Please select appropriate role to map.">
		<bbNG:dataElement isRequired="true" label="Role Mapping">
	        <bbNG:settingsPageList collection="${bbRoles}" objectVar="bbRole" className="blackboard.platform.security.CourseRole"
	           description="" reorderable="false">
	        	<bbNG:listElement isRowHeader="true" label="Role Name" name="name">${bbRole.courseName}</bbNG:listElement>
	        	<c:forEach var="role" items="<%=Role.ROLE_NAMES_FOR_MAPPING %>">
		    	 	<bbNG:listElement isRowHeader="false" name="${roleNames[role]}" label="${roleNames[role]}">
		    	 		<c:set var="key" value="${bbRole.identifier}${role}" />
		    			<bbNG:checkboxElement isSelected="${roleMapping[key]}" name="role${bbRole.identifier}" value="${role}" />
		          	</bbNG:listElement>
	          	</c:forEach>
      	  </bbNG:settingsPageList>
      </bbNG:dataElement>
    
    </bbNG:step>
    
    <bbNG:step hideNumber="false" id="stepFour" title="Course Mapping" instructions="Viewing the Course Mappings">
    	<bbNG:inventoryList collection="${courseMapping}" objectVar="mapping" className="ca.ubc.ctlt.ipeerb2.MappingWrapper"
					initialSortCol="bbCourseId" includePageParameters="true">
			<bbNG:listElement label="BB Course" name="bbCourseId" isRowHeader="true">${mapping.key}</bbNG:listElement>
			<bbNG:listElement label="iPeer Course" name="ipeerCourseId">${mapping.value}</bbNG:listElement>
		</bbNG:inventoryList>	 	
    </bbNG:step>
    
    <bbNG:stepSubmit hideNumber="false" showCancelButton="true" />
  </bbNG:dataCollection>
  </bbNG:form>
  
</bbNG:genericPage>