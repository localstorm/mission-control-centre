package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/task/UnhintTask")
public class TaskUnhintActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int taskId;
    
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    @DefaultHandler
    @Logged
    public Resolution resolvingTask() throws Exception {
        
        TaskManager tm = super.getTaskManager();
        HintManager hm = super.getHintManager();

        Task t = tm.findById(this.getTaskId());

        hm.updateHintsForTask(t);

        ReturnPageBean rpb = super.getReturnPageBean();

        if (rpb!=null) {
            return NextDestinationUtil.getRedirection(rpb);
        } else {
            return NextDestinationUtil.getDefaultRedirection();
        }
    }

}
