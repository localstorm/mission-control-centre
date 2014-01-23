package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NoCacheFilter implements Filter 
{
    public NoCacheFilter() {
    
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) _res;
        
        resp.setHeader("Cache-Control", "private,no-cache,no-store");
        resp.addHeader("Pragma", "No-Cache");
        resp.setDateHeader("Expires", 0);
        
        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}