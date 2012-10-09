package ca.ubc.ctlt.ipeerb2;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import blackboard.platform.plugin.PlugInException;

@Component
public class Configuration implements ServletContextAware {
	public static final String COURSE_ID = "mapping.course_id.";
	public static final String IPEER_URL = "ipeer.url";
	
	private ServletContext servletContext;
	private boolean inMemory = false;
	
	public boolean isInMemory() {
		return inMemory;
	}

	public void setInMemory(boolean inMemory) {
		this.inMemory = inMemory;
	}

	public void setSettings(Properties settings) {
		servletContext.setAttribute("settings", settings);
	}
	
	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}
	
	public Properties getSettings() {
		Properties settings = (Properties)servletContext.getAttribute("settings");
		if (null == settings && !inMemory) {
			try {
				settings = BuildingBlockHelper.loadBuildingBlockSettings();
				servletContext.setAttribute("settings", settings);
			} catch (PlugInException e) {
				throw new RuntimeException("There is an error loading the settings!", e);
			} catch (IOException e) {
				throw new RuntimeException("Could not read setting files!", e);
			}
		} else if (null == settings) {
			settings = new Properties();
		}
		
		return settings;
	}
	
	public void saveSettings() {
		if (!inMemory) {
			try {
				BuildingBlockHelper.saveBuildingBlockSettings(getSettings());
			} catch (PlugInException e) {
				throw new RuntimeException("There is an error loading the settings!", e);
			} catch (IOException e) {
				throw new RuntimeException("Could not write setting files!", e);
			}
		}
	}
	
	public String getSetting(String key) {
		return getSettings().getProperty(key);
	}
	
	public void setSetting(String key, String value) {
		getSettings().setProperty(key, value);
		saveSettings();
	}
	
	public void deleteSetting(String key) {
		getSettings().remove(key);
		saveSettings();
	}

	public int getIpeerCourseId(String bbCourseId) {
		String idString = getSetting(COURSE_ID + bbCourseId);
		if (null == idString) {
			return -1;
		}
		
		return Integer.parseInt(idString);
	}
	
	public String getIpeerUrl() {
		return getSetting(IPEER_URL);
	}
	
	public boolean connectionExists(String bbCourseId) {
		return getIpeerCourseId(bbCourseId) > -1;
	}
	
	public void setConnection(String bbCourseId, int ipeerCourseId) {
		setSetting(COURSE_ID + bbCourseId, Integer.toString(ipeerCourseId));
	}
	
	public void deleteConnection(String bbCourseId) {
		deleteSetting(COURSE_ID + bbCourseId);
	}
}
