package ca.ubc.ctlt.ipeerb2.service;

import blackboard.data.user.User;
import blackboard.data.user.User.SystemRole;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.security.AccessException;
import blackboard.platform.security.SecurityUtil;
import org.springframework.stereotype.Service;

/**
 *
 * @author whboyd
 */
@Service
public class BbUserServiceImpl implements BbUserService {
    
    @Override
    public boolean currentUserHasEntitlement(String entitlement) {
        return SecurityUtil.userHasEntitlement(entitlement);
    }
    
    @Override
    public boolean currentUserHasEntitlementInCurrentContext(String entitlement) {
        try {
            SecurityUtil.checkEntitlement(entitlement);
            return true;
        } catch (AccessException e) {
            return false;
        }
    }
    
    @Override
    public boolean currentUserHasSystemRole(SystemRole systemRole) {
        User currentUser = ContextManagerFactory.getInstance().getContext().getUser();
        if (currentUser != null) {
            return userHasSystemRole(currentUser, systemRole);
        } else {
            return false;
        }
    } 
    
    @Override
    public boolean userHasSystemRole(User user, SystemRole systemRole) {
        return systemRole.equals(user.getSystemRole());
    }
    
}
