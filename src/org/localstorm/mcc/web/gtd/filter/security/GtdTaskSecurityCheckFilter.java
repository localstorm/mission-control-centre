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
public class GtdTaskSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String TASK_ID_PARAM = "taskId";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String tid = req.getParameter(TASK_ID_PARAM);

        if (tid!=null)
        {
            Integer taskId = Integer.parseInt(tid);
            SecurityUtil.checkTaskSecurity(req.getSession(true), taskId, user, log);
        }
    }


}