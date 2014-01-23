package org.localstorm.mcc.web.people.filter.security;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.people.PeopleSecuritySupport;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SecurityRuntimeException;
import org.localstorm.mcc.web.people.PeopleSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
class SecurityUtil 
{
    public static final String ACCESS_DENIED = "Access denied!";
    
    @SuppressWarnings("unchecked")
    public static void checkGroupSecurity(HttpSession sess, Integer groupId, User user, Logger log)
    {
        if (groupId!=null)
        {
            log.info("Checking access to personGroup=" + groupId + " for user=" + user.getLogin());
            Map<Integer, Boolean> acm = (Map<Integer, Boolean>) SessionUtil.getValue(sess,
                                                                                     PeopleSessionKeys.ACCESSIBLE_GROUPS_MAP);
            if (acm.containsKey(groupId)) {
                return;
            }

            throw new SecurityRuntimeException(ACCESS_DENIED);
        }
    }

    @SuppressWarnings("unchecked")
    public static void checkMailListSecurity(HttpSession sess, Integer mailListId, User user, Logger log)
    {
        if (mailListId!=null)
        {
            log.info("Checking access to mailList=" + mailListId + " for user=" + user.getLogin());
            Map<Integer, Boolean> acm = (Map<Integer, Boolean>) SessionUtil.getValue(sess,
                                                                                     PeopleSessionKeys.ACCESSIBLE_MAIL_LISTS_MAP);
            if (acm.containsKey(mailListId)) {
                return;
            }

            throw new SecurityRuntimeException(ACCESS_DENIED);
        }
    }

    public static void checkPersonSecurity(HttpSession sess, Integer personId, User user, Logger log)
            throws SecurityRuntimeException
    {
        if (personId!=null)
        {
            checkPersonsSecurity(sess, new Integer[]{personId}, user, log);
        }
    }

    public static void checkPersonsSecurity(HttpSession sess, Integer[] personIds, User user, Logger log)
            throws SecurityRuntimeException
    {
        if (personIds!=null && personIds.length>0) {
            log.info("Checking access to persons=" + Arrays.asList(personIds) + " for user=" + user.getLogin());

            PeopleSecuritySupport  pss = ContextLookup.lookup(PeopleSecuritySupport.class, PeopleSecuritySupport.BEAN_NAME);
            if (!pss.checkPersonsAccess(personIds, user)) {
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }
        }
    }

    public static void checkAttributesSecurity(HttpSession sess, Integer[] attributeIds, User user, Logger log)
            throws SecurityRuntimeException
    {
        if (attributeIds!=null && attributeIds.length>0) {
            log.info("Checking access to attributes=" + Arrays.asList(attributeIds) + " for user=" + user.getLogin());

            PeopleSecuritySupport  pss = ContextLookup.lookup(PeopleSecuritySupport.class, PeopleSecuritySupport.BEAN_NAME);
            if (!pss.checkAttributesAccess(attributeIds, user)) {
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }
        }
    }

    public static void checkAttributeSecurity(HttpSession sess, Integer attrId, User user, Logger log)
            throws SecurityRuntimeException
    {
        if (attrId!=null) {
            checkAttributesSecurity(sess, new Integer[]{attrId}, user, log);
        }
    }


}
