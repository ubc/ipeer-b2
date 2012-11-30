package ca.ubc.ctlt.ipeerb2.security;

import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.session.BbSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class BlackboardPreAuthenticatedProcessingFilter extends
		AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		Context context = ContextManagerFactory.getInstance().getContext();
		BbSession session = context.getSession();
		if (context != null && session != null && context.getUser() != null
				&& session.isAuthenticated()) {
			return context.getUser().getUserName();
		} else {
			return null;
		}
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		Context context = ContextManagerFactory.getInstance().getContext();
		BbSession session = context.getSession();
		if (context != null && session != null && session.isAuthenticated()) {
			return context.getUser();
		} else {
			return null;
		}
	}

}
