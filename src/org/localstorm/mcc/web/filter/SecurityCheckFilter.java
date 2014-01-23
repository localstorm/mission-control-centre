package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.CommonSessionKeys;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.util.RequestUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class SecurityCheckFilter implements Filter
{
    protected final static Logger log = Logger.getLogger(SecurityCheckFilter.class);

    public User getUser(HttpServletRequest req)
    {
        HttpSession sess = req.getSession(true);
        return (User) sess.getAttribute(CommonSessionKeys.USER);
    }

    @Override
    public final void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException,
                   ServletException
    {
        User user = this.getUser((HttpServletRequest) req);
        HttpServletRequest  _req = (HttpServletRequest) req;
        HttpServletResponse _res = (HttpServletResponse) res;

        try {
            this.doFilter(_req, _res,user);
        } catch(Exception e) {
            RequestUtil.setException((HttpServletRequest) req, e);
            req.getRequestDispatcher( Views.ERROR ).forward( req, res );
        }

        if (!res.isCommitted())
        {
            chain.doFilter(req, res);
        }
    }


    public abstract void doFilter(HttpServletRequest req,
                                  HttpServletResponse res,
                                  User user)
            throws IOException,
                   ServletException;

    @Override
    public void init(FilterConfig cfg)
            throws ServletException
    {

    }

    @Override
    public void destroy()
    {
        
    }
}
