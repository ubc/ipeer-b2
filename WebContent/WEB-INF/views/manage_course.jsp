<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="ca.ubc.ctlt.ipeerb2.*, ca.ubc.ctlt.ipeerb2.webservice.*"
    %>

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<c:url var="syncclass"  value="/instructor/course/syncclass?course_id=${course_id}"/>
<c:url var="pushgroups" value="/instructor/course/pushgroups?course_id=${course_id}"/>
<c:url var="pullgroups" value="/instructor/course/pullgroups?course_id=${course_id}"/>
<c:url var="syncgrades" value="/instructor/course/syncgrades?course_id=${course_id}"/>
<c:url var="gotoipeer"  value="/instructor/course/gotoipeer?course_id=${course_id}&redirect=/courses/home/${ipeer_course_id}"/>
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

#tinyfootnote
{
	margin-top: 5em;
	font-size: 0.7em;
	text-align: center;
	color: #aaa;
}
#tinyfootnote a:link, #tinyfootnote a:visited
{
	color: #888;
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
				<li><a href="${syncclass}"><spring:message code="label.sync_class"/></a></li>
				<li><a href="${pushgroups}"><spring:message code="label.push_group"/></a></li>
				<li><a href="${pullgroups}"><spring:message code="label.pull_group"/></a></li>
				<li><a href="${syncgrades}"><spring:message code="label.pull_grade"/></a></li>
				<li><a href="${gotoipeer}" target="_blank"><spring:message code="label.manage_course"/></a></li>
				<li><a href="${disconnect}"><spring:message code="label.disconnect_course"/></a></li>
				
				<sec:authorize url="/instructor/course/delete">
				<!-- only ISS admin or sys admin can delete course remotely -->
				<li><a href="${delete}"><spring:message code="label.delete_course"/></a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>
	
	<div id="leftpanels">
		<div id="bbcourse"  class="portlet">
			<h2>Current (Blackboard) Course</h2>
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
										content += '<li>'+res.groups[i].group_name+' ('+res.groups[i].member_count+' active members)</li>';
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
										content += '<li>'+res.groups[i].group_name+' ('+res.groups[i].member_count+' active members)</li>';
									}
									content += '</ul></div>';
									content += '<div class="portletBlock">Events:<ul>';
									for (var i=0; i<res.events.length; i++) {
										content += '<li>'+res.events[i].title+' - Due on '+new Date(res.events[i].due_date)+'</li>';
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
	<p id="tinyfootnote">
		Version: <%=BuildingBlockHelper.getVersion()%> - API <%=iPeerWebService.API_VERSION%> - <a href="https://github.com/ubc/ipeer-b2/issues" target="_blank">Bug or Suggestion?</a><br />
		Open source project, available on <a href="https://github.com/ubc/ipeer-b2" target="_blank">Github</a>
	</p>
</bbNG:learningSystemPage>