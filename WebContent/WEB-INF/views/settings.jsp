<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.spvsoftwareproducts.blackboard.utils.B2Context,
	ca.ubc.ctlt.ipeerb2.iPeerB2Util"
   errorPage="../error.jsp"%>
<%@taglib prefix="bbNG" uri="/bbNG"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
      	<bbNG:textElement name="<%=iPeerB2Util.IPEER_URL%>" value="${ipeerUrl}" isRequired="true" title="iPeer Server URL" size="50" helpText="e.g. https://ipeer.your.edu.ca"/>
      </bbNG:dataElement>
    </bbNG:step>
    <bbNG:stepSubmit hideNumber="false" showCancelButton="true" />
  </bbNG:dataCollection>
  </bbNG:form>
</bbNG:genericPage>