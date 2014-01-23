package org.localstorm.mcc.web.cashflow.filter.security;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.entity.Operation;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SecurityRuntimeException;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
class SecurityUtil 
{
    public static final String ACCESS_DENIED = "Access denied!";

    @SuppressWarnings("unchecked")
    public static void checkAssetSecurity(HttpSession sess, Integer assetId, User user, Logger log)
    {
        if (assetId!=null)
        {
            log.info("Checking access to asset=" + assetId + " for user=" + user.getLogin());
            Map<Integer, Boolean> aam = (Map<Integer, Boolean>) SessionUtil.getValue(sess,
                                                                                     CashflowSessionKeys.ACCESSIBLE_ASSETS_MAP);
            if (aam.containsKey(assetId)) {
                return;
            }

            throw new SecurityRuntimeException(ACCESS_DENIED);
        }
    }

    @SuppressWarnings("unchecked")
    public static void checkTargetSecurity(HttpSession sess, Integer targetId, User user, Logger log)
    {
        if (targetId!=null)
        {
            log.info("Checking access to target=" + targetId + " for user=" + user.getLogin());

            Map<Integer, Boolean> atm = (Map<Integer, Boolean>) SessionUtil.getValue(sess,
                                                                                     CashflowSessionKeys.ACCESSIBLE_TARGETS_MAP);
            if (atm.containsKey(targetId)) {
                return;
            }

            throw new SecurityRuntimeException(ACCESS_DENIED);
        }
    }

    public static void checkOperationSecurity(HttpSession session, Integer operationId, User user, Logger log)
    {
        if (operationId!=null)
        {
            log.info("Checking access to operation=" + operationId + " for user=" + user.getLogin());
            
            OperationManager om = ContextLookup.lookup(OperationManager.class, OperationManager.BEAN_NAME);

            try
            {
                Operation op  = om.findOperation(operationId);

                if (!user.getId().equals(op.getCost().getValuable().getOwner().getId())) {
                    throw new SecurityRuntimeException(ACCESS_DENIED);
                }
            } catch(ObjectNotFoundException e) {
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }
        }
    }
}
