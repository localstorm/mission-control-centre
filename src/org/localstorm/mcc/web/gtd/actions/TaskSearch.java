package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.tools.aop.runtime.Logged;


/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/SearchTasks")
public class TaskSearch extends GtdBaseActionBean
{
    public boolean isFound() {
        return false;
    }

    public String getText() {
        return "";
    }

    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        return new ForwardResolution(Views.SEARCH_TASKS);
    }

    
}
