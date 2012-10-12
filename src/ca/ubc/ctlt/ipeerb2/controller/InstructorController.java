package ca.ubc.ctlt.ipeerb2.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.data.ReceiptOptions;
import blackboard.data.user.User;
import blackboard.platform.servlet.InlineReceiptUtil;
import ca.ubc.ctlt.blackboardb2util.B2Util;
import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.domain.Department;
import ca.ubc.ctlt.ipeerb2.service.IPeerB2Service;

import com.spvsoftwareproducts.blackboard.utils.B2Context;

@Controller
@RequestMapping("/instructor")
public class InstructorController {
	@Autowired
	private IPeerB2Service service;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private Configuration configuration;
	
	@RequestMapping(value="/course")
	public String course(HttpServletRequest request, @RequestParam("course_id") String bbCourseId, ModelMap model) {
		if (configuration.connectionExists(bbCourseId)) {
			model.addAttribute("course_id", bbCourseId);
			return "manage_course";
		}
		B2Context b2Context = new B2Context(request);
		blackboard.data.course.Course course = b2Context.getContext().getCourse();
		
		List<Department> departments = service.getDepartments();
		model.addAttribute("departments", departments);
		
		Course courseForm = new Course();
		courseForm.setBbCourseId(bbCourseId);
		courseForm.setCourse(course.getBatchUid());
		courseForm.setTitle(course.getDisplayTitle());
		model.addAttribute("course", courseForm);
		
		return "course_creation_form";
	}
	
	@RequestMapping(value="/course/create", method = RequestMethod.POST)
	public String createCourse(HttpServletRequest webRequest, @ModelAttribute("course") Course course, BindingResult result, Locale locale) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.createCourse(course)) {
			for (Department dept : course.getDepartments()) {
				service.assignCourseToDepartment(course.getId(), dept.getId());
			}
			ro.addSuccessMessage(messageSource.getMessage("message.create_course_success", new Object[]{course.getCourse()}, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.create_course_failed", new Object[]{course.getCourse()}, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		
		return "redirect:/instructor/course?course_id="+course.getBbCourseId();
	}
	
	@RequestMapping(value="/course/disconnect", method = RequestMethod.GET)
	public String disconnectCourse(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.disconnectCourse(bbCourseId)) {
			ro.addSuccessMessage(messageSource.getMessage("message.disconnect_course_success", null, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.disconnect_course_failed", null, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		model.addAttribute("course_id", bbCourseId);
		
		return "redirect:/instructor/course";
	}
	
	@RequestMapping(value="/course/delete", method = RequestMethod.GET)
	public String deleteCourse(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.deleteCourse(bbCourseId)) {
			ro.addSuccessMessage(messageSource.getMessage("message.delete_course_success", null, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.delete_course_failed", null, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		model.addAttribute("course_id", bbCourseId);
		
		return "redirect:/instructor/course";
	}
	
	
	@RequestMapping(value="/course/syncclass", method = RequestMethod.GET)
	public String syncClass(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.syncClass(bbCourseId)) {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_class_success", null, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_class_failed", null, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		model.addAttribute("course_id", bbCourseId);
		
		return "redirect:/instructor/course";
	}
	
	@RequestMapping(value="/course/pushgroups", method = RequestMethod.GET)
	public String pushGroups(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.pushGroups(bbCourseId)) {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_groups_success", null, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_groups_failed", null, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		model.addAttribute("course_id", bbCourseId);
		
		return "redirect:/instructor/course";
	}
	
	@RequestMapping(value="/course/pullgroups", method = RequestMethod.GET)
	public String pullGroups(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.pullGroups(bbCourseId)) {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_groups_success", null, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_groups_failed", null, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		model.addAttribute("course_id", bbCourseId);
		
		return "redirect:/instructor/course";
	}
	
	@RequestMapping(value="/course/syncgrades", method = RequestMethod.GET)
	public String syncGrades(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.syncGrades(bbCourseId)) {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_grades_success", null, locale));
		} else {
			ro.addSuccessMessage(messageSource.getMessage("message.sync_grades_failed", null, locale));
		}
		InlineReceiptUtil.addReceiptToRequest(webRequest, ro);
		model.addAttribute("course_id", bbCourseId);
		
		return "redirect:/instructor/course";
	}
	
	@RequestMapping(value="/course/gotoipeer", method = RequestMethod.GET)
	public String gotoIpeer(HttpServletRequest request, @RequestParam("course_id") String bbCourseId, Locale locale, ModelMap model) {
		int ipeerCourseId = configuration.getIpeerCourseId(bbCourseId);
		String url = configuration.getIpeerUrl();
		User user = B2Util.getCurrentUser(request);
		
		return "redirect:"+url+"/login?course_id="+ipeerCourseId+"&username="+user.getUserName();
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Department.class, new CourseDepartmentPropertyEditor());
	}
}
