package org.localstorm.mcc.web.cashflow.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.cashflow.Price;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.web.cashflow.CashflowBaseActionBean;
import org.localstorm.mcc.web.cashflow.Views;
import org.localstorm.tools.aop.runtime.Logged;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author localstorm
 * @secure-by session (no security check)
 */
@UrlBinding("/actions/cash/nil/AssetsLocationReport")
public class AssetsLocationViewActionBean extends CashflowBaseActionBean {

    public Set<String> classes;

    public Set<String> getClasses() {
        return classes;
    }

    @DefaultHandler
    @Logged
    public Resolution filling() {

        Collection<Asset> assets = this.getAssetManager().getAssets(this.getUser());
        classes = new TreeSet<String>();
        for (Asset a : assets) {
            String ac = a.getAssetClass();
            if (ac == null || ac.trim().length() == 0) {
                ac = "Other";
            }
            classes.add(ac);
        }

        return new ForwardResolution(Views.ASSETS_LOCATION);
    }

}