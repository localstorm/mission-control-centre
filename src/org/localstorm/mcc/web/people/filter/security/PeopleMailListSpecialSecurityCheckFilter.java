package org.localstorm.mcc.web.people.filter.security;

import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.WebUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class PeopleMailListSpecialSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String MANY_EMAILS_PERSON_IDS_PARAM = "manyEmailsPersonIds";
    public static final String RESOLVED_PERSON_IDS_PARAM    = "resolvedPersonIds";
    public static final String ATTRIBUTE_IDS_PARAM          = "attributes";


    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String   mepid = req.getParameter(MANY_EMAILS_PERSON_IDS_PARAM);
        String   rpid  = req.getParameter(RESOLVED_PERSON_IDS_PARAM);
        String[] attrs = req.getParameterValues(ATTRIBUTE_IDS_PARAM);

        Collection<Integer> personIds = new ArrayList<Integer>();
        Collection<Integer> attributeIds = new ArrayList<Integer>();

        if (mepid!=null && mepid.length()>0) {
            personIds.addAll(WebUtil.stringToIds(mepid));
        }

        if (rpid!=null && rpid.length()>0) {
            personIds.addAll(WebUtil.stringToIds(rpid));
        }

        if (!personIds.isEmpty()) {
            Integer[] pids = new Integer[personIds.size()];
            personIds.toArray(pids);

            SecurityUtil.checkPersonsSecurity(req.getSession(true), pids, user, log);
        }

        if (attrs!=null && attrs.length>0) {
            for (String att: attrs) {
                attributeIds.add(Integer.parseInt(att));
            }

            Integer[] attrsArray = new Integer[attributeIds.size()];
            attributeIds.toArray(attrsArray);

            SecurityUtil.checkAttributesSecurity(req.getSession(true), attrsArray, user, log);
        }
    }

}