package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.web.Views;
import org.localstorm.mcc.web.util.RequestUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TxFilter implements Filter 
{
    private static final Logger log = Logger.getLogger(TxFilter.class);

    public TxFilter() {
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        
        UserTransaction ut = null;
        HttpServletRequest req = (HttpServletRequest) _req;
        
        try
        {
            long t1 = System.currentTimeMillis();
            try {

                ut = ContextLookup.lookupTransaction();
                ut.begin();
                    chain.doFilter( req, resp );
                ut.commit();

            } catch (Exception e) {
                log.error("Transaction failed:", e);
                if (ut!=null && Status.STATUS_COMMITTED!=ut.getStatus() &&
                    ut.getStatus()!=Status.STATUS_NO_TRANSACTION) {
                    ut.setRollbackOnly();
                }

                RequestUtil.setException(req, e);
                req.getRequestDispatcher( Views.ERROR ).forward( req, resp );

            } finally {
                if (ut!=null && 
                    Status.STATUS_COMMITTED!=ut.getStatus() && 
                    ut.getStatus()!=Status.STATUS_NO_TRANSACTION) 
                {
                    ut.rollback();
                }
                log.info("Transaction ["+req.getRequestURL()+"] processed in "+(System.currentTimeMillis()-t1)+" ms.");
            }
        } catch(SystemException e) {
            RequestUtil.setException(req, e);
            req.getRequestDispatcher(Views.ERROR).forward( req, resp );
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}
