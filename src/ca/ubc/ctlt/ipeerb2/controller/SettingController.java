package ca.ubc.ctlt.ipeerb2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.security.CourseRole;
import blackboard.platform.servlet.InlineReceiptUtil;
import ca.ubc.ctlt.blackboardb2util.B2Util;
import ca.ubc.ctlt.ipeerb2.BuildingBlockHelper;
import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.iPeerB2Util;
import ca.ubc.ctlt.ipeerb2.domain.Role;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Controller
@RequestMapping("/settings")
@Secured("BB_SYSTEM_ADMIN_ROLE")
public class SettingController {
	@Autowired
	private iPeerWebService webService;
	
	@Autowired
	private Configuration configuration;
	
	private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ipeerUrl", configuration.getSetting(Configuration.IPEER_URL));
		model.addAttribute("consumerKey", configuration.getSetting(Configuration.CONSUMER_KEY));
		model.addAttribute("secret", configuration.getSetting(Configuration.SHARED_SECRET));
		model.addAttribute("tokenKey", configuration.getSetting(Configuration.TOKEN_KEY));
		model.addAttribute("tokenSecret", configuration.getSetting(Configuration.TOKEN_SECRET));
		model.addAttribute("courseMapping", configuration.getCourseMappings());
		
		// load roles and role mappings
		model.addAttribute("roleMapping", iPeerB2Util.getRoleMapping(configuration));
		model.addAttribute("bbRoles", B2Util.getCourseRoles());
		
		return "settings";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, 
			@RequestParam(value=Configuration.IPEER_URL) String ipeerUrl, 
			@RequestParam(Configuration.CONSUMER_KEY) String consumerKey, 
			@RequestParam(Configuration.SHARED_SECRET) String secret,
			@RequestParam(Configuration.TOKEN_KEY) String tokenKey, 
			@RequestParam(Configuration.TOKEN_SECRET) String tokenSecret
			) {		
		configuration.setSetting(Configuration.IPEER_URL, ipeerUrl);
		configuration.setSetting(Configuration.CONSUMER_KEY, consumerKey);
		configuration.setSetting(Configuration.SHARED_SECRET, secret);
		configuration.setSetting(Configuration.TOKEN_KEY, tokenKey);
		configuration.setSetting(Configuration.TOKEN_SECRET, tokenSecret);
		
		// process roles
		List<CourseRole> roles = B2Util.getCourseRoles();
		for(CourseRole role : roles) {
			String[] values = request.getParameterValues(Role.PREIX + role.getIdentifier());
			configuration.setSetting(Configuration.ROLE_MAPPING_PREFIX + "." + role.getIdentifier(), StringUtils.arrayToCommaDelimitedString(values));
		}
		
		return "redirect:"+InlineReceiptUtil.addSuccessReceiptToUrl(
				BuildingBlockHelper.getServerUrl(request)+PlugInUtil.getPlugInManagerURL(), 
				"The settings have been saved successfully!");
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
		logger.error(ex.getMessage(), ex);
		return "error";
	}
}