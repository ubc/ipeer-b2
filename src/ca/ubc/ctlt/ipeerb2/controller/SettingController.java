package ca.ubc.ctlt.ipeerb2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.servlet.InlineReceiptUtil;
import ca.ubc.ctlt.ipeerb2.BuildingBlockHelper;
import ca.ubc.ctlt.ipeerb2.Configuration;
import ca.ubc.ctlt.ipeerb2.webservice.iPeerWebService;

@Controller
@RequestMapping("/settings")
public class SettingController {
	@Autowired
	private iPeerWebService webService;
	
	@Autowired
	private Configuration configuration;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		model.addAttribute("ipeerUrl", configuration.getSetting(Configuration.IPEER_URL));

		return "settings";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, @RequestParam(value=Configuration.IPEER_URL) String ipeerUrl) {		
		configuration.setSetting(Configuration.IPEER_URL, ipeerUrl);
		
		return "redirect:"+InlineReceiptUtil.addSuccessReceiptToUrl(
				BuildingBlockHelper.getServerUrl(request)+PlugInUtil.getPlugInManagerURL(), 
				"The settings have been saved successfully!");
	}
	
}