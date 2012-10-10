package ca.ubc.ctlt.ipeerb2;

import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetailsService;

public class iPeerB2ProtectedResourceDetailsService implements
		ProtectedResourceDetailsService {
	public static final String IPEERB2_DETAILS_ID = "ipeerResourceDetails";
	
	private ProtectedResourceDetails protectedResourceDetails;
	
	public ProtectedResourceDetails getProtectedResourceDetails() {
		return protectedResourceDetails;
	}


	public void setProtectedResourceDetails(
			ProtectedResourceDetails protectedResourceDetails) {
		this.protectedResourceDetails = protectedResourceDetails;
	}

	@Override
	public ProtectedResourceDetails loadProtectedResourceDetailsById(String id)
			throws IllegalArgumentException {
		return protectedResourceDetails;
	}

}
