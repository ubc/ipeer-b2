package ca.ubc.ctlt.ipeerb2;

import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;

public class iPeerB2ProtectedResourceDetails extends BaseProtectedResourceDetails {
	private Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public iPeerB2ProtectedResourceDetails() {
		setId(iPeerB2ProtectedResourceDetailsService.IPEERB2_DETAILS_ID);
		// don't use authorization header as cakephp doens't support it
		setAcceptsAuthorizationHeader(false);
	}

	@Override
	public String getConsumerKey() {
		String key = configuration.getSetting(Configuration.CONSUMER_KEY);
		if (null == key || key.isEmpty()) {
			throw new RuntimeException("Consumer key is empty. Did you forget to set up the building block?");
		}
		
		return key;
	}

	@Override
	public SignatureSecret getSharedSecret() {
		String secret = configuration.getSetting(Configuration.SHARED_SECRET);
		if (null == secret || secret.isEmpty()) {
			throw new RuntimeException("Shared secret is empty. Did you forget to set up the building block?");
		}
		
		return new SharedConsumerSecretImpl(secret);
	}

}
