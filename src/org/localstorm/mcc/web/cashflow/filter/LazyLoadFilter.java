package org.localstorm.mcc.web.cashflow.filter;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.AssetManager;
import org.localstorm.mcc.ejb.cashflow.OperationManager;
import org.localstorm.mcc.ejb.cashflow.entity.Target;
import org.localstorm.mcc.ejb.cashflow.TargetManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.cashflow.CashflowSessionKeys;
import org.localstorm.mcc.web.cashflow.actions.wrap.TargetWrapper;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
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

    private void performLazyLoad(HttpServletRequest req, HttpServletResponse res) {
        HttpSession sess = req.getSession(true);
        User user = (User) sess.getAttribute(CashflowSessionKeys.USER);
        
        if ( SessionUtil.isEmpty(sess, CashflowSessionKeys.ASSETS) ) {
            AssetManager am = ContextLookup.lookup(AssetManager.class,
                                                   AssetManager.BEAN_NAME);
            OperationManager om = ContextLookup.lookup(OperationManager.class,
                                                       OperationManager.BEAN_NAME);

            Collection<Asset> assets = am.getAssets(user);
            assets = WrapUtil.wrapAssets(assets, om);
            
            SessionUtil.fill(sess, CashflowSessionKeys.ASSETS, assets);

            this.fillAccessibleAssets(assets, am.getArchivedAssets(user), sess);
        }

        if ( SessionUtil.isEmpty(sess, CashflowSessionKeys.TARGETS) ) {
            TargetManager tm = ContextLookup.lookup(TargetManager.class,
                                                    TargetManager.BEAN_NAME);
            OperationManager om = ContextLookup.lookup(OperationManager.class,
                                                       OperationManager.BEAN_NAME);
            Collection<Target> targets = tm.find(user);
            targets = WrapUtil.wrapTargets(targets, om);

            List<Target> tarList = new ArrayList<Target>(targets);
            Collections.sort(tarList, TargetWrapper.getCurrentCostComparator());

            SessionUtil.fill(sess, CashflowSessionKeys.TARGETS, tarList);

            this.fillAccessibleTargets(tarList, tm.findArchived(user), sess);
        }
    }

    private void fillAccessibleAssets(Collection<Asset> assets, Collection<Asset> archivedAssets, HttpSession sess)
    {
        Map<Integer, Boolean> acm = new HashMap<Integer, Boolean>();
        for (Asset asset : assets) {
            acm.put(asset.getId(), Boolean.TRUE);
        }
        
        for (Asset asset : archivedAssets) {
            acm.put(asset.getId(), Boolean.TRUE);
        }

        SessionUtil.fill(sess, CashflowSessionKeys.ACCESSIBLE_ASSETS_MAP, acm);
    }

    private void fillAccessibleTargets(Collection<Target> targets, Collection<Target> archivedTargets, HttpSession sess)
    {
        Map<Integer, Boolean> acm = new HashMap<Integer, Boolean>();
        for (Target t : targets) {
            acm.put(t.getId(), Boolean.TRUE);
        }
        
        for (Target t : archivedTargets) {
            acm.put(t.getId(), Boolean.TRUE);
        }

        SessionUtil.fill(sess, CashflowSessionKeys.ACCESSIBLE_TARGETS_MAP, acm);
    }
    

}