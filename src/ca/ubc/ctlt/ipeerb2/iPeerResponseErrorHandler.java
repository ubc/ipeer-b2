package ca.ubc.ctlt.ipeerb2;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;

public class iPeerResponseErrorHandler extends DefaultResponseErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(DefaultResponseErrorHandler.class);
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		try {
			super.handleError(response);
		} catch (HttpClientErrorException e) {
			logger.error("Error in API call! Response message: "+e.getResponseBodyAsString(), e);
			throw e;
		} 
	}
}
