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
<c:url var="gotoipeer"  value="/instructor/course/gotoipeer?redirect=/courses/home/${ipeer_course_id}"/>
<c:url var="disconnect" value="/instructor/course/disconnect?course_id=${course_id}"/>
<c:url var="delete"     value="/instructor/course/delete?course_id=${course_id}"/>
<c:url var="bbcourseinfo" value="/instructor/course/bbcourseinfo?course_id=${course_id}"/>
<c:url var="ipeercourseinfo" value="/instructor/course/ipeercourseinfo?course_id=${course_id}"/>
	
<spring:message var="page_title" code="page.course_manage.title"/>
	
<style type="text/css">
div#menu {
	width: 15em;
	float: right;
}

.listmenu li a {
	display: block;
	height: 16px;
	padding: 0.8em 0 0.8em 1em;
	text-decoration: none;
}

.listmenu li a:hover {
	background-color: #666;
	color:#fff;
	text-decoration: none;
}

div#leftpanels {
	margin-right: 18em;
}

div#leftpanels div.panel-content {
	overflow: auto;
	border-top: 1px solid #CCC;
	padding: 6px 12px 12px;
	border-radius: 0 0 3px 3px;
	-webkit-border-radius: 0 0 3px 3px;
	-moz-border-radius: 0 0 3px 3px;
}

div#leftpanels div.panel-content li {
	padding: 0.5em 0 0.5em 3em;
}
</style>

<bbNG:learningSystemPage title="${page_title}" ctxId="ctx">
	<bbNG:pageHeader instructions="">
		<bbNG:breadcrumbBar>
			<bbNG:breadcrumb>${page_title}</bbNG:breadcrumb>
		</bbNG:breadcrumbBar>
		<bbNG:pageTitleBar>${page_title}</bbNG:pageTitleBar>
	</bbNG:pageHeader>

	<div id="menu" class="portlet clearfix">
		<h2>Actions</h2>
		<div style="overflow: auto;border-top: 1px solid #CCC;">
			<ul class="listmenu">
				<li><a href="${syncclass}">Synchronize Class List</a></li>
				<li><a href="${pushgroups}">Push Groups</a></li>
				<li><a href="${pullgroups}">Pull Groups</a></li>
				<li><a href="${syncgrades}">Synchronize Grades</a></li>
				<li><a href="${gotoipeer}" target="_blank">Manage Course in iPeer</a></li>
				<li><a href="${disconnect}">Disconnect Course</a></li>
				<li><a href="${delete}">Delete Course</a></li>
			</ul>
		</div>
	</div>
	
	<div id="leftpanels">
		<div id="bbcourse"  class="portlet">
			<h2>Current Course</h2>
			<div class="panel-content">
				<div id="div_1_1" class="eudModule">Please wait while the module loads...</div>
				<script type="text/javascript">
					Event.observe(window, 'load', function() { 
						new Ajax.Request('${bbcourseinfo}', {
							method: 'get', 
							parameters: '', 
							onSuccess: function(transport) {
								var content = '';
								try { 
									var res = transport.responseText.evalJSON();
									content = '<div class="eudModule-inner"><div class="portletBlock">Class Size: '+ res.classSize + '</div>'
									content += '<div class="portletBlock">Groups:<ul>';
									for (var i=0; i<res.groups.length; i++) {
										content += '<li>'+res.groups[i].group_name+'</li>';
									}
									content += '</ul></div>';
								} catch ( e ) { 
									content = 'Information is temporarily unavailable. Please reload the page. <!--' + e.toString().escapeHTML().gsub( '-', '&#045;' ) + '-->'; 
								}
								content += '<div class="portletInfoFooter">Last Updated: '+new Date()+'</div>';
								$('div_1_1').innerHTML = content;
							}, 
							onFailure: function(transport) {
								$('div_1_1').innerHTML = 'Error loading info.';
							}
						}); 
					});
				</script>
			</div>
		</div>
		
		<div id="ipeercourse"  class="portlet">
			<h2>iPeer Course</h2>
			<div class="panel-content">
				<div id="div_2_1" class="eudModule">Please wait while the module loads...</div>
				<script type="text/javascript">
					Event.observe(window, 'load', function() { 
						new Ajax.Request('${ipeercourseinfo}', {
							method: 'get', 
							parameters: '', 
							onSuccess: function(transport) {
								var content = '';
								try { 
									var res = transport.responseText.evalJSON();
									content = '<div class="eudModule-inner"><div class="portletBlock">Class Size: '+ res.classSize + '</div>'
									content += '<div class="portletBlock">Groups:<ul>';
									for (var i=0; i<res.groups.length; i++) {
										content += '<li>'+res.groups[i].group_name+'</li>';
									}
									content += '</ul></div>';
									content += '<div class="portletBlock">Events:<ul>';
									for (var i=0; i<res.events.length; i++) {
										content += '<li>'+res.events[i].title+' - Due on '+res.events[i].due_date+'</li>';
									}
									content += '</ul></div>';
								} catch ( e ) { 
									content = 'Information is temporarily unavailable. Please reload the page. <!--' + e.toString().escapeHTML().gsub( '-', '&#045;' ) + '-->'; 
								}
								content += '<div class="portletInfoFooter">Last Updated: '+new Date()+'</div>';
								$('div_2_1').innerHTML = content;
							}, 
							onFailure: function(transport) {
								$('div_2_1').innerHTML = 'Error loading info.';
							}
						}); 
					});
				</script>
			</div>
		</div>
	</div>
		
</bbNG:learningSystemPage>