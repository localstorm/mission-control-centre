package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author localstorm
 */
@UrlBinding("/actions/gtd/nil/TaskStructureReport")
public class TaskStructureReportActionBean extends GtdBaseActionBean {

    @DefaultHandler
    @Logged
    public Resolution filling() {
        return new ForwardResolution(Views.TASKS_STRUCTURE);
    }

}