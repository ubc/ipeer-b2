package ca.ubc.ctlt.ipeerb2.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.data.user.User;
import ca.ubc.ctlt.blackboardb2util.B2Util;
import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.iPeerB2Util;
import ca.ubc.ctlt.ipeerb2.domain.Event;
import ca.ubc.ctlt.ipeerb2.service.IPeerB2Service;

import com.spvsoftwareproducts.blackboard.utils.B2Context;

@Controller
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	private IPeerB2Service service;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private Configuration configuration;
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);
	
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, @RequestParam(value="course_id", defaultValue="") String bbCourseId, ModelMap model) {
//		try {
//			Module m = PortalUtil.getModule(request);
//			System.out.println(m);
//			CustomData cd = CustomData.getModuleData(request);
//			System.out.println(cd);
//		} catch (PersistenceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ValidationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/**
		 * The Filters are not executed when Blackboard calls a module action.
		 * Spring security filters don't get called. Thus the oauth request do
		 * not get token from token service. The workaround is generating an
		 * ajax request, let client browser to do the request (getEvents).
		 */
		if (!configuration.connectionExists(bbCourseId)) {
			
			return "module/unavailable";
		}
		
		B2Context b2Context = new B2Context(request);
		List<Event> events = null;
		if ("".equals(bbCourseId)) {
			events = service.getEventsForUser(B2Util.getCurrentUsername(request));
		} else {
			events = service.getEventsForUserInCourse(B2Util.getCurrentUsername(request), bbCourseId);
		}
		
		model.addAttribute("events", events);
		model.addAttribute("webapp", b2Context.getPath());
		
		return "module/view";
	}
	
	@RequestMapping(value="/gotoipeer", method = RequestMethod.GET)
	public String gotoIpeer(HttpServletRequest request, @RequestParam("redirect") String redirect, Locale locale, ModelMap model) {
		String url = configuration.getIpeerUrl();
		User user = B2Util.getCurrentUser(request);
		long timestamp = System.nanoTime();
		String key = configuration.getSetting(Configuration.TOKEN_KEY);
		String secret = configuration.getSetting(Configuration.TOKEN_SECRET);
		String signature = iPeerB2Util.calcSignature(user.getBatchUid(), timestamp, key, secret);
		return "redirect:"+url+redirect+"?"+
				"&username="+iPeerB2Util.urlEncode(user.getBatchUid())+
				"&timestamp="+timestamp+
				"&token="+iPeerB2Util.urlEncode(key)+
				"&signature="+iPeerB2Util.urlEncode(signature);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
		logger.error(ex.getMessage(), ex);
		return "error";
	}
}
