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
public class PeopleMailListSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String MAIL_LIST_ID_PARAM = "mailListId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String mlid = req.getParameter(MAIL_LIST_ID_PARAM);

        if (mlid!=null)
        {
            Integer mailListId = Integer.parseInt(mlid);
            SecurityUtil.checkMailListSecurity(req.getSession(true), mailListId, user, log);
        }
    }

}