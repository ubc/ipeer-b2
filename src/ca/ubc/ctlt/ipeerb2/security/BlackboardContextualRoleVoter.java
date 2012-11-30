package ca.ubc.ctlt.ipeerb2.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import blackboard.data.user.User;
import ca.ubc.ctlt.ipeerb2.service.BbUserService;

@Service
public class BlackboardContextualRoleVoter implements AccessDecisionVoter<Object> {
    private static final String ATTRIBUTE_PREFIX = "BB_";
    private static final String SYSTEM_ADMIN_ROLE = "SYSTEM_ADMIN_ROLE";
    private static final String ENTITLEMENT_PREFIX = "ENTITLEMENT_";
    
    @Autowired
    private BbUserService bbUserService;
    
    
    public BbUserService getBbUserService() {
		return bbUserService;
	}

	public void setBbUserService(BbUserService bbUserService) {
		this.bbUserService = bbUserService;
	}

	@Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int affirmatives = 0;
        
        if (authentication == null) {
        	return ACCESS_ABSTAIN;
        }

        for(ConfigAttribute attribute : attributes) {
            if (attribute.getAttribute().startsWith(ATTRIBUTE_PREFIX) && attribute.getAttribute().length() > ATTRIBUTE_PREFIX.length()) {
                String attributeCode = attribute.getAttribute().substring(ATTRIBUTE_PREFIX.length());
                if (attributeCode.equals(SYSTEM_ADMIN_ROLE)) {
                    if (!bbUserService.currentUserHasSystemRole(User.SystemRole.SYSTEM_ADMIN)) {
                        return ACCESS_DENIED;
                    } else {
                        affirmatives++;
                    }
                } else if (attributeCode.startsWith(ENTITLEMENT_PREFIX) && attributeCode.length() > ENTITLEMENT_PREFIX.length()) {
                    String entitlement = attributeCode.substring(ENTITLEMENT_PREFIX.length());
                    if (!bbUserService.currentUserHasEntitlementInCurrentContext(entitlement)) {
                        return ACCESS_DENIED;
                    } else {
                        affirmatives++;
                    }
                }
            }
        }
        
        if (affirmatives > 0) {
            return ACCESS_GRANTED;
        } else {
            return ACCESS_ABSTAIN;
        }

    }

	@Override
    public boolean supports(ConfigAttribute attribute) {
        if (null != attribute.getAttribute() && attribute.getAttribute().startsWith(ATTRIBUTE_PREFIX)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean supports(Class<?> type) {
        return true;
    }
}
