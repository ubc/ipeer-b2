package ca.ubc.ctlt.ipeerb2;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URI;

/**
 * Created by compass on 1/25/2014.
 */
public class IPeerB2OAuthRestTemplate extends OAuthRestTemplate {
    public IPeerB2OAuthRestTemplate(ProtectedResourceDetails resource) {
        super(resource);
    }

    public IPeerB2OAuthRestTemplate(ClientHttpRequestFactory requestFactory, ProtectedResourceDetails resource) {
        super(requestFactory, resource);
    }

    /**
     * Override this method to allow response body to be recorded when RuntimeException is thrown
     * @param url the fully-expanded URL to connect to
     * @param method the HTTP method to execute (GET, POST, etc.)
     * @param requestCallback object that prepares the request (can be <code>null</code>)
     * @param responseExtractor object that extracts the return value from the response (can be <code>null</code>)
     * @return an arbitrary object, as returned by the {@link ResponseExtractor}
     * @throws RestClientException
     */
    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "'url' must not be null");
        Assert.notNull(method, "'method' must not be null");
        ClientHttpResponse response = null;
        try {
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            if (!getErrorHandler().hasError(response)) {
                logResponseStatus(method, url, response);
            }
            else {
                handleResponseError(method, url, response);
            }
            if (responseExtractor != null) {
                return responseExtractor.extractData(response);
            }
            else {
                return null;
            }
        }
        catch (IOException ex) {
            throw new ResourceAccessException("I/O error on " + method.name() +
                    " request for \"" + url + "\":" + ex.getMessage(), ex);
        }
        catch (RuntimeException ex) {
            if (null == response) {
                throw ex;
            } else {
                throw new RuntimeException("Failed parse the response! Response Body: \"" + iPeerB2Util.getResponseBody(response) + "\"");
            }
        }
        finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void logResponseStatus(HttpMethod method, URI url, ClientHttpResponse response) {
        if (logger.isDebugEnabled()) {
            try {
                logger.debug(method.name() + " request for \"" + url + "\" resulted in " +
                        response.getStatusCode() + " (" +
                        response.getStatusText() + ")");
            }
            catch (IOException e) {
                // ignore
            }
        }
    }

    private void handleResponseError(HttpMethod method, URI url, ClientHttpResponse response) throws IOException {
        if (logger.isWarnEnabled()) {
            try {
                logger.warn(
                        method.name() + " request for \"" + url + "\" resulted in " + response.getStatusCode() + " (" +
                                response.getStatusText() + "); invoking error handler");
            }
            catch (IOException e) {
                // ignore
            }
        }
        getErrorHandler().handleError(response);
    }
}
