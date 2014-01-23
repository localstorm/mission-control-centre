package org.localstorm.mcc.web.gtd.filter.security;

import javax.servlet.http.HttpServletResponse;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
public class GtdContextSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String CONTEXT_ID_PARAM = "contextId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String cid = req.getParameter(CONTEXT_ID_PARAM);

        if (cid!=null)
        {
            Integer contextId = Integer.parseInt(cid);
            SecurityUtil.checkContextSecurity(req.getSession(true), contextId, user, log);
        }
    }

}