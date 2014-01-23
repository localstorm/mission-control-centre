package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.ejb.gtd.entity.TaskResolutionAction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.GTDList;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.backend.TaskResolutionLogic;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/task/ResolveTask")
public class TaskResolveActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int taskId;
    
    @Validate( required=true )
    private String action;

    private String runtimeNote;

    public String getRuntimeNote() {
        return runtimeNote;
    }

    public void setRuntimeNote(String runtimeNote) {
        this.runtimeNote = runtimeNote;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    @DefaultHandler
    @Logged
    public Resolution resolvingTask() throws Exception {
        
        TaskManager tm = super.getTaskManager();
        Task t = tm.findById(this.getTaskId());
        GTDList list = t.getList();

        TaskResolutionAction resolveAction = this.getResolveAction();
        TaskResolutionLogic.resolveTask(this.getTaskId(),
                                        resolveAction,
                                        this.getRuntimeNote(),
                                        super.getCurrentList(),
                                        super.getClipboard(),
                                        super.getTaskManager(),
                                        super.getListManager(),
                                        super.getFlightPlanManager(),
                                        super.getUser());

        ReturnPageBean rpb = super.getReturnPageBean();

        if (rpb!=null) {
            return NextDestinationUtil.getRedirection(rpb);
        } else {
            RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
            {
                rr.addParameter(ListViewActionBean.IncomingParameters.LIST_ID, list.getId());
            }
            return rr;
        }
    }

    private TaskResolutionAction getResolveAction() {
        return TaskResolutionAction.valueOf(action);
    }
   
}
