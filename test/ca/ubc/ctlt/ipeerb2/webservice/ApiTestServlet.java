package ca.ubc.ctlt.ipeerb2.webservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;

public class ApiTestServlet extends HttpServlet {

	private static final long serialVersionUID = 5612878886223011290L;
	private MediaType contentType;
	private Properties properties;
	private List<TestServletConfig> servletConfigs;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
		servletConfigs = new ArrayList<TestServletConfig>();
		int i = 1;
		while(properties.getProperty("test."+i+".uri") != null) {
			
			TestServletConfig config = new TestServletConfig();
			for(String prop : TestServletConfig.PROPERTIES) {
				String value = properties.getProperty("test."+i+"."+prop.toLowerCase());
				if (value == null) {
					continue;
				}
				try {
					Method method = config.getClass().getMethod("set"+prop, String.class);
					method.invoke(config, value);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			servletConfigs.add(config);
			i++;
		}
	}

	public MediaType getContentType() {
		return contentType;
	}

	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonNode received = null;
		String body = FileCopyUtils.copyToString(request.getReader());
		if (!"".equals(body)) {
			ObjectMapper m = new ObjectMapper();
			try {
				received = m.readValue(body, JsonNode.class);
			} catch (JsonParseException e) {
				throw new ServletException("Failed parsing request body " + body, e);
			} catch (JsonMappingException e) {
				throw new ServletException("Failed mapping request body " + body, e);
			} catch (IOException e) {
				throw new ServletException("Failed reading request body " + body, e);
			}
		}

		for(TestServletConfig config : servletConfigs) {
			// check the uri patten, request method and body
			if (request.getRequestURI().matches(config.getUri()) && 
					request.getMethod().equals(config.getMethod().toUpperCase()) &&
					(received == null || received.equals(config.getRequestBody()))
					) {
				// set the status code before any output!
				if(config.getResponseStatus() != null) {
					response.setStatus(Integer.parseInt(config.getResponseStatus()));
				}
				
				if (config.getRequestBody() != null) {
					// expecting something in request body
					assertTrue("Invalid request content-length", request.getContentLength() > 0);
					assertNotNull("No content-type", request.getContentType());
				}
				
				if (config.getResponseBody() != null) {
					response.setContentLength(config.getResponseBody().length());
					response.setContentType(contentType.toString());
					FileCopyUtils.copy(
							config.getResponseBody().getBytes("UTF-8"),
							response.getOutputStream());
				}
				
				return;
			}
		}
		
		// something wrong, unexpected request.
		throw new ServletException("No valid handler found! Check the fixtures. Request: " + request.getMethod() + " " + request.getRequestURI() + " Body: "+body);
	}
	
	public static class TestServletConfig {
		public static final String[] PROPERTIES = new String[] {"Uri", "Method", "RequestBody", "ResponseBody", "ResponseStatus"};
		private String uri;
		private String method;
		private JsonNode requestBody = null;
		private String responseBody = null;
		private String responseStatus = null;
		
		public String getUri() {
			return uri;
		}
		public void setUri(String uri) {
			this.uri = uri;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public JsonNode getRequestBody() {
			return requestBody;
		}
		public void setRequestBody(String requestBody) {
			if (requestBody != null && requestBody != "") {
				ObjectMapper m = new ObjectMapper();
				try {
					this.requestBody = m.readValue(requestBody, JsonNode.class);
				} catch (JsonParseException e) {
					throw new RuntimeException("Failed parsing expecting value " + requestBody, e);
				} catch (JsonMappingException e) {
					throw new RuntimeException("Failed mapping expecting value " + requestBody, e);
				} catch (IOException e) {
					throw new RuntimeException("Failed reading expecting value " + requestBody, e);
				}
			}
		}
		public String getResponseBody() {
			return responseBody;
		}
		public void setResponseBody(String responseBody) {
			this.responseBody = responseBody;
		}
		public String getResponseStatus() {
			return responseStatus;
		}
		public void setResponseStatus(String responseStatus) {
			this.responseStatus = responseStatus;
		}
		
		@Override
		public String toString() {
			return "{url: " + uri + "; method: " + method + "; request body: " + requestBody + "; response body: " + responseBody + "; response status: " + responseStatus + "}";
		}
		
	}
}
