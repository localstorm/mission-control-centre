package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import org.localstorm.mcc.web.Constants;
import java.text.SimpleDateFormat;
import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.entity.FlightPlan;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.entity.Hint;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.util.DateUtil;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/task/ViewTask")
public class TaskViewActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int taskId;

    private Task taskResult;
    private String deadline;
    private String redline;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int id) {
        this.taskId = id;
    }
    
    public Task getTaskResult() {
        return taskResult;
    }
    
    public void setTaskResult(Task taskResult) {
        this.taskResult = taskResult;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    
    public String getRedline() {
        return redline;
    }

    public void setRedline(String redline) {
        this.redline = redline;
    }

    
    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        
        TaskManager        tm = super.getTaskManager();
        FlightPlanManager fpm = super.getFlightPlanManager();
        HintManager        hm = super.getHintManager();

        Task task = tm.findById(this.getTaskId());

        FlightPlan        fp  = fpm.findByUser(super.getUser());
        Collection<Task> cfpt = fpm.getTasksFromFlightPlan(fp);
        Collection<Hint> hints=hm.getHintsForTask(task);

        task = WrapUtil.genWrapper(task, cfpt, hints);
        
        super.setCurrent(task);
        
        this.setTaskResult(task);
        
        this.setDeadline(DateUtil.format(task.getDeadline(), sdf));
        this.setRedline(DateUtil.format(task.getRedline(), sdf));
        
        return new ForwardResolution(Views.VIEW_TASK);
    }

    
}
