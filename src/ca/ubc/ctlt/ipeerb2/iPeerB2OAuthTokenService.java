package ca.ubc.ctlt.ipeerb2;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.consumer.OAuthConsumerToken;
import org.springframework.security.oauth.consumer.token.OAuthConsumerTokenServices;

/**
 * iPeerB2 OAuth Token Service. Used to bypass standard 3-legged OAuth so that we can use 
 * 2-legged OAuth.
 * 
 * @author compass
 *
 */
public class iPeerB2OAuthTokenService implements OAuthConsumerTokenServices {
	@Override
	public void storeToken(String resourceId, OAuthConsumerToken token) {
	}

	@Override
	public void removeToken(String resourceId) {
	}

	@Override
	public OAuthConsumerToken getToken(String resourceId)
			throws AuthenticationException {
		OAuthConsumerToken token = new OAuthConsumerToken();
		token.setAccessToken(true);
		return token;
	}

}
