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
public class GtdInboxSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String ENTRY_ID_PARAM = "entryId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String ieId = req.getParameter(ENTRY_ID_PARAM);

        if (ieId!=null)
        {
            Integer entryId = Integer.parseInt(ieId);
            SecurityUtil.checkInboxEntrySecurity(req.getSession(true), entryId, user, log);
        }
    }

}