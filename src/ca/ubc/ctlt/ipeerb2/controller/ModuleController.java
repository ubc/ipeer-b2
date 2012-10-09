package ca.ubc.ctlt.ipeerb2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ubc.ctlt.blackboardb2util.B2Util;
import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.domain.Event;
import ca.ubc.ctlt.ipeerb2.service.IPeerB2Service;

@Controller
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	private IPeerB2Service service;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private Configuration configuration;
	
	@RequestMapping(value="/view")
	public String course(HttpServletRequest request, @RequestParam(value="course_id", defaultValue="") String bbCourseId, ModelMap model) {
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
		
		if (!configuration.connectionExists(bbCourseId)) {
			
			return "module/unavailable";
		}
		
		List<Event> events = service.getEventsForUserInCourse(B2Util.getCurrentUsername(request), bbCourseId);
		model.addAttribute("events", events);
		model.addAttribute("ipeer_url", configuration.getIpeerUrl());
		model.addAttribute("username", B2Util.getCurrentUsername(request));
		model.addAttribute("token", "");
		
		return "module/view";
	}
}
