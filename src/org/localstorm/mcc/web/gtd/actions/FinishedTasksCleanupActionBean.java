package org.localstorm.mcc.web.gtd.actions;

import java.util.Iterator;
import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.dashboard.actions.DashboardActionBean;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/nil/CleanupFinishedTasks")
public class FinishedTasksCleanupActionBean extends GtdBaseActionBean {

    @DefaultHandler
    @Logged
    public Resolution cleanup() {
        
        TaskManager tm = super.getTaskManager();
        tm.removeFinishedTasks(this.getUser());

        GtdClipboard clip = super.getClipboard();

        for (Iterator<Task> it = clip.getTasks().iterator(); it.hasNext(); ) {
            Task t = it.next();

            if (t.isCancelled() || t.isFinished() ) {
                it.remove();
            }
        }

        return new RedirectResolution( DashboardActionBean.class );
    }
    
}