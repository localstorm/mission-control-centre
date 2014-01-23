package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.entity.Context;
import org.localstorm.mcc.ejb.gtd.ContextManager;
import org.localstorm.mcc.ejb.gtd.entity.FlightPlan;
import org.localstorm.mcc.ejb.gtd.FlightPlanManager;
import org.localstorm.mcc.ejb.gtd.entity.Effort;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.gtd.actions.wrap.WrapUtil;
import org.localstorm.mcc.web.util.FilterUtil;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/BattleMapSupport")
public class BattleMapSupportActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private Integer contextId;

    @Validate( required=true )
    private String filter;

    private List<Task> tasks;

    private Context ctx;


    public Context getContextResult() {
        return this.ctx;
    }

    public void setContextResult(Context ctx) {
        this.ctx = ctx;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<? extends Task> tasks) {
        this.tasks = new ArrayList<Task>(tasks);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getContextId() {
        return contextId;
    }

    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }


    @DefaultHandler
    @Logged
    public Resolution filling() throws Exception {

        User            user = super.getUser();
        ContextManager    cm = super.getContextManager();
        TaskManager       tm = super.getTaskManager();
        FlightPlanManager fpm = super.getFlightPlanManager();

        Filter    f = Filter.valueOf(this.getFilter());
        Context context = cm.findById(this.getContextId());

        this.setContextResult(context);

        Collection<Task> _tasks = null;

        FlightPlan fp        = fpm.findByUser(user);
        Collection<Task> fpt = fpm.getTasksFromFlightPlan(fp);

        switch( f ) {
            case ELEMENTARY:
            case EASY:
            case MEDIUM:
            case DIFFICULT:
            case VERY_DIFFICULT:
                _tasks = tm.findByLoE(user, context, Effort.valueOf(this.getFilter()));
                break;
            case HINTED:
                _tasks = tm.findHinted(user, context);
                break;
            case PENDING:
                _tasks = tm.findPending(user, context);
                break;
            case FIN:
                _tasks = tm.findFinished(user, context);
                break;
            case AWAITED:
                _tasks = tm.findAwaited(user, context);
                break;
            case REDLINE:
                _tasks = tm.findRedlined(user, context);
                break;
            case DEADLINE:
                _tasks = tm.findDeadlined(user, context);
                break;
            case FLIGHT:
                _tasks = fpm.getTasksFromFlightPlan(fp, context);
                FilterUtil.applyContextFilter(_tasks, this.getContextId(), true);
                break;
            default:
                throw new RuntimeException("Unexpected case!");
        }

        super.setAffectedContexts(_tasks);
        this.setTasks(WrapUtil.genWrappers(_tasks, fpt));

        ReturnPageBean rpb = new ReturnPageBean(Pages.BMS_VIEW.toString());
        {
            rpb.setParam(InputParameters.CTX,    this.getContextId().toString());
            rpb.setParam(InputParameters.FILTER, this.getFilter());
        }

        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.VIEW_BMS);
    }

    private static interface InputParameters
    {
        public static final String CTX    = "contextId";
        public static final String FILTER = "filter";
    }

    private static enum Filter
    {
        HINTED,
        PENDING,
        AWAITED,
        FLIGHT,
        REDLINE,
        DEADLINE,
        FIN,
        ELEMENTARY,
        EASY,
        MEDIUM,
        DIFFICULT,
        VERY_DIFFICULT
    }
}
