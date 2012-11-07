package ca.ubc.ctlt.ipeerb2;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class iPeerHttpErrorException extends HttpStatusCodeException {

	private static final long serialVersionUID = -1386996782091134415L;
	private ResponseError responseError;

	public iPeerHttpErrorException(HttpStatus statusCode, ResponseError error) {
		super(statusCode);
		responseError = error;
	}

	public iPeerHttpErrorException(HttpStatus statusCode, String statusText,
			HttpHeaders headers, byte[] responseBody, Charset charset, ResponseError error) {
		super(statusCode, statusText, headers, responseBody, charset);
		responseError = error;
	}

	@Override
	public String getMessage() {
		return (null == responseError ? this.getResponseBodyAsString() : responseError.toString());
	}
}
