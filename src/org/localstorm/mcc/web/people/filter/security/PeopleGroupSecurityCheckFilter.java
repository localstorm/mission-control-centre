package org.localstorm.mcc.web.people.filter.security;

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
public class PeopleGroupSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String GROUP_ID_PARAM = "groupId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String gid = req.getParameter(GROUP_ID_PARAM);

        if (gid!=null)
        {
            Integer groupId = Integer.parseInt(gid);
            SecurityUtil.checkGroupSecurity(req.getSession(true), groupId, user, log);
        }
    }

}