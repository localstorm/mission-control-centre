package org.localstorm.mcc.web.cashflow.filter.security;

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
public class CashflowTargetSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String TARGET_ID_PARAM = "targetId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String tid = req.getParameter(TARGET_ID_PARAM);

        if (tid!=null)
        {
            Integer targetId = Integer.parseInt(tid);
            SecurityUtil.checkTargetSecurity(req.getSession(true), targetId, user, log);
        }
    }

}