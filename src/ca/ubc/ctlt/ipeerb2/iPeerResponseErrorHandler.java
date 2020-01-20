package ca.ubc.ctlt.ipeerb2;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;

public class iPeerResponseErrorHandler extends DefaultResponseErrorHandler {
	private static final Logger logger = LoggerFactory.getLogger(DefaultResponseErrorHandler.class);
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		ResponseError error = null;
		HttpStatus statusCode = getHttpStatusCode(response);
		byte[] responseBody = iPeerB2Util.getResponseBody(response);
		try {
			ObjectMapper mapper = new ObjectMapper();
			error = mapper.readValue(responseBody, ResponseError.class);
			logger.error("iPeer B2 Response Error: " + error);
		} catch (Exception e) {
			// iPeerHttpErrorException will do full response body dump when ResponseError is null
			logger.error("Failed to parse response as ipeer response error! Fallback to full response body dump: " + new String(responseBody));
		}
		
		throw new iPeerHttpErrorException(statusCode, response.getStatusText(),
				response.getHeaders(), responseBody, getCharset(response), error);		
	}
	
}
