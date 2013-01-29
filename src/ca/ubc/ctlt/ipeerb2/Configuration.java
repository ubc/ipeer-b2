package ca.ubc.ctlt.ipeerb2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import blackboard.platform.plugin.PlugInException;

public class Configuration {
	public static final String COURSE_ID = "mapping.course_id.";
	public static final String IPEER_URL = "ipeer.url";
	public static final String CONSUMER_KEY = "ipeer.consumer_key";
	public static final String SHARED_SECRET = "ipeer.consumer_secret";
	public static final String TOKEN_KEY = "ipeer.token_key";
	public static final String TOKEN_SECRET = "ipeer.token_secret";
	public static final String ROLE_MAPPING_PREFIX = "ipeer.role_mapping";

	public Properties getSettings() {
		Properties settings = null;
		try {
			settings = BuildingBlockHelper.loadBuildingBlockSettings();
		} catch (PlugInException e) {
			throw new RuntimeException(
					"There is an error loading the settings!", e);
		} catch (IOException e) {
			throw new RuntimeException("Could not read setting files!", e);
		}
		
		return settings;
	}
	
	public void saveSettings(Properties settings) {
		try {
			BuildingBlockHelper.saveBuildingBlockSettings(settings);
		} catch (PlugInException e) {
			throw new RuntimeException(
					"There is an error loading the settings!", e);
		} catch (IOException e) {
			throw new RuntimeException("Could not write setting files!", e);
		}
	}
	
	public String getSetting(String key) {
		return getSetting(key, null);
	}
	
	public String getSetting(String key, String defaultValue) {
		return getSettings().getProperty(key, defaultValue);
	}
	
	public void setSetting(String key, String value) {
		Properties settings = getSettings();
		settings.setProperty(key, value);
		saveSettings(settings);
	}
	
	public void deleteSetting(String key) {
		Properties settings = getSettings();
		settings.remove(key);
		saveSettings(settings);
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
		if (bbCourseId == null || "".equals(bbCourseId)) {
			throw new RuntimeException("Invalid bbCourseId! Please refresh the page and try again.");
		}
		setSetting(COURSE_ID + bbCourseId, Integer.toString(ipeerCourseId));
	}
	
	public void deleteConnection(String bbCourseId) {
		deleteSetting(COURSE_ID + bbCourseId);
	}
	
	public List<MappingWrapper> getCourseMappings() {
		List<MappingWrapper> mapping = new ArrayList<MappingWrapper>();
		Properties settings = getSettings();
		for (String key : settings.stringPropertyNames()) {
			if (!key.startsWith(COURSE_ID)) {
				continue;
			}
			String value = settings.getProperty(key);
			mapping.add(new MappingWrapper(key.substring(COURSE_ID.length()), value));
		}
		
		return mapping;
	}
}
