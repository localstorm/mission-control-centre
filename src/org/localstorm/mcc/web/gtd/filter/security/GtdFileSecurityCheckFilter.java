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
public class GtdFileSecurityCheckFilter extends SecurityCheckFilter
{
    private static final String FILE_ID_PARAM="fileId";
  
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String fid = req.getParameter(FILE_ID_PARAM);

        if (fid!=null)
        {
            Integer fileId = Integer.parseInt(fid);
            SecurityUtil.checkFileSecurity(req.getSession(true), fileId, user, log);
        }
    }

}