package ca.ubc.ctlt.ipeerb2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.servlet.InlineReceiptUtil;
import ca.ubc.ctlt.ipeerb2.iPeerB2Util;

import com.spvsoftwareproducts.blackboard.utils.B2Context;

@Controller
@RequestMapping("/settings")
public class SettingController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		B2Context b2Context = new B2Context(request);

		model.addAttribute("bundle", b2Context.getResourceStrings());
		model.addAttribute("ipeerUrl", b2Context.getSetting(iPeerB2Util.IPEER_URL));

		return "settings";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest webRequest, HttpServletResponse response, ModelMap model) {
		B2Context b2Context = new B2Context(webRequest);
	
		String ipeer_url = b2Context.getRequestParameter(iPeerB2Util.IPEER_URL, "").trim();
		b2Context.setSetting(iPeerB2Util.IPEER_URL, ipeer_url);

		b2Context.persistSettings();

		return "redirect:"+InlineReceiptUtil.addSuccessReceiptToUrl(
				b2Context.getServerUrl()+PlugInUtil.getPlugInManagerURL(), 
				"The settings have been saved successfully!");
	}
	
}