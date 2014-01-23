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
public class GtdListSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String LIST_ID_PARAM = "listId";
 
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String lid = req.getParameter(LIST_ID_PARAM);

        if (lid!=null)
        {
            Integer listId = Integer.parseInt(lid);
            SecurityUtil.checkListSecurity(req.getSession(true), listId, user, log);
        }
    }


}