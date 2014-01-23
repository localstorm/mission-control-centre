package org.localstorm.mcc.web.gtd.filter;


import org.localstorm.mcc.ejb.gtd.RefObjectManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.ContextManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.gtd.GtdSessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class LazyLoadFilter implements Filter 
{
    
    public LazyLoadFilter() {
    
    }

    @Override
    public void doFilter(ServletRequest _req, 
                         ServletResponse _res, 
                         FilterChain chain) throws IOException, ServletException 
    {
        performLazyLoad((HttpServletRequest) _req, (HttpServletResponse) _res );
        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

    private void fillAccessibleContexts(List<Context> ctxs, Collection<Context> actxList, HttpSession sess)
    {
        Map<Integer, Boolean> acm = new HashMap<Integer, Boolean>();
        for (Context ctx:ctxs) {
            acm.put(ctx.getId(), Boolean.TRUE);
        }

        for (Context ctx:actxList) {
            acm.put(ctx.getId(), Boolean.TRUE);
        }
        
        SessionUtil.fill(sess, GtdSessionKeys.ACCESSIBLE_CONTEXTS_MAP, acm);
    }

    private void performLazyLoad(HttpServletRequest req, HttpServletResponse res) {
        HttpSession sess = req.getSession(true);
        User user = (User) sess.getAttribute(GtdSessionKeys.USER);
        
        if ( SessionUtil.isEmpty(sess, GtdSessionKeys.FILTER_CONTEXT) ) {
            SessionUtil.fill(sess, GtdSessionKeys.FILTER_CONTEXT, -1);
        }
        
        if ( SessionUtil.isEmpty(sess, GtdSessionKeys.CONTEXTS) ) {
            ContextManager cm = ContextLookup.lookup(ContextManager.class, 
                                                     ContextManager.BEAN_NAME);
            
            Collection<Context> ctxList = cm.getContexts(user);
            Collection<Context> actxList = cm.getArchived(user);

            List<Context> ctxs = new ArrayList<Context>(ctxList);
            
            Collections.sort(ctxs, new Context.NameAscComparator());
            
            SessionUtil.fill(sess, GtdSessionKeys.CONTEXTS, ctxs);

            this.fillAccessibleContexts(ctxs, actxList,sess);
        }
        
        if ( SessionUtil.isEmpty(sess, GtdSessionKeys.REFERENCE_OBJECTS) ) {
            RefObjectManager rom = ContextLookup.lookup(RefObjectManager.class, 
                                                         RefObjectManager.BEAN_NAME);

            SessionUtil.fill(sess, GtdSessionKeys.REFERENCE_OBJECTS, rom.findOperativeByOwner(user, false));
        }

    }
    

}