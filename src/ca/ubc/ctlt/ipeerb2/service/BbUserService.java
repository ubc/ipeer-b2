package ca.ubc.ctlt.ipeerb2.service;

import blackboard.data.user.User;
import blackboard.data.user.User.SystemRole;

/**
 *
 * @author whboyd
 */
public interface BbUserService {
    
    boolean currentUserHasEntitlement(String entitlement);
    
    boolean currentUserHasEntitlementInCurrentContext(String entitlement);
    
    boolean currentUserHasSystemRole(SystemRole systemRole);
    
    boolean userHasSystemRole(User user, SystemRole systemRole);

}
