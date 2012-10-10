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
	private Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public void storeToken(String resourceId, OAuthConsumerToken token) {
	}

	@Override
	public void removeToken(String resourceId) {
	}

	@Override
	public OAuthConsumerToken getToken(String resourceId)
			throws AuthenticationException {
		System.out.println("************ Get token for " + resourceId);
		String key = configuration.getSetting(Configuration.TOKEN_KEY);
		String secret = configuration.getSetting(Configuration.TOKEN_SECRET);
		if (null == key || key.isEmpty() || null == secret || secret.isEmpty()) {
			throw new RuntimeException("Token key or secret is empty. Did you forget to set up the building block?");
		}
		
		OAuthConsumerToken token = new OAuthConsumerToken();
		token.setAccessToken(true);
		token.setSecret(secret);
		token.setValue(key);
		
		return token;
	}

}
