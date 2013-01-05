<%@page
	import="blackboard.portal.external.*, com.spvsoftwareproducts.blackboard.utils.B2Context"
	errorPage="/error.jsp"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<c:url var="getEvents" value="/module/getEvents" context="${webapp}"/>

<bbData:context>
	<div id="div_ipeer">Please wait while the module loads...</div>
	<script type="text/javascript">
		Event.observe(window, 'load', function() {
			new Ajax.Request('${getEvents}', {
				method : 'get',
				parameters : 'course_id=${course_id}',
				onSuccess : function(transport) {
					try {
						var res = transport.responseText;
						$('div_ipeer').innerHTML = res.stripScripts();
						page.globalEvalScripts(res, true);
					} catch (e) {
						$('div_ipeer').innerHTML = 
							'Module information is temporarily unavailable. Please reload the page. <!--' + 
							e.toString().escapeHTML().gsub('-', '&#045;') + '-->';
					}
				},
				onFailure : function(transport) {
					$('div_ipeer').innerHTML = 'Error loading module.';
				}
			});
		});
	</script>
</bbData:context>
