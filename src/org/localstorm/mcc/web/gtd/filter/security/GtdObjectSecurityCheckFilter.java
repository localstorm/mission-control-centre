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
public class GtdObjectSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String OBJECT_ID_PARAM = "objectId";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String oid = req.getParameter(OBJECT_ID_PARAM);

        if (oid!=null)
        {
            Integer objectId = Integer.parseInt(oid);
            SecurityUtil.checkObjectSecurity(req.getSession(true), objectId, user, log);
        }
    }

}