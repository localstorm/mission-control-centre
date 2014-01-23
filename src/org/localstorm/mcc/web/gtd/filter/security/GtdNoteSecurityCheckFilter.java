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
public class GtdNoteSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String NOTE_ID_PARAM = "noteId";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String nid = req.getParameter(NOTE_ID_PARAM);

        if (nid!=null)
        {
            Integer noteId = Integer.parseInt(nid);
            SecurityUtil.checkNoteSecurity(req.getSession(true), noteId, user, log);
        }
    }

}