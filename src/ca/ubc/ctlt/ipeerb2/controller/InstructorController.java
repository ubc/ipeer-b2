package ca.ubc.ctlt.ipeerb2.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.data.ReceiptOptions;
import blackboard.platform.servlet.InlineReceiptUtil;
import ca.ubc.ctlt.ipeerb2.iPeerB2Util;
import ca.ubc.ctlt.ipeerb2.domain.Course;
import ca.ubc.ctlt.ipeerb2.service.IPeerB2Service;

@Controller
@RequestMapping("/instructor")
public class InstructorController {
	@Autowired
	private IPeerB2Service service;
	
	@Autowired
    private MessageSource messageSource;
	
	@RequestMapping(value="/course")
	public String course(HttpServletRequest webRequest, @RequestParam("course_id") String bbCourseId, ModelMap model) {
		if (iPeerB2Util.connectionExists(webRequest)) {
			model.addAttribute("course_id", bbCourseId);
			return "manage_course";
		}
		Course courseForm = new Course();
		courseForm.setBbCourseId(bbCourseId);
		model.addAttribute("course", courseForm);
		return "course_creation_form";
	}
	
	@RequestMapping(value="/course/create", method = RequestMethod.POST)
	public String createCourse(HttpServletRequest webRequest, @ModelAttribute("course") Course course, BindingResult result, Locale locale) {
		ReceiptOptions ro = new ReceiptOptions();
		if (service.createCourse(course)) {
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
}
