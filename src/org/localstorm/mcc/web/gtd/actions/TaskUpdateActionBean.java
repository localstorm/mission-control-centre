package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.Constants;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

import org.localstorm.mcc.ejb.gtd.entity.Hint;
import org.localstorm.mcc.ejb.gtd.entity.HintCondition;
import org.localstorm.mcc.ejb.gtd.HintManager;
import org.localstorm.mcc.ejb.gtd.entity.Task;
import org.localstorm.mcc.ejb.gtd.TaskManager;
import org.localstorm.mcc.web.util.DateUtil;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.gtd.GtdClipboard;
import org.localstorm.tools.aop.runtime.Logged;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/ctx/list/task/UpdateTask")
public class TaskUpdateActionBean extends TaskViewActionBean
{
    @Validate( required=true )
    private String summary;
    
    private String details;
    
    @Validate( required=true )
    private Integer effort;

    @Validate( required=true )
    private String mode;

    private String[] hints;

    public String getSummary() {
        return summary;
    }

    public String getDetails() {
        return details;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getEffort() {
        return effort;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String[] getHints() {
        return hints;
    }

    public void setHints(String[] hints) {
        this.hints = hints;
    }
    
    @After( LifecycleStage.BindingAndValidation )
    public void doPostValidationStuff() throws Exception {
        if ( getContext().getValidationErrors().hasFieldErrors() )
        {
            super.filling();
        }
    }

    @DefaultHandler
    @Logged
    public Resolution updatingTask() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        
        HintManager hm = super.getHintManager();
        TaskManager tm = super.getTaskManager();
        Task t = tm.findById(super.getTaskId());
        
        t.setSummary(this.getSummary());
        t.setDetails(this.getDetails());

        Mode m = Mode.valueOf(this.getMode());
        switch(m)
        {
            case DATES:
                t.setRedline(DateUtil.parse(this.getRedline(), sdf));
                t.setDeadline(DateUtil.parse(this.getDeadline(), sdf));
                hm.discardHintsForTask(t);
                break;
            case HINTS:
                t.setRedline(null);
                t.setDeadline(null);
                this.setUpHints(hm, t);
                break;
            default:
                throw new RuntimeException("Unexpected case!");
        }

        t.setEffort(this.getEffort());
        
        tm.update(t);
        
        GtdClipboard clip = super.getClipboard();
        if (clip.isTaskInClipboard(t))
        {
            clip.copyTask(t);
        }

        RedirectResolution rr;
        
        ReturnPageBean rpb = super.getReturnPageBean();

        if (rpb==null)
        {
            rr = new RedirectResolution(ListViewActionBean.class);
            rr.addParameter(ListViewActionBean.IncomingParameters.LIST_ID, t.getList().getId());
            return rr;
        } else {
            rr = NextDestinationUtil.getRedirection(rpb);
        }
            
        return rr;
    }

    private void setUpHints(HintManager hm, Task t) throws Exception
    {
        Collection<Hint> oldHints = hm.getHintsForTask(t);
        String[]         newHints = this.getHints();
        if (newHints==null)
        {
            newHints = new String[]{};
        }

        Map<String, Boolean> newHMap = new TreeMap<String, Boolean>();
        for (String nh: newHints)
        {
            newHMap.put(nh, Boolean.TRUE);
        }
        
        for (Hint old: oldHints)
        {
            String oldHc = old.getHintCondition().toString();
            if (!newHMap.containsKey(oldHc))
            {
                hm.remove(old);
            } else {
                newHMap.remove(oldHc);
            }
        }

        for (String hc : newHMap.keySet())
        {
            hm.create(new Hint(t, HintCondition.valueOf(hc)));
        }
    }

    public static enum Mode
    {
        DATES,
        HINTS
    }
}
