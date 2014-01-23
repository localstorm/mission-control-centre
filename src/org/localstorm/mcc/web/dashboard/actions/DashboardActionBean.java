package org.localstorm.mcc.web.dashboard.actions;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.BaseActionBean;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.Views;

import org.localstorm.mcc.web.gtd.GtdDashboardAgent;
import org.localstorm.mcc.web.gtd.actions.Pages;
import org.localstorm.mcc.web.people.PeopleDashboardAgent;
import org.localstorm.tools.aop.runtime.Logged;

@UrlBinding("/actions/Dashboard")
public class DashboardActionBean extends BaseActionBean {

    
    @DefaultHandler
    @Logged
    public Resolution filling() {

        ActionBeanContext ctx = super.getContext();

        GtdDashboardAgent.prepare(ctx);
        PeopleDashboardAgent.prepare(ctx);

        ReturnPageBean rpb = new ReturnPageBean(Pages.DASH.toString());
        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.DASH);
    }

}